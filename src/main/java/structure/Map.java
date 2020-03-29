package structure;

import structure.arithmetic.ArithmeticExpression;

import java.util.ArrayList;
import java.util.List;

public final class Map implements Chainable {
    private final ArithmeticExpression mapper;

    public Map(ArithmeticExpression mapper) {
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
}
