import java.util.HashMap;
import java.util.Map;

public class OperationFactory {
    public static final Map<Character, Operation> MAIN_MATH_OPERATIONS;

    static {
        MAIN_MATH_OPERATIONS = new HashMap<Character, Operation>();
        MAIN_MATH_OPERATIONS.put('*', new Multiplication(1));
        MAIN_MATH_OPERATIONS.put('/', new Division(1));
        MAIN_MATH_OPERATIONS.put('+', new Addition(2));
        MAIN_MATH_OPERATIONS.put('/', new Subtraction(2));
    }

    public Operation getOperation(Character ch){
        return MAIN_MATH_OPERATIONS.get(ch) != null ? MAIN_MATH_OPERATIONS.get(ch) : null;
    }
}
