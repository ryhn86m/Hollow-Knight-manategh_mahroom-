package MyGame.view.menus;
import MyGame.Main;
import MyGame.controller.menuController.Achievement.AchievementsMenuController;
import MyGame.controller.menuController.MainMenuController;
import MyGame.controller.menuController.MenusController;
import MyGame.model.enums.MusicTracks;
import MyGame.model.world.AssetManager;
import MyGame.view.renderers.itemsRenderer.CrystalParticle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class AchievementsMenu extends MenusController implements Screen {
    private Stage stage;
    private final BitmapFont font;
    private final BitmapFont fontSmall;
    private TextButton backBtn;
    private Array<CrystalParticle> particles = new Array<>();
    private ShapeRenderer particleRenderer = new ShapeRenderer();
    private Label.LabelStyle titleStyle;
    private Label.LabelStyle textStyle;
    private Label.LabelStyle lockedTitleStyle;
    private Label.LabelStyle lockedTextStyle;
    public AchievementsMenu() {
        this.font = Main.getMain().getFont();
        this.fontSmall = Main.getMain().getFontSmall();

        if(Main.getMain().isMusicOn())Main.getMain().changeMusic(MusicTracks.Menu2);
        titleStyle = new Label.LabelStyle(font, Color.WHITE);
        textStyle = new Label.LabelStyle(fontSmall, Color.GOLD);


        lockedTitleStyle = new Label.LabelStyle(font, Color.GRAY);
        lockedTextStyle = new Label.LabelStyle(fontSmall, Color. LIGHT_GRAY);
    }
    @Override
    public void show() {

        stage = new Stage(Main.getMain().getViewport());

        Gdx.input.setInputProcessor(stage);


        Image bg = new Image(AssetManager.getAssetManager().getAchievementMenu());
        bg.setFillParent(true);
        stage.addActor(bg);

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.top().pad(40f);
        Label mainTitle = new Label(Main.getLanguage().Achievements, titleStyle);
        rootTable.add(mainTitle).colspan(2).align(Align.center).padBottom(30f).row();


        Table listTable = new Table();
        Table imageTable = new Table();
        AchievementsMenuController amController = AchievementsMenuController.getInstance();
        Texture a1 = AssetManager.getAssetManager().getAchievement1();
        Texture a2 = AssetManager.getAssetManager().getAchievement2();
        Texture a3 = AssetManager.getAssetManager().getAchievement3();
        Texture a4 = AssetManager.getAssetManager().getAchievement4();
        Texture a5 = AssetManager.getAssetManager().getAchievement5();
        Texture a6 = AssetManager.getAssetManager().getAchievement6();
        addAchievementRow(listTable, Main.getLanguage().getA1(),imageTable, Main.getLanguage().getDesc1(), amController.isCompletionUnlocked(),a1);
        addAchievementRow(listTable, Main.getLanguage().getA2(),imageTable, Main.getLanguage().getDesc2(), amController.isSpeedrunUnlocked(),a2);
        addAchievementRow(listTable, Main.getLanguage().getA3(),imageTable, Main.getLanguage().getDesc3(), amController.isTrueHunterUnlocked(),a3);
        addAchievementRow(listTable, Main.getLanguage().getA4(),imageTable, Main.getLanguage().getDesc4(), amController.isDefeatFalseKnightUnlocked(),a4);
        addAchievementRow(listTable, Main.getLanguage().getA5(),imageTable, Main.getLanguage().getDesc5(), amController.isPureCombatUnlocked(),a5);
        addAchievementRow(listTable, Main.getLanguage().getA6(),imageTable, Main.getLanguage().getDesc6(), amController.isSoulFoulUnlocked(),a6);


        Texture bigIcon = AssetManager.getAssetManager().getIconBozorg();
        Image bigImage = new Image(bigIcon);
        rootTable.center();
        Table contentTable = new Table();

        contentTable.add(listTable).padRight(500f);
        contentTable.add(imageTable).padRight(200f);
        contentTable.add(bigImage).size(600f, 600f);

        rootTable.add(contentTable).center();
        rootTable.row();

        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
        btnStyle.font = font;
        btnStyle.fontColor = Color.WHITE;
        backBtn = new TextButton(Main.getLanguage().back, btnStyle);


        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenu(new MainMenuController(), null));
            }
        });

        rootTable.add(backBtn).colspan(2).expandY().bottom().padBottom(10f);

        stage.addActor(rootTable);
        float width = stage.getViewport().getWorldWidth();
        float height = stage.getViewport().getWorldHeight();
        particles.clear();
        for (int i = 0; i < 50; i++) {
            float startX = MathUtils.random(0, width);
            float startY = MathUtils.random(0, height);
            float speed = MathUtils.random(10f, 60f);
            float size = MathUtils.random(2f, 6f);
            CrystalParticle p = new CrystalParticle(startX, startY, speed,size);
            particles.add(p);
        }
        addClickSoundToButtons(stage.getRoot());
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
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.act(delta);
        stage.draw();
        updateParticles(delta);
        Main.getMain().updateMusic(delta);
        renderParticles();
    }
    private void addAchievementRow(Table table, String name, Table imageTable, String desc, boolean isUnlocked, Texture image) {
        Label.LabelStyle currentTitleStyle = isUnlocked ? titleStyle : lockedTitleStyle;
        Label.LabelStyle currentDescStyle = isUnlocked ? textStyle : lockedTextStyle;

        String statusIcon = isUnlocked ? " [UNLOCKED]" : " [LOCKED]";

        Label nameLabel = new Label(name + statusIcon, currentTitleStyle);
        Label descLabel = new Label(desc, currentDescStyle);
        descLabel.setWrap(true);


        if (!isUnlocked) {
            nameLabel.setColor(0.5f, 0.5f, 0.5f, 1f);
            descLabel.setColor(0.5f, 0.5f, 0.5f, 1f);
        }

        table.add(nameLabel).left().width(900f).padBottom(5f).row();
        table.add(descLabel).left().width(900f).padBottom(60f).row();
        Image iconImage = new Image(image);
        iconImage.setScale(2f,2f);

        if (!isUnlocked) {
            iconImage.setColor(Color.DARK_GRAY);
        }

        imageTable.add(iconImage).size(80f, 80f).right().padBottom(100f).row();
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
        if(stage != null) stage.dispose();
    }
}
