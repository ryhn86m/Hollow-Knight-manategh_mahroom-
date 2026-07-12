package MyGame.view.renderers.itemsRenderer;

import com.badlogic.gdx.math.MathUtils;

public class CrystalParticle {
    public float x;
    public float y;
    public float speed;
    public float time;
    public float randomOffset;
    public float size;

    public CrystalParticle(float x, float y, float speed,float size) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.size = size;
        this.time = 0;
        this.randomOffset = MathUtils.random(0f, 100f);
    }

    public void update(float delta) {
        time += delta;
        y -= speed * delta;
        x += MathUtils.sin(time + randomOffset) * 15f * delta;
    }
}
