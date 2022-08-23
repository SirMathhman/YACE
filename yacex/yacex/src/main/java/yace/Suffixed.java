package yace;

public class Suffixed implements Renderable {
    private final String value;
    private final int spacing;

    public Suffixed(String value, int spacing) {
        this.value = value;
        this.spacing = spacing;
    }

    @Override
    public String render() {
        if(value.isEmpty()) return "";
        return value + new Spacing(spacing).render();
    }
}
