package MyGame.view.renderers.enemyRenderer;

import MyGame.model.world.AssetManager;
import MyGame.model.entities.Boss;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BossRenderer {
    private Boss boss;
    private float stateTime = 0f;
   public BossRenderer(Boss boss){
       this.boss = boss;
   }
    public void render(SpriteBatch batch, float delta) {
        stateTime += delta;
        float currentAnimTime = boss.getStateTime();
//        if (boss.getInvincibleTimer() > 0) {
//
//        }
        Animation<TextureRegion> currentAnimation;
        boolean shouldLoop= true;
        switch (boss.getState()) {
            case Idle:
                currentAnimation = AssetManager.getAssetManager().getIdleBoss();
                shouldLoop = true;
                break;
            case Attack:
                currentAnimation =  AssetManager.getAssetManager().getAttackBoss();
                shouldLoop= false;
                break;
            case Run:
                currentAnimation =  AssetManager.getAssetManager().getRunBoss();
                shouldLoop = true;
                break;
            case Jump:
                currentAnimation =  AssetManager.getAssetManager().getJumpBoss();
                shouldLoop = false;
                break;
            case JumpAttack:
                currentAnimation =  AssetManager.getAssetManager().getJumpAttackBoss();
                shouldLoop = false;
                break;
            case Stun:
                currentAnimation =  AssetManager.getAssetManager().getStunBoss();
                shouldLoop= false;
                break;
            case StunDone:
                currentAnimation =AssetManager.getAssetManager().getStunDoneBoss();
                shouldLoop = false;
                break;
            case Death:
                currentAnimation =  AssetManager.getAssetManager().getDeathBoss();
                shouldLoop = false;
                break;
            case RunFast:
                currentAnimation = AssetManager.getAssetManager().getRunBoss();
                currentAnimTime = boss.getStateTime() * 2.5f;
                shouldLoop = true;
                break;
            default:
                currentAnimation =  AssetManager.getAssetManager().getIdleBoss();
        }
        TextureRegion currentFrame = currentAnimation.getKeyFrame(currentAnimTime, shouldLoop);
        boolean flip = boss.isFacingRight();
        float drawX = flip ? boss.getX() + boss.getWidth() : boss.getX();
        float drawWidth = flip ? -boss.getWidth() : boss.getWidth();

            batch.draw(currentFrame, drawX, boss.getY(), drawWidth, boss.getHeight());

    }
}
