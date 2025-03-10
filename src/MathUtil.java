public class MathUtil {

    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    // 可以添加其他数学辅助方法
}
