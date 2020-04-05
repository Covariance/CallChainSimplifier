package ru.covariance.jbintern.structure;

import java.util.ArrayList;
import java.util.List;

public final class Mapper implements Chainable {
    private final Polynomial mapper;

    public Mapper(Polynomial mapper) {
        this.mapper = mapper;
    }

    public Polynomial getMapper() {
        return mapper;
    }

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
