import java.util.List;
import java.util.Random;

public class ExpressionGenerator {
    private final Random random = new Random();
    private static final double BRACKET_PROBABILITY = 0.4;
    private final int range;  // 通过构造函数动态传入范围
    private static final int MAX_OPERATORS = 3; // 新增最大运算符限制

    // 构造函数接收范围参数
    public ExpressionGenerator(int range) {
        if (range < 1) {
            throw new IllegalArgumentException("数值范围必须大于0");
        }
        this.range = range;
    }

    public ExpressionNode generateExpression(int maxOperators) {
        if (maxOperators < 1 || maxOperators > 3) {
            throw new IllegalArgumentException("运算符数量必须在1-3之间");
        }

        ExpressionNode root = null;
        do {
            try {
                root = buildExpressionTree(MAX_OPERATORS,false, true,false,0);
                validateExpression(root); // 验证表达式有效性
            } catch (ValidationException e) {
                root = null; // 如果验证失败则重新生成
            }
        } while (root == null);

        return root;
    }


    private ExpressionNode buildExpressionTree(int remainingOps, boolean bracketsUsed,
                                               boolean isRoot, boolean inBracketContext,
                                               int parentPriority) {
        if (remainingOps <= 0) {
            return new NumberNode(Fraction.generate(range));
        }

        Operator op = randomOperator();
        // 动态分配剩余运算符（关键修改点）
        int leftOps = random.nextInt(remainingOps);
        int rightOps = remainingOps - leftOps - 1;

        // 强化括号上下文传递（新增多层检测）
        boolean canAddBracket = !isRoot && !inBracketContext && remainingOps >= 2;
        boolean addBrackets = canAddBracket && random.nextDouble() < BRACKET_PROBABILITY;

        // 生成子树时严格传递上下文状态
        ExpressionNode left = buildSubTree(leftOps, bracketsUsed || addBrackets,
                addBrackets, op.getPriority());
        ExpressionNode right = buildSubTree(rightOps, bracketsUsed || addBrackets,
                addBrackets, op.getPriority());

        return new OperatorNode(op, left, right, addBrackets, isRoot, parentPriority);
    }

    private ExpressionNode buildSubTree(int remainingOps, boolean bracketsUsed,
                                        boolean inBracketContext, int parentPriority) {
        if (remainingOps > 0 && random.nextDouble() < 0.4) {
            return buildExpressionTree(remainingOps, bracketsUsed, false,
                    inBracketContext, parentPriority);
        }
        return new NumberNode(Fraction.generate(range));
    }


     private boolean shouldAddBrackets(int remainingOps) {
           return random.nextDouble() < BRACKET_PROBABILITY * (4 - remainingOps)/3.0;
        }

    private Operator randomOperator() {
        Operator[] operators = Operator.values();
        return operators[random.nextInt(operators.length)];
    }



    private void validateExpression(ExpressionNode node) {
        try {
            List<String> infix = node.toExpressionList();
            List<String> postfix = PostfixConverter.convert(infix);
            Fraction result = PostfixCalculator.calculate(postfix);

            // 检查结果是否为负数或分母为零
            if (result.getNumerator() < 0) {
                throw new ValidationException("结果为负数");
            }
        } catch (ArithmeticException e) {
            throw new ValidationException("计算过程中出现除零错误");
        }
    }

    private static class ValidationException extends RuntimeException {
        public ValidationException(String message) {
            super(message);
        }
    }
}