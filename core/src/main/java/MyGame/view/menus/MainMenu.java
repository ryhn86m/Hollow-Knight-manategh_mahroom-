package MyGame.view.menus;
import MyGame.Main;
import MyGame.controller.menuController.MainMenuController;
import MyGame.model.enums.MusicTracks;
import MyGame.model.world.AssetManager;
import MyGame.view.renderers.itemsRenderer.CrystalParticle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainMenu implements Screen {
    private Stage stage;
    Image cherry,title,logos;
    private final BitmapFont font;
    private final TextButton startGame;
    private final TextButton settings;
    private final TextButton guide;
    private final TextButton achievements;
    private final TextButton quitGame;
    public Table table;
    private final MainMenuController controller;
    private Array<CrystalParticle> particles = new Array<>();
    private ShapeRenderer particleRenderer = new ShapeRenderer();


    public MainMenu(MainMenuController controller, Skin skin) {

        this.controller = controller;
        this.table= new Table();
        this.font= Main.getMain().getFont();
        TextButton.TextButtonStyle custom = new TextButton.TextButtonStyle();//(skin.get(TextButton.TextButtonStyle.class));
        custom.font=this.font;
        this.startGame = new TextButton(Main.getLanguage().StartGame,custom);
        this.guide= new TextButton(Main.getLanguage().Guide, custom);
        this.achievements= new TextButton(Main.getLanguage().Achievements, custom);
        this.quitGame= new TextButton(Main.getLanguage().QuitGame, custom);
        this.settings= new TextButton(Main.getLanguage().Settings, custom);
        if(Main.getMain().isMusicOn())Main.getMain().changeMusic(MusicTracks.Enter);
    }

    @Override
    public void show() {
            stage = new Stage(Main.getMain().getViewport());

            Image BG = new Image(AssetManager.getAssetManager().getMainMenuBackground());
            BG.setFillParent(true);

            Gdx.input.setInputProcessor(stage);

            title = new Image(AssetManager.getAssetManager().getGameTitle());
            logos = new Image(AssetManager.getAssetManager().getLogo());
            cherry = new Image(AssetManager.getAssetManager().getTeamCherry());

            title.setScaling(Scaling.fit);

            Table rootTable = new Table();
            rootTable.setFillParent(true);

            float buttonWidth = 450f;
            float padding = 30f;

            table.clear();
            table.add(startGame).width(buttonWidth).padBottom(padding).row();
            table.add(settings).width(buttonWidth).padBottom(padding).row();
            table.add(guide).width(buttonWidth).padBottom(padding).row();
            table.add(achievements).width(buttonWidth).padBottom(padding).row();
            table.add(quitGame).width(buttonWidth).padBottom(padding).row();

            rootTable.add(title)
                .colspan(2)
                .center()
                .padTop(40f)
                .width(Value.percentWidth(0.72f, rootTable))
                .row();

            rootTable.add().colspan(2).expandY().row();

            rootTable.add(table)
                .colspan(2)
                .center()
                .row();

            rootTable.add().colspan(2).expandY().row();

            rootTable.add(logos)
                .bottom().left()
                .padLeft(30f).padBottom(25f)
                .expandX();
            rootTable.add(cherry)
                .bottom().right()
                .padRight(30f).padBottom(25f)
                .expandX();

            stage.addActor(BG);
            stage.addActor(rootTable);
        controller.setView(this);
        float width = stage.getViewport().getWorldWidth();
        float height = stage.getViewport().getWorldHeight();
        particles.clear();
        for (int i = 0; i < 70; i++) {
            float startX = MathUtils.random(0, width);
            float startY = MathUtils.random(0, height);
            float speed = MathUtils.random(-10f, -60f);
            float size = MathUtils.random(2f, 6f);
            CrystalParticle p = new CrystalParticle(startX, startY, speed,size);
            particles.add(p);
        }
        }


    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.BLACK);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        updateParticles(v);
        Main.getMain().updateMusic(v);
        renderParticles();
    }
    private void updateParticles(float delta) {
        float width = stage.getViewport().getWorldWidth();
        float height = stage.getViewport().getWorldHeight();

        for(CrystalParticle p : particles) {
            p.update(delta);


            if(p.y > height + 20) {
                p.y = -20;
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
     if(stage!=null) stage.dispose();
    }

}
