package yace;

/**
 * Simple Java package statement, e.g. <code>package yace;</code>
 */
public class PackageStatement implements Renderable {
    private final String name;
    private final Padding prefix;
    private final Padding infix;

    /**
     * Constructs a new instance of {@link PackageStatement}
     * which renders to: <pre>
     * {@code
     *  <prefix> "package" <infix> <name>
     * }
     * </pre>
     *
     * @param prefix The prefix/
     * @param infix The infix.
     * @param name The name.
     */
    public PackageStatement(Padding prefix, Padding infix, String name) {
        this.name = name;
        this.prefix = prefix;
        this.infix = infix;
    }

    @Override
    public String render() {
        return prefix.render() + "package" + infix.render() + name + ";";
    }
}
