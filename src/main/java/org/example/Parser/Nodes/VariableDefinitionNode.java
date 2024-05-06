package org.example.Parser.Nodes;

import lombok.Getter;

public class VariableDefinitionNode extends Node {
    @Getter
    String name;

    @Getter
    Node expression;

    public VariableDefinitionNode(String name, Node expression) {
        this.name = name;
        this.expression = expression;
    }

    @Override
    public NodeType getType() {
        return NodeType.VariableDefinition;
    }
}