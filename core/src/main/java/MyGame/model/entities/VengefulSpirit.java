package MyGame.model.entities;

import com.badlogic.gdx.math.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VengefulSpirit {
    private float x, y;
    private boolean facingRight;
    private float speed = 500f;
    private Rectangle bounds;
    private boolean destroyed = false;
    private float stateTime = 0f;

    public VengefulSpirit(float knightX, float knightY, boolean facingRight) {
        this.facingRight = facingRight;
        this.x = facingRight ? knightX + 40 : knightX - 40;
        this.y = knightY + 10;
        this.bounds = new Rectangle(x, y, 60, 40);
    }

    public void update(float delta) {
        stateTime += delta;
        x += facingRight ? (speed * delta) : -(speed * delta);
        bounds.setPosition(x, y);
    }
}
