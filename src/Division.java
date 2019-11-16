import java.math.BigDecimal;
import java.util.Objects;

public class Division implements Operation {
    private String operation = "/";
    @Override
    public BigDecimal execute(BigDecimal a, BigDecimal b) {
        return a.divide(b);
    }

    @Override
    public String getOperation() {
        return operation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Division division = (Division) o;
        return Objects.equals(operation, division.operation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation);
    }

    @Override
    public String toString() {
        return operation;
    }
}
