package adventure.chest;

import java.util.List;

public class Achievement {
    private final String info;
    private final List<Treasure> treasures;

    public Achievement(String info, List<Treasure> treasures){

        this.info = info;
        this.treasures = treasures;
    }

    public String getInfo() {
        return info;
    }

    public List<Treasure> getTreasures() {
        return treasures;
    }
}
