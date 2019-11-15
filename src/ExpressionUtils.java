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

    /**
     * Преобразует выражение из инфиксной нотации в обратную польскую нотацию (ОПН) по алгоритму <i>Сортировочная
     * станция</i> Эдскера Дейкстры. Отличительной особенностью обратной польской нотации является то, что все
     * аргументы (или операнды) расположены перед операцией. Это позволяет избавиться от необходимости использования
     * скобок. Например, выражение, записаное в инфиксной нотации как 3 * (4 + 7), будет выглядеть как 3 4 7 + *
     * в ОПН. Символы скобок могут быть изменены.
     * https://ru.wikipedia.org/wiki/%D0%90%D0%BB%D0%B3%D0%BE%D1%80%D0%B8%D1%82%D0%BC_%D1%81%D0%BE%D1%80%D1%82%D0%B8%D1%80%D0%BE%D0%B2%D0%BE%D1%87%D0%BD%D0%BE%D0%B9_%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B8
     *
     * @param expression выражение в инфиксной форме.
     * @param operations операторы, использующиеся в выражении (ассоциированные, либо лево-ассоциированные).
     * Значениями карты служат приоритеты операции (самый высокий приоритет - 1). Например, для 5
     * основных математических операторов карта будет выглядеть так:
     * <pre>
     *      *   ->   1
     *      /   ->   1
     *      +   ->   2
     *      -   ->   2
     * </pre>
     * Приведенные операторы определены в константе {@link #MAIN_MATH_OPERATIONS}.
     * @param leftBracket открывающая скобка.
     * @param rightBracket закрывающая скобка.
     * @return преобразованное выражение в ОПН.
     */

    public static Stack<String> sortingStation(String expression, Map<Operation, Integer> operations, String leftBracket,
                                        String rightBracket) {
        if (expression == null || expression.length() == 0)
            throw new IllegalStateException("Expression isn't specified.");
        if (operations == null || operations.isEmpty())
            throw new IllegalStateException("Operations aren't specified.");
        // Выходная строка, разбитая на "символы" - операции и операнды..
        List<String> out = new ArrayList<String>();
        // Стек операций.
        Stack<String> stack = new Stack<String>();

        // Удаление пробелов из выражения.
        expression = expression.replace(" ", "");

        Set<String> operation2 = new HashSet<String>();
        for (Operation op : operations.keySet()){
            operation2.add(op.getOperation());
        }

        // Множество "символов", не являющихся операндами (операции и скобки).
        Set<String> operationSymbols = new HashSet<String>(operation2);
        operationSymbols.add(leftBracket);
        operationSymbols.add(rightBracket);

        // Индекс, на котором закончился разбор строки на прошлой итерации.
        int index = 0;
        // Признак необходимости поиска следующего элемента.
        boolean findNext = true;
        while (findNext) {
            int nextOperationIndex = expression.length();
            String nextOperation = "";
            // Поиск следующего оператора или скобки.
            for (String operation : operationSymbols) {
                int i = expression.indexOf(operation, index);
                if (i >= 0 && i < nextOperationIndex) {
                    nextOperation = operation;
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
                        out.add(stack.pop());
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
            out.add(stack.pop());
        }
        //Стек результирующий
        Stack<String> stackRes = new Stack<String>();
        if (!out.isEmpty())
            stackRes.push(out.remove(0));
        while (!out.isEmpty())
            stackRes.push(out.remove(0));

        return stackRes;
    }

    public static BigDecimal calculateExpression(String expression) {
        Stack<String> result = sortingStation(expression, MAIN_MATH_OPERATIONS, "(",")" );
        Stack<BigDecimal> stack = new Stack<BigDecimal>();

        Set<String> operations = new HashSet<String>();
        for (Operation op : MAIN_MATH_OPERATIONS.keySet()){
            operations.add(op.getOperation());
        }

        if (!result.isEmpty()){
           for (int i = 0; i< result.size(); i++){
                String t = result.get(i);
                if (!operations.contains(t)) {
                    stack.push(new BigDecimal(t));
                } else {
                    BigDecimal operand2 = stack.pop();
                    BigDecimal operand1 = stack.empty() ? BigDecimal.ZERO : stack.pop();
                    if (t.equals("*")) {
                        stack.push(operand1.multiply(operand2));
                    } else if (t.equals("/")) {
                        stack.push(operand1.divide(operand2));
                    } else if (t.equals("+")) {
                        stack.push(operand1.add(operand2));
                    } else if (t.equals("-")) {
                        stack.push(operand1.subtract(operand2));
                    }
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