package MyGame.model.entities;

import MyGame.model.enums.MosquitoState;
import MyGame.model.world.GameModel;
import com.badlogic.gdx.math.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mosquito {
    private MosquitoState state = MosquitoState.Idle;
    private float x, y;
    private float width = 300f, height = 300f;
    private float velocityX = 0, velocityY = 0;
    private int hp = 4;
    private boolean facingRight = true;
    private float stateTime = 0f;
    private float invincibleTimer = 0f;
    private boolean dead = false;

private float patrolSpeed = 120f;
    private float targetX, targetY;
    private float detectionRange = 400f;
    private float speed = 450f;
    private float anticipateTimer = 0f;
    private float attackTimer = 0f;

    public Mosquito(float startX, float startY) {
        this.x = startX;
        this.y = startY;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void takeDamage(int amount) {
        if (dead || invincibleTimer > 0) return;
        hp -= amount;
        invincibleTimer = 0.2f;
        if (hp <= 0) {
            die();
        }
    }

    private void die() {
        dead = true;
        state = MosquitoState.Death;
         GameModel.getInstance().addKill();
    }
}
