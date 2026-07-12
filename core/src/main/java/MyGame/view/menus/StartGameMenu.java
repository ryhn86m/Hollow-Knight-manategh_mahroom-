package MyGame.view.menus;

import MyGame.Main;
import MyGame.controller.menuController.StartGameMenuController;
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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StartGameMenu implements Screen {
    private Stage stage;
    public Table table;
    private final BitmapFont font;
    private final BitmapFont font2;
    private final TextButton startNewGame;
    private final TextButton currentGame1;
    private final TextButton currentGame2;
    private final TextButton currentGame3;
    private final TextButton currentGame4;
    private final TextButton back;
    private StartGameMenuController controller;
    private final Label.LabelStyle customLabel;
    private Array<CrystalParticle> particles = new Array<>();
    private ShapeRenderer particleRenderer = new ShapeRenderer();
    public StartGameMenu(StartGameMenuController controller){
        this.controller = controller;
        this.font = Main.getMain().getFont();
        this.font2 = Main.getMain().getFont2();
        this.table = new Table();
        TextButton.TextButtonStyle custom = new TextButton.TextButtonStyle();//(skin.get(TextButton.TextButtonStyle.class));
        custom.font=this.font;
        custom.fontColor = Color.WHITE;
        custom.overFontColor = Color.BLACK;
        custom.checkedFontColor = Color.WHITE;
        this.startNewGame = new TextButton(Main.getLanguage().startNewGame,custom);
        this.currentGame1= new TextButton(Main.getLanguage().currentGame1, custom);
        this.currentGame2= new TextButton(Main.getLanguage().currentGame2, custom);
        this.currentGame3= new TextButton(Main.getLanguage().currentGame3, custom);
        this.currentGame4= new TextButton(Main.getLanguage().currentGame4, custom);
        this.back = new TextButton(Main.getLanguage().back,custom);
        this.customLabel = new Label.LabelStyle();
        this.customLabel.font = this.font2;
        this.customLabel.fontColor = Color.WHITE;
        if(Main.getMain().isMusicOn())Main.getMain().changeMusic(MusicTracks.Menu2);
    }
    @Override
    public void show() {
        stage = new Stage(Main.getMain().getViewport());
        Image BG = new Image (AssetManager.getAssetManager().getStartGameMenuBackground());
        BG.setFillParent(true);
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.center();
        Label title = new Label(Main.getLanguage().StartGameMenu,customLabel);
        Image symbol = new Image(AssetManager.getAssetManager().getSelectGame());
        Image symbol2 = new Image(AssetManager.getAssetManager().getCurrentGame());
        float buttonWidth = 450f;
        float padding = 5f;
        table.clear();
        table.add(title).expandX().center().padBottom(padding).row();
        table.add(symbol).padBottom(90f).row();
        table.add(startNewGame).width(buttonWidth).padBottom(padding).row();
        table.add(new Image(AssetManager.getAssetManager().getCurrentGame()))
            .padBottom(40)
            .row();
        table.add(currentGame1).width(buttonWidth).padBottom(padding).row();
        table.add(new Image(AssetManager.getAssetManager().getCurrentGame()))
            .padBottom(40)
            .row();
        table.add(currentGame2).width(buttonWidth).padBottom(padding).row();
        table.add(new Image(AssetManager.getAssetManager().getCurrentGame()))
            .padBottom(40)
            .row();
        table.add(currentGame3).width(buttonWidth).padBottom(padding).row();
        table.add(new Image(AssetManager.getAssetManager().getCurrentGame()))
            .padBottom(40)
            .row();
        table.add(currentGame4).width(buttonWidth).padBottom(padding).row();
        table.add(new Image(AssetManager.getAssetManager().getCurrentGame()))
            .padBottom(90)
            .row();
        table.add(back).width(buttonWidth).padBottom(padding).row();
        stage.addActor(BG);
        stage.addActor(table);
        controller.setView(this);
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
        particleRenderer.setColor(1f, 0.65f, 0f, 0.6f);

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
