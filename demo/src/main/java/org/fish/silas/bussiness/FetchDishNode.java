package org.fish.silas.bussiness;

import org.fish.silas.Rune;
import org.karic.silas.core.Node;

@Rune(id = "100", childMatchedId = "200", childMismatchId = "400", childExceptionId = "500", group = "1")
public class FetchDishNode extends Node {
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
