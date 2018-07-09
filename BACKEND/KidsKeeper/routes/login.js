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
    key: function(req, file, cb) {
      cb(null, Date.now() + '.' + file.originalname.split('.').pop())
    }
  })
});



/**
 * api 목적        : iotNum 체크
 * request params : { string iotNumber: "아이디" }
 */
router.get('/check_iotNumber', function (req, res) {

    let checkID = function (connection, callback) {
        connection.query('select * from User where iotNumber = ? ', req.query.iotNumber, function (error, rows) {
            if (error) {
                callback(error, connection, "Select query Error : ", res);
            } else {
                if (rows.length !== 0) {
                    res.status(200).send({message: "SUCCESS"});
                     callback("NOT_VAILD", connection, "api : /login/iotNumber");
                } else {
                    res.status(200).send({message: "not_vaild"});
                    callback(null, connection, "api : /login/iotNumber");
                }
            }
        });
    }

    var task = [globalModule.connect.bind(this), checkID, globalModule.releaseConnection.bind(this)];
    async.waterfall(task, globalModule.asyncCallback.bind(this));
});


module.exports = router;