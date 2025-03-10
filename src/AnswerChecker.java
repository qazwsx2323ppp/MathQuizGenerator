import java.io.*;
import java.util.*;

public class AnswerChecker {

    public static void checkAnswers(String exerciseFile, String answerFile) {
        // 读取文件内容并判断答案是否正确
        // 统计对错数量并输出
        List<String> correctAnswers = new ArrayList<>();
        List<String> wrongAnswers = new ArrayList<>();

        // 假设通过解析两个文件，读取题目和答案
        // 然后根据实际计算结果与给定答案比对

        // 将正确和错误的题目及其编号写入Grade.txt文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Grade.txt"))) {
            writer.write("Correct: " + correctAnswers.size() + " (" + String.join(", ", correctAnswers) + ")\n");
            writer.write("Wrong: " + wrongAnswers.size() + " (" + String.join(", ", wrongAnswers) + ")\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
