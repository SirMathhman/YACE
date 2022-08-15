package yace.app;

import yace.CompileException;
import yace.MismatchException;
import yace.io.File;
import yace.io.Path;

import java.io.IOException;
import java.util.Optional;

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
     *
     * @param replacement   The replacement of the class name.
     *                      If the replacement is empty, then the class
     *                      will not be renamed and the source file will be
     *                      compiled normally.
     * @param shouldPreview If a preview should be returned instead of writing the refactored file
     * @return The preview of the output.
     */
    Optional<String> run(Optional<String> replacement, boolean shouldPreview) {
        try {
            return source.existingAsFile().flatMap(source1 -> runInSource(source1, replacement, shouldPreview));
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    private Optional<String> runInSource(File source, Optional<String> replacement, boolean shouldPreview) {
        try {
            return compile(source, replacement.map(value -> new Rename(shouldPreview, value)));
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    private Optional<String> compile(File source, Optional<Rename> renamer) throws IOException {
        var input = source.readAsString();
        if (input.isEmpty()) {
            throw new CompileException("Input may not be empty for Java source files.");
        }

        var slice = input.substring("class ".length());
        var actualName = slice.substring(0, slice.indexOf('{')).strip();

        if (renamer.isPresent()) {
            return renamer.orElseThrow().perform(source, input, actualName);
        } else {
            return compile(source, actualName);
        }
    }

    private Optional<String> compile(File source, String actualName) {
        var expectedFullName = source.getName();
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
