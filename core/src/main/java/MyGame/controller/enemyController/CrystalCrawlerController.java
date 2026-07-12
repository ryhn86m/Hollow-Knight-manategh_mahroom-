package MyGame.controller.enemyController;

import MyGame.controller.gameController.CollisionController;
import MyGame.model.world.GameModel;
import MyGame.model.entities.BreakableWall;
import MyGame.model.entities.CrystalCrawler;
import MyGame.view.renderers.itemsRenderer.SolidBlock;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class CrystalCrawlerController {
    private CollisionController collisionController = new CollisionController();
    private CrystalCrawler model;
    private Array<SolidBlock> solidBlocks;
    private float turnTimer = 0f;
    private BreakableWall wall;
    private float turnCooldown = 0f;
    public CrystalCrawlerController(CrystalCrawler model, Array<SolidBlock> solidBlocks,BreakableWall wall) {
        this.model = model;
        this.solidBlocks = solidBlocks;
        this.wall = wall;
    }

    public void update(float delta) {
        model.update(delta);

        collisionController.checkEnemyGroundCollision(model, solidBlocks);
        if (model.isDead()) {
            model.setCurrentState(CrystalCrawler.State.DEAD);
            return;
        }
        if (model.getInvincibleTimer() > 0) {
            return;
        }
        if (!model.isSpawnLanded()) {
            return;
        }
        if (turnCooldown > 0) {
            turnCooldown -= delta;
        }
        if (model.getCurrentState() == CrystalCrawler.State.TURNING) {
            turnTimer -= delta;
            if (turnTimer <= 0) {
                model.setFacingRight(!model.isFacingRight());
                model.setCurrentState(CrystalCrawler.State.WALKING);
            }
            return;
        }
        if (model.isFacingRight()) {
            model.setX(model.getX() + (model.getSpeed() * delta));
        } else {
            model.setX(model.getX() - (model.getSpeed() * delta));
        }
        float sensorX = model.isFacingRight() ? (model.getX() + model.getWidth()) : (model.getX() - 5f);
        Rectangle wallSensor = new Rectangle(sensorX, model.getY()+5f , 5f, model.getHeight() );
        Rectangle groundSensor = new Rectangle(sensorX, model.getY() - 15f, 5f, 15f);

        boolean hitWall = false;
        boolean hasGroundAhead = false;

        for (SolidBlock block : solidBlocks) {
            if (wallSensor.overlaps(block.getBounds())) {
                hitWall = true;
            }
            if (wall != null && !wall.isBroken()) {
                if (wallSensor.overlaps(wall.getBounds())) {
                    hitWall = true;
                }
            }
            if (groundSensor.overlaps(block.getBounds())) {
                hasGroundAhead = true;
            }
        }
        if ((hitWall || !hasGroundAhead) && turnCooldown <= 0) {
            model.setCurrentState(CrystalCrawler.State.TURNING);
            turnTimer = 0.25f;
            turnCooldown = 0.8f;
        }
    }
}
