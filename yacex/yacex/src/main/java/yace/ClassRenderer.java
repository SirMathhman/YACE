package yace;

public class ClassRenderer implements Renderable {
    private final int prefix;
    private final int suffix;

    public ClassRenderer(int prefix, int suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
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
