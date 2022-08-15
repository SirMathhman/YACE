package yace;

import yace.app.Compiler;
import yace.io.NIOPath;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

/**
 * The <b>actual</b> main entry point of the program.
 */
public class Main {
    public static void main(String[] args) {
        var source = Paths.get(".", "Index.java");
        var target = Paths.get(".", "Index.mgs");

        var absoluteSource = source.toAbsolutePath();
        if (Files.exists(source)) {
            System.out.printf("Compiling '%s'...%n", absoluteSource);
            try {
                var start = Instant.now();
                new Compiler(new NIOPath(source), new NIOPath(target)).run();
                var end = Instant.now();
                var elapsed = Duration.between(start, end);
                System.out.printf("Compiled '%s' to '%s' in '%s'%n", absoluteSource, target.toAbsolutePath(), elapsed);
            } catch (MismatchException e) {
                System.err.println(e.getMessage());

                var refactoring = e.renamer;
                System.err.println("A refactoring has been discovered:");
                System.err.println(refactoring.summary + " - " + refactoring.createDetails());
            }
        } else {
            System.err.printf("'%s' does not exist, nothing will happen.%n", absoluteSource);
        }
    }
}
