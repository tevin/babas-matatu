'use strict';

export async function scoreTime(legs, user) {
    for (leg in legs) {
        if (leg.legType === 'matatu') {
            const {wimtLineID, waypoints} = leg;
            const stopsTraversed = waypoints.length;
            
            // Calculate Total Distance Between Waypoints
            
        }
    }

}