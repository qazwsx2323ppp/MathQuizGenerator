package com.mathquiz;

import com.mathquiz.ExpressionNode;
import com.mathquiz.Fraction;

import java.util.Collections;
import java.util.List;

public class NumberNode implements ExpressionNode {
    private final Fraction fraction;

    public NumberNode(Fraction fraction) {
        this.fraction = fraction;
    }

    @Override
    public Fraction evaluate() {
        return fraction;
    }

    @Override
    public String toString() {
        return fraction.toString();
    }

    @Override
    public List<String> toExpressionList() {
        return Collections.singletonList(fraction.toString());
    }
}