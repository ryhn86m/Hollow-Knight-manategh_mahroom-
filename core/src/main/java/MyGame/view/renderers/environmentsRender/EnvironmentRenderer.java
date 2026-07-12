package MyGame.view.renderers.environmentsRender;

import MyGame.view.renderers.itemsRenderer.SolidBlock;
import MyGame.view.renderers.itemsRenderer.TiledMapHelper;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class EnvironmentRenderer {
    protected TiledMap map;
    protected TiledMapHelper helper;
    protected OrthogonalTiledMapRenderer renderer;
    protected Texture backgroundTexture;
    protected SpriteBatch backgroundBatch;
    public abstract void reset();
    public abstract void render(OrthographicCamera camera);
    public abstract void dispose();
    public abstract Array<SolidBlock> getSolidBlocks();
    public abstract void instaKillAll();
    public abstract void renderBackground(OrthographicCamera camera);
    public abstract void renderForeground(OrthographicCamera camera);
    public Vector2 getPlayerSpawnPoint() {
        if (map == null){
            return new Vector2(200f, 300f);}
        MapLayer spawnLayer = map.getLayers().get("logical");
        if(spawnLayer == null){
            return new Vector2(200,300);
        }


            MapObject spawnPoint = spawnLayer.getObjects().get("SpawnPlayer");
        if(spawnPoint == null){
            return new Vector2(200,300);
        }
                float spawnX = spawnPoint.getProperties().get("x", Float.class);
                float spawnY = spawnPoint.getProperties().get("y", Float.class);
                return new Vector2(spawnX, spawnY);

    }
}

