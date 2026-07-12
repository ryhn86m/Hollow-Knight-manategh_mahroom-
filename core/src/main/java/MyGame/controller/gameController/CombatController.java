package MyGame.controller.gameController;

import MyGame.controller.enemyController.ZoteController;
import MyGame.model.world.GameModel;
import MyGame.model.entities.*;
import MyGame.model.enums.CharmType;
import MyGame.model.enums.KnightState;
import MyGame.model.enums.ZoteState;
import MyGame.model.world.Inventory;
import com.badlogic.gdx.math.Rectangle;

public class CombatController {
    private SoulController soulController = new SoulController();
    public void handleCombatMosquito(Knight knight, Mosquito mosquito) {
        if (mosquito.isDead()) return;

        boolean pogoSuccessful = false;

        if (knight.isAttacking()) {
            Rectangle attackHitbox = getAttackHitbox(knight);
            if (attackHitbox.overlaps(mosquito.getBounds())) {

                if (knight.getState() == KnightState.ATTACK_DOWN) {
                    pogoSuccessful = true;
                }

                if (!knight.isAttackConsumed()) {
                    knight.setAttackConsumed(true);

                    if (pogoSuccessful) {
                        knight.setVelocityY(850f);
                        knight.setDashAvailable(true);
                        knight.setJumpCount(1);
                    }

                    if (mosquito.getInvincibleTimer() <= 0) {
                        int damage = Inventory.getInstance().isCharmEquipped(CharmType.UNBREAKABLE_STRENGTH) ? 2 : 1;
                        mosquito.takeDamage(damage);
                        mosquito.setInvincibleTimer(1f);
                        float knockbackDir = knight.isFacingRight() ? 1 : -1;
                        float kbForce = Inventory.getInstance().isCharmEquipped(CharmType.HEAVY_BLOW) ? 150f : 80f;
                        mosquito.setX(mosquito.getX() + (knockbackDir * kbForce));

                        soulController.addSoulFromAttack(knight);
                    }
                }
            }
        }
        if (!pogoSuccessful && knight.getBounds().overlaps(mosquito.getBounds())) {
            boolean hasSharpShadow = Inventory.getInstance().isCharmEquipped(CharmType.SHARP_SHADOW);
            if (knight.getState() == KnightState.DASH && hasSharpShadow) {
                knight.setInvincibleTimer(0.3f);
                if (mosquito.getInvincibleTimer() <= 0) {
                    int damage = Inventory.getInstance().isCharmEquipped(CharmType.UNBREAKABLE_STRENGTH) ? 2 : 1;
                    mosquito.takeDamage(damage);
                    mosquito.setInvincibleTimer(1f);
                }
            } else {
                if (knight.getInvincibleTimer() <= 0) {
                    knight.takeDamage(1);
                    knight.setKnockbackTimer(0.3f);
                    knight.setInvincibleTimer(1.5f);
                    float knockbackDir = knight.getX() < mosquito.getX() ? -1 : 1;
                    knight.setVelocityX(knockbackDir * 400);
                }
            }
        }
    }
    public void handleCombat(Knight knight, CrystalCrawler crawler) {
        if (crawler.isDead()) return;

        boolean pogoSuccessful = false;

        if (knight.isAttacking()) {
            Rectangle attackHitbox = getAttackHitbox(knight);
            if (attackHitbox.overlaps(crawler.getBounds())) {

                if (knight.getState() == KnightState.ATTACK_DOWN) {
                    pogoSuccessful = true;
                }

                if (!knight.isAttackConsumed()) {
                    knight.setAttackConsumed(true);

                    if (pogoSuccessful) {
                        knight.setVelocityY(850f);
                        knight.setDashAvailable(true);
                        knight.setJumpCount(1);
                    }

                    if (crawler.getInvincibleTimer() <= 0) {
                        int damage = Inventory.getInstance().isCharmEquipped(CharmType.UNBREAKABLE_STRENGTH) ? 2 : 1;
                        crawler.takeDamage(damage);
                        crawler.setInvincibleTimer(1f);
                        float knockbackDir = knight.isFacingRight() ? 1 : -1;
                        float kbForce = Inventory.getInstance().isCharmEquipped(CharmType.HEAVY_BLOW) ? 170f : 90f;
                        crawler.setX(crawler.getX() + (knockbackDir * kbForce));
                        soulController.addSoulFromAttack(knight);
                    }
                }
            }
        }


        if (!pogoSuccessful && knight.getBounds().overlaps(crawler.getBounds())) {
            boolean hasSharpShadow = Inventory.getInstance().isCharmEquipped(CharmType.SHARP_SHADOW);
            if (knight.getState() == KnightState.DASH && hasSharpShadow) {
                knight.setInvincibleTimer(0.3f);
                if (crawler.getInvincibleTimer() <= 0) {
                    int damage = Inventory.getInstance().isCharmEquipped(CharmType.UNBREAKABLE_STRENGTH) ? 2 : 1;
                    crawler.takeDamage(damage);
                    crawler.setInvincibleTimer(1f);
                }
            }
            else {
            if (knight.getInvincibleTimer() <= 0) {
                knight.takeDamage(1);
                knight.setKnockbackTimer(0.15f);
                float knockbackDir = knight.getX() < crawler.getX() ? -1 : 1;
                knight.setVelocityX(knockbackDir * 400);
            } }
        }
    }


