package MyGame.view.renderers.environmentsRender;
import MyGame.Main;
import MyGame.controller.enemyController.CrystalCrawlerController;
import MyGame.controller.enemyController.CrystalGuardianController;
import MyGame.controller.enemyController.HuskHornheadController;
import MyGame.controller.enemyController.ZoteController;
import MyGame.controller.gameController.CombatController;
import MyGame.model.enums.MusicTracks;
import MyGame.model.world.AssetManager;
import MyGame.model.entities.*;
import MyGame.view.renderers.enemyRenderer.CrystalCrawlerRenderer;
import MyGame.view.renderers.enemyRenderer.CrystalGuardianRenderer;
import MyGame.view.renderers.enemyRenderer.HuskHornheadRenderer;
import MyGame.view.renderers.enemyRenderer.ZoteRenderer;
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
public class CrystalPeaksRenderer extends EnvironmentRenderer {
    private int[] backGrounds;
    private int[] foreGrounds;
    private Array<SolidBlock> solidBlocks;
    private Array<CrystalParticle> particles = new Array<>();
    private ShapeRenderer particleRenderer = new ShapeRenderer();
    private Array<Vector2> lampPositions;
    private LanternRenderer lanternRenderer;
    private CrystalCrawler crawler;
    private CrystalCrawler crawler2;
    private CrystalCrawlerController crawlerController;
    private CrystalCrawlerRenderer crawlerRenderer;
    private CombatController combatController;
    private CrystalCrawlerController crawlerController2;
    private CrystalCrawlerRenderer crawlerRenderer2;
    private HuskHornhead husk;
    private CrystalGuardian crystallized;
    private CrystalGuardianRenderer crystallizedRenderer;
    private CrystalGuardianController crystalGuardianController;
    private HuskHornheadController huskController;
    private HuskHornheadRenderer huskRenderer;
    private Zote zote;
    private ZoteController zoteController;
    private ZoteRenderer zoteRenderer;
    private DialogueBox dialogueBox;
    public CrystalPeaksRenderer() {

        backgroundTexture = AssetManager.getAssetManager().getCrystalPeaksBackground();


        this.helper = new TiledMapHelper();
        map = helper.loadMap("maps/crystal_map_Test.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        solidBlocks = helper.getSolidRectangles();
        lampPositions = helper.getLampPositions();
        lanternRenderer = new LanternRenderer();

             crawler = new CrystalCrawler(helper.getCrawlerSpawn().x, helper.getCrawlerSpawn().y);
            crawlerController=new CrystalCrawlerController(crawler, solidBlocks,helper.getBreakableWall());
            crawlerRenderer=new CrystalCrawlerRenderer(crawler);
            crawler2 = new CrystalCrawler(helper.getCrawlerSpawn2().x, helper.getCrawlerSpawn2().y);
            crawlerController2=new CrystalCrawlerController(crawler2, solidBlocks,helper.getBreakableWall());
            crawlerRenderer2=new CrystalCrawlerRenderer(crawler2);

            husk = new HuskHornhead(helper.getHuskSpawn().x, helper.getHuskSpawn().y);
            huskController = new HuskHornheadController(husk,solidBlocks);
            huskRenderer = new HuskHornheadRenderer(husk);

            crystallized = new CrystalGuardian(helper.getCrysSpawn().x, helper.getCrysSpawn().y);
            crystalGuardianController = new CrystalGuardianController(crystallized,solidBlocks);
            crystallizedRenderer = new CrystalGuardianRenderer(crystallized);

            zote = new Zote(helper.getZoteSpawn().x, helper.getZoteSpawn().y);
            zoteController = new ZoteController(zote);
            zoteRenderer = new ZoteRenderer(zote);
             dialogueBox = new DialogueBox();


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
        this.combatController = new CombatController();

        backgroundBatch = new SpriteBatch();
        for(int i = 0; i < 150; i++) {
            particles.add(
                new CrystalParticle(
                    MathUtils.random(2000, 10000),
                    MathUtils.random(14000, 20000),
                    MathUtils.random(10, 30),
                    MathUtils.random(2f, 6f)
                )
            );
        }
        if(Main.getMain().isMusicOn())Main.getMain().changeMusic(MusicTracks.CrystalPeak);
    }
    public void updateAndRenderEnemies(SpriteBatch batch, float delta,Knight knight) {
            crawlerController.update(delta);
        if (combatController != null) {
            combatController.handleCombat(knight, crawler);
            combatController.handleSpellCombat(crawler);
        }
        crawlerRenderer.render(batch, delta);
        crawlerController2.update(delta);
        if (combatController != null) {
            combatController.handleCombat(knight, crawler2);
            combatController.handleSpellCombat(crawler2);
        }
        crawlerRenderer2.render(batch, delta);

        huskController.update(delta,knight);
        if (combatController != null) {
            combatController.handleCombatHusk(knight, husk);
            combatController.handleSpellCombat(husk);
        }
        huskRenderer.render(batch, delta);

        crystalGuardianController.update(delta,knight);
        if (combatController != null) {
            combatController.handleCombatCrystallized(knight, crystallized);
            combatController.handleSpellCombat(crystallized);
        }
        crystallizedRenderer.render(batch, delta);
        zoteController.update(delta, knight);
        if (combatController != null) {
            combatController.handleZoteCombat(knight, zote,zoteController);
            combatController.handleSpellCombat(zote);
        }
        zoteRenderer.render(batch, delta,knight,dialogueBox);
    }
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
    public void renderForeground(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render(foreGrounds);
    }
    @Override
    public void render(OrthographicCamera camera) {
        renderBackground(camera);

    }

    public void dispose() {
        if (renderer != null) renderer.dispose();
        if (map != null) map.dispose();
        backgroundBatch.dispose();
        lanternRenderer.dispose();
    }
    private void updateParticles(float delta) {
        for(CrystalParticle p : particles) {

            p.update(delta);

            if(p.y < 14000) {
                p.y = 20000;
                p.x = MathUtils.random(2000, 10000);
            }
        }
        Main.getMain().updateMusic(delta);
    }
    private void renderParticles(OrthographicCamera camera) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        particleRenderer.setProjectionMatrix(camera.combined);
        particleRenderer.begin(ShapeRenderer.ShapeType.Filled);

        particleRenderer.setColor(0.7f, 0.5f, 0.95f, 0.6f);

        for(CrystalParticle p : particles) {
            particleRenderer.circle(p.x, p.y, MathUtils.random(5f, 9f));
        }

        particleRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
    @Override
    public void reset() {
        crawler = new CrystalCrawler(
            helper.getCrawlerSpawn().x,
            helper.getCrawlerSpawn().y
        );

        crawlerController = new CrystalCrawlerController(crawler, solidBlocks,helper.getBreakableWall());
        crawlerRenderer = new CrystalCrawlerRenderer(crawler);

        crawler2 = new CrystalCrawler(
            helper.getCrawlerSpawn2().x,
            helper.getCrawlerSpawn2().y
        );

        crawlerController2 = new CrystalCrawlerController(crawler2, solidBlocks,helper.getBreakableWall());
        crawlerRenderer2 = new CrystalCrawlerRenderer(crawler2);

        husk = new HuskHornhead(
           5550f,16083f
        );

        huskController = new HuskHornheadController(husk, solidBlocks);
        huskRenderer = new HuskHornheadRenderer(husk);

        crystallized = new CrystalGuardian(
            helper.getCrysSpawn().x,
            helper.getCrysSpawn().y
        );

        crystalGuardianController =
            new CrystalGuardianController(crystallized, solidBlocks);

        crystallizedRenderer =
            new CrystalGuardianRenderer(crystallized);}

    public void instaKillAll() {
        if (crawler != null) crawler.takeDamage(100);
        if (crawler2 != null) crawler2.takeDamage(100);
        if (husk != null) husk.takeDamage(100);
        if(crystallized!=null) crystallized.takeDamage(100);
    }
}
