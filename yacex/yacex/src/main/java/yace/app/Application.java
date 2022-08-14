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
     * @param replacement The replacement of the class name.
     *                    If the replacement is empty, then the class
     *                    will not be renamed and the source file will be
     *                    compiled normally.
     */
    void run(Optional<String> replacement) {
        try {
            source.existingAsFile().ifPresent(source1 -> runInFile(source1, replacement));
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    private void runInFile(File source, Optional<String> replacement) {
        try {
            var input = source.readAsString();
            if (input.isEmpty()) {
                throw new CompileException("Input may not be empty for Java source files.");
            }
            
            var slice = input.substring("class ".length());
            var actualName = slice.substring(0, slice.indexOf('{')).strip();
            
            if (replacement.isPresent()) {
                source.writeAsString(input.replace(actualName, replacement.orElseThrow()));
            } else {
                var expectedFullName = source.getName();
                var separator = expectedFullName.indexOf('.');
                var expectedName = expectedFullName.substring(0, separator);
                if (!expectedName.equals(actualName)) {
                    throw new MismatchException(expectedName, actualName, "Java classes require the same file name as the top-level class.");
                }

                writeOutput();
            }
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
