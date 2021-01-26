package Tests;

public class UtilityTestFunctions {
    public static void testerObject(Object want, Object got, String wantMessage) {
        if (want != got) {
            System.out.println("Expected to get " + wantMessage + " but instead got:");
            System.out.println("got:  " + got);
            System.out.println("want: " + want);
            throw new AssertionError();
        }
    }
    public static void testerString(String want, String got, String wantMessage) {
        if (!Algorithms.UtilityFunctions.stringEqual(want, got)) {
            System.out.println("Expected to get " + wantMessage + " but instead got:");
            System.out.println("got:  " + got);
            System.out.println("want: " + want);
            throw new AssertionError();
        }
    }
}
