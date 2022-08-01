package yace;

public class CreatePackage implements Node {
    public int leftNamePad = 0;
    public int rightNamePad = 0;

    public Quantity quantity;

    public CreatePackage(Quantity quantity) {
        this.quantity = quantity;
    }

    @Override
    public String render() {
        return " ".repeat(leftNamePad) + "createSource" + " ".repeat(rightNamePad) + this.quantity.render();
    }
}
