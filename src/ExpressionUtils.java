import java.math.BigDecimal;

import java.util.*;



public class ExpressionUtils {
    public static final Map<Operation, Integer> MAIN_MATH_OPERATIONS;

    static {
        MAIN_MATH_OPERATIONS = new HashMap<Operation, Integer>();
        MAIN_MATH_OPERATIONS.put(new Multiplication(), 1);
        MAIN_MATH_OPERATIONS.put(new Division(), 1);
        MAIN_MATH_OPERATIONS.put(new Addition(), 2);
        MAIN_MATH_OPERATIONS.put(new Subtraction(), 2);
    }



    public static Operation find(String op){
        for (Operation operation : MAIN_MATH_OPERATIONS.keySet()){
            if (operation.getOperation().equals(op)){
                return operation;
            }
        }
        return null;
    }

    public static List<Object> sortingStation(String expression, Map<Operation, Integer> operations, String leftBracket,
                                        String rightBracket) {
        if (expression == null || expression.length() == 0)
            throw new IllegalStateException("Expression isn't specified.");
        if (operations == null || operations.isEmpty())
            throw new IllegalStateException("Operations aren't specified.");
        // Выходная строка, разбитая на "символы" - операции и операнды..
        List<Object> out = new ArrayList<Object>();
        // Стек операций.
        Stack<String> stack = new Stack<String>();

        // Удаление пробелов из выражения.
        expression = expression.replace(" ", "");

        // Множество "символов", не являющихся операндами (операции и скобки).
        Map<Operation, Integer> operationSymbols = new HashMap(operations);
        operationSymbols.put(new LeftBracket(), 0);
        operationSymbols.put(new RightBracket(), 0);

        // Индекс, на котором закончился разбор строки на прошлой итерации.
        int index = 0;
        // Признак необходимости поиска следующего элемента.
        boolean findNext = true;
        while (findNext) {
            int nextOperationIndex = expression.length();
            String nextOperation = "";
            // Поиск следующего оператора или скобки.
            for (Operation operation : operationSymbols.keySet()) {
                int i = expression.indexOf(operation.getOperation(), index);
                if (i >= 0 && i < nextOperationIndex) {
                    nextOperation = operation.getOperation();
                    nextOperationIndex = i;
                }
            }
            // Оператор не найден.
            if (nextOperationIndex == expression.length()) {
                findNext = false;
            } else {
                // Если оператору или скобке предшествует операнд, добавляем его в выходную строку.
                if (index != nextOperationIndex) {
                    out.add(expression.substring(index, nextOperationIndex));
                }
                // Обработка операторов и скобок.
                // Открывающая скобка.
                if (nextOperation.equals(leftBracket)) {
                    stack.push(nextOperation);
                }
                // Закрывающая скобка.
                else if (nextOperation.equals(rightBracket)) {
                    while (!stack.peek().equals(leftBracket)) {
                        out.add(stack.pop());
                        if (stack.empty()) {
                            throw new IllegalArgumentException("Unmatched brackets");
                        }
                    }
                    stack.pop();
                }
                // Операция.
                else {
                    while (!stack.empty() && !stack.peek().equals(leftBracket) &&
                            (operations.get(nextOperation) >= operations.get(stack.peek()))) {
                        out.add(find(stack.pop()));
                    }
                    stack.push(nextOperation);
                }
                index = nextOperationIndex + nextOperation.length();
            }
        }
        // Добавление в выходную строку операндов после последнего операнда.
        if (index != expression.length()) {
            out.add(expression.substring(index));
        }
        // Пробразование выходного списка к выходной строке.
        while (!stack.empty()) {
            out.add(find(stack.pop()));
        }

        return out;
    }

    public static BigDecimal calculateExpression(String expression) {
        List<Object> result = sortingStation(expression, MAIN_MATH_OPERATIONS, "(",")" );
        Stack<BigDecimal> stack = new Stack<BigDecimal>();

        if (!result.isEmpty()){
           for (int i = 0; i< result.size(); i++){
                Object t = result.get(i);
                if (find(t.toString()) == null) {
                    stack.push(new BigDecimal((String)t));
                } else {
                    BigDecimal operand2 = stack.pop();
                    BigDecimal operand1 = stack.empty() ? BigDecimal.ZERO : stack.pop();
                    stack.push(find(t.toString()).execute(operand1, operand2));
                }
            }
            }
        if (stack.size() != 1)
            throw new IllegalArgumentException("Expression syntax error.");
        return stack.pop();
    }

    private ExpressionUtils() {
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.print("Input expression: ");
        String expression = in.nextLine();
        System.out.println("\tResult =  " + calculateExpression(expression));
    }
}