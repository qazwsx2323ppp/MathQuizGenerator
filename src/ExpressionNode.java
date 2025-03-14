import java.util.List;

public interface ExpressionNode {
    Fraction evaluate();
    String toString();
    List<String> toExpressionList();
}