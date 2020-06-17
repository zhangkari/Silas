package org.karic.silas.core;

import androidx.annotation.NonNull;

public class NodeHandler implements Handler {
    private NodeWrapper currentNode;

    public NodeHandler(@NonNull NodeWrapper currentNode) {
        this.currentNode = currentNode;
    }

    @Override
    public void dispose() {
        currentNode.init();
        try {
            currentNode.run();
            if (currentNode.isMatch()) {
                if (currentNode.nextMatch != null) {
                    setNext(new NodeHandler(currentNode.nextMatch));
                    getNext().dispose();

                }
            } else {
                if (currentNode.nextError != null) {
                    setNext(new NodeHandler(currentNode.nextError));
                    getNext().dispose();
                } else {
                    currentNode.onError();
                }
            }
        } catch (Exception e) {
            if (currentNode.nextException != null) {
                setNext(new NodeHandler(currentNode.nextException));
                getNext().dispose();
            } else {
                currentNode.onException();
            }
        }
    }

    @Override
    public Handler getNext() {
        return null;
    }

    @Override
    public void setNext(Handler handler) {

    }
}
