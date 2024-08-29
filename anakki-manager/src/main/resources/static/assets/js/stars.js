const canvas = document.getElementById('starCanvas');
const ctx = canvas.getContext('2d');

// 获取背景图元素
const background = document.querySelector('.spotlight'); // 替换成你的背景图元素选择器

// 设置 canvas 的尺寸
function resizeCanvas() {
    const backgroundRect = background.getBoundingClientRect();
    canvas.width = backgroundRect.width;
    canvas.height = backgroundRect.height;
}

// 调用函数设置初始尺寸
resizeCanvas();

// 监听窗口调整事件，调整 canvas 尺寸
window.addEventListener('resize', resizeCanvas);

class Star {
    constructor() {
        // 生成位置只在背景的上半部分
        this.x = Math.random() * canvas.width;
        this.y = Math.random() * (canvas.height / 2);
        this.size = Math.random() * 1 + 0.5; // 更小的星星
        this.opacity = Math.random() * 0.7 + 0.3; // 更亮的星星
        this.fadeSpeed = Math.random() * 0.03 + 0.02; // 更快的闪烁速度
    }

    update() {
        this.opacity += this.fadeSpeed;
        if (this.opacity > 1 || this.opacity < 0.3) { // 更大的亮度范围
            this.fadeSpeed = -this.fadeSpeed;
        }
    }

    draw() {
        ctx.fillStyle = `rgba(255, 255, 255, ${this.opacity})`;
        ctx.beginPath();
        ctx.arc(this.x, this.y, this.size, 0, Math.PI * 2);
        ctx.closePath();
        ctx.fill();
    }
}

const stars = [];
for (let i = 0; i < 200; i++) {
    stars.push(new Star());
}

function animate() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    stars.forEach(star => {
        star.update();
        star.draw();
    });
    requestAnimationFrame(animate);
}

animate();
