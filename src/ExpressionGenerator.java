import java.util.*;

// 用于表示数学表达式及其答案的类


public class ExpressionGenerator {

    // 生成指定数量的数学表达式
    public static List<MathExpression> generateExpressions(int numOfQuestions, int range) {
        List<MathExpression> expressions = new ArrayList<>();

        for (int i = 0; i < numOfQuestions; i++) {
            MathExpression expression = generateRandomExpression(range);
            expressions.add(expression);
        }

        return expressions;
    }

    // 生成一个随机的数学表达式并计算答案
    private static MathExpression generateRandomExpression(int range) {
        String expression = generateRandomMathExpression(range);  // 生成随机的数学表达式
        String answer = calculateAnswer(expression);  // 计算该表达式的答案
        return new MathExpression(expression, answer);  // 使用生成的表达式和答案创建 MathExpression 对象
    }

    // 生成随机的数学表达式
    private static String generateRandomMathExpression(int range) {
        Random random = new Random();

        // 生成随机数
        int num1 = random.nextInt(range);  // 生成一个在 [0, range) 范围内的随机整数
        int num2 = random.nextInt(range);

        // 选择一个随机的运算符
        String[] operators = {"+", "-", "*", "/"};
        String operator = operators[random.nextInt(operators.length)];

        // 构建一个简单的数学表达式
        return num1 + " " + operator + " " + num2;
    }

    // 计算表达式的答案
    private static String calculateAnswer(String expression) {
        // 这里简单解析并计算基础的四则运算
        String[] tokens = expression.split(" ");
        int num1 = Integer.parseInt(tokens[0]);
        String operator = tokens[1];
        int num2 = Integer.parseInt(tokens[2]);

        int result = 0;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                // 除法时处理除数为0的情况
                if (num2 != 0) {
                    result = num1 / num2;
                }
                break;
        }
        return String.valueOf(result);
    }
}

