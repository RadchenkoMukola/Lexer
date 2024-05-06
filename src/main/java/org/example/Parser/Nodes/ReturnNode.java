package org.example.Parser.Nodes;

import lombok.Getter;

public class ReturnNode extends Node {
    @Getter
    private Node expression;

    public ReturnNode(Node expression) {
        this.expression = expression;
    }

    @Override
    public NodeType getType() {
        return NodeType.Return;
    }
}