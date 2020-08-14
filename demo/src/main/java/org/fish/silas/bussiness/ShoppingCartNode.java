package org.fish.silas.bussiness;

import org.fish.silas.Rune;
import org.karic.silas.core.BusinessNode;

@Rune(id = "200", childMatchedId = "300", childMismatchId = "400", childExceptionId = "500", group = "1")
public class ShoppingCartNode extends BusinessNode {
    @Override
    public void init() {
        System.out.println("ShoppingCartNode init()");
    }

    @Override
    public boolean isMatch() {
        return false;
    }

    @Override
    public void onMatch() {
        System.out.println("ShoppingCartNode onMatch()");
    }

    @Override
    public void onMismatch() {
        System.out.println("ShoppingCartNode onMismatch()");
    }

    @Override
    public void onException() {
        System.out.println("ShoppingCartNode onException()");
    }
}
