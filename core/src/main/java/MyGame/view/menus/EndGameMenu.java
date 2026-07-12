package MyGame.view.menus;

import MyGame.Main;
import MyGame.controller.menuController.MainMenuController;
import MyGame.model.enums.EnvironmentType;
import MyGame.model.enums.MusicTracks;
import MyGame.model.world.AssetManager;
import MyGame.model.world.GameModel;
import MyGame.service.SaveData;
import MyGame.service.SaveService;
import MyGame.view.GameScreen;
import MyGame.view.renderers.itemsRenderer.CrystalParticle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class EndGameMenu implements Screen {
    private Stage stage;
    public Table table;
    private final BitmapFont font;
    private final BitmapFont font2;
    private final TextButton playAgain;
    private final TextButton backToMainMenu;
    private final Label.LabelStyle customLabel;
    private Array<CrystalParticle> particles = new Array<>();
    private GameModel model;
    private ShapeRenderer particleRenderer = new ShapeRenderer();

    public EndGameMenu(GameModel model){
        this.model = model;
        this.font = Main.getMain().getFont();
        this.font2 = Main.getMain().getFont2();
        this.table = new Table();

        TextButton.TextButtonStyle custom = new TextButton.TextButtonStyle();
        custom.font = this.font;
        custom.fontColor = Color.WHITE;
        custom.overFontColor = Color.BLACK;
        custom.checkedFontColor = Color.WHITE;

        this.playAgain = new TextButton(Main.getLanguage().playAgain, custom);
        this.backToMainMenu = new TextButton(Main.getLanguage().backToMainMenu, custom);

        this.customLabel = new Label.LabelStyle();
        this.customLabel.font = this.font2;
        this.customLabel.fontColor = Color.WHITE;

        if(Main.getMain().isMusicOn()) {
            Main.getMain().changeMusic(MusicTracks.End_Game);
        }
    }

    @Override
    public void show() {
        stage = new Stage(Main.getMain().getViewport());
        Gdx.input.setInputProcessor(stage);

        Actor animatedBG = new Actor() {
            float stateTime = 0f;
            @Override
            public void act(float delta) {
                super.act(delta);
                stateTime += delta;
            }
            @Override
            public void draw(Batch batch, float parentAlpha) {
                TextureRegion currentFrame = AssetManager.getAssetManager().getBGEnd().getKeyFrame(stateTime);
                batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
            }
        };
        animatedBG.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(animatedBG);

        table.setFillParent(true);
        table.center();


        Label titleLabel = new Label(Main.getLanguage().victory, customLabel);
        titleLabel.setColor(Color.GOLD);

        Label timeLabel = new Label(Main.getLanguage().totalTime + " " + model.getFormattedTime(), customLabel);
        Label deathLabel = new Label(Main.getLanguage().Deaths + " " + model.getDeathCount(), customLabel);
        Label killsLabel = new Label(Main.getLanguage().enemyKilled + " " + model.getEnemiesKilled(), customLabel);

        float buttonWidth = 450f;
        float padding = 15f;

        table.clear();
        table.add(titleLabel).expandX().center().padBottom(40f).row();
        table.add(timeLabel).padBottom(padding).row();
        table.add(deathLabel).padBottom(padding).row();
        table.add(killsLabel).padBottom(40f).row();
        table.add(playAgain).width(buttonWidth).padBottom(10f).row();
        table.add(backToMainMenu).width(buttonWidth).padBottom(padding).row();

        stage.addActor(table);

        playAgain.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //GameModel.getInstance().setDeathCount(0);
                if (GameModel.getInstance() != null) model.resetGameStats();
                int emptySlot = SaveService.getFirstEmptySlot();
                SaveData save = new SaveData();
                save.setSlot(emptySlot);
                save.setEnvironmentType(EnvironmentType.CRYSTAL_PEAKS);
                save.setHp(5);
                save.setSoul(0);
                save.setPlayerX(0);
                save.setPlayerY(0);
                if (emptySlot != -1) {
                    SaveService.save(emptySlot, save);
                }
                Main.getMain().setScreen(new GameScreen(save));
            }
        });

        backToMainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
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
            CrystalParticle p = new CrystalParticle(startX, startY, speed, size);
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
        particleRenderer.setColor(0f, 0f, 0f, 0.6f);

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
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        if(stage != null) stage.dispose();
        if(particleRenderer != null) particleRenderer.dispose();
    }
}
