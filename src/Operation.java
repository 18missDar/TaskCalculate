import java.math.BigDecimal;

public interface Operation {
    BigDecimal execute(BigDecimal a, BigDecimal b);
    String getOperation();
}
