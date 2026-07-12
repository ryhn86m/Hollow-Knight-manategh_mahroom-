package MyGame.controller.menuController;

import MyGame.Main;
import MyGame.model.world.GameModel;
import MyGame.model.enums.EnvironmentType;
import MyGame.service.DatabaseManager;
import MyGame.view.GameScreen;
import MyGame.view.menus.*;
import MyGame.view.renderers.environmentsRender.CrystalPeaksRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PauseMenuController extends MenusController{
    private PauseMenu view;
    private GameModel model;
    private ShapeRenderer shapeRenderer;
    private BitmapFont fontText;
    private float displayTimer = 0f;
    private final float DISPLAY_DURATION = 12.0f;
    private boolean listenersInitialized = false;
    private boolean showCheats = false;
    public void Render(float delta, SpriteBatch batch) {
        if (!showCheats) return;
            displayTimer += delta;
            float startX = 20f;
            float startY = 600f;
            float width = 350f;
            float height = 300f;
        BitmapFont currentFont = Main.getMain().getFont();
            currentFont.setColor(Color.GOLD);
       currentFont.draw(batch, Main.getMain().getLanguage().CheatCodes, startX + 15, startY + height - 20);

        if (displayTimer >= DISPLAY_DURATION) {
            showCheats = false;
        }
        }

    public PauseMenuController(GameModel model){
        this.model = model;
        this.shapeRenderer= new ShapeRenderer();
        this.fontText = Main.getMain().getFont();
    }
    public void handlePauseMenuButtons() {
        if (view == null || listenersInitialized) {
            return;
        }

        listenersInitialized = true;

        addClickSoundToButtons(view.getStage().getRoot());

        view.getEnterSettingMenu().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                    Main.getMain().setScreen(
                        new SettingsMenu(new SettingsMenuController(
                                (GameScreen) Main.getMain().getScreen()), null));
            }
        });


        view.getContinueBtn().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                model.setPaused(false);
                view.hide();
                showCheats = false;
            }
        });

        view.getShowCheatCodes().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showCheats = true;
                displayTimer = 0f;
            }
        });

        view.getSaveAndQuitToMenu().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameScreen gameScreen = (GameScreen) Main.getMain().getScreen();
                gameScreen.saveAndQuit();
                Main.getMain().setScreen(new MainMenu(new MainMenuController(), null));
            }

    }); }
   }
