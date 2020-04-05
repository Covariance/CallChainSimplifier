package ru.covariance.jbintern.exceptions;

/**
 * Thrown to indicate that a parse method of a CallChainParsed failed because of type mismatch exception
 */
public class TypeMismatchException extends ParserException {
    /**
     * Constructs new TypeMismatchException with a given expected and got types
     * @param expected expected expression type
     * @param got got expression type
     */
    public TypeMismatchException(String expected, String got) {
        super("Type mismatch exception: expected " + expected + ", got: " + got);
    }
}
