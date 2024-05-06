package org.example;

import org.example.Lexer.Lexer;
import org.example.Lexer.LexerException;
import org.example.Lexer.Token;
import org.example.Parser.Nodes.ProgramNode;
import org.example.Parser.Parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, LexerException {
        FileInputStream input = new FileInputStream("src/main/resources/code.js");
        String source = new String(input.readAllBytes());

        Lexer lexer = Lexer.getInstance();
        List<Token> tokens = lexer.getTokens(source);

        for (Token token : tokens) {
            System.out.println(token);
        }

        Parser parser = Parser.getParser();
        ProgramNode root = parser.parse(tokens);
        System.out.println(root);
    }
}