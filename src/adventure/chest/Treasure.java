package adventure.chest;

import java.util.Objects;

public class Treasure implements Comparable<Treasure> {
    private String name;
    private Integer value;

    public Treasure(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public void half() {
        this.value = this.value / 2;
    }

    public static Treasure create(String treasureString) {
        String[] treasureArray = treasureString.split(" ");
        return new Treasure(treasureArray[0], Integer.parseInt(treasureArray[1]));
    }

    @Override
    public int compareTo(Treasure o) {
//        if (value.compareTo(o.value) != 0) {
//            return value.compareTo(o.value);
//        } else {
//            return name.compareTo(o.name);
//        }
        return value.compareTo(o.value) != 0 ? value.compareTo(o.value) : name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return String.format("%s which is worth %d golds.", name, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Treasure)) return false;
        Treasure treasure = (Treasure) o;
        return Objects.equals(name, treasure.name) &&
                Objects.equals(value, treasure.value);
    }

    @Override
    public int hashCode() {
        return name.hashCode() ^ value;
    }
}
