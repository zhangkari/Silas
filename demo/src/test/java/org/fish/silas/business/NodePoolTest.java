package org.fish.silas.business;

import org.junit.Test;
import org.karic.silas.NodePool;

public class NodePoolTest {
    @Test
    public void testFetchDishNode() {
        String className = NodePool.find("100");
        System.out.println(className);

        className = NodePool.find("200");
        System.out.println(className);
    }
}
