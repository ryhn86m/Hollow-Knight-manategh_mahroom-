package MyGame.view.renderers.enemyRenderer;

import MyGame.model.world.AssetManager;
import MyGame.model.entities.CrystalGuardian;
import MyGame.model.entities.Laser;
import MyGame.model.enums.CrystallizedState;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrystalGuardianRenderer {
    private CrystalGuardian model;
    private CrystallizedState lastState;
    private TextureRegion laserStart,laserMiddle,laserEnd;
    private float stateTime = 0f;
    public CrystalGuardianRenderer(CrystalGuardian model){this.model = model;
        this.lastState = model.getCurrentState();
        this.laserStart = AssetManager.getAssetManager().getStartLaser();
        this.laserMiddle = AssetManager.getAssetManager().getMiddleLaser();
        this.laserEnd = AssetManager.getAssetManager().getEndLaser();}
    public void render(SpriteBatch batch , float delta){
        if (lastState != model.getCurrentState()) {
            stateTime = 0;
            lastState = model.getCurrentState();
        }
        stateTime += delta;
        if (model.getInvincibleTimer() > 0) {
            TextureRegion frame = AssetManager.getAssetManager().getEvadeCrystallized().getKeyFrame(stateTime,false);

            boolean flip = model.isFacingRight();
            float drawX = flip ? model.getX() + model.getWidth() : model.getX();
            float drawWidth = flip ? -model.getWidth() : model.getWidth();

            batch.draw(frame, drawX, model.getY(), drawWidth, model.getHeight());
            return;
        }
        Animation<TextureRegion> currentAnimation;
        boolean shouldLoop= true;
        switch (model.getCurrentState()) {
            case TURN:
                currentAnimation = AssetManager.getAssetManager().getTurnCrystallized();
                shouldLoop = false;
                break;
            case DEATH:
                currentAnimation = AssetManager.getAssetManager().getDeathCrystallized();
                shouldLoop  = false;
                break;
            case RUN:
                currentAnimation = AssetManager.getAssetManager().getRunCrystallized();
                shouldLoop = true;
                break;
            case SHOOT:
                currentAnimation = AssetManager.getAssetManager().getShootCrystallized();
                shouldLoop = false;
                break;
            case IDLE:
                currentAnimation = AssetManager.getAssetManager().getIdleCrystallized();
                shouldLoop = true;
                break;
            case EVADE:
                currentAnimation = AssetManager.getAssetManager().getEvadeCrystallized();
                shouldLoop = false;
                break;
            default:
                currentAnimation = AssetManager.getAssetManager().getIdleCrystallized();
                shouldLoop = true;
                break;
        }
        TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, shouldLoop);
        boolean flip = model.isFacingRight();
        float drawX = flip ? model.getX() + model.getWidth() : model.getX();
        float drawWidth = flip ? -model.getWidth() : model.getWidth();
        if(model.getCurrentState()!= CrystallizedState.DEATH)
            batch.draw(currentFrame, drawX, model.getY(), drawWidth, model.getHeight());
        else
            batch.draw(currentFrame, drawX, model.getY(), drawWidth*0.3f, model.getHeight()*0.3f);
        renderLaser(batch, model.getLaser());
    }
    private void renderLaser(SpriteBatch batch, Laser laser){
        if (laser == null || laser.getState() == Laser.State.INACTIVE)
            return;

        float x = laser.getX();
        float y = laser.getY();
        float fullLength = laser.getLength();
        float currentLength = fullLength;

        TextureRegion middleFlip = new TextureRegion(laserMiddle);
        middleFlip.flip(true, false);

        float alpha = 1f;
        if (laser.getState() == Laser.State.CHARGING) {
            alpha = 0.3f;
        } else if (laser.getState() == Laser.State.BUILDING) {
            alpha = laser.getProgress();
        }

        batch.setColor(1, 1, 1, alpha);
        batch.draw(laserStart, x, y, laserStart.getRegionWidth(), laserStart.getRegionHeight());
        float cursor = x + laserStart.getRegionWidth();
        float endX = x + currentLength - laserEnd.getRegionWidth();
        boolean mirror = false;

        while (cursor < endX) {
            TextureRegion region = mirror ? middleFlip : laserMiddle;
            batch.draw(region, cursor, y, laserMiddle.getRegionWidth(), laserMiddle.getRegionHeight());
            cursor += laserMiddle.getRegionWidth();
            mirror = !mirror;
        }

        batch.draw(laserEnd, endX, y, laserEnd.getRegionWidth(), laserEnd.getRegionHeight());

        batch.setColor(1, 1, 1, 1);
    }

}
