package structure;

public interface Expression {
    boolean isPolynomial();
    void compose(Polynomial poly);
}
