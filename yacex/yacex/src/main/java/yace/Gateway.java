package yace;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * Represents an object which offers passage between code and an external I/O system.
 */
public interface Gateway {
    /**
     * Writes a module to this gateway.
     * @param module The module.
     * @throws IOException If this module could not be written.
     */
    void write(Module module) throws IOException;

    /**
     * Streams all the modules present.
     *
     * @return The modules.
     */
    Stream<Module> read() throws IOException;
}
