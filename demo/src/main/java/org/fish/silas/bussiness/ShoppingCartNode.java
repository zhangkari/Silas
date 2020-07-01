package org.fish.silas.bussiness;

import org.fish.silas.Rune;
import org.karic.silas.core.Node;

@Rune(id = "200", childMatchedId = "300", childMismatchId = "400", childExceptionId = "500", group = "1")
public class ShoppingCartNode extends Node {
    @Override
    public void init() {

    }

    @Override
    public void run() {

    }

    @Override
    public boolean isMatch() {
        return false;
    }

    @Override
    public void onError() {

    }

    @Override
    public void onException() {

    }
}
