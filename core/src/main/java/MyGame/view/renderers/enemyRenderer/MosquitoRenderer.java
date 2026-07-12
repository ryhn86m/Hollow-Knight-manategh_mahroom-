package MyGame.view.renderers.enemyRenderer;

import MyGame.model.entities.Mosquito;
import MyGame.model.enums.MosquitoState;
import MyGame.model.world.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MosquitoRenderer {
    private Mosquito model;

    public MosquitoRenderer(Mosquito model) {
        this.model = model;
    }

    public void render(SpriteBatch batch, float delta) {
        boolean isFacingRight = model.isFacingRight();
        float drawX = isFacingRight ? model.getX() + model.getWidth() : model.getX();
        float drawWidth = isFacingRight ? -model.getWidth() : model.getWidth();


        if (model.getState() == MosquitoState.Death) {
            TextureRegion deathFrame = AssetManager.getAssetManager().getMosDeath().getKeyFrame(model.getStateTime(), false);

            batch.draw(deathFrame, drawX, model.getY(), drawWidth, model.getHeight());
            return;
        }

        Animation<TextureRegion> currentAnimation;
        boolean loop = true;

        switch (model.getState()) {
            case AttackAnticipate:
                currentAnimation = AssetManager.getAssetManager().getMosAttackAnticipate();
                loop = false;
                break;
            case Attack:
                currentAnimation = AssetManager.getAssetManager().getMosAttack();
                break;
            case Turn:
                currentAnimation = AssetManager.getAssetManager().getMosTurn();
                loop = false;
                break;
            case Idle:
            default:
                currentAnimation = AssetManager.getAssetManager().getMosIdle();
                break;
        }

        TextureRegion frame = currentAnimation.getKeyFrame(model.getStateTime(), loop);


        boolean isFacingRight2 = model.isFacingRight();
        float drawX2 = isFacingRight2 ? model.getX() + model.getWidth() : model.getX();
        float drawWidth2 = isFacingRight2? -model.getWidth() : model.getWidth();


        if (model.getInvincibleTimer() > 0) {
            batch.setColor(1, 0, 0, 0.8f);
        }

        batch.draw(frame, drawX2, model.getY(), drawWidth2, model.getHeight());

        batch.setColor(1, 1, 1, 1f);
    }
}
