package yace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Provides convenience implementations for gateways using the {@link Path} system.
 */
public abstract class PathGateway implements Gateway<Path> {
    @Override
    public Path write(Module module, String extension) throws IOException {
        var package_ = module.streamPackage().reduce(computeRoot(), Path::resolve, (current, next) -> next);
        var name = module.computeName();
        var path = package_.resolve(name + "." + extension);

        var parent = path.getParent();
        if(!Files.exists(parent)) {
            Files.createDirectories(parent);
        }
        Files.createFile(path);
        return path;
    }

    /**
     * Returns the top-level directory for the gateway.
     * @return The top-level directory.
     */
    protected abstract Path computeRoot();
}
