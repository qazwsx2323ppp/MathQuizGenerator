package com.mathquiz;

public enum Operator {
    ADD("+", 1),
    SUBTRACT("-", 1),
    MULTIPLY("×", 2),
    DIVIDE("÷", 2);

//    ADD(1), SUBTRACT(1), MULTIPLY(2), DIVIDE(2);

    private final int priority;
    public int getPriority() { return priority; }

    private final String symbol;

    Operator(String symbol, int priority) {
        this.symbol = symbol;
        this.priority = priority;
    }

    public static Operator fromSymbol(String symbol) {
        for (Operator op : values()) {
            if (op.symbol.equals(symbol)) return op;
        }
        throw new IllegalArgumentException("无效运算符: " + symbol);
    }

    public String getSymbol() {
        return symbol;
    }


}