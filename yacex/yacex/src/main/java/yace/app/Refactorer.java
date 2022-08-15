package yace.app;

import yace.io.Path;

import java.io.IOException;
import java.util.Optional;

/**
 * The main entry point of the application.
 */
public class Refactorer extends Application {
    private final Renamer renamer;

    /**
     * The application will run by reading the content
     * within the source file and producing output in the target file.
     *
     * @param source  The source/input file.
     * @param target  The target/output file.
     * @param renamer The refactoring.
     */
    public Refactorer(Path source, Path target, Renamer renamer) {
        super(source, target);
        this.renamer = renamer;
    }

    @Override
    protected Optional<String> handle(Input input) throws IOException {
        return renamer.perform(input);
    }
}
