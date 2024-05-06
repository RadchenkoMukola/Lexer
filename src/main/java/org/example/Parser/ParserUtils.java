
package org.example.Parser;

import org.example.Lexer.TokenType;
import org.example.Parser.Nodes.BinaryOperationNode;
import org.example.Parser.Nodes.BinaryOperationType;

public class ParserUtils {
    public static KeywordType getKeywordTypeFromIdentifier(String identifier) {
        return switch (identifier) {
            case "let" -> KeywordType.Let;
            case "const" -> KeywordType.Const;
            case "function" -> KeywordType.Function;
            case "return" -> KeywordType.Return;
            default -> KeywordType.None;
        };
    }

    public static int getOperatorPrecedence(TokenType type) {
        return switch (type) {
            case AddEquals, Assign, GreaterOrEqual, LesserOrEqual, DivideEquals, MultiplyEquals, SubtractEquals -> 10;
            case Equals, Greater, Lesser -> 20;
            case Add, Subtract -> 30;
            case Divide -> 40;
            case Multiply -> 50;
            default -> -1;
        };
    }

    public static BinaryOperationType getBinaryOperatorTypeFromTokenType(TokenType type) {
        return switch (type) {
            case AddEquals -> BinaryOperationType.AddEquals;
            case SubtractEquals -> BinaryOperationType.SubtractEquals;
            case MultiplyEquals -> BinaryOperationType.MultiplyEquals;
            case DivideEquals -> BinaryOperationType.DivideEquals;
            case LesserOrEqual -> BinaryOperationType.LesserOrEquals;
            case GreaterOrEqual -> BinaryOperationType.GreaterOrEquals;
            case Assign -> BinaryOperationType.Assignment;

            case Add -> BinaryOperationType.Addition;
            case Subtract -> BinaryOperationType.Subtraction;
            case Multiply -> BinaryOperationType.Multiplication;
            case Divide -> BinaryOperationType.Division;

            case Equals -> BinaryOperationType.Equals;
            case Lesser -> BinaryOperationType.Lesser;
            case Greater -> BinaryOperationType.Greater;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }
}
