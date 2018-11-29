(() => {
    'use strict';

    const SOCKET = new WebSocket("ws://localhost:8080/BrowserGame/game-socket");
    const ACTION_CANVAS = document.getElementById('actionLayer');
    const ACTION_CONTEXT = ACTION_CANVAS.getContext('2d');
    const CHARACTER_CANVAS = drawCharacter();
    let xClick = 0;
    let yClick = 0;
    let selfId;
    let objArray = [];

    SOCKET.onmessage = function (message) {
        let data = message.data;
        if (isNaN(parseInt(data))) {
            let array = [];
            for (const obj of JSON.parse(data)) {
                array.push(new GameObject(obj))
            }

            /*
                        for (const oldObj of objArray) {
                            let newObj = array.find(value => value.id === oldObj.id);
                            console.log(`x ${oldObj.currentPosition.x - newObj.currentPosition.x}, y ${oldObj.currentPosition.y - newObj.currentPosition.y}`);
                        }
            */

            objArray = array;
            // console.log(objArray);
        } else {
            selfId = parseInt(message.data);
            console.log(selfId);
        }
    };

    ACTION_CANVAS.onclick = function (ev) {
        let clickPosition = getMousePos(ACTION_CANVAS, ev);
        SOCKET.send(JSON.stringify(clickPosition));
        // console.log(JSON.stringify(clickPosition));

        xClick = clickPosition.x;
        yClick = clickPosition.y;
    };

    window.setInterval(() => {
        // console.time('gameCycle');
        gameCycle();
        // console.timeEnd('gameCycle');
    }, 15);

    function gameCycle() {
        ACTION_CONTEXT.clearRect(0, 0, ACTION_CANVAS.width, ACTION_CANVAS.height);

        for (const obj of objArray) {
            ACTION_CONTEXT.drawImage(CHARACTER_CANVAS, obj.currentPosition.x, obj.currentPosition.y);
        }
        smoothMove();
    }

    function drawCharacter() {
        let result = document.createElement('canvas');
        result.width = 20;
        result.height = 20;
        let context = result.getContext('2d');
        context.beginPath();
        context.arc(10, 10, 10, 0, 2 * Math.PI);
        context.fillStyle = 'green';
        context.fill();
        return result;
    }

    function getMousePos(canvas, evt) {
        let rect = canvas.getBoundingClientRect();
        return {
            x: evt.clientX - rect.left,
            y: evt.clientY - rect.top
        };
    }

    function smoothMove() {
        for (const obj of objArray) {
            let remainingX;
            let remainingY;
            if (obj.id === selfId) {
                remainingX = xClick - obj.currentPosition.x;
                remainingY = yClick - obj.currentPosition.y;
            } else {
                remainingX = obj.destination.x - obj.currentPosition.x;
                remainingY = obj.destination.y - obj.currentPosition.y;
            }

            if (remainingX === 0 && remainingY === 0) {
                return;
            }

            let destinationDistance = Math.sqrt(Math.pow(remainingX, 2) + Math.pow(remainingY, 2));
            let distanceCoefficient = obj.maxPossibleDistance / destinationDistance;
            obj.currentPosition.x += remainingX * distanceCoefficient;
            obj.currentPosition.y += remainingY * distanceCoefficient;
        }
    }
})();