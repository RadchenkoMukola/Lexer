
package org.example.Parser.Nodes;

import lombok.Getter;

public class LiteralNode<T> extends Node {
    public enum LiteralType {
        Integer,
        Float,
        String
    }

    @Getter
    private T value;

    @Getter
    private LiteralType type;

    public LiteralNode(T value, LiteralType type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public NodeType getType() {
        return NodeType.Literal;
    }
}
