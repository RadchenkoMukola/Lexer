
package org.example.Parser;

import lombok.Getter;
import org.example.Lexer.Token;
import org.example.Lexer.TokenType;

import java.util.List;

public class ParserState {
    @Getter
    private int currentTokenIndex = 0;

    @Getter
    private List<Token> tokens;

    ParserState(List<Token> tokens) {
        this.tokens = tokens;

        while (currentTokenIndex < tokens.size() && tokens.get(currentTokenIndex).getType() == TokenType.Comment) {
            ++currentTokenIndex;
        }
    }

    Token getToken(int offset) {
        return currentTokenIndex + offset >= tokens.size() ? null : tokens.get(currentTokenIndex + offset);
    }

    Token getToken() {
        return getToken(0);
    }

    Token next(int offset) {
        Token token = getToken();

        while (currentTokenIndex < tokens.size() && offset > 0) {
            ++currentTokenIndex;

            if (currentTokenIndex < tokens.size() && getToken().getType() == TokenType.Comment) {
                continue;
            }

            --offset;
        }

        return token;
    }

    Token next() {
        return next(1);
    }

    boolean hasToken() {
        return currentTokenIndex < tokens.size();
    }
}
