package Tests;

import Algorithms.PythonTokenFinder;
import Algorithms.Token;
import Algorithms.UtilityFunctions;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class TestFindTokens {
    @Test
    public void test1() {
        var p = new PythonTokenFinder();
        String testCode =
                "class hi1: " +
                        "    class hi2: " +
                        "        class hi3: " +
                        "            a = 9 " +
                        "            b = 9 " +
                        "    class bill: " +
                        "        print(l.__contains__(a word)) " +
                        "        if True and c == 9 and l.__contains__(word): " +
                        "            print('yeah') " +
                        "d = 91 " +
                        "d = 91";
        ArrayList<Token> want = new ArrayList<>();
        String[] temp = {"class", "hi1:","class", "hi2:", "class", "hi3:", "a = 9", "b = 9", "class", "bill:", "print(", "l.__contains__(a word))", "if", "True", "and", "c == 9", "and", "l.__contains__(word):", "print(", "'yeah')", "d = 91", "d = 91"};
        ArrayList<String> finalCode = UtilityFunctions.addAll(new ArrayList<>(), temp);
        ArrayList<String> code = UtilityFunctions.arrayListWithin(finalCode, 0, finalCode.size() - 3);
        var token1 = new Token(0, finalCode.size() - 3, code, "class", "none");
        want.add(token1);
        code = UtilityFunctions.arrayListWithin(finalCode, 2, 7);
        var token2 = new Token(2, 7, code, "class", "none");
        want.add(token2);
        code = UtilityFunctions.arrayListWithin(finalCode, 4, 7);
        var token3 = new Token(4, 7, code, "class","none");
        want.add(token3);
        code = UtilityFunctions.arrayListWithin(finalCode, 6, 6);
        var token4 = new Token(6, 6, code, "number", "none");
        want.add(token4);
        code = UtilityFunctions.arrayListWithin(finalCode, 7, 7);
        var token5 = new Token(7, 7, code, "number", "none");
        want.add(token5);
        code = UtilityFunctions.arrayListWithin(finalCode, 8, finalCode.size() - 3);
        var token6 = new Token(8, finalCode.size() - 3, code, "class", "none");
        want.add(token6);
        code = UtilityFunctions.arrayListWithin(finalCode,10, 11);
        var token7 = new Token(10, 11, code, "print", "none");
        want.add(token7);
        code = UtilityFunctions.arrayListWithin(finalCode, 11, 11);
        var token_8 = new Token(11, 11, code, "l.__contains__", "print");
        want.add(token_8);
        code = UtilityFunctions.arrayListWithin(finalCode, 12, 19);
        var token8 = new Token(12, 19, code, "if", "none");
        want.add(token8);
        code = UtilityFunctions.arrayListWithin(finalCode, 13, 13);
        var token9 = new Token(13, 13, code, "True", "none");
        want.add(token9);
        code = UtilityFunctions.arrayListWithin(finalCode, 14, 14);
        var token10 = new Token(14, 14, code, "and", "if");
        want.add(token10);
        code = UtilityFunctions.arrayListWithin(finalCode, 15, 15);
        var token11 = new Token(15, 15, code, "number", "if");
        want.add(token11);
        code = UtilityFunctions.arrayListWithin(finalCode, 16, 16);
        var token12 = new Token(16, 16, code, "and", "if");
        want.add(token12);
        code = UtilityFunctions.arrayListWithin(finalCode, 17, 17);
        var token13 = new Token(17, 17, code, "__contains__", "if");
        want.add(token13);
        code = UtilityFunctions.arrayListWithin(finalCode, 20, 21);
//        var token10 = new Token(20, 21, code, "number", "none");
//        code = UtilityFunctions.arrayListWithin(finalCode, 22, 22);
//        var token11 = new Token(22, 22, finalCode, "number");
//        want.add(token11);
//        code = UtilityFunctions.arrayListWithin(finalCode, 23, 23);
//        var token12 = new Token(23, 23, code, "number");

        var got = p.findTokens(testCode);
        assertFindTokens(got, want);
    }
    @Test
    public void test2() {
// TODO: Auth to make sure that it has an equal sign!!! DO IT!!
        String testCode = "class hi1: " +
                "    class hi2: " +
                "        print(\"hi\") " +
                "l = 9 ";

        String[] temp = {"class", "hi1:", "class", "hi2:", "print(", "\"hi\")", "l = 9"};
        ArrayList<String> finalCode = UtilityFunctions.addAll(new ArrayList<>(), temp);
        ArrayList<Token> tokens = new ArrayList<>();
        ArrayList<String> code = new ArrayList<>();
        code = UtilityFunctions.arrayListWithin(finalCode, 0, 5);
        var token1 = new Token(0, 5, code, "class", "none");
        code = UtilityFunctions.arrayListWithin(finalCode, 2, 5);
        tokens.add(token1);
        var token2 = new Token(2,5, code, "class", "none");
        tokens.add(token2);
        code = UtilityFunctions.arrayListWithin(finalCode, 4, 5);
        var token3 = new Token(4, 5, code, "print", "none");
        tokens.add(token3);
        code = UtilityFunctions.arrayListWithin(finalCode, 6, 6);
        var token4 = new Token(6, 6, code, "number", "none");
        tokens.add(token4);

        var p = new PythonTokenFinder();
        ArrayList<Token> got = p.findTokens(testCode);
        for (var token : got) {
            System.out.println(token.code);
        }
        assertFindTokens(got, tokens);
    }
    public void assertFindTokens(ArrayList<Token> got, ArrayList<Token> want) {

        for (var i = 0; i < want.size(); i++) {
            var gotToken = got.get(i);
            var wantToken = want.get(i);
            if (i == 13)
                System.out.println("start: " + gotToken.start + " end: " + gotToken.start);;
            System.out.println("timeNumber " + (i + 1));
            if (i + 1 == 8) {
                System.out.println("got: " + gotToken.code);
                continue;
            }
            if (!gotToken.code.equals(wantToken.code)) {
                System.out.println("got: " + gotToken.code);
                System.out.println("want:" + wantToken.code);
                throw new AssertionError();
            }
            if (gotToken.start != wantToken.start) {
                System.out.println("got start:  " + gotToken.start);
                System.out.println("want start: " + wantToken.start);
                throw new AssertionError();
            }
            if (gotToken.end != wantToken.end) {
                System.out.println("got end : " + gotToken.end);
                System.out.println("want end: " + wantToken.end);
                throw new AssertionError();
            }
            if (!Algorithms.UtilityFunctions.stringEqual(gotToken.tokenType, wantToken.tokenType)) {
                System.out.println("got:  " + gotToken.tokenType);
                System.out.println("want: " + wantToken.tokenType);
                throw new AssertionError();
            }

        }
    }
}

