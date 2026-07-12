package MyGame.view.renderers.enemyRenderer;

import MyGame.Main;
import MyGame.model.world.AssetManager;
import MyGame.model.entities.Knight;
import MyGame.model.entities.Zote;
import MyGame.view.renderers.itemsRenderer.DialogueBox;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;

public class ZoteRenderer {
    private Zote zote;
    public ZoteRenderer(Zote zote){
        this.zote = zote;
    }
    public void render(SpriteBatch batch, float delta, Knight knight, DialogueBox dialogueBox) {
        AssetManager assets = AssetManager.getAssetManager();
        TextureRegion currentFrame = null;
        switch (zote.getCurrentState()) {
            case Idle:
                currentFrame = assets.getIdleZote().getKeyFrame(zote.getStateTime(), true);
                break;
            case Fall:
                currentFrame = assets.getFallZote().getKeyFrame(zote.getStateTime(), false);
                break;
            case Attack:
                currentFrame = assets.getAttackZote().getKeyFrame(zote.getStateTime(), true);
                break;
            case Roll:
                currentFrame = assets.getRollZote().getKeyFrame(zote.getStateTime(), false);
                break;
            case Talk:
                currentFrame = assets.getTalkZote().getKeyFrame(zote.getStateTime(), true);
                break;
        }

        if (currentFrame != null) {
            batch.draw(currentFrame, zote.getX(), zote.getY(), zote.getWidth(), zote.getHeight());
        }
        if (knight != null && dialogueBox != null) {
            if (Math.abs(knight.getX() - zote.getX()) < 150 && !dialogueBox.isActive()) {
                BitmapFont font = Main.getMain().getFont();
                float oldScaleX = font.getScaleX();
                float oldScaleY = font.getScaleY();


                font.getData().setScale(1.0f);
                font.setColor(Color.WHITE);

                String promptText = Main.getLanguage().pressEnter;


                float textX = zote.getX() + (zote.getWidth() / 2f);
                float textY = zote.getY() + zote.getHeight() + 40f;


                font.draw(batch, promptText, textX, textY, 0, Align.center, false);


                font.getData().setScale(oldScaleX, oldScaleY);
            }
        }
    }
    }

