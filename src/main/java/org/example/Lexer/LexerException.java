package org.example.Lexer;

public class LexerException extends Exception {
    public LexerException(LexerState state, String message) {
        super("[L" + state.getLine() + ":" + state.getPositionInLine() + "] " + message);
    }
}
