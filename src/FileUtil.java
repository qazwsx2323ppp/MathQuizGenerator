import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileUtil {

    public static void writeExercisesToFile(List<MathExpression> expressions, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            int index = 1;
            for (MathExpression expression : expressions) {
                writer.write(index++ + ". " + expression.getExpression() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeAnswersToFile(List<MathExpression> expressions, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            int index = 1;
            for (MathExpression expression : expressions) {
                writer.write(index++ + ". " + expression.getAnswer() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
