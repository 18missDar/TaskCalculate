package operations;

import java.util.HashMap;
import java.util.Map;

public class OperationFactory {
    public static final Map<Character, Operations> MAIN_MATH_OPERATIONS;

    static {
        MAIN_MATH_OPERATIONS = new HashMap<Character, Operations>();
        MAIN_MATH_OPERATIONS.put('*', new Multiplication(4));
        MAIN_MATH_OPERATIONS.put('/', new Division(4));
        MAIN_MATH_OPERATIONS.put('+', new Addition(2));
        MAIN_MATH_OPERATIONS.put('-', new Subtraction(3));
        MAIN_MATH_OPERATIONS.put('(', new LeftBracket(0));
        MAIN_MATH_OPERATIONS.put(')', new RightBracket(1));
    }

    public Operations getOperation(Character ch) {
        return MAIN_MATH_OPERATIONS.get(ch) != null ? MAIN_MATH_OPERATIONS.get(ch).copy() : null;
    }
}
