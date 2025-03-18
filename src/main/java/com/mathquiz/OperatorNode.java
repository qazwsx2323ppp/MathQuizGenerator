package com.mathquiz;

import com.mathquiz.ExpressionNode;
import com.mathquiz.Fraction;
import com.mathquiz.Operator;

import java.util.ArrayList;
import java.util.List;

public class OperatorNode implements ExpressionNode {
    private final Operator operator;
    private final ExpressionNode left;
    private final ExpressionNode right;
    private final boolean forceBrackets;
    private final boolean isRoot; // 新增根节点标识
    private final int parentPriority; // 新增字段

    public OperatorNode(Operator operator, ExpressionNode left, ExpressionNode right,
                        boolean forceBrackets, boolean isRoot, int parentPriority) {
        this.operator = operator;
        this.left = left;
        this.right = right;
        this.forceBrackets = forceBrackets;
        this.isRoot = isRoot;
        this.parentPriority = parentPriority;
    }

    private int parentPriority() {
        return this.parentPriority;
    }

    @Override
    public Fraction evaluate() {
        Fraction leftVal = left.evaluate();
        Fraction rightVal = right.evaluate();
        switch (operator) {
            case ADD: return leftVal.add(rightVal);
            case SUBTRACT: return leftVal.subtract(rightVal);
            case MULTIPLY: return leftVal.multiply(rightVal);
            case DIVIDE: return leftVal.divide(rightVal);
            default: throw new UnsupportedOperationException();
        }
    }



    @Override
    public String toString() {
        // 新增运算符数量检测
        if (getOperatorCount() > 3) {
            throw new IllegalStateException("运算符数量超标");
        }

        String leftExpr = smartWrap(left, true);
        String rightExpr = smartWrap(right, false);
        String expr = leftExpr + " " + operator.getSymbol() + " " + rightExpr;

        // 强化根节点检测（防止根节点包裹括号）
        if (forceBrackets && !isRoot && !hasNestedRedundancy()) {
            return "(" + expr + ")";
        }
        return expr;
    }

    // 新增嵌套冗余检测
    private boolean hasNestedRedundancy() {
        return (left instanceof OperatorNode && ((OperatorNode) left).hasOuterBrackets()) ||
                (right instanceof OperatorNode && ((OperatorNode) right).hasOuterBrackets());
    }

    // 精确计算运算符数量
    private int getOperatorCount() {
        int count = 1;
        if (left instanceof OperatorNode) count += ((OperatorNode) left).getOperatorCount();
        if (right instanceof OperatorNode) count += ((OperatorNode) right).getOperatorCount();
        return count;
    }



//    private boolean hasOuterBrackets() {
//        return this.forceBrackets || operator.getPriority() < this.parentPriority;
//    }

    private boolean hasOuterBrackets() {
        // 基本条件检测
        if (!this.forceBrackets && operator.getPriority() >= parentPriority) {
            return false;
        }

        // 深度检测子节点括号必要性（关键修改）
        return needsBracketByChildPriority();
    }

    private boolean needsBracketByChildPriority() {
        // 获取子节点最低优先级
        int minChildPriority = Math.min(
                getChildMinPriority(left),
                getChildMinPriority(right)
        );
        // 仅当子节点存在更低优先级运算符时才需要括号
        return minChildPriority < this.operator.getPriority();
    }

    private int getChildMinPriority(ExpressionNode node) {
        if (node instanceof OperatorNode) {
            OperatorNode child = (OperatorNode) node;
            // 递归获取子树最低优先级
            return Math.min(
                    child.operator.getPriority(),
                    Math.min(
                            getChildMinPriority(child.left),
                            getChildMinPriority(child.right)
                    )
            );
        }
        return Integer.MAX_VALUE; // 数字节点无优先级
    }

    // 智能节点包裹（新增运算符优先级判断）
    private String smartWrap(ExpressionNode node, boolean isLeft) {
        if (node instanceof OperatorNode) {
            OperatorNode child = (OperatorNode) node;
            boolean shouldWrap = needsWrap(child.operator, isLeft);

            // 避免嵌套括号的特殊情况处理
            if (child.forceBrackets && this.forceBrackets) {
                return child.toString().replaceFirst("^\\(", "").replaceFirst("\\)$", "");
            }
            return shouldWrap ? "(" + node + ")" : node.toString();
        }
        return node.toString();
    }


    // 精确运算符优先级判断
    private boolean needsWrap(Operator childOp, boolean isLeft) {
        if (childOp.getPriority() < this.operator.getPriority()) return true;
        if (childOp.getPriority() == this.operator.getPriority()) {
            if (this.operator == Operator.SUBTRACT && !isLeft) return true;
            if (this.operator == Operator.DIVIDE) return true;
        }
        return false;
    }

    @Override
    public List<String> toExpressionList() {
        List<String> list = new ArrayList<>();
        if (forceBrackets) list.add("(");
        list.addAll(left.toExpressionList());
        list.add(operator.getSymbol());
        list.addAll(right.toExpressionList());
        if (forceBrackets) list.add(")");
        return list;
    }
}