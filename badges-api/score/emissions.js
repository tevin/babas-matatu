/**
 * @see {https://ec.europa.eu/clima/policies/transport/vehicles/cars}
 */
 const EMISSIONS_PER_KM = 118.1
module.exports = function scoreEmissions({ legs }) {
  const usage = legs.reduce((p,c) => p + parseInt(c.distance), 0) * EMISSIONS_PER_KM
  return {
    type: `emissions`,
    score: 20 - Math.round(Math.log2(usage))
  }
}
