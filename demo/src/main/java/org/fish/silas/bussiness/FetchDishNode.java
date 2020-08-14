package org.fish.silas.bussiness;

import org.fish.silas.Rune;
import org.karic.silas.core.BusinessNode;

@Rune(id = "100", childMatchedId = "200", childMismatchId = "400", childExceptionId = "500", group = "1")
public class FetchDishNode extends BusinessNode {
    @Override
    public void init() {
        System.out.println("FetchDishNode init()");
    }

    @Override
    public boolean isMatch() {
        return true;
    }

    @Override
    public void onMatch() {
        System.out.println("FetchDishNode onMatch()");
    }

    @Override
    public void onMismatch() {
        System.out.println("FetchDishNode onMismatch()");
    }

    @Override
    public void onException() {
        System.out.println("FetchDishNode onException()");
    }
}
