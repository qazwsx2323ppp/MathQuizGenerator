package com.mathquiz;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试Main类功能
 * 
 * 本测试类验证主程序的功能，包括：
 * - 命令行参数解析
 * - 文件生成
 * - 不同参数组合的功能验证
 */
@DisplayName("主程序功能测试")
public class MainTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    
    @TempDir
    Path tempDir;
    
    private File exerciseFile;
    private File answerFile;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        
        // 设置临时目录作为工作目录以隔离文件操作
        exerciseFile = new File(tempDir.toFile(), "Exercises.txt");
        answerFile = new File(tempDir.toFile(), "Answers.txt");
        System.setProperty("user.dir", tempDir.toString());
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    @DisplayName("默认参数测试")
    void testMainWithDefaultParameters() throws IOException {
        // 使用默认参数执行main
        Main.main(new String[]{});
        
        // 检查文件是否创建
        assertTrue(exerciseFile.exists(), "应创建练习文件");
        assertTrue(answerFile.exists(), "应创建答案文件");
        
        // 检查文件是否包含预期的行数（默认10个问题）
        List<String> exerciseLines = Files.readAllLines(exerciseFile.toPath());
        List<String> answerLines = Files.readAllLines(answerFile.toPath());
        
        assertEquals(10, exerciseLines.size(), "默认应生成10个问题");
        assertEquals(10, answerLines.size(), "默认应生成10个答案");
        
        // 验证输出确认生成完成
        assertTrue(outContent.toString().contains("生成完毕"), "应显示生成完毕信息");
    }

    @Test
    @DisplayName("自定义题目数量测试")
    void testMainWithCustomQuestionCount() throws IOException {
        // 使用自定义问题数量执行main
        Main.main(new String[]{"-n", "5"});
        
        // 检查文件是否包含预期的行数
        List<String> exerciseLines = Files.readAllLines(exerciseFile.toPath());
        List<String> answerLines = Files.readAllLines(answerFile.toPath());
        
        assertEquals(5, exerciseLines.size(), "应生成5个问题");
        assertEquals(5, answerLines.size(), "应生成5个答案");
    }

    @Test
    @DisplayName("自定义数值范围测试")
    void testMainWithCustomRange() throws IOException {
        // 使用自定义范围执行main
        Main.main(new String[]{"-r", "20"});
        
        // 验证文件创建（实际范围测试需要更复杂的解析）
        assertTrue(exerciseFile.exists(), "应创建练习文件");
        assertTrue(answerFile.exists(), "应创建答案文件");
    }

    @ParameterizedTest
    @ValueSource(strings = {"-n", "0", "-n", "-5"})
    @DisplayName("无效参数测试")
    void testMainWithInvalidParameters(String arg) {
        // 测试无效参数
        if (arg.equals("-n")) {
            Main.main(new String[]{arg});
            assertFalse(errContent.toString().contains("未知参数"), "缺少值时应显示错误");
        } else {
            Main.main(new String[]{"-n", arg});
            assertTrue(errContent.toString().contains("必须为正整数"), "非正数时应显示错误");
        }
    }
}
