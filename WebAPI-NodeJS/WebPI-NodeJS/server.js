'use strict';
var http = require('http');
var port = process.env.PORT || 1337;

var mysql = require('mysql');
var url = require('url');
var ConString = { host: '10.40.0.16', user: 'TestUser', password: 'TestUserPass', database: 'TemperatureDB' };


http.createServer(function (req, res) {

    res.writeHead(200, { 'Content-Type': 'application/json' });

    var queryData = url.parse(req.url, true).query;

    var urlPar = req.url.toLowerCase();
    if (urlPar == "/sensors") {
        var retSens = GetSensors(res);
    }    
    else if (urlPar.startsWith("/last")) {
        if (queryData.sensor) {
            if (queryData.count) {
                var countVal = Number(queryData.count);
                if (countVal) {
                    var retLast = GetValues(res, queryData.sensor, countVal);
                }
                else
                {
                    var retLast = GetValues(res, queryData.sensor, 1);                    
                }
            }
            else {
                var retLast = GetValues(res, queryData.sensor, 1); 
            }
        }
        else
        {
            res.end("Error");
        }
    }
    else{
        res.end();
    }
    
}).listen(port);

console.log('Server running at http://127.0.0.1:1337/');

function GetValues(res, sensor, count)
{
    var connection = mysql.createConnection(ConString);
    connection.connect();

    var ret = null;
    connection.query('SELECT * FROM TemperatureDB.SensorValue where Sensor = ? ORDER BY id DESC LIMIT ?', [sensor, count], function (error, results, fields) {
        if (error) {
            error
            connection.end();
        };

        ret = JSON.stringify(results);


        if (ret) {
            res.write(ret);
        }

        res.end();

        connection.end();
        return ret;
    });     
}

function GetSensors(res)
{
    var connection = mysql.createConnection(ConString);
    connection.connect();

    var ret = null;
    connection.query('SELECT *  FROM Sensors', function (error, results, fields) {
        if (error) {
            error
            connection.end();
        };
      
        ret = JSON.stringify(results);

        if (ret) {
        res.write(ret);
        }

        res.end();

        connection.end();
        return ret;
    });      
}