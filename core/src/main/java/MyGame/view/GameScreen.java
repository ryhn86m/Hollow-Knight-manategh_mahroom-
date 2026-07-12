package MyGame.view;

import MyGame.Main;
import MyGame.controller.gameController.GameController;
import MyGame.controller.gameController.GameProcessor;
import MyGame.controller.menuController.Achievement.AchievementPopupSystem;
import MyGame.controller.menuController.Achievement.AchievementsMenuController;
import MyGame.controller.menuController.PauseMenuController;
import MyGame.model.entities.CrystalCrawler;
import MyGame.model.enums.CharmType;
import MyGame.model.world.AssetManager;
import MyGame.model.world.GameModel;
import MyGame.model.entities.Boss;
import MyGame.model.entities.BreakableWall;
import MyGame.model.entities.Knight;
import MyGame.model.enums.EnvironmentType;
import MyGame.model.enums.KnightState;
import MyGame.model.enums.MusicTracks;
import MyGame.model.world.Inventory;
import MyGame.service.SaveData;
import MyGame.service.SaveService;
import MyGame.view.menus.EndGameMenu;
import MyGame.view.menus.InventoryMenu;
import MyGame.view.menus.PauseMenu;
import MyGame.view.renderers.environmentsRender.EnvironmentRenderer;
import MyGame.view.renderers.itemsRenderer.*;
import MyGame.view.renderers.environmentsRender.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameScreen implements Screen {
    private Knight knight;
    private Stage stage;
    private SpriteBatch batch;
    private ScreenViewport viewport;
    private GameController controller;
    private PauseMenu view;
    private BreakableWall wall;
    private BreakableWall wall2;
    private boolean isBossArenaLocked = false;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private GameProcessor gameProcessor;
    private PauseMenu pauseMenu;
    private InventoryMenu inventoryMenu;
    private GameModel model;
    private Texture darkTexture;
    private EnvironmentRenderer currentMapRenderer;
    private KnightRenderer knightRenderer;
    private HUDRenderer hudRenderer;
    private float mapStartX;
    private float mapEndX;
    private float mapTopY;
    private float shakeTime = 0f;
    private float shakePower = 0f;
    float transitionPoint = 10338.163f;
    private AchievementPopupSystem achievementPopup;
    private SpellsRenderer spellsRenderer;
    public GameScreen(SaveData save) {

        this.model = GameModel.getInstance(knight);
        initializeBase();

        this.model.setCurrentSlot(save.getSlot());


        if (save.getEnvironmentType() == EnvironmentType.CRYSTAL_PEAKS) {
            this.currentMapRenderer = new CrystalPeaksRenderer();
        } else {
            this.currentMapRenderer = new ForgottenCrossroadsRenderer();
        }
        model.setCurrentMapRenderer(currentMapRenderer);
        float startX = (float) save.getPlayerX();
        float startY = (float) save.getPlayerY();


        if (startX == 0 && startY == 0) {
            Vector2 spawn = currentMapRenderer.getPlayerSpawnPoint();
            startX = spawn.x;
            startY = spawn.y;
        }


        this.knight = new Knight(startX, startY);
        this.model.setKnight(this.knight);

        this.knight.setHp(save.getHp());
        this.knight.setSoul(save.getSoul());


        this.model.setHp(save.getHp());
        this.model.setSoul(save.getSoul());
        this.model.setDeathCount(save.getDeathCount());
        this.model.setEnemiesKilled(save.getKillCount());
        this.model.setElapsedTime(save.getElapsedTime());
        Inventory inventory = Inventory.getInstance();
        inventory.getEquippedCharms().clear();


        if (save.getSoulCatcher() == 1) inventory.equipCharm(CharmType.SOUL_CATCHER);
        if (save.getDashmaster() == 1) inventory.equipCharm(CharmType.DASHMASTER);
        if (save.getUnbreakableStrength() == 1) inventory.equipCharm(CharmType.UNBREAKABLE_STRENGTH);
        if (save.getQuickSlash() == 1) inventory.equipCharm(CharmType.QUICK_SLASH);
        if (save.getQuickFocus() == 1) inventory.equipCharm(CharmType.QUICK_FOCUS);
        if (save.getHeavyBlow() == 1) inventory.equipCharm(CharmType.HEAVY_BLOW);
        if (save.getSharpShadow() == 1) inventory.equipCharm(CharmType.SHARP_SHADOW);
        if (save.getVoidHeart() == 1) inventory.equipCharm(CharmType.VOID_HEART);
        if (this.currentMapRenderer instanceof ForgottenCrossroadsRenderer) {
            Boss boss = ((ForgottenCrossroadsRenderer) this.currentMapRenderer).getBoss();
            if (boss != null) {
                if (save.getBossDefeated() == 1) {
                    boss.setDead(true);
                } else {

                    if (save.getBossX() != 0 && save.getBossY() != 0) {
                        boss.setX((float) save.getBossX());
                        boss.setY((float) save.getBossY());
                    }


                    if (save.getBossHp() > 0 && save.getBossHp() <= boss.getMaxHp()) {
                        boss.setHp(save.getBossHp());
                    }


                    boss.setPhase2(save.getBossPhase2() == 1);
                }
            }
        }
        this.knightRenderer = new KnightRenderer(this.knight);
        this.spellsRenderer = new SpellsRenderer();
        this.controller = new GameController(knight, currentMapRenderer.getSolidBlocks(), currentMapRenderer, currentMapRenderer.getHelper().getBreakableWall(),currentMapRenderer.getHelper().getBreakableWall2());
        this.hudRenderer = new HUDRenderer(knight);
    }

    public void saveAndQuit() {
        SaveData data = new SaveData();
        data.setSlot(model.getCurrentSlot());
        data.setPlayerX(knight.getX());
        data.setPlayerY(knight.getY());
        data.setHp(knight.getHp());
        data.setSoul(knight.getSoul());

        data.setDeathCount(model.getDeathCount());
        data.setKillCount(model.getEnemiesKilled());
        data.setKillCount(model.getEnemiesKilled());
        if (currentMapRenderer instanceof CrystalPeaksRenderer) {
            data.setEnvironmentType(EnvironmentType.CRYSTAL_PEAKS);
        } else {
            data.setEnvironmentType(EnvironmentType.CROSSROADS);
        }
        if (currentMapRenderer instanceof ForgottenCrossroadsRenderer) {
            data.setEnvironmentType(EnvironmentType.CROSSROADS);

            Boss boss = ((ForgottenCrossroadsRenderer) currentMapRenderer).getBoss();
            if (boss != null) {
                if (boss.isDead()) {
                    data.setBossDefeated(1);
                } else {
                    data.setBossDefeated(0);
                    data.setBossX(boss.getX());
                    data.setBossY(boss.getY());
                    data.setBossHp(boss.getHp());
                    data.setBossPhase2(boss.isPhase2() ? 1 : 0);
                }
            }
        }
        Inventory inventory = Inventory.getInstance();

        data.setSoulCatcher(inventory.isCharmEquipped(CharmType.SOUL_CATCHER) ? 1 : 0);
        data.setDashmaster(inventory.isCharmEquipped(CharmType.DASHMASTER) ? 1 : 0);
        data.setUnbreakableStrength(inventory.isCharmEquipped(CharmType.UNBREAKABLE_STRENGTH) ? 1 : 0);
        data.setQuickSlash(inventory.isCharmEquipped(CharmType.QUICK_SLASH) ? 1 : 0);
        data.setQuickFocus(inventory.isCharmEquipped(CharmType.QUICK_FOCUS) ? 1 : 0);
        data.setHeavyBlow(inventory.isCharmEquipped(CharmType.HEAVY_BLOW) ? 1 : 0);
        data.setSharpShadow(inventory.isCharmEquipped(CharmType.SHARP_SHADOW) ? 1 : 0);
        data.setVoidHeart(inventory.isCharmEquipped(CharmType.VOID_HEART) ? 1 : 0);
        data.setElapsedTime(model.getElapsedTime());

        if (data.getSlot() != -1) {
            SaveService.save(data.getSlot(), data);
        }
    }

    private void initializeBase() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        viewport = new ScreenViewport(camera);
        shapeRenderer = new ShapeRenderer();
        model.setPaused(false);
        pauseMenu = new PauseMenu(new PauseMenuController(model), new ScreenViewport()
        );
        inventoryMenu = new InventoryMenu();
    }
    public void startShake(float duration,float power){

        shakeTime = duration;

        shakePower = power;
    }
    @Override
    public void render(float delta) {
        System.out.println(knight.getX()+" "+knight.getY());
        if(knight.getX() ==  currentMapRenderer.getHelper().getHeartSpawn().x &&
            knight.getY() ==  currentMapRenderer.getHelper().getHeartSpawn().y ){
            model.setVoidHeartLocked(false);
            Inventory.getInstance().equipCharm(CharmType.VOID_HEART);
        }
        delta = Math.min(delta, 0.05f);
        if (model.isInZone2() && currentMapRenderer instanceof CrystalPeaksRenderer) {
            switchEnvironment(new ForgottenCrossroadsRenderer());
        }
        ScreenUtils.clear(Color.BLACK);
        float leftLimit;
        float rightLimit;

        float transitionPoint = 10338.163f;
        float padding = 1400f;
        if (!model.isInZone2() && knight.getX() >= transitionPoint) {
            model.setInZone2(true);
            if(Main.getMain().isMusicOn())Main.getMain().changeMusic(MusicTracks.Forgotten);
            switchEnvironment(new ForgottenCrossroadsRenderer());
        }

        if (knight.getX() < transitionPoint) {
            float leftPadding = 850f;
            leftLimit = mapStartX + 880 + leftPadding;
            rightLimit = transitionPoint - padding;
        } else {
            leftLimit = transitionPoint + 1700;
            rightLimit = mapEndX + 5000;
        }

        if (rightLimit < leftLimit) {
            rightLimit = leftLimit;
        }

        float clampedX = MathUtils.clamp(knight.getX(), leftLimit, rightLimit);
        float yCameraOffset = -400f;
        float cameraVisibleHeight = viewport.getWorldHeight() * camera.zoom;
        float desiredTargetY = knight.getY() - yCameraOffset;
        float maxTargetY = mapTopY - (cameraVisibleHeight / 2f);
        float targetY = Math.min(desiredTargetY, maxTargetY);
        float lerpSpeed = 5f * delta;
        float baseCamX;
        float baseCamY;

        if (currentMapRenderer instanceof ForgottenCrossroadsRenderer) {
            Vector2 bossSpawn = currentMapRenderer.getHelper().getBossSpawn();
            Boss currentBoss = ((ForgottenCrossroadsRenderer) currentMapRenderer).getBoss();

            if (bossSpawn != null && currentBoss != null) {


                if (!isBossArenaLocked && !currentBoss.isDead() &&
                    Math.abs(knight.getX() - bossSpawn.x) < 1638f && Math.abs(knight.getY() - bossSpawn.y) < 100 && wall2.isBroken()) {
                    isBossArenaLocked = true;
                } else if (currentBoss.isDead()) {
                    isBossArenaLocked = false;
                }

                if (!currentBoss.isDead()) {
                    float arenaLeft = bossSpawn.x - 160f;
                    float arenaRight = bossSpawn.x + 1738f;



                        if (currentBoss.getX() < arenaLeft) {
                            currentBoss.setX(arenaLeft);
                        }
                        if (currentBoss.getX() > arenaRight - currentBoss.getWidth()) {
                            currentBoss.setX(arenaRight - currentBoss.getWidth());
                        }

                }
                if (isBossArenaLocked && !currentBoss.isDead()) {
                    float arenaLeft = bossSpawn.x - 160f;
                    float arenaRight = bossSpawn.x + 1738f;
                    float arenaBottom = bossSpawn.y - 100f;
                    float arenaTop = bossSpawn.y + 640f;

                    float arenaWidth = arenaRight - arenaLeft;
                    float arenaHeight = arenaTop - arenaBottom;
                    float zoomX = arenaWidth / viewport.getWorldWidth();
                    float zoomY = arenaHeight / viewport.getWorldHeight();
                    camera.zoom = Math.min(zoomX, zoomY);

                    float cameraVisibleWidth = viewport.getWorldWidth() * camera.zoom;
                     cameraVisibleHeight = viewport.getWorldHeight() * camera.zoom;

                    if (knight.getY() > arenaTop - knight.getHeight()) {
                        knight.setY(arenaTop - knight.getHeight());
                    }
                    if (knight.getX() < arenaLeft) {
                        knight.setX(arenaLeft);
                    }
                    if (knight.getX() > arenaRight - knight.getWidth()) {
                        knight.setX(arenaRight - knight.getWidth());
                    }

                    float desiredCamY = arenaBottom + (arenaHeight / 2f);
                    float desiredCamX = knight.getX();


                    clampedX = MathUtils.clamp(desiredCamX, arenaLeft + (cameraVisibleWidth / 2f), arenaRight - (cameraVisibleWidth / 2f));
                    targetY = MathUtils.clamp(desiredCamY, arenaBottom + (cameraVisibleHeight / 2f), arenaTop - (cameraVisibleHeight / 2f));


                    baseCamX = camera.position.x + (clampedX - camera.position.x) * lerpSpeed;
                    baseCamY = camera.position.y + (targetY - camera.position.y) * lerpSpeed;


                } else {
                    camera.zoom = 1f;
                    baseCamX = camera.position.x + (clampedX - camera.position.x) * lerpSpeed;
                    baseCamY = camera.position.y + (targetY - camera.position.y) * lerpSpeed;
                }
            } else {
                camera.zoom = 1f;
                baseCamX = camera.position.x + (clampedX - camera.position.x) * lerpSpeed;
                baseCamY = camera.position.y + (targetY - camera.position.y) * lerpSpeed;
            }
        } else {
            camera.zoom = 1f;
            baseCamX = camera.position.x + (clampedX - camera.position.x) * lerpSpeed;
            baseCamY = camera.position.y + (targetY - camera.position.y) * lerpSpeed;
        }


        float shakeOffsetX = 0;
        float shakeOffsetY = 0;

        if (shakeTime > 0) {
            shakeTime -= delta;
            shakeOffsetX = MathUtils.random(-shakePower, shakePower);
            shakeOffsetY = MathUtils.random(-shakePower, shakePower);
        }


        camera.position.set(baseCamX + shakeOffsetX, baseCamY + shakeOffsetY, 0);
        if (knight.isDamageShakeRequested()) {
            startShake(0.8f, 5f);
            knight.clearDamageShakeRequest();
        }
        if (knight.isSpellShakeRequested()) {
            startShake(0.5f, 6f);
            knight.clearSpellShakeRequest();
        }
        Boss falseKnight = null;
        if (currentMapRenderer instanceof ForgottenCrossroadsRenderer) {
            falseKnight = ((ForgottenCrossroadsRenderer) currentMapRenderer).getBoss();
        }
        if (falseKnight != null && falseKnight.isCameraShakeRequested()) {
            startShake(falseKnight.getShakeDuration(), falseKnight.getShakePower());
            falseKnight.clearCameraShakeRequest();
        }


        camera.update();

        camera.position.set(baseCamX, baseCamY, 0);
        if (model.isBossDefeated() && model.getBossDefeatTimer() >= 10f) {

            Main.getMain().setScreen(new EndGameMenu( model));
            return;
        }
        if (!model.isPaused()) {
            model.update(delta);
            controller.update(delta);
            if (controller.isRespawnRequested()) {

                respawnGame();

                controller.clearRespawnRequest();
            }
            if (knight.isDamageShakeRequested()) {
                startShake(0.8f, 5f);
                knight.clearDamageShakeRequest();
            }
            currentMapRenderer.renderBackground(camera);
            batch.setProjectionMatrix(camera.combined);

            batch.begin();
            spellsRenderer.SpellsRender(batch, model,knight);
            knightRenderer.render(batch, delta);
            if (currentMapRenderer instanceof CrystalPeaksRenderer) {
                ((CrystalPeaksRenderer) currentMapRenderer).updateAndRenderEnemies(batch, delta, knight);
            } else if (currentMapRenderer instanceof ForgottenCrossroadsRenderer) {
                ((ForgottenCrossroadsRenderer) currentMapRenderer).updateAndRenderEnemiesAndNPCs(batch, delta, knight);
            }

             wall = currentMapRenderer.getHelper().getBreakableWall();
             wall2 = currentMapRenderer.getHelper().getBreakableWall2();
            Rectangle secretCover = currentMapRenderer.getHelper().getSecretCover();
            Rectangle secretCover2 = currentMapRenderer.getHelper().getSecretCover2();
            Vector2 heartPos = currentMapRenderer.getHelper().getHeartSpawn();

            if (heartPos != null && model.isVoidHeartLocked()) {
                Texture imgHeart = AssetManager.getAssetManager().getVoidHeart();
                batch.draw(imgHeart, heartPos.x, heartPos.y, 90, 90);
                Rectangle heartHitbox = new Rectangle(heartPos.x, heartPos.y, 60, 60);
                if (knight.getBounds().overlaps(heartHitbox)) {
                    model.setVoidHeartLocked(false);
                    Inventory.getInstance().equipCharm(CharmType.VOID_HEART);

                }
            }

            if (wall != null && !wall.isBroken() && secretCover != null) {

                batch.setColor(0, 0, 0, 1f);
                batch.draw(
                    darkTexture,
                    secretCover.x,
                    secretCover.y,
                    secretCover.width,
                    secretCover.height
                );
                batch.setColor(Color.WHITE);
            }
            if (wall2 != null && !wall2.isBroken() && secretCover2 != null) {

                batch.setColor(0, 0, 0, 1f);
                batch.draw(
                    darkTexture,
                    secretCover2.x,
                    secretCover2.y,
                    secretCover2.width,
                    secretCover2.height
                );
                batch.setColor(Color.WHITE);
            }


            if (wall != null && !wall.isBreaking() && !wall.isBroken()) {

                batch.draw(
                    AssetManager.getAssetManager().getWall(),
                    wall.getBounds().x,
                    wall.getBounds().y,
                    wall.getBounds().width,
                    wall.getBounds().height
                );
            }
            if (wall2 != null && !wall2.isBreaking() && !wall2.isBroken()) {

                batch.draw(
                    AssetManager.getAssetManager().getWall(),
                    wall2.getBounds().x,
                    wall2.getBounds().y,
                    wall2.getBounds().width,
                    wall2.getBounds().height
                );
            }


            if (wall != null && wall.isBreaking() && !wall.isBroken()) {

                wall.setStateTime(wall.getStateTime() + delta);

                TextureRegion currentFrame =
                    AssetManager.getAssetManager()
                        .getBreakWall()
                        .getKeyFrame(wall.getStateTime(), false);

                batch.draw(
                    currentFrame,
                    wall.getBounds().x,
                    wall.getBounds().y,
                    wall.getBounds().width,
                    wall.getBounds().height
                );


                if (AssetManager.getAssetManager()
                    .getBreakWall()
                    .isAnimationFinished(wall.getStateTime())) {

                    wall.setBroken(true);
                }
            }
            if (wall2 != null && wall2.isBreaking() && !wall2.isBroken()) {

                wall2.setStateTime(wall2.getStateTime() + delta);

                TextureRegion currentFrame =
                    AssetManager.getAssetManager()
                        .getBreakWall()
                        .getKeyFrame(wall2.getStateTime(), false);

                batch.draw(
                    currentFrame,
                    wall2.getBounds().x,
                    wall2.getBounds().y,
                    wall2.getBounds().width,
                    wall2.getBounds().height
                );


                if (AssetManager.getAssetManager()
                    .getBreakWall()
                    .isAnimationFinished(wall2.getStateTime())) {

                    wall2.setBroken(true);
                }
            }

            batch.end();
            if (currentMapRenderer instanceof CrystalPeaksRenderer) {
                DialogueBox db = ((CrystalPeaksRenderer) currentMapRenderer).getDialogueBox();
                batch.setProjectionMatrix(stage.getCamera().combined);
                db.getShapeRenderer().setProjectionMatrix(stage.getCamera().combined);
                db.update(delta);
                db.render(batch, viewport.getWorldWidth(), viewport.getWorldHeight());
            }
            shapeRenderer.setProjectionMatrix(camera.combined);
           shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

            for (RockParticle p : controller.getRockParticles()) {
                shapeRenderer.setColor(0.14f, 0.18f, 0.24f, 1f);
                shapeRenderer.circle(p.x, p.y, p.size);
            }


            hudRenderer.render(delta);
            currentMapRenderer.renderForeground(camera);

     shapeRenderer.end();
        } else {

            currentMapRenderer.renderBackground(camera);

            batch.setProjectionMatrix(stage.getCamera().combined);
            batch.begin();

            batch.enableBlending();
            batch.setColor(0, 0, 0, 0.6f);

            batch.draw(
                darkTexture,
                0,
                0,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight()
            );

            batch.setColor(Color.WHITE);
            batch.end();

            currentMapRenderer.renderForeground(camera);
            pauseMenu.draw(delta);
            batch.begin();

            pauseMenu.getController().Render(delta,batch);
            batch.end();
            if(model.isInventoryOpen()){
                inventoryMenu.render(delta);
            }
        }
        Main.getMain().updateMusic(delta);
        batch.setProjectionMatrix(stage.getCamera().combined);
        batch.begin();
        achievementPopup.updateAndRender(delta, batch);
        batch.end();
    }
    @Override
    public void show() {
        this.achievementPopup = new AchievementPopupSystem();
        AchievementsMenuController.getInstance().addObserver(this.achievementPopup);
        mapStartX = Float.MAX_VALUE;
        mapEndX = Float.MIN_VALUE;
        mapTopY = Float.MIN_VALUE;
        for(SolidBlock block : currentMapRenderer.getSolidBlocks()){
            mapStartX = Math.min(mapStartX, block.getBounds().x);
            mapEndX = Math.max(mapEndX, block.getBounds().x + block.getBounds().width);
            mapTopY = Math.max(mapTopY, block.getBounds().y + block.getBounds().height);
        }

        stage = new Stage(Main.getMain().getViewport());
        if (batch == null) {
            batch = new SpriteBatch();
        }
        if (currentMapRenderer instanceof CrystalPeaksRenderer) {
            CrystalPeaksRenderer cr = (CrystalPeaksRenderer) currentMapRenderer;
            gameProcessor = new GameProcessor(model, pauseMenu, knight, cr.getZote(), cr.getZoteController(), cr.getDialogueBox(),currentMapRenderer.getHelper());
        } else {
            gameProcessor = new GameProcessor(model, pauseMenu, knight);
        }
        inventoryMenu.show();
        InputMultiplexer inputProcessor = new InputMultiplexer();
        inputProcessor.addProcessor(pauseMenu.getStage());
        inputProcessor.addProcessor(inventoryMenu.getStage());
        inputProcessor.addProcessor(stage);
        inputProcessor.addProcessor(gameProcessor);
        Gdx.input.setInputProcessor(inputProcessor);
        if (model.isPaused()) {
            pauseMenu.show();
        } else {
            pauseMenu.hide();
        }
        if (darkTexture == null) {
            Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pixmap.setColor(Color.WHITE);
            pixmap.fill();

            darkTexture = new Texture(pixmap);
            pixmap.dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);

        if (pauseMenu != null && pauseMenu.getStage() != null) {
            pauseMenu.getStage().getViewport().update(width, height, true);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        darkTexture.dispose();
        pauseMenu.getStage().dispose();
        if (currentMapRenderer != null) {
            currentMapRenderer.dispose();
        }
    }
    public void respawnGame() {

        knight.respawn(true,
            currentMapRenderer.getPlayerSpawnPoint());
        knight.setState(KnightState.IDLE);
        wall.reset();
        currentMapRenderer.reset();
        if (currentMapRenderer instanceof ForgottenCrossroadsRenderer) {
            Boss boss = ((ForgottenCrossroadsRenderer) currentMapRenderer).getBoss();
            if (boss != null && !boss.isDead()) {
                boss.reset();


                Vector2 bossSpawn = currentMapRenderer.getHelper().getBossSpawn();
                if (bossSpawn != null) {
                    boss.setX(bossSpawn.x);
                    boss.setY(bossSpawn.y);
                }
            }
    } }
    public void switchEnvironment(EnvironmentRenderer newRenderer) {
        if (currentMapRenderer != null) {
            currentMapRenderer.dispose();
        }

        currentMapRenderer = newRenderer;
        model.setCurrentMapRenderer(currentMapRenderer);

        Vector2 spawn;

        if (model.isTeleportToBoss()) {
            spawn = new Vector2(17889.041f,15675.0f);
            model.setTeleportToBoss(false);
        } else {
            spawn = currentMapRenderer.getPlayerSpawnPoint();
        }

        knight.setX(spawn.x);
        knight.setY(spawn.y);

        this.controller = new GameController(knight, currentMapRenderer.getSolidBlocks(), currentMapRenderer, currentMapRenderer.getHelper().getBreakableWall(), currentMapRenderer.getHelper().getBreakableWall2());


        mapStartX = Float.MAX_VALUE;
        mapEndX = Float.MIN_VALUE;
        mapTopY = Float.MIN_VALUE;
        for(SolidBlock block : currentMapRenderer.getSolidBlocks()){
            mapStartX = Math.min(mapStartX, block.getBounds().x);
            mapEndX = Math.max(mapEndX, block.getBounds().x + block.getBounds().width);
            mapTopY = Math.max(mapTopY, block.getBounds().y + block.getBounds().height);
        }

        InputMultiplexer inputProcessor = (InputMultiplexer) Gdx.input.getInputProcessor();
        inputProcessor.removeProcessor(gameProcessor);

        if (currentMapRenderer instanceof CrystalPeaksRenderer) {
            CrystalPeaksRenderer cr = (CrystalPeaksRenderer) currentMapRenderer;
            gameProcessor = new GameProcessor(model, pauseMenu, knight, cr.getZote(), cr.getZoteController(), cr.getDialogueBox(), currentMapRenderer.getHelper());
        } else {
            gameProcessor = new GameProcessor(model, pauseMenu, knight);
        }

        inputProcessor.addProcessor(gameProcessor);
    }

}
