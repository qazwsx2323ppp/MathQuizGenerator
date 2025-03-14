import java.util.*;

public class PostfixConverter {
    public static List<String> convert(List<String> infix) {
        List<String> output = new ArrayList<>();
        Stack<String> stack = new Stack<>();

        for (String token : infix) {
            if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.peek().equals("(")) {
                    output.add(stack.pop());
                }
                stack.pop();
            } else if (isOperator(token)) {
                Operator currentOp = Operator.fromSymbol(token);
                while (!stack.isEmpty() && !stack.peek().equals("(") &&
                        comparePriority(stack.peek(), currentOp) >= 0) {
                    output.add(stack.pop());
                }
                stack.push(token);
            } else {
                output.add(token);
            }
        }

        while (!stack.isEmpty()) {
            output.add(stack.pop());
        }
        return output;
    }

    private static boolean isOperator(String token) {
        return "+-×÷".contains(token);
    }

    private static int comparePriority(String stackOp, Operator currentOp) {
        return Operator.fromSymbol(stackOp).getPriority() - currentOp.getPriority();
    }
}