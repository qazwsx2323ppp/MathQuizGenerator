//public class Fraction {
//    private int numerator;
//    private int denominator;
//
//    // 构造函数和getter/setter方法
//    public Fraction(int numerator, int denominator) {
//        this.numerator = numerator;
//        this.denominator = denominator;
//    }
//
//    // 分数运算方法：加法、减法、乘法、除法
//    public Fraction add(Fraction other) {
//        int newNumerator = this.numerator * other.denominator + other.numerator * this.denominator;
//        int newDenominator = this.denominator * other.denominator;
//        return new Fraction(newNumerator, newDenominator).simplify();
//    }
//
//    public Fraction subtract(Fraction other) {
//        int newNumerator = this.numerator * other.denominator - other.numerator * this.denominator;
//        int newDenominator = this.denominator * other.denominator;
//        return new Fraction(newNumerator, newDenominator).simplify();
//    }
//
//    public Fraction multiply(Fraction other) {
//        return new Fraction(this.numerator * other.numerator, this.denominator * other.denominator).simplify();
//    }
//
//    public Fraction divide(Fraction other) {
//        return new Fraction(this.numerator * other.denominator, this.denominator * other.numerator).simplify();
//    }
//
//    // 最简分数化简
//    public Fraction simplify() {
//        int gcd = gcd(numerator, denominator);
//        return new Fraction(numerator / gcd, denominator / gcd);
//    }
//
//    private int gcd(int a, int b) {
//        return b == 0 ? a : gcd(b, a % b);
//    }
//}

public class Fraction {
    private int numerator; // 分子
    private int denominator; // 分母
    private int wholePart; // 带分数的整数部分（默认为0）

    // 构造函数
    public Fraction(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("分母不能为0");
        }
        this.numerator = numerator;
        this.denominator = denominator;
        this.wholePart = 0;
        simplify(); // 构造时自动简化分数
    }

    // Getter和Setter方法
    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public int getWholePart() {
        return wholePart;
    }

    // 分数运算方法：加法
    public Fraction add(Fraction other) {
        int newNumerator = this.numerator * other.denominator + other.numerator * this.denominator;
        int newDenominator = this.denominator * other.denominator;
        return new Fraction(newNumerator, newDenominator).simplify();
    }

    // 分数运算方法：减法
    public Fraction subtract(Fraction other) {
        int newNumerator = this.numerator * other.denominator - other.numerator * this.denominator;
        int newDenominator = this.denominator * other.denominator;
        return new Fraction(newNumerator, newDenominator).simplify();
    }

    // 分数运算方法：乘法
    public Fraction multiply(Fraction other) {
        return new Fraction(this.numerator * other.numerator, this.denominator * other.denominator).simplify();
    }

    // 分数运算方法：除法
    public Fraction divide(Fraction other) {
        if (other.numerator == 0) {
            throw new ArithmeticException("除数不能为0");
        }
        return new Fraction(this.numerator * other.denominator, this.denominator * other.numerator).simplify();
    }

    // 最简分数化简
    public Fraction simplify() {
        // 计算最大公约数
        int gcd = gcd(numerator, denominator);

        // 简化分子和分母
        numerator /= gcd;
        denominator /= gcd;

        // 如果分子大于分母，转换为带分数
        if (Math.abs(numerator) >= denominator) {
            wholePart = numerator / denominator; // 计算整数部分
            numerator = numerator % denominator; // 更新分子为余数
        } else {
            wholePart = 0; // 如果是真分数，整数部分为0
        }

        // 确保分母为正数
        if (denominator < 0) {
            denominator = -denominator;
            numerator = -numerator;
        }

        return this;
    }

    // 辅助方法：计算最大公约数
    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    // 转换为字符串表示
    @Override
    public String toString() {
        if (wholePart == 0) {
            // 如果没有整数部分，直接返回分数形式
            return numerator + "/" + denominator;
        } else if (numerator == 0) {
            // 如果分子为0，直接返回整数部分
            return String.valueOf(wholePart);
        } else {
            // 返回带分数形式
            return wholePart + "'" + numerator + "/" + denominator;
        }
    }

    // 测试代码
//    public static void main(String[] args) {
//        Fraction f1 = new Fraction(11, 4);
//        Fraction f2 = new Fraction(3, 2);
//
//        System.out.println("f1: " + f1); // 输出：2'3/4
//        System.out.println("f2: " + f2); // 输出：1'1/2
//
//        Fraction sum = f1.add(f2);
//        System.out.println("f1 + f2: " + sum); // 输出：3'5/8
//
//        Fraction diff = f1.subtract(f2);
//        System.out.println("f1 - f2: " + diff); // 输出：1'1/4
//
//        Fraction prod = f1.multiply(f2);
//        System.out.println("f1 * f2: " + prod); // 输出：1'21/32
//
//        Fraction quot = f1.divide(f2);
//        System.out.println("f1 / f2: " + quot); // 输出：1'5/6
//    }
}
