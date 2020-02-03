class HuffmanTree {

    constructor(input) {
        if (typeof input === 'string') {
            this.construct_from_encoder(input)
        } else {
            this.construct_from_decoder(input)
        }
    }

    construct_from_encoder(message) {
        const dict = new HashMap()
        for (let i = 0; i < message.length; i++) {
            const c = message.charAt(i)
            if (dict.containsKey(c)) {
                dict.put(c, dict.get(c) + 1)
            } else {
                dict.put(c, 1)
            }
        }
        this.dict = dict
        const pq = new PriorityQueue([], function (self, other) {
                if (self.count != other.count) {
                    return self.count - other.count
                }
                if (self.symbol === null && other.symbol !== null) {
                    return 1
                }
                if (self.symbol !== null && other.symbol === null) {
                    return -1
                }
                if (self.symbol === null && other.symbol === null) {
                    return 0
                }
                return self.symbol.charCodeAt(0) - other.symbol.charCodeAt(0)
            }
        )
        const keySet = this.dict.keySet()
        for (let i = 0; i < keySet.length; i++) {
            const symbol = keySet[i]
            pq.push(new TreeNode(symbol, this.dict.get(symbol)))  
        }
        while (pq.size() > 1) {
            const left = pq.pop()
            const right = pq.pop()
            const parent = new TreeNode(null, left.getCount() + right.getCount())
            parent.setLeft(left)
            parent.setRight(right)
            pq.push(parent)
        }
        this.root = pq.pop()
        if (this.root.getSymbol() !== null) {
            const newRoot = new TreeNode(null, this.root.getCount())
            newRoot.setLeft(this.root)
            this.root = newRoot
        }
        this.scheme = new HashMap()
        this.traverse(this.root, '')
        this.encoding = true
    }

    construct_from_decoder(scheme) {
        this.scheme = scheme
        this.root = new TreeNode(null, 0)
        const keySet = this.scheme.keySet()
        for (let counter = 0; counter < keySet.length; counter++) {
            const c = keySet[counter]
            const s = scheme.get(c)
            let curr = this.root
            for (let i = 0; i < s.length; i++) {
                let symbol = null
                if (i === s.length - 1) {
                    symbol = c 
                }
                if (s.charAt(i) === '0') {
                    if (curr.getLeft() === null) {
                        curr.setLeft(new TreeNode(symbol, 0))
                    }
                    curr = curr.getLeft()
                } else {
                    if (curr.getRight() === null) {
                        curr.setRight(new TreeNode(symbol, 0))
                    }
                    curr = curr.getRight()
                }
            }
        }
        this.encoding = false;
    }

    getRoot() {
        return this.root
    }

    getEncodingScheme() {
        return this.scheme
    }

    traverse(curr, prefix) {
        if (curr === null) {
            return
        }
        if (curr.getSymbol() !== null) {
            this.scheme.put(curr.getSymbol(), prefix)
        } else {
            this.traverse(curr.getLeft(), prefix + '0')
            this.traverse(curr.getRight(), prefix + '1')
        }
    }

    getFrequencyChart() {
        return this.dict
    }
}