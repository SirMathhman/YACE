package yace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class Application {
    private final Set<Path> sources;

    public Application(Set<Path> sources) {
        this.sources = sources;
    }

    void run() throws ApplicationException {
        for (Path source : sources) {
            if (!Files.exists(source)) {
                throw new SourceDoesNotExistException(source);
            }

            var fileName = source.getFileName().toString();
            var separator = fileName.indexOf('.');
            var fileNameWithoutExtension = fileName.substring(0, separator);

            try {
                Files.createFile(source.resolveSibling(fileNameWithoutExtension + ".mgs"));
            } catch (IOException e) {
                throw new ApplicationException("Failed to create target file: ", e);
            }
        }
    }
}
