package yace;

public class ClassRenderer {
    private final String name;

    public ClassRenderer(String name) {
        this.name = name;
    }

    String renderClass() {
        return "class " + name + " {\n}";
    }
}
