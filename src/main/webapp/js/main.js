(() => {
    'use strict';

    const SOCKET = new WebSocket("ws://localhost:8080/BrowserGame/game-socket");
    const ACTION_CANVAS = document.getElementById('actionLayer');
    const ACTION_CONTEXT = ACTION_CANVAS.getContext('2d');
    const CHARACTER_CANVAS = drawCharacter();
    let xClick = 0;
    let yClick = 0;
    let selfId;
    let gameObjects = [];

    SOCKET.onmessage = function (message) {
        let data = message.data;
        if (isNaN(parseInt(data))) {
            let tmpArray = [];
            for (const objFromServer of JSON.parse(data)) {
                let oldObject = gameObjects.find(value => value.id === objFromServer.id);
                if (!oldObject) {
                    tmpArray.push(new GameObject(objFromServer));
                } else {
                    oldObject.update(objFromServer);
                    tmpArray.push(oldObject);
                }
            }

            gameObjects = tmpArray;

            // console.log(JSON.stringify(gameObjects));
        } else {
            selfId = parseInt(message.data);
            console.log(selfId);
        }
    };

    ACTION_CANVAS.onclick = function (ev) {
        let clickPosition = getMousePos(ACTION_CANVAS, ev);
        SOCKET.send(JSON.stringify(clickPosition));
        console.log(JSON.stringify(clickPosition));

        xClick = clickPosition.x;
        yClick = clickPosition.y;
    };

    window.requestAnimationFrame(renderActionLayer);

    window.setInterval(() => {
        // console.time('updatePositioning');
        updatePositioning();
        // console.timeEnd('updatePositioning');
    }, 16);

    function renderActionLayer() {
        ACTION_CONTEXT.clearRect(0, 0, ACTION_CANVAS.width, ACTION_CANVAS.height);

        for (const obj of gameObjects) {
            ACTION_CONTEXT.drawImage(CHARACTER_CANVAS, obj.currentPosition.x, obj.currentPosition.y);
        }

        window.requestAnimationFrame(renderActionLayer);
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

    function updatePositioning() {
        for (const obj of gameObjects) {
            let xOffset;
            let yOffset;
            if (obj.id === selfId) {
                xOffset = xClick - obj.currentPosition.x;
                yOffset = yClick - obj.currentPosition.y;
            } else {
                xOffset = obj.destination.x - obj.currentPosition.x;
                yOffset = obj.destination.y - obj.currentPosition.y;
            }

            if (xOffset === 0 && yOffset === 0) {
                return;
            }


            let destinationDistance = Math.sqrt(Math.pow(xOffset, 2) + Math.pow(yOffset, 2));
            if (destinationDistance < obj.maxPossibleDistance) {
                obj.currentPosition.x += xOffset;
                obj.currentPosition.y += yOffset;
            } else {
                let distanceCoefficient = obj.maxPossibleDistance / destinationDistance;
                obj.currentPosition.x += roundedOffset(xOffset, obj.errorFixOffset.x, distanceCoefficient);
                obj.currentPosition.y += roundedOffset(yOffset, obj.errorFixOffset.y, distanceCoefficient);
            }
        }
    }

    function roundedOffset(offset, errorFixOffset, coefficient) {
        let result = (offset + errorFixOffset / 30) * coefficient;

        //More efficient rendering, but less precise
        // result = Math.round(result);
        return result;
    }
})();