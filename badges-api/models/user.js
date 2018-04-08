const mongoose = require('mongoose')
const Schema = mongoose.Schema

const userSchema = new Schema({
  name: { type: String, default: '' },
  email: { type: String, default: '' },
  username: { type: String, default: '' },
  trophies: { type: Object, default: {} }
})

module.exports = mongoose.model('User', userSchema)
