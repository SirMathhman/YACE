package yace;

import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Represents a path which has code.
 */
public class PathModule implements Module {
    private final Path path;

    /**
     * The path to the file.
     *
     * @param path The path.
     */
    public PathModule(Path path) {
        this.path = path;
    }

    @Override
    public Stream<String> streamPackage() {
        return IntStream.range(0, path.getNameCount() - 1)
                .mapToObj(path::getName)
                .map(Path::toString);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathModule module = (PathModule) o;
        return Objects.equals(path, module.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }

    @Override
    public Module resolveSibling(String name) {
        return new PathModule(path.resolveSibling(name));
    }

    @Override
    public String computeName() {
        var fileName = path.getFileName().toString();
        var extensionSeparator = fileName.indexOf('.');
        return fileName.substring(0, extensionSeparator);
    }
}
