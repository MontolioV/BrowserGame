(() => {
    'use strict';

    const SOCKET = new WebSocket("ws://localhost:8080/BrowserGame/game-socket");
    const ACTION_CANVAS = document.getElementById('actionLayer');
    const ACTION_CONTEXT = ACTION_CANVAS.getContext('2d');
    const CHARACTER_CANVAS = drawCharacter();
    let selfId;
    let selfObject;
    let gameObjects = [];

    SOCKET.onmessage = function (message) {
        let data = message.data;
        let response = JSON.parse(data);
        if (response.hasOwnProperty('type') && response.type === 'INIT') {
            let responseInit = new ResponseInit(response);
            selfId = responseInit.selfId;
            console.log(selfId);
        } else {
            let tmpArray = [];
            for (const objFromServer of response) {
                let oldObject = gameObjects.find(value => value.id === objFromServer.id);
                if (!oldObject) {
                    tmpArray.push(new GameObject(objFromServer));
                } else {
                    oldObject.update(objFromServer);
                    tmpArray.push(oldObject);
                }

            }

            gameObjects = tmpArray;

            if (!selfObject) {
                selfObject = gameObjects.find(value => value.id === selfId);
            }
            console.log(JSON.stringify(gameObjects));
        }
    };

    ACTION_CANVAS.onclick = function (ev) {
        let clickPosition = getMousePos(ACTION_CANVAS, ev);
        let response = new ClientRequest({type: 'MOVE', content: JSON.stringify(clickPosition)});
        SOCKET.send(JSON.stringify(response));

        console.log(JSON.stringify(clickPosition));

        selfObject.destination.x = clickPosition.x;
        selfObject.destination.y = clickPosition.y;
        selfObject.calculateRotation();
    };
    document.onkeypress = function (ev) {
        switch (ev.key) {
            case 'f':
                SOCKET.send(JSON.stringify(new ClientRequest({type: 'FIRE'})));
                console.log(JSON.stringify(new ClientRequest({type: 'FIRE'})));
                break;
        }
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
            ACTION_CONTEXT.save();
            ACTION_CONTEXT.translate(obj.currentPosition.x, obj.currentPosition.y);
            ACTION_CONTEXT.rotate(obj.rotationAngleRadians);
            ACTION_CONTEXT.drawImage(CHARACTER_CANVAS, -10, -10);
            ACTION_CONTEXT.restore();
        }

        window.requestAnimationFrame(renderActionLayer);
    }


    function drawCharacter() {
        let result = document.createElement('canvas');
        result.width = 20;
        result.height = 20;
        let context = result.getContext('2d');
        context.beginPath();
        // context.arc(10, 10, 10, 0, 2 * Math.PI);
        context.moveTo(0, 0);
        context.lineTo(20, 10);
        context.lineTo(0, 20);
        context.lineTo(5, 10);
        context.lineTo(0, 0);
        context.strokeStyle = 'black';
        context.fillStyle = 'green';
        context.stroke();
        context.fill();
        return result;
    }

    function getMousePos(canvas, evt) {
        let rect = canvas.getBoundingClientRect();
        return new Position({
            x: evt.clientX - rect.left,
            y: evt.clientY - rect.top
        });
    }

    function updatePositioning() {
        for (const obj of gameObjects) {
            let xOffset = obj.destination.x - obj.currentPosition.x;
            let yOffset = obj.destination.y - obj.currentPosition.y;

            if (xOffset === 0 && yOffset === 0) {
                continue;
            }

            let destinationDistance = Math.sqrt(Math.pow(xOffset, 2) + Math.pow(yOffset, 2));

            if (obj.errorFixOffsetNotNull()) {
                let errorFixOffsetDistance = Math.sqrt(Math.pow(obj.errorFixOffset.x, 2) + Math.pow(obj.errorFixOffset.y, 2));
                if (destinationDistance < errorFixOffsetDistance) {
                    obj.currentPosition.x += obj.errorFixOffset.x;
                    obj.currentPosition.y += obj.errorFixOffset.y;
                    obj.nullifyErrorFixOffset();
                    continue;
                }
            }

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
        return (offset + errorFixOffset / 3) * coefficient;

        //More efficient rendering, but less precise
        // result = Math.round(result);
    }
})();