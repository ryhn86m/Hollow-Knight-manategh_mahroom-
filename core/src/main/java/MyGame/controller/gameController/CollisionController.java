package MyGame.controller.gameController;
import MyGame.model.entities.*;
import MyGame.model.enums.KnightState;
import MyGame.view.renderers.itemsRenderer.SolidBlock;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class CollisionController {
    public void checkEnemyGroundCollision(HuskHornhead enemy, Array<SolidBlock> blocks) {
        if (enemy.isSpawnLanded()) return;
        float dt = Math.min(Gdx.graphics.getDeltaTime(), 0.05f);

        float gravity = -900f;
        enemy.setVelocityY(Math.max(enemy.getVelocityY() + (gravity * dt), -1000f));
        enemy.setY(enemy.getY() + (enemy.getVelocityY() * dt));

        Rectangle enemyBounds = enemy.getBounds();

        for (SolidBlock block : blocks) {
            if (block.isNotForKnight()) continue;
            Rectangle footSensor = new Rectangle(enemyBounds.x, enemyBounds.y - 15f, enemyBounds.width, 20f);

            if (footSensor.overlaps(block.getBounds())) {
                float blockTop = block.getBounds().y + block.getBounds().height;

                if (enemy.getVelocityY() <= 0) {
                    enemy.setY(blockTop);
                    enemy.setVelocityY(0);
                    enemy.setSpawnLanded(true);
                    break;
                }
            }
        }
    }

    public void checkEnemyGroundCollision(CrystalCrawler enemy, Array<SolidBlock> blocks) {
        if (enemy.isSpawnLanded()) return;
        float dt = Math.min(Gdx.graphics.getDeltaTime(), 0.05f);

        float gravity = -900f;
        enemy.setVelocityY(Math.max(enemy.getVelocityY() + (gravity * dt), -1000f));
        enemy.setY(enemy.getY() + (enemy.getVelocityY() * dt));

        Rectangle enemyBounds = enemy.getBounds();

        for (SolidBlock block : blocks) {
            if (block.isNotForKnight()) continue;
            Rectangle footSensor = new Rectangle(enemyBounds.x, enemyBounds.y - 15f, enemyBounds.width, 20f);

            if (footSensor.overlaps(block.getBounds())) {
                float blockTop = block.getBounds().y + block.getBounds().height;

                if (enemy.getVelocityY() <= 0) {
                    enemy.setY(blockTop);
                    enemy.setVelocityY(0);
                    enemy.setSpawnLanded(true);
                    break;
                }
            }
        }
    }
    public CollisionResult checkMapCollision(Knight knight, Array<SolidBlock> blocks, BreakableWall breakableWall,BreakableWall breakableWall2) {
        CollisionResult result = new CollisionResult();
        Rectangle player = knight.getBounds();

        Rectangle pogoHitbox = null;
        if (knight.getState() == KnightState.ATTACK_DOWN) {
            pogoHitbox = new Rectangle(knight.getX(), knight.getY() - 100f, knight.getHitboxWidth(), 100f);
        }

        for (SolidBlock block : blocks) {
            if(block.isNotForKnight()) continue;

            if (block.isDeadly() && pogoHitbox != null && pogoHitbox.overlaps(block.getBounds())) {
                result.setDeadly(true);
            }
            if (!player.overlaps(block.getBounds())) continue;

            detectGround(knight, block, result);
            detectCeiling(knight, block, result);
            detectWalls(knight, block, result);
            detectDeadly(block, result);
        }
        if (breakableWall != null && !breakableWall.isBroken()) {
            SolidBlock wallBlock = new SolidBlock(breakableWall.getBounds().x, breakableWall.getBounds().y, breakableWall.getBounds().width, breakableWall.getBounds().height, false, false, false);
            if (player.overlaps(wallBlock.getBounds())) {
                detectGround(knight, wallBlock, result);
                detectCeiling(knight, wallBlock, result);
                detectWalls(knight, wallBlock, result);
            }
        }
        if (breakableWall2 != null && !breakableWall2.isBroken()) {
            SolidBlock wallBlock = new SolidBlock(breakableWall2.getBounds().x, breakableWall.getBounds().y, breakableWall2.getBounds().width, breakableWall2.getBounds().height, false, false, false);
            if (player.overlaps(wallBlock.getBounds())) {
                detectGround(knight, wallBlock, result);
                detectCeiling(knight, wallBlock, result);
                detectWalls(knight, wallBlock, result);
            }
        }
        return result;
    }

    private void detectGround(Knight knight, SolidBlock block, CollisionResult result) {
        Rectangle player = knight.getBounds();
        float blockTop = block.getBounds().y + block.getBounds().height;
        boolean falling = knight.getVelocityY() <= 0;
        boolean wasAbove = knight.getPreviousY() >= blockTop;
        if (falling && wasAbove && !block.isCrystal()) {
            knight.setY(blockTop);
            knight.setVelocityY(0);
            result.setGrounded(true);
        }
    }

    private void detectCeiling(Knight knight, SolidBlock block, CollisionResult result) {
        Rectangle player = knight.getBounds();
        boolean movingUp = knight.getVelocityY() > 0;
        float blockBottom = block.getBounds().y;
        float playerTop = player.y + player.height;
        boolean hitFromBelow = knight.getPreviousY() + player.height <= blockBottom;
        if (movingUp && hitFromBelow) {
            knight.setY(blockBottom - player.height);
            knight.setVelocityY(0);
            result.setHitCeiling(true);
        }
    }

    private void detectWalls(Knight knight, SolidBlock block, CollisionResult result) {
        Rectangle player = knight.getBounds();
        float playerRight = player.x + player.width;
        float playerLeft = player.x;
        float blockLeft = block.getBounds().x;
        float blockRight = block.getBounds().x + block.getBounds().width;
        if (knight.getVelocityX() > 0) {
            boolean hitRightWall = knight.getPreviousX() + player.width <= blockLeft;
            if (hitRightWall) {knight.setX(blockLeft - player.width);
                result.setWallRight(true);
            }
        }
        if (knight.getVelocityX() < 0) {
            boolean hitLeftWall = knight.getPreviousX() >= blockRight;
            if (hitLeftWall) {
                knight.setX(blockRight);
                result.setWallLeft(true);
            }
        }
    }

    private void detectDeadly(SolidBlock block, CollisionResult result) {
        if (block.isDeadly()) {
            result.setDeadly(true);
        }
    }
}
