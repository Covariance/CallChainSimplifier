import org.junit.Test;
import org.junit.Assert;
import parser.CallChainParser;
import structure.*;

import java.util.*;

public class ParserTests {


    @Test
    public void randomExpressionsParsingTest() {
        CallChainParser parser = new CallChainParser();
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                for (int k = 0; k < 100; k++) {
                    CallChain test = Generators.generateCallChain(
                            (i / 10) + 1,
                            (j / 10) + 1,
                            (k / 10) + 1,
                            rand
                    );
                    Assert.assertEquals(test.toString(), parser.parse(test.toString()).toString());
                }
            }
        }
    }
}
