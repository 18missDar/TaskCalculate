import java.math.BigDecimal;

public class LeftBracket implements Operation {
    @Override
    public BigDecimal execute(BigDecimal a, BigDecimal b) {
        return null;
    }

    @Override
    public String getOperation() {
        return "(";
    }
}
