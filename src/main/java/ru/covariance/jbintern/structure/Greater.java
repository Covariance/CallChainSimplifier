package structure;

public final class Greater extends ComparativeExpression {
    public Greater(Polynomial left) {
        super(left);
    }

    protected String getSign() {
        return ">";
    }

    @Override
    public boolean evaluate(int element) {
        return poly.evaluate(element) > 0;
    }
}
