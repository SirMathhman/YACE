package yace;

import java.nio.file.OpenOption;
import java.nio.file.Path;

/**
 * <p>
 * Represents an object that may be rendered to a string.
 * Contains a single method, {@link Renderable#render()},
 * which converts this object to a string representation.
 * </p>
 * <p>
 * Do note that this method is different from {@link Object#toString()}
 * </p>
 * <p>
 * The output of this method is to be used in other processing,
 * such as potentially writing it to a file using
 * {@link java.nio.file.Files#writeString(Path, CharSequence, OpenOption...)}.
 * Furthermore, the output of {@link #render()} may vary between statements of a similar usage,
 * but of different languages, e.g. the class declaration between C++ and Java is different,
 * and thus render to two different strings.
 * </p>
 * <p>
 * This is compared to usages of {@link Object#toString()},
 * which is mostly used for display and structural purposes.
 * E.g. the representation of <i>classes</i> as {@link Renderable}s in C++ and Java
 * may have a similar structure.
 * </p>
 */
public interface Renderable {
    /**
     * Renders this object to a string.
     *
     * @return The rendered string.
     */
    String render();
}
