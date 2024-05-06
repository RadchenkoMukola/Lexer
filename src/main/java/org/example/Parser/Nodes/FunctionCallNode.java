
package org.example.Parser.Nodes;

import lombok.Getter;

import java.util.List;

public class FunctionCallNode extends Node {
    @Getter
    private String name;

    @Getter
    private List<Node> inputs;

    public FunctionCallNode(String name, List<Node> inputs) {
        this.name = name;
        this.inputs = inputs;
    }

    @Override
    public NodeType getType() {
        return NodeType.FunctionCall;
    }
}
