package ru.covariance.jbintern.exceptions;

/**
 * Thrown to indicate that a parse method of a CallChainParser failed
 */
public class ParserException extends RuntimeException {
    /**
     * Constructs new ParserException with no message
     */
    public ParserException() {
        this("");
    }

    /**
     * Constructs new ParserException with given message
     * @param message message to send
     */
    public ParserException(String message) {
        super("Parses exception: " + message);
    }
}
