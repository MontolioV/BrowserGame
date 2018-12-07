'use strict';

class GameObject {
    constructor({id, currentPosition, destination, speed}) {
        this.id = id;
        this.currentPosition = new Position(currentPosition);
        this.destination = new Position(destination);
        this.errorFixOffset = new Position({x: 0, y: 0});
        this.speed = speed;
        this.maxPossibleDistance = speed / 1000 * 16;
        this.rotationAngleRadians = 0;
        this.calculateRotation();
    }

    update({currentPosition, destination}) {
        this.destination = new Position(destination);
        this.errorFixOffset = this.currentPosition.calculateOffset(currentPosition);
        this.calculateRotation();
    }

    nullifyErrorFixOffset() {
        this.errorFixOffset.x = 0;
        this.errorFixOffset.y = 0;
    }

    errorFixOffsetNotNull() {
        return this.errorFixOffset.x !== 0 && this.errorFixOffset.y !== 0;
    }

    calculateRotation() {
        let y = this.destination.y - this.currentPosition.y;
        let x = this.destination.x - this.currentPosition.x;
        if (x !== 0 && y !== 0) {
            this.rotationAngleRadians = Math.atan2(y, x);
        }
    }
}