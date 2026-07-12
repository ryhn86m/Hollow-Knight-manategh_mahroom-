package MyGame.controller.enemyController;

import MyGame.model.entities.Boss;
import MyGame.model.enums.BossState;
import MyGame.view.renderers.itemsRenderer.SolidBlock;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class BossController {
    private Boss boss;
    private final float GRAVITY = -900f;
    private Random random;
    private Array<SolidBlock> solidBlocks;
    private boolean isAwake = false;
    public BossController(Boss boss,Array<SolidBlock> solidBlocks){
        this.boss = boss;
        this.random = new Random();
        this.solidBlocks = solidBlocks;
    }
    public void update(float delta, float knightX, float knightY) {
        delta = Math.min(delta, 0.05f);
        boss.setStateTime(boss.getStateTime() + delta);


        if (boss.isDead()) {
            applyPhysics(delta);
            return;
        }

        if (!isAwake) {
            boolean isCloseHorizontally = Math.abs(boss.getX() - knightX) < 1000f;
            boolean isBelowCeiling = knightY < (boss.getY() + 750f);

            if (isCloseHorizontally && isBelowCeiling) {
                isAwake = true;
            }
        }

        boss.setStateTime(boss.getStateTime() + delta);

        if (boss.getInvincibleTimer() > 0) {
            boss.setInvincibleTimer(boss.getInvincibleTimer() - delta);
        }
        boss.updateHitWindow(delta);
        boolean oldFacing = boss.isFacingRight();
        if (boss.getState() == BossState.Idle) {
            boss.setFacingRight(knightX > boss.getX());
        }

        oldFacing = boss.isFacingRight();
        if (boss.getState() != BossState.RunFast && boss.getState() != BossState.Stun && boss.getState() != BossState.StunDone) {
            boss.setFacingRight(knightX > boss.getX());
        }


        if (oldFacing != boss.isFacingRight()) {
            for (SolidBlock block : solidBlocks) {
                if (boss.getBounds().overlaps(block.getBounds())) {
                    boss.setFacingRight(oldFacing);
                    break;
                }
            }
        }

        applyPhysics(delta);

        if (isAwake) {
            handleStates(delta, knightX);
        } else {
            boss.setVelocityX(0);
        }
    }

    private void applyPhysics(float delta) {
        float oldX = boss.getX();
        float oldY = boss.getY();


        boss.setX(boss.getX() + (boss.getVelocityX() * delta));
        for (SolidBlock block : solidBlocks) {
            if (boss.getBounds().overlaps(block.getBounds())) {


                float bossCenter = boss.getBounds().x + (boss.getBounds().width / 2f);
                float blockCenter = block.getBounds().x + (block.getBounds().width / 2f);

                if ((bossCenter < blockCenter && boss.getVelocityX() > 0) ||
                    (bossCenter > blockCenter && boss.getVelocityX() < 0)) {

                    boss.setX(oldX);
                    boss.setVelocityX(0);
                    if (boss.getState() == BossState.RunFast) {
                        changeState(BossState.Idle);
                        boss.setAiCooldown(boss.isPhase2() ? 0.6f : 1.2f);
                    }
                }
                break;
            }
        }


        float newVelocityY = boss.getVelocityY() + (GRAVITY * delta);
        boss.setVelocityY(Math.max(newVelocityY, -1000f));

        boss.setY(boss.getY() + (boss.getVelocityY() * delta));
        for (SolidBlock block : solidBlocks) {
            if (boss.getBounds().overlaps(block.getBounds())) {

                if (boss.getVelocityY() < 0) {
                    float blockTop = block.getBounds().y + block.getBounds().height;

                    if (oldY >= blockTop - 45f) {
                        boss.setY(blockTop);
                        boss.setVelocityY(0);
                        break;
                    }
                }
                else if (boss.getVelocityY() > 0) {
                    float blockBottom = block.getBounds().y;

                    if (oldY + boss.getHitboxHeight() <= blockBottom + 45f) {
                        boss.setY(oldY);
                        boss.setVelocityY(0);
                        break;
                    }
                }
            }
        }
    }

    private void handleStates(float delta, float knightX) {
        if (boss.getHitCounter() >= 3 && boss.getState() != BossState.Stun && boss.getState() != BossState.StunDone) {
            executeMove(BossState.DefensiveJump, knightX);
            boss.setHitCounter(0);
            boss.setAiCooldown(boss.isPhase2() ? 0.6f : 1.2f);
            return;
        }

        switch (boss.getState()) {
            case Idle:
                boss.setVelocityX(0);
                boss.setAiCooldown(boss.getAiCooldown() - delta);
                boss.setHitKnight(false);
                if (boss.getAiCooldown() <= 0) {
                    decideNextMove(knightX);
                }
                break;

            case RunFast:
                if (Math.abs(boss.getX() - knightX) < 100) {
                    changeState(BossState.Attack);
                    boss.setVelocityX(0);
                }
                break;

            case Attack:
                if (boss.getStateTime() >= 1.6f && boss.getStateTime() < 1.6f + delta) {
                    boss.requestCameraShake(0.6f, 10f);
                }
                if (boss.getStateTime() > 1.6f) {
                    changeState(BossState.Idle);
                    boss.setAiCooldown(boss.isPhase2() ? 0.6f : 1.2f);
                }
                break;


            case Jump:
            case JumpAttack:
            case DefensiveJump:
                if (Math.abs(boss.getVelocityY()) < 1f && boss.getStateTime() > 0.1f) {
                    if (boss.getState() == BossState.JumpAttack) {
                        boss.requestCameraShake(0.8f, 15f);
                    }
                    changeState(BossState.Idle);
                    boss.setAiCooldown(boss.isPhase2() ? 0.4f : 0.8f);
                }
                break;

            case Stun:
                boss.setVelocityX(0);
                boss.setStunTimer(boss.getStunTimer() - delta);
                if (boss.getStunTimer() <= 0) {
                    changeState(BossState.StunDone);
                }
                break;

            case StunDone:
                if (boss.getStateTime() > 1.5f) {
                    changeState(BossState.Idle);
                    boss.setNormalSpeedRun(boss.getNormalSpeedRun() * 1.5f);
                    boss.setChargeSpeedRun(boss.getChargeSpeedRun() * 1.5f);
                }
                break;
        }
    }

    private void decideNextMove(float knightX) {
        float distance = Math.abs(boss.getX() - knightX);
        BossState nextMove;
        int rand = random.nextInt(100);


        if (distance < 150) {
            // فواصل نزدیک
            if (rand < 50) nextMove = BossState.Attack;
            else if (rand < 80) nextMove = BossState.Jump;
            else nextMove = BossState.DefensiveJump;
        }
        else if (distance < 400) {

            nextMove = (rand < 60) ? BossState.RunFast : BossState.Jump;
        }
        else {

            nextMove = BossState.RunFast;
        }


        if (boss.isPhase2() && rand < 35) {
            nextMove = BossState.JumpAttack;
        }


        if (nextMove == boss.getLastAttackState()) {
            if (nextMove == BossState.Attack) nextMove = BossState.Jump;
            else if (nextMove == BossState.RunFast) nextMove = BossState.Jump;
            else if (nextMove == BossState.Jump) nextMove = BossState.Attack;
            else if (nextMove == BossState.JumpAttack) nextMove = BossState.RunFast;
            else if (nextMove == BossState.DefensiveJump) nextMove = BossState.RunFast;
        }

        executeMove(nextMove, knightX);
    }
    private void executeMove(BossState move, float knightX) {
        changeState(move);
        boss.setLastAttackState(move);

        float direction = (knightX > boss.getX()) ? 1 : -1;

        switch (move) {
            case Attack:
                boss.setVelocityX(0);
                break;
            case RunFast:
                boss.setVelocityX(boss.getChargeSpeedRun() * direction);
                break;
            case Jump:
                boss.setVelocityY(600f);
                boss.setVelocityX(boss.getNormalSpeedRun() * direction);
                break;
            case JumpAttack:
                boss.setVelocityY(750f);
                boss.setVelocityX((boss.getNormalSpeedRun() * 1.3f) * direction);
                break;
            case DefensiveJump:
                boss.setVelocityY(500f);
                boss.setVelocityX((boss.getNormalSpeedRun() * 1.5f) * -direction);
                break;
        }
    }

    private void changeState(BossState newState) {
        boss.setLastState(boss.getState());
        boss.setState(newState);
        boss.setStateTime(0);
    }
}
