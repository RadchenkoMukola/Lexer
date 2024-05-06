
package org.example.Parser;

import lombok.Getter;
import org.example.Lexer.Token;
import org.example.Lexer.TokenType;
import org.example.Parser.Nodes.*;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    @Getter
    private static Parser parser = new Parser();

    private Parser() {
    }

    public ProgramNode parse(List<Token> tokens) {
        ParserState state = new ParserState(tokens);
        return new ProgramNode(parseBody(state, false));
    }

    private List<Node> parseBody(ParserState state, boolean isFunction) {
        List<Node> nodes = new ArrayList<>();

        if (isFunction) {
            if (state.hasToken()) {
                Token curlyParenthesesToken = state.next();
                if (curlyParenthesesToken.getType() != TokenType.CurlyOpeningParentheses) {
                    nodes.add(new ErrorNode("Expected opening curly parentheses"));
                }
            } else {
                nodes.add(new ErrorNode("Expected opening curly parentheses"));
            }
        }

        while (state.hasToken()) {
            Token token = state.getToken();

            if (isFunction && token.getType() == TokenType.CurlyClosingParentheses) {
                state.next();
                return nodes;
            }

            Node node = null;

            KeywordType type = ParserUtils.getKeywordTypeFromIdentifier(token.getValue());

            if (type != KeywordType.None) {
                node = parseKeyword(state, type);
            } else {
                node = parseExpression(state);
            }

            if (node != null) {
                nodes.add(node);
            }
        }

        if (isFunction) {
            nodes.add(new ErrorNode("Function body was not closed properly"));
        }

        return nodes;
    }

    private Node parseKeyword(ParserState state, KeywordType type) {
        state.next();
        return switch (type) {
            case Let -> parseVariableDefinition(state);
            case Const -> parseConstVariableDefinition(state);
            case Function -> parseFunctionDeclaration(state);
            case Return -> new ReturnNode(parseExpression(state));
            default -> new ErrorNode("Unexpected token keyword: " + type);
        };
    }

    private Node parseFunctionDeclaration(ParserState state) {
        Token functionNameToken = state.next();
        if (functionNameToken != null && functionNameToken.getType() == TokenType.Identifier) {
            Token parenthesesToken = state.next();
            if (parenthesesToken != null && parenthesesToken.getType() == TokenType.OpeningParentheses) {
                List<String> names = new ArrayList<>();

                while (state.hasToken()) {
                    Token parameterNameToken = state.next();
                    if (parameterNameToken.getType() == TokenType.Identifier) {
                        if (ParserUtils.getKeywordTypeFromIdentifier(parameterNameToken.getValue()) == KeywordType.None) {
                            names.add(parameterNameToken.getValue());
                        } else {
                            return new ErrorNode("Keyword cannot be a used a name for function parameter");
                        }
                    }

                    Token commaToken = state.next();
                    if (commaToken != null && commaToken.getType() == TokenType.Comma) {
                        continue;
                    } else if (commaToken != null && commaToken.getType() == TokenType.ClosingParentheses) {
                        break;
                    } else {
                        if (commaToken == null) {
                            return new ErrorNode("Function parameter list was not closed");
                        }

                        return new ErrorNode("Unexpected token: " + commaToken.getValue());
                    }
                }

                List<Node> body = parseBody(state, true);
                return new FunctionDeclarationNode(functionNameToken.getValue(), names, body);
            }

            return new ErrorNode("Expected function parameters list");
        }

        return new ErrorNode("Expected function identifier");
    }

    private Node parseVariableDefinition(ParserState state) {
        Token identifierToken = state.next();
        if (identifierToken != null && identifierToken.getType() == TokenType.Identifier) {
            Node node = null;

            Token assignmentToken = state.next();
            if (assignmentToken != null && assignmentToken.getType() == TokenType.Assign) {
                node = parseExpression(state);
            }

            return new VariableDefinitionNode(identifierToken.getValue(), node);
        }

        return new ErrorNode("Expected variable identifier");
    }

    private Node parseConstVariableDefinition(ParserState state) {
        Token identifierToken = state.next();
        if (identifierToken != null && identifierToken.getType() == TokenType.Identifier) {
            Token assignmentToken = state.next();
            if (assignmentToken != null && assignmentToken.getType() == TokenType.Assign) {
                Node node = parseExpression(state);
                return new ConstVariableDefinitionNode(identifierToken.getValue(), node);
            }

            return new ErrorNode("Const variable must be initialized");
        }

        return new ErrorNode("Expected const variable identifier");
    }

    private Node parseExpression(ParserState state) {
        Node left = parsePrimary(state);
        return parseBinaryOperationRight(state, 0, left);
    }

    private Node parseBinaryOperationRight(ParserState state, int expressionPrecedence, Node left) {
        Node currentLeft = left;

        while (state.hasToken()) {
            TokenType type = state.getToken().getType();

            int precedence = ParserUtils.getOperatorPrecedence(type);

            if (precedence < expressionPrecedence) {
                return currentLeft;
            }

            BinaryOperationType operationType = ParserUtils.getBinaryOperatorTypeFromTokenType(type);

            state.next();

            Node right = parsePrimary(state);
            if (state.hasToken()) {
                int nextPrecedence = ParserUtils.getOperatorPrecedence(state.getToken().getType());

                if (nextPrecedence > precedence) {
                    right = parseBinaryOperationRight(state, precedence + 1, right);
                }
            }

            currentLeft = new BinaryOperationNode(operationType, currentLeft, right);
        }

        return currentLeft;
    }

    private Node parsePrimary(ParserState state) {
        Token token = state.next();
        if (token != null) {
            return switch (token.getType()) {
                case IntegerLiteral -> new LiteralNode<>(Integer.parseInt(token.getValue()), LiteralNode.LiteralType.Integer);
                case FloatLiteral -> new LiteralNode<>(Float.parseFloat(token.getValue()), LiteralNode.LiteralType.Float);
                case StringLiterial -> new LiteralNode<>(token.getValue(), LiteralNode.LiteralType.String);
                case Identifier -> {
                    Node node = parseFunctionCall(state, token);

                    if (node == null) {
                        node = new VariableNode(token.getValue());
                    }

                    yield node;
                }
                default -> new ErrorNode("Unexpected token: " + token);
            };
        }

        return new ErrorNode("No tokens left");
    }

    private Node parseFunctionCall(ParserState state, Token token) {
        Token parenthesesToken = state.getToken();
        if (parenthesesToken != null && parenthesesToken.getType() == TokenType.OpeningParentheses) {
            List<Node> inputs = new ArrayList<>();

            while (state.hasToken()) {
                state.next();

                if (state.hasToken()) {
                    inputs.add(parseExpression(state));
                } else {
                    return new ErrorNode("Function call was not finished");
                }

                Token commaToken = state.getToken();
                if (commaToken != null && commaToken.getType() == TokenType.Comma) {
                    continue;
                } else if (commaToken != null && commaToken.getType() == TokenType.ClosingParentheses) {
                    state.next();
                    return new FunctionCallNode(token.getValue(), inputs);
                } else {
                    return new ErrorNode("Unexpected token: " + commaToken);
                }
            }

            return new ErrorNode("Function call was not finished");
        }

        return null;
    }

}
