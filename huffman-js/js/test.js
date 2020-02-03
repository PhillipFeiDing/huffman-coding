const {Encoder} = require('./encoder')
const {Decoder} = require('./decoder')

const encoder = new Encoder()
encoder.encode('sdjkfbeiwbfiuwefuiwefbuibvuibfuiwefwe')
// console.log(encoder.getScheme())
const scheme = encoder.getScheme()
const decoder = new Decoder(scheme)
console.log(decoder.decode(encoder.getEncoded()))