package ru.covariance.jbintern.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Object that represent part of call chain that makes operation with provided list
 */
public final class Mapper implements Chainable {
    private final Polynomial mapper;

    /**
     * Constructs new Mapper with a mapper equal to given Polynomial
     * @param mapper given Polynomial
     */
    public Mapper(Polynomial mapper) {
        this.mapper = mapper;
    }

    /**
     * Returns mapping Polynomial of this Mapper
     * @return mapping Polynomial of this Mapper
     */
    public Polynomial getMapper() {
        return mapper;
    }

    /**
     * Applies mapping polynomial to every element of the given list
     * @param array given list of Integers
     * @return new Integer list, consisting of results of appliance of mapper to elements of given list
     */
    @Override
    public List<Integer> apply(List<Integer> array) {
        List<Integer> result = new ArrayList<>();
        for (Integer a : array) {
            result.add(mapper.evaluate(a));
        }
        return result;
    }

    @Override
    public void compose(Polynomial poly) {
        mapper.compose(poly);
    }

    @Override
    public String toMiniString() {
        return "map{" + mapper.toMiniString() + "}";
    }

    @Override
    public String toString() {
        return "map{" + mapper.toString() + "}";
    }
}
