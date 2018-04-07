const journeysApi = require('../controllers/journeys')
module.exports = (app) => {
  app.get('/', (req, res) => {
    res.send('Welcome to BabasMatatu')
  })
  app.get('/journeys', journeysApi.index)
  app.post('/journeys', journeysApi.save)
};
