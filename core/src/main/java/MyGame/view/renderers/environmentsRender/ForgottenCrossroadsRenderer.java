package MyGame.view.renderers.environmentsRender;

import MyGame.controller.enemyController.*;
import MyGame.controller.gameController.CombatController;
import MyGame.model.world.AssetManager;
import MyGame.model.entities.*;
import MyGame.view.renderers.enemyRenderer.*;
import MyGame.view.renderers.itemsRenderer.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgottenCrossroadsRenderer extends EnvironmentRenderer {
    private Boss boss;
    private BossController bossController;
    private BossRenderer bossRenderer;
private Mosquito mos;
private MosquitoRenderer mosRenderer;
private MosquitoController mosController;
    private CombatController combatController;
    private HuskHornhead husk2;
    private LanternRenderer lanternRenderer;
    private Array<Vector2> lampPositions;
    private Array<SolidBlock> solidBlocks;
    private CrystalGuardian crystallized2;
    private CrystalGuardianRenderer crystallizedRenderer2;
    private CrystalGuardianController crystalGuardianController2;
    private HuskHornheadController huskController2;
    private HuskHornheadRenderer huskRenderer2;
    private int[] backGrounds;
    private int[] foreGrounds;
    private Array<CrystalParticle> particles = new Array<>();
    private ShapeRenderer particleRenderer = new ShapeRenderer();
    public ForgottenCrossroadsRenderer(){
        backgroundTexture = AssetManager.getAssetManager().getForgottenCrossroadBG();

        backgroundBatch = new SpriteBatch();
        this.helper = new TiledMapHelper();
       map = helper.loadMap("maps/crystal_map_Test.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        solidBlocks = helper.getSolidRectangles();
        lampPositions = helper.getLampPositions();
        lanternRenderer = new LanternRenderer();
        husk2= new HuskHornhead(helper.getHuskSpawn2().x, helper.getHuskSpawn2().y);
        huskController2 = new HuskHornheadController(husk2,solidBlocks);
        huskRenderer2 = new HuskHornheadRenderer(husk2);

        crystallized2 = new CrystalGuardian(helper.getCrysSpawn2().x, helper.getCrysSpawn2().y);
        crystalGuardianController2 = new CrystalGuardianController(crystallized2,solidBlocks);
        crystallizedRenderer2 = new CrystalGuardianRenderer(crystallized2);

        mos = new Mosquito(helper.getMosSpawn().x,helper.getMosSpawn().y);
        mosController = new MosquitoController(mos,solidBlocks);
        mosRenderer = new MosquitoRenderer(mos);

        this.boss = new Boss(helper.getBossSpawn().x,helper.getBossSpawn().y);
        this.bossRenderer = new BossRenderer(boss);
        this.bossController = new BossController(boss,solidBlocks);
        combatController = new CombatController();
        backGrounds = new int[] {
            map.getLayers().getIndex("BG"),
            map.getLayers().getIndex("backbackbackground"),
            map.getLayers().getIndex("backbackground"),
            map.getLayers().getIndex("background"),
            map.getLayers().getIndex("main")
        };
        foreGrounds = new int[] {
            map.getLayers().getIndex("foreground"),
            map.getLayers().getIndex("foreforeground")
        };
        for(int i = 0; i < 150; i++) {
            particles.add(
                new CrystalParticle(
                    MathUtils.random(10400f, 20000f),
                    MathUtils.random(14000f, 20000f),
                    MathUtils.random(10f, 30f),
                    MathUtils.random(2f, 6f)
                )
            );
        }
    }

    public void updateAndRenderEnemiesAndNPCs(SpriteBatch batch, float delta, Knight knight) {
        huskController2.update(delta, knight);
        if (combatController != null) {
            combatController.handleCombatHusk(knight, husk2);
            combatController.handleSpellCombat(husk2);
        }
        huskRenderer2.render(batch, delta);

        crystalGuardianController2.update(delta, knight);
        if (combatController != null) {
            combatController.handleCombatCrystallized(knight, crystallized2);
            combatController.handleSpellCombat(crystallized2);
        }
        crystallizedRenderer2.render(batch, delta);

        mosController.update(delta,knight.getX(),knight.getY());
        if (combatController != null) {
            combatController.handleCombatMosquito(knight, mos);
            combatController.handleSpellCombat(mos);
        }
        mosRenderer.render(batch, delta);

       bossController.update(delta, knight.getX(),knight.getY());
        if (combatController != null) {
            combatController.handleBossCombat(knight, boss);
            combatController.handleSpellCombat(boss);
        }
        bossRenderer.render(batch, delta);


    }
    @Override
    public void reset() {

    }

    @Override
    public void render(OrthographicCamera camera) {
        renderBackground(camera);
    }

    @Override
    public void dispose() {
        if (renderer != null) renderer.dispose();
        if (map != null) map.dispose();
        backgroundBatch.dispose();
        lanternRenderer.dispose();
    }

    @Override
    public Array<SolidBlock> getSolidBlocks() {
        return solidBlocks;
    }

    @Override
    public void renderBackground(OrthographicCamera camera) {
        backgroundBatch.setProjectionMatrix(camera.combined);

        renderer.setView(camera);
        AnimatedTiledMapTile.updateAnimationBaseTime();
        renderer.render(backGrounds);
        updateParticles(Gdx.graphics.getDeltaTime());
        renderParticles(camera);
        lanternRenderer.updateTime(Gdx.graphics.getDeltaTime());

        backgroundBatch.begin();
        backgroundBatch.setBlendFunction(com.badlogic.gdx.graphics.GL20.GL_SRC_ALPHA, com.badlogic.gdx.graphics.GL20.GL_ONE);

        for(Vector2 pos : lampPositions) {
            lanternRenderer.render(backgroundBatch, pos.x, pos.y);
        }

        backgroundBatch.setBlendFunction(com.badlogic.gdx.graphics.GL20.GL_SRC_ALPHA, com.badlogic.gdx.graphics.GL20.GL_ONE_MINUS_SRC_ALPHA);

        backgroundBatch.end();
    }

    @Override
    public void renderForeground(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render(foreGrounds);
    }

    public void instaKillAll() {

        if (boss != null && !boss.isDead()) {
            boss.instaKill();
        }
        if (husk2 != null) husk2.takeDamage(100);
        if (boss != null) boss.takeDamage(100);
        if(crystallized2!=null) crystallized2.takeDamage(100);
        if(mos!=null) mos.takeDamage(100);

    }
    private void updateParticles(float delta) {
        for(CrystalParticle p : particles) {
            p.update(delta);
            if(p.y < 14000) {
                p.y = 20000;
                p.x = MathUtils.random(10400f, 20000f);
            }
        }
    }

    private void renderParticles(OrthographicCamera camera) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        particleRenderer.setProjectionMatrix(camera.combined);
        particleRenderer.begin(ShapeRenderer.ShapeType.Filled);


        particleRenderer.setColor(0.25f, 0.6f, 0.95f, 0.8f);

        for(CrystalParticle p : particles) {
            particleRenderer.circle(p.x, p.y, MathUtils.random(5f, 9f));
        }

        particleRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
    @Override
    public Vector2 getPlayerSpawnPoint() {
        return helper.getKnightSpawn2();
    }
}
