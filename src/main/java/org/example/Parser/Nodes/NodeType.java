package org.example.Parser.Nodes;

public enum NodeType {
    None,
    Program,
    Error,
    Literal,
    Variable,
    BinaryOperation,
    VariableDefinition,
    ConstantDefinition,
    FunctionDeclaration,
    FunctionCall,
    Return
}