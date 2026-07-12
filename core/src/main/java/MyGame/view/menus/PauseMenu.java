package MyGame.view.menus;

import MyGame.Main;
import MyGame.controller.menuController.PauseMenuController;
import MyGame.model.world.AssetManager;
import MyGame.view.renderers.itemsRenderer.CrystalParticle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import lombok.Getter;
@Getter
public class PauseMenu {
    private Stage stage;
    private PauseMenuController controller;
    private PauseMenu view;
    private BitmapFont font;
    private TextButton enterSettingMenu,showCheatCodes,continueBtn,saveAndQuitToMenu;
    private Array<CrystalParticle> particles = new Array<>();
    private ShapeRenderer particleRenderer = new ShapeRenderer();
    public PauseMenu(PauseMenuController controller, Viewport viewport){
        stage = new Stage(viewport);
        this.font = Main.getMain().getFont();
        this.controller = controller;
        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
        btnStyle.font = this.font;
        btnStyle.fontColor = Color.WHITE;
        btnStyle.overFontColor = Color.BLACK;
        btnStyle.checkedFontColor = Color.WHITE;

        this.continueBtn = new TextButton(Main.getLanguage().continueBtn,btnStyle);
        this.enterSettingMenu = new TextButton(Main.getLanguage().enterSettingMenu,btnStyle);
        this.showCheatCodes = new TextButton(Main.getLanguage().showCheatCodes,btnStyle);
        this.saveAndQuitToMenu = new TextButton(Main.getLanguage().saveGameAndQuit,btnStyle);

        Image img1 = new Image(AssetManager.getAssetManager().getPauseMenu1());
        Image img2 = new Image(AssetManager.getAssetManager().getPauseMenu2());
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.defaults().pad(15);
        table.add(img1).size(300,100).row();
        table.add(continueBtn).row();
        table.add(enterSettingMenu).row();
        table.add(showCheatCodes).row();
        table.add(saveAndQuitToMenu).row();
        table.add(img2).size(400,100).row();
        stage.addActor(table);
        stage.getRoot().setVisible(false);
        controller.setView(this);
        controller.handlePauseMenuButtons();
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
    public void draw(float delta) {
        stage.act(delta);
        stage.draw();
    }
    public void hide() {
        stage.getRoot().setVisible(false);
        stage.getRoot().setTouchable(Touchable.disabled);
    }

    public void show() {
        stage.getRoot().setVisible(true);
        stage.getRoot().setTouchable(Touchable.enabled);
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
    public void refreshLanguage() {
        this.font = Main.getMain().getFont();
        TextButton.TextButtonStyle btnStyle = continueBtn.getStyle();
        btnStyle.font = this.font;
        continueBtn.setStyle(btnStyle);
        enterSettingMenu.setStyle(btnStyle);
        saveAndQuitToMenu.setStyle(btnStyle);
        showCheatCodes.setStyle(btnStyle);
        continueBtn.setText(Main.getLanguage().getContinueBtn());
        enterSettingMenu.setText(Main.getLanguage().getEnterSettingMenu());
        saveAndQuitToMenu.setText(Main.getLanguage().getSaveGameAndQuit());
        showCheatCodes.setText(Main.getLanguage().getShowCheatCodes());
    }
}
