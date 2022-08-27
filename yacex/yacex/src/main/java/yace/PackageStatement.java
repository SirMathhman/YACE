package yace;

public class PackageStatement {
    private final int prefixLength;
    private final int infixLength;
    private final String name;

    public PackageStatement(int prefixLength, int infixLength, String name) {
        this.prefixLength = prefixLength;
        this.infixLength = infixLength;
        this.name = name;
    }

    String renderPackage() {
        return new Spacing(getPrefixLength()).render() + "package" + new Spacing(getInfixLength()).render() + getName() + ";";
    }

    public int getPrefixLength() {
        return prefixLength;
    }

    public int getInfixLength() {
        return infixLength;
    }

    public String getName() {
        return name;
    }
}
