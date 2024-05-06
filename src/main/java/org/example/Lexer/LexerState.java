
package org.example.Lexer;

import lombok.Getter;

import java.util.List;

public class LexerState {
    public int position;
    public String source;
    public List<Token> tokens;

    @Getter
    private int line = 1;

    @Getter
    private int positionInLine = 1;

    public char getCharacter(int offset) {
        return source.charAt(position + offset);
    }

    public char getCharacter() {
        return getCharacter(0);
    }

    public boolean hasReachedEnd() {
        return position >= source.length();
    }

    public void movePosition() {
        movePosition(1);
    }

    public boolean canMove(int offset) {
        return position + offset < source.length();
    }

    public boolean canMove() {
        return canMove(1);
    }

    public void movePosition(int offset) {
        for (int i = position; i < position + offset && i < source.length(); ++i) {
            char c = source.charAt(i);

            if (c == '\n') {
                line++;
                positionInLine = 1;
            } else {
                ++positionInLine;
            }
        }

        position += offset;
    }

    public void addToken(TokenType type, String value) {
        tokens.add(new Token(getLine(), getPositionInLine(), type, value));
    }

    public void addToken(TokenType type, int length) {
        tokens.add(new Token(getLine(), getPositionInLine(), type, source.substring(position, position + length)));
    }
}
