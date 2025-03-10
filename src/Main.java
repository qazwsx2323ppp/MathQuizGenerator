//主函数入口
import java.util.*;
public class Main {
    public static void main(String[] args) {
        int numOfQuestions = 0;
        int range = 0;

        // 解析命令行参数
        for (int i = 0; i < args.length; i++) {
            if ("-n".equals(args[i])) {
                numOfQuestions = Integer.parseInt(args[++i]);
            } else if ("-r".equals(args[i])) {
                range = Integer.parseInt(args[++i]);
            }
        }

        if (range == 0) {
            System.out.println("Error: Missing required parameter -r.");
            return;
        }

        // 调用题目生成器来生成题目
        List<MathExpression> expressions = ExpressionGenerator.generateExpressions(numOfQuestions, range);

        // 输出题目和答案
        FileUtil.writeExercisesToFile(expressions, "Exercises.txt");
        FileUtil.writeAnswersToFile(expressions, "Answers.txt");
    }
}
