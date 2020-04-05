package ru.covariance.jbintern.structure;

public final class Less extends ComparativeExpression {
    public Less(Polynomial left) {
        super(left);
    }

    protected String getSign() {
        return "<";
    }

    @Override
    public boolean evaluate(int element) {
        return poly.evaluate(element) < 0;
    }
}
