public class MathExpression {

    private String expression;  // 表达式字符串
    private String answer;      // 计算结果

    public MathExpression(String expression, String answer) {
        this.expression = expression;
        this.answer = answer;
    }

    public String getExpression() {
        return expression;
    }

    public String getAnswer() {
        return answer;
    }
}
