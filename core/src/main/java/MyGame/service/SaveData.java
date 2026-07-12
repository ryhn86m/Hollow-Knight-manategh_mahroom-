package MyGame.service;

import MyGame.model.enums.EnvironmentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveData {
    private int slot;
    private double playerX;
    private double playerY;
    private int hp;
    private int soul;
    private EnvironmentType environmentType;
    private int bossDefeated;
    private int deathCount;
    private int killCount;
    private float elapsedTime;


    private int soulCatcher;
    private int dashmaster;
    private int unbreakableStrength;
    private int quickSlash;
    private int quickFocus;
    private int heavyBlow;
    private int sharpShadow;
    private int voidHeart;

    private double bossX;
    private double bossY;
    private int bossHp;
    private int bossPhase2;
}
