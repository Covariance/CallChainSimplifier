package ru.covariance.jbintern;

import ru.covariance.jbintern.exceptions.SyntaxException;
import ru.covariance.jbintern.exceptions.TypeMismatchException;
import ru.covariance.jbintern.parser.CallChainParser;
import ru.covariance.jbintern.structure.CallChain;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        try {
            CallChain callChain = new CallChainParser().parse(scan.nextLine());
            callChain.simplify();
            System.out.println(callChain.toMiniString());
        } catch (SyntaxException e) {
            System.out.println("SYNTAX ERROR");
        } catch (TypeMismatchException e) {
            System.out.println("TYPE ERROR");
        }
    }
}
