package MyGame.view.menus;

import MyGame.Main;
import MyGame.controller.gameController.KeyController;
import MyGame.controller.menuController.SettingsMenuController;
import MyGame.model.enums.MusicTracks;
import MyGame.model.world.AssetManager;
import MyGame.model.enums.Language;
import MyGame.view.renderers.itemsRenderer.CrystalParticle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettingsMenu implements Screen {
    private Stage stage;
private final Slider musicVolume;
private final Slider brightness;
private final CheckBox music;
private final CheckBox soundEffect;
private final BitmapFont font;
private final TextButton back;
private final Label.LabelStyle customLabel;
private final TextButton resetController;
private final TextButton resetSound;
private final SelectBox<Language> languages;
private SettingsMenuController controller;
    private TextButton jumpBtn;
    private TextButton attackBtn;
    private TextButton dashBtn;
    private TextButton fireballBtn;
    private TextButton screamBtn;
    private TextButton inventoryBtn;
    private TextButton interactBtn;
    private TextButton focusBtn;
    TextButton.TextButtonStyle btnStyle;
    private Array<CrystalParticle> particles = new Array<>();
    private ShapeRenderer particleRenderer = new ShapeRenderer();
public SettingsMenu(SettingsMenuController controller, Skin skin){
    this.controller = controller;
    this.font = Main.getMain().getFont();
    controller.setView(this);
     btnStyle = new TextButton.TextButtonStyle();
    btnStyle.font = this.font;
    btnStyle.fontColor = Color.CYAN;
    btnStyle.overFontColor = Color.BLACK;
    btnStyle.checkedFontColor = Color.CYAN;

    CheckBox.CheckBoxStyle checkStyle = new CheckBox.CheckBoxStyle();
    checkStyle.font = this.font;
    checkStyle.fontColor = Color.CYAN;
    checkStyle.overFontColor = Color.BLACK;
    checkStyle.checkedFontColor = Color.CYAN;

    Texture offTex = new Texture(Gdx.files.internal("emptyBox.png"));
    checkStyle.checkboxOff = new TextureRegionDrawable(offTex);

    Texture onTex = new Texture(Gdx.files.internal("tickcheckbox.png"));
    checkStyle.checkboxOn = new TextureRegionDrawable(onTex);
    music = new CheckBox( Main.getLanguage().OnOff, checkStyle);
    music.setChecked(true);

    soundEffect = new CheckBox( Main.getLanguage().OnOff, checkStyle);
    soundEffect.setChecked(true);


    Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
    sliderStyle.background = createColorDrawable(300, 10, Color.WHITE);
    sliderStyle.knob = createColorDrawable(20, 30, Color.LIGHT_GRAY);
    this.customLabel = new Label.LabelStyle();
    this.customLabel.font = this.font;
    this.customLabel.fontColor = Color.CYAN;
    SelectBox.SelectBoxStyle selectStyle = new SelectBox.SelectBoxStyle();
    selectStyle.font = this.font;
    selectStyle.fontColor = Color.CYAN;
    selectStyle.scrollStyle = new ScrollPane.ScrollPaneStyle();

    List.ListStyle listStyle = new List.ListStyle();
    listStyle.font = this.font;
    listStyle.fontColorSelected = Color.CYAN;
    listStyle.fontColorUnselected = Color.LIGHT_GRAY;
    listStyle.selection = createColorDrawable(200, 40, Color.BLUE);
    selectStyle.listStyle = listStyle;

    musicVolume = new Slider(0f, 100f, 1f, false, sliderStyle);
    musicVolume.setValue(50f);

    brightness = new Slider(0f, 100f, 1f, false, sliderStyle);
    brightness.setValue(50f);

    resetSound = new TextButton(Main.getLanguage().resetAudio, btnStyle);
    resetController = new TextButton(Main.getLanguage().resetControls, btnStyle);
    back = new TextButton(Main.getLanguage().back, btnStyle);

    languages = new SelectBox<>(selectStyle);
    languages.setItems(Language.ENGLISH, Language.CHINESE);
    languages.setSelected(Main.getLanguage());
    if(Main.getMain().isMusicOn())Main.getMain().changeMusic(MusicTracks.Menu2);
}
    private TextureRegionDrawable createColorDrawable(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegionDrawable(texture);
    }
    @Override
    public void show() {
    music.getImage().setScale(0.2f);
    soundEffect.getImage().setScale(0.2f);
    stage = new Stage(Main.getMain().getViewport());
    Image BG = new Image (AssetManager.getAssetManager().getSettingsMenuBackground());
    BG.setFillParent(true);

    stage.addActor(BG);
        Gdx.input.setInputProcessor(stage);
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        Label titleLabel = new Label(Main.getLanguage().Settings,customLabel);
        rootTable.add(titleLabel).colspan(2).padBottom(50f).row();
        float pad = 30f;
        rootTable.add(new Label(Main.getLanguage().musicVolume, customLabel)).right().pad(pad);
        rootTable.add(musicVolume).left().width(300f).pad(pad).row();


        rootTable.add(new Label(Main.getLanguage().music, customLabel)).right().pad(pad).height(30f);
        rootTable.add(music).left().pad(pad).height(30f).row();


        rootTable.add(new Label(Main.getLanguage().soundEffect, customLabel)).right().pad(pad).height(30f);
        rootTable.add(soundEffect).left().pad(pad).height(30f).row();


        rootTable.add(new Label(Main.getLanguage().brightness, customLabel)).right().pad(pad);
        rootTable.add(brightness).left().width(300f).pad(pad).row();


        rootTable.add(new Label(Main.getLanguage().language, customLabel)).right().pad(pad);
        rootTable.add(languages).left().width(200f).pad(3*pad).row();

        jumpBtn = new TextButton(Input.Keys.toString(KeyController.JUMP), btnStyle);
        attackBtn = new TextButton(Input.Keys.toString(KeyController.ATTACK), btnStyle);
        dashBtn = new TextButton(Input.Keys.toString(KeyController.DASH), btnStyle);
        fireballBtn = new TextButton(Input.Keys.toString(KeyController.FIREBALL), btnStyle);
        screamBtn = new TextButton(Input.Keys.toString(KeyController.SCREAM), btnStyle);
        inventoryBtn = new TextButton(Input.Keys.toString(KeyController.INVENTORY), btnStyle);
        interactBtn = new TextButton(Input.Keys.toString(KeyController.INTERACT), btnStyle);
        focusBtn = new TextButton(Input.Keys.toString(KeyController.FOCUS), btnStyle);
        Table controlsTable = new Table();


        controlsTable.add(new Label(Main.getLanguage().getJump(), customLabel)).padRight(10f);
        controlsTable.add(jumpBtn).width(120f).padBottom(10f).padRight(40f);
        controlsTable.add(new Label(Main.getLanguage().getFireball(), customLabel)).padRight(10f);
        controlsTable.add(fireballBtn).width(120f).padBottom(10f).row();


        controlsTable.add(new Label(Main.getLanguage().getAttack(), customLabel)).padRight(100f);
        controlsTable.add(attackBtn).width(120f).padBottom(10f).padRight(70f);
        controlsTable.add(new Label(Main.getLanguage().getScream(), customLabel)).padRight(100f);
        controlsTable.add(screamBtn).width(120f).padBottom(10f).row();


        controlsTable.add(new Label(Main.getLanguage().getDash(), customLabel)).padRight(100f);
        controlsTable.add(dashBtn).width(120f).padBottom(10f).padRight(70f);
        controlsTable.add(new Label(Main.getLanguage().getInteract(), customLabel)).padRight(100f);
        controlsTable.add(interactBtn).width(120f).padBottom(10f).row();


        controlsTable.add(new Label(Main.getLanguage().getFocusHeal(), customLabel)).padRight(100f);
        controlsTable.add(focusBtn).width(120f).padBottom(10f).padRight(70f);
        controlsTable.add(new Label(Main.getLanguage().getInventory(), customLabel)).padRight(100f);
        controlsTable.add(inventoryBtn).width(120f).padBottom(10f).row();


        rootTable.add(controlsTable).colspan(2).padTop(20f).row();


        Table resetTable = new Table();
        resetTable.add(resetSound).padRight(50f);
        resetTable.add(resetController);


        rootTable.add(resetTable).colspan(2).padTop(30f).row();


        rootTable.add(back).colspan(2).padTop(40f);

        stage.addActor(rootTable);
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
        controller.handleSettingsMenuButtons();
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

    }
}
