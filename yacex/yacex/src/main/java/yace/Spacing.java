package yace;

public class Spacing {
    private final int length;

    public Spacing(int length) {
        this.length = length;
    }

    String render() {
        return " ".repeat(length);
    }
}
