import java.math.BigDecimal;
import java.util.Objects;

public class Multiplication implements Operation {
    private int priority;
    @Override
    public BigDecimal execute(BigDecimal a, BigDecimal b) {
        return a.multiply(b);
    }

    public Multiplication(int priority) {
        this.priority = priority;
    }

    @Override
    public int getPriority() {
        return priority;
    }
    public boolean compare(Operation op){
        return this.priority >= op.getPriority() ? true : false ;
    }
}
