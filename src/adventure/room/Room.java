package adventure.room;

import adventure.InvalidMapException;
import adventure.chest.Treasure;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Room implements Openable {
    private List<Treasure> treasures;

    public Room(List<Treasure> treasures) {
        this.treasures = treasures;
    }

    public List<Treasure> getTreasures() {
        return treasures;
    }

    protected static List<String> treasureStrings(String s) {
        List<String> treasureStrings = Arrays.asList(s.split(","));
        if (treasureStrings.size() != 2) {
            throw new InvalidMapException("");
        }
        return treasureStrings;
    }

    protected static List<Treasure> treasures(String s) {
        List<String> treasureStrings = treasureStrings(s);
        List<Treasure> treasures = treasureStrings.stream().map(Treasure::create).collect(Collectors.toList());
        if (treasures.size() > 1) {
            throw new InvalidMapException("");
        }
        return treasures;
    }
}
