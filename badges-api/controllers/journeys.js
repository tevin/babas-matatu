const Journey = require('../models/journey')
const TimesLeaderboard = require('../models/times');
exports.index = (req, res) => {
  res.json({'message': 'Welcome'})
}
const scoreGenerator = require('../score')
exports.save = async (req, res) => {
  const journey = await Journey.create(req.body);
  const scores = scoreGenerator(journey);

  // save scores for times leaderboard
  const {score} = scores.find(s => s.type === 'time');
  score.map(async (leg) => {
    const toSave = {
      user: journey.user,
      journey: journey._id,
      line: leg.wimtLineID,
      score: leg.timeScore
    }
    await TimesLeaderboard.create(toSave);
  });

  res.json({journey, scores});
}
