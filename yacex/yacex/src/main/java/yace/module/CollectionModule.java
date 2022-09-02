package yace.module;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Implements Module using Collections.
 */
public class CollectionModule implements Module {
    private final String name;
    private final String[] package_;

    /**
     * Constructs a new module using a given name and package_.
     * The package_ is at the end to enable variable arguments.
     * @param name The name of the module.
     * @param package_ The package of the module. An empty package is permissible.
     */
    public CollectionModule(String name, String... package_) {
        this.name = name;
        this.package_ = package_;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectionModule that = (CollectionModule) o;
        return Objects.equals(name, that.name) && Arrays.equals(package_, that.package_);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name);
        result = 31 * result + Arrays.hashCode(package_);
        return result;
    }

    @Override
    public Stream<String> streamPackage() {
        return Arrays.stream(package_);
    }

    @Override
    public Module resolveSibling(String name) {
        return new CollectionModule(name, package_);
    }

    @Override
    public String computeName() {
        return name;
    }

    @Override
    public String read() {
        throw new UnsupportedOperationException("No content available.");
    }
}
