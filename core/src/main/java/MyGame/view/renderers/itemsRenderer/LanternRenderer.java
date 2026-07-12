package MyGame.view.renderers.itemsRenderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class LanternRenderer {
    private Texture glowTexture;
    private float time = 0;

    public LanternRenderer() {
        int size = 256;
        int center = size / 2;
        float radius = 120f;
        Pixmap pixmap = new Pixmap(size, size, Pixmap.Format.RGBA8888);

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                float dist = (float) Math.sqrt(Math.pow(x - center, 2) + Math.pow(y - center, 2));
                if (dist < radius) {
                    float intensity = 1f - (dist / radius);
                    intensity = (float) Math.pow(intensity, 2.5);
                    pixmap.setColor(new Color(1f, 0.85f, 0.5f, intensity * 0.7f));
                    pixmap.drawPixel(x, y);
                }
            }
        }
        glowTexture = new Texture(pixmap);
        pixmap.dispose();
    }

    public void updateTime(float delta) {
        time += delta;
    }

    public void render(SpriteBatch batch, float x, float y) {
        float alpha = 0.7f + 0.3f * MathUtils.sin(time * 4f);
        batch.setColor(1f, 1f, 1f, alpha);

        float glowSize = 180f;

        float drawX = x - (glowSize / 2f);
        float drawY = y - (glowSize / 2f);

        batch.draw(glowTexture, drawX, drawY, glowSize, glowSize);
        batch.setColor(Color.WHITE);
    }

    public void dispose() {
        if (glowTexture != null) {
            glowTexture.dispose();
        }
    }
}
