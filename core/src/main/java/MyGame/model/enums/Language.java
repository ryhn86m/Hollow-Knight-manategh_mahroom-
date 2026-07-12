package MyGame.model.enums;

import MyGame.controller.gameController.KeyController;
import com.badlogic.gdx.Input;
import lombok.Getter;
import lombok.Setter;

import java.io.PrintWriter;

@Getter
public enum Language {
    ENGLISH("Settings","Guide","Achievements","Quit Game","Start Game","Yes","No","QUIT GAME?","On/Off"
    ,"Reset Audio","Reset Controls","Back","Music Volume:",
        "Music :","Sound Effects:","Brightness:","Language:","Start New Game","Save Slot 1","Save Slot 2","Save Slot 3","Save Slot 4","Start Game Menu"
    ,"Continue","Show Cheat Codes","Save Game & Quit","Enter Setting Menu","Equiped/Unequiped",
    "Soul Catcher: Increases the amount of Soul gained from striking enemies with the Nail.",
        "Dashmaster: Reduces the cooldown of your Dash, allowing you to dash more frequently.",

        "Unbreakable Strength: Strengthens the Knight, increasing the damage of normal Nail strikes.",

        "Quick Slash: Greatly increases your attack speed, reducing the cooldown between Nail strikes.",

        "Quick Focus: Increases Focus speed, shortening the time required to heal.",

        "Heavy Blow: Increases the knockback force of your attacks, pushing enemies further back.",

        "Sharp Shadow: Dash through enemies to deal damage without getting hurt. Also increases dash length by 20%.",

        "Void Heart: Upgrades your spells, increasing their damage by 50% and unlocking their dark forms.",
        "Notches Full! You can only equip 3 charms.",
        "Curse you! Why did you get in my way?",
        "I am Zote the Mighty, a knight of great renown. If you ever cross my path again, you'll find out why they call my weapon 'Life Ender'.",
        "Now step aside! I have a promise to fulfil.",
        "Precept One: 'Always Win Your Battles'. Losing a battle earns you nothing and teaches you nothing. Win your battles, or don't engage in them at all!",
        "Precept Two: 'Never Let Them Laugh at You'. Fools laugh at everything, even at their superiors. But beware, laughter isn't harmless!",
        "Precept Three: 'Always Be Rested'. Fighting and adventuring take their toll on your body. When you rest, your body strengthens and repairs itself.",
        "Precept Four: 'Forget Your Past'. The past is painful, and thinking about your past can only bring you misery. Think about something else instead.",
        "Precept Five: 'Strength Beats Strength'. Is your opponent strong? No matter! Simply overcome their strength with even more strength, and they will soon be defeated.",
    "Press E to Talk",
        "Guide Menu",
        "MOTION KEYS:\n" +
            "Move Right  ->\n" +
            "Move Left   <-\n" +
            "Jump        %s\n" +
            "Attack      %s\n" +
            "Dash        %s\n" +
            "Focus       hold %s\n" +
            "Wall Slide  %s + -> OR %s + <-\n" +
            "Pogo        hold Down Arrow + %s\n" +
            "Interact with zote  %s\n" +
            "Skip Dialogue Zote  Enter\n" +
            "Pause Menu  Esc\n" +
            "Inventory   %s\n" +
            "Vengeful Spirit   %s\n" +
            "Howling Wraiths   %s","\nHEALTH SYSTEM (MASKS): Taking damage removes one mask and grants 1s of invulnerability. Reaching zero health respawns you at the start.\n\n" +
        "SOUL VESSEL: Your magical energy. Fill it by striking enemies (Max: 99). Stored Soul persists across rooms.\n\n" +
        "FOCUS (HEALING): Hold 'A' for 1.5s while stationary to consume Soul and restore one Mask. Taking damage or releasing the key interrupts it without costing Soul.\n\n" +
        "VENGEFUL SPIRIT: Fires a horizontal projectile that ignores gravity. It pierces enemies but breaks on walls.\n\n" +
        "HOWLING WRAITHS: A stationary, upward magical explosion that deals three rapid hits. Perfect for targeting flying",
        "\nCHEAT CODES:\n" +
            "Boss Arena Teleport     Ctrl+B\n" +
            "Spectator Mode          Ctrl+S\n" +
            "Emergency Heal          Ctrl+H\n" +
            "Refill Soul Vessel      Ctrl+R\n" +
            "God Mode                Ctrl+G\n" +
            "Insta-Kill              Ctrl+K","JUMP:","ATTACK:","DASH:","FOCUS/HEAL:","FIREBALL:","SCREAM:","INTERACT:","INVENTORY:",
    "Completion","Finish the main story of the game.","Speedrun","Finish the game in under 5 minutes.",
        "True Hunter","Defeat at least 7 enemies.","Defeat False Knight","Defeat the boss.",
        "Pure Combat","Finish the game without using Focus to heal.","Soul Master","Fill your Soul Vessel to its maximum capacity.","Achievement Unlocked: ",
        "Play Again","Back To Main Menu","VICTORY!","Total Time:","Deaths:","Enemy Killed:"),

