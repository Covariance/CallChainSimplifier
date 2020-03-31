package parser;

import exceptions.ParserException;

public class BaseParser {
    private final StringSource source;
    protected char ch;

    protected BaseParser(final StringSource source) {
        this.source = source;
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
            throw error("Expected '" + c + "', found '" + ch + "'");
        }
        nextChar();
    }

    protected void expect(final String value) {
        for (char c : value.toCharArray()) {
            expect(c);
        }
    }

    protected void skipWhitespace() {
        while (ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r') {
            nextChar();
        }
    }

    protected ParserException error(final String message) {
        return source.error(message);
    }

    protected boolean between() {
        return '0' <= ch && ch <= '9';
    }
}
