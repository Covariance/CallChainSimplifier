package structure;

import java.util.List;

public interface Chainable {
    List<Integer> apply(List<Integer> array);
    void compose(Polynomial poly);
}
