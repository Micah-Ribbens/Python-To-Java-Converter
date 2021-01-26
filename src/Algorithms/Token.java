package Algorithms;

import java.util.ArrayList;

public class Token {
        public int start;
        public int end;
        public ArrayList<String> code;
        public String tokenType;
//      Parent being inside an if or print statement; cant think of a good way to describe it
        public String parent;

        public Token(int start, int end, ArrayList<String> code, String tokenType, String parent) {
            this.code = code;
            this.end = end;
            this.start = start;
            this.tokenType = tokenType;
            this.parent = parent;
    }
    @Override
    public String toString() {
            return tokenType;
    }
}
