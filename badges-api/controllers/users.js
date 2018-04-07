const User = require('../models/user')
exports.create = (req, res, next) => {
  const user = new User(req.body)
  user.save((err, result) => {
    if(err) return next(err)
    res.json(result)
    next()
  })
}
