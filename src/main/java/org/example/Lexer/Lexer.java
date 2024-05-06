
package org.example.Lexer;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lexer {
    @Getter
    private static Lexer instance = new Lexer();

    private List<String> operators = Arrays.asList("+=", "-=", "*=", "/=", "=<", ">=", "==", "=", "+", "-", "*", "/", "<", ">");

    private Lexer() {

    }

    public List<Token> getTokens(String source) throws LexerException {
        LexerState state = new LexerState();
        state.position = 0;
        state.source = source;
        state.tokens = new ArrayList<>();

        parse(state);

        return state.tokens;
    }

    private void parse(LexerState state) throws LexerException {
        while (!state.hasReachedEnd()) {
            char c = state.getCharacter();

            if (LexerUtils.isSplitCharacter(c)) {
                state.movePosition();
                continue;
            } else if (Character.isDigit(c)) {
                parseIntegerLiteral(state);
            } else if (Character.isAlphabetic(c)) {
                parseIdentifier(state);
            } else if (LexerUtils.isQuotes(c)) {
                parseStringLiteral(state);
            } else if (LexerUtils.isSymbol(c)) {
                parseSymbol(state);
            } else {
                parseOperator(state);
            }
        }
    }

    private void parseIdentifier(LexerState state) {
        int currentOffset = 0;

        while (state.canMove(currentOffset)) {
            char c = state.getCharacter(currentOffset);

            if (Character.isAlphabetic(c) || c == '.') {
                ++currentOffset;
            } else  {
                break;
            }
        }

        state.addToken(TokenType.Identifier, currentOffset);
        state.movePosition(currentOffset);
    }

    private void parseIntegerLiteral(LexerState state) throws LexerException {
        int currentOffset = 0;

        while (state.canMove(currentOffset)) {
            char c = state.getCharacter(currentOffset);

            if (Character.isDigit(c)) {
                ++currentOffset;
            } else if (c == '.') {
                parseFloatLiteral(state);
                return;
            } else if (LexerUtils.isSplitCharacter(c) || c == ',' || c == ')' || Character.isWhitespace(c)) {
                break;
            } else {
                throw new LexerException(state, "Improper integer literal");
            }
        }

        state.addToken(TokenType.IntegerLiteral, currentOffset);
        state.movePosition(currentOffset);
    }

    private void parseFloatLiteral(LexerState state) throws LexerException {
        int currentOffset = 0;

        boolean HasEncounteredDot = false;

        while (state.canMove(currentOffset)) {
            char c = state.getCharacter(currentOffset);

            if (Character.isDigit(c)) {
                ++currentOffset;
            } else if (c == '.') {
                if (!HasEncounteredDot) {
                    HasEncounteredDot = true;
                    ++currentOffset;
                } else {
                    throw new LexerException(state, "Float literal has two '.' ");
                }
            } else if (LexerUtils.isSplitCharacter(c)) {
                break;
            } else {
                throw new LexerException(state, "Improper float literal");
            }
        }

        state.addToken(TokenType.FloatLiteral, currentOffset);
        state.movePosition(currentOffset);
    }

    private void parseStringLiteral(LexerState state) throws LexerException {
        int currentOffset = 1;

        boolean isOpen = true;
        boolean isEscapeCharacterActive = true;

        while (state.canMove(currentOffset)) {
            char c = state.getCharacter(currentOffset);

            if (isOpen) {
                if (c == state.getCharacter()) {
                    isOpen &= c == '\'' && isEscapeCharacterActive;
                } else if (LexerUtils.isNewLine(c)) {
                    throw new LexerException(state, "String is not properly closed");
                }

                isEscapeCharacterActive = c == '\\';

                ++currentOffset;
            } else {
                if (LexerUtils.isSplitCharacter(c)) {
                    state.addToken(TokenType.StringLiterial, currentOffset);
                    state.movePosition(currentOffset);
                    return;
                } else {
                    throw new LexerException(state, "String is not properly closed");
                }
            }
        }
    }

    private void parseSymbol(LexerState state) {
        char symbol = state.getCharacter();
        state.addToken(LexerUtils.getTokenTypeFromSymbol(symbol), Character.toString(symbol));
        state.movePosition();
    }

    private void parseOperator(LexerState state) throws LexerException {
        if (state.canMove() && state.getCharacter() == '/') {
            char c = state.getCharacter(1);
            if (c == '*') {
                parseMultilineComment(state);
                return;
            } else if (c == '/') {
                parseSingleLineComment(state);
                return;
            }
        }

        for (String name : operators) {
            if (state.canMove(name.length())) {
                boolean isMatching = true;

                for (int i = 0; i < name.length() && isMatching; ++i) {
                    if (name.charAt(i) != state.getCharacter(i)) {
                        isMatching = false;
                    }
                }

                if (isMatching) {
                    state.addToken(LexerUtils.getTokenTypeFromOperatorString(name), name);
                    state.movePosition(name.length());
                    return;
                }
            }
        }

        throw new LexerException(state, "Unknown operator detected");
    }

    private void parseMultilineComment(LexerState state) {
        int currentOffset = 0;

        while (state.canMove(currentOffset + 1)) {
            char c1 = state.getCharacter(currentOffset);
            char c2 = state.getCharacter(currentOffset + 1);

            if (c1 == '*' && c2 == '/')
            {
                break;
            }

            ++currentOffset;
        }

        state.addToken(TokenType.Comment, currentOffset + 2);
        state.movePosition(currentOffset + 2);
    }

    private void parseSingleLineComment(LexerState state) {
        int currentOffset = 0;

        while (state.canMove(currentOffset)) {
            char c = state.getCharacter(currentOffset);

            if (LexerUtils.isNewLine(c)) {
                break;
            }

            ++currentOffset;
        }

        state.addToken(TokenType.Comment, currentOffset);
        state.movePosition(currentOffset);
    }
}

