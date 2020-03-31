package structure;

import structure.arithmetic.ArithmeticalExpression;

import java.util.ArrayList;
import java.util.List;

public final class Mapper implements Chainable {
    private final ArithmeticalExpression mapper;

    public Mapper(ArithmeticalExpression mapper) {
        this.mapper = mapper;
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
    public String toString() {
        return "map{" + mapper.toString() + "}";
    }
}
