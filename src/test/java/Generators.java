import parser.CallChainParser;
import structure.*;

import java.util.*;

public class Generators {
    public static Polynomial generatePolynomial(int degree, Random rand) {
        Polynomial result = new Polynomial();
        for (int i = 0; i < degree; i++) {
            result = Polynomial.add(result, new Polynomial(1 + rand.nextInt(10), i));
        }
        return result;
    }

    private static final List<CallChainParser.ComparativeExpressionCreator> COMP_CREATORS = List.of(
            CallChainParser.COMP_EXPR.get('>'),
            CallChainParser.COMP_EXPR.get('<'),
            CallChainParser.COMP_EXPR.get('=')
    );

    public static ComparativeExpression generateComparative(int degree, Random rand) {
        return COMP_CREATORS.get(rand.nextInt(COMP_CREATORS.size())).create(generatePolynomial(degree, rand));
    }

    private static final List<CallChainParser.LogicalExpressionCreator> LOGIC_CREATORS = List.of(
            CallChainParser.LOGIC_EXPR.get('&'),
            CallChainParser.LOGIC_EXPR.get('|')
    );

    public static LogicalExpression generateLogical(BooleanExpression a, BooleanExpression b, Random rand) {
        return LOGIC_CREATORS.get(rand.nextInt(LOGIC_CREATORS.size())).create(a, b);
    }

    public static BooleanExpression generateBooleanExpression(int depth, int degree, Random rand) {
        Queue<BooleanExpression> q = new ArrayDeque<>();
        for (int i = 0; i < 1 << (depth - 1); i++) {
            q.add(generateComparative(degree, rand));
        }
        while (q.size() != 1) {
            q.add(generateLogical(q.remove(), q.remove(), rand));
        }
        return q.peek();
    }

    public static CallChain generateCallChain(int length, int depth, int degree, Random rand) {
        List<Chainable> result = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            if ((rand.nextInt() & 1) == 0) {
                result.add(new Mapper(generatePolynomial(degree, rand)));
            } else {
                result.add(new Filter(generateBooleanExpression(depth, degree, rand)));
            }
        }
        return new CallChain(result);
    }
}
