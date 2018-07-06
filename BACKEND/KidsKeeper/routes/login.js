const pool = require('../config/db_pool');
const router = require('express').Router();
const bcrypt = require('bcrypt-nodejs');
const async = require('async');
//const jwtModule = require('../module/jwtModule');
const globalModule = require('../module/globalModule');


    
/**
 * api 목적        : 로그인 (일반)
 * request params : {string id: "아이디", string password: "비밀번호"}
 */
router.get('/', function (req, res) {


    var task = [globalModule.connect.bind(this),  globalModule.releaseConnection.bind(this)];
    async.waterfall(task, globalModule.asyncCallback.bind(this));
});

module.exports = router;