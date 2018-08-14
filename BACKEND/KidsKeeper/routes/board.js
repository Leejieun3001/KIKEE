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
 * 글쓰기
 * title, content date hits user_idx nickname
 */

router.post('/write', function (req, res) {

    let resultJson = {
        message: 'SUCCESS',
        boards: []
    };

    let selectBoardList = function (connection, callback) {
        connection.query("SELECT * FROM Board;", function (error, rows) {
            if (error) callback(error, connection, "Selecet query Error : ");
            else {
                if (rows.length === 0) {
                    res.status(200).send({ message: "BOARD_NOT_EXIT" });
                    callback("ALREADY_SEND_MESSAGE", connection, "api : /board/write");
                } else {
                    for (var x in rows) {
                        var board = {}
                        board = rows[x];
                        resultJson.boards.push(board);
                    }
                    res.status(200).send(resultJson)
                    callback(null, connection, "api : /board/write");
                }
            }
        });
    }
    var task = [globalModule.connect.bind(this), selectBoardList, globalModule.releaseConnection.bind(this)];
    async.waterfall(task, globalModule.asyncCallback.bind(this));
});


/**
 * 글 조회
 */
router.post('/', function (req, res) {

    let resultJson = {
        message: 'SUCCESS',
        board: {},
        comments: []
    };

    let selectBoard = function (connection, callback) {
        connection.query("SELECT Board.idx, Board.title, Board.content, Board.date, Board.hits, Board.nickname ,Board.user_idx , Count(Pick.board_idx) pick "
            + "FROM Board  left join Pick on Pick.board_idx = Board.idx "
            + "where Board.idx = ? ", req.body.board_idx, function (error, rows) {
                if (error) callback(error, connection, "Selecet query Error : ");
                else {
                    if (rows.length === 0) {
                        res.status(200).send({ message: "BOARD_NOT_EXIT" });
                        callback("ALREADY_SEND_MESSAGE", connection, "api : /board");
                    } else {
                        resultJson.board = rows[0];
                        if (rows[0].user_idx === req.body.user_idx) { resultJson.isMine = 1; }
                        else { resultJson.isMine = 0; }
                        callback(null, connection, "api : /board");
                    }
                }
            });
    }

    let selectCommentList = function (connection, callback) {
        connection.query("SELECT * FROM CommentBoard where board_idx = ? ", req.body.board_idx, function (error, rows) {
            if (error) callback(error, connection, "Selecet query Error : ");
            else {
                if (rows.length === 0) {
                    res.status(200).send({ message: "BOARD_NOT_EXIT" });
                    callback("ALREADY_SEND_MESSAGE", connection, "api : /board");
                } else {
                    resultJson.message = "SUCCESS";

                    for (var x in rows) {
                        var comment = {}
                        comment = rows[x];
                        resultJson.comments.push(comment);
                    }
                    res.status(200).send(resultJson);
                    callback(null, connection, "api : /board");
                }
            }
        });
    }


    var task = [globalModule.connect.bind(this), selectBoard, selectCommentList, globalModule.releaseConnection.bind(this)];
    async.waterfall(task, globalModule.asyncCallback.bind(this));
});


/**
 * 전체 글 리스트 조회
 */

router.get('/total', function (req, res) {

    let resultJson = {
        message: 'SUCCESS',
        boards: []
    };

    let selectBoardList = function (connection, callback) {
        connection.query("SELECT * FROM Board;", function (error, rows) {
            if (error) callback(error, connection, "Selecet query Error : ");
            else {
                if (rows.length === 0) {
                    res.status(200).send({ message: "BOARD_NOT_EXIT" });
                    callback("ALREADY_SEND_MESSAGE", connection, "api : /board/total");
                } else {
                    for (var x in rows) {
                        var board = {}
                        board = rows[x];
                        resultJson.boards.push(board);
                    }
                    res.status(200).send(resultJson)
                    callback(null, connection, "api : /board/total");
                }
            }
        });
    }
    var task = [globalModule.connect.bind(this), selectBoardList, globalModule.releaseConnection.bind(this)];
    async.waterfall(task, globalModule.asyncCallback.bind(this));
});


/**
 * 글 수정
 */

/**
 * 글 좋아요
 */

/*
*글 싫어요
 *
 */

module.exports = router;