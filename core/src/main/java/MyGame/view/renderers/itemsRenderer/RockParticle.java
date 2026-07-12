package MyGame.view.renderers.itemsRenderer;

import com.badlogic.gdx.math.MathUtils;

public class RockParticle {
    public float x, y;
    public float vx, vy;
    public float life, maxLife;
    public float size;

    public RockParticle(float startX, float startY, boolean hitFromLeft) {
        this.x = startX;
        this.y = startY;

        float dirMultiplier = hitFromLeft ? -1f : 1f;


        this.vx = MathUtils.random(50f, 250f) * dirMultiplier;
        this.vy = MathUtils.random(100f, 350f);
        this.maxLife = MathUtils.random(0.3f, 0.6f);
        this.life = maxLife;
        this.size = MathUtils.random(7f, 14f);
    }

    public void update(float delta) {
        vy -= 900f * delta;

        x += vx * delta;
        y += vy * delta;

        life -= delta;
    }

    public boolean isDead() {
        return life <= 0;
    }
}
