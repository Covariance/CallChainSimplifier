package parser;

import exceptions.SyntaxException;
import exceptions.TypeMismatchException;
import structure.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CallChainParser {
    public CallChain parse(final String source) {
        return new InnerParser(new StringSource(source)).parseChain();
    }

    public interface PolynomialAction {
        Polynomial apply(Polynomial a, Polynomial b);
    }

    public final static Map<Character, PolynomialAction> POLY_ACTION = Map.of(
            '+', Polynomial::add,
            '-', Polynomial::subtract,
            '*', Polynomial::multiply
    );

    public interface ComparativeExpressionCreator {
        ComparativeExpression create(Polynomial a);
    }

    public final static Map<Character, ComparativeExpressionCreator> COMP_EXPR = Map.of(
            '<', Less::new,
            '>', Greater::new,
            '=', Equals::new
    );

    public interface LogicalExpressionCreator {
        LogicalExpression create(BooleanExpression left, BooleanExpression right);
    }

    public final static Map<Character, LogicalExpressionCreator> LOGIC_EXPR = Map.of(
            '&', And::new,
            '|', Or::new
    );

    private static class InnerParser extends BaseParser {
        protected InnerParser(final StringSource source) {
            super(source);
            nextChar();
        }

        private boolean callChainEndCheck() {
            if (test('%')) {
                expect(">%");
                return false;
            }
            return true;
        }

        /*
         *  <call> | <call> “%>%” <call-chain>
         */
        public CallChain parseChain() {
            List<Chainable> result = new ArrayList<>();
            while (true) {
                if (test('f')) {
                    expect("ilter{");
                    result.add(parseFilter());
                    expect('}');
                    if (callChainEndCheck()) {
                        break;
                    }
                } else if (test('m')) {
                    expect("ap{");
                    result.add(parseMapper());
                    expect('}');
                    if (callChainEndCheck()) {
                        break;
                    }
                } else {
                    break;
                }
            }
            nextChar();
            if (ch != '\0') {
                throw new SyntaxException("Non-empty tail after parsing call chain");
            }
            return new CallChain(result);
        }

        /*
         *  "filter{" <expression> "}"
         */
        private Filter parseFilter() {
            expect('(');
            Expression result = parseExpression();
            expect(')');
            if (result instanceof Polynomial) {
                throw new TypeMismatchException("logical", result.toString());
            }
            return new Filter((BooleanExpression) result);
        }

        /*
         *  "map{" <expression> "}"
         */
        private Mapper parseMapper() {
            if (ch != '(') {
                return new Mapper(parsePrimal());
            }
            expect('(');
            Expression result = parseExpression();
            expect(')');
            if (!(result instanceof Polynomial)) {
                throw new TypeMismatchException("arithmetical", result.toString());
            }
            return new Mapper((Polynomial) result);
        }

        /*
         * <expression> ::= "element" | <constant-expression> | <binary-expression>
         */
        private Expression parseExpressionWrapper() {
            if (test('(')) {
                Expression result = parseExpression();
                expect(')');
                return result;
            }
            return parsePrimal();
        }


        private TypeMismatchException typeError(String expected, Expression left, Expression right) {
            return new TypeMismatchException(expected, left.toString() + " and " + right.toString());
        }

        /*
         *  <binary-expression> ::= "(" <expression> <operation> <expression> ")"
         */
        private Expression parseExpression() {
            Expression left = parseExpressionWrapper();
            Character op = ch;
            nextChar();
            Expression right = parseExpressionWrapper();
            if (POLY_ACTION.containsKey(op)) {
                if (!(left instanceof Polynomial) || !(right instanceof Polynomial)) {
                    throw typeError("polynomial", left, right);
                }
                return POLY_ACTION.get(op).apply((Polynomial) left, (Polynomial) right);
            }
            if (COMP_EXPR.containsKey(op)) {
                if (!(left instanceof Polynomial) || !(right instanceof Polynomial)) {
                    throw typeError("polynomial", left, right);
                }
                return COMP_EXPR.get(op).create(Polynomial.subtract((Polynomial) left, (Polynomial) right));
            }
            if (LOGIC_EXPR.containsKey(op)) {
                if (left instanceof Polynomial || right instanceof Polynomial) {
                    throw typeError("logical", left, right);
                }
                return LOGIC_EXPR.get(op).create((BooleanExpression) left, (BooleanExpression) right);
            }
            throw new SyntaxException("Unknown operation symbol: " + op);
        }

        /*
         *  <number> | "element"
         */
        private Polynomial parsePrimal() {
            if (test('e')) {
                expect("lement");
                return new Polynomial(1, 1);
            }
            return new Polynomial(parseConstant(), 0);
        }

        /*
         *  <constant-expression> ::= "-" <number> | <number>
         *  <number> ::= <digit> | <digit> <number>
         */
        private int parseConstant() {
            StringBuilder result = new StringBuilder();
            if (test('-')) {
                result.append('-');
            }
            while (between()) {
                result.append(ch);
                nextChar();
            }
            return Integer.parseInt(result.toString());
        }
    }
}
