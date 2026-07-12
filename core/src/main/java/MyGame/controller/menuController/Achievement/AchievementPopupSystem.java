package MyGame.controller.menuController.Achievement;

import MyGame.Main;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.LinkedList;
import java.util.Queue;

public class AchievementPopupSystem implements AchievementObserver{
    private class PopupData {
        String title, text;
        public PopupData(String t, String d) { title = t; text = d; }
    }
    private Queue<PopupData> popupQueue = new LinkedList<>();
    private PopupData currentPopup = null;

    private float displayTimer = 0f;
    private final float DISPLAY_DURATION = 3.0f;

    private ShapeRenderer shapeRenderer;
    private BitmapFont fontTitle;
    private BitmapFont fontText;
    public AchievementPopupSystem() {
        this.shapeRenderer = new ShapeRenderer();
        this.fontTitle = Main.getMain().getFont();
        this.fontText = Main.getMain().getFontSmall();
    }
    @Override
    public void onAchievementUnlocked(String title, String text) {
        popupQueue.add(new PopupData(title, text));
    }
    public void updateAndRender(float delta, SpriteBatch batch) {

        if (currentPopup == null && !popupQueue.isEmpty()) {
            currentPopup = popupQueue.poll();
            displayTimer = 0f;
        }

        if (currentPopup != null) {
            displayTimer += delta;
            float startX = 20f;
            float startY = 20f;
            float width = 400f;
            float height = 80f;
            batch.end();
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0.1f, 0.1f, 0.1f, 0.8f);
            shapeRenderer.rect(startX, startY, width, height);
            shapeRenderer.end();

            batch.begin();


            fontTitle.setColor(Color.GOLD);
            fontTitle.draw(batch,Main.getLanguage().AchievementUnlocked + currentPopup.title, startX + 10, startY + 65);

            fontText.setColor(Color.WHITE);
            fontText.draw(batch, currentPopup.text, startX + 10, startY + 20);

            if (displayTimer >= DISPLAY_DURATION) {
                currentPopup = null;
            }
        }
    }
    public void dispose() {
        shapeRenderer.dispose();
    }
}