    private Rectangle getAttackHitbox(Knight knight) {
        if (knight.getState() == KnightState.ATTACK_DOWN) {
            return new Rectangle(knight.getX(), knight.getY() - 40f, knight.getHitboxWidth(), 40f);
        }

        float attackRange = 80f;
        float x = knight.isFacingRight() ? knight.getX() + knight.getHitboxWidth() : knight.getX() - attackRange;
        float y = knight.getY();
        return new Rectangle(x, y, attackRange, knight.getHitboxHeight());
    }


    public void handleCombatHusk(Knight knight, HuskHornhead husk) {
        if (husk.isDead()) return;

        boolean pogoSuccessful = false;

        if (knight.isAttacking()) {
            Rectangle attackHitbox = getAttackHitbox(knight);
            if (attackHitbox.overlaps(husk.getBounds())) {

                if (knight.getState() == KnightState.ATTACK_DOWN) {
                    pogoSuccessful = true;
                }

                if (!knight.isAttackConsumed()) {
                    knight.setAttackConsumed(true);

                    if (pogoSuccessful) {
                        knight.setVelocityY(850f);
                        knight.setDashAvailable(true);
                        knight.setJumpCount(1);
                    }

                    if (husk.getInvincibleTimer() <= 0) {
                        int damage = Inventory.getInstance().isCharmEquipped(CharmType.UNBREAKABLE_STRENGTH) ? 2 : 1;
                        husk.takeDamage(damage);
                        husk.setInvincibleTimer(1f);
                        float knockbackDir = knight.isFacingRight() ? 1 : -1;
                        float kbForce = Inventory.getInstance().isCharmEquipped(CharmType.HEAVY_BLOW) ? 150f : 90f;
                        husk.setX(husk.getX() + (knockbackDir * kbForce));
                        soulController.addSoulFromAttack(knight);
                    }
                }
            }
        }

        if (!pogoSuccessful && knight.getBounds().overlaps(husk.getBounds()) && !husk.isHitKnight()) {
            boolean hasSharpShadow = Inventory.getInstance().isCharmEquipped(CharmType.SHARP_SHADOW);
            if (knight.getState() == KnightState.DASH && hasSharpShadow) {
                knight.setInvincibleTimer(0.3f);
                if (husk.getInvincibleTimer() <= 0) {
                    int damage = Inventory.getInstance().isCharmEquipped(CharmType.UNBREAKABLE_STRENGTH) ? 2 : 1;
                    husk.takeDamage(damage);
                    husk.setInvincibleTimer(1f);
                }
            }
            else {
            if (knight.getInvincibleTimer() <= 0) {
                knight.takeDamage(1);
                husk.setHitKnight(true);
                knight.setKnockbackTimer(0.3f);
                float knockbackDir = knight.getX() < husk.getX() ? -1 : 1;
                knight.setVelocityX(knockbackDir * 400);
            }
        }
        }
    }

