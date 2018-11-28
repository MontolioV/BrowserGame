class Position {
    constructor({x, y}) {
        this.x = x;
        this.y = y;
    }
}

class GameObject {
    constructor({currentPosition, destination, speed}) {
        this.currentPosition = new Position(currentPosition);
        this.destination = new Position(destination);
        this.speed = speed;
    }
}

