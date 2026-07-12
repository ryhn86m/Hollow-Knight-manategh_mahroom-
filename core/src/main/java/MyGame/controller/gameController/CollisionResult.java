package MyGame.controller.gameController;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CollisionResult {
    private boolean grounded;
    private boolean hitCeiling;
    private boolean wallLeft;
    private boolean wallRight;
    private boolean deadly;
}
