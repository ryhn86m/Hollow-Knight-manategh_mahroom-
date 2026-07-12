package MyGame.view.renderers.enemyRenderer;

import MyGame.model.world.AssetManager;
import MyGame.model.entities.CrystalCrawler;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CrystalCrawlerRenderer {
    private CrystalCrawler model;
    private float stateTime = 0;
public CrystalCrawlerRenderer(CrystalCrawler model){
    this.model = model;
}
    public void render(SpriteBatch batch, float delta) {
        stateTime += delta;

        Animation<TextureRegion> currentAnimation;
        boolean shouldLoop = true;
        switch (model.getCurrentState()) {
            case TURNING:
                currentAnimation = AssetManager.getAssetManager().getTurnCrystalCrawler();
                shouldLoop = false;
                break;
            case DEAD:
                currentAnimation = AssetManager.getAssetManager().getDeathCrystalCrawler();
                shouldLoop  = false;
                break;
            case WALKING:
            default:
                currentAnimation = AssetManager.getAssetManager().getWalkCrystalCrawler();
                shouldLoop = true;
                break;
        }

        TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, shouldLoop);
        boolean flip = model.isFacingRight();
        float drawX = flip ? model.getX() + model.getWidth() : model.getX();
        float drawWidth = flip ? -model.getWidth() : model.getWidth();

        batch.draw(currentFrame, drawX, model.getY(), drawWidth, model.getHeight());
    }

    }


