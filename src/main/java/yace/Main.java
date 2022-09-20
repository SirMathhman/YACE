package yace;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Path source = Paths.get(".", "Index.java");
        Path target = Paths.get(".", "Index.mgs");
        new Application(source, target);
    }
}
