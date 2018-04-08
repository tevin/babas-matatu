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
    if(!lbType) {
        return res.status(400).json('Please provide a type');
    }

    const {lb} = leaderboards.find(l => l.type === lbType);
    
    if(!lb) {
        console.log(req.params);
        return res.status(500).json('Could not find leaderboard type specified');
    }
    let query = {};
    if (lbType === 'time') {
        query.line = req.query.line;
    }

    const usersInLb = await lb.distinct('user', query);
    const scoresP = usersInLb.map(async (user) => {
        const entries = await lb.find({...query, user});
        const score = entries.reduce((acc, entry) => {
            return acc += entry.score;
        }, 0);
        const {name} = await Users.findById(user);
        console.log({name, score});
        return {name, score};
    });
    const scores = await Promise.all(scoresP);
    scores.sort((e1, e2) => {
        return e1.score - e2.score;
    });

    res.json(scores);
}
