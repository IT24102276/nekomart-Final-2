class Sakura {
    constructor(canvas) {
        this.canvas = canvas;
        this.ctx = canvas.getContext('2d');
        this.petals = [];
        this.maxPetals = 50;
        this.init();
    }

    init() {
        this.resizeCanvas();
        window.addEventListener('resize', () => this.resizeCanvas());
        this.createPetals();
        this.animate();
    }

    resizeCanvas() {
        this.canvas.width = window.innerWidth;
        this.canvas.height = window.innerHeight;
    }

    createPetals() {
        for (let i = 0; i < this.maxPetals; i++) {
            this.petals.push({
                x: Math.random() * this.canvas.width,
                y: Math.random() * this.canvas.height,
                size: Math.random() * 3 + 2,
                speedX: Math.random() * 2 - 1,
                speedY: Math.random() * 2 + 1,
                rotation: Math.random() * 360,
                rotationSpeed: Math.random() * 2 - 1,
                opacity: Math.random() * 0.5 + 0.5
            });
        }
    }

    drawPetal(petal) {
        this.ctx.save();
        this.ctx.translate(petal.x, petal.y);
        this.ctx.rotate(petal.rotation * Math.PI / 180);
        this.ctx.beginPath();
        this.ctx.fillStyle = `rgba(255, 182, 193, ${petal.opacity})`;
        this.ctx.ellipse(0, 0, petal.size, petal.size * 0.6, 0, 0, Math.PI * 2);
        this.ctx.fill();
        this.ctx.restore();
    }

    updatePetal(petal) {
        petal.x += petal.speedX;
        petal.y += petal.speedY;
        petal.rotation += petal.rotationSpeed;

        if (petal.y > this.canvas.height) {
            petal.y = -10;
            petal.x = Math.random() * this.canvas.width;
        }
        if (petal.x < -10) petal.x = this.canvas.width + 10;
        if (petal.x > this.canvas.width + 10) petal.x = -10;
    }

    animate() {
        this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
        
        this.petals.forEach(petal => {
            this.drawPetal(petal);
            this.updatePetal(petal);
        });

        requestAnimationFrame(() => this.animate());
    }
} 