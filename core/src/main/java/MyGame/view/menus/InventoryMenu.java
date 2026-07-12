package MyGame.view.menus;

import MyGame.Main;
import MyGame.model.world.AssetManager;
import MyGame.model.world.GameModel;
import MyGame.model.enums.CharmType;
import MyGame.model.world.Inventory;
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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryMenu implements Screen {
    private Stage stage;
    private TextButton back;
    private InventoryMenu view;
    private Inventory model;
    private BitmapFont font;
    private BitmapFont fontSmall;
    private final Label.LabelStyle customLabel;
    private final CheckBox status;
    private final  CheckBox.CheckBoxStyle checkStyle;
    private CharmType charmType;
    private Label errorLabel;
    private Label[] charmDescLabels = new Label[8];
    private CheckBox[] charmStatusBoxes = new CheckBox[8];
    private Image[] charmImagesArray = new Image[8];
    private Array<CrystalParticle> particles = new Array<>();
    private ShapeRenderer particleRenderer = new ShapeRenderer();

    public  InventoryMenu(){
        this.font = Main.getMain().getFontSmall();

        this.model = Inventory.getInstance();
         checkStyle = new CheckBox.CheckBoxStyle();
        checkStyle.font = this.font;
        checkStyle.fontColor = Color.GRAY;
        checkStyle.overFontColor = Color.BLACK;
        checkStyle.checkedFontColor = Color.GRAY;
        Texture offTex = new Texture(Gdx.files.internal("emptyBox.png"));
        checkStyle.checkboxOff = new TextureRegionDrawable(offTex);
        Texture onTex = new Texture(Gdx.files.internal("tickcheckbox.png"));
        checkStyle.checkboxOn = new TextureRegionDrawable(onTex);
        TextureRegionDrawable on = new TextureRegionDrawable(onTex);
        on.setMinSize(50, 50);

        TextureRegionDrawable off = new TextureRegionDrawable(offTex);
        off.setMinSize(50, 50);

        checkStyle.checkboxOn = on;
        checkStyle.checkboxOff = off;
        status = new CheckBox( Main.getLanguage().equiped, checkStyle);
        status.setChecked(false);
        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
        btnStyle.font = this.font;
        btnStyle.fontColor = Color.WHITE;
        btnStyle.overFontColor = Color.BLACK;
        btnStyle.checkedFontColor = Color.WHITE;
        this.back = new TextButton(Main.getLanguage().back,btnStyle);
        this.customLabel = new Label.LabelStyle();
        this.customLabel.font = this.font;
        this.customLabel.fontColor = Color.WHITE;
    }
    @Override
    public void show() {
        status.getImage().setScale(0.2f);
        stage = new Stage(Main.getMain().getViewport());

        Image BG = new Image (AssetManager.getAssetManager().getInventoryMenuBackground());
        BG.setFillParent(true);
        stage.addActor(BG);

        Label.LabelStyle errorStyle = new Label.LabelStyle(this.font, Color.RED);
        errorLabel = new Label(Main.getLanguage().error, errorStyle);
        errorLabel.setPosition(stage.getWidth() / 2f, stage.getHeight() - 200, Align.center);
        errorLabel.setVisible(false);
        stage.addActor(errorLabel);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();
        String[] charmDescriptions = {
            Main.getLanguage().soulCatcher,Main.getLanguage().dashMaster,Main.getLanguage().unbreakableStrength,
            Main.getLanguage().quickSlash,Main.getLanguage().quickFocus,Main.getLanguage().heavyBlow,
            Main.getLanguage().sharpShadow,Main.getLanguage().voidHeart
        };

        Texture[] charmImages = {AssetManager.getAssetManager().getSoulCatcher(),AssetManager.getAssetManager().getDashMaster(),
            AssetManager.getAssetManager().getUnbreakableStrength(),AssetManager.getAssetManager().getQuickSlash(),
            AssetManager.getAssetManager().getQuickFocus(),AssetManager.getAssetManager().getHeavyBlow(),
            AssetManager.getAssetManager().getSharpShadow(),AssetManager.getAssetManager().getVoidHeart()
        };
        String statusText = Main.getLanguage().equiped;


        for (int i = 0; i < 8; i++) {
            Table cellTable = new Table();

            Image charmImage = new Image((charmImages[i]));
            charmImage.setScale(2);
            //cellTable.add(charmImage).size(60, 60).padRight(10);


            Label descLabel = new Label(charmDescriptions[i], customLabel);
           // descLabel.setWrap(true);
            //cellTable.add(descLabel).width(220).padRight(10);


            final CheckBox status = new CheckBox(statusText, checkStyle);

            final CharmType currentCharmType;
            switch (i){
                case 0 -> currentCharmType = CharmType.SOUL_CATCHER;
                case 1 -> currentCharmType = CharmType.DASHMASTER;
                case 2 -> currentCharmType = CharmType.UNBREAKABLE_STRENGTH;
                case 3 -> currentCharmType = CharmType.QUICK_SLASH;
                case 4 -> currentCharmType = CharmType.QUICK_FOCUS;
                case 5 -> currentCharmType = CharmType.HEAVY_BLOW;
                case 6 -> currentCharmType = CharmType.SHARP_SHADOW;
                default -> currentCharmType = CharmType.VOID_HEART;
            }
            status.setChecked(model.isCharmEquipped(currentCharmType));
            charmDescLabels[i] = descLabel;
            charmStatusBoxes[i] = status;
            charmImagesArray[i] = charmImage;
            if (currentCharmType == CharmType.VOID_HEART && GameModel.getInstance().isVoidHeartLocked()) {
                charmImage.setColor(Color.BLACK);
                status.setTouchable(com.badlogic.gdx.scenes.scene2d.Touchable.disabled);
            }
            status.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                    if (status.isChecked()) {
                        boolean success = model.equipCharm(currentCharmType);
                        if (!success) {
                            status.setProgrammaticChangeEvents(false);
                            status.setChecked(false);
                            status.setProgrammaticChangeEvents(true);
                            showErrorMessage();
                        }
                    } else {
                        model.unequipCharm(currentCharmType);
                    }
                }
            });
            descLabel.setWrap(true);
            cellTable.add(charmImage).size(50, 50).padRight(50).left();
            cellTable.add(descLabel).width(650).padRight(10);
            cellTable.add(status);


            mainTable.add(cellTable).pad(15);

            if (i % 2 == 1) {
                mainTable.row();
            }
        }
        mainTable.row();
        mainTable.add(back).colspan(2).padTop(20);
        stage.addActor(mainTable);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                GameModel.getInstance().setInventoryOpen(false);
                GameModel.getInstance().setPaused(false);
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
    private void showErrorMessage() {
        errorLabel.setVisible(true);
        errorLabel.clearActions();
        errorLabel.addAction(Actions.sequence(
            Actions.alpha(1f),
            Actions.delay(2f),
            Actions.fadeOut(1f),
            Actions.visible(false)
        ));
    }

    public void refreshLanguage() {
        this.font = Main.getMain().getFontSmall();


        customLabel.font = this.font;
        checkStyle.font = this.font;
        back.getStyle().font = this.font;
        if (errorLabel != null && errorLabel.getStyle() != null) {
            errorLabel.getStyle().font = this.font;
        }


        String[] charmDescriptions = {
            Main.getLanguage().soulCatcher, Main.getLanguage().dashMaster, Main.getLanguage().unbreakableStrength,
            Main.getLanguage().quickSlash, Main.getLanguage().quickFocus, Main.getLanguage().heavyBlow,
            Main.getLanguage().sharpShadow, Main.getLanguage().voidHeart
        };


        for (int i = 0; i < 8; i++) {
            if (charmDescLabels[i] != null) {
                charmDescLabels[i].setText(charmDescriptions[i]);
            }
            if (charmStatusBoxes[i] != null) {
                charmStatusBoxes[i].setText(Main.getLanguage().equiped);
            }
        }


        if (back != null) {
            back.setText(Main.getLanguage().back);
        }
        if (errorLabel != null) {
            errorLabel.setText(Main.getLanguage().error);
        }
    }
    @Override
    public void render(float delta) {
        CharmType[] types = {CharmType.SOUL_CATCHER, CharmType.DASHMASTER, CharmType.UNBREAKABLE_STRENGTH,
            CharmType.QUICK_SLASH, CharmType.QUICK_FOCUS, CharmType.HEAVY_BLOW, CharmType.SHARP_SHADOW, CharmType.VOID_HEART};

        for (int i = 0; i < 8; i++) {
            if (charmStatusBoxes[i] != null) {
                boolean isEquipped = model.isCharmEquipped(types[i]);
                if (charmStatusBoxes[i].isChecked() != isEquipped) {
                    charmStatusBoxes[i].setProgrammaticChangeEvents(false);
                    charmStatusBoxes[i].setChecked(isEquipped);
                    charmStatusBoxes[i].setProgrammaticChangeEvents(true);
                }

                if (types[i] == CharmType.VOID_HEART && !GameModel.getInstance().isVoidHeartLocked()) {
                    if (charmImagesArray[i] != null) charmImagesArray[i].setColor(Color.WHITE);
                    charmStatusBoxes[i].setTouchable(com.badlogic.gdx.scenes.scene2d.Touchable.enabled);

                    if (charmDescLabels[i].getText().toString().equals("???")) {
                        charmDescLabels[i].setText(Main.getLanguage().voidHeart);
                        charmStatusBoxes[i].setText(Main.getLanguage().equiped);
                    }
                }
            }
        }
        stage.act(delta);
        stage.draw();
        updateParticles(delta);
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
    public void resize(int i, int i1) {

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
