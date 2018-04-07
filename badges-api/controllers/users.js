const User = require('../models/user')
const TimesLeaderboard = require('../models/times');
const CostLeaderboard = require('../models/costs');
const EmissionsLeaderboard = require('../models/emissions');
const ObjectId = require('mongoose').Types.ObjectId; 
const lbs = [TimesLeaderboard, CostLeaderboard, EmissionsLeaderboard]

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
  const trophies = user.trophies;
  const query = {
    user: '5ac8d375e488b67b9ac4917b'
  }
  let entries = [];
  await lbs.map(async (lb) => {
    const entry = await lb.find(query);
    const rank = await lb.find({pts: {$gt: entry.score}}).count() + 1;
    if(entry) {
      console.log("pushing entry")
      entries.push({entry, rank});
    }
  });
  console.log("ENTRIES")
  console.log(entries)

  res.json({entries, trophies});
}
