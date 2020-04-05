package ru.covariance.jbintern.parser;

import ru.covariance.jbintern.exceptions.ParserException;
import ru.covariance.jbintern.exceptions.SyntaxException;
import ru.covariance.jbintern.exceptions.TypeMismatchException;
import ru.covariance.jbintern.structure.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Object that parses expressions according to given grammar:
 * <digit>   ::= “0” | “1" | “2” | “3" | “4” | “5" | “6” | “7" | “8” | “9"
 * <number> ::= <digit> | <digit> <number>
 * <operation> ::= “+” | “-” | “*” | “>” | “<” | “=” | “&” | “|”
 * <constant-expression> ::= “-” <number> | <number>
 * <binary-expression> ::= “(” <expression> <operation> <expression> “)”
 * <expression> ::= “element” | <constant-expression> | <binary-expression>
 * <map-call> ::= “map{” <expression> “}”
 * <filter-call> ::= “filter{” <expression> “}”
 * <call> ::= <map-call> | <filter-call>
 * <call-chain> ::= <call> | <call> “%>%” <call-chain>
 */
public class CallChainParser {
    /**
     * Parses given expression into CallChain
     * @param source source to parse
     * @return CallChain corresponding to given source
     * @throws ParserException if source does not correspond the expression grammar
     */
    public CallChain parse(final String source) throws ParserException, NumberFormatException {
        try {
            return new InnerParser(new StringSource(source)).parseChain();
        } catch (NumberFormatException e) {
            throw new SyntaxException("wrong number format: " + e.getMessage());
        }
    }

    /**
     * An object that applies action to two Polynomials and returns its result
     */
    public interface PolynomialAction {
        /**
         * Apply action to given Polynomials and returns its result
         * @param a left operand
         * @param b right operand
         * @return result of operation
         */
        Polynomial apply(Polynomial a, Polynomial b);
    }

    /**
     * Maps characters that represent arithmetical actions to those actions
     */
    public final static Map<Character, PolynomialAction> POLY_ACTION = Map.of(
            '+', Polynomial::add,
            '-', Polynomial::subtract,
            '*', Polynomial::multiply
    );

    /**
     * An object that creates new ComparativeExpression from given Polynomial
     */
    public interface ComparativeExpressionCreator {
        /**
         * Create new ComparativeExpression from given Polynomial
         * @param a Polynomial to create CommonExpression from
         * @return new ComparativeExpression
         */
        ComparativeExpression create(Polynomial a);
    }

    /**
     * Maps characters that represent comparative operators to their creators
     */
    public final static Map<Character, ComparativeExpressionCreator> COMP_EXPR = Map.of(
            '<', Less::new,
            '>', Greater::new,
            '=', Equals::new
    );

    /**
     * An object that creates new LogicalExpression from two given BooleanExpressions
     */
    public interface LogicalExpressionCreator {
        /**
         * Create new LogicalExpression from two given Boolean expression
         * @param left left operand of LogicalExpression
         * @param right right operand of LogicalExpression
         * @return new LogicalExpression
         */
        LogicalExpression create(BooleanExpression left, BooleanExpression right);
    }

    /**
     * Maps characters that represent logical operators to their creators
     */
    public final static Map<Character, LogicalExpressionCreator> LOGIC_EXPR = Map.of(
            '&', And::new,
            '|', Or::new
    );

    private static class InnerParser {
        private final StringSource source;
        private char ch;

        protected InnerParser(final StringSource source) {
            this.source = source;
            nextChar();
        }

        protected void nextChar() {
            ch = source.hasNext() ? source.next() : '\0';
        }

        protected boolean test(char expected) {
            if (ch == expected) {
                nextChar();
                return true;
            }
            return false;
        }

        protected void expect(final char c) {
            if (ch != c) {
                throw new SyntaxException("Expected '" + c + "', found '" + ch + "'");
            }
            nextChar();
        }

        protected void expect(final String value) {
            for (char c : value.toCharArray()) {
                expect(c);
            }
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
        private CallChain parseChain() {
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
            if (ch != '(') {
                parsePrimal();
                throw new TypeMismatchException("logical", "arithmetical");
            }
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
            while ('0' <= ch && ch <= '9') {
                result.append(ch);
                nextChar();
            }
            return Integer.parseInt(result.toString());
        }
    }
}
