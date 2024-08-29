const canvas = document.getElementById('meteorCanvas');
const ctx = canvas.getContext('2d');

canvas.width = window.innerWidth;
canvas.height = window.innerHeight;

// 流星类
class Meteor {
    constructor() {
        this.reset();
    }

    reset() {
        this.x = Math.random() * canvas.width;
        this.y = Math.random() * canvas.height / 2; // 流星从上半部分出现
        this.length = Math.random() * 80 + 20;
        this.angle = Math.random() * Math.PI / 4 + Math.PI / 4; // 流星的角度
        this.speed = Math.random() * 5 + 2;
        this.opacity = 1;
    }

    update() {
        this.x += Math.cos(this.angle) * this.speed;
        this.y += Math.sin(this.angle) * this.speed;
        this.opacity -= 0.02;
        if (this.opacity <= 0) this.reset();
    }

    draw() {
        ctx.strokeStyle = `rgba(255, 255, 255, ${this.opacity})`;
        ctx.lineWidth = 2;
        ctx.beginPath();
        ctx.moveTo(this.x, this.y);
        ctx.lineTo(this.x - Math.cos(this.angle) * this.length, this.y - Math.sin(this.angle) * this.length);
        ctx.stroke();
    }
}

const meteors = [];
for (let i = 0; i < 50; i++) {
    meteors.push(new Meteor());
}

function animate() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    meteors.forEach(meteor => {
        meteor.update();
        meteor.draw();
    });
    requestAnimationFrame(animate);
}

animate();
