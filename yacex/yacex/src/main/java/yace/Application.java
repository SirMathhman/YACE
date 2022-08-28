package yace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The main entry point for the program.
 */
public class Application {
    private final Path source;
    private final Path target;

    /**
     * Constructs the application with a given source and target file.
     * @param source The source file.
     * @param target The target file.
     */
    public Application(Path source, Path target) {
        this.source = source;
        this.target = target;
    }

    /**
     * Runs the application.
     * @throws ApplicationException If an error occurred.
     */
    void run() throws ApplicationException {
        String input;
        try {
            input = Files.readString(source);
        } catch (IOException e1) {
            throw new ApplicationException(e1);
        }

        if (input.isEmpty()) {
            throw new EmptyFileException();
        } else {
            try {
                Files.createFile(target);
            } catch (IOException e) {
                throw new ApplicationException(e);
            }
        }
    }
}
