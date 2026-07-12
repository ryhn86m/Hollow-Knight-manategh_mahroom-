package MyGame.model.world;

import MyGame.Main;
import MyGame.model.enums.EnvironmentType;
import MyGame.model.enums.Language;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class AssetManager {
    private static AssetManager AssetManager;
    private final Map<EnvironmentType, Texture> backgroundTextures = new HashMap<>();
    private final Map<EnvironmentType, Texture> tilesetTextures = new HashMap<>();
    private final Map<EnvironmentType, Texture> hazardTextures = new HashMap<>();
    private final Map<EnvironmentType, Texture> enemyTextures = new HashMap<>();
    private Texture playerIdle;
    private final Texture gameTitle;
    private final Texture gameTitleChinese;
    private final Texture teamCherryEnglish;
    private final Texture teamCherryChinese;
    private final Texture logo, laser, iconBozorg;
    private final Texture mainMenuBackground;
    private Texture settingsMenuBackground, dialogue_box;
    private Texture startGameMenuBackground;
    private Texture achievementMenuBackground;
    private Texture guideMenuBackground;
    private Texture inventoryMenuBackground, achievementMenu;
    private Texture crystalPeaksBackground, ForgottenCrossroadBG;
    private Texture pauseMenu1, pauseMenu2;
    private Texture exitMenuBackground, selectGame, currentGame;
    private Texture exit, wall, BossStand;
    private Texture achievement1, achievement2, achievement3, achievement4, achievement5, achievement6;
    private Texture maskFull, maskEmpty, beingAttacked, GuideMenuBG;
    private Texture soulCatcher, unbreakableStrength, dashMaster, quickSlash, quickFocus, heavyBlow, sharpShadow, voidHeart;
    TextureRegion startLaser, middleLaser, endLaser;
    Array<Texture> animationTextures = new Array<>();
    private Animation<TextureRegion> BGEnd, BGFire, mosIdle, mosAttack, mosAttackAnticipate, mosDeath, mosTurn;
    private Animation<TextureRegion> idleAnimation, fillMask, soulAnimation, dashEffect, attackEffect, walkHusk, attackHusk, idleHusk, turnHusk, deathHusk;
    private Animation<TextureRegion> runAnimation, walkCrystalCrawler, turnCrystalCrawler, deathCrystalCrawler;
    private Animation<TextureRegion> jumpAnimation, attackAltAnimation, upSlashAnimation, hurtAnimation, shootCrystallized,
        runCrystallized, deathCrystallized, idleCrystallized, turnCrystallized, evadeCrystallized, soulAnimationFilling;
    private Animation<TextureRegion> dashAnimation, doubleJumpAnimation, deathAnimation, downSlashAnimation, wallSlideAnimation;
    private Animation<TextureRegion> lookUp, soulScream, shadowScream, soulBall, blast, fireBallCast, shadowBall, shadowDash, breakWall, attackAnimation, fallAnimation, focusAnimation, focusEndAnimation, focusStartAnimation;
    private Animation<TextureRegion> attackZote, IdleZote, talkZote, rollZote, fallZote;
    private Animation<TextureRegion> attackBoss, runBoss, jumpBoss, IdleBoss, jumpAttackBoss, deathBoss, stunBoss, stunDoneBoss;

    private AssetManager() {
        iconBozorg = new Texture(Gdx.files.internal("IconBozorg.png"));
        achievement1 = new Texture(Gdx.files.internal("Achievement/1.png"));
        achievement2 = new Texture(Gdx.files.internal("Achievement/2.png"));
        achievement3 = new Texture(Gdx.files.internal("Achievement/3.png"));
        achievement4 = new Texture(Gdx.files.internal("Achievement/4.png"));
        achievement5 = new Texture(Gdx.files.internal("Achievement/5.png"));
        achievement6 = new Texture(Gdx.files.internal("Achievement/6.png"));
        GuideMenuBG = new Texture(Gdx.files.internal("GuideMenuBG.png"));
        dashMaster = new Texture(Gdx.files.internal("charms/Dashmaster.png"));
        heavyBlow = new Texture(Gdx.files.internal("charms/Heavy Blow.png"));
        quickFocus = new Texture(Gdx.files.internal("charms/Quick Focus.png"));
        quickSlash = new Texture(Gdx.files.internal("charms/Quick Slash.png"));
        sharpShadow = new Texture(Gdx.files.internal("charms/Sharp Shadow.png"));
        soulCatcher = new Texture(Gdx.files.internal("charms/Soul Catcher.png"));
        unbreakableStrength = new Texture(Gdx.files.internal("charms/Unbreakable Strength.png"));
        voidHeart = new Texture(Gdx.files.internal("charms/Void Heart.png"));
        wall = new Texture(Gdx.files.internal("maps/mine_break_wall_03.png"));
        mainMenuBackground = new Texture(Gdx.files.internal("MainMenuBG.png"));
        gameTitle = new Texture(Gdx.files.internal("HollowKnight.png"));
        gameTitleChinese = new Texture(Gdx.files.internal("HollowKnightChinese.png"));
        teamCherryEnglish = new Texture(Gdx.files.internal("TeamCherry.png"));
        teamCherryChinese = new Texture(Gdx.files.internal("TeamCherrychinese.png"));
        crystalPeaksBackground = new Texture(Gdx.files.internal("maps/BG.png"));
        logo = new Texture(Gdx.files.internal("Logos.png"));
        exitMenuBackground = new Texture(Gdx.files.internal("ExitMenuBG.png"));
        exit = new Texture(Gdx.files.internal("Exit.png"));
        selectGame = new Texture(Gdx.files.internal("selectGame.png"));
        currentGame = new Texture(Gdx.files.internal("currentGame.png"));
        settingsMenuBackground = new Texture(Gdx.files.internal("ExitMenuBG.png"));
        startGameMenuBackground = new Texture(Gdx.files.internal("StartGameMenuBG.PNG"));
        achievementMenu = new Texture(Gdx.files.internal("AchievementMenuBG.png"));
        inventoryMenuBackground = new Texture(Gdx.files.internal("InventoryBG.png"));
        pauseMenu1 = new Texture(Gdx.files.internal("pause1.png"));
        pauseMenu2 = new Texture(Gdx.files.internal("pause2.png"));
        beingAttacked = new Texture(Gdx.files.internal("animation/Husk_Hornhead/Death Air.png"));
        idleAnimation = loadAnimation("Idle", 9, 0.08f);
        runAnimation = loadAnimation("Run", 13, 0.06f);
        dashAnimation = loadAnimation("Dash", 12, 3f);
        jumpAnimation = loadAnimation("Airborne", 12, 0.08f);
        fallAnimation = loadAnimation("Fall", 6, 0.08f);
        doubleJumpAnimation = loadAnimation("Double Jump", 8, 0.1f);
        attackAnimation = loadAnimation("Slash", 5, 0.05f);
        attackAltAnimation = loadAnimation("SlashAlt", 5, 0.05f);
        upSlashAnimation = loadAnimation("UpSlash", 5, 0.05f);
        downSlashAnimation = loadAnimation("DownSlash", 5, 0.09f);
        hurtAnimation = loadAnimation("Idle Hurt", 12, 0.08f);
        deathAnimation = loadAnimation("Death", 18, 0.19f);
        wallSlideAnimation = loadAnimation("Wall Slide", 4, 0.1f);
        focusStartAnimation = loadAnimation("Focus Start", 3, 0.08f);
        focusAnimation = loadAnimation("Focus Get", 6, 0.08f);
        focusEndAnimation = loadAnimation("Focus End", 3, 0.08f);
        walkCrystalCrawler = loadAnimationCrystalCrawler("Walk", 4, 0.07f);
        turnCrystalCrawler = loadAnimationCrystalCrawler("Turn", 2, 0.08f);
        maskEmpty = new Texture(Gdx.files.internal("maskEmpty.png"));
        maskFull = new Texture(Gdx.files.internal("maskFull.png"));
        fillMask = loadAnimation("BreakHealth", 6, 0.08f);
        soulAnimation = loadAnimation("HealthBar", 5, 0.08f);
        attackEffect = loadAnimation("SlashEffect", 4, 0.05f);
        dashEffect = loadAnimation("Dash Effect", 7, 0.07f);
        deathCrystalCrawler = loadAnimationCrystalCrawler("Death Air", 5, 0.09f);
        deathHusk = loadAnimationHusk("Death Land", 8, 0.08f);
        idleHusk = loadAnimationHusk("Idle", 6, 0.08f);
        walkHusk = loadAnimationHusk("Walk", 7, 0.06f);
        attackHusk = loadAnimationHusk("Attack Lunge", 12, 0.09f);
        turnHusk = loadAnimationHusk("Turn", 2, 0.05f);
        shootCrystallized = loadAnimationCrystallized("Shoot", 7, 0.08f);
        runCrystallized = loadAnimationCrystallized("Run", 6, 0.07f);
        turnCrystallized = loadAnimationCrystallized("Turn", 3, 0.05f);
        idleCrystallized = loadAnimationCrystallized("Idle", 5, 0.07f);
        evadeCrystallized = loadAnimationCrystallized("Evade", 7, 0.08f);
        deathCrystallized = loadAnimationCrystallized("Death Land", 3, 0.05f);
        laser = new Texture(Gdx.files.internal("animation/Crystallized/atlas0 #25304.png"));
        startLaser = new TextureRegion(laser, 30, 20, 170, 35);
        middleLaser = new TextureRegion(laser, 30, 20, 170, 35);
        breakWall = loadAnimation("breakWall", 4, 0.5f);
        endLaser = new TextureRegion(laser, 10, 70, 160, 70);
        shadowDash = loadAnimation("Shadow Dash", 11, 0.07f);
        soulAnimationFilling = loadAnimation("HUD Cln", 19, 0.07f);
        soulScream = loadAnimation("SoulScream", 12, 0.1f);
        shadowScream = loadAnimation("ShadowScream", 13, 0.05f);
        soulBall = loadAnimation("SoulBall", 4, 0.08f);
        blast = loadAnimation("Blast", 8, 0.1f);
        fireBallCast = loadAnimation("Fireball Cast", 9, 0.1f);
        shadowBall = loadAnimation("ShadowBall", 6, 0.05f);
        lookUp = loadAnimation("LookUp", 6, 0.05f);
        attackZote = loadAnimationZote("Attack", 4, 0.12f);
        IdleZote = loadAnimationZote("Idle", 5, 0.1f);
        talkZote = loadAnimationZote("Talk", 5, 0.1f);
        rollZote = loadAnimationZote("Roll", 3, 0.16f);
        fallZote = loadAnimationZote("Fall", 5, 0.1f);
        dialogue_box = new Texture(Gdx.files.internal("dialogue_box.png"));
        attackBoss = loadAnimationBoss("Attack", 8, 0.2f);
        runBoss = loadAnimationBoss("Run", 5, 0.2f);
        jumpBoss = loadAnimationBoss("Jump", 9, 0.1f);
        IdleBoss = loadAnimationBoss("Idle", 5, 0.08f);
        jumpAttackBoss = loadAnimationBoss("Jump Attack", 9, 0.2f);
        deathBoss = loadAnimationBoss("DeathLand", 11, 0.1f);
        stunBoss = loadAnimationBoss("Body", 5, 0.08f);
        BossStand = new Texture(Gdx.files.internal("BossStand.png"));
        stunDoneBoss = loadAnimationBoss("BodyUp", 6, 0.09f);
        ForgottenCrossroadBG = new Texture(Gdx.files.internal("BG_Forgotten_crossroad.png"));
        mosIdle = loadAnimationMoss("Idle", 8, 0.08f);
        mosAttack = loadAnimationMoss("Attack", 3, 0.08f);
        mosAttackAnticipate = loadAnimationMoss("Attack Anticipate", 6, 0.08f);
        mosDeath = loadAnimationMoss("Death Air", 5, 0.08f);
        mosTurn = loadAnimationMoss("Turn", 2, 0.08f);
        BGFire = loadAnimationBG("ezgif-frame", 12, 0.18f);
        BGEnd = loadAnimationBG2("ezgif-frame", 19, 0.12f);
    }

    public static AssetManager getAssetManager() {
        if (AssetManager == null) {
            AssetManager = new AssetManager();
        }
        return AssetManager;
    }

    public Texture getTeamCherry() {
        if (Main.getLanguage() == Language.CHINESE) {
            return teamCherryChinese;
        }
        return teamCherryEnglish;
    }

    public Texture getGameTitle() {
        if (Main.getLanguage() == Language.CHINESE) {
            return gameTitleChinese;
        }
        return gameTitle;
    }

    private Animation<TextureRegion> loadAnimation(String baseName, int frameCount, float frameDuration) {
        TextureRegion[] frames = new TextureRegion[frameCount];
        for (int i = 0; i < frameCount; i++) {
            String path = String.format("animation/%s_%03d.png", baseName, i);
            Texture texture = new Texture(Gdx.files.internal(path));
            animationTextures.add(texture);
            frames[i] = new TextureRegion(texture);
        }
        return new Animation<>(frameDuration, frames);
    }

    private Animation<TextureRegion> loadAnimationBG(String baseName, int frameCount, float frameDuration) {
        TextureRegion[] frames = new TextureRegion[frameCount];
        for (int i = 0; i < frameCount; i++) {
            String path = String.format("animation/%s-%03d.jpg", baseName, i + 1);
            Texture texture = new Texture(Gdx.files.internal(path));
            animationTextures.add(texture);
            frames[i] = new TextureRegion(texture);
        }
        Animation<TextureRegion> animation = new Animation<>(frameDuration, frames);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        return animation;
    }

    private Animation<TextureRegion> loadAnimationBG2(String baseName, int frameCount, float frameDuration) {
        TextureRegion[] frames = new TextureRegion[frameCount];
        for (int i = 0; i < frameCount; i++) {
            String path = String.format("%s-%03d.png", baseName, i + 1);
            Texture texture = new Texture(Gdx.files.internal(path));
            animationTextures.add(texture);
            frames[i] = new TextureRegion(texture);
        }
        Animation<TextureRegion> animation = new Animation<>(frameDuration, frames);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        return animation;
    }

    private Animation<TextureRegion> loadAnimationCrystalCrawler(String baseName, int frameCount, float frameDuration) {
        TextureRegion[] frames = new TextureRegion[frameCount];
        for (int i = 0; i < frameCount; i++) {
            String path = String.format("animation/Crystal_Crawler/%s_%03d.png", baseName, i);
            Texture texture = new Texture(Gdx.files.internal(path));
            animationTextures.add(texture);
            frames[i] = new TextureRegion(texture);
        }
        return new Animation<>(frameDuration, frames);
    }

    private Animation<TextureRegion> loadAnimationHusk(String baseName, int frameCount, float frameDuration) {
        TextureRegion[] frames = new TextureRegion[frameCount];
        for (int i = 0; i < frameCount; i++) {
            String path = String.format("animation/Husk_Hornhead/%s_%03d.png", baseName, i);
            Texture texture = new Texture(Gdx.files.internal(path));
            animationTextures.add(texture);
            frames[i] = new TextureRegion(texture);
        }
        return new Animation<>(frameDuration, frames);
    }

    private Animation<TextureRegion> loadAnimationCrystallized(String baseName, int frameCount, float frameDuration) {
        TextureRegion[] frames = new TextureRegion[frameCount];
        for (int i = 0; i < frameCount; i++) {
            String path = String.format("animation/Crystallized/%s_%03d.png", baseName, i);
            Texture texture = new Texture(Gdx.files.internal(path));
            animationTextures.add(texture);
            frames[i] = new TextureRegion(texture);
        }
        return new Animation<>(frameDuration, frames);
    }

    private Animation<TextureRegion> loadAnimationZote(String baseName, int frameCount, float frameDuration) {
        TextureRegion[] frames = new TextureRegion[frameCount];
        for (int i = 0; i < frameCount; i++) {
            String path = String.format("animation/Zote/%s_%03d.png", baseName, i);
            Texture texture = new Texture(Gdx.files.internal(path));
            animationTextures.add(texture);
            frames[i] = new TextureRegion(texture);
        }
        return new Animation<>(frameDuration, frames);
    }

    private Animation<TextureRegion> loadAnimationBoss(String baseName, int frameCount, float frameDuration) {
        TextureRegion[] frames = new TextureRegion[frameCount];
        for (int i = 0; i < frameCount; i++) {
            String path = String.format("animation/False_knight/%s_%03d.png", baseName, i);
            Texture texture = new Texture(Gdx.files.internal(path));
            animationTextures.add(texture);
            frames[i] = new TextureRegion(texture);
        }
        return new Animation<>(frameDuration, frames);
    }

    private Animation<TextureRegion> loadAnimationMoss(String baseName, int frameCount, float frameDuration) {
        TextureRegion[] frames = new TextureRegion[frameCount];
        for (int i = 0; i < frameCount; i++) {
            String path = String.format("animation/Mosquito/%s_%03d.png", baseName, i);
            Texture texture = new Texture(Gdx.files.internal(path));
            animationTextures.add(texture);
            frames[i] = new TextureRegion(texture);
        }
        return new Animation<>(frameDuration, frames);
    }

    public void dispose() {
        for (Texture t : animationTextures) {
            if (t != null) t.dispose();
        }
        animationTextures.clear();
        if (mainMenuBackground != null) mainMenuBackground.dispose();
        if (gameTitle != null) gameTitle.dispose();
        if (gameTitleChinese != null) gameTitleChinese.dispose();
        if (teamCherryEnglish != null) teamCherryEnglish.dispose();
        if (teamCherryChinese != null) teamCherryChinese.dispose();
        if (logo != null) logo.dispose();
        if (exitMenuBackground != null) exitMenuBackground.dispose();
        if (exit != null) exit.dispose();
        if (selectGame != null) selectGame.dispose();
        if (currentGame != null) currentGame.dispose();
        if (settingsMenuBackground != null) settingsMenuBackground.dispose();
        if (startGameMenuBackground != null) startGameMenuBackground.dispose();
        if (achievementMenu != null) achievementMenu.dispose();
        if (inventoryMenuBackground != null) inventoryMenuBackground.dispose();
        if (pauseMenu1 != null) pauseMenu1.dispose();
        if (pauseMenu2 != null) pauseMenu2.dispose();
        if (GuideMenuBG != null) GuideMenuBG.dispose();
        if (dialogue_box != null) dialogue_box.dispose();
        if (crystalPeaksBackground != null) crystalPeaksBackground.dispose();
        if (ForgottenCrossroadBG != null) ForgottenCrossroadBG.dispose();
        if (wall != null) wall.dispose();
        if (BossStand != null) BossStand.dispose();
        if (achievement1 != null) achievement1.dispose();
        if (achievement2 != null) achievement2.dispose();
        if (achievement3 != null) achievement3.dispose();
        if (achievement4 != null) achievement4.dispose();
        if (achievement5 != null) achievement5.dispose();
        if (achievement6 != null) achievement6.dispose();
        if (dashMaster != null) dashMaster.dispose();
        if (heavyBlow != null) heavyBlow.dispose();
        if (quickFocus != null) quickFocus.dispose();
        if (quickSlash != null) quickSlash.dispose();
        if (sharpShadow != null) sharpShadow.dispose();
        if (soulCatcher != null) soulCatcher.dispose();
        if (unbreakableStrength != null) unbreakableStrength.dispose();
        if (voidHeart != null) voidHeart.dispose();
        if (maskEmpty != null) maskEmpty.dispose();
        if (maskFull != null) maskFull.dispose();
        if (iconBozorg != null) iconBozorg.dispose();
        if (beingAttacked != null) beingAttacked.dispose();
        if (laser != null) laser.dispose();
    }
}
