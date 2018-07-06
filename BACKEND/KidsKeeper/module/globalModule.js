const errorConfig =  require('../config/error');
const pool = require('../config/dbPool');

let connect = function (callback) {
    pool.getConnection(function (err, connection) {
        if (err) {
            console.log("get Connection error : ", err.message);
            callback(err, connection, null);
        } else callback(null, connection);
    });
}

let releaseConnection = function (connection, apiName, callback) {
    connection.release();
    callback(null, null, apiName);
};


let asyncCallback = function (err, connection, result, res) {
    if (connection) connection.release();

    if (!!err && err !== "ALREADY_SEND_MESSAGE") {
        console.log("err", err, result, err.message);
        res.status(503).send({message: "FAILURE"});
    }
};

module.exports.connect = connect;
module.exports.releaseConnection = releaseConnection;
module.exports.asyncCallback = asyncCallback;