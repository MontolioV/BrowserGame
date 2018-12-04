class Position {
    constructor({x, y}) {
        this.x = x;
        this.y = y;
    }

    calculateOffset({x, y}) {
        return new Position({x: x - this.x, y: y - this.y})
    }
}