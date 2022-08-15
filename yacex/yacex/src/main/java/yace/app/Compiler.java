package yace.app;

import yace.MismatchException;
import yace.io.Path;

import java.io.IOException;
import java.util.Optional;

/**
 * The main entry point of the application.
 */
public class Compiler extends Application {

    /**
     * The application will run by reading the content
     * within the source file and producing output in the target file.
     *
     * @param source The source/input file.
     * @param target The target/output file.
     */
    public Compiler(Path source, Path target) {
        super(source, target);
    }

    @Override
    protected Optional<String> handle(Input input) {
        var actualName = input.actualName;
        var expectedFullName = input.source.getName();
        var separator = expectedFullName.indexOf('.');
        var expectedName = expectedFullName.substring(0, separator);
        if (!expectedName.equals(actualName)) {
            throw new MismatchException(expectedName, actualName, "Java classes require the same file name as the top-level class.");
        }

        writeOutput();
        return Optional.empty();
    }

    private void writeOutput() {
        try {
            target.createAsFile();
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }
}
