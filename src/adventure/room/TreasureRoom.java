package adventure.room;

import adventure.chest.Achievement;
import adventure.chest.Treasure;

import java.util.ArrayList;
import java.util.List;

public class TreasureRoom extends Room {
    private boolean emptied = false;

    public TreasureRoom(List<Treasure> treasures) {
        super(treasures);
    }

    public static TreasureRoom create(String line) {
        return new TreasureRoom(treasures(line));
    }


    @Override
    public Achievement openDoor() {
        if (emptied || getTreasures().isEmpty()) {
            return new Achievement("This room is empty.", new ArrayList<>());
        } else {
            emptied = true;
            return new Achievement(String.format("You've got %s", getTreasures().get(0)), new ArrayList<>(getTreasures()));
        }

    }
}
