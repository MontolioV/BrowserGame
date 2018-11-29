class Position {
    constructor({x, y}) {
        this.x = x;
        this.y = y;
    }
}

class GameObject {
    constructor({id, currentPosition, destination, speed}) {
        this.id = id;
        this.currentPosition = new Position(currentPosition);
        this.destination = new Position(destination);
        this.speed = speed;
        this.maxPossibleDistance = speed / 1000 * 16;
        // this.timeOfLastMove = 0;
    }
}

