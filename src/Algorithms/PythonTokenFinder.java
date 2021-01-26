package Algorithms;

import java.util.ArrayList;


public class PythonTokenFinder implements  TokenFinder {
    ArrayList<String> allCodeWords = new ArrayList<>();
    ArrayList<Integer> indentationLevels = new ArrayList<>();
    ArrayList<Integer> tokenLocations = new ArrayList<>();
    ArrayList<Integer> lineEnds = new ArrayList<>();

    @Override
    public ArrayList<Token> findTokens(String code) {
        findAllCodeWords(code);
        findTokenLocations(allCodeWords);
        return createTokenList();
    }

    private ArrayList<Token> createTokenList() {
        ArrayList<Token> tokens = new ArrayList<>();
        for (var i = 0; i < getTokenLocationsSize(); i++) {
            int distanceToTokenEnd = findTokenEnd(i);

            if (distanceToTokenEnd == -1)
                distanceToTokenEnd = i;

            int tokenStart = getTokenLocation(i );
            int tokenEnd = getTokenLocation(distanceToTokenEnd);
            String tokenType = findTokenType(getCodeWord(tokenStart));

            if (getCodeWord(tokenEnd) == "print(")
                tokenEnd = findPrintEnd(tokenEnd);

            ArrayList<String> code = UtilityFunctions.arrayListWithin(allCodeWords, tokenStart, tokenEnd);
            var token = new Token(tokenStart, tokenEnd, code, tokenType, findParentToken(tokenStart, allCodeWords));
            tokens.add(token);
        }
        return tokens;

    }

    public ArrayList<Integer> findTokenLocations(ArrayList<String> allCodeWords) {
        for (var i = 0; i < allCodeWords.size(); i++)
            if (hasToken(allCodeWords.get(i)))
                addTokenLocation(i);

        return tokenLocations;
    }

    private void findAllCodeWords(String code) {
        //Below methods only add stuff to the list if there is a space at the end
        code += " ";
        String word = "";
        boolean startingTokenIsFound = false;
        Character[] allLetters = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '=', '[', ']', '(', ')', '#', '/', '+', '-', '*', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'x', 'y', 'z', '"', '"'};
        char empty = ' ';
        int spacesBetweenCode = 0;
        int variableSpaces = 0;

        for (var ch : code.toCharArray()) {
            boolean canBeAdded = (ch == empty
                    && UtilityFunctions.characterListContains(word, allLetters)
                    && !startingTokenIsFound);
            boolean rightNumberOfSpaces = isStartOfLineOfCode(getCodeWordsSize() - 1);
            if (canBeAdded && rightNumberOfSpaces && spacesBetweenCode % 4 == 0)
                addIndentationLevel(spacesBetweenCode);

            else if (canBeAdded && rightNumberOfSpaces && spacesBetweenCode % 4 == 1)
                addIndentationLevel(spacesBetweenCode - 1);

            else if (canBeAdded && spacesBetweenCode % 4 == 0)
                addIndentationLevel(variableSpaces);

            if (canBeAdded) {
                spacesBetweenCode = 0;
                addCodeWord(word);
                word = "";
            }

            if (ch == '(')
                startingTokenIsFound = true;

            if (ch == ')' && startingTokenIsFound)
                startingTokenIsFound = false;

            else if (ch == empty && !UtilityFunctions.characterListContains(word, allLetters))
                word = "";

            if (!startingTokenIsFound && ch == empty)
                spacesBetweenCode += 1;

            word += ch;
        }

        for (var i = 0; i < getCodeWordsSize(); i++)
            changeCodeWord(i, UtilityFunctions.removeExtraSpaces(getCodeWord(i)));

