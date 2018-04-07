'use strict';
const distance = require('./helpers/distance');

module.exports = function scoreTime({legs}) {
    let totalScores = [];
    for (let i = 0; i < legs.length; i++) {
        const leg = legs[i];
        if (leg.legType === 'matatu') {
            const {timeTaken, wimtLineID, waypoints} = leg;
            const stopsTraversed = waypoints.length;
            
            // Calculate Total Distance Between Waypoints
            const listOfPoints = waypoints.map((w) => w.location);
            const totalDistance = distance(listOfPoints);

            // Score Leg - The Higher the better.
            const timeScore = totalDistance / stopsTraversed / timeTaken;
            totalScores.push({wimtLineID, timeScore});
        }
    }
    return {
        type: 'time',
        score: totalScores
    };
}