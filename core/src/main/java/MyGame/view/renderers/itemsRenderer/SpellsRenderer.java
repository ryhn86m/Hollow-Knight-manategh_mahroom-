package MyGame.view.renderers.itemsRenderer;

import MyGame.model.world.AssetManager;
import MyGame.model.world.GameModel;
import MyGame.model.entities.HowlingWraiths;
import MyGame.model.entities.Knight;
import MyGame.model.entities.VengefulSpirit;
import MyGame.model.enums.CharmType;
import MyGame.model.world.Inventory;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpellsRenderer {
public void SpellsRender(SpriteBatch batch, GameModel model,Knight knight){
    boolean hasVoidHeart = Inventory.getInstance().isCharmEquipped(CharmType.VOID_HEART);
    AssetManager assets = AssetManager.getAssetManager();

    for (VengefulSpirit fb : model.getFireballs()) {
        TextureRegion frame = hasVoidHeart ?
            assets.getShadowBall().getKeyFrame(fb.getStateTime(), true) :
            assets.getSoulBall().getKeyFrame(fb.getStateTime(), true);

        float drawX = fb.getX();
        float width = fb.isFacingRight() ? knight.getWidth() : -knight.getWidth();
        float height = knight.getHeight();
        batch.draw(frame, drawX, fb.getY(), width, height);
    }

    for (HowlingWraiths scream : model.getScreams()) {
        TextureRegion frame = hasVoidHeart ?
            assets.getShadowScream().getKeyFrame(scream.getStateTime(), false) :
            assets.getSoulScream().getKeyFrame(scream.getStateTime(), false);

        float scale = 1.3f;
        float effectWidth = knight.getWidth() * scale;
        float effectHeight = knight.getHeight() * scale;

        batch.draw(frame, scream.getX(), scream.getY(), effectWidth, effectHeight);
    }
}
}
