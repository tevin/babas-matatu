'use strict';
require('./helpers/distance');

module.exports = async function scoreTime(legs, user) {
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
