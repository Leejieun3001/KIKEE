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
 * "title": "글제목", 
 * "content": "내용",
 * "user_idx" : 2,
 * "nickname" : "관리자2"
 */

router.post('/write', function (req, res) {

    let updateBoard = function (connection, callback) {
        let insertquery = "insert into Board (title, content, date, hits, user_idx, nickname)"
            + " values (?,?, curdate(), 0 , ?,? );"
        let params = [
            req.body.title,
            req.body.content,
            req.body.user_idx,
            req.body.nickname
        ]
        connection.query(insertquery, params, function (error, rows) {
            if (error) {
                callback(error, connection, "insertquery Error : ", res);
            } else {
                res.status(200).send({ message: "SUCCESS" });
                callback(null, connection, "api /board/wirte");
            }
        });
    }
    var task = [globalModule.connect.bind(this), updateBoard, globalModule.releaseConnection.bind(this)];
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

    let updateBoard = function (connection, callback) {
        connection.query("update Board set hits = hits+1 where idx = ? ;", req.body.board_idx, function (error, rows) {
            if (error) callback(error, connection, "update query Error : ");
            else {
                callback(null, connection);
            }
        });
    }

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
                        callback(null, connection);
                    }
                }
            });
    }


    let selectCommentList = function (connection, callback) {
        connection.query("SELECT CommentBoard.board_idx, CommentBoard.user_idx, CommentBoard.content, User.nickname FROM CommentBoard"
            + " join User on User.idx = CommentBoard.user_idx where board_idx = ? ", req.body.board_idx, function (error, rows) {
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
    var task = [globalModule.connect.bind(this), updateBoard, selectBoard, selectCommentList, globalModule.releaseConnection.bind(this)];
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
        connection.query("SELECT  Board.idx, Board.title, Board.content, Board.date, Board.hits, Board.nickname ,Board.user_idx ,count(Board.idx) pick " +
            "FROM Board  left join Pick on Pick.board_idx = Board.idx  group by Board.idx", function (error, rows) {
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
/**
 * 댓글 쓰기
 *
 */

router.post('/write/comment', function (req, res) {

    let updateComment = function (connection, callback) {
        let insertquery = "insert into CommentBoard (board_idx, user_idx, content)"
            + " values (?, ? ,? );"
        let params = [
            req.body.board_idx,
            req.body.user_idx,
            req.body.content,
        ]
        connection.query(insertquery, params, function (error, rows) {
            if (error) {
                callback(error, connection, "insertquery Error : ", res);
            } else {
                res.status(200).send({ message: "SUCCESS" });
                callback(null, connection, "api /board/wirte/comment");
            }
        });
    }
    var task = [globalModule.connect.bind(this), updateComment, globalModule.releaseConnection.bind(this)];
    async.waterfall(task, globalModule.asyncCallback.bind(this));
});


module.exports = router;