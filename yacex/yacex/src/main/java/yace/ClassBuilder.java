package yace;

public class ClassBuilder {
    private int prefix = 0;
    private int infix = 0;
    private String name = "";
    private int suffix = 0;

    public ClassBuilder setPrefix(int prefix) {
        this.prefix = prefix;
        return this;
    }

    public ClassBuilder setInfix(int infix) {
        this.infix = infix;
        return this;
    }

    public ClassNode build() {
        return new ClassNode(prefix, infix, name, suffix);
    }

    public ClassBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ClassBuilder setSuffix(int spacing) {
        this.suffix = spacing;
        return this;
    }
}