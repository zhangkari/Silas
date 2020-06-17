package org.karic.silas.core;

public interface HandlerChain extends Handler {
    void addNode(Node... arg);
}
