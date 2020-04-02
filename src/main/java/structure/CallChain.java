package structure;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public final class CallChain {
    private List<Chainable> chain;

    public CallChain(List<Chainable> chain) {
        this.chain = chain;
    }

    public List<Integer> apply(List<Integer> array) {
        for (Chainable call : chain) {
            array = call.apply(array);
        }
        return array;
    }

    private List<Chainable> reduce() {
        Polynomial current = new Polynomial(1, 1);
        Queue<BooleanExpression> queueOfFilters = new ArrayDeque<>();
        for (Chainable element : chain) {
            if (element instanceof Filter) {
                element.compose(current);
                queueOfFilters.add(((Filter) element).getFilter());
            } else {
                element.compose(current);
                current = ((Mapper) element).getMapper();
            }
        }
        Filter finalFilter;
        if (queueOfFilters.size() == 0) {
            finalFilter = new Filter(new Equals(new Polynomial()));
        } else {
            while (queueOfFilters.size() > 1) {
                queueOfFilters.add(new And(queueOfFilters.remove(), queueOfFilters.remove()));
            }
            finalFilter = new Filter(queueOfFilters.peek());
        }
        Mapper finalMapper = new Mapper(current);
        return List.of(finalFilter, finalMapper);
    }

    public void simplify() {
        this.chain = reduce();
    }

    public boolean isSimple() {
        return chain.size() == 2 && chain.get(0) instanceof Filter && chain.get(1) instanceof Mapper;
    }

    @Override
    public String toString() {
        int x = chain.size();
        if (x == 0) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        result.append(chain.get(0).toString());
        for (int i = 1; i < x; i++) {
            result.append("%>%").append(chain.get(i).toString());
        }
        return result.toString();
    }
}
