package MyGame.controller.menuController;

import MyGame.Main;
import MyGame.view.menus.ExitMenu;
import MyGame.view.menus.MainMenu;
import com.badlogic.gdx.Gdx;

public class ExitMenuController extends MenusController {
    private ExitMenu view;

    public void setView(ExitMenu view) {
        this.view = view;
    }

    public void handleExitMenuButtons() {
        addClickSoundToButtons(view.getStage().getRoot());
        if (view != null) {
            if (view.getYes().isChecked()) {
                Main.getMain().getScreen().dispose();
                Gdx.app.exit();
            } else if (view.getNo().isChecked()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenu(new MainMenuController(), null));
            }
        }
    }
}


