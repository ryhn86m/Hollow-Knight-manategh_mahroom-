package MyGame.view.renderers.itemsRenderer;

import MyGame.model.world.AssetManager;
import MyGame.model.entities.Knight;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class HUDRenderer {
    private enum MaskState { FULL, BREAKING, EMPTY }

    private MaskState[] maskStates;
    private float[] maskAnimTimers;
    private int lastHp;

    private Texture maskFull, maskEmpty;
    private TextureRegion[] fillAnimation;
    private TextureRegion[] soulAnimation;
    private TextureRegion[] soulAnimationFilling;
    private float stateTime = 0;

    private Knight knight;
    private SpriteBatch uiBatch;
    private OrthographicCamera uiCamera;


    public HUDRenderer(Knight knight) {
        this.knight = knight;
        this.uiBatch = new SpriteBatch();

        this.uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(false, 1280, 720);
        maskFull = AssetManager.getAssetManager().getMaskFull();
        maskEmpty = AssetManager.getAssetManager().getMaskEmpty();
        this.maskStates = new MaskState[5];
        this.maskAnimTimers = new float[5];
        for(int i=0; i<5; i++) {
            maskStates[i] = (i < knight.getHp()) ? MaskState.FULL : MaskState.EMPTY;
            maskAnimTimers[i] = 0f;
        }
        fillAnimation = new TextureRegion[5];
        fillAnimation = AssetManager.getAssetManager().getFillMask().getKeyFrames();
        soulAnimation = new TextureRegion[6];
        soulAnimation = AssetManager.getAssetManager().getSoulAnimation().getKeyFrames();
        soulAnimationFilling = new TextureRegion[19];
        soulAnimationFilling = AssetManager.getAssetManager().getSoulAnimationFilling().getKeyFrames();
    }

    public void render(float delta) {
        checkDamageEvent();
        stateTime += delta;
        uiBatch.setProjectionMatrix(uiCamera.combined);
        uiBatch.begin();

        drawSoulVessel();
        drawMasks(delta);

        uiBatch.end();
    }
    private void checkDamageEvent() {
        if (knight.getHp() < lastHp) {
            int indexToBreak = knight.getHp();
            if (indexToBreak >= 0 && indexToBreak < 5) {
                maskStates[indexToBreak] = MaskState.BREAKING;
                maskAnimTimers[indexToBreak] = 0f;
            }

        }
        else if (knight.getHp() > lastHp) {

            for (int i = 0; i < knight.getHp(); i++) {
                maskStates[i] = MaskState.FULL;
            }

            for (int i = knight.getHp(); i < 5; i++) {
                maskStates[i] = MaskState.EMPTY;
            }
        }
        lastHp = knight.getHp();
    }
    private void drawMasks(float delta) {
        float startX = 100;
        float startY = 580;
        float padding = 2;

        for (int i = 0; i < 5; i++) {
            float width = 50f;
            float xPos = startX + (i * (width + padding));
            float height = 100f;

            if (maskStates[i] == MaskState.FULL) {
                uiBatch.draw(maskFull, xPos, startY,width,height);
            }
            else if (maskStates[i] == MaskState.BREAKING) {
                maskAnimTimers[i] += delta;

                int frameIndex = (int) (maskAnimTimers[i] / 0.08f);
                if (frameIndex < 6) {
                    uiBatch.draw(fillAnimation[frameIndex], xPos, startY,width,height);
                } else {
                    maskStates[i] = MaskState.EMPTY;
                }
            }
            else {
                uiBatch.draw(maskEmpty, xPos, startY,width,height);
            }
        }
       }

    private void drawSoulVessel() {
        float currentSoul = knight.getSoul();
        float maxSoul = 99f;
        float soulX = -20;
        float soulY = 575;
        float fillOffsetX = 10;
        float fillOffsetY = 6;
        int frameIndex = (int) ((currentSoul / maxSoul) * 4);
        frameIndex = MathUtils.clamp(frameIndex, 0, 5);
        int frameIndexSoul = MathUtils.clamp((int)((currentSoul / maxSoul) * 18), 0, 18);
        uiBatch.draw(soulAnimation[frameIndex], soulX+30, soulY+30, 120, 100);
        uiBatch.draw(soulAnimationFilling[frameIndexSoul], soulX+10, soulY+fillOffsetY, 120, 130);
    }

    public void dispose() {
        uiBatch.dispose();
    }
}
