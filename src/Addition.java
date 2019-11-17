import java.math.BigDecimal;
import java.util.Objects;

public class Addition implements Operation {
    private int priority;
    @Override
    public BigDecimal execute(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    public Addition(int priority) {
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
