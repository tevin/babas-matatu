const Journey = require('../models/journey')
exports.index = (req, res) => {
  res.json({'message': 'Welcome'})
}
const scoreGenerator = require('../score')
exports.save = (req, res, next) => {
  const journey = new Journey(req.body)
  journey.save((err, result) => {
    if(err) return next(err)
    const scores = scoreGenerator(result)
    res.json({journey: result, scores})
    next()
  })

}
