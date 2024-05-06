
package org.example.Parser.Nodes;

import lombok.Getter;

public class ConstVariableDefinitionNode extends Node {
    @Getter
    private String name;

    @Getter
    private Node expression;

    public ConstVariableDefinitionNode(String name, Node expression) {
        this.name = name;
        this.expression = expression;
    }

    @Override
    public NodeType getType() {
        return NodeType.ConstantDefinition;
    }
}
