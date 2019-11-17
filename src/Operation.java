import java.math.BigDecimal;

public interface Operation {
    BigDecimal execute(BigDecimal a, BigDecimal b);
    public int getPriority();
    public boolean compare(Operation op);
}
