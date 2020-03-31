package structure;

import java.util.ArrayList;
import java.util.List;

public final class Filter implements Chainable {
    private final BooleanExpression filter;

    public Filter(BooleanExpression filter) {
        this.filter = filter;
    }

    @Override
    public List<Integer> apply(List<Integer> array) {
        List<Integer> result = new ArrayList<>();
        for (Integer a : array) {
            if (filter.evaluate(a)) {
                result.add(a);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "filter{" + filter.toString() + "}";
    }
}
