package parser;

import exceptions.ParserException;

public class StringSource {
    private final String data;
    private int pos;

    public StringSource(final String data) {
        this.data = data;
    }

    public boolean hasNext() {
        return pos < data.length();
    }

    public char next() {
        return data.charAt(pos++);
    }

    public ParserException error(final String message) {
        return new ParserException(pos + ": " + message);
    }
}