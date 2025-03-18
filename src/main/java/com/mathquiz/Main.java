package com.mathquiz;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Main {
    private static final int DEFAULT_NUM_QUESTIONS = 10;
    private static final int DEFAULT_RANGE = 10;
    private static final int DEFAULT_MAX_OPERATORS = 3;

    public static void main(String[] args) {
        int numQuestions = DEFAULT_NUM_QUESTIONS;
        int range = DEFAULT_RANGE;
        //int maxOperators = DEFAULT_MAX_OPERATORS;可用命令行控制运算符数量
        //随机生成1-3个运算符
        Random random = new Random();
        int maxOperators = random.nextInt(3) + 1;

        // 解析命令行参数
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-n":
                    if (i + 1 < args.length) {
                        numQuestions = parsePositiveInt(args[++i], "题目数量");
                    }
                    break;
                case "-r":
                    if (i + 1 < args.length) {
                        range = parsePositiveInt(args[++i], "数值范围");
                    }
                    break;
//                case "-o":
//                    if (i + 1 < args.length) {
//                        maxOperators = parsePositiveInt(args[++i], "运算符数量");
//                        if (maxOperators < 1 || maxOperators > 3) {
//                            System.err.println("运算符数量必须在1-3之间");
//                            return;
//                        }
//                    }
//                    break;
                default:
                    System.err.println("未知参数: " + args[i]);
                    return;
            }
        }

        generateMode(numQuestions, range, maxOperators);//传入的3个参数
        System.out.println("生成完毕");
    }

    private static int parsePositiveInt(String value, String paramName) {
        try {
            int num = Integer.parseInt(value);
            if (num <= 0) throw new NumberFormatException();
            return num;
        } catch (NumberFormatException e) {
            System.err.println(paramName + "必须为正整数");
            System.exit(1);
            return -1; // 不会执行到此处
        }
    }

    private static void generateMode(int numQuestions, int range, int maxOperators) {
        ExpressionGenerator generator = new ExpressionGenerator(range);

        try (BufferedWriter exerciseWriter = new BufferedWriter(new FileWriter("Exercises.txt"));
             BufferedWriter answerWriter = new BufferedWriter(new FileWriter("Answers.txt"))) {

            for (int i = 1; i <= numQuestions; i++) {
                ExpressionNode expr = generator.generateExpression(maxOperators);
                String problem = i + ". " + expr;
                String answer = i + ". " + calculateAnswer(expr);

                exerciseWriter.write(problem);
                exerciseWriter.newLine();
                answerWriter.write(answer);
                answerWriter.newLine();
            }
        } catch (IOException e) {
            System.err.println("文件写入错误: " + e.getMessage());
        }
    }

    private static String calculateAnswer(ExpressionNode expr) {
        List<String> infix = expr.toExpressionList();
        List<String> postfix = PostfixConverter.convert(infix);
        return PostfixCalculator.calculate(postfix).toString();
    }
}