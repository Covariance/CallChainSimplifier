package structure;

public final class Polynomial implements Expression {
    private int[] k;   // coefficients p(x) = sum { coef[i] * x^i }
    private int degree;   // degree of polynomial (-1 for the zero polynomial)

    public Polynomial() {
        k = new int[0];
        degree = -1;
    }

    // creates a * x^b
    public Polynomial(int a, int b) {
        k = new int[b + 1];
        k[b] = a;
        reduce();
    }

    // pre-compute the degree of the polynomial, in case of leading zero coefficients
    // (that is, the length of the array need not relate to the degree of the polynomial)
    private void reduce() {
        degree = -1;
        for (int i = k.length - 1; i >= 0; i--) {
            if (k[i] != 0) {
                degree = i;
                return;
            }
        }
    }

    public static Polynomial add(Polynomial a, Polynomial b) {
        Polynomial poly = new Polynomial(0, Math.max(a.degree, b.degree));
        for (int i = 0; i <= a.degree; i++) {
            poly.k[i] += a.k[i];
        }
        for (int i = 0; i <= b.degree; i++) {
            poly.k[i] += b.k[i];
        }
        poly.reduce();
        return poly;
    }

    public static Polynomial subtract(Polynomial a, Polynomial b) {
        Polynomial poly = new Polynomial(0, Math.max(a.degree, b.degree));
        for (int i = 0; i <= a.degree; i++) {
            poly.k[i] += a.k[i];
        }
        for (int i = 0; i <= b.degree; i++) {
            poly.k[i] -= b.k[i];
        }
        poly.reduce();
        return poly;
    }

    public static Polynomial multiply(Polynomial a, Polynomial b) {
        Polynomial poly = new Polynomial(0, a.degree + b.degree);
        for (int i = 0; i <= a.degree; i++) {
            for (int j = 0; j <= b.degree; j++) {
                poly.k[i + j] += (a.k[i] * b.k[j]);
            }
        }
        poly.reduce();
        return poly;
    }

    public void compose(Polynomial inner) {
        Polynomial poly = new Polynomial(0, 0);
        for (int i = this.degree; i >= 0; i--) {
            Polynomial term = new Polynomial(this.k[i], 0);
            poly = add(term, multiply(inner, poly));
        }
        poly.reduce();
        this.k = poly.k;
        this.degree = poly.degree;
    }

    public int evaluate(int x) {
        int p = 0;
        for (int i = degree; i >= 0; i--) {
            p = k[i] + (x * p);
        }
        return p;
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
