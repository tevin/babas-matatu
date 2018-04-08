require('dotenv').config()

const fs = require('fs');
const join = require('path').join
const express = require('express')
const mongoose = require('mongoose')
const config = require('./badges-api/config/config')
const models = join(__dirname, './badges-api/models')
const port = process.env.PORT || 3000

const app = express()
const connect = function () {
  mongoose.connect(config.db)
  return mongoose;
}

const connection = connect().connection

/**
 * Expose
 */

module.exports = {
  app,
  connection
};

// Bootstrap models
fs.readdirSync(models)
  .filter(file => ~file.indexOf('.js'))
  .forEach(file => require(join(models, file)))

// Bootstrap routes
require('./badges-api/config/express')(app)
require('./badges-api/config/routes')(app)

connection.on('error', console.error.bind(console, 'connection error:'));
connection.once('open', listen)


function listen () {
  app.use(function (err, req, res, next) {
    res.status(500).send(err)
    next()
  })
  app.listen(port)
  console.log('Express app started on port ' + port)
}
