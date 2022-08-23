package yace;

public class ClassNode implements Renderable {
    private final int prefix;
    private final int suffix;

    public ClassNode(int prefix, int suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public ClassNode() {
        this(0, 0);
    }

    @Override
    public String render() {
        return new Spacing(getPrefix()).render() + " class" + new Spacing(getSuffix()).render();
    }

    public int getPrefix() {
        return prefix;
    }

    public int getSuffix() {
        return suffix;
    }
}
