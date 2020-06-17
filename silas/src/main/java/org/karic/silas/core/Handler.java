package org.karic.silas.core;

public interface Handler {
    void dispose();

    Handler getNext();

    void setNext(Handler handler);
}
