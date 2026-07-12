package MyGame.controller.enemyController;

import MyGame.model.world.AssetManager;
import MyGame.model.world.GameModel;
import MyGame.model.entities.*;
import MyGame.model.enums.CrystallizedState;
import MyGame.view.renderers.itemsRenderer.SolidBlock;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class CrystalGuardianController {
    private CrystalGuardian model;
    private Array<SolidBlock> solidBlocks;
    private float turnTimer = 0f;
    private float stateTime=0f;
    public CrystalGuardianController(CrystalGuardian model, Array<SolidBlock> solidBlocks) {
        this.model = model;
        this.solidBlocks = solidBlocks;
    }

    public void update(float delta, Knight knight ){
        stateTime += delta;
        model.update(delta);
        model.getLaser().update(delta);
        Laser laser = model.getLaser();
        if (laser.getState() == Laser.State.ACTIVE) {
            if (laser.getBounds().overlaps(knight.getBounds())) {
                if (knight.getInvincibleTimer() <= 0) {
                    knight.takeDamage(1);
                    knight.setInvincibleTimer(1.0f);
                }
            }
        }
        if (model.isDead()) {
            model.changeState(CrystallizedState.DEATH);
            return;
        }
        if (model.getInvincibleTimer() > 0) {
            return;
        }
        float sensorX = model.isFacingRight()
            ? model.getBounds().x + model.getBounds().width + 2
            : model.getBounds().x - 7;
        Rectangle wallSensor =     new Rectangle(sensorX, model.getY() + 120, 5, 80);
        Rectangle groundSensor = new Rectangle(sensorX, model.getY() - 15f, 5f, 15f);

        boolean hitWall = false;
        boolean hasGroundAhead = false;

        for (SolidBlock block : solidBlocks) {
            if (wallSensor.overlaps(block.getBounds())) {
                hitWall = true;

            }
            if (groundSensor.overlaps(block.getBounds())) {
                hasGroundAhead = true;
            }
        }
        switch (model.getCurrentState()) {
            case IDLE:
                if (canSeeKnight(knight)) {
                    model.changeState(CrystallizedState.SHOOT);
                    model.setLaserShot(false);
                    break;
                }
            break;

            case SHOOT:
                if (!model.isLaserShot()) {
                    model.getLaser().activate(
                        model.getX() + 290,
                        model.getY() + 120,
                        model.isFacingRight()
                    );
                    model.setLaserShot(true);
                }
                if (shootAnimationFinished()) {
                    model.setEnraged(true);
                    model.setAngryTimer(model.getAngryDuration());
                    model.changeState(CrystallizedState.RUN);
                }

                break;
            case RUN:
                model.setAngryTimer(model.getAngryTimer() - delta);
                move(delta, model.getRunSpeed());
                if (hitWall || !hasGroundAhead) {
                    model.setFacingRight(!model.isFacingRight());
                }
                if (model.getAngryTimer() <= 0) {
                    model.setEnraged(false);
                    model.changeState(CrystallizedState.IDLE);
                }

                break;
            case EVADE:

            default:
                break;
        }

    }
    private void move(float delta, float speed) {
        if (model.isFacingRight()) {
            model.setX(model.getX() + (speed * delta));
        } else {
            model.setX(model.getX() - (speed * delta));
        }
    }
    private boolean canSeeKnight(Knight knight) {
        float visionWidth = 800f;
        float visionX = model.isFacingRight() ? model.getX() + model.getWidth() : model.getX() - visionWidth;
        Rectangle visionRect = new Rectangle(visionX, model.getY(), visionWidth, model.getHeight());
        return visionRect.overlaps(knight.getBounds());
    }
    private float shootTimer = 0f;

    private boolean shootAnimationFinished() {
        Animation<TextureRegion> anim = AssetManager.getAssetManager().getShootCrystallized();
        return anim.isAnimationFinished(model.getStateTimer());
    }
}
