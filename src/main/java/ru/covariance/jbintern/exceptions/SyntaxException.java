package exceptions;

public class SyntaxException extends ParserException {
    public SyntaxException(String message) {
        super("Syntax error in: " + message);
    }
}
