package MyGame.model.world;

import MyGame.controller.menuController.Achievement.AchievementsMenuController;
import MyGame.model.entities.HowlingWraiths;
import MyGame.model.entities.Knight;
import MyGame.model.entities.VengefulSpirit;
import java.util.List;
import java.util.ArrayList;

import MyGame.model.enums.KnightState;
import MyGame.view.renderers.environmentsRender.EnvironmentRenderer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameModel {
    @Getter
    private static GameModel instance;
    private boolean paused;
    private  boolean VoidHeartLocked = true;
    private Knight knight;
    private boolean inventoryOpen = false;
    private int hp = 5;
    private int soul = 0;
    private int deathCount = 0;
    private int enemiesKilled = 0;
    private float elapsedTime = 0f;
    private boolean godMode = false;
    private boolean noclipMode = false;
    private boolean soulFull = false;
    private boolean teleportToBoss = false;
    private boolean inZone2;
    private int currentSlot;
    private EnvironmentRenderer currentMapRenderer;
    private List<VengefulSpirit> fireballs;
    private List<HowlingWraiths> screams;
    private boolean bossDefeated = false;
    private float bossDefeatTimer = 0f;
    private boolean hasUsedFocus = false;

    public void update(float delta){
        elapsedTime += delta;
        if (bossDefeated) {
            bossDefeatTimer += delta;
        }
    }

    public void addDeath(){
        deathCount++;
    }

    public void addKill(){
        enemiesKilled++;
        if (enemiesKilled >= 7) {
            AchievementsMenuController.getInstance().setTrueHunterUnlocked(true);
           }

    }
    private GameModel(Knight knight){
        fireballs = new ArrayList<>();
        screams = new ArrayList<>();
        this.knight = knight;
    }

    public static GameModel getInstance(Knight knight){
        if (instance==null){
            instance = new GameModel(knight);
        }
        return instance;
    }
    public String getFormattedTime() {

        int totalSeconds = (int) elapsedTime;

        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }


    public void refillSoul() {
        this.knight.setSoul(99);
    }

    public void emergencyHeal() {
        int currentHp = knight.getHp();
        if (currentHp <= 1) {
            knight.setHp(knight.getHp()+1);
            knight.setDead(false);
            knight.setState(KnightState.IDLE);
        }
    }
    public void resetGameStats() {
        this.deathCount = 0;
        this.enemiesKilled = 0;
        this.elapsedTime = 0f;
        this.hp = 5;
        this.soul = 0;
        this.hasUsedFocus = false;
        this.bossDefeated = false;
        this.bossDefeatTimer = 0f;
    }

}
