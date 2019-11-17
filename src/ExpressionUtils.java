import java.math.BigDecimal;

import java.util.*;



public class ExpressionUtils {


    public static List<Object> getExpression(String expression){
        NumberBuilder nBuilder = new NumberBuilder(new StringBuilder(""));
        OperationFactory opFactory = new OperationFactory();
        List<Object> result = new ArrayList<>();
        Stack<Object> stack = new Stack<>();

        for (Character ch : expression.toCharArray()) {
            Operation op = opFactory.getOperation(ch);
            if (op != null) {
                BigDecimal number = nBuilder.createNumber();
                if (number != null) {
                    result.add(number);
                }
                if (stack.size() > 0) //Если в стеке есть элементы
                    if (op.compare((Operation)stack.peek()))//И если приоритет нашего оператора меньше или равен приоритету оператора на вершине стека
                        result.add(stack.pop());
                stack.push(op);
            }
            else {
                nBuilder.put(ch);
            }
        }
        BigDecimal number = nBuilder.createNumber();
        if (number != null) {
            result.add(number);
        }
        while (stack.size() > 0){
            result.add(stack.pop());
        }
        return result;
    }


    public static BigDecimal calculateExpression(String expression) {
        List<Object> result =  getExpression(expression);
        Stack<BigDecimal> numbers = new Stack<>();

        for (Object object : result) {
            if (object instanceof Operation) {
                BigDecimal operand2 = numbers.pop();
                BigDecimal operand1 = numbers.empty() ? BigDecimal.ZERO : numbers.pop();
                Operation op = (Operation)object;
                numbers.push(op.execute(operand1, operand2));

            }
            else {
                BigDecimal operand = (BigDecimal)object;
                numbers.push(operand);
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