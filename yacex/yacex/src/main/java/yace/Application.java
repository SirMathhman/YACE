package yace;

import java.io.IOException;

/**
 * The main entry point of the application.
 */
public class Application {
    private final Path source;
    private final Path target;

    /**
     * The application will run by reading the content
     * within the source file and producing output in the target file.
     *
     * @param source The source/input file.
     * @param target The target/output file.
     */
    public Application(Path source, Path target) {
        this.source = source;
        this.target = target;
    }

    /**
     * Actually executes the application.
     */
    void run() {
        try {
            source.existingAsFile().ifPresent(this::runInFile);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    private void runInFile(File value) {
        try {
            var input = value.readAsString();
            if (input.isEmpty()) {
                throw new CompileException("Input may not be empty for Java source files.");
            }

            var expectedFullName = value.getName();
            var separator = expectedFullName.indexOf('.');
            var expectedName = expectedFullName.substring(0, separator);

            var slice = input.substring("class ".length());
            var actualName = slice.substring(0, slice.indexOf('{')).strip();

            if(!expectedName.equals(actualName)) {
                throw new MismatchException(expectedName, actualName, "Java classes require the same file name as the top-level class.");
            }

            writeOutput();
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    private void writeOutput() {
        try {
            target.createAsFile();
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }
}
