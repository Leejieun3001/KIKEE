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

 /**
  * 글 조회
  */

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
                        board= rows[x];
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