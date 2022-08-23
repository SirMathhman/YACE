package yace;

public class Spacing implements Renderable {
    private final int spacing;

    public Spacing(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public String render() {
        return " ".repeat(getSpacing());
    }

    public int getSpacing() {
        return spacing;
    }
}
