package MyGame.model.entities;

import MyGame.Main;
import MyGame.model.enums.HuskHornheadState;
import MyGame.model.enums.Sfx;
import MyGame.model.world.GameModel;
import com.badlogic.gdx.math.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HuskHornhead {
    private HuskHornheadState currentState;
    private float x, y;
    private float width = 400f, height = 400f;
    private float speed = 150f;
    private boolean facingRight = true;
    private float invincibleTimer;
    private int hp;
    private boolean dead;
    private float normalSpeed = 130f;
    private float chargeSpeed = 500f;
    private boolean hitKnight = false;
    private float stateTimer = 3f;
    private float walkDuration = 4f;
    private float restDuration = 2f;
    private float velocityY = 0;
    private float previousY;
    private boolean spawnLanded = false;

    public HuskHornhead(float startX, float startY) {
        this.x = startX;
        this.y = startY;
        this.hp=10;
        dead = false;
        this.previousY = startY;
        this.stateTimer = walkDuration;
        this.currentState = HuskHornheadState.WALK;
    }public void update(float delta) {
        previousY = y;
        if (invincibleTimer > 0) {
            invincibleTimer -= delta;
        }
    }

    public void takeDamage(int amount) {
        if (dead) return;
        hp -= amount;
        if (Main.getMain().isSfx())
            Sfx.EnemyDamage.play();
        if (hp <= 0) {
            die();
        }
    }
    private void die() {
        dead = true;
        currentState = HuskHornheadState.DEATH;
        GameModel.getInstance().addKill();
        if (Main.getMain().isSfx())
            Sfx.EnemyDeath.play();
    }
    public Rectangle getBounds() {
        return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight()/2.2f);
    }
}
