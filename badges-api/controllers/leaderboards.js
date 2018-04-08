const TimesLeaderboard = require('../models/times');
const CostLeaderboard = require('../models/costs');
const EmissionsLeaderboard = require('../models/emissions');
const Users = require('../models/user');

const leaderboards = [
    {type: 'time', lb: TimesLeaderboard},
    {type: 'cost', lb: CostLeaderboard},
    {type: 'emissions', lb: EmissionsLeaderboard}
]

exports.get = async (req, res) => {
    const lbType = req.params.type;
    const user = req.query.user;
    const {lb} = leaderboards.find(l => l.type === lbType);
    
    if(!lb) {
        console.log(req.params);
        return res.status(500).json('Type is missing');
    }
    let query = {};
    if (lbType === 'time') {
        query.line = req.query.line;
    }

    const topTen = await lb.find(query).limit(10);
    const toReturn = {topTen}
    if(user) {
        toReturn.userPosition = await lb.findOne({...query, user});
        toReturn.userRank = await lb.find({...query, pts: {$gt: toReturn.userPosition.score}}).count() + 1;
    }
    res.json(toReturn);
}
