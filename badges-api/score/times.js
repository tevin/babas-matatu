'use strict';
import * as haversine from 'haversine';

export async function scoreTime(legs, user) {
    for (leg in legs) {
        if (leg.legType === 'matatu') {
            const {wimtLineID, waypoints} = leg;
            const stopsTraversed = waypoints.length;
            
            // Calculate Total Distance Between Waypoints
            for (waypoint in waypoints) {
                // need to do some data cleansing
            }
            
        }
    }

}

/**
 * Gets total distance between list of points
 * @param {Array} points List of points as an array of arrays, where each array is of the form [lat, long]
 */
function getTotalDistance(points) {
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