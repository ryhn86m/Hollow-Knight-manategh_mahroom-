package MyGame.model.entities;

import MyGame.Main;
import MyGame.model.world.GameModel;
import MyGame.model.enums.KnightState;
import MyGame.model.enums.Sfx;
import MyGame.model.world.Inventory;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class Knight {
    public Knight(float x, float y) {
        this.x = x;
        this.y = y;
        this.inventory = new Inventory();
    }
    private boolean gravityEnabled = true;
    private float stateTime;
    private boolean inFire;
    private float fireCooldown;
    private int hp = 5;
    private boolean dead = false;
    private float deathTimer = 0f;
    private final int MAX_HP = 5;
    private int soul = 0;
    private final int MAX_SOUL = 99;
    private float dashTimer;
    private float attackTimer;
    private float attackDTimer;
    private float attackUTimer;
    private float focusTimer=0f;
    private float x;
    private float y;
    private float previousX;
    private float previousY;
    private float width = 400;
    private float height = 300;
    private float hitboxWidth = 70;
    private float hitboxHeight = 130;
    private float velocityX;
    private float velocityY;
    private boolean isSpawning = true;
    private boolean facingRight = true;
    private boolean isFocusing = false;
    private boolean grounded;
    private boolean dashing;
    private boolean attacking;
    private float invincibleTimer=0;
    private float healthChangeTimer = 0f;
    private boolean touchingWallLeft;
    private boolean touchingWallRight;
    private boolean touchingDeadly;
    private boolean wallSliding;
    private boolean attackConsumed;
    private KnightState state = KnightState.IDLE;
    private int jumpCount = 0;
    private float knockbackTimer = 0f;
    private boolean damageShakeRequested = false;
    private boolean spellShakeRequested = false;
    private boolean dashAvailable = true;
    private Inventory inventory;
    private float dashCooldownTimer = 0f;
    public enum DeathType {
        NORMAL_DEATH,
        HAZARD_DEATH
    }
    public Rectangle getBounds() {
        return new Rectangle(x,y, hitboxWidth, hitboxHeight);}
    public void clearSpellShakeRequest() {
        this.spellShakeRequested = false;
    }
    public void takeDamage(int amount) {
        if (GameModel.getInstance().isGodMode()) return;
        if (invincibleTimer > 0)
            return;
        damageShakeRequested = true;
        hp -= amount;
        if (Main.getMain().isSfx()) {
            Sfx.KnightDamage.play();
        }
        this.healthChangeTimer = 0.5f;
        if (hp < 0)
            hp = 0;
        if (hp == 0) {
            dead = true;
            state = KnightState.DEAD;
            stateTime = 0f;
            hp = 5;
            velocityX = 0;
            velocityY = 0;
            if (Main.getMain().isSfx()) {
                Sfx.KnightDeath.play();
            }
        }
        invincibleTimer = 1f;
        isFocusing = false;
        focusTimer = 0f;
        if(state == KnightState.FOCUS_START) {
            state = KnightState.IDLE;
        }
    }

    public void updateInvincibility(float delta) {

        if (invincibleTimer>0) {
            invincibleTimer -= delta;
        }
        if (fireCooldown > 0)
            fireCooldown -= delta;
        else
            inFire = false;
    }

    public void jump() {
        if (jumpCount >= 2)
            return;
        jumpCount++;
        velocityY = 850f;
        if (jumpCount == 1) state = KnightState.JUMP;
        else state = KnightState.DOUBLE_JUMP;
    }
    public void cutJump(){
        if(velocityY > 0){velocityY *= 0.5f;}
    }
    public void startDash(){
        if(!dashAvailable || dashCooldownTimer > 0) return;

        dashAvailable = false;
        dashing = true;
        state = KnightState.DASH;
        this.stateTime = 0f;
    }

    public void attack(){
        attackConsumed = false;
        attacking = true;
        state = KnightState.ATTACK;
        this.stateTime = 0f;
    }
    public void attackDown(){

        attackConsumed = false;
        attacking = true;
        state = KnightState.ATTACK_DOWN;
        this.stateTime = 0f;
    }
    public void attackUp(){

        attackConsumed = false;
        attacking = true;
        state = KnightState.ATTACK_UP;
        this.stateTime = 0f;
    }
    public void respawn(boolean fullReset, Vector2 spawnPoint) {

        setX(spawnPoint.x);
        setY(spawnPoint.y);

        setVelocityX(0);
        setVelocityY(0);

        setDashTimer(0f);
        setAttackTimer(0f);
        setAttackUTimer(0f);
        setAttackDTimer(0f);
        setFocusTimer(0f);

        setDashing(false);
        setAttacking(false);
        setFocusing(false);

        setState(KnightState.IDLE);

        setKnockbackTimer(0);
        setInvincibleTimer(1.0f);

        if (fullReset) {
            setHp(getMAX_HP());
            setSoul(0);
            GameModel.getInstance().setEnemiesKilled(0);

        }

    }
    public void startFocus() {
        state = KnightState.FOCUS_START;
    }
    public void clearDamageShakeRequest() {
        damageShakeRequested = false;
    }
    public void setState(KnightState newState) {
        if (this.state != newState) {
            this.state = newState;
            this.stateTime = 0f;
        }
    }
    public void castFireball() {
        if (this.soul >= 33 && state != KnightState.FIREBALL && state != KnightState.SCREAM && state != KnightState.DASH) {
            this.soul -= 33;
            this.spellShakeRequested = true;
            this.state = KnightState.FIREBALL;
            this.stateTime = 0f;

             if(Main.getMain().isSfx()) Sfx.FireBallCast.play();

        }
    }

    public void castScream() {
        if (this.soul >= 33 && state != KnightState.FIREBALL && state != KnightState.SCREAM && state != KnightState.DASH) {
            this.soul -= 33;
            this.spellShakeRequested = true;
            this.state = KnightState.SCREAM;
            this.stateTime = 0f;

            if(Main.getMain().isSfx()) Sfx.ScreamCast.play();
        }
    }
}




