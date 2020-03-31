import parser.CallChainParser;
import structure.Polynomial;

public class Test {
    public static void main(String[] args) {
        Polynomial poly = (new Polynomial(1, 2)).add(new Polynomial(2, 1)).add(new Polynomial(1, 0));
        Polynomial poly2 = new Polynomial(2, 1);
        System.out.println(poly.compose(poly2));
        System.out.println(poly2.compose(poly));
    }
}
