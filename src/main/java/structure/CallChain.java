package structure;

import java.util.List;

public final class CallChain implements Chainable {
    private final List<Chainable> chain;

    public CallChain(List<Chainable> chain) {
        this.chain = chain;
    }

    @Override
    public List<Integer> apply(List<Integer> array) {
        for (Chainable call : chain) {
            array = call.apply(array);
        }
        return array;
    }
}
