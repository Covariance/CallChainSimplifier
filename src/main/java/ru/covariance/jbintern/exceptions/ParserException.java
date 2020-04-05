package ru.covariance.jbintern.exceptions;

public class ParserException extends RuntimeException {
    public ParserException(String message) {
        super("Parses exception: " + message);
    }
}
