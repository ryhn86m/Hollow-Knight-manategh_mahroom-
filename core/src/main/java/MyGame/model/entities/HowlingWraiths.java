package MyGame.model.entities;

import com.badlogic.gdx.math.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HowlingWraiths {
    private float x, y;
    private Rectangle bounds;
    private float stateTime = 0f;
    private boolean finished = false;
    private float damageTickTimer = 0f;

    public HowlingWraiths(float knightX, float knightY) {
        updatePosition(knightX, knightY);
        this.bounds = new Rectangle(x, y, 90, 150);
    }

    public void update(float delta, float knightX, float knightY) {
        stateTime += delta;
        damageTickTimer += delta;
        updatePosition(knightX, knightY);
        bounds.setPosition(x, y);

        if (stateTime >= 1.2f) {
            finished = true;
        }
    }

    private void updatePosition(float knightX, float knightY) {
        this.x = knightX - 220;
        this.y = knightY + 15;
    }
}
