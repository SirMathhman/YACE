package yace;

import java.nio.file.Path;
import java.util.Set;

public interface Gateway {
    Set<Path> collectSources();
}
