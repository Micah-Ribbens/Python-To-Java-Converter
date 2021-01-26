package Algorithms;

import java.util.ArrayList;

public class UtilityFunctions {
    public static boolean stringEqual(String word1, String word2) {
        return word1.contains(word2) && word2.contains(word1);
    }
    public static boolean characterListContains(String word, Character[] list) {
        for (var ch : word.toCharArray())
            for (var i = 0; i < list.length; i++) {
                if (ch == list[i].toUpperCase(list[i]))
                    return true;
                if (ch == list[i].toLowerCase(list[i]))
                    return true;
            }

        return false;
    }
    public static boolean stringListContains(String word, String[] list) {
        for (var i = 0; i < list.length; i++) {
            if (list[i] == null)
                continue;
            if (list[i].contains(word) || word.contains(list[i]))
                return true;
        }
        return false;
    }


    public static String removeExtraSpaces(String word) {
        boolean lastChWasSpace = false;
        String temp = "";
        for (var ch : word.toCharArray()) {
            if (!lastChWasSpace || ch != ' ') {
                temp += ch;
            }

            if (ch == ' ') {
                lastChWasSpace = true;
            }
            else
                lastChWasSpace = false;
        }
        return temp.trim();
    }

    public static ArrayList<String> arrayListWithin(ArrayList<String> list, int start, int end) {
//      The indexes of a list are one off of the length
        ArrayList<String> newList = new ArrayList<>();
        for (var i = start; i <= end; i++)
            newList.add(list.get(i));

        return newList;
    }
    public static String arrayListContentWithin(ArrayList<String> list, int start, int end) {
        String content = "";
        for (var i = start; i <= end; i++) {
            if (i != end) {
                content += list.get(i) + " ";
            }
            else
                content += list.get(i);
        }

        return content;
    }
    public static ArrayList<? extends Object> removeIndexes(ArrayList<? extends Object> list, int start, int end) {
        int length = end - start;
        for (var i = 0; i <= length; i++) {
            list.remove(start);
        }
        return list;
    }
    public static ArrayList<?> removeIndexes(ArrayList<?> list, ArrayList<Integer> indexes) {
        for (var index : indexes)
            list.remove(index);

        return list;
    }
    public static String removeLetters (String word, ArrayList<String> removeLetters) {
        ArrayList<Integer> lengths = new ArrayList<>();
        for (var letters : removeLetters) {
            if (!lengths.contains(letters.length()))
                lengths.add(letters.length());
        }

        for (var length : lengths) {
            ArrayList<String> items = itemsWithLength(removeLetters, length);
            word = removeLetters(word, items, length);
        }
        return word;


    }
    public static ArrayList<String> itemsWithLength(ArrayList<String> list, int length) {
        ArrayList<String> newList = new ArrayList<>();
        for (var item : list) {
            if (item.length() == length)
                newList.add(item);
        }
        return newList;
    }
    private static String removeLetters(String word, ArrayList<String> removeLetters, int length) {
        String newWord = "";
        String currentContent = "";
        for (var ch : word.toCharArray()) {
            currentContent += ch;
            boolean lengthsEqual = currentContent.length() == length;

            if (lengthsEqual && !removeLetters.contains(currentContent)) {
                newWord += currentContent;
                currentContent = "";
            }

            if (lengthsEqual && removeLetters.contains(currentContent)) {
                int index = UtilityFunctions.findWhichEqual(currentContent, removeLetters);
                removeLetters.remove(index);
                currentContent = "";
            }
        }
        return newWord + currentContent;
    }
    private static int findWhichEqual(String word, ArrayList<String> letters) {
        for (var i = 0; i < letters.size(); i++) {
            if (UtilityFunctions.stringEqual(word, letters.get(i)))
                return i;
        }
//      This is to keep Java "happy" this return won't come into play because this function
//      is only called if the list has something that contains those letters
        return -1;
    }
    public static ArrayList<String> addAll(ArrayList<String> arrayList, String[] list) {
        for (var word : list) {
            arrayList.add(word);
        }
        return arrayList;
    }
    public static ArrayList<Integer> addAll(ArrayList<Integer> arrayList, int[] list) {
        for (var word : list) {
            arrayList.add(word);
        }
        return arrayList;
    }
    public static boolean startsAndEndsWith(String word, String characters) {
        if (word.length() == characters.length())
            return false;
        String current = "";
        int timesChecked = 0;
        boolean isStartedWithCharacters = false;
        for (var ch : word.toCharArray()) {
            current += ch;
            boolean isSameLength = characters.length() == current.length();
            boolean startingWithCanBeChecked = (isSameLength && timesChecked == 0);
            if (UtilityFunctions.stringEqual(current, characters) && startingWithCanBeChecked) {
                isStartedWithCharacters = true;
                timesChecked++;
                current = "";
            }

            else if (!UtilityFunctions.stringEqual(current, characters) && startingWithCanBeChecked) {
                return false;
            }
            else if(UtilityFunctions.stringEqual(current, characters) && isStartedWithCharacters) {
                return true;

            }
            else if (current.length() >= characters.length())
                current = "";
        }
        return false;
    }
    public static boolean startsWithThenEndsWith(String word, String startsWith, String endsWith) {
        String current = "";
        int timesGoneThrough = 0;
        boolean hasCheckedStartsWith = false;
        for (var ch : word.toCharArray()) {
            boolean canCheckStartsWith = (timesGoneThrough == startsWith.length() - 1);
            boolean canCheckEndsWith = (timesGoneThrough == word.length() - 1);
            boolean canAddCharacters = (timesGoneThrough <= startsWith.length() - 1 ||
                    timesGoneThrough >= word.length() - endsWith.length());
            timesGoneThrough++;
            if (canAddCharacters) {
                current += ch;
            }

            if (canCheckStartsWith && UtilityFunctions.stringEqual(current, startsWith)) {
                current = "";
                hasCheckedStartsWith = true;
                continue;
            }

            else if (canCheckStartsWith && UtilityFunctions.stringEqual(current, startsWith)) {
                return false;
            }
            if (canCheckEndsWith && UtilityFunctions.stringEqual(current, endsWith)) {
                return true;
            }
        }
        return false;
    }
    public static int countNumberOf(String word, String letter) {
        String currentLetter = "";
        int count = 0;
        for (var ch : word.toCharArray()) {
            currentLetter += ch;
            if (UtilityFunctions.stringEqual(currentLetter, letter)) {
                count++;
            }
            currentLetter = "";
        }
        return count;
    }

}
