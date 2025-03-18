package com.mathquiz;

import java.util.Random;

public class Fraction {
    private final int numerator;
    private final int denominator;
    private static int range = 10;
    private static Random random = new Random();

    public Fraction(int numerator, int denominator) {
        if (denominator == 0) throw new IllegalArgumentException("分母不能为零");
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }
        int gcd = gcd(Math.abs(numerator), denominator);
        this.numerator = numerator / gcd;
        this.denominator = denominator / gcd;
    }

    // 新增获取分子的方法
    public int getNumerator() {
        return numerator;
    }

    // 新增获取分母的方法（可选，但推荐）
    public int getDenominator() {
        return denominator;
    }

//    public static Fraction generate(int max) {
//        int denominator = random.nextInt(max - 1) + 1;
//        int numerator = random.nextInt(max * 2) - max;
//        return new Fraction(numerator, denominator);
//    }

    public static Fraction generate(int range) {
        if (range <= 0) {
            throw new IllegalArgumentException("range 必须大于零");
        }
        Random random = new Random();
        int numerator = random.nextInt(range);   // [0, range-1]
        int denominator = random.nextInt(range) + 1; // [1, range]
        return new Fraction(numerator, denominator);
    }

    public Fraction add(Fraction other) {
        int newDenominator = this.denominator * other.denominator;
        int newNumerator = this.numerator * other.denominator + other.numerator * this.denominator;
        return new Fraction(newNumerator, newDenominator);
    }

    public Fraction subtract(Fraction other) {

        int newDenominator = this.denominator * other.denominator;
        if (newDenominator == 0) {
            throw new ArithmeticException("减法操作中生成了分母为零的分数");
        }
        int newNumerator = this.numerator * other.denominator - other.numerator * this.denominator;
        return new Fraction(newNumerator, newDenominator);
    }

    public Fraction multiply(Fraction other) {
        int newNumerator = this.numerator * other.numerator;
        int newDenominator = this.denominator * other.denominator;
        return new Fraction(newNumerator, newDenominator);
    }

    public Fraction divide(Fraction other) {
        if (other.numerator == 0) {
            throw new ArithmeticException("不能除以零分数");
        }
        int newNumerator = this.numerator * other.denominator;
        int newDenominator = this.denominator * other.numerator;
        return new Fraction(newNumerator, newDenominator);
    }


    @Override
    public String toString() {
        if (denominator == 1) return String.valueOf(numerator);
        int whole = numerator / denominator;
        int remainder = Math.abs(numerator % denominator);
        if (whole == 0) return numerator + "/" + denominator;  // 直接显示分子分母
        return whole + "'" + remainder + "/" + denominator;
    }

    private static int gcd(int a, int b) {
        if (a == 0 && b == 0) {
            throw new ArithmeticException("gcd(0, 0) 未定义");
        }
        return b == 0 ? a : gcd(b, a % b);
    }


}