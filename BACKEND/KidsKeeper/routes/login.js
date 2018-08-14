const pool = require('../config/db_pool');
const router = require('express').Router();
const bcrypt = require('bcrypt-nodejs');
const async = require('async');
const globalModule = require('../module/globalModule');
const nodemailer = require('nodemailer');
const aws = require('aws-sdk');
const multer = require('multer');
const multerS3 = require('multer-s3');
aws.config.loadFromPath('./config/aws_config.json');
const s3 = new aws.S3();
const upload = multer({
    storage: multerS3({
        s3: s3,
        bucket: 'sungshinproject',
        acl: 'public-read',
        key: function (req, file, cb) {
            cb(null, Date.now() + '.' + file.originalname.split('.').pop())
        }
    })
});

/**
 * api 목적        : 로그인
 * request params : { String email: "이메일"
 *                    String password : "패스워드"
 *                    }
 */
router.post('/', function (req, res) {

    let resultJson = {
        message: '',
        idx: '',
        nickname:''
    };

    let selectUserInfo = function (connection, callback) {
        connection.query("select * from User where email = ? ", req.body.email, function (error, rows) {
            if (error) callback(error, connection, "Selecet query Error : ");
            else {
                console.log(rows);
                console.log(rows.length);
                if (rows.length === 0) {
                    // 존재하는 아이디가 없는 경우
                    res.status(200).send({ message: "NOT_SIGN_UP" });
                    callback("ALREADY_SEND_MESSAGE", connection, "api : /login/");
                } else {
                    callback(null, connection, rows);
                }
            }
        });
    }

    let comparePW = function (connection, rows, callback) {
        bcrypt.compare(req.body.password, rows[0].password, function (err, isCorrect) {
            // isCorrect === true : 일치, isCorrect === false : 불일치
            if (err) {
                res.status(200).send({ message: "NOT_SIGN_UP" });
                callback(err, connection, "Bcrypt Error : ", res);
            }

            if (!isCorrect) {
                res.status(200).send({ message: "INCORRECT_PASSWORD" });
            } else {
                resultJson.message = "SUCCESS";

                resultJson.idx = rows[0].idx;
                resultJson.nickname = rows[0].nickname;
                res.status(200).send(resultJson);
            }
            callback(null, connection, "api : /login/");
        });
    }

    var task = [globalModule.connect.bind(this), selectUserInfo, comparePW, globalModule.releaseConnection.bind(this)];
    async.waterfall(task, globalModule.asyncCallback.bind(this));
});




module.exports = router;