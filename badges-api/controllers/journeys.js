const Journey = require('../models/journey')
const TimesLeaderboard = require('../models/times');
const CostLeaderboard = require('../models/costs');
const EmissionsLeaderboard = require('../models/emissions');
exports.index = (req, res) => {
  res.json({'message': 'Welcome'})
}
const scoreGenerator = require('../score')
exports.save = async (req, res) => {
  const journey = await new Journey(req.body);
  const scores = scoreGenerator(journey);
  const defaults = {
    user: journey.user,
    journey: journey._id
  }
  res.json({journey, scores});
  // save scores for times leaderboard
  const {score} = scores.find(s => s.type === 'time');
  score.map(async (leg) => {
    const toSave = {
      ...defaults,
      line: leg.wimtLineID,
      score: leg.timeScore
    }
    await TimesLeaderboard.create(toSave);
  });

  const costScore = scores.find(s => s.type === 'cost');
  if(costScore)
  await CostLeaderboard.create({
    ...defaults,
    score: costScore.score
  })
  const emissionsScore = scores.find(s => s.type === 'emissions');
  if(emissionsScore)
  await EmissionsLeaderboard.create({
    ...defaults,
    score: emissionsScore.score
  })
}
