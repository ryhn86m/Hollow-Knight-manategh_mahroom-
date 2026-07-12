package MyGame.model.entities;

import MyGame.Main;
import MyGame.controller.menuController.Achievement.AchievementsMenuController;
import MyGame.model.enums.BossState;
import MyGame.model.enums.Sfx;
import MyGame.model.world.GameModel;
import com.badlogic.gdx.math.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Boss {
    private float x, y;
    private float width = 800, height = 600;
    private float velocityX = 0;
    private float velocityY = 0;
    private int hp;
    private final int maxHp=50;
    private BossState state;
    private BossState lastState;
    private BossState lastAttackState;
    private float stateTime = 0;
    private float stunTimer = 0;
    private float aiCooldown = 2f;
    private boolean isPhase2 = false;
    private boolean facingRight = true;
    private float invincibleTimer;
    private int hitCounter = 0;
    private boolean dead;
    private float normalSpeedRun = 130f;
    private float chargeSpeedRun = 500f;
    private boolean hitKnight = false;
    private float stunDuration = 4f;
    private boolean cameraShakeRequested = false;
    private float shakeDuration = 0f;
    private float shakePower = 0f;

    private float hitboxWidth = 200f;
    private float hitboxHeight = 350f;
    private float hitWindowTimer = 0;
    private float hitboxOffsetX = 300f;
    public Boss(float startX, float startY) {
        this.x = startX;
        this.y = startY;
        this.hp = maxHp;
        this.state = BossState.Idle;
        this.lastState = BossState.Idle;
        this.lastAttackState = BossState.Idle;
    }
    public void updateHitWindow(float delta) {
        if (hitWindowTimer > 0) {
            hitWindowTimer -= delta;
            if (hitWindowTimer <= 0) {
                hitCounter = 0;
            }
        } }
    public void requestCameraShake(float duration, float power) {
        this.cameraShakeRequested = true;
        this.shakeDuration = duration;
        this.shakePower = power;
    }

    public void clearCameraShakeRequest() {
        this.cameraShakeRequested = false;
        this.shakeDuration = 0f;
        this.shakePower = 0f;
    }
    public Rectangle getBounds() {
        float currentOffsetX = facingRight ? hitboxOffsetX : (width - hitboxWidth - hitboxOffsetX);

        return new Rectangle(x + currentOffsetX, y, hitboxWidth, hitboxHeight);
    }
    public void update(float delta) {
        if (invincibleTimer > 0) {
            invincibleTimer -= delta;
        }
    }
    public void reset() {
        this.dead = false;
        this.hp = maxHp;
        this.isPhase2 = false;
        this.state = BossState.Idle;
        this.lastState = BossState.Idle;
        this.lastAttackState = BossState.Idle;
        this.stateTime = 0;
        this.stunTimer = 0;
        this.hitCounter = 0;
        this.invincibleTimer = 0;
        this.hitWindowTimer = 0;


        this.normalSpeedRun = 130f;
        this.chargeSpeedRun = 500f;
        this.aiCooldown = 2f;

        this.hitKnight = false;
        this.clearCameraShakeRequest();
    }
    public void takeDamage(int amount) {
        if (dead) return;
        hp -= amount;
        hitCounter++;
        hitWindowTimer = 1.5f;
        invincibleTimer = 0.2f;
        if (Main.getMain().isSfx())
            Sfx.EnemyDamage.play();
        if (!isPhase2 && hp <= maxHp / 2) {
            isPhase2 = true;
            state = BossState.Stun;
            stateTime = 0;
            stunTimer = stunDuration;
            velocityX = 0;
        } else if (hp <= 0) {
            die();
        }
    }
    private void die() {
        dead = true;
        this.state = BossState.Death;
        this.stateTime = 0;
        lastState =BossState.Death;
        GameModel.getInstance().addKill();
        if (!GameModel.getInstance().isHasUsedFocus()) {
            AchievementsMenuController.getInstance().setPureCombatUnlocked(true);
        }
        if(GameModel.getInstance().getEnemiesKilled()>=7){
        AchievementsMenuController.getInstance().setTrueHunterUnlocked(true);}
        AchievementsMenuController.getInstance().setCompletionUnlocked(true);
        AchievementsMenuController.getInstance().setDefeatFalseKnightUnlocked(true);
        if(((int)GameModel.getInstance().getElapsedTime()/60)<=5){
        AchievementsMenuController.getInstance().setSpeedrunUnlocked(true);}
        if (Main.getMain().isSfx())
            Sfx.EnemyDeath.play();
        GameModel.getInstance().setBossDefeated(true);
    }
    public void instaKill() {
        GameModel.getInstance().addKill();
        this.hp = 0;
        this.isPhase2 = true;
        die();
    }
}
