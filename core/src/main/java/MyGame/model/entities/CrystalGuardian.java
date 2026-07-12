package MyGame.model.entities;

import MyGame.Main;
import MyGame.model.enums.CrystallizedState;
import MyGame.model.enums.HuskHornheadState;
import MyGame.model.enums.Sfx;
import MyGame.model.world.GameModel;
import MyGame.view.renderers.enemyRenderer.CrystalGuardianRenderer;
import com.badlogic.gdx.math.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrystalGuardian {
    private CrystallizedState currentState;
    private Laser laser = new Laser();
    private float x, y;
    private float width = 500f, height = 400f;
    private float speedRun = 300f;
    private boolean facingRight = true;
    private float invincibleTimer=0f;
    private int hp;
    private boolean dead;
    private boolean hitKnight = false;
    private float stateTimer=0f;
    private float angryDuration = 2.5f;
    private boolean enraged = false;
    private float angryTimer = 0;
    private float runSpeed = 650f;
    private boolean laserShot = false;
    private boolean invincible = false;
    public CrystalGuardian(float startX, float startY) {
        this.x = startX;
        this.y = startY;
        this.hp=23;
        dead = false;
        this.currentState = CrystallizedState.IDLE;
    }public void update(float delta) {
        stateTimer += delta;
        if (invincibleTimer > 0) {
            invincibleTimer -= delta;
        }
    }    public void changeState(CrystallizedState newState){
        if(currentState != newState){
            currentState = newState;
            stateTimer = 0;
        }
    }

    public void takeDamage(int amount) {
        if (dead) return;
        if (invincibleTimer > 0)
            return;
        hp -= amount;
        invincibleTimer=0.5f;
        if (Main.getMain().isSfx())
            Sfx.EnemyDamage.play();
        if (hp <= 0) {
            die();
        }
        facingRight = !facingRight;

    }
    private void die() {
        dead = true;
        currentState = CrystallizedState.DEATH;
        GameModel.getInstance().addKill();
        if (Main.getMain().isSfx())
            Sfx.EnemyDeath.play();
    }
    public Rectangle getBounds() {
        float hitboxWidth = 260;
        float hitboxHeight = 180;
        float hitboxX = x + 100;
        float hitboxY = y;
        return new Rectangle(hitboxX,hitboxY,hitboxWidth,hitboxHeight);
    }
}
