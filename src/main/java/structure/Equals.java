package structure;

public final class Equals extends ComparativeExpression {
    public Equals(Polynomial left) {
        super(left);
    }

    protected String getSign() {
        return "=";
    }

    @Override
    public boolean evaluate(int element) {
        return poly.evaluate(element) == 0;
    }
}
