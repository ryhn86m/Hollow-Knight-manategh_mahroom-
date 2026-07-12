package MyGame;

import MyGame.controller.menuController.MainMenuController;
import MyGame.model.enums.EnvironmentType;
import MyGame.model.enums.Language;
import MyGame.model.enums.MusicTracks;
import MyGame.model.world.AssetManager;
import MyGame.service.DatabaseManager;
import MyGame.service.SaveData;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import MyGame.view.menus.MainMenu;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Main extends Game {
    private static Main main;
    private static SpriteBatch batch;
    private BitmapFont font;
    private BitmapFont font2;
    private BitmapFont chineseFont;
    private BitmapFont fontSmall;
    private BitmapFont chineseFontBig;
    private  ScreenViewport viewport;
    private OrthographicCamera Camera;
    private static Texture image;
    private MusicTracks musicTrack = MusicTracks.Enter;
    private boolean sfx = true;
    private boolean musicOn = true;
    private boolean autoReload;
    private Music music;;
    private Float musicVolume = 0.1f;
    private Float brightness = 1f;
    private Integer time = 2;
    private ShaderProgram shader;
    private Language language ;
    private Texture brightnessPixel;
    private ShapeRenderer shapeRenderer;
    private float fadeTimer = 0;
    private boolean isFading = false;
    private Music nextMusic;
    @Override
    public void create() {
        DatabaseManager.initializeDatabase();
        this.language = Language.ENGLISH;
        main=this;
     batch = new SpriteBatch();
     var generator = new FreeTypeFontGenerator(Gdx.files.internal("TrajanPro-Regular.ttf"));
     var fontParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
     fontParams.size = 40;
     fontParams.color= Color.WHITE;
        var generator2 = new FreeTypeFontGenerator(Gdx.files.internal("TrajanPro-Regular.ttf"));
        var fontParams2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParams2.size = 60;
        fontParams2.color= Color.WHITE;
     font = generator.generateFont(fontParams);
     font2 = generator2.generateFont(fontParams2);
        var chineseGenerator = new FreeTypeFontGenerator(
            Gdx.files.internal("chinesefont.ttf")
        );

        var chineseParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        chineseParams.size = 40;
        chineseParams.color = Color.WHITE;
        chineseParams.characters = FreeTypeFontGenerator.DEFAULT_CHARS +
            "首领功退出遊戲？退出遊戲切灵代码此续储設定指南成就退出遊戲開始是否關重音效控制返回樂量亮度語言新存檔槽繼續顯示作弊碼儲並進入選單已装备未灵魂捕手增加用骨钉击中敌人时获得的冲刺大师减少冷却间使你能更频繁地坚固力强化小骑士普通攻伤害快速劈砍幅提高缩短之聚集治疗所需沉打退远锋利影穿过造且自身不会受同距离虚空心升级法术将其解锁黑暗形态凹满只个护符诅咒为什么挡我路佐特一位声名显赫如果再敢就知道他们把武器叫做生命终结者现在让开有诺言兑现戒律永远赢得战斗输掉给带任何东西也教干脆参绝嘲笑愚甚至上级但当心并非没害处总保持休息冒险消耗体自我修复忘记过去痛苦沉迷于想点别事情吧五胜对手很吗没关系只需压倒败按键交谈菜单移动向右左跳跃凝长贴墙滑行下互话暂停物品栏仇嚎幽系统面具值以表飞或环境陷阱如尖刺损失格归零初地活每次后秒无容器这主要能源可收最多换房仍留恢整程静止完部分若前松则断发射水平弹恒响透撞到壁等障碍便散正方释放股爆异技原围内连三次适合适落传送场观模式紧急补必杀二四与单即魔范↓！？：。，！“”（）、；完成主要部分在5分至少7绝不";
        chineseFont = chineseGenerator.generateFont(chineseParams);


        var chineseGenerator2 = new FreeTypeFontGenerator(
            Gdx.files.internal("chinesefont.ttf")
        );

        var chineseParams2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        chineseParams2.size = 60;
        chineseParams2.color = Color.WHITE;
        chineseParams.characters = FreeTypeFontGenerator.DEFAULT_CHARS +
            "首领功退出遊戲？退出遊戲切灵代码此续储設定指南成就退出遊戲開始是否關重音效控制返回樂量亮度語言新存檔槽繼續顯示作弊碼儲並進入選單已装备未灵魂捕手增加用骨钉击中敌人时获得的冲刺大师减少冷却间使你能更频繁地坚固力强化小骑士普通攻伤害快速劈砍幅提高缩短之聚集治疗所需沉打退远锋利影穿过造且自身不会受同距离虚空心升级法术将其解锁黑暗形态凹满只个护符诅咒为什么挡我路佐特一位声名显赫如果再敢就知道他们把武器叫做生命终结者现在让开有诺言兑现戒律永远赢得战斗输掉给带任何东西也教干脆参绝嘲笑愚甚至上级但当心并非没害处总保持休息冒险消耗体自我修复忘记过去痛苦沉迷于想点别事情吧五胜对手很吗没关系只需压倒败按键交谈菜单移动向右左跳跃凝长贴墙滑行下互话暂停物品栏仇嚎幽系统面具值以表飞或环境陷阱如尖刺损失格归零初地活每次后秒无容器这主要能源可收最多换房仍留恢整程静止完部分若前松则断发射水平弹恒响透撞到壁等障碍便散正方释放股爆异技原围内连三次适合适落传送场观模式紧急补必杀二四与单即魔范↓！？：。，！“”（）、；完成主要部分在5分至少7绝不";

        chineseFontBig = chineseGenerator2.generateFont(chineseParams2);
     generator.dispose();
     generator2.dispose();
        chineseGenerator.dispose();
        chineseGenerator2.dispose();
     Main.getMain().playMusic();
        var smallGenerator = new FreeTypeFontGenerator(
            Gdx.files.internal("TrajanPro-Regular.ttf")
        );

        var smallParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        smallParams.size = 35;
        smallParams.color = Color.WHITE;

        fontSmall = smallGenerator.generateFont(smallParams);
        fontSmall.setColor(Color.WHITE);
        smallGenerator.dispose();
     Camera = new OrthographicCamera();
     viewport= new ScreenViewport(Camera);
     getMain().setScreen(new MainMenu(new MainMenuController(),null));
        Pixmap originalPixmap = new Pixmap(Gdx.files.internal("cursor.png"));
        Pixmap standardCursor = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        standardCursor.drawPixmap(originalPixmap,
            0, 0, originalPixmap.getWidth(), originalPixmap.getHeight(),
            0, 0, standardCursor.getWidth(), standardCursor.getHeight()
        );
        Cursor cursor = Gdx.graphics.newCursor(standardCursor, 0, 0);
        Gdx.graphics.setCursor(cursor);
        originalPixmap.dispose();
        standardCursor.dispose();
        Pixmap pixmap = new Pixmap(1,1,Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        brightnessPixel = new Texture(pixmap);
        pixmap.dispose();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        super.resize(width, height);
    }

    @Override
    public void render() {

        if(Gdx.input.isKeyPressed(Input.Keys.D)) Camera.translate(1,0,0);
        else if(Gdx.input.isKeyPressed(Input.Keys.W)) Camera.translate(0,1,0);
        else if(Gdx.input.isKeyPressed(Input.Keys.A)) Camera.translate(-1,0,0);
        else if(Gdx.input.isKeyPressed(Input.Keys.S)) Camera.translate(0,-1,0);
        Camera.update();
        batch.setProjectionMatrix(Camera.combined);
        shapeRenderer.setProjectionMatrix(Camera.combined);
        super.render();
        float currentBrightness = getBrightness();
        float darkness = 1f - currentBrightness;

        if (darkness > 0f) {
            batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.begin();
            batch.enableBlending();
            batch.setColor(0, 0, 0, darkness);
            batch.draw(brightnessPixel, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.setColor(Color.WHITE);

            batch.end();
        }
    }
    public void updateMusic(float delta) {
        if (isFading && music != null) {
            float newVolume = music.getVolume() - delta * 0.2f;

            if (newVolume <= 0f) {
                music.setVolume(0f);
                music.stop();
                music.dispose();


                music = nextMusic;
                music.setVolume(0f);
                music.play();
                isFading = false;
            } else {

                music.setVolume(newVolume);
            }
        } else if (music != null && music.getVolume() < musicVolume) {
            music.setVolume(Math.min(musicVolume, music.getVolume() + delta * 0.5f));
        }
    }

    public void changeMusic(MusicTracks newTrack) {
        if (musicTrack == newTrack) return;
        this.musicTrack = newTrack;
        this.nextMusic = Gdx.audio.newMusic(Gdx.files.internal(newTrack.getPath()));
        this.nextMusic.setLooping(true);
        this.isFading = true;
    }
    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        font.dispose();
        brightnessPixel.dispose();
        AssetManager.getAssetManager().dispose();
    }

    public static void setMain(Main main) {
        Main.main = main;
    }

    public void setMusicVolume(Float musicVolume) {
        this.musicVolume = musicVolume;
        this.music.setVolume(musicVolume);
    }

    public void setMusic(String music) {
        this.musicTrack = MusicTracks.findMusic(music);
        this.music.pause();
        this.music.stop();
        this.music.dispose();
        playMusic();
    }

    public void playMusic() {
        music = Gdx.audio.newMusic(Gdx.files.internal(this.musicTrack.getPath()));
        music.setLooping(true);
        music.setVolume(musicVolume);
        music.play();
    }
    public void setLanguage(String language) {
        this.language = Language.findLanguage(language);
    }

    public static Language getLanguage() {
        if (main == null) return Language.ENGLISH;
        return getMain().getLangMain();
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Language getLangMain() {
        return language;
    }

    public BitmapFont getFont() {
        if(language == Language.CHINESE)
            return chineseFont;

        return font;
    }
    public BitmapFont getFont2() {
        if(language == Language.CHINESE)
            return chineseFontBig;

        return font2;
    }
    public BitmapFont getFontSmall() {
        if (language == Language.CHINESE)
            return chineseFont;

        return fontSmall;
    }
    public Viewport getViewport() {
        return viewport;
    }
    public static Main getMain(){
        return main;
    }
}
