package MyGame.view.renderers.itemsRenderer;

import MyGame.model.entities.BreakableWall;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import java.util.Iterator;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TiledMapHelper {
    private TiledMap tiledMap;
    Array<Vector2> lampPositions = new Array<>();
    private Vector2 crawlerSpawn,huskSpawn2;
    private Vector2 huskSpawn,mosSpawn;
    private Vector2 crysSpawn,crysSpawn2,zoteSpawn,knightSpawn2,bossSpawn,knightSpawn3,crawlerSpawn2;
    private BreakableWall breakableWall,breakableWall2;
    private SolidBlock wall;
    private Rectangle secretCover,secretCover2;
    private Vector2 HeartSpawn;
    public TiledMap loadMap(String path){
        TmxMapLoader loader = new TmxMapLoader();
        this.tiledMap = loader.load(path);

        Iterator<String> it = tiledMap.getProperties().getKeys();

        while (it.hasNext()) {
            String key = it.next();
        }
        return this.tiledMap;
    }
    public Array<SolidBlock> getSolidRectangles(){
        Array<SolidBlock> solidBlocks = new Array<>();
        MapLayer layer = tiledMap.getLayers().get("logical");

        for(MapObject object : layer.getObjects()){
            if ("BreakableWall".equals(object.getName())) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                breakableWall = new BreakableWall(rect.x, rect.y, rect.width, rect.height);
                if (object.getProperties().containsKey("hp")) {
                    breakableWall.setHp(object.getProperties().get("hp", Integer.class));
                }
                continue;
            }
            if ("BreakableWall2".equals(object.getName())) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                breakableWall2 = new BreakableWall(rect.x, rect.y, rect.width, rect.height);
                if (object.getProperties().containsKey("hp")) {
                    breakableWall2.setHp(object.getProperties().get("hp", Integer.class));
                }
                continue;
            }

            if ("SecretCover".equals(object.getName())) {
                secretCover = ((RectangleMapObject) object).getRectangle();
                continue;
            }
            if ("SecretCover2".equals(object.getName())) {
                secretCover2 = ((RectangleMapObject) object).getRectangle();
                continue;
            }

//            if ("SpawnPointHeart".equals(object.getName())) {
//                float x = object.getProperties().get("x", Float.class);
//                float y = object.getProperties().get("y", Float.class);
//                HeartSpawn = new Vector2(x, y);
//                continue;
//            }
            HeartSpawn = new Vector2(8000f,16026.0f);
            if ("SpawnCrys".equals(object.getName())) {
                float x = object.getProperties().get("x", Float.class);
                float y = object.getProperties().get("y", Float.class);
                crysSpawn=new Vector2(x, y);
                continue;
            }
            if ("SpawnMosquito".equals(object.getName())) {
                float x = object.getProperties().get("x", Float.class);
                float y = object.getProperties().get("y", Float.class);
                mosSpawn=new Vector2(x, y);
                continue;
            }
            if ("SpawnCrys2".equals(object.getName())) {
                float x = object.getProperties().get("x", Float.class);
                float y = object.getProperties().get("y", Float.class);
                crysSpawn2=new Vector2(x, y);
                continue;
            }
            if ("SpawnKnight2".equals(object.getName())) {
                float x = object.getProperties().get("x", Float.class);
                float y = object.getProperties().get("y", Float.class);
                knightSpawn2=new Vector2(x, y);
                continue;
            }
//            if ("SpawnCrawler".equals(object.getName())) {
//                float x = object.getProperties().get("x", Float.class);
//                float y = object.getProperties().get("y", Float.class);
//                crawlerSpawn=new Vector2(x, y);
//                continue;
//            }
            crawlerSpawn = new Vector2(7114.8994f,16040.0f);
//            if ("SpawnCrawler2".equals(object.getName())) {
//                float x = object.getProperties().get("x", Float.class);
//                float y = object.getProperties().get("y", Float.class);
//                crawlerSpawn2=new Vector2(x, y);
//                continue;
//            }
            crawlerSpawn2= new Vector2(4746.5723f,17703.03f);
            huskSpawn = new Vector2(5550f,16083f);
//            if ("SpawnHusk".equals(object.getName())) {
//                float x = object.getProperties().get("x", Float.class);
//                float y = object.getProperties().get("y", Float.class);
//                huskSpawn=new Vector2(x, y);
//                continue;
//            }
            if ("SpawnHusk2".equals(object.getName())) {
                float x = object.getProperties().get("x", Float.class);
                float y = object.getProperties().get("y", Float.class);
                huskSpawn2=new Vector2(x, y);
                continue;
            }
            if ("SpawnZote".equals(object.getName())) {
                float x = object.getProperties().get("x", Float.class);
                float y = object.getProperties().get("y", Float.class);
                zoteSpawn=new Vector2(x, y);
                continue;
            }
            bossSpawn = new Vector2(16094.687f,15654.55f);
//            if ("SpawnBoss".equals(object.getName())) {
//
//                float x = object.getProperties().get("x", Float.class);
//                float y = object.getProperties().get("y", Float.class);
//                bossSpawn=new Vector2(x, y);
//                continue;
//            }
            if ("SpawnKnight3".equals(object.getName())) {
                float x = object.getProperties().get("x", Float.class);
                float y = object.getProperties().get("y", Float.class);
                knightSpawn3=new Vector2(x, y);
                continue;
            }
            if (object instanceof EllipseMapObject) {
                float x = ((EllipseMapObject) object).getEllipse().x;
                float y = ((EllipseMapObject) object).getEllipse().y;
                float w = ((EllipseMapObject) object).getEllipse().width;
                float h = ((EllipseMapObject) object).getEllipse().height;


                lampPositions.add(new Vector2(x + (w / 2f), y + (h / 2f)));
            }
            else if ("Lamp".equals(object.getName())) {
                float x = object.getProperties().get("x", Float.class);
                float y = object.getProperties().get("y", Float.class);

                float w = object.getProperties().containsKey("width") ? object.getProperties().get("width", Float.class) : 0;
                float h = object.getProperties().containsKey("height") ? object.getProperties().get("height", Float.class) : 0;


                lampPositions.add(new Vector2(x + (w / 2f), y + (h / 2f)));
            }
            else if(object instanceof RectangleMapObject){
                boolean isCrystal = false;
                if (object.getProperties().containsKey("crystal")) {
                    isCrystal = object.getProperties().get("crystal", Boolean.class);
                }
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                boolean isDeadly = false;
                if(object.getProperties().containsKey("deadly")){
                    isDeadly = object.getProperties().get("deadly",Boolean.class);
                }
                boolean isNotForKnight = false;
                if(object.getProperties().containsKey("isNotForKnight")){
                    isNotForKnight = object.getProperties().get("isNotForKnight",Boolean.class);
                }
                solidBlocks.add(new SolidBlock(rect.x,rect.y, rect.width, rect.height, isDeadly,isCrystal,isNotForKnight));
            }

        }
        return solidBlocks;
    }


}
