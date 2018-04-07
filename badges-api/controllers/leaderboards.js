const TimesLeaderboard = require('../models/times');
const CostLeaderboard = require('../models/costs');
const EmissionsLeaderboard = require('../models/emissions');
const leaderboards = [
    {type: 'time', lb: TimesLeaderboard},
    {type: 'cost', lb: CostLeaderboard},
    {type: 'emissions', lb: EmissionsLeaderboard}
]

exports.get = async (req, res) => {
    const lbType = req.params.type;
    const user = req.params.user;
    const {lb} = leaderboards.find(l => l.type === lbType);
    
    if(!lb) {
        console.log(req.params);
        return res.status(500).json('Type is missing');
    }
    let query = {};
    if (lbType === 'time') {
        query.line = req.params.line;
    }

    const topTen = await lb.find(query).limit(10);
    const userPosition = await lb.findOne(Object.assign({user: user}, query));
    res.json({topTen, userPosition});
}