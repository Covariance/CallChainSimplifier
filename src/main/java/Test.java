import parser.CallChainParser;
import structure.Polynomial;

public class Test {
    public static void main(String[] args) {
        Polynomial poly = Polynomial.add(new Polynomial(1, 2), Polynomial.add(new Polynomial(2, 1), new Polynomial(1, 0)));
        Polynomial poly2 = new Polynomial(2, 1);
        System.out.println(Polynomial.compose(poly, poly2));
        System.out.println(Polynomial.compose(poly2, poly));
    }
}
