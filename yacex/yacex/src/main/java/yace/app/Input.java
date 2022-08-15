package yace.app;

import yace.io.File;

public class Input {
    public final File source;
    public final String input;
    public final String actualName;

    public Input(File source, String input, String actualName) {
        this.source = source;
        this.input = input;
        this.actualName = actualName;
    }
}
