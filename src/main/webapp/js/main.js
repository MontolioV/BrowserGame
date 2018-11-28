(() => {
    'use strict';

    const SOCKET = new WebSocket("ws://localhost:8080/BrowserGame/game-socket");
    const ACTION_CANVAS = document.getElementById('actionLayer');
    const ACTION_CONTEXT = ACTION_CANVAS.getContext('2d');
    const CHARACTER_CANVAS = drawCharacter();
    const CHARACTER_SPEED = 50;
    let xPosition = 100;
    let yPosition = 100;
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
            objArray = array;
            console.log(objArray);
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

    window.setInterval(gameCycle, 16);

    function gameCycle() {
        ACTION_CONTEXT.clearRect(0, 0, ACTION_CANVAS.width, ACTION_CANVAS.height);

        for (const obj of objArray) {
            ACTION_CONTEXT.drawImage(CHARACTER_CANVAS, obj.currentPosition.x, obj.currentPosition.y);
        }
        smothMove();
        // rotateXY();
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

    function smothMove() {
        for (const obj of objArray) {
            // obj.currentPosition.x += (xClick - obj.currentPosition.x) / obj.speed;
            // obj.currentPosition.y += (yClick - obj.currentPosition.y) / obj.speed;
        }
        // xPosition += (xClick - xPosition) / CHARACTER_SPEED;
        // yPosition += (yClick - yPosition) / CHARACTER_SPEED;
    }
})();
