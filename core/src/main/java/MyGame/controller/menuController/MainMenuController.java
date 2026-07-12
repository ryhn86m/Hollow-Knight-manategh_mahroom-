package MyGame.controller.menuController;

import MyGame.Main;
import MyGame.model.enums.MusicTracks;
import MyGame.view.menus.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainMenuController extends MenusController {

    private MainMenu view;
    private boolean listenersInitialized = false;

    public void setView(MainMenu view) {
        this.view = view;
        handleMainMenuButtons();
    }

    public void handleMainMenuButtons() {
        if (view == null || listenersInitialized) {
            return;
        }

        listenersInitialized = true;

        addClickSoundToButtons(view.getStage().getRoot());

        view.getSettings().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new SettingsMenu(new SettingsMenuController(), null));
            }
        });

        view.getGuide().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.getMain().getScreen().dispose();
                 Main.getMain().setScreen(
                    new GuideMenu()
                 );
            }
        });

        view.getAchievements().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                Main.getMain().getScreen().dispose();
                 Main.getMain().setScreen(
                    new AchievementsMenu());}
        });

        view.getStartGame().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new StartGameMenu(new StartGameMenuController()));
            }
        });

        view.getQuitGame().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new ExitMenu(new ExitMenuController(), null));
            }
        });
    }
}
