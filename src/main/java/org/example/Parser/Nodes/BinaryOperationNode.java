
package org.example.Parser.Nodes;

import lombok.Getter;

public class BinaryOperationNode extends Node {
    @Getter
    private BinaryOperationType type;

    @Getter
    private Node left;

    @Getter
    private Node right;

    public BinaryOperationNode(BinaryOperationType type, Node left, Node right) {
        this.type = type;
        this.left = left;
        this.right = right;
    }

    @Override
    public NodeType getType() {
        return NodeType.BinaryOperation;
    }
}
