package MyGame.model.entities;

import MyGame.Main;
import MyGame.model.world.AssetManager;
import MyGame.model.enums.Sfx;
import com.badlogic.gdx.math.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BreakableWall {
    private Rectangle bounds;
    private int hp = 3;
    private boolean broken = false;
    private float stateTime = 0f;
    private boolean breaking = false;
    private boolean animationFinished = false;
    public BreakableWall(float x, float y, float width, float height) {
        this.bounds = new Rectangle(x, y, width, height);
    }
    public void takeDamage() {
        if (breaking || broken) return;

        hp--;

        if (Main.getMain().isSfx()) Sfx.Wall.play();

        if (hp <= 0) {
            startBreaking();
        }
    } private void startBreaking() {
        breaking = true;
        stateTime = 0f;

        if (Main.getMain().isSfx()) Sfx.BreakWall.play();
    }
    public void update(float delta) {
        if (breaking && !broken) {
            stateTime += delta;

            if (AssetManager.getAssetManager()
                .getBreakWall()
                .isAnimationFinished(stateTime)) {

                broken = true;
            }
        }
    }
    public void reset() {
        hp = 3;
        breaking = false;
        broken = false;
        stateTime = 0f;
    }
}
