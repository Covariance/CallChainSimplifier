import org.junit.Assert;
import org.junit.Test;
import parser.CallChainParser;
import structure.CallChain;

import java.util.List;
import java.util.Random;

public class SimplifierTests {

    private final static List<String> SAMPLES = List.of(
            "filter{(element>0)}%>%map{(element*-1)}%>%filter{(element<-1)}%>%map{(element*-1)}",
            "filter{(1=1)}"
    );

    private final static List<List<Integer>> TEST_CASES = List.of(
            List.of(-3, -2, -1, 0, 1, 2, 3),
            List.of(Integer.MAX_VALUE, 0, Integer.MIN_VALUE)
    );

    @Test
    public void manualSimplifierTests() {
        CallChainParser parser = new CallChainParser();
        for (int i = 0; i < SAMPLES.size(); i++) {
            CallChain answer = parser.parse(SAMPLES.get(i));
            CallChain sample = parser.parse(SAMPLES.get(i));
            sample.simplify();
            Assert.assertTrue(sample.isSimple());
            Assert.assertEquals(
                    answer.apply(TEST_CASES.get(i)),
                    sample.apply(TEST_CASES.get(i))
            );
        }
    }

    interface CallChainGenerator {
        CallChain generate(int length, int depth, int degree, Random rand);
    }

    private void generatedSimplifierTests(int lengthRange,
                                          int depthRange,
                                          int degreeRange,
                                          int repeat,
                                          List<Integer> testCase,
                                          CallChainGenerator gen,
                                          Random rand) {
        CallChainParser parser = new CallChainParser();
        for (int i = 1; i <= lengthRange; i++) {
            for (int j = 1; j <= depthRange; j++) {
                for (int k = 1; k <= degreeRange; k++) {
                    for (int l = 0; l <= repeat; l++) {
                        String callChain = gen.generate(i, j, k, rand).toString();
                        CallChain answer = parser.parse(callChain);
                        CallChain sample = parser.parse(callChain);
                        sample.simplify();
                        Assert.assertTrue(sample.isSimple());
                        Assert.assertEquals(
                                answer.apply(testCase),
                                sample.apply(testCase)
                        );
                    }
                }
            }
        }
    }

    @Test
    public void randomSimpleSimplifierTests() {
        Random rand = new Random();
        generatedSimplifierTests(10, 2, 3, 100,
                Generators.generateIntList(100000, rand),
                (length, depth, degree, random) -> Generators.generateSimpleCallChain(length, degree, random),
                rand);
    }

    @Test
    public void randomHardSimplifierTests() {
        Random rand = new Random();
        generatedSimplifierTests(10, 2, 3, 100,
                Generators.generateIntList(100000, rand),
                Generators::generateCallChain,
                rand);
    }
}
