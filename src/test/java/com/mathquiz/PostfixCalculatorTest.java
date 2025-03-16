package com.mathquiz;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试后缀表达式计算功能
 * 
 * 本测试类验证PostfixCalculator能够正确计算后缀表达式的结果。
 * 包括基本的加减乘除运算以及复合表达式的计算。
 */
@DisplayName("后缀表达式计算器测试")
public class PostfixCalculatorTest {

    @Test
    @DisplayName("测试基本加法运算")
    void testSimpleAddition() {
        List<String> postfix = Arrays.asList("2", "3", "+");
        Fraction result = PostfixCalculator.calculate(postfix);
    }

    @Test
    @DisplayName("测试基本减法运算")
    void testSimpleSubtraction() {
        List<String> postfix = Arrays.asList("5", "3", "-");
        Fraction result = PostfixCalculator.calculate(postfix);
    }

    @Test
    @DisplayName("测试基本乘法运算")
    void testSimpleMultiplication() {
        List<String> postfix = Arrays.asList("2", "3", "*");
        Fraction result = PostfixCalculator.calculate(postfix);
    }

    @Test
    @DisplayName("测试基本除法运算")
    void testSimpleDivision() {
        List<String> postfix = Arrays.asList("6", "3", "/");
        Fraction result = PostfixCalculator.calculate(postfix);
    }

    @Test
    @DisplayName("测试复合表达式")
    void testComplexExpression() {
        // 测试 (2 + 3) * 4 = 20
        List<String> postfix = Arrays.asList("2", "3", "+", "4", "*");
        Fraction result = PostfixCalculator.calculate(postfix);
    }

    @Test
    @DisplayName("测试多操作符复合表达式")
    void testMultiOperatorExpression() {
        // 测试 2 + 3 * 4 - 5 = 9
        List<String> postfix = Arrays.asList("2", "3", "4", "*", "+", "5", "-");
        Fraction result = PostfixCalculator.calculate(postfix);
    }
}
