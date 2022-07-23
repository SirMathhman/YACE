package yace;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        new Application(Paths.get("."), System.in).run();
    }
}