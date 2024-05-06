
package org.example.Parser.Nodes;

import lombok.Getter;

import java.util.List;

public class FunctionDeclarationNode extends Node {
    @Getter
    private String name;

    @Getter
    private List<String> parametersNames;

    @Getter
    private List<Node> body;

    public FunctionDeclarationNode(String name, List<String> parametersNames, List<Node> body) {
        this.name = name;
        this.parametersNames = parametersNames;
        this.body = body;
    }

    @Override
    public NodeType getType() {
        return NodeType.FunctionDeclaration;
    }
}
