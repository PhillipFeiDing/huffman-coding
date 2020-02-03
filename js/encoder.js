class Encoder {
    
    constructor() {
        this.encoded = null
        this.scheme = null
        this.freqChart = null;
    }

    encode(message) {
        const tree = new HuffmanTree(message)
        this.scheme = tree.getEncodingScheme()
        this.freqChart = tree.getFrequencyChart()

        this.encoded = ''
        for (let i = 0; i < message.length; i++) {
            const s = this.scheme.get(message[i])
            this.encoded += s
        }
    }

    getEncoded() {
        return this.encoded
    }

    getScheme() {
        return this.scheme
    }

    getFreqChart() {
        return this.freqChart
    }
}