package adventure.room;

import adventure.chest.Achievement;
import adventure.chest.Treasure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MonsterRoom extends Room {
    private String monsterName;
    private int monsterStrength;
    private Random random = new Random(7);
    private boolean emptied = false;

    public static MonsterRoom create(String monsterName, String treasureLine) {
        return new MonsterRoom(treasures(treasureLine), monsterName);
    }

    void resetRandom(int newSeed) {
        random = new Random(newSeed);
    }

    public MonsterRoom(List<Treasure> treasures, String monsterName) {
        super(treasures);
        this.monsterName = monsterName;
        monsterStrength = random.nextInt(12);
    }

    @Override
    public Achievement openDoor() {
        if (emptied || getTreasures().isEmpty()) {
            return new Achievement("This room is empty.", new ArrayList<>());
        } else {
            int playerStrength = random.nextInt(12);
            boolean playerWon = playerStrength >= monsterStrength;
            if (playerWon) {
                emptied = true;
                StringBuilder sb = new StringBuilder();
                sb.append(String.format(" You defeated %s. You got:", monsterName));
                for (Treasure treasure : getTreasures()) {
                    sb.append(treasure);
                }
                return new Achievement(sb.toString(), new ArrayList<>(getTreasures()));
            } else {
                getTreasures().forEach(treasure -> treasure.half());
                return new Achievement(String.format("%s has won. Treasure values halved.", monsterName), new ArrayList<>());
            }

        }

    }
}
