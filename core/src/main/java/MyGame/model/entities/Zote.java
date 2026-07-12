package MyGame.model.entities;

import MyGame.Main;
import MyGame.model.enums.ZoteState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Zote {
    private float x, y;
    private float width = 500, height =400;
    private float stateTime = 0;
    private boolean hasFinishedMainDialogue = false;
    private int currentDialogueLine = 0;
    private ZoteState currentState = ZoteState.Idle;
    private String[] mainDialogues;
    private String[] precepts;


    public Zote(float startX, float startY) {
        this.x = startX;
        this.y = startY;
    }
}
