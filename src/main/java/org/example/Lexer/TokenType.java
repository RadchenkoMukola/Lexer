
package org.example.Lexer;

public enum TokenType {
    Unknown,

    Comment,

    IntegerLiteral,
    FloatLiteral,
    StringLiterial,
    Identifier,

    AddEquals,
    SubtractEquals,
    MultiplyEquals,
    DivideEquals,

    LesserOrEqual,
    GreaterOrEqual,

    Assign,

    Add,
    Subtract,
    Multiply,
    Divide,


    Equals,
    Lesser,
    Greater,

    OpeningParentheses,
    ClosingParentheses,

    SquareOpeningParentheses,
    SquareClosingParentheses,

    CurlyOpeningParentheses,
    CurlyClosingParentheses,

    Comma,
    Point,
}
