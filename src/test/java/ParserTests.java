import exceptions.SyntaxException;
import exceptions.TypeMismatchException;
import org.junit.Test;
import org.junit.Assert;
import parser.CallChainParser;
import structure.*;

import java.util.*;

public class ParserTests {
    @Test
    public void manualExpressionParserTest() {
        // TODO -- manual examples for parser
    }

    @Test
    public void syntaxErrorsParserTest() {
        CallChainParser parser = new CallChainParser();
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                for (int k = 0; k < 100; k++) {
                    String callChain = Generators.generateCallChain(
                            (i / 10) + 1,
                            (j / 10) + 1,
                            (k / 10) + 1,
                            rand
                    ).toString();
                    int divider = rand.nextInt(callChain.length());
                    String test = callChain.substring(0, divider) +
                            (char) (rand.nextInt(26) + 'a') +
                            callChain.substring(divider);
                    try {
                        parser.parse(test);
                        Assert.fail();
                    } catch (SyntaxException | NumberFormatException ignored) {}
                }
            }
        }
    }

    private final static List<String> TYPE_MISMATCH_SAMPLES = List.of(
            "filter{element}",
            "filter{(element+1)}",
            "filter{((element[0)](element:1))}",
            "filter{((element:1)](element[1))}",
            "filter{((element[0)[0)}",
            "filter{(0[(element[0))}",
            "filter{(((1[1)](1[1))[0)}",
            "map{(element:(element[0))}",
            "map{(element[0)}",
            "map{((element[0)](element[0))}",
            "map{((element[0):element)}"
    );

    private final static Map<Character, List<Character>> REPLACEMENT = Map.of(
            ':', List.of('+', '-', '-'),
            '[', List.of('<', '>', '='),
            ']', List.of('&', '|')
    );

    @Test
    public void typeMismatchErrorParserTest() {
        CallChainParser parser = new CallChainParser();
        for (String sample : TYPE_MISMATCH_SAMPLES) {
            for (Character repl1 : REPLACEMENT.get(':')) {
                for (Character repl2 : REPLACEMENT.get('[')) {
                    for (Character repl3 : REPLACEMENT.get(']')) {
                        String test = sample.replace(':', repl1)
                                .replace('[', repl2)
                                .replace(']', repl3);
                        try {
                            parser.parse(test);
                            Assert.fail();
                        } catch (TypeMismatchException ignored) {}
                    }
                }
            }
        }
    }

    @Test
    public void randomExpressionsParserTest() {
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
