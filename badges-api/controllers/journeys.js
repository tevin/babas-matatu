exports.index = (req, res) => {
  res.json({'message': 'Welcome'})
}

exports.save = (req, res) => {
  res.json(req.body)
}
