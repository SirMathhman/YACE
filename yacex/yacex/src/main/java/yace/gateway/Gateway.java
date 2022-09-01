package yace.gateway;

import yace.module.Module;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * Represents an object which offers passage between code and an external I/O system.
 */
public interface Gateway<T> {
    /**
     * Writes a module to this gateway.
     *
     * @param module    The module.
     * @param extension The extension.
     * @return The location of the created module.
     * @throws IOException If this module could not be written.
     */
    T write(Module module, String extension) throws IOException;

    /**
     * Streams all the modules present.
     *
     * @return The modules.
     */
    Stream<Module> read() throws IOException;
}
