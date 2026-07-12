package MyGame.view.menus;

import MyGame.Main;
import MyGame.controller.menuController.ExitMenuController;
import MyGame.model.enums.MusicTracks;
import MyGame.model.world.AssetManager;
import MyGame.view.renderers.itemsRenderer.CrystalParticle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class ExitMenu implements Screen {
    private Stage stage;
    private final BitmapFont font;
    private final BitmapFont font2;
    private final TextButton yes;
    private final TextButton no;
    private final TextButton quitGame;
    public Table table;
    private Array<CrystalParticle> particles = new Array<>();
    private ShapeRenderer particleRenderer = new ShapeRenderer();
    private final ExitMenuController controller;
    public ExitMenu(ExitMenuController controller, Skin skin) {
        this.controller = controller;
        this.table= new Table();
        controller.setView(this);
        this.font= Main.getMain().getFont();
        this.font2= Main.getMain().getFont2();
        TextButton.TextButtonStyle custom = new TextButton.TextButtonStyle();
        custom.font=this.font;
        TextButton.TextButtonStyle custom2 = new TextButton.TextButtonStyle();
        custom2.font=this.font2;
        this.yes = new TextButton(Main.getLanguage().Yes,custom);
        this.no= new TextButton(Main.getLanguage().No, custom);
        this.quitGame= new TextButton(Main.getLanguage().quitGame, custom2);
        if(Main.getMain().isMusicOn())Main.getMain().changeMusic(MusicTracks.FireBG);
    }
    @Override
    public void show() {
        stage = new Stage(Main.getMain().getViewport());
        Actor animatedBG = new Actor() {
            float stateTime = 0f;
            @Override
            public void act(float delta) {
                super.act(delta);
                stateTime += delta;
            }
            @Override
            public void draw(Batch batch, float parentAlpha) {
                TextureRegion currentFrame = AssetManager.getAssetManager().getBGFire().getKeyFrame(stateTime);
                batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
            }
        };
        animatedBG.setSize(stage.getWidth(), stage.getHeight());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        Image symbol = new Image(AssetManager.getAssetManager().getExit());
        float buttonWidth = 450f;
        float padding = 60f;
        table.clear();
        table.add(symbol).width(600).padBottom(padding).row();
        table.add(quitGame).width(buttonWidth).padBottom(padding).row();
        table.add(yes).width(buttonWidth).padBottom(padding).row();
        table.add(no).width(buttonWidth).padBottom(padding).row();
        stage.addActor(animatedBG);
        stage.addActor(table);
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

    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.BLACK);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        controller.handleExitMenuButtons();
        updateParticles(v);
        Main.getMain().updateMusic(v);
        renderParticles();
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

    public Stage getStage() {
        return stage;
    }

    public TextButton getNo() {
        return no;
    }

    public TextButton getYes() {
        return yes;
    }
}
