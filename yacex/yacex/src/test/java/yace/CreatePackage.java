package yace;

public class CreatePackage {
    private final String name;
    public int leftNamePad = 0;
    public int rightNamePad = 0;
    public boolean hasOpen = true;
    public boolean hasClose = true;

    public CreatePackage(String name) {
        this.name = name;
    }

    String render() {
        var openParentheses = hasOpen ? "(" : "";
        var closeParentheses = hasClose ? ")" : "";
        return " ".repeat(leftNamePad) + "createSource" + " ".repeat(rightNamePad) + openParentheses + "\"" + name + "\"" + closeParentheses;
    }

    public void toggleOpenParentheses() {
        hasOpen = !hasOpen;
    }

    public void toggleClosingParentheses() {
        hasClose = !hasClose;
    }
}
