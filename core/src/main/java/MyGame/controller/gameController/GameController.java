package MyGame.controller.gameController;

import MyGame.Main;
import MyGame.model.world.AssetManager;
import MyGame.model.world.GameModel;
import MyGame.model.entities.BreakableWall;
import MyGame.model.entities.HowlingWraiths;
import MyGame.model.entities.Knight;
import MyGame.model.entities.VengefulSpirit;
import MyGame.model.enums.CharmType;
import MyGame.model.enums.KnightState;
import MyGame.model.enums.Sfx;
import MyGame.model.world.Inventory;
import MyGame.view.renderers.environmentsRender.EnvironmentRenderer;
import MyGame.view.renderers.itemsRenderer.RockParticle;
import MyGame.view.renderers.itemsRenderer.SolidBlock;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameController {
        private Knight knight;
        private PhysicsEngineController physics;
        private CollisionController collisionController;
        private Array<SolidBlock> solidBlocks;
    private Array<RockParticle> rockParticles = new Array<>();
        private CombatController combatController;
    private BreakableWall breakableWall;
    private BreakableWall breakableWall2;
    private Vector2 spawnPoint;
        public GameController(Knight knight, Array<SolidBlock> solidBlocks, EnvironmentRenderer currentMapRenderer,BreakableWall breakableWall,BreakableWall breakableWall2){
            this.knight = knight;
            this.solidBlocks = solidBlocks;
            this.physics = new PhysicsEngineController();
            this.combatController = new CombatController();
            this.collisionController = new CollisionController();
            this.breakableWall = breakableWall;
            this.breakableWall2 = breakableWall2;
            spawnPoint = currentMapRenderer.getPlayerSpawnPoint();
        }
    private boolean respawnRequested = false;

    public void requestRespawn() {
        respawnRequested = true;
    }

    public void clearRespawnRequest() {
        respawnRequested = false;
    }
    public void updateDash(float delta) {
        if (knight.getDashCooldownTimer() > 0) {
            knight.setDashCooldownTimer(knight.getDashCooldownTimer() - delta);
        }
        if (knight.getState() == KnightState.DASH) {
            knight.setDashTimer( knight.getDashTimer() + delta);
            if (knight.getDashTimer() > 0.5f) {
                knight.setState(KnightState.IDLE);
                knight.setDashing(false);
                knight.setDashTimer(0f);
                float cooldown = Inventory.getInstance().isCharmEquipped(CharmType.DASHMASTER) ? 0.1f : 1f;
                knight.setDashCooldownTimer(cooldown);
            }
        }
    }
    public void updateAttack(float delta) {
        if (!knight.isAttacking())
            return;

        if (knight.getState() != KnightState.ATTACK &&
            knight.getState() != KnightState.ATTACK_DOWN &&
            knight.getState() != KnightState.ATTACK_UP) {

            knight.setAttacking(false);
            knight.setAttackTimer(0f);
            return;
        }

        knight.setAttackTimer(knight.getAttackTimer() + delta);
        float attackDuration = Inventory.getInstance().isCharmEquipped(CharmType.QUICK_SLASH) ? 0.2f : 0.8f;
        if (knight.getAttackTimer() > attackDuration) {
            knight.setAttacking(false);
            knight.setAttackTimer(0f);

            if (knight.isGrounded()) {
                knight.setState(KnightState.IDLE);
            } else {
                if (knight.getVelocityY() < 0) {
                    knight.setState(KnightState.FALL);
                } else {
                    knight.setState(KnightState.JUMP);
                }
            }
        }
    }
        public void update(float delta){
            if (knight.isDead()) {
                knight.setDeathTimer(knight.getDeathTimer() + delta);

                Animation<TextureRegion> death =
                    AssetManager.getAssetManager().getDeathAnimation();

                if (death.isAnimationFinished(knight.getStateTime())) {
                    knight.setDead(false);
                    requestRespawn();
                    GameModel.getInstance().addDeath();
                    knight.setState(KnightState.IDLE);


                }

                return;
            }
            if (GameModel.getInstance().isNoclipMode()) {
                handleNoclipMovement(delta);
            }
            else{
            knight.updateInvincibility(delta);
            if (knight.getKnockbackTimer() > 0) {
                knight.setKnockbackTimer(knight.getKnockbackTimer() - delta);
            }
            if (knight.getInvincibleTimer() > 0) {
                knight.setInvincibleTimer(knight.getInvincibleTimer() - delta);
            }
            handleFocus(delta);
           handleMovement();
           updateDash(delta);
            updateSpells(delta);
            updateMagicLocks();
           updateAttack(delta);
            boolean wallHit = combatController.handleWallCombat(knight, breakableWall);
            boolean wallHit2 = combatController.handleWallCombat(knight, breakableWall2);
            if (wallHit) {
                float hitX = knight.isFacingRight() ? breakableWall.getBounds().x : breakableWall.getBounds().x + breakableWall.getBounds().width;
                float hitY = knight.getY() + (knight.getHeight());
                for (int i = 0; i < 20; i++) {
                    rockParticles.add(new RockParticle(hitX, hitY, knight.isFacingRight()));
                }
            }
            for (int i = rockParticles.size - 1; i >= 0; i--) {
                RockParticle p = rockParticles.get(i);
                p.update(delta);
                if (p.isDead()) {
                    rockParticles.removeIndex(i);
                }
            }
            if (wallHit2) {
                float hitX = knight.isFacingRight() ? breakableWall2.getBounds().x : breakableWall2.getBounds().x + breakableWall2.getBounds().width;
                float hitY = knight.getY() + (knight.getHeight());
                for (int i = 0; i < 20; i++) {
                    rockParticles.add(new RockParticle(hitX, hitY, knight.isFacingRight()));
                }
            }
            for (int i = rockParticles.size - 1; i >= 0; i--) {
                RockParticle p = rockParticles.get(i);
                p.update(delta);
                if (p.isDead()) {
                    rockParticles.removeIndex(i);
                }
            }
            physics.update(knight, delta);
            CollisionResult result = collisionController.checkMapCollision(knight, solidBlocks,breakableWall,breakableWall2);
            applyCollisionResult(result);
        }}
    private void handleNoclipMovement(float delta) {

        float noclipSpeed = 1200f;
        float velX = 0;
        float velY = 0;


        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) velX = -noclipSpeed;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) velX = noclipSpeed;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) velY = noclipSpeed;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) velY = -noclipSpeed;


        if (velX > 0) knight.setFacingRight(true);
        if (velX < 0) knight.setFacingRight(false);


        knight.setState(KnightState.IDLE);


        knight.setX(knight.getX() + velX * delta);
        knight.setY(knight.getY() + velY * delta);
    }
    private void handleFocus(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.A) && knight.isGrounded()) {

            knight.setFocusing(true);
            knight.setState(KnightState.FOCUS_START);

            if (knight.getSoul() >= 33 && knight.getHp() < knight.getMAX_HP()) {
                knight.setFocusTimer(knight.getFocusTimer() + delta);
                float requiredFocusTime = Inventory.getInstance().isCharmEquipped(CharmType.QUICK_FOCUS) ? 0.6f : 1.5f;
                if (knight.getFocusTimer() >= requiredFocusTime) {
                    knight.setHp(knight.getHp() + 1);
                    knight.setSoul(knight.getSoul() - 33);
                    GameModel.getInstance().setHasUsedFocus(true);
                    if (Main.getMain().isSfx())
                        Sfx.SoulGain2.play();
                    knight.setFocusTimer(0f);
                }
            } else {
                knight.setFocusTimer(0f);
            }

        } else {
            knight.setFocusing(false);
            knight.setFocusTimer(0f);
            if (knight.getState() == KnightState.FOCUS_START) {
                knight.setState(KnightState.IDLE);
            }
        }
    }
        private void handleMovement(){
        if (knight.getKnockbackTimer() > 0) {
            return;
        }
            if (knight.getState() == KnightState.FIREBALL || knight.getState() == KnightState.SCREAM) {
                knight.setVelocityX(0);
                return;
            }
        if (knight.isFocusing() || knight.getState() == KnightState.DASH || knight.isAttacking()) {
            if (knight.getState() == KnightState.DASH) {
                float dashSpeed = Inventory.getInstance().isCharmEquipped(CharmType.SHARP_SHADOW) ? 850f : 700f;
                knight.setVelocityX(knight.isFacingRight() ? dashSpeed : -dashSpeed);
            }
            else{
                knight.setVelocityX(0);
            }
            return;
        }

        float speed = 300;
        knight.setVelocityX(0);
        boolean pressingLeft = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean pressingRight = Gdx.input.isKeyPressed(Input.Keys.RIGHT);

        if (pressingLeft){
            knight.setVelocityX(-speed);
            knight.setFacingRight(false);
            if (knight.isGrounded())
                knight.setState(KnightState.RUN);
        }
        else if (pressingRight){
            knight.setVelocityX(speed);
            knight.setFacingRight(true);
            if (knight.isGrounded())
                knight.setState(KnightState.RUN);
        }
        else if (knight.getVelocityX() == 0 && knight.isGrounded() && !knight.isAttacking()) {
            knight.setState(KnightState.IDLE);
        }

        Rectangle bounds = knight.getBounds();
        Rectangle leftCheck = new Rectangle(bounds.x - 2, bounds.y, bounds.width, bounds.height);
        Rectangle rightCheck = new Rectangle(bounds.x + 2, bounds.y, bounds.width, bounds.height);

        boolean realWallLeft = false;
        boolean realWallRight = false;

        for (SolidBlock block : solidBlocks) {
            if (block.getBounds().getHeight() > 300f) {
            if (leftCheck.overlaps(block.getBounds())) realWallLeft = true;
            if (rightCheck.overlaps(block.getBounds())) realWallRight = true; }
        }

        if (!knight.isGrounded() && knight.getVelocityY() < 0) {

            if (realWallLeft && pressingLeft) {
                knight.setWallSliding(true);
                knight.setFacingRight(true);

                if (knight.getVelocityY() < -400f) {
                    knight.setVelocityY(-400f);
                }
                if (!knight.isAttacking()) {
                    knight.setState(KnightState.WALL_SLIDE);
                }

            } else if (realWallRight && pressingRight) {
                knight.setWallSliding(true);
                knight.setFacingRight(false);

                if (knight.getVelocityY() < -400f) {
                    knight.setVelocityY(-400f);
                }
                if (!knight.isAttacking()) {
                    knight.setState(KnightState.WALL_SLIDE);
                }

            } else {
                knight.setWallSliding(false);
                if (!knight.isAttacking() && knight.getState() != KnightState.DASH) {
                    knight.setState(KnightState.FALL);
                }
            }
        } else {
            knight.setWallSliding(false);
        }
    }
    private void applyCollisionResult(CollisionResult result) {
        if (result.isDeadly()) {
            if (knight.getState() == KnightState.ATTACK_DOWN) {
                knight.setVelocityY(850f);
                knight.setDashAvailable(true);
                knight.setJumpCount(1);
                knight.setGrounded(false);
                knight.setAttacking(false);
                knight.setAttackTimer(0);
                knight.setState(KnightState.JUMP);

            } else if (knight.getInvincibleTimer() <= 0) {

                knight.takeDamage(1);
                if (!knight.isDead()) {
                    knight.respawn(false, spawnPoint);
                    knight.setState(KnightState.IDLE);
                    knight.setVelocityX(0);
                    knight.setVelocityY(0);
                }
            }
        }
        else {
            knight.setGrounded(result.isGrounded());

            if (result.isGrounded()) {
                knight.setJumpCount(0);
                knight.setDashAvailable(true);

            }
        }

        knight.setTouchingWallLeft(result.isWallLeft());
        knight.setTouchingWallRight(result.isWallRight());
        knight.setWallSliding(!result.isGrounded() && (result.isWallLeft() || result.isWallRight()));
    }
    private void updateSpells(float delta) {
        java.util.Iterator<VengefulSpirit> fbIter = GameModel.getInstance().getFireballs().iterator();
        while (fbIter.hasNext()) {
            VengefulSpirit fb = fbIter.next();
            fb.update(delta);
            boolean hitWall = false;

            for (SolidBlock block : solidBlocks) {
                if (fb.getBounds().overlaps(block.getBounds())) {
                    hitWall = true;
                    break;
                }
            }

            if (!hitWall && breakableWall != null && !breakableWall.isBroken() && fb.getBounds().overlaps(breakableWall.getBounds())) {
                hitWall = true;
            }

            if (hitWall || Math.abs(fb.getX() - knight.getX()) > 1500) {
                fbIter.remove();
            }
        }




        java.util.Iterator<HowlingWraiths> scIter = GameModel.getInstance(knight).getScreams().iterator();
        while (scIter.hasNext()) {
            HowlingWraiths scream = scIter.next();
            scream.update(delta, knight.getX(), knight.getY());

            if (scream.isFinished()) {
                scIter.remove();
            }
        }
    }
    private void updateMagicLocks() {
        if (knight.getState() == KnightState.FIREBALL && knight.getStateTime() >= 0.8f) {
            knight.setState(KnightState.IDLE);
        }

        if (knight.getState() == KnightState.SCREAM && knight.getStateTime() >= 1.2f) {
            knight.setState(KnightState.IDLE);
        }
    }

    }

