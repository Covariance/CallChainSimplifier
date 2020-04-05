import exceptions.SyntaxException;
import exceptions.TypeMismatchException;
import parser.CallChainParser;
import structure.CallChain;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        try {
            CallChain callChain = new CallChainParser().parse(scan.nextLine());
            callChain.simplify();
            System.out.println(callChain);
        } catch (SyntaxException | NumberFormatException e) {
            System.out.println("SYNTAX ERROR");
        } catch (TypeMismatchException e) {
            System.out.println("TYPE ERROR");
        }
    }
}
