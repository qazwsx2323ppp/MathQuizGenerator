package com.mathquiz;

import com.mathquiz.Fraction;

import java.util.List;

public interface ExpressionNode {
    Fraction evaluate();
    String toString();
    List<String> toExpressionList();
}