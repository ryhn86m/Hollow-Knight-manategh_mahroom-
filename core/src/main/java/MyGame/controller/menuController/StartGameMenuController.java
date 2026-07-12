package MyGame.controller.menuController;
import MyGame.Main;
import MyGame.model.enums.EnvironmentType;
import MyGame.model.world.GameModel;
import MyGame.service.DatabaseManager;
import MyGame.service.SaveData;
import MyGame.service.SaveService;
import MyGame.view.GameScreen;
import MyGame.view.menus.MainMenu;
import MyGame.view.menus.StartGameMenu;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class StartGameMenuController extends MenusController {

    private StartGameMenu view;

    public void handleStartGameMenuButtons() {

        addClickSoundToButtons(view.getStage().getRoot());

        view.getBack().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenu(new MainMenuController(), null));
            }
        });

        view.getStartNewGame().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (GameModel.getInstance() != null)GameModel.getInstance().resetGameStats();
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

        view.getCurrentGame1().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SaveData save = SaveService.loadOrCreate(1);
                Main.getMain().setScreen(new GameScreen(save));
            }
        });

        view.getCurrentGame2().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SaveData save = SaveService.loadOrCreate(2);
                Main.getMain().setScreen(new GameScreen(save));
            }
        });

        view.getCurrentGame3().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SaveData save = SaveService.loadOrCreate(3);
                Main.getMain().setScreen(new GameScreen(save));
            }
        });

        view.getCurrentGame4().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SaveData save = SaveService.loadOrCreate(4);
                Main.getMain().setScreen(new GameScreen(save));
            }
        }); }

    public void setView(StartGameMenu view) {
        this.view = view;
        handleStartGameMenuButtons();
    }
}
