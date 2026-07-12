package MyGame.model.enums;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import lombok.Getter;
import lombok.Setter;
@Getter
public enum Sfx {
        SoulGain2("Focus Health Charging.mp3"),
        Click("Click.mp3"),
        Focus("Focus.mp3"),
        SoulGain("SoulGain.mp3"),
        KnightDeath("KnightDeath.mp3"),
        EnemyDeath("EnemyDeath.mp3"),
        NailSlash("NailSlash.mp3"),
        KnightDamage("KnightDamage.mp3"),
        Dash("Dash.mp3"),
    Wall("Wall.mp3"),
    BreakWall("BreakWall.mp3"),
    ScreamCast("Scream.mp3"),
    FireBallCast("Hollow Shade Fireball.mp3"),
    Zote1("Zote 01.mp3"),
    Zote2("Zote 02.mp3"),
    Zote3("Zote 03.mp3"),
    Zote4("Zote 04.mp3"),
    Zote5("Zote 05.mp3"),
    Zote6("Zote 06.mp3"),
    Zote7("Zote 07.mp3"),
        EnemyDamage("EnemyDamage.mp3");
        private final String path;
    Sfx(String path) {
        this.path = path;
    }
    private Sound sound;
        private void init() {
            if (sound == null) {
                sound = Gdx.audio.newSound(Gdx.files.internal(path));
            }
        }
        public void play() {
            init();
            sound.play();
        }
    }

