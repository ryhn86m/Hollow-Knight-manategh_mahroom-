package MyGame.view.renderers.enemyRenderer;

import MyGame.model.world.AssetManager;
import MyGame.model.entities.HuskHornhead;
import MyGame.model.enums.HuskHornheadState;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HuskHornheadRenderer {
    private HuskHornhead model;
    private float stateTime = 0f;
    public HuskHornheadRenderer(HuskHornhead model){this.model = model;}
   public void render(SpriteBatch batch , float delta){
        stateTime += delta;
       if (model.getInvincibleTimer() > 0) {

           Texture frame = AssetManager.getAssetManager().getBeingAttacked();

           boolean flip = model.isFacingRight();
           float drawX = flip ? model.getX() + model.getWidth() : model.getX();
           float drawWidth = flip ? -model.getWidth() : model.getWidth();

           batch.draw(frame, drawX, model.getY()-30f, drawWidth*0.7f, model.getHeight()*0.7f);
           return;
       }
       Animation<TextureRegion> currentAnimation;
       boolean shouldLoop= true;
       switch (model.getCurrentState()) {
           case TURN:
               currentAnimation = AssetManager.getAssetManager().getTurnHusk();
               shouldLoop = false;
               break;
           case DEATH:
               currentAnimation = AssetManager.getAssetManager().getDeathHusk();
               shouldLoop  = false;
               break;
           case WALK:
               currentAnimation = AssetManager.getAssetManager().getWalkHusk();
               shouldLoop = true;
               break;
           case ATTACK:
               currentAnimation = AssetManager.getAssetManager().getAttackHusk();
               shouldLoop = true;
               break;
           case IDLE:
               currentAnimation = AssetManager.getAssetManager().getIdleHusk();
               shouldLoop = true;
               break;
           default:
               currentAnimation = AssetManager.getAssetManager().getWalkHusk();
               shouldLoop = true;
               break;
       }
       TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, shouldLoop);
       boolean flip = model.isFacingRight();
       float drawX = flip ? model.getX() + model.getWidth() : model.getX();
       float drawWidth = flip ? -model.getWidth() : model.getWidth();
       if(model.getCurrentState()!= HuskHornheadState.DEATH)
       batch.draw(currentFrame, drawX, model.getY(), drawWidth, model.getHeight());
       else
           batch.draw(currentFrame, drawX, model.getY(), drawWidth*0.3f, model.getHeight()*0.3f);
   }


}
