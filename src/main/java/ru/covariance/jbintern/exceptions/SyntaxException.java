package ru.covariance.jbintern.exceptions;

/**
 * Thrown to indicate that a parse method of a CallChainParsed failed because of syntax exception
 */
public class SyntaxException extends ParserException {
    /**
     * Constructs new SyntaxException with empty method
     */
    public SyntaxException() {
        this("");
    }

    /**
     * Constructs new SyntaxException with given message
     * @param message message to send
     */
    public SyntaxException(String message) {
        super("Syntax error in: " + message);
    }
}
