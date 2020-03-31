package parser;

import exceptions.ParserException;
import structure.*;
import structure.arithmetic.*;
import structure.logical.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CallChainParser {
    public CallChain parse(final String source) {
        return new InnerParser(new StringSource(source)).parseChain();
    }

    private static class InnerParser extends BaseParser {
        interface ArithmeticalBinOpsCreator {
            AbstractBinaryOperator create(ArithmeticalExpression left, ArithmeticalExpression right);
        }

        interface LogicalBinOpsCreator {
            AbstractBinaryOperator create(LogicalExpression left, LogicalExpression right);
        }

        private final static Map<Character, ArithmeticalBinOpsCreator> ARITHMETICAL_BIN_OPS = Map.of(
                '+', Add::new,
                '-', Subtract::new,
                '*', Multiply::new,
                '<', Less::new,
                '>', Greater::new,
                '=', Equals::new
        );

        private final static Map<Character, LogicalBinOpsCreator> LOGICAL_BIN_OPS = Map.of(
                '&', And::new,
                '|', Or::new
        );

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
                throw new ParserException("CallChain parsed, but there's something else in the source"); //fixme
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
            if (result.isArithmetical()) {
                throw new ParserException("Type mismatch: expected logical, got: " + result.toString());
            }
            return new Filter((LogicalExpression) result);
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
            if (!result.isArithmetical()) {
                throw new ParserException("Type mismatch: expected arithmetical, got:" + result.toString());
            }
            return new Mapper((ArithmeticalExpression) result);
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


        /*
         *  <binary-expression> ::= "(" <expression> <operation> <expression> ")"
         */
        private Expression parseExpression() {
            Expression left = parseExpressionWrapper();
            Character op = ch;
            nextChar();
            Expression right = parseExpressionWrapper();
            if (ARITHMETICAL_BIN_OPS.containsKey(op)) {
                if (!left.isArithmetical() || !right.isArithmetical()) {
                    throw new ParserException("Type mismatch: expected logical, got: " + left + " and " + right); //fixme
                }
                return ARITHMETICAL_BIN_OPS.get(op).create((ArithmeticalExpression) left, (ArithmeticalExpression) right);
            } else if (LOGICAL_BIN_OPS.containsKey(op)) {
                if (left.isArithmetical() || right.isArithmetical()) {
                    throw new ParserException("Type mismatch: expected logical, got: " + left + " and " + right); //fixme
                }
                return LOGICAL_BIN_OPS.get(op).create((LogicalExpression) left, (LogicalExpression) right);
            } else {
                throw new ParserException("Unknown binary operator: " + op); //fixme
            }
        }

        /*
         *  <number> | "element"
         */
        private ArithmeticalExpression parsePrimal() {
            if (test('e')) {
                expect("lement");
                return new Variable();
            }
            return new Const(parseConstant());
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
            while (between('0', '9')) {
                result.append(ch);
                nextChar();
            }
            return Integer.parseInt(result.toString());
        }
    }
}
