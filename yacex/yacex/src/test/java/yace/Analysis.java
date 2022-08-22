package yace;

import java.util.Objects;

public class Analysis {
    final String message;

    public Analysis(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Analysis analysis = (Analysis) o;
        return Objects.equals(message, analysis.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }
}
