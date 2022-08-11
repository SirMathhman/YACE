package yace;

import java.io.IOException;

/**
 * The main entry point of the application.
 */
public class Application {
    private final NIOPath source;
    private final yace.NIOPath target;

    /**
     * The application will run by reading the content
     * within the source file and producing output in the target file.
     *
     * @param source The source/input file.
     * @param target The target/output file.
     */
    public Application(NIOPath source, NIOPath target) {
        this.source = source;
        this.target = target;
    }

    /**
     * Actually executes the application.
     *
     * @throws IOException If an internal file error happened.
     */
    void run() throws IOException {
        if (source.isExists()) {
            target.createAsFile();
        }
    }
}
