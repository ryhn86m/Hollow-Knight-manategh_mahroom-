package MyGame.controller.gameController;

import MyGame.model.world.GameModel;
import MyGame.model.entities.Knight;
import MyGame.model.enums.KnightState;

public class PhysicsEngineController {
        private static final float GRAVITY = -1400f;
        private static final float WALL_SLIDE_SPEED = -200f;
        private static final float DASH_SPEED = 700f;
        public void update(Knight knight, float delta) {
            if (GameModel.getInstance().isNoclipMode()) {
                knight.setX(knight.getX() + knight.getVelocityX() * delta);
                knight.setY(knight.getY() + knight.getVelocityY() * delta);
                return;
            }
            knight.setPreviousX(knight.getX());
            knight.setPreviousY(knight.getY());
            delta = Math.min(delta, 0.05f);
            if (knight.isDashing()) {
                knight.setVelocityY(0);
                knight.setVelocityX(knight.isFacingRight() ? DASH_SPEED : -DASH_SPEED);
            } else if (knight.isWallSliding()) {
                if (knight.getVelocityY() < WALL_SLIDE_SPEED) {
                    knight.setVelocityY(WALL_SLIDE_SPEED);
                }
            } else {
                knight.setVelocityY(knight.getVelocityY() + GRAVITY * delta);
            }
            knight.setY(knight.getY() + knight.getVelocityY() * delta);
            knight.setX(knight.getX() + knight.getVelocityX() * delta);
            if (!knight.isGrounded() && !knight.isDashing()    && knight.getState() != KnightState.ATTACK_DOWN && !knight.isWallSliding()) {
                if (knight.getVelocityY() > 0) {
                    knight.setState(knight.getJumpCount() <= 1 ? KnightState.JUMP : KnightState.DOUBLE_JUMP);
                } else {
                    knight.setState(KnightState.FALL);
                }
            }

        }
    }

