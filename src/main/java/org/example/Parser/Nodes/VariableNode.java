
package org.example.Parser.Nodes;

import lombok.Getter;

public class VariableNode extends Node {
    @Getter
    String name;

    public VariableNode(String name) {
        this.name = name;
    }

    @Override
    public NodeType getType() {
        return NodeType.Variable;
    }
}
