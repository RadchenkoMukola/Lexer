
package org.example.Parser.Nodes;

import lombok.Getter;

public class ErrorNode extends Node {
    @Getter
    private String text;

    public ErrorNode(String text) {
        this.text = text;
    }

    @Override
    public NodeType getType() {
        return NodeType.Error;
    }
}
