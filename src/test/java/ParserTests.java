import org.junit.Test;
import org.junit.Assert;
import parser.CallChainParser;
import structure.*;

import java.util.*;

public class ParserTests {
    private Polynomial generatePolynomial(int degree, Random rand) {
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

    private ComparativeExpression generateComparative(int degree, Random rand) {
        return COMP_CREATORS.get(rand.nextInt(COMP_CREATORS.size())).create(generatePolynomial(degree, rand));
    }

    private static final List<CallChainParser.LogicalExpressionCreator> LOGIC_CREATORS = List.of(
            CallChainParser.LOGIC_EXPR.get('&'),
            CallChainParser.LOGIC_EXPR.get('|')
    );

    private LogicalExpression generateLogical(BooleanExpression a, BooleanExpression b, Random rand) {
        return LOGIC_CREATORS.get(rand.nextInt(LOGIC_CREATORS.size())).create(a, b);
    }

    private BooleanExpression generateBooleanExpression(int depth, int degree, Random rand) {
        Queue<BooleanExpression> q = new ArrayDeque<>();
        for (int i = 0; i < 1 << (depth - 1); i++) {
            q.add(generateComparative(degree, rand));
        }
        while (q.size() != 1) {
            q.add(generateLogical(q.remove(), q.remove(), rand));
        }
        return q.peek();
    }

    private CallChain generateCallChain(int length, int depth, int degree, Random rand) {
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

    @Test
    public void randomExpressionsParsingTest() {
        CallChainParser parser = new CallChainParser();
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                for (int k = 0; k < 100; k++) {
                    CallChain test = generateCallChain((i / 10) + 1, (j / 10) + 1, (k / 10) + 1, rand);
                    Assert.assertEquals(test.toString(), parser.parse(test.toString()).toString());
                }
            }
        }
    }
}