    CHINESE(    "設定", "指南", "成就", "退出遊戲", "開始遊戲",
        "是", "否", "退出遊戲？", "開/關", "重設音效", "重設控制", "返回", "音樂音量：", "音樂：", "音效：",
        "亮度：", "語言：","開始新遊戲",
        "存檔槽 1", "存檔槽 2", "存檔槽 3", "存檔槽 4","開始新遊戲","繼續遊戲", "顯示作弊碼","儲存並退出","進入設定選單","已装备/未装备",
        "灵魂捕手: 增加用骨钉击中敌人时获得的灵魂量。",
        "冲刺大师: 减少冲刺的冷却时间，使你能更频繁地冲刺。",

        "坚固力量: 强化小骑士，增加普通骨钉攻击的伤害。",

        "快速劈砍: 大幅提高攻击速度，缩短骨钉攻击之间的冷却时间。",

        "快速聚集: 提高聚集速度，缩短治疗所需的时间。",

        "沉重打击: 增加攻击的击退力，将敌人击退得更远。",

        "锋利之影: 冲刺穿过敌人造成伤害且自身不会受伤。同时将冲刺距离增加20%。",

        "虚空之心: 升级你的法术，将其伤害增加50%并解锁其黑暗形态。",
        "凹槽已满！你只能装备3个护符。",
        "诅咒你！你为什么挡我的路？",
        "我是强大的佐特，一位声名显赫的骑士。如果你再敢挡我的路，你就会知道为什么他们把我的武器叫做“生命终结者”。",
        "现在让开！我有一个诺言要兑现。",
        "戒律一：“永远赢得你的战斗”。输掉战斗不会给你带来任何东西，也不会教会你任何东西。要么赢得战斗，要么干脆不要参战！",
        "戒律二：“绝不让他们嘲笑你”。愚人嘲笑一切，甚至嘲笑他们的上级。但要当心，嘲笑并非没有害处！",
        "戒律三：“总是保持休息”。战斗和冒险会消耗你的身体。当你休息时，你的身体会增强并自我修复。",
        "戒律四：“忘记你的过去”。过去是痛苦的，沉迷于过去只会给你带来痛苦。想点别的事情吧。",
        "戒律五：“力量战胜力量”。你的对手很强吗？没关系！只需用更强大的力量去压倒他们的力量，他们很快就会被击败。","按 E 键交谈",
        "指南菜单",
        "移动按键：\n" +
            "向右移动      ->\n" +
            "向左移动      <-\n" +
            "跳跃          %s\n" +
            "攻击          %s\n" +
            "冲刺          %s\n" +
            "凝聚          长按 %s\n" +
            "贴墙滑行      %s + -> 或 %s + <-\n" +
            "下劈攻击      长按 ↓ + %s\n" +
            "与左特互动    %s\n" +
            "跳过对话      Enter\n" +
            "暂停菜单      Esc\n" +
            "物品栏菜单    %s\n" +
            "复仇之魂      %s\n" +
            "嚎叫幽魂      %s",

        "生命系统（面具）：骑士的生命值以面具表示。受到敌人、飞行物或环境陷阱（如尖刺）的伤害时，会损失一格面具。当生命归零时，你将会在初始地点重新复活。每次受到伤害后，将获得 1 秒的无敌时间。\n" +
            "灵魂容器：这是你的主要能量来源。使用骨钉攻击敌人即可收集灵魂。灵魂容器最多可储存 99 点灵魂，并且切换房间后仍会保留已储存的灵魂。\n" +
            "凝聚（治疗）：长按“A”键即可凝聚灵魂恢复生命。整个过程需保持静止持续 1.5 秒。成功完成后，会消耗部分灵魂并恢复一格面具。若过程中受到伤害或提前松开按键，则凝聚会被打断，不会消耗灵魂，也不会恢复生命。\n" +
            "复仇之魂：向当前面朝方向发射一道水平飞行的魔法弹。魔法弹以恒定速度飞行，不受重力影响，可穿透敌人造成伤害；撞到墙壁等地形障碍后便会消散。\n" +
            "嚎叫幽魂：在骑士正上方释放一股向上的魔法爆发。与飞行魔法不同，此技能会在原地短暂持续，并对范围内的敌人连续造成三次快速伤害。此技能特别适合攻击飞行中的敌人或正在下落的敌人。",

        "作弊代码：\n" +
            "传送至 首领 战场      Ctrl+B\n" +
            "观战模式              Ctrl+S\n" +
            "紧急治疗              Ctrl+H\n" +
            "补满灵魂容器          Ctrl+R\n" +
            "无敌模式              Ctrl+G\n" +
            "一击必杀              Ctrl+K",
                    "跳跃:","攻击:","冲刺:","凝聚/治疗:","复仇之魂:","嚎叫幽魂:","互动:","物品栏:","完成遊戲", "完成遊戲主要部分。", "快速完成", "在5分内完成遊戲。",
        "战斗大师", "击败至少7个敌人。", "击败骑士", "击败对手。",
        "绝不治疗", "不使用凝聚治疗完成遊戲。", "灵魂大师", "将灵魂容器加满。","成就已解锁",
        "再次游戏", "返回主菜单", "胜利！", "总时间：", "死亡次数：", "击败敌人:"

        );
    private String name;
    public final String Settings;
    public final String Guide;
    public final String Achievements;
    public final String QuitGame;
    public final String StartGame,AchievementUnlocked;
    public final String Yes;
    public final String No;
    public final String playAgain,backToMainMenu,victory,totalTime,Deaths,enemyKilled;
    private final String a1,desc1,a2,desc2,a3,desc3,a4,desc4,a5,desc5,a6,desc6;
    public final String Jump,Attack,Dash,FocusHeal,Fireball,Scream,Interact,Inventory;
    public final String back,error,CheatCodes;
    public final String dialogue1,dialogue2,dialogue3;
    public final String precept1,precept2,precept3,precept4,abilities,precept5;
    public final String quitGame,StartGameMenu,pressEnter,GuideMenu,MotionKey;
    public final  String soulCatcher,dashMaster,unbreakableStrength,quickSlash,quickFocus,heavyBlow,sharpShadow,voidHeart;
    public final String OnOff,equiped,continueBtn,enterSettingMenu,saveGameAndQuit,showCheatCodes,resetAudio,resetControls,musicVolume,music,soundEffect,brightness,language,startNewGame,currentGame1,currentGame2,currentGame3,currentGame4;
    Language(String settings, String guide, String achievements, String QuitGame, String startGame,String yes, String no,String quitGame, String onoff
    ,String resetAudio, String resetControls,String back,String musicVolume,String music,String soundEffect,String brightness,String language,
             String startNewGame,String currentGame1,String currentGame2,String currentGame3,String currentGame4,String startGameMenu,
             String continueBtn,String showCheatCodes,String saveGameAndQuit,String enterSettingMenu,String equiped,
             String soulCatcher,String dashMaster,String unbreakableStrength,String quickSlash,String quickFocus,String heavyBlow,String sharpShadow,String voidHeart
    ,String error,String dialogue1,String dialogue2,String dialogue3,String precept1,String precept2,
             String precept3,String precept4,String precept5,String pressEnter,String guideMenu,String motionKey,String abilities,String cheatCodes,
             String Jump,String Attack,String Dash,String FocusHeal,String Fireball,String Scream,String Interact,String Inventory,
             String a1,String desc1,String a2,String desc2,String a3,String desc3,String a4,String desc4,String a5,String desc5,String a6,String desc6,String AchievementUnlocked,
             String playAgain,String backToMainMenu,String victory,String totalTime,String Deaths,String enemyKilled) {
        this.Settings = settings;
        this.Guide = guide;
        this.Achievements = achievements;
        this.QuitGame = QuitGame;
        this.StartGame = startGame;
        this.Yes = yes;
        this.No = no;
        this.quitGame = quitGame;
        this.OnOff = onoff;
        this.resetAudio=resetAudio;
        this.resetControls = resetControls;
        this.back = back;
        this.musicVolume = musicVolume;
        this.music=music;
        this.soundEffect = soundEffect;
        this.brightness = brightness;
        this.language = language;
        this.startNewGame = startNewGame;
        this.currentGame1=currentGame1;
        this.currentGame2=currentGame2;
        this.currentGame3=currentGame3;
        this.currentGame4=currentGame4;
        this.StartGameMenu=startGameMenu;
        this.continueBtn = continueBtn;
        this.showCheatCodes = showCheatCodes;
        this.saveGameAndQuit = saveGameAndQuit;
        this.enterSettingMenu = enterSettingMenu;
        this.equiped= equiped;
        this.soulCatcher=soulCatcher;
        this.dashMaster=dashMaster;
        this.unbreakableStrength=unbreakableStrength;
        this.quickSlash=quickSlash;
        this.quickFocus=quickFocus;
        this.heavyBlow=heavyBlow;
        this.sharpShadow=sharpShadow;
        this.voidHeart=voidHeart;
        this.error = error;
        this.dialogue1 = dialogue1;
        this.dialogue2 = dialogue2;
        this.dialogue3 = dialogue3;
        this.precept1 = precept1;
        this.precept2 = precept2;
        this.precept3 = precept3;
        this.precept4 = precept4;
        this.precept5 = precept5;
        this.pressEnter = pressEnter;
        this.GuideMenu = guideMenu;
        this.MotionKey = motionKey;
        this.abilities = abilities;
        this.CheatCodes = cheatCodes;
        this.Jump=Jump;
        this.Attack = Attack;
        this.Dash=Dash;
        this.FocusHeal=FocusHeal;
        this.Fireball=Fireball;
        this.Scream= Scream;
        this.Interact= Interact;
        this.Inventory=Inventory;
        this.desc1 = desc1;
        this.desc2 = desc2;
        this.desc3 = desc3;
        this.desc4 = desc4;
        this.desc5 = desc5;
        this.desc6 = desc6;
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a4 = a4;
        this.a5 = a5;
        this.a6 = a6;
        this.AchievementUnlocked = AchievementUnlocked;
        this.playAgain=playAgain;
        this.backToMainMenu=backToMainMenu;
        this.victory=victory;
        this.totalTime=totalTime;
        this.Deaths=Deaths;
        this.enemyKilled=enemyKilled;
    }
    public static Language findLanguage(String language) {
        for (Language value : Language.values()) {
            if (value.getName().equals(language)) {
                return value;
            }
        }
        return null;
    }


}
