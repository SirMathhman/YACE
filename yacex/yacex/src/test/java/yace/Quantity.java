package yace;

public class Quantity implements Node {
    public final String argument;
    public boolean hasOpen = true;
    public boolean hasClose = true;

    public Quantity(String argument) {
        this.argument = argument;
    }

    @Override
    public String render() {
        var openParentheses = this.hasOpen ? "(" : "";
        var closeParentheses = this.hasClose ? ")" : "";
        return openParentheses + this.argument + closeParentheses;
    }

    public void toggleClosingParentheses() {
        this.hasClose = !this.hasClose;
    }

    public void toggleOpenParentheses() {
        this.hasOpen = !this.hasOpen;
    }
}
