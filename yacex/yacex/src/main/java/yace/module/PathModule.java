package yace.module;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Represents a path which has code.
 */
public class PathModule implements Module {
    private final Path parent;
    private final Path child;

    public PathModule(Path parent, Path child) {
        this.parent = parent;
        this.child = child;
    }

    /**
     * Constructs a new PathModule from a working directory and a path.
     *
     * @param parent The working directory of the module.
     * @param child    The location of the module.
     */
    public static Module createFromAbsolute(Path parent, Path child) {
        return new PathModule(parent, child);
    }

    private Path inResolvedForm() {
        return parent.resolve(child);
    }

    @Override
    public Stream<String> streamPackage() {
        var value = parent.relativize(child);
        return IntStream.range(0, value.getNameCount() - 1)
                .mapToObj(value::getName)
                .map(Path::toString);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathModule that = (PathModule) o;
        return Objects.equals(parent, that.parent) && Objects.equals(child, that.child);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, child);
    }

    @Override
    public Module resolveSibling(String name) {
        return new PathModule(parent, Paths.get(name));
    }

    @Override
    public String computeName() {
        var fileName = child.getFileName().toString();
        var extensionSeparator = fileName.indexOf('.');
        return fileName.substring(0, extensionSeparator);
    }

    @Override
    public String load() throws IOException {
        return Files.readString(inResolvedForm());
    }

    @Override
    public Module detach() {
        return new CollectionModule(computeName(), streamPackage().toArray(String[]::new));
    }

    @Override
    public Module store(String output) {
        return null;
    }
}
