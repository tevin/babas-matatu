const journeysApi = require('../controllers/journeys')
const usersApi = require('../controllers/users')
module.exports = (app) => {
  app.get('/', (req, res) => {
    res.send('Welcome to BabasMatatu')
  })
  app.get('/journeys', journeysApi.index)
  app.post('/journeys', journeysApi.save)

  app.post('/users', usersApi.create)
};
