module.exports = function costScore({ legs }) {
  const usage = legs.reduce((p,c) => p + parseInt(c.distance), 0)
  return {
    type: `cost`,
    score: 20 - Math.round(Math.log2(usage))
  }
}
