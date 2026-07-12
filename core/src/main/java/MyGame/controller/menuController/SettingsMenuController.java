package MyGame.controller.menuController;

import MyGame.Main;
import MyGame.controller.gameController.KeyController;
import MyGame.model.enums.Language;
import MyGame.view.GameScreen;
import MyGame.view.menus.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettingsMenuController extends MenusController {
    private SettingsMenu view;
    private GameScreen gameScreen;

    public SettingsMenuController() {
    }

    public SettingsMenuController(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }
    private void rebindKey(TextButton button, int actionId) {
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                button.setText("Press Key...");

                Gdx.input.setInputProcessor(new com.badlogic.gdx.InputAdapter() {
                    @Override
                    public boolean keyDown(int keycode) {
                        if(actionId == 1) KeyController.JUMP = keycode;
                        else if(actionId == 2) KeyController.ATTACK = keycode;
                        else if(actionId == 3) KeyController.DASH = keycode;
                        else if(actionId == 4) KeyController.FIREBALL = keycode;
                        else if(actionId == 5) KeyController.SCREAM = keycode;
                        else if(actionId == 6) KeyController.INVENTORY = keycode;
                        else if(actionId == 7) KeyController.INTERACT = keycode;
                        else if(actionId == 8) KeyController.FOCUS = keycode;


                        button.setText(Input.Keys.toString(keycode));
                        Gdx.input.setInputProcessor(view.getStage());
                        return true;
                    }
                });
            }
        });
    }
    public void handleSettingsMenuButtons() {
        if (view == null) return;
        addClickSoundToButtons(view.getStage().getRoot());
        view.getBack().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (gameScreen != null) {

                    gameScreen.getModel().setPaused(true);
                    Main.getMain().setScreen(gameScreen);
                    if (gameScreen.getPauseMenu() != null) {
                        gameScreen.getPauseMenu().refreshLanguage();
                    }
                    if (gameScreen.getInventoryMenu() != null) {
                        gameScreen.getInventoryMenu().refreshLanguage();
                    }
                } else {
                    Main.getMain().setScreen(new MainMenu(new MainMenuController(), null));
                }
            }
        });

        view.getMusic().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                boolean isChecked = view.getMusic().isChecked();
                Main.getMain().setMusicOn(isChecked);
                if (isChecked) {
                    if (!Main.getMain().getMusic().isPlaying()) {
                        Main.getMain().getMusic().play();
                    }
                } else {
                    Main.getMain().getMusic().stop();
                }
            }
        });

        view.getSoundEffect().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.getMain().setSfx(view.getSoundEffect().isChecked());
            }
        });


        view.getResetSound().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (view.getResetSound().isChecked()) {
                    Main.getMain().setSfx(true);
                    Main.getMain().setMusicOn(true);
                    Main.getMain().setMusicVolume(0.3f);

                    view.getMusicVolume().setValue(50);
                    view.getMusic().setChecked(true);
                    view.getSoundEffect().setChecked(true);

                    Main.getMain().getMusic().play();
                    view.getResetSound().setChecked(false);
                }
            }
        });


        view.getLanguages().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Language selected = (Language) view.getLanguages().getSelected();

                if (Main.getLanguage() != selected) {
                    Main.getMain().setLanguage(selected);

                    Main.getMain().setScreen(new SettingsMenu(new SettingsMenuController(gameScreen), null));
                }
            }
        });


        view.getMusicVolume().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                float volume = view.getMusicVolume().getValue() / 100f;
                Main.getMain().setMusicVolume(volume);
                if (Main.getMain().isMusicOn()) {
                    Main.getMain().getMusic().setVolume(volume);
                }
            }
        });


        view.getBrightness().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                float brightness = view.getBrightness().getValue() / 100f;
                Main.getMain().setBrightness(brightness);
            }
        });


        view.getResetController().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                KeyController.resetToDefault();
                view.getJumpBtn().setText(Input.Keys.toString(KeyController.JUMP));
                view.getAttackBtn().setText(Input.Keys.toString(KeyController.ATTACK));
                view.getDashBtn().setText(Input.Keys.toString(KeyController.DASH));
                view.getFireballBtn().setText(Input.Keys.toString(KeyController.FIREBALL));
                view.getScreamBtn().setText(Input.Keys.toString(KeyController.SCREAM));
                view.getInventoryBtn().setText(Input.Keys.toString(KeyController.INVENTORY));
                view.getInteractBtn().setText(Input.Keys.toString(KeyController.INTERACT));
                view.getFocusBtn().setText(Input.Keys.toString(KeyController.FOCUS));
            }
        });

        rebindKey(view.getJumpBtn(), 1);
        rebindKey(view.getAttackBtn(), 2);
        rebindKey(view.getDashBtn(), 3);
        rebindKey(view.getFireballBtn(), 4);
        rebindKey(view.getScreamBtn(), 5);
        rebindKey(view.getInventoryBtn(), 6);
        rebindKey(view.getInteractBtn(), 7);
        rebindKey(view.getFocusBtn(), 8);
    }
}
