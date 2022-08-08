package yace;

public class ClassRenderer {
    private final String name;
    public int classPrefix = 0;
    public int namePrefix = 1;
    public int nameSuffix = 0;

    public ClassRenderer(String name) {
        this.name = name;
    }

    String renderClass() {
        return " ".repeat(classPrefix) + "class " + " ".repeat(namePrefix) + name + " ".repeat(nameSuffix) + "{}";
    }
}
