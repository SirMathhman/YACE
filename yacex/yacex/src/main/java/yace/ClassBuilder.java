package yace;

public class ClassBuilder {
    private int prefix = 0;
    private int suffix = 0;
    private String name = "";

    public ClassBuilder setPrefix(int prefix) {
        this.prefix = prefix;
        return this;
    }

    public ClassBuilder setSuffix(int suffix) {
        this.suffix = suffix;
        return this;
    }

    public ClassNode build() {
        return new ClassNode(prefix, suffix, name);
    }

    public ClassBuilder setName(String name) {
        this.name = name;
        return this;
    }
}