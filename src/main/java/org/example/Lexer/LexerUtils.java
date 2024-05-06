
package org.example.Lexer;

public class LexerUtils {
    public static boolean isSplitCharacter(char c) {
        return c == '\n' || c == ' ' || c == '\r' || c == '\t' || c == ';';
    }

    public static boolean isQuotes(char c) {
        return c == '\'' || c == '"';
    }

    public static boolean isParentheses(char c) {
        return c == '(' || c == ')' || c == '{' || c == '}' || c == '[' || c == ']';
    }

    public static boolean isSymbol(char c) {
        return isParentheses(c) || c == ',' || c == '.';
    }

    public static TokenType getTokenTypeFromSymbol(char c) {
        return switch (c) {
            case '(' -> TokenType.OpeningParentheses;
            case ')' -> TokenType.ClosingParentheses;
            case '[' -> TokenType.SquareOpeningParentheses;
            case ']' -> TokenType.SquareClosingParentheses;
            case '{' -> TokenType.CurlyOpeningParentheses;
            case '}' -> TokenType.CurlyClosingParentheses;
            case ',' -> TokenType.Comma;
            case '.' -> TokenType.Point;
            default -> TokenType.Unknown;
        };
    }

    public static TokenType getTokenTypeFromOperatorString(String operator) {
        return switch (operator) {
            case "+=" -> TokenType.AddEquals;
            case "-=" -> TokenType.SubtractEquals;
            case "*=" -> TokenType.MultiplyEquals;
            case "/=" -> TokenType.DivideEquals;
            case "=<" -> TokenType.LesserOrEqual;
            case ">=" -> TokenType.GreaterOrEqual;
            case "==" -> TokenType.Equals;
            case "=" -> TokenType.Assign;
            case "+" -> TokenType.Add;
            case "-" -> TokenType.Subtract;
            case "*" -> TokenType.Multiply;
            case "/" -> TokenType.Divide;
            case "<" -> TokenType.Lesser;
            case ">" -> TokenType.Greater;
            default -> TokenType.Unknown;
        };
    }


    public static boolean isNewLine(char c) {
        return c == '\n';
    }
}
