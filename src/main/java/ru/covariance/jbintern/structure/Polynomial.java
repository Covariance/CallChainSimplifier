package ru.covariance.jbintern.structure;

/**
 * Object, that stores arithmetical expression represented by polynomial
 */
public final class Polynomial implements Expression {
    private int[] k;
    private int degree;

    /**
     * Constructs new zero Polynomial
     */
    public Polynomial() {
        k = new int[0];
        degree = -1;
    }

    /**
     * Constructs new Polynomial with a value of a * x^b
     * @param a coefficient of a term
     * @param b power of a term
     */
    public Polynomial(int a, int b) {
        k = new int[b + 1];
        k[b] = a;
        reduce();
    }

    private void reduce() {
        degree = -1;
        for (int i = k.length - 1; i >= 0; i--) {
            if (k[i] != 0) {
                degree = i;
                return;
            }
        }
    }

    /**
     * Polynomial action that returns result of adding two given Polynomials
     * @param a left operand
     * @param b right operand
     * @return result of adding two given Polynomials
     */
    public static Polynomial add(Polynomial a, Polynomial b) {
        Polynomial result = new Polynomial(0, Math.max(Math.max(a.degree, b.degree), 0));
        for (int i = 0; i <= a.degree; i++) {
            result.k[i] += a.k[i];
        }
        for (int i = 0; i <= b.degree; i++) {
            result.k[i] += b.k[i];
        }
        result.reduce();
        return result;
    }

    /**
     * Polynomial action that returns result of subtraction of two given Polynomials
     * @param a left operand
     * @param b right operand
     * @return result of subtraction of two given Polynomials
     */
    public static Polynomial subtract(Polynomial a, Polynomial b) {
        Polynomial result = new Polynomial(0, Math.max(Math.max(a.degree, b.degree), 0));
        for (int i = 0; i <= a.degree; i++) {
            result.k[i] += a.k[i];
        }
        for (int i = 0; i <= b.degree; i++) {
            result.k[i] -= b.k[i];
        }
        result.reduce();
        return result;
    }

    /**
     * Polynomial action that returns product of two given Polynomials
     * @param a left operand
     * @param b right operand
     * @return product of two given Polynomials
     */
    public static Polynomial multiply(Polynomial a, Polynomial b) {
        Polynomial result = new Polynomial(0, Math.max(a.degree + b.degree, 0));
        for (int i = 0; i <= a.degree; i++) {
            for (int j = 0; j <= b.degree; j++) {
                result.k[i + j] += a.k[i] * b.k[j];
            }
        }
        result.reduce();
        return result;
    }

    /**
     * Composes this Polynomial with given Polynomial, that is f(g(x)) if this Polynomial is f(x)
     * and given Polynomial is g(x)
     * @param inner Polynomial to compose with
     */
    public void compose(Polynomial inner) {
        Polynomial poly = new Polynomial(0, 0);
        for (int i = this.degree; i >= 0; i--) {
            Polynomial term = new Polynomial(this.k[i], 0);
            poly = add(term, multiply(inner, poly));
        }
        this.k = poly.k;
        this.degree = poly.degree;
        reduce();
    }

    /**
     * Returns value of this Polynomial when x is equal to given value
     * @param x given value
     * @return value of this Polynomial when x is equal to given value
     */
    public int evaluate(int x) {
        int p = 0;
        for (int i = degree; i >= 0; i--) {
            p = k[i] + (x * p);
        }
        return p;
    }

    /**
     * Returns representation of this Polynomial without products with ones
     * and sums with zeros
     * @return minimal representation of this Polynomial
     */
    public String toMiniString() {
        reduce();
        if (degree == -1) {
            return "0";
        }
        if (degree == 0) {
            return Integer.toString(k[0]);
        }
        if (degree == 1 && k[0] == 0 && k[1] == 1) {
            return "element";
        }
        int cntOfClosing = 0;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < degree; i++) {
            if (k[i] != 0) {
                cntOfClosing++;
                result.append('(').append(k[i]).append('+');
            }
            if (i != degree - 1) {
                result.append("(element").append('*');
            }
        }
        if (k[degree] != 1) {
            result.append("(element").append('*').append(k[degree]);
        } else {
            result.append("element");
            cntOfClosing--;
        }
        result.append(")".repeat(degree + cntOfClosing));
        return result.toString();
    }

    @Override
    public String toString() {
        if (degree == -1) {
            return "0";
        }
        if (degree == 0) {
            return Integer.toString(k[0]);
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < degree; i++) {
            result.append('(').append(k[i]).append('+').append("(element").append('*');
        }
        result.append(k[degree]).append(")".repeat(2 * degree));
        return result.toString();
    }
}
