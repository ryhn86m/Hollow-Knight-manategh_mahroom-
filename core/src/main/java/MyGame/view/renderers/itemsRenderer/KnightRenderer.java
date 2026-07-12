package MyGame.view.renderers.itemsRenderer;

import MyGame.model.world.AssetManager;
import MyGame.model.entities.Knight;
import MyGame.model.enums.CharmType;
import MyGame.model.enums.KnightState;
import MyGame.model.world.Inventory;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class KnightRenderer {
    private final Knight knight;
    private final AssetManager assets;

    public KnightRenderer(Knight knight){
        this.knight = knight;
        this.assets = AssetManager.getAssetManager();
    }
    public void render(SpriteBatch batch, float delta){
        knight.setStateTime(knight.getStateTime() + delta);
        TextureRegion frame = getCurrentFrame();


        if(knight.isFacingRight() && !frame.isFlipX()){
            frame.flip(true,false);
        }
        if(!knight.isFacingRight() && frame.isFlipX()){
            frame.flip(true,false);
        }

        if (knight.getInvincibleTimer() > 0) {
            if (knight.getInvincibleTimer() % 0.2f < 0.1f) {
                batch.setColor(1f, 1f, 1f, 0.4f);
            } else {
                batch.setColor(1f, 1f, 1f, 1f);
            }
        }


        if (knight.getState() == KnightState.WALL_SLIDE) {

            float slideWidth = knight.getWidth();
            float slideHeight = knight.getHeight();
            float drawX;
            if (knight.isFacingRight()) {
                frame.flip(true,false);
                drawX = knight.getX() - 150;
            } else {
                frame.flip(true,false);
                drawX = knight.getX() - 150;
            }

            batch.draw(frame, drawX, knight.getY(), slideWidth, slideHeight);
        } else {
            batch.draw(frame, knight.getX() - 150, knight.getY(), knight.getWidth(), knight.getHeight());
        }


        batch.setColor(1f, 1f, 1f, 1f);

        if (knight.getState() == KnightState.DASH) {
            TextureRegion effectFrame = assets.getDashEffect().getKeyFrame(knight.getStateTime(), false);

            if (knight.isFacingRight() && !effectFrame.isFlipX()) effectFrame.flip(true, false);
            if (!knight.isFacingRight() && effectFrame.isFlipX()) effectFrame.flip(true, false);

            batch.draw(effectFrame, knight.getX() - 100, knight.getY()-50, knight.getWidth(), knight.getHeight());
        }

        if(knight.getState() == KnightState.ATTACK){
            TextureRegion effectAttack = assets.getAttackEffect().getKeyFrame(knight.getStateTime(),false);
            if (knight.isFacingRight() && !effectAttack.isFlipX()) {effectAttack.flip(true, false);}
            if (!knight.isFacingRight() && effectAttack.isFlipX()) {effectAttack.flip(true, false);}
            float drawXAttack = knight.isFacingRight() ? (knight.getX() - 90) : (knight.getX() - 150);
            batch.draw(effectAttack, drawXAttack, knight.getY() - 50, knight.getWidth(), knight.getHeight());
        }        if(knight.getState() == KnightState.ATTACK_UP){
            TextureRegion effectAttack = assets.getAttackEffect().getKeyFrame(knight.getStateTime(), false);


            if (knight.isFacingRight() && !effectAttack.isFlipX()) { effectAttack.flip(true, false); }
            if (!knight.isFacingRight() && effectAttack.isFlipX()) { effectAttack.flip(true, false); }


            float drawXAttack = knight.isFacingRight() ? (knight.getX() - 90) : (knight.getX() - 150);
            float drawYAttack = knight.getY() - 10;

            float originX = knight.getWidth() / 2f;
            float originY = knight.getHeight() / 2f;
            float rotation = knight.isFacingRight() ? 90f : -90f;
            batch.draw(effectAttack, drawXAttack, drawYAttack, originX, originY, knight.getWidth(), knight.getHeight(), 1f, 1f, rotation);
        }        if(knight.getState() == KnightState.ATTACK_DOWN){
            TextureRegion effectAttack = assets.getAttackEffect().getKeyFrame(knight.getStateTime(), false);

            if (knight.isFacingRight() && !effectAttack.isFlipX()) { effectAttack.flip(true, false); }
            if (!knight.isFacingRight() && effectAttack.isFlipX()) { effectAttack.flip(true, false); }

            float drawXAttack = knight.isFacingRight() ? (knight.getX() - 90) : (knight.getX() - 150);
            float drawYAttack = knight.getY() - 90;


            float originX = knight.getWidth() / 2f;
            float originY = knight.getHeight() / 2f;

            float rotation = knight.isFacingRight() ? -90f : 90f;

            batch.draw(effectAttack, drawXAttack, drawYAttack, originX, originY, knight.getWidth(), knight.getHeight(), 1f, 1f, rotation);
        }
        if(knight.getState() == KnightState.FIREBALL){
            TextureRegion effectBlast = assets.getBlast().getKeyFrame(knight.getStateTime(),false);
            if (knight.isFacingRight() && !effectBlast.isFlipX()) {effectBlast.flip(true, false);}
            if (!knight.isFacingRight() && !effectBlast.isFlipX()) {effectBlast.flip(false, false);}
            float drawX = knight.isFacingRight() ? (knight.getX()) : (knight.getX() - 100);
            batch.draw(effectBlast, drawX, knight.getY() -110, knight.getWidth(), knight.getHeight()*2);

//            if(!Inventory.getInstance().isCharmEquipped(CharmType.VOID_HEART)){
//                TextureRegion effectSoulBall= assets.getSoulBall().getKeyFrame(knight.getStateTime(),false);
//                if (knight.isFacingRight() && !effectSoulBall.isFlipX()) {effectSoulBall.flip(false, false);}
//                if (!knight.isFacingRight() && !effectSoulBall.isFlipX()) {effectSoulBall.flip(true, false);}
//                float drawXSoul = knight.isFacingRight() ? (knight.getX() + 70) : (knight.getX() - 170);
//                batch.draw(effectSoulBall, drawXSoul, knight.getY() + 50, knight.getWidth()*2, knight.getHeight()*2);
//            }
//            else {
//                TextureRegion effectSoulBall= assets.getShadowBall().getKeyFrame(knight.getStateTime(),false);
//                if (knight.isFacingRight() && !effectSoulBall.isFlipX()) {effectSoulBall.flip(false, false);}
//                if (!knight.isFacingRight() && !effectSoulBall.isFlipX()) {effectSoulBall.flip(true, false);}
//                float drawXSoul = knight.isFacingRight() ? (knight.getX() + 70) : (knight.getX() - 170);
//                batch.draw(effectSoulBall, drawXSoul, knight.getY() + 50, knight.getWidth()*2, knight.getHeight()*2);
//            }
        }
//        if(knight.getState() == KnightState.SCREAM){
//            if(!Inventory.getInstance().isCharmEquipped(CharmType.VOID_HEART)){
//                TextureRegion effectScream= assets.getSoulScream().getKeyFrame(knight.getStateTime(),false);
//                float scale = 4.5f;
//                float effectWidth = knight.getWidth() * scale;
//                float effectHeight = knight.getHeight() * scale;
//                float drawX = knight.getX() + (knight.getWidth() / 2f) - (effectWidth / 2f);
//                float drawY = knight.getY() - (knight.getHeight() * 0.15f);
//                batch.draw(effectScream, drawX, drawY, effectWidth, effectHeight);
//            }
//            else {
//                TextureRegion effectScreamDark= assets.getShadowScream().getKeyFrame(knight.getStateTime(),false);
//                float scale = 4.5f;
//                float effectWidth = knight.getWidth() * scale;
//                float effectHeight = knight.getHeight() * scale;
//                float drawX = knight.getX() + (knight.getWidth() / 2f) - (effectWidth / 2f);
//                float drawY = knight.getY() - (knight.getHeight() * 0.15f);
//                batch.draw(effectScreamDark, drawX, drawY, effectWidth, effectHeight);
//            }
//        }

    }
    private TextureRegion getCurrentFrame(){
        return switch (knight.getState()) {
            case IDLE ->
                assets.getIdleAnimation().getKeyFrame(knight.getStateTime(), true);
            case FOCUS_START ->
                assets.getFocusAnimation().getKeyFrame(knight.getStateTime(), true);

            case HURT ->
                assets.getHurtAnimation().getKeyFrame(knight.getStateTime(), false);
            case RUN ->
                assets.getRunAnimation().getKeyFrame(knight.getStateTime(), true);

            case JUMP ->
                assets.getJumpAnimation().getKeyFrame(knight.getStateTime(), true);

            case DOUBLE_JUMP ->
                assets.getDoubleJumpAnimation().getKeyFrame(knight.getStateTime(), false);

            case DASH ->Inventory.getInstance().isCharmEquipped(CharmType.SHARP_SHADOW) ?
                assets.getShadowDash().getKeyFrame(knight.getStateTime(), false) :
                assets.getDashAnimation().getKeyFrame(knight.getStateTime(), false);


            case ATTACK ->
                assets.getAttackAltAnimation().getKeyFrame(knight.getStateTime(), false);

            case ATTACK_DOWN ->
                assets.getDownSlashAnimation().getKeyFrame(knight.getStateTime(), false);
            case ATTACK_UP ->
                assets.getUpSlashAnimation().getKeyFrame(knight.getStateTime(), false);
            case WALL_SLIDE ->
                assets.getWallSlideAnimation().getKeyFrame(knight.getStateTime(), true);

            case FALL ->
                assets.getFallAnimation().getKeyFrame(knight.getStateTime(), true);

            case DEAD ->
                assets.getDeathAnimation().getKeyFrame(knight.getStateTime(), false);
            case FIREBALL ->
                assets.getFireBallCast().getKeyFrame(knight.getStateTime(),false);
            case SCREAM ->
                assets.getLookUp().getKeyFrame(knight.getStateTime(),true);
            default ->
                assets.getIdleAnimation().getKeyFrame(knight.getStateTime(), true);
        };
    }
}
