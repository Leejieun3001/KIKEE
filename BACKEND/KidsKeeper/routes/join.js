const pool = require('../config/db_pool');
const router = require('express').Router();
const bcrypt = require('bcrypt-nodejs');
const async = require('async');
const globalModule = require('../module/globalModule');
const nodemailer = require('nodemailer');
const mailConfig = require('../config/mailAccount');
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
 * api 목적        : 회원 등록
 * request params : { int idx: "회원 번호"
 *                    String iotNumber : "아이오티 번호" 
 *                    String password : "패스워드"
 *                    String email : "이메일"
 *                    String nickname : "닉네임"
 *                    }
 */
router.post('/', function (req, res) {
    let checkAlreadyJoin = function (connection, callback) {
        connection.query('select * from User where email = ?', req.body.email, function (error, rows) {
            if (error) {
                callback(error, connection, "Select query Error : ", res);
            } else {
                if (rows.length !== 0) {
                    res.status(200).send({ message: "ALRAEDY_JOIN" });
                    callback("ALREADY_SEND_MESSAGE", connection, "api : /join");
                } else {
                    callback(null, connection);
                }
            }
        });
    }

    let bcryptedPassword = function (connection, callback) {
        bcrypt.hash(req.body.password, null, null, function (err, hash) {
            if (err) callback(err, connection, "Bcrypt hashing Error : ", res);
            else callback(null, connection, hash);
        });
    }
    let insertUser = function (connection, hash, callback) {
        let updateQuery = 'update User ' +
            'set password = ?, ' +
            '    email = ? ,' +
            '    nickname = ? ' +
            'where iotNumber = ? ';
        let params = [
            hash,
            req.body.email,
            req.body.nickname,
            req.body.iotNumber
        ];

        connection.query(updateQuery, params, function (err, rows) {
            if (err) {
                callback(err, connection, "updateQuery Error : ", res);
            } else {
                // res.status(200).send({message: "SUCCESS"});
                callback(null, connection);
            }
        });
    }

    let insertVoice = function (connection, callback) {
        let insertQuery = 'insert into Voice (user_idx, record01, record02, record03, record04)values (? , 0, 0, 0, 0);';
        let params = [
            req.body.idx,
        ];

        connection.query(insertQuery, params, function (err, rows) {
            if (err) {
                callback(err, connection, "insertQuery Error : ", res);
            } else {
                res.status(200).send({ message: "SUCCESS" });
                callback(null, connection, "api : /join");
            }
        });
    }
    var task = [globalModule.connect.bind(this), checkAlreadyJoin, bcryptedPassword, insertUser, insertVoice, globalModule.releaseConnection.bind(this)];
    async.waterfall(task, globalModule.asyncCallback.bind(this));
});


/**
 * api 목적        : iotNum 체크
 * request params : { string iotNumber: "로봇 번호" }
 */
router.get('/check_iotNumber', function (req, res) {

    let checkID = function (connection, callback) {
        connection.query('select idx from User where iotNumber = ? ', req.query.iotNumber, function (error, rows) {
            if (error) {
                callback(error, connection, "Select query Error : ", res);
            } else {
                if (rows.length !== 0) {
                    res.status(200).send({
                        message: "SUCCESS",
                        user_idx: rows
                    });
                    callback(null, connection, "api : /join/iotNumber");

                } else {
                    res.status(200).send({ message: "NOT_VAILD" });
                    callback(null, connection, "api : /join/iotNumber");
                }
            }
        });
    }

    var task = [globalModule.connect.bind(this), checkID, globalModule.releaseConnection.bind(this)];
    async.waterfall(task, globalModule.asyncCallback.bind(this));
});

/**
 * api 목적        : 이메일 중복체크
 * request params : { string email: "이메일" }
 */
router.get('/check_dupplicate', function (req, res) {


    let checkID = function (connection, callback) {
        connection.query('select * from User where email = ?', req.query.email, function (error, rows) {
            if (error) {
                callback(error, connection, "Select query Error : ", res);
            } else {
                if (rows.length !== 0) {
                    res.status(200).send({ message: "ALREAY_JOIN" });
                    callback("ALREADY_SEND_MESSAGE", connection, "api : /join/check_dupplicate");
                } else {
                    res.status(200).send({ message: "SUCCESS" });
                    callback(null, connection, "api : /join/check_dupplicate");
                }
            }
        });
    }

    var task = [globalModule.connect.bind(this),  checkID, globalModule.releaseConnection.bind(this)];
    async.waterfall(task, globalModule.asyncCallback.bind(this));
});

/**
 * api 목적        : email 인증
 * request params : tempEmail(query)
 */

router.get('/verificationCode', function (req, res) {

    var Transport = nodemailer.createTransport({
        service: "Gmail",
        auth: {
            user: mailConfig.jieun.user,
            pass: mailConfig.jieun.pass
        }
    });

    var rand;
    var resultJson = {
        message: '',
        verificationCode: ''
    };

    var selectUserInfo = function (connection, callback) {
        let duplicate_check_query = "select * from User where email = ?";
        connection.query(duplicate_check_query, req.query.tempEmail, function (err, data) {
            if (err) {
                console.log("duplicate check select query error : ", err);
                callback(err, connection, "api : /join/verificationCode");
            } else {
                if (data.length == 0) {
                    callback(null, connection);
                }
                else {
                    res.status(201).send({message: "ALREADY_JOIN"});
                    callback("ALREADY_SEND_MESSAGE", connection, "api : /join/verificationCode");
                }
            }
        });
    }

    var sendMail = function (connection, callback) {
        rand = Math.floor((Math.random() * 10000));
        let mailOption = {
            to: req.query.tempEmail,
            subject: "안녕하세요. KIKEE 입니다.",
            html: "안녕하세요,<br> 고객님의 인증번호는 " + rand + "입니다. <br>"
                + "<br>어플로 돌아가셔서 인증번호를 입력해 주세요.</br>"
                + "<br>감사합니다.</br>"
        };
        Transport.sendMail(mailOption, function (error, info) {
            if (error) {
                callback(error, connection, "Transport Error : ");
            } else {
                resultJson.message = "SUCCESS";
                resultJson.verificationCode = rand;
                res.status(201).send(resultJson);
                console.log(1);
                callback(null, connection, "api : /join/verificationCode", res);
            }
        });
    }

    var verificationCode_task = [globalModule.connect.bind(this), selectUserInfo, sendMail, globalModule.releaseConnection.bind(this)];
    async.waterfall(verificationCode_task, globalModule.asyncCallback.bind(this));
});


module.exports = router;