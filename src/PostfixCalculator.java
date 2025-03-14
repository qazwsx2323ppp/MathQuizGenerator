import java.util.List;
import java.util.Stack;

public class PostfixCalculator {
    public static Fraction calculate(List<String> postfix) {
        Stack<Fraction> stack = new Stack<>();

        for (String token : postfix) {
            if (isOperator(token)) {
                Fraction b = stack.pop();
                Fraction a = stack.pop();
                stack.push(applyOperator(a, b, token));
            } else {
                stack.push(parseFraction(token));
            }
        }
        return stack.pop();
    }

    private static Fraction parseFraction(String token) {
        if (token.contains("'")) {
            String[] parts = token.split("'");
            int whole = Integer.parseInt(parts[0]);
            String[] fractionParts = parts[1].split("/");
            return new Fraction(whole * Integer.parseInt(fractionParts[1]) + Integer.parseInt(fractionParts[0]),
                    Integer.parseInt(fractionParts[1]));
        }
        if (token.contains("/")) {
            String[] parts = token.split("/");
            return new Fraction(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        }
        return new Fraction(Integer.parseInt(token), 1);
    }

    private static boolean isOperator(String token) {
        return "+-×÷".contains(token);
    }

    private static Fraction applyOperator(Fraction a, Fraction b, String op) {
        switch (op) {
            case "+": return a.add(b);
            case "-": return a.subtract(b);
            case "×": return a.multiply(b);
            case "÷": return a.divide(b);
            default: throw new IllegalArgumentException("无效运算符: " + op);
        }
    }
}