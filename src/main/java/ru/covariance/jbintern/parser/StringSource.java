package ru.covariance.jbintern.parser;

/**
 * Object that is wrapped around String to provide more convenient way to access it - like
 * a stream of characters
 */
public class StringSource {
    private final String data;
    private int pos;

    /**
     * Constructs new StringSource from given String
     * @param data String to construct StringSource from
     */
    public StringSource(final String data) {
        this.data = data;
    }

    /**
     * Returns true only if there is next character in the source
     * @return true only if there is next character in the source
     */
    public boolean hasNext() {
        return pos < data.length();
    }

    /**
     * Returns next character from the source string
     * @return next character from the source string
     * @throws StringIndexOutOfBoundsException if there is no characters in the source string
     */
    public char next() throws StringIndexOutOfBoundsException {
        return data.charAt(pos++);
    }
}