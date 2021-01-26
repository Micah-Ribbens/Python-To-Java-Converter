package Tests;

import Algorithms.UtilityFunctions;
import org.testng.annotations.Test;

import java.util.ArrayList;

//
public class TestUtilityFunctions {
    @Test
    public void testRemoveLetters() {
        String testWord = "print(hello)";
        String[] temp = {"(", "print", ")"};
        ArrayList<String> removeLetters = UtilityFunctions.addAll(new ArrayList<>(), temp);

        String want = "hello";

        String got = Algorithms.UtilityFunctions.removeLetters(testWord, removeLetters);

        UtilityTestFunctions.testerString(want, got, "remove letters from a word");
    }
    @Test
    public void testStartsAndEndsWith() {
        String testWord = "'hi'";

        boolean got = UtilityFunctions.startsAndEndsWith(testWord, "'");
        UtilityTestFunctions.testerObject(true, got, "should return true");

        got = UtilityFunctions.startsAndEndsWith(testWord, "''");
        UtilityTestFunctions.testerObject(false, got, "should return false");
    }
    @Test
    public void testStartsWithThenEndsWith() {
        String testWord = "__contains__(a word)";

        boolean got = UtilityFunctions.startsWithThenEndsWith(testWord, "__contains__(", ")");
        UtilityTestFunctions.testerObject(true, got, "should return true");

        testWord = "__contains__(a word)";
        got = UtilityFunctions.startsWithThenEndsWith(testWord, "__contains__(", "_");

        UtilityTestFunctions.testerObject(false, got, "should return false");
    }
    @Test
    public void testCountNumberOf() {
        int want = 4;

        int got = UtilityFunctions.countNumberOf("test))yeah))", ")");

        UtilityTestFunctions.testerObject(want, got, "4");
    }
}
