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
