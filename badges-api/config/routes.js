const journeysApi = require('../controllers/journeys')
const usersApi = require('../controllers/users')
const leaderboardApi = require('../controllers/leaderboards')

module.exports = (app) => {
  app.get('/', (req, res) => {
    res.send('Welcome to BabasMatatu')
  })
  app.get('/journeys', journeysApi.index)
  app.post('/journeys', journeysApi.save)
  app.post('/users', usersApi.create)
  app.get('/leaderboard/:type/:user/:line', leaderboardApi.get)
};
