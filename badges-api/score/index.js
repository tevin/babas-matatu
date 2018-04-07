const costScore = require('./cost')
const emissionsScore = require('./emissions')
const loudnessScore = require('./loudness')

module.exports = function (journey) {
  return [costScore, emissionsScore, loudnessScore].map((scorer) => {
    return scorer(journey)
  })
}
