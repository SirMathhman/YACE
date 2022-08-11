package yace.io;

import java.io.IOException;

/**
 * Represents a singular item on the file system
 * that <b>actually</b> exists,
 * and contains data, i.e.
 * it is not a directory, shortcut, etc.
 */
public interface File {
    /**
     * Writes the content of the value contained within the argument
     * to this file.
     *
     * @param content to write.
     */
    void writeString(String content) throws IOException;

    /**
     * Reads the content of this file as string.
     *
     * @return The content of the file.
     * If the file was empty, then an empty string will be returned.
     */
    String readAsString() throws IOException;

    /**
     * @return The name of this file is the last segment of the file,
     * e.g. <code>first/second/third.txt</code> => <code>third.txt</code>
     */
    String getName();
}
