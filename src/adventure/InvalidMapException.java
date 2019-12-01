package adventure;

public class InvalidMapException extends IllegalArgumentException {
    String line;

    public InvalidMapException(String line) {
        this.line = line;
    }

    @Override
    public String getMessage() {
        return String.format("%s is not a valid map line.", line);
    }
}
