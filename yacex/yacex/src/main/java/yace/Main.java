package yace;

import yace.app.Compiler;
import yace.io.NIOPath;

import java.nio.file.Paths;

/**
 * The <b>actual</b> main entry point of the program.
 */
public class Main {
    public static void main(String[] args) {
        var source = Paths.get(".", "Index.java");
        var target = Paths.get(".", "Index.mgs");
        new Compiler(new NIOPath(source), new NIOPath(target));
    }
}
