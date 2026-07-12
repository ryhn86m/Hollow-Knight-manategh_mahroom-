package MyGame.controller.enemyController;

import MyGame.controller.gameController.CollisionController;
import MyGame.model.world.GameModel;
import MyGame.model.entities.HuskHornhead;
import MyGame.model.entities.Knight;
import MyGame.model.enums.HuskHornheadState;
import MyGame.view.renderers.itemsRenderer.SolidBlock;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class HuskHornheadController {
    private CollisionController collisionController = new CollisionController();
    private HuskHornhead model;
    private Array<SolidBlock> solidBlocks;
    private float turnTimer = 0f;

    public HuskHornheadController(HuskHornhead model, Array<SolidBlock> solidBlocks) {
        this.model = model;
        this.solidBlocks = solidBlocks;
    }

    public void update(float delta, Knight knight ){

        model.update(delta);
        collisionController.checkEnemyGroundCollision(model, solidBlocks);
        if (model.isDead()) {
            model.setCurrentState(HuskHornheadState.DEATH);
            return;
        }
        if (model.getInvincibleTimer() > 0) {
            return;
        }
        if (!model.isSpawnLanded()) {
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
            case TURN:
                turnTimer -= delta;
                if (turnTimer <= 0) {
                    model.setFacingRight(!model.isFacingRight());
                    model.setCurrentState(HuskHornheadState.WALK);
                    model.setStateTimer(model.getWalkDuration());
                }
                return;

            case IDLE:
                if (canSeeKnight(knight)) {
                    model.setCurrentState(HuskHornheadState.ATTACK);
                    break;
                }

                model.setStateTimer(model.getStateTimer() - delta);
                if (model.getStateTimer() <= 0) {
                    model.setCurrentState(HuskHornheadState.WALK);
                    model.setStateTimer(model.getWalkDuration());
                }
                break;

            case WALK:
                if (canSeeKnight(knight)) {
                    model.setCurrentState(HuskHornheadState.ATTACK);
                    model.setHitKnight(false);
                    break;
                }

                move(delta, model.getNormalSpeed());
                if (hitWall || !hasGroundAhead) {
                    model.setCurrentState(HuskHornheadState.TURN);
                    turnTimer = 0.25f;
                    break;
                }

                model.setStateTimer(model.getStateTimer() - delta);
                if (model.getStateTimer() <= 0) {
                    model.setCurrentState(HuskHornheadState.IDLE);
                    model.setStateTimer(model.getRestDuration());
                }
                break;

            case ATTACK:
                move(delta, model.getChargeSpeed());
                if (hitWall || !hasGroundAhead) {
                    model.setCurrentState(HuskHornheadState.TURN);
                    turnTimer = 0.25f;
                }
                break;

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
        float visionWidth = 500f;
        float visionX = model.isFacingRight() ? model.getX() + model.getWidth() : model.getX() - visionWidth;
        Rectangle visionRect = new Rectangle(visionX, model.getY(), visionWidth, model.getHeight());
        return visionRect.overlaps(knight.getBounds());
    }
}
