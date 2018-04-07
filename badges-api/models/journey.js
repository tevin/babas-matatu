const mongoose = require('mongoose')
const Schema = mongoose.Schema



const journeySchema = new Schema({
    user: {
        type: Schema.ObjectId,
        ref: 'User',
        required: true
    },
    to: {
        type: [Number],
        index: '2d',
        required: true
    },
    from: {
        type: [Number],
        index: '2d',
        required: true
    },
    legs: [{
        legType: {
            type: String,
            default: 'matatu',
        },
        start: {
            type: [Number],
            index: '2d',
            required: true
        },
        end: {
            type: [Number],
            index: '2d',
            required: true
        },
        wimtLineID: {
            type: String,
            required: true
        },
        description: {
            type: String
        },
        distance: {
            type: Number,
            required: true
        },
        timeTaken: {
            type: Number
        },
        startStopId: {
            type: String
        },
        endStopId: {
            type: String
        },
        steps: [{
            instructions: {
                type: String
            },
            location: {
                type: [Number],
                index: '2d'
            }
        }],
        waypoints: [{
            stopId: {
                type: String
            },
            location: {
                type: [Number],
                index: '2d'
            }
        }]
    }],
    createdAt: {
        type: Date,
        default: Date.now
    }
})
module.exports = mongoose.model('Journey', journeySchema)
