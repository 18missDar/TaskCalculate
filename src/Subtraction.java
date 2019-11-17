import java.math.BigDecimal;
import java.util.Objects;

public class Subtraction implements Operation {
    private int priority;
    @Override
    public BigDecimal execute(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }

    @Override
    public int getPriority() {
        return priority;
    }

    public Subtraction(int priority) {
        this.priority = priority;
    }

    public boolean compare(Operation op){
        return this.priority >= op.getPriority() ? true : false ;
    }
}
