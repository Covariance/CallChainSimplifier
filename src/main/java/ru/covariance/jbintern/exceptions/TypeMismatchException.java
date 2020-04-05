package ru.covariance.jbintern.exceptions;

public class TypeMismatchException extends ParserException {
    public TypeMismatchException(String expected, String got) {
        super("Type mismatch exception: expected " + expected + ", got: " + got);
    }
}
