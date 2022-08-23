package yace;

public class ClassNode implements Renderable {
    private final int prefix;
    private final int infix;
    private final String name;
    private final int suffix;

    public ClassNode(int prefix, int infix, String name, int suffix) {
        this.prefix = prefix;
        this.infix = infix;
        this.name = name;
        this.suffix = suffix;
    }

    public ClassNode() {
        this(0, 0, "", 0);
    }

    @Override
    public String render() {
        return renderSpacing(prefix) + "class" +
               renderSpacing(infix) + name +
               renderSpacing(suffix);
    }

    private String renderSpacing(int spacing) {
        return new Spacing(spacing).render();
    }
}
