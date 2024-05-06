
package org.example.Lexer;

import lombok.Getter;

@Getter
public class Token {
    private int line;
    private int position;
    private TokenType type;
    private String value;

    public Token(int line, int position, TokenType type, String value)
    {
        this.line = line;
        this.position = position;
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "line=" + line +
                ", position=" + position +
                ", type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
