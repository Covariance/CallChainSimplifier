package structure;

import structure.logical.LogicalExpression;

import java.util.ArrayList;
import java.util.List;

public final class Filter implements Chainable {
    private final LogicalExpression filter;

    public Filter(LogicalExpression filter) {
        this.filter = filter;
    }

    @Override
    public List<Integer> apply(List<Integer> array) {
        List<Integer> result = new ArrayList<>();
        for (Integer a : array) {
            if (filter.evaluate(a) == 1) {
                result.add(a);
            }
        }
        return result;
    }
}
