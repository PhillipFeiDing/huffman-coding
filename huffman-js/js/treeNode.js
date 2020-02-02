class TreeNode {
    
    constructor(symbol, count) {
        this.symbol = symbol
        this.count = count
        this.left = null
        this.right = null
    }

    getSymbol() {
        return this.symbol
    }

    getCount() {
        return this.count
    }

    getLeft() {
        return this.left
    }

    getRight() {
        return this.right
    }

    setLeft(left) {
        this.left = left
    }

    setRight(right) {
        this.right = right
    }

    compareTo(other) {
        if (this.count !== other.count) {
            return this.count - other.count
        }
        if (this.symbol === null && other.symbol !== null) {
            return 1
        }
        if (this.symbol !== null && other.symbol === null) {
            return -1
        }
        if (this.symbol === null && other.symbol === null) {
            return 0
        }
        return this.symbol.charCodeAt(0) - other.symbol.charCodeAt(0)
    }
}

module.exports = {
    TreeNode
}