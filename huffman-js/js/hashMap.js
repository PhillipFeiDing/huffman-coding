class HashMap {
    constructor() {
        this.object = {}
        this._size = 0
    }
    put(key, val) {
        if (!(key in this.object)) {
            this._size++
        }
        this.object[key] = val
    }
    get(key) {
        return this.object[key]
    }
    containsKey(key) {
        return key in this.object
    }
    keySet() {
        let ret = []
        for (const key in this.object) {
            ret[ret.length] = key
        }
        return ret
    }
    size() {
        return this._size
    }
    remove(key) {
        if (key in this.object) {
            delete this.object[key]
            this._size--
        }
    }
    isEmpty() {
        return this.size === 0
    }
}

module.exports = {
    HashMap
}