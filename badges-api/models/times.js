const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const timeLeaderboardSchema = new Schema({
    user: {
        type: Schema.ObjectId,
        ref: 'User'
    },
    journey: {
        type: Schema.ObjectId,
        ref: 'Journey'
    },
    line: {
        type: String,
    },
    score: {
        type: Number,
        default: 0
    }
});

module.exports = mongoose.model('TimeLeaderboard', timeLeaderboardSchema)
