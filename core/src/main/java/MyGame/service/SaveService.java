package MyGame.service;

import MyGame.model.enums.EnvironmentType;
import MyGame.view.renderers.environmentsRender.CrystalPeaksRenderer;
import com.badlogic.gdx.math.Vector2;

public class SaveService {
    public static boolean slotExists(int slot) {
        return DatabaseManager.getSave(slot) != null;
    }

    public static int getFirstEmptySlot() {
        for (int i = 1; i <= 4; i++) {
            if (!slotExists(i)) {
                return i;
            }
        }
        return -1;
    }

    public static SaveData loadOrCreate(int slot) {
        SaveData save = DatabaseManager.getSave(slot);
        if (save != null) {
            return save;
        }

        SaveData newSave = new SaveData();
        newSave.setSlot(slot);
        newSave.setHp(5);
        newSave.setSoul(0);
        newSave.setEnvironmentType(EnvironmentType.CRYSTAL_PEAKS);
        newSave.setBossHp(50);
        newSave.setElapsedTime(0f);

        Vector2 spawn = new CrystalPeaksRenderer().getPlayerSpawnPoint();
        newSave.setPlayerX(spawn.x);
        newSave.setPlayerY(spawn.y);

        DatabaseManager.saveGame(newSave);
        return newSave;
    }

    public static void save(int slot, SaveData save) {
        save.setSlot(slot);
        DatabaseManager.saveGame(save);
    }
}
