'use strict';
const haverside = require('haversine');

/**
 * Gets total distance between list of points
 * 
 * @param {Array} points List of points as an array of arrays, where each element is of the form `[lat, long]`
 * @returns Total Distance in meters
 */
module.exports = function getTotalDistance(points) {
    let totalDistanceSoFar = 0;
    for(let i = 1; i < points.length; i++) {
        const start = {
            latitude: points[i-1][0], 
            longitude: points[i-1][1]
        };
        const end = {
            latitude: points[i][0], 
            longitude: points[i][1]
        }
        totalDistanceSoFar += haversine(start, end, {unit: 'meter'})
    }
    return totalDistanceSoFar;
}