    public boolean handleWallCombat(Knight knight, BreakableWall wall) {
        if (wall == null || wall.isBroken()) return false;

        if (knight.isAttacking()) {
            Rectangle attackHitbox = getAttackHitbox(knight);
            if (attackHitbox.overlaps(wall.getBounds())) {

                if (!knight.isAttackConsumed()) {
                    knight.setAttackConsumed(true);
                    wall.takeDamage();


                    knight.setKnockbackTimer(0.1f);
                    float knockbackDir = knight.isFacingRight() ? -1 : 1;
                    knight.setVelocityX(knockbackDir * 200);
                    return true;
                }
            }
        }
        return false;
    }
    public void handleCombatCrystallized(Knight knight, CrystalGuardian crys) {
        if (crys.isDead()) return;

        boolean pogoSuccessful = false;

        if (knight.isAttacking()) {
            Rectangle attackHitbox = getAttackHitbox(knight);
            if (attackHitbox.overlaps(crys.getBounds())) {

                if (knight.getState() == KnightState.ATTACK_DOWN) {
                    pogoSuccessful = true;
                }

                if (!knight.isAttackConsumed()) {
                    knight.setAttackConsumed(true);

                    if (pogoSuccessful) {
                        knight.setVelocityY(850f);
                        knight.setDashAvailable(true);
                        knight.setJumpCount(1);
                    }

                    if (crys.getInvincibleTimer() <= 0) {
                        int damage = Inventory.getInstance().isCharmEquipped(CharmType.UNBREAKABLE_STRENGTH) ? 2 : 1;
                        crys.takeDamage(damage);
                        crys.setInvincibleTimer(1f);
                        float knockbackDir = knight.isFacingRight() ? 1 : -1;
                        float kbForce = Inventory.getInstance().isCharmEquipped(CharmType.HEAVY_BLOW) ? 150f : 90f;
                        crys.setX(crys.getX() + (knockbackDir * kbForce));
                        soulController.addSoulFromAttack(knight);
                    }
                }
            }
        }

        if (!pogoSuccessful && knight.getBounds().overlaps(crys.getBounds())) {
            boolean hasSharpShadow = Inventory.getInstance().isCharmEquipped(CharmType.SHARP_SHADOW);
            if (knight.getState() == KnightState.DASH && hasSharpShadow) {
                knight.setInvincibleTimer(0.3f);
                if (crys.getInvincibleTimer() <= 0) {
                    int damage = Inventory.getInstance().isCharmEquipped(CharmType.UNBREAKABLE_STRENGTH) ? 2 : 1;
                    crys.takeDamage(damage);
                    crys.setInvincibleTimer(1f);
                }
            }
            else {
            if (knight.getInvincibleTimer() <= 0) {
                knight.takeDamage(1);
                crys.setHitKnight(true);
                knight.setKnockbackTimer(0.3f);
                float knockbackDir = knight.getX() < crys.getX() ? -1 : 1;
                knight.setVelocityX(knockbackDir * 400);
            } }
        }
    }
    public void handleSpellCombat(Object enemyObj) {
        Rectangle enemyBounds = null;
        float invincibility = 0;
        boolean isDead = false;

        if (enemyObj instanceof CrystalCrawler) {
            enemyBounds = ((CrystalCrawler) enemyObj).getBounds();
            invincibility = ((CrystalCrawler) enemyObj).getInvincibleTimer();
            isDead = ((CrystalCrawler) enemyObj).isDead();
        } else if (enemyObj instanceof HuskHornhead) {
            enemyBounds = ((HuskHornhead) enemyObj).getBounds();
            invincibility = ((HuskHornhead) enemyObj).getInvincibleTimer();
            isDead = ((HuskHornhead) enemyObj).isDead();
        } else if (enemyObj instanceof CrystalGuardian) {
            enemyBounds = ((CrystalGuardian) enemyObj).getBounds();
            invincibility = ((CrystalGuardian) enemyObj).getInvincibleTimer();
            isDead = ((CrystalGuardian) enemyObj).isDead();
        }
        else if (enemyObj instanceof Boss) {
            enemyBounds = ((Boss) enemyObj).getBounds();
            invincibility = ((Boss) enemyObj).getInvincibleTimer();
            isDead = ((Boss) enemyObj).isDead();
        }
        else if (enemyObj instanceof Mosquito) {
            enemyBounds = ((Mosquito) enemyObj).getBounds();
            invincibility = ((Mosquito) enemyObj).getInvincibleTimer();
            isDead = ((Mosquito) enemyObj).isDead();
        }
        if (isDead || enemyBounds == null) return;
        boolean hasVoidHeart = Inventory.getInstance().isCharmEquipped(CharmType.VOID_HEART);
        int spellDamage = hasVoidHeart ? 3 : 2;

        for (VengefulSpirit fb : GameModel.getInstance().getFireballs()) {
            if (fb.getBounds().overlaps(enemyBounds) && invincibility <= 0) {
                applyDamage(enemyObj, spellDamage, 0.5f);
                return;
            }
        }

        for (HowlingWraiths scream : GameModel.getInstance().getScreams()) {
            if (scream.getBounds().overlaps(enemyBounds) && invincibility <= 0) {
                applyDamage(enemyObj, spellDamage, 0.2f);
                return;
            }
        }
    }

