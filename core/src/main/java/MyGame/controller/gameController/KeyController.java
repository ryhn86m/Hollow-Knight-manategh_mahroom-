package MyGame.controller.gameController;

import com.badlogic.gdx.Input;

public class KeyController {
    public static int JUMP = Input.Keys.Z;
    public static int ATTACK = Input.Keys.X;
    public static int DASH = Input.Keys.C;
    public static int FIREBALL = Input.Keys.F;
    public static int SCREAM = Input.Keys.S;
    public static int INVENTORY = Input.Keys.I;
    public static int INTERACT = Input.Keys.E;
    public static int FOCUS = Input.Keys.A;


    public static void resetToDefault() {
        JUMP = Input.Keys.Z;
        ATTACK = Input.Keys.X;
        DASH = Input.Keys.C;
        FIREBALL = Input.Keys.F;
        SCREAM = Input.Keys.S;
        INVENTORY = Input.Keys.I;
        INTERACT = Input.Keys.E;
        FOCUS = Input.Keys.A;
    }
}
