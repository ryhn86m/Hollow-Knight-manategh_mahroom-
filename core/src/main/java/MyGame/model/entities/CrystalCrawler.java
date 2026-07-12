package MyGame.model.entities;

import MyGame.Main;
import MyGame.model.enums.Sfx;
import MyGame.model.world.GameModel;
import com.badlogic.gdx.math.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrystalCrawler {
    public enum State { WALKING, TURNING, DEAD }
    private State currentState = State.WALKING;
    private float x, y;
    private float width = 130f, height = 90f;
    private float speed = 90f;
    private boolean facingRight = true;
    private float invincibleTimer;
    private int hp;
    private boolean dead;
    private float velocityY = 0;
    private float previousY;
    private boolean spawnLanded = false;
    public CrystalCrawler(float startX, float startY) {
        this.x = startX;
        this.y = startY;
        this.hp=3;
        this.previousY = startY;
        dead = false;
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
        currentState = State.DEAD;
        GameModel.getInstance().addKill();
        if (Main.getMain().isSfx())
            Sfx.EnemyDeath.play();
    }
    public Rectangle getBounds() {
        return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
