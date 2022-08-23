package yace;

import java.util.Objects;

public class ClassStructureError implements AnalysisResult {
    private final String context;

    public ClassStructureError(String context) {
        this.context = context;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassStructureError that = (ClassStructureError) o;
        return Objects.equals(context, that.context);
    }

    @Override
    public int hashCode() {
        return Objects.hash(context);
    }
}
