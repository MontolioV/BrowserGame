class Position {
    constructor({x, y}) {
        this.x = x;
        this.y = y;
    }

    calculateOffset({x, y}) {
        return new Position({x: x - this.x, y: y - this.y})
    }
}

class GameObject {
    constructor({id, currentPosition, destination, speed}) {
        this.id = id;
        this.currentPosition = new Position(currentPosition);
        this.destination = new Position(destination);
        this.errorFixOffset = new Position({x: 0, y: 0});
        this.speed = speed;
        this.maxPossibleDistance = speed / 1000 * 16;
        // this.timeOfLastMove = 0;
    }

    update({currentPosition, destination}) {
        // this.currentPosition = new Position(currentPosition);
        this.destination = new Position(destination);
        this.errorFixOffset = this.currentPosition.calculateOffset(currentPosition);
    }
}

