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

    private static void writeTarget(Path target) throws ApplicationException {
        try {
            Files.createFile(target);
        } catch (IOException e) {
            throw new ApplicationException(String.format("Failed to create target file '%s':", target), e);
        }
    }

    private static String readSource(Path source) throws ApplicationException {
        String input;
        try {
            input = Files.readString(source);
        } catch (IOException e) {
            throw new ApplicationException(String.format("FAiled to read source file '%s':", source), e);
        }
        return input;
    }

    void run() throws ApplicationException {
        for (Path source : sources) {
            if (!Files.exists(source)) throw new SourceDoesNotExistException(source);

            var input = readSource(source);
            if (input.isBlank()) {
                throw new SourceEmptyException(source);
            }

            var name = input.substring("class ".length(), input.indexOf('{') - 1);

            var fileName = source.getFileName().toString();
            var separator = fileName.indexOf('.');
            var fileNameWithoutExtension = fileName.substring(0, separator);

            if (!fileNameWithoutExtension.equals(name)) {
                throw new NameMismatchException(String.format("Expected class '%s' to be in file of '%s'.java, and not of '%s'.",
                        name, name, fileName));
            }

            var target = source.resolveSibling(fileNameWithoutExtension + ".mgs");
            writeTarget(target);
        }
    }
}