    private void applyDamage(Object enemyObj, int damage, float invincibleTime) {
        if (enemyObj instanceof CrystalCrawler) {
            ((CrystalCrawler) enemyObj).takeDamage(damage);
            ((CrystalCrawler) enemyObj).setInvincibleTimer(invincibleTime);
        } else if (enemyObj instanceof HuskHornhead) {
            ((HuskHornhead) enemyObj).takeDamage(damage);
            ((HuskHornhead) enemyObj).setInvincibleTimer(invincibleTime);
        } else if (enemyObj instanceof CrystalGuardian) {
            ((CrystalGuardian) enemyObj).takeDamage(damage);
            ((CrystalGuardian) enemyObj).setInvincibleTimer(invincibleTime);
        }
        else if (enemyObj instanceof Boss) {
            ((Boss) enemyObj).takeDamage(damage);
            ((Boss) enemyObj).setInvincibleTimer(invincibleTime);
        }
        else if (enemyObj instanceof Mosquito) {
            ((Mosquito) enemyObj).takeDamage(damage);
            ((Mosquito) enemyObj).setInvincibleTimer(invincibleTime);
        }
    }
    public void handleBossCombat(Knight knight, Boss boss) {
        if (boss.isDead()) return;
        boolean pogoSuccessful = false;

        if (knight.isAttacking()) {
            Rectangle attackHitbox = getAttackHitbox(knight);
            if (attackHitbox.overlaps(boss.getBounds())) {
                if (knight.getState() == KnightState.ATTACK_DOWN) {
                    pogoSuccessful = true;
                }
                if (!knight.isAttackConsumed()) {
                    knight.setAttackConsumed(true);
                    if (pogoSuccessful) {
                        knight.setVelocityY(850f);
                        knight.setDashAvailable(true);
                        knight.setJumpCount(1);
                    }

                    if (boss.getInvincibleTimer() <= 0) {
                        int damage = Inventory.getInstance().isCharmEquipped(CharmType.UNBREAKABLE_STRENGTH) ? 2 : 1;


                        boss.takeDamage(damage);
                        boss.setInvincibleTimer(1f);
                        boss.setFacingRight(knight.getX() > boss.getX());
                        float knockbackDir = knight.isFacingRight() ? 1 : -1;
                        float kbForce = Inventory.getInstance().isCharmEquipped(CharmType.HEAVY_BLOW) ? 60f : 20f;
                        boss.setX(boss.getX() + (knockbackDir * kbForce));

                        soulController.addSoulFromAttack(knight);
                    }
                }
            }
        }


        if (!pogoSuccessful && knight.getBounds().overlaps(boss.getBounds()) && !boss.isHitKnight()) {
            boolean hasSharpShadow = Inventory.getInstance().isCharmEquipped(CharmType.SHARP_SHADOW);

            if (knight.getState() == KnightState.DASH && hasSharpShadow) {
                knight.setInvincibleTimer(0.3f);
                if (boss.getInvincibleTimer() <= 0) {
                    int damage = Inventory.getInstance().isCharmEquipped(CharmType.UNBREAKABLE_STRENGTH) ? 2 : 1;
                    boss.takeDamage(damage);
                    boss.setInvincibleTimer(1f);
                }
            }
            else {
                if (knight.getInvincibleTimer() <= 0) {
                    knight.takeDamage(1);
                    boss.setHitKnight(true);
                    knight.setKnockbackTimer(0.3f);
                    knight.setInvincibleTimer(1.5f);
                    float knockbackDir = knight.getX() < boss.getX() ? -1 : 1;
                    knight.setVelocityX(knockbackDir * 500);
                }
            }
        }
    }
    public void handleZoteCombat(Knight knight, Zote zote, ZoteController zoteController) {

        if (knight.isAttacking()) {
            Rectangle attackHitbox = getAttackHitbox(knight);

            Rectangle zoteHitbox = new Rectangle(zote.getX(), zote.getY(), zote.getWidth(), zote.getHeight());

            if (attackHitbox.overlaps(zoteHitbox)) {


                if (zote.getCurrentState() != ZoteState.Fall && zote.getCurrentState() != ZoteState.Attack) {

                    if (!knight.isAttackConsumed()) {
                        knight.setAttackConsumed(true);

                        if (knight.getState() == KnightState.ATTACK_DOWN) {
                            knight.setVelocityY(850f);
                            knight.setDashAvailable(true);
                            knight.setJumpCount(1);
                        } else {
                            knight.setKnockbackTimer(0.1f);
                            float knockbackDir = knight.isFacingRight() ? -1 : 1;
                            knight.setVelocityX(knockbackDir * 200);
                        }


                        zoteController.takeHit();
                    }
                }
            }
        }
    }
}
