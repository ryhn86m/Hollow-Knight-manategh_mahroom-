package MyGame.model.entities;

import com.badlogic.gdx.math.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Laser {
    private float x;
    private float y;
    private float length = 1800f;
    private float height = 60f;
    private boolean facingRight;

    private float timer = 0f;
    private float chargeTime = 0.3f;
    private float buildTime = 0.3f;
    private float activeTime = 0.4f;
    public enum State {
        CHARGING,
        BUILDING,
        ACTIVE,
        INACTIVE
    }
    private State state = State.INACTIVE;
    public void activate(float x,float y,boolean facingRight){
        this.x = x;
        this.y = y;
        this.facingRight = facingRight;

        timer = 0f;
        state = State.CHARGING;
    }
    public void update(float delta){
        if(state == State.INACTIVE)
            return;
        timer += delta;
        if (state == State.CHARGING && timer > chargeTime) {
            state = State.BUILDING;
        }
        else if (state == State.BUILDING && timer > chargeTime + buildTime) {
            state = State.ACTIVE;
        }
        else if (state == State.ACTIVE && timer > chargeTime + buildTime + activeTime) {
            state = State.INACTIVE;
        }
    }
    public Rectangle getBounds(){
        if (!canDamage()) return new Rectangle(0, 0, 0, 0);

        if (facingRight)
            return new Rectangle(x, y, length, height);

        return new Rectangle(x - length, y, length, height);

    }
    public float getProgress() {
        if (state == State.CHARGING) {
            return timer / chargeTime;
        }

        if (state == State.BUILDING) {
            return (timer - chargeTime) / buildTime;
        }

        if (state == State.ACTIVE) {
            return 1f;
        }

        return 0f;
    }
    public boolean canDamage() {
        return state == State.ACTIVE;
    }
}
