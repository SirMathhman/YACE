package yace;

public class ClassBuilder {
    private int prefix = 0;
    private int suffix = 0;

    public ClassBuilder setPrefix(int prefix) {
        this.prefix = prefix;
        return this;
    }

    public ClassBuilder setSuffix(int suffix) {
        this.suffix = suffix;
        return this;
    }

    public ClassNode build() {
        return new ClassNode(prefix, suffix);
    }
}