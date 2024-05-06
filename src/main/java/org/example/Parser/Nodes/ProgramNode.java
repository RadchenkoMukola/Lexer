package org.example.Parser.Nodes;

import lombok.Getter;

import java.util.List;

public class ProgramNode extends Node {
    @Getter
    private List<Node> nodes;

    public ProgramNode(List<Node> nodes)
    {
        this.nodes = nodes;
    }

    @Override
    public NodeType getType() {
        return NodeType.Program;
    }
}