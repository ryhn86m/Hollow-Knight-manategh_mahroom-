package MyGame.view.menus;
import MyGame.Main;
import MyGame.controller.gameController.KeyController;
import MyGame.controller.menuController.MainMenuController;
import MyGame.model.enums.MusicTracks;
import MyGame.model.world.AssetManager;
import MyGame.view.renderers.itemsRenderer.CrystalParticle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class GuideMenu implements Screen {
    private Stage stage;
    private final BitmapFont font;
    private final BitmapFont fontSmall;
    private TextButton back;
    private final Label.LabelStyle customLabel;
    private final TextButton.TextButtonStyle customButton;
    private final Label.LabelStyle customLabelSmall;
    private Array<CrystalParticle> particles = new Array<>();
    private ShapeRenderer particleRenderer = new ShapeRenderer();
public GuideMenu(){
    this.font = Main.getMain().getFont();
    this.fontSmall = Main.getMain().getFontSmall();
    this.customLabel = new Label.LabelStyle();
    this.customLabel.font = this.font;
    this.customLabel.fontColor = Color.WHITE;
    this.customLabelSmall = new Label.LabelStyle();
    this.customLabelSmall.font = this.fontSmall;
    this.customLabelSmall.fontColor = Color.WHITE;
    this.customButton = new TextButton.TextButtonStyle();
    this.customButton.font = this.font;
    this.customButton.fontColor = Color.WHITE;
    if(Main.getMain().isMusicOn())Main.getMain().changeMusic(MusicTracks.Menu2);
}
    @Override
    public void show() {
        stage = new Stage(Main.getMain().getViewport());
        Gdx.input.setInputProcessor(stage);
        Image BG = new Image (AssetManager.getAssetManager().getGuideMenuBG());
        BG.setFillParent(true);
        stage.addActor(BG);
        String formattedText = String.format(Main.getLanguage().MotionKey,
            Input.Keys.toString(KeyController.JUMP),
            Input.Keys.toString(KeyController.ATTACK),
            Input.Keys.toString(KeyController.DASH),
            Input.Keys.toString(KeyController.FOCUS),
            Input.Keys.toString(KeyController.JUMP),
            Input.Keys.toString(KeyController.JUMP),
            Input.Keys.toString(KeyController.ATTACK),
            Input.Keys.toString(KeyController.INTERACT),
            Input.Keys.toString(KeyController.INVENTORY),
            Input.Keys.toString(KeyController.FIREBALL),
            Input.Keys.toString(KeyController.SCREAM)
        );

        Label motionKeys = new Label(formattedText, customLabelSmall);
        Label cheatCodes = new Label(Main.getLanguage().CheatCodes,customLabelSmall);
        Label abilities = new Label(Main.getLanguage().abilities,customLabelSmall);
        Label titleLabel = new Label(Main.getLanguage().GuideMenu,customLabel);
        back = new TextButton(Main.getLanguage().back,customButton);
        abilities.setWrap(true);
        cheatCodes.setWrap(true);
        motionKeys.setWrap(true);
        motionKeys.setAlignment(Align.topLeft);
        abilities.setAlignment(Align.topLeft);
        cheatCodes.setAlignment(Align.topLeft);
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.top();
        rootTable.pad(30f);
        rootTable.add(titleLabel).align(Align.center).padBottom(30f).row();

        float col1Width = 420f;
        float col2Width = 600f;
        float col3Width = 1400f;

        Table contentTable = new Table();

        contentTable.add(motionKeys).width(col1Width).top().left().padRight(30f);
        contentTable.add(cheatCodes).width(col2Width).top().left().padRight(30f);
        contentTable.add(abilities).width(col3Width).top().left().row();

        rootTable.add(contentTable).expandX().align(Align.center).row();
        rootTable.add(back).expandY().bottom().align(Align.center).padBottom(20f);

        stage.addActor(rootTable);

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenu(new MainMenuController(), null));
            }
        });
        float width = stage.getViewport().getWorldWidth();
        float height = stage.getViewport().getWorldHeight();
        particles.clear();
        for (int i = 0; i < 50; i++) {
            float startX = MathUtils.random(0, width);
            float startY = MathUtils.random(0, height);
            float speed = MathUtils.random(30f, 100f);
            float size = MathUtils.random(2f, 6f);
            CrystalParticle p = new CrystalParticle(startX, startY, speed,size);
            particles.add(p);
        }
    }
    private void updateParticles(float delta) {
        float width = stage.getViewport().getWorldWidth();
        float height = stage.getViewport().getWorldHeight();

        for(CrystalParticle p : particles) {
            p.update(delta);


            if(p.y < -20) {
                p.y = height + 20;
                p.x = MathUtils.random(0, width);
            }
        }
    }

    private void renderParticles() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        particleRenderer.setProjectionMatrix(stage.getCamera().combined);
        particleRenderer.begin(ShapeRenderer.ShapeType.Filled);
        particleRenderer.setColor(1f, 1f, 1f, 0.6f);

        for(CrystalParticle p : particles) {
            particleRenderer.circle(p.x, p.y, p.size);
        }

        particleRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.BLACK);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        updateParticles(v);
        renderParticles();
        Main.getMain().updateMusic(v);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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

    }
}
