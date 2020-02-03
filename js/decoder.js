class Decoder {
    
    constructor(scheme) {
        this.tree = new HuffmanTree(scheme)
        this.scheme = scheme
    }

    decode(bitstring) {
        let idx = 0;
        let message = ''
        while (idx < bitstring.length) {
            let curr = this.tree.getRoot()
            while (curr.getSymbol() === null) {
                if (bitstring[idx] === '0') {
                    curr = curr.getLeft()
                } else {
                    curr = curr.getRight()
                }
                if (curr === null) {
                    throw new Error('ERROR: Malformed Message.')
                }
                idx++
            }
            message += curr.getSymbol()
        }
        return message
    }
}