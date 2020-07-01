package org.karic.silas.core;

class NodeWrapper extends Node {
    String id;
    String nextMatchId;
    String nextErrorId;
    String nextExceptionId;

    Node currentNode;
    NodeWrapper nextMatch;
    NodeWrapper nextError;
    NodeWrapper nextException;

    int sequence;

    int groupSequence;

    public NodeWrapper(Node node) {
        currentNode = node;
    }
}
