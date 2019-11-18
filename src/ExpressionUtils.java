import operations.OperationFactory;
import operations.Operations;

import java.math.BigDecimal;

import java.util.*;


public class ExpressionUtils {
    public static final List<Object> result = new ArrayList<>();
    public static final NumberBuilder nBuilder = new NumberBuilder();

    public static void addNumber() {
        BigDecimal number = nBuilder.createNumber();
        if (number != null) {
            result.add(number);
        }
    }


    public static void getExpression(String expression) {
        OperationFactory opFactory;
        Stack<Object> stack = new Stack<>();

        for (Character ch : expression.toCharArray()) {
            Operations op = OperationFactory.getOperation(ch);
            if (op != null) {
                if (op.getPriority() == 0) {
                    stack.push(op);
                } else if (op.getPriority() == 1) {
                    addNumber();
                    Operations op1 = (Operations) stack.pop();

                    while (op1.getPriority() != 0) {
                        result.add(op1);
                        op1 = (Operations) stack.pop();
                    }
                } else {
                    addNumber();
                    if (stack.size() > 0)
                        if (op.compare((Operations) stack.peek()))//И если приоритет нашего оператора меньше или равен приоритету оператора на вершине стека
                            result.add(stack.pop());
                    stack.push(op);
                }
            } else {
                nBuilder.put(ch);
            }
        }
        addNumber();
        while (stack.size() > 0) {
            result.add(stack.pop());
        }
    }


    public static BigDecimal calculateExpression(String expression) {
        getExpression(expression);
        Stack<BigDecimal> numbers = new Stack<>();

        for (Object object : result) {
            if (object instanceof Operations) {
                BigDecimal operand2 = numbers.pop();
                BigDecimal operand1 = numbers.empty() ? BigDecimal.ZERO : numbers.pop();
                numbers.push(((Operations)object).execute(operand1, operand2));

            } else {
                numbers.push((BigDecimal) object);
            }

        }
        return numbers.pop();
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.print("Input expression: ");
        String expression = in.nextLine();
        System.out.println("\tResult =  " + calculateExpression(expression));
    }
}