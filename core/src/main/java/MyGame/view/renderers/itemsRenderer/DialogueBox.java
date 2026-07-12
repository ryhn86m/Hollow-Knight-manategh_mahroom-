package MyGame.view.renderers.itemsRenderer;

import MyGame.Main;
import MyGame.model.world.AssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DialogueBox {
    private String fullText = "";
    private String displayedText = "";
    private float textTimer = 0;
    private float charRevealSpeed = 0.04f;
    private int visibleChars = 0;
    private boolean isActive = false;
    private boolean isTypingFinished = false;
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;
    private Texture frameTexture;

    public DialogueBox() {
        font = Main.getMain().getFont();
        font.getData().setScale(1.3f);
        shapeRenderer = new ShapeRenderer();
        frameTexture = AssetManager.getAssetManager().getDialogue_box();
    }

    public void startDialogue(String text) {
        this.fullText = text;
        this.displayedText = "";
        this.textTimer = 0;
        this.visibleChars = 0;
        this.isActive = true;
        this.isTypingFinished = false;
    }

    public void update(float delta) {
        if (!isActive || isTypingFinished) return;
        textTimer += delta;
        if (textTimer >= charRevealSpeed) {
            textTimer = 0;
            if (visibleChars < fullText.length()) {
                visibleChars++;
                displayedText = fullText.substring(0, visibleChars);
            } else {
                isTypingFinished = true;
            }
        }
    }

    public void render(SpriteBatch batch, float screenWidth, float screenHeight) {
        if (!isActive) return;

        float boxWidth = 900f;
        float boxHeight = 400f;
        float boxX = (screenWidth - boxWidth) / 2;
        float boxY = 200f;

        boolean wasDrawing = batch.isDrawing();

        if (wasDrawing) {
            batch.end();
        }

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.8f);
        shapeRenderer.rect(boxX + 20, boxY + 30, boxWidth - 40, boxHeight - 60);
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
        if (!batch.isDrawing()) {
            batch.begin();
        }

        if (frameTexture != null) {
            batch.draw(frameTexture, boxX, boxY, boxWidth, boxHeight);
        }

        BitmapFont currentFont = Main.getMain().getFont();

        if (currentFont != null) {
            float oldScaleX = currentFont.getScaleX();
            float oldScaleY = currentFont.getScaleY();
            currentFont.getData().setScale(0.8f);
            currentFont.setColor(Color.WHITE);
            float textPaddingX = 40f;
            float textPaddingY = boxHeight - 150f;
            currentFont.draw(batch, displayedText, boxX + textPaddingX, boxY + textPaddingY, boxWidth - (textPaddingX * 2),
                Align.left, true);
            currentFont.getData().setScale(oldScaleX, oldScaleY);}


        if (!wasDrawing) {
            batch.end();
        }
    }

    public void closeDialogue() {
        isActive = false;
        displayedText = "";
    }

    public void skipTyping() {
        visibleChars = fullText.length();
        displayedText = fullText;
        isTypingFinished = true;
    }
}
