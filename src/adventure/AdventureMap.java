package adventure;

import adventure.chest.Achievement;
import adventure.chest.Treasure;
import adventure.room.MonsterRoom;
import adventure.room.Room;
import adventure.room.TreasureRoom;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdventureMap {
    List<Room> rooms;
    List<Treasure> treasures;
    int currentRoom;

    public AdventureMap() {
        this(new ArrayList<Room>() {{
            add(new TreasureRoom(new ArrayList<>()));
        }});
    }

    public AdventureMap(List<Room> rooms) {
        this.rooms = rooms;
        currentRoom = 0;
        treasures = new ArrayList<>();
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public String move(Integer roomNumber) {
        if (Math.abs(this.currentRoom - roomNumber) > 1) {
            return String.format("You tried to enter Room %s. You cannot do that.", roomNumber);
        }

        currentRoom = roomNumber;
        Room room = rooms.get(currentRoom);
        Achievement achievement = room.openDoor();
        treasures.addAll(achievement.getTreasures());
        return String.format("You tried to enter Room %s. %s", currentRoom, achievement.getInfo());
    }

    @Override
    public String toString() {
        return String.format("You are at Room %s.", currentRoom);
    }

    public List<Treasure> getHoard() {
        List<Treasure> treasures = new ArrayList<>(this.treasures);
//        Collections.sort(treasures);
//        treasures.sort((t1, t2) -> t1.compareTo(t2));
        treasures.sort(Treasure::compareTo);
        return treasures;
    }

    public static AdventureMap loadMap(String filePath) {
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            List<Room> rooms = stream
                    .map(line -> {
                        try {
                            if (line.length() == 0) {
                                throw new InvalidMapException("");
                            }
                            String[] splitLine = line.split(";");
                            if (splitLine.length == 0) {
                                throw new InvalidMapException(line);
                            }
                            if (splitLine.length == 1) {
                                return TreasureRoom.create(splitLine[0]);
                            } else {
                                return MonsterRoom.create(splitLine[0], splitLine[1]);
                            }
                        } catch (InvalidMapException e) {
                            throw new InvalidMapException(line);
                        }
                    })
                    .collect(Collectors.toList());
            return new AdventureMap(rooms);
        } catch (IOException e) {
            System.out.println("Could not open file!");
            return null;
        } catch (InvalidMapException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
