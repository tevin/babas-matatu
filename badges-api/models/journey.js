const mongoose = require('mongoose')
const Schema = mongoose.Schema

const journeySchema = new Schema({
    user: {
        type: Schema.ObjectId,
        ref: 'User'
    },
    to: {
        type: [Number],
        index: '2d'
    },
    from: {
        type: [Number],
        index: '2d'
    },
    legs: [{
        legType: {
            type: String,
            default: 'matatu'
        },
        start: {
            type: [Number],
            index: '2d'
        },
        end: {
            type: [Number],
            index: '2d'
        },
        wimtLineID: {
            type: String
        },
        description: {
            type: String
        },
        distance: {
            type: Number
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
mongoose.model('Journey', journeySchema)
