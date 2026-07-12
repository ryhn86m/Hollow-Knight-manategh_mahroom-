package MyGame.view.renderers.itemsRenderer;
import com.badlogic.gdx.math.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolidBlock {
    private Rectangle bounds;
    private boolean Deadly;
    private boolean Crystal;
    private boolean isNotForKnight;
    public SolidBlock(float x , float y , float width , float height, boolean isDeadly, boolean isCrystal,boolean isNotForKnight){
        this.bounds = new Rectangle(x,y,width,height);
        this.Deadly = isDeadly;
        this.Crystal=isCrystal;
        this.isNotForKnight = isNotForKnight;
    }
}
