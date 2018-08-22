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
 * 할일 조회
 */
router.get('/', function (req, res) {

    let resultJson = {
        message: 'SUCCESS',
        todos: []
    };

    let selectTodo = function (connection, callback) {
        connection.query("SELECT * FROM sungshinDB.Notice where user_idx = ? ", req.query.user_idx, function (error, rows) {
            if (error) callback(error, connection, "Selecet query Error : ");
            else {
                if (rows.length === 0) {
                    res.status(200).send({ message: "TODO_NOT_EXIT" });
                    callback("ALREADY_SEND_MESSAGE", connection, "api : /notice/");
                } else {
                    for (var x in rows) {
                        var todo = {}
                        todo.idx = rows[x].idx;
                        todo.todo = rows[x].todo;
                        todo.isdo = rows[x].isDo;
                        resultJson.todos.push(todo);
                    }
                    res.status(200).send(resultJson)
                    callback(null, connection, "api : /words/");
                }
            }
        });
    }
    var task = [globalModule.connect.bind(this), selectTodo, globalModule.releaseConnection.bind(this)];
    async.waterfall(task, globalModule.asyncCallback.bind(this));
});



/**
 * 할일 추가
 */

router.post('/write', function (req, res) {

    let updateComment = function (connection, callback) {
        let insertquery = "insert into Notice (todo, user_idx, isDO)"
            + "values ( ? , ? ,0);"
        let params = [
            req.body.todo,
            req.body.user_idx
        ]
        connection.query(insertquery, params, function (error, rows) {
            if (error) {
                callback(error, connection, "insertquery Error : ", res);
            } else {
                res.status(200).send({ message: "SUCCESS" });
                callback(null, connection, "api /notice/write");
            }
        });
    }
    var task = [globalModule.connect.bind(this), updateComment, globalModule.releaseConnection.bind(this)];
    async.waterfall(task, globalModule.asyncCallback.bind(this));
});


module.exports = router;