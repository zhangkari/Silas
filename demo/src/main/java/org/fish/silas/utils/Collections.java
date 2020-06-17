package org.fish.silas.utils;

import java.util.Collection;

public final class Collections {
    public static boolean isEmpty(Collection<?> c) {
        return c == null || c.size() < 1;
    }
}