        fixVariables();
        fixIfStatements();
        fixPrintStatements();
        findLineEnds(allCodeWords);
    }
    public void findLineEnds(ArrayList<String> allCodeWords) {
        String[] haveSemicolonAtEnd = {"def", "class", "def", "if", "for"};
        boolean waitForSemicolon = false;
        for (var i = 0; i < allCodeWords.size(); i++) {
            String word = allCodeWords.get(i);
            if (waitForSemicolon && !word.contains(":"))
                continue;

            if (UtilityFunctions.stringListContains(word, haveSemicolonAtEnd)) {
                lineEnds.add(i);
                waitForSemicolon = true;
                continue;
            }

            if (waitForSemicolon && word.contains(":"))
                waitForSemicolon = false;

            if (word.contains("=") && !word.contains("=="))
                lineEnds.add(i);

            else if (word.contains("print"))
                lineEnds.add(i);

        }
    }
    private boolean isStartOfLineOfCode(int index) {
        if (index <= 0)
            return false;
        Character[] characters = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '=', '[', ']', '(', ')', '#', '/', '+', '-', '*', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'x', 'y', 'z', '"', '"'};
        String lastWord = getCodeWord(index);
        if (lastWord.contains(":"))
            return true;

        if (lastWord.contains("print"))
            return true;

        if (getCodeWordsSize() == 1)
            return false;

        String beforeWord = getCodeWord(index - 1);
        if (beforeWord.contains("=") && UtilityFunctions.characterListContains(lastWord, characters) && !beforeWord.contains("=="))
            return  true;

        return false;
    }
    private void fixVariables() {
        for (var i = 0; i < getCodeWordsSize(); i++) {
            boolean canFixVariable = UtilityFunctions.stringEqual("=", getCodeWord(i));
            if (canFixVariable)
                replaceArrayContent(allCodeWords, i - 1, i + 1);
        }

    }
    private ArrayList<String> replaceArrayContent(ArrayList<String> list, int start, int end) {
        String expression = UtilityFunctions.arrayListContentWithin(list, start, end);
        list.set(start, expression);
        UtilityFunctions.removeIndexes(list, start + 1, end);
        return list;
    }
    private void fixIfStatements() {
        boolean ifIsStarted = false;
        for (var i = 0; i < getCodeWordsSize(); i++) {
            var word = getCodeWord(i);
            if (UtilityFunctions.stringEqual("if", word))
                ifIsStarted = true;

            if (ifIsStarted && UtilityFunctions.stringEqual("==", word)) {
//              Within if statements all the things are booleans i.e. c == 9, isYellow, False, True etc.
                replaceArrayContent(allCodeWords, i - 1, i + 1);

            }
            if (ifIsStarted && word.contains(":"))
                ifIsStarted = false;
        }
    }
    public String findParentToken(int index, ArrayList<String> allCodeWords) {
        boolean printStarted = false;
        boolean otherTokenStarted = false;
        int printStart = 0;
        String[]  otherTokens = {"elif", "if"};
        for (var i = 0; i <= index; i++) {
            if (index == i && otherTokenStarted)
                return "if";

            else if (index == i && printStarted)
                return "print";

            else if (index == i)
                return "none";

            String word = allCodeWords.get(i);
            if ((UtilityFunctions.stringListContains(word, otherTokens))) {}
                otherTokenStarted = true;

            if (otherTokenStarted && word.contains(":"))
                otherTokenStarted = false;

            if (word.contains("print")) {
                printStarted = true;
                printStart = i;
            }

            if (printStarted && findPrintEnd(printStart) == i)
                printStarted = false;
        }
        return "This isn't possible to reach, but java doesn't like not having a return";
    }
    private int findTokenEnd(int start) {
        if (!lineEnds.contains(getTokenLocation(start)))
            return -1;

        start = lineEnds.indexOf(getTokenLocation(start));
        int indentation = getIndentationLevel(start);
        for (var i = start + 1; i < lineEnds.size(); i++)
            if (getIndentationLevel(i) <= indentation)
                return tokenLocations.indexOf(lineEnds.get(i - 1));

        return getTokenLocationsSize() - 1;
    }

    private boolean hasToken(String token) {
        return !UtilityFunctions.stringEqual(findTokenType(token), "No token found");
    }
    public String findTokenType(String token) {
//     Some of the tokens below have no closing parentheses because if there is one that means
//     There has to be another at the end and checking if there is one parentheses is easier
//     Than checking if there are two.
        String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        for (var number : numbers)
            if (token.contains(number) && token.contains("="))
                return "number";

        String[] notExactTokens = {"if", "print(",
               "len(", "str(", "int(", "list(", "dict(", "type(",
                "tuple("};
        boolean tokenFound = false;
        for (var notExactToken : notExactTokens) {
            if (token.contains(notExactToken) ||
                    UtilityFunctions.startsAndEndsWith(token, notExactToken)) {
                tokenFound = true;
            }

            if (!tokenFound)
                continue;

            if (notExactToken.contains("("))
                return notExactToken.replace("(", "");

            return notExactToken;
        }

        String[] exactTokens = {"class", "typeof", "def", "__main__", "and", "True"};

        for (var exactToken : exactTokens)
            if (UtilityFunctions.stringEqual(token, exactToken))
                return exactToken;

        token = cleanUpTokenIfNeeded(token);
        String[] startsAndEndsWithTokens = {"(", "{", "[", "\"", "'"};
        for (var startsAndEndsWithToken : startsAndEndsWithTokens) {
            if (UtilityFunctions.startsAndEndsWith(token, startsAndEndsWithToken))
                tokenFound = true;

            if (!tokenFound)
                continue;

            if (startsAndEndsWithToken.contains("{"))
                return "dictionary";

            if (startsAndEndsWithToken.contains("("))
                return "tuple";

            if (startsAndEndsWithToken.contains("["))
                return "list";

            if ((startsAndEndsWithToken.contains("'") ||
                    startsAndEndsWithToken.contains("\"")) &&
                    token.contains("="))
                return "string";
        }
//       All these tokens start with some word i.e. __contains__( then end with )
        String[] otherTokens = {"__contains__(", "__len__("};
        for (var otherToken : otherTokens)
            if (UtilityFunctions.startsWithThenEndsWith(token, otherToken, ")")
                    || UtilityFunctions.startsWithThenEndsWith(token, otherToken, "):"))
                return otherToken.replace("(", "");

        return "No token found";
    }
    private String cleanUpTokenIfNeeded(String token) {
        boolean isTokenWithStuffBeforeIt = false;
        String[] tokensWithStuffBeforeIt = {"__contains__(", "__len__(", "="};
        for (var tokenWithStuffBeforeIt : tokensWithStuffBeforeIt)
            if (token.contains(tokenWithStuffBeforeIt))
                isTokenWithStuffBeforeIt = true;

        if (!isTokenWithStuffBeforeIt)
            return token;

        String cleanedUpToken = "";
        boolean canAddToToken = false;
        for (var ch : token.toCharArray()) {
//          If the ch is '_' than I know the __contains__ or other ones have started
            if (ch == '_' || ch == '=')
                canAddToToken = true;

            if (canAddToToken)
                cleanedUpToken += ch;
        }
        return cleanedUpToken;
    }

    private void fixPrintStatements() {
//      Because the for loop adds items to the list, so the list gets bigger, the for lop goes one forever
//      Unless it has a fixed size at the start
        final int codeWordsLength = getCodeWordsSize();
        for (var i = 0; i < codeWordsLength; i++) {
            String word = getCodeWord(i);
            if (UtilityFunctions.stringEqual("print", findTokenType(word))) {
//              This function returns how many words were added so that the for loop doesn't loop
//              over the same thing again and again
                int wordsAdded = fixPrintStatement(word, i);
                i += wordsAdded;
            }
        }
    }
    private int fixPrintStatement(String printStatement, int statementIndex) {
        int wordsAdded = 1;
        int lastWordAdded = 1;
        String word = "";
        String[] temp = {"(", ")", "print"};
        changeCodeWord(statementIndex, "print(");
        ArrayList<String> removeLetters = UtilityFunctions.addAll(new ArrayList<>(), temp);
        printStatement = UtilityFunctions.removeLetters(printStatement, removeLetters);
        for (var ch : printStatement.toCharArray()) {
            word += ch;
            if (hasToken(word) ||
                    UtilityFunctions.startsAndEndsWith(word, "\"") ||
                    UtilityFunctions.startsAndEndsWith(word, "'")) {
                allCodeWords.add(statementIndex + wordsAdded, word);
                word = "";
                lastWordAdded = wordsAdded + statementIndex;
                wordsAdded++;
            }
        }
        changeCodeWord(lastWordAdded, getCodeWord(lastWordAdded) + ")");
        return wordsAdded;
    }
    private int findPrintEnd(int printStart) {
        int neededExteriorParentheses = 0;
        for (var i = printStart; i < getCodeWordsSize(); i++) {
            String word = getCodeWord(i);
            neededExteriorParentheses += UtilityFunctions.countNumberOf(word, "(");
            neededExteriorParentheses -= UtilityFunctions.countNumberOf(word, ")");
            if (getCodeWord(i).contains(")") && neededExteriorParentheses == 0)
                return i;
        }
        return printStart;
    }
