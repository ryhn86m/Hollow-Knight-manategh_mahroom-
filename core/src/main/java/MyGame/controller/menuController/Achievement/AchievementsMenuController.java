package MyGame.controller.menuController.Achievement;

import MyGame.Main;
import MyGame.controller.menuController.MenusController;
import MyGame.service.DatabaseManager;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AchievementsMenuController extends MenusController {
    private static AchievementsMenuController instance;

    private boolean completionUnlocked;
    private boolean speedrunUnlocked;
    private boolean trueHunterUnlocked ;
    private boolean defeatFalseKnightUnlocked;
    private boolean soulFoulUnlocked;
    private boolean pureCombatUnlocked;
    private List<AchievementObserver> observers = new ArrayList<>();
    private AchievementsMenuController() {
        this.completionUnlocked = DatabaseManager.isAchievementUnlocked("COMPLETION");
        this.speedrunUnlocked = DatabaseManager.isAchievementUnlocked("SPEEDRUN");
        this.trueHunterUnlocked = DatabaseManager.isAchievementUnlocked("TRUE_HUNTER");
        this.defeatFalseKnightUnlocked = DatabaseManager.isAchievementUnlocked("DEFEAT_FALSE_KNIGHT");
        this.soulFoulUnlocked = DatabaseManager.isAchievementUnlocked("SOUL_FULL");
        this.pureCombatUnlocked = DatabaseManager.isAchievementUnlocked("PURE_COMBAT");

    }

    public static AchievementsMenuController getInstance() {

        if (instance == null) {
            instance = new AchievementsMenuController();
        }
        return instance;
    }
    public void addObserver(AchievementObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers(String title, String text) {
        for (AchievementObserver observer : observers) {
            observer.onAchievementUnlocked(title, text);
        }
    }
    public void setDefeatFalseKnightUnlocked(boolean unlocked) {
        if (unlocked && !this.defeatFalseKnightUnlocked) {
            this.defeatFalseKnightUnlocked = true;
            DatabaseManager.saveAchievement("DEFEAT_FALSE_KNIGHT", true);
            notifyObservers(Main.getLanguage().getA4(), Main.getLanguage().getDesc4());
        }
    }

    public void setCompletionUnlocked(boolean unlocked) {
        if (unlocked && !this.completionUnlocked) {
            this.completionUnlocked = true;
            DatabaseManager.saveAchievement("COMPLETION", true);
            notifyObservers(Main.getLanguage().getA1(), Main.getLanguage().getDesc1());
        }
    }

    public void setTrueHunterUnlocked(boolean unlocked) {
        if (unlocked && !this.trueHunterUnlocked) {
            this.trueHunterUnlocked = true;
            DatabaseManager.saveAchievement("TRUE_HUNTER", true);
            notifyObservers(Main.getLanguage().getA3(), Main.getLanguage().getDesc3());
        }
    }

    public void setSpeedrunUnlocked(boolean unlocked) {
        if (unlocked && !this.speedrunUnlocked) {
            this.speedrunUnlocked = true;
            DatabaseManager.saveAchievement("SPEEDRUN", true);
            notifyObservers(Main.getLanguage().getA2(), Main.getLanguage().getDesc2());
        }
    }

    public void setPureCombatUnlocked(boolean unlocked) {
        if (unlocked && !this.pureCombatUnlocked) {
            this.pureCombatUnlocked = true;
            DatabaseManager.saveAchievement("PURE_COMBAT", true);
            notifyObservers(Main.getLanguage().getA5(), Main.getLanguage().getDesc5());
        }
    }
    public void setSoulFullUnlocked(boolean unlocked) {
        if (unlocked && !this.soulFoulUnlocked) {
            this.soulFoulUnlocked = true;
            DatabaseManager.saveAchievement("SOUL_FULL", true);
            notifyObservers(Main.getLanguage().getA6(), Main.getLanguage().getDesc6());
        }
    }
}
