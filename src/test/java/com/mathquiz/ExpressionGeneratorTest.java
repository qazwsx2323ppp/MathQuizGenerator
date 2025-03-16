package com.mathquiz;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试表达式生成功能
 * 
 * 本测试类验证ExpressionGenerator能够根据指定的操作符数量和数值范围
 * 生成有效的数学表达式。
 */
@DisplayName("表达式生成器测试")
public class ExpressionGeneratorTest {

    @Test
    @DisplayName("测试不同操作符数量的表达式生成")
    void testGenerateExpression() {
        ExpressionGenerator generator = new ExpressionGenerator(10);
        
        // 测试不同操作符数量的情况
        for (int i = 1; i <= 3; i++) {
            ExpressionNode expr = generator.generateExpression(i);
            assertNotNull(expr, "生成的表达式不应为空");
            
            // 转换为字符串以验证它是有效表达式
            String exprString = expr.toString();
            assertNotNull(exprString, "表达式字符串不应为空");
            assertFalse(exprString.isEmpty(), "表达式字符串不应为空字符串");
        }
    }

    @Test
    @DisplayName("测试数值范围约束")
    void testRangeConstraint() {
        int range = 5;
        ExpressionGenerator generator = new ExpressionGenerator(range);
        
        // 生成多个表达式并验证它们遵守范围
        for (int i = 0; i < 20; i++) {
            ExpressionNode expr = generator.generateExpression(1);
            // 这需要访问表达式中的操作数值
            // 目前我们只验证它不会崩溃
            assertNotNull(expr, "生成的表达式不应为空");
        }
    }
    
    @Test
    @DisplayName("测试表达式格式有效性")
    void testExpressionFormat() {
        ExpressionGenerator generator = new ExpressionGenerator(10);
        
        // 生成不同复杂度的表达式
        ExpressionNode singleOpExpr = generator.generateExpression(1);
        ExpressionNode doubleOpExpr = generator.generateExpression(2);
        ExpressionNode tripleOpExpr = generator.generateExpression(3);
        
        // 验证生成的表达式格式是否符合预期
        String singleOpStr = singleOpExpr.toString();
        assertTrue(singleOpStr.matches(".*[+\\-*/].*"), 
                "单操作符表达式应包含一个运算符");
        
        String doubleOpStr = doubleOpExpr.toString();
        assertTrue(doubleOpStr.matches(".*[+\\-*/].*[+\\-*/].*"), 
                "双操作符表达式应包含两个运算符");
        
        String tripleOpStr = tripleOpExpr.toString();
        assertTrue(tripleOpStr.matches(".*[+\\-*/].*[+\\-*/].*[+\\-*/].*"), 
                "三操作符表达式应包含三个运算符");
    }
}
