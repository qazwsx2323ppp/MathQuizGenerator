package com.mathquiz;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试中缀表达式到后缀表达式的转换功能
 * 
 * 本测试类验证PostfixConverter能够正确地将中缀表达式转换为后缀表达式。
 * 包含简单表达式和复杂表达式的测试用例。
 */
@DisplayName("后缀表达式转换器测试")
public class PostfixConverterTest {

    @Test
    @DisplayName("测试简单中缀到后缀表达式转换")
    void testSimpleInfixToPostfixConversion() {
        // 测试简单表达式：2 + 3
        List<String> infix = Arrays.asList("2", "+", "3");
        List<String> postfix = PostfixConverter.convert(infix);
        
        List<String> expected = Arrays.asList("2", "3", "+");
        assertEquals(expected, postfix, "简单加法应正确转换");
    }

    @Test
    @DisplayName("测试带括号的复杂中缀到后缀表达式转换")
    void testComplexInfixToPostfixConversion() {
        // 测试更复杂的表达式：(2 + 3) * 4
        List<String> infix = Arrays.asList("(", "2", "+", "3", ")", "*", "4");
        List<String> postfix = PostfixConverter.convert(infix);
        
        List<String> expected = Arrays.asList("2", "3", "+", "4", "*");
        assertEquals(expected, postfix, "带括号的表达式应正确转换");
    }
    
    @Test
    @DisplayName("测试多操作符中缀到后缀表达式转换")
    void testMultipleOperatorInfixToPostfixConversion() {
        // 测试多操作符表达式：2 + 3 * 4 - 5
        List<String> infix = Arrays.asList("2", "+", "3", "*", "4", "-", "5");
        List<String> postfix = PostfixConverter.convert(infix);
        
        // 根据操作符优先级，后缀表达式应为：2 3 4 * + 5 -
        List<String> expected = Arrays.asList("2", "3", "4", "*", "+", "5", "-");
        assertEquals(expected, postfix, "多操作符表达式应考虑优先级正确转换");
    }
    
    @Test
    @DisplayName("测试嵌套括号的中缀到后缀表达式转换")
    void testNestedParenthesesInfixToPostfixConversion() {
        // 测试嵌套括号表达式：((2 + 3) * (4 - 1)) / 3
        List<String> infix = Arrays.asList(
            "(", "(", "2", "+", "3", ")", "*", "(", "4", "-", "1", ")", ")", "/", "3"
        );
        List<String> postfix = PostfixConverter.convert(infix);
        
        // 预期后缀表达式：2 3 + 4 1 - * 3 /
        List<String> expected = Arrays.asList("2", "3", "+", "4", "1", "-", "*", "3", "/");
        assertEquals(expected, postfix, "嵌套括号的表达式应正确转换");
    }
}
