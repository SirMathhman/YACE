package yace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Executor {
    public static final String CreatePackage = invokeCreate("Packages");
    protected static final String PlatformExtension = "java";
    static final String CreateSource = invokeCreate("Sources");
    private final String line;
    private final Path workingDirectory;

    public Executor(String line, Path workingDirectory) {
        this.line = line;
        this.workingDirectory = workingDirectory;
    }

    protected static String invokeCreate(String name) {
        return name + ".create";
    }

    boolean execute() throws IOException {
        if (line.equals("exit")) return true;
        else if (line.contains("(") && line.endsWith(")")) {
            var argStart = line.indexOf("(");
            var caller = line.substring(0, argStart);
            var argEnd = line.length() - 1;

            var argString = line.substring(argStart + 1, argEnd).strip();
            var name = argString.substring(1, argString.length() - 1);

            if (caller.equals(CreateSource)) {
                Files.writeString(workingDirectory.resolve(name + "." + PlatformExtension), new ClassRenderer(name).renderClass());
                return false;
            } else if (caller.equals(CreatePackage)) {
                var args = name.split(",");
                var current = workingDirectory;
                for (String arg : args) {
                    current = current.resolve(arg);
                }
                Files.createDirectories(current);
                return false;
            } else {
                throw new IllegalArgumentException("Invalid caller: " + caller);
            }
        } else {
            throw new IllegalArgumentException("Invalid line: " + line);
        }
    }
}
