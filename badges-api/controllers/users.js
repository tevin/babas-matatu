const User = require('../models/user')
const TimesLeaderboard = require('../models/times');
const CostLeaderboard = require('../models/costs');
const EmissionsLeaderboard = require('../models/emissions');
const ObjectId = require('mongoose').Types.ObjectId; 
const lbs = [
  {type: 'time', lb: TimesLeaderboard},
  {type: 'cost', lb: CostLeaderboard},
  {type: 'emissions', lb: EmissionsLeaderboard}
]
exports.create = (req, res, next) => {
  const user = new User(req.body)
  user.save((err, result) => {
    if(err) return next(err)
    res.json(result)
    next()
  })
}

exports.get = async (req, res) => {
  const uid = req.params.uid;
  const user = await User.find({_id: uid});
  const trophies = [];
  let boards = [];

  for(let i = 0; i < lbs.length; i++ ) {
    const lb = lbs[i].lb;
    const leaderboard = lbs[i].type
    const entry = await lb.find({user: uid});
    const rank = await lb.find({pts: {$gt: entry.score}}).count() + 1;
    const totalEntries = await lb.count();
    if(entry.length > 0) {
      boards.push({leaderboard, entry, rank});
      if (rank === 1) {
        trophies.push({type: 'first', msg: `#1 on the ${leaderboard} leaderboard`});
      }
      if (rank === totalEntries) {
        trophies.push({type: 'last', msg: `You're last on the ${leaderboard} leaderboard`});
      }
    }
  }

  res.json({boards, trophies});
}
