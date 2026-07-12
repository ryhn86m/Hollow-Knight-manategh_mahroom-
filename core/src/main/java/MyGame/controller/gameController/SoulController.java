package MyGame.controller.gameController;

import MyGame.Main;
import MyGame.controller.menuController.Achievement.AchievementsMenuController;
import MyGame.model.entities.Knight;
import MyGame.model.enums.CharmType;
import MyGame.model.enums.Sfx;
import MyGame.model.world.GameModel;
import MyGame.model.world.Inventory;

public class SoulController {
    public void addSoulFromAttack(Knight knight) {
        int soulGain = Inventory.getInstance().isCharmEquipped(CharmType.SOUL_CATCHER) ? 22 : 11;
        int newSoul = knight.getSoul() + soulGain;

        if (newSoul > knight.getMAX_SOUL()) {
            newSoul = knight.getMAX_SOUL();
            if (!GameModel.getInstance().isSoulFull()){
            GameModel.getInstance().setSoulFull(true);
                AchievementsMenuController.getInstance().setSoulFullUnlocked(true); }
        }
        else         {if (Main.getMain().isSfx())
            Sfx.SoulGain.play();}
        knight.setSoul(newSoul);
    }
}
