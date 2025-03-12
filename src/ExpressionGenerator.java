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



    private static String calculateAnswer(String expression) {
        // 简单解析并计算基础的四则运算
        String[] tokens = expression.split(" ");
        int num1 = Integer.parseInt(tokens[0]);
        String operator = tokens[1];
        int num2 = Integer.parseInt(tokens[2]);

        // 用于存储最终结果的字符串
        String result;

        switch (operator) {
            case "+":
                result = String.valueOf(num1 + num2);
                break;
            case "-":
                result = String.valueOf(num1 - num2);
                break;
            case "*":
                result = String.valueOf(num1 * num2);
                break;
            case "/":
                // 使用 Fraction 类处理除法运算
                Fraction fraction = new Fraction(num1, num2);
                result = fraction.toString();
                break;
            default:
                result = "Invalid operator";
        }

//        public static void main(String[] args) {
//            // 测试代码
//            System.out.println(calculateAnswer("10 + 5")); // 输出：15
//            System.out.println(calculateAnswer("10 - 5")); // 输出：5
//            System.out.println(calculateAnswer("10 * 5")); // 输出：50
//            System.out.println(calculateAnswer("10 / 5")); // 输出：2
//            System.out.println(calculateAnswer("11 / 4")); // 输出：2'3/4
//            System.out.println(calculateAnswer("10 / 3")); // 输出：3'1/3
//        }
        return String.valueOf(result);
    }
}

