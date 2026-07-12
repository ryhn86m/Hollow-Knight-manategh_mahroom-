package MyGame.controller.enemyController;

import MyGame.model.entities.Mosquito;
import MyGame.model.enums.MosquitoState;
import MyGame.view.renderers.itemsRenderer.SolidBlock;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class MosquitoController {
    private Mosquito model;
    private Array<SolidBlock> solidBlocks;

    public MosquitoController(Mosquito model, Array<SolidBlock> solidBlocks) {
        this.model = model;
        this.solidBlocks = solidBlocks;
    }

    public void update(float delta, float knightX, float knightY) {
        if (model.isDead()) return;

        model.setStateTime(model.getStateTime() + delta);

        if (model.getInvincibleTimer() > 0) {
            model.setInvincibleTimer(model.getInvincibleTimer() - delta);
        }

        switch (model.getState()) {
            case Idle:
                float moveDir = model.isFacingRight() ? 1 : -1;
                float nextPatrolX = model.getX() + (moveDir * model.getPatrolSpeed() * delta);

                Rectangle patrolBounds = new Rectangle(nextPatrolX, model.getY(), model.getWidth(), model.getHeight());
                boolean hitPatrolWall = false;

                for (SolidBlock block : solidBlocks) {
                    if (patrolBounds.overlaps(block.getBounds())) {
                        hitPatrolWall = true;
                        break;
                    }
                }

                if (hitPatrolWall) {
                    model.setFacingRight(!model.isFacingRight());
                } else {
                    model.setX(nextPatrolX);
                }

                float distToKnight = Vector2.dst(model.getX(), model.getY(), knightX, knightY);
                if (distToKnight <= model.getDetectionRange()) {
                    model.setState(MosquitoState.AttackAnticipate);
                    model.setAnticipateTimer(0.6f);

                    model.setTargetX(knightX);
                    model.setTargetY(knightY);

                    model.setFacingRight(knightX > model.getX());
                    model.setStateTime(0);
                }
                break;

            case AttackAnticipate:
                model.setAnticipateTimer(model.getAnticipateTimer() - delta);
                if (model.getAnticipateTimer() <= 0) {
                    model.setState(MosquitoState.Attack);
                    model.setAttackTimer(1.5f);
                    model.setStateTime(0);

                    Vector2 direction = new Vector2(model.getTargetX() - model.getX(), model.getTargetY() - model.getY()).nor();
                    model.setVelocityX(direction.x * model.getSpeed());
                    model.setVelocityY(direction.y * model.getSpeed());
                }
                break;

            case Attack:
                model.setAttackTimer(model.getAttackTimer() - delta);
                float nextX = model.getX() + (model.getVelocityX() * delta);
                float nextY = model.getY() + (model.getVelocityY() * delta);

                Rectangle nextBounds = new Rectangle(nextX, nextY, model.getWidth(), model.getHeight());
                boolean hitObstacle = false;

                for (SolidBlock block : solidBlocks) {
                    if (nextBounds.overlaps(block.getBounds())) {
                        hitObstacle = true;
                        break;
                    }
                }

                if (hitObstacle || model.getAttackTimer() <= 0) {
                    model.setVelocityX(0);
                    model.setVelocityY(0);
                    model.setState(MosquitoState.Turn);
                    model.setStateTime(0);
                } else {
                    model.setX(nextX);
                    model.setY(nextY);
                }
                break;

            case Turn:
                if (model.getStateTime() > 0.8f) {
                    model.setState(MosquitoState.Idle);
                    model.setStateTime(0);
                }
                break;
        }}
}
