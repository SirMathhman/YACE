package yace;

import yace.app.Compiler;
import yace.app.Refactorer;
import yace.io.NIOPath;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

/**
 * The <b>actual</b> main entry point of the program.
 */
public class Main {
    public static void main(String[] args) {
        var source = Paths.get(".", "Index.java");
        var sourcePath = new NIOPath(source);

        var target = Paths.get(".", "Index.mgs");
        var targetPath = new NIOPath(target);

        var absoluteSource = source.toAbsolutePath();
        if (Files.exists(source)) {
            System.out.printf("Compiling '%s'...%n", absoluteSource);
            try {
                var elapsed = measure(() -> new Compiler(sourcePath, targetPath).run());

                System.out.printf("Compiled '%s' to '%s' in '%s'%n", absoluteSource, target.toAbsolutePath(), elapsed);
            } catch (MismatchException e) {
                System.err.println(e.getMessage());

                var refactoring = e.renamer;
                System.err.println("A refactoring has been discovered:");
                System.err.printf("\t - %s : %s%n%n", refactoring.summary, refactoring.createDetails());

                System.err.println("Enter yes or no to perform this action:");
                var scanner = new Scanner(System.in);
                while (true) {
                    var line = scanner.nextLine();
                    if (line.strip().toLowerCase().contains("y")) {
                        var elapsed = measure(() -> new Refactorer(sourcePath, targetPath, refactoring).run());
                        System.out.printf("Refactoring completed in '%s'.%n", elapsed);
                    } else {
                        break;
                    }
                }
            }
        } else {
            System.err.printf("'%s' does not exist, nothing will happen.%n", absoluteSource);
        }
    }

    private static Duration measure(Runnable task) {
        var start = Instant.now();
        task.run();

        var end = Instant.now();
        return Duration.between(start, end);
    }
}
