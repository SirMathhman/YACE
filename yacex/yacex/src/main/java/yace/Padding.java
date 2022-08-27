package yace;

/**
 * Implements a simple {@link Renderable} in which a given length
 * creates a padding composed of spaces.
 * <pre>
 *     2 -> "  ",
 *     3 -> "   ",
 * </pre>
 */
public class Padding implements Renderable {
    private final int length;

    /**
     * Constructs new padding with a given length.
     * @param length The length of the padding.
     */
    public Padding(int length) {
        this.length = length;
    }

    @Override
    public String render() {
        return " ".repeat(length);
    }
}
