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

    setLeft(newLeft) {
        this.left = newLeft
    }

    setRight(newRight) {
        this.right = newRight
    }
}