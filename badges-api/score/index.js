const costScore = require('./cost')
const emissionsScore = require('./emissions')
const loudnessScore = require('./loudness')
const timeScore = require('./times')

module.exports = function (journey) {
  return [emissionsScore, timeScore].map((scorer) => {
    return scorer(journey)
  })
}
