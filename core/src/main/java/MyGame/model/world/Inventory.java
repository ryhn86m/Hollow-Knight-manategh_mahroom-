package MyGame.model.world;

import MyGame.model.enums.CharmType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Inventory {
    private final int maxNumCharm = 3;
    private List<CharmType> equippedCharms;
    private static Inventory instance;
    public Inventory() {
        equippedCharms = new ArrayList<>();
    }
    public static Inventory getInstance(){
        if(instance==null) instance = new Inventory();
        return instance;
    }

    public boolean equipCharm(CharmType charm) {
        if (equippedCharms.contains(charm)) return false;
        if (equippedCharms.size() < maxNumCharm) {
            equippedCharms.add(charm);
            return true;
        }
        return false;
    }

    public void unequipCharm(CharmType charm) {
        equippedCharms.remove(charm);
    }

    public boolean isCharmEquipped(CharmType charm) {
        return equippedCharms.contains(charm);
    }
}