//  Helper functions for being able to add stuff to lists; It adds more flexibility because if the list
//  changes type the code won't have be affected because it works with the lists indirectly through these functions.
    private void addCodeWord(String word) {
        allCodeWords.add(word);
    }
    private void addCodeWordFromIndex(int index, String word) {
        allCodeWords.add(index, word);
    }
    private int getCodeWordsSize() {
        return allCodeWords.size();
    }
    private void removeCodeWord(int index) {
        allCodeWords.remove(index);
    }

    private void changeCodeWord(int index, String word) {
        allCodeWords.set(index, word);
    }

    private void removeCodeWords(int start, int end) {
        UtilityFunctions.removeIndexes(allCodeWords, start, end);
    }

    private String getCodeWord(int index) {
        return allCodeWords.get(index);
    }

    private void removeCodeWords(ArrayList<Integer> indexes) {
        UtilityFunctions.removeIndexes(allCodeWords, indexes);
    }

    private void addIndentationLevel(int number) {
        indentationLevels.add(number);
    }
    private int getIndentationLevelsSize() {
        return indentationLevels.size();
    }
    private void removeIndentationLevel(int index) {
        indentationLevels.remove(index);
    }

    private void changeIndentationLevel(int index, int number) {
        indentationLevels.set(index, number);
    }

    private void removeIndentationLevels(int start, int end) {
        UtilityFunctions.removeIndexes(indentationLevels, start, end);
    }

    private int getIndentationLevel(int index) {
        return indentationLevels.get(index);
    }

    private void removeIndentationLevel(ArrayList<Integer> indexes) {
        UtilityFunctions.removeIndexes(indentationLevels, indexes);
    }
    private void addTokenLocation(int number) {
        tokenLocations.add(number);
    }
    private int getTokenLocationsSize() {
        return tokenLocations.size();
    }
    private void removeTokenLocation(int index) {
        tokenLocations.remove(index);
    }

    private void changeTokenLocation(int index, int number) {
        tokenLocations.set(index, number);
    }

    private void removeTokenLocations(int start, int end) {
        UtilityFunctions.removeIndexes(tokenLocations, start, end);
    }

    private int getTokenLocation(int index) {
        return tokenLocations.get(index);
    }

    private void removeTokenLocations(ArrayList<Integer> indexes) {
        UtilityFunctions.removeIndexes(tokenLocations, indexes);
    }
}




