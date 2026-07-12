package MyGame.controller.enemyController;

import MyGame.Main;
import MyGame.model.entities.HuskHornhead;
import MyGame.model.entities.Knight;
import MyGame.model.entities.Zote;
import MyGame.model.enums.Sfx;
import MyGame.model.enums.ZoteState;
import MyGame.view.renderers.itemsRenderer.SolidBlock;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class ZoteController {
    private Zote zote;
    private float animationDuration = 0.5f;
    private final Sfx[] zoteVoices = {
        Sfx.Zote1, Sfx.Zote2, Sfx.Zote3, Sfx.Zote4, Sfx.Zote5, Sfx.Zote6, Sfx.Zote7
    };
    public ZoteController(Zote model) {
        this.zote = model;
    }
    public void playRandomVoice() {
        if (Main.getMain().isSfx()) {
            int index = MathUtils.random(0, zoteVoices.length - 1);
            zoteVoices[index].play();
        }
    }
    public void update(float delta, Knight knight) {
        zote.setStateTime(zote.getStateTime() + delta);

        switch (zote.getCurrentState()) {
            case Fall:
                if (zote.getStateTime() >= animationDuration) {
                    zote.setCurrentState(ZoteState.Attack);
                    zote.setStateTime(0);
                }
                break;

            case Attack:
                float speed = 100f;
                if (knight.getX() > zote.getX()) {
                    zote.setX(zote.getX() + speed * delta);
                } else {
                    zote.setX(zote.getX() - speed * delta);
                }
                if (zote.getStateTime() >= 5.0f) {
                    zote.setCurrentState(ZoteState.Idle);
                    zote.setStateTime(0);
                }
                break;

            case Roll:
                if (zote.getStateTime() >= animationDuration) {
                    zote.setCurrentState(ZoteState.Talk);
                    zote.setStateTime(0);
                }
                break;

            case Idle:
            case Talk:

                break;
        }
    }

    public String interact() {
        if (zote.getCurrentState() == ZoteState.Idle) {
            zote.setCurrentState(ZoteState.Roll);
            zote.setStateTime(0);
        }

        if (!zote.isHasFinishedMainDialogue()) {
            String text = zote.getMainDialogues()[zote.getCurrentDialogueLine()];
            zote.setCurrentDialogueLine(zote.getCurrentDialogueLine() + 1);
            if (zote.getCurrentDialogueLine() >= zote.getMainDialogues().length) {
                zote.setHasFinishedMainDialogue(true);
            }
            return text;
        } else {
            int randomIndex = com.badlogic.gdx.math.MathUtils.random(0, zote.getPrecepts().length - 1);
            return zote.getPrecepts()[randomIndex];
        }
    }

    public void endInteraction() {
        if (zote.getCurrentState() == ZoteState.Talk) {
            zote.setCurrentState(ZoteState.Idle);
            zote.setStateTime(0);
        }
    }

    public void takeHit() {
        zote.setCurrentState(ZoteState.Fall);
        zote.setStateTime(0);
    }
}
