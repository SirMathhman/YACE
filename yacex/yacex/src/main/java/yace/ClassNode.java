package yace;

public class ClassNode implements Renderable {
    private final int prefix;
    private final int suffix;
    private final String name;

    public ClassNode(int prefix, int suffix, String name) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.name = name;
    }

    public ClassNode() {
        this(0, 0, "");
    }

    @Override
    public String render() {
        return new Spacing(prefix).render() + "class" + new Spacing(suffix).render() + name;
    }
}
