/**
 * @see {https://ec.europa.eu/clima/policies/transport/vehicles/cars}
 */
 const EMISSIONS_PER_KM = 118.1
module.exports = function scoreEmissions(journey) {
  return journey.emissions * EMISSIONS_PER_KM
}
