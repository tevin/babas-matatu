const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const emissionsLeaderboardSchema = new Schema({
    user: {
        type: Schema.ObjectId,
        ref: 'User'
    },
    journey: {
        type: Schema.ObjectId,
        ref: 'Journey'
    },
    score: {
        type: Number,
        default: 0
    }
});

module.exports = mongoose.model('EmissionsLeaderboard', emissionsLeaderboardSchema)
