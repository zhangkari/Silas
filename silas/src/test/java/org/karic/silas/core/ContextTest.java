package org.karic.silas.core;

import org.fish.silas.Rune;
import org.junit.Test;

import java.util.Random;

public class ContextTest {

    @Test
    public void testStart() {
        Node1 node1 = new Node1();
        node1.registerModel(new Model1());
        node1.registerView(new View1());
        node1.registerPresenter(new Presenter1());

        Node2 node2 = new Node2();
        node2.registerModel(new Model2());
        node2.registerView(new View2());
        node2.registerPresenter(new Presenter2());

        Node3 node3 = new Node3();
        node3.registerModel(new Model3());
        node3.registerView(new View3());
        node3.registerPresenter(new Presenter3());

        Context.getDefault()
                .register(node1)
                .register(node2)
                .register(node3);

        System.out.println("-----------------------");
        System.out.println("Node1 match => Node2");
        System.out.println("Node1 mismatch => Node3");
        System.out.println("-----------------------");
        Context.getDefault().start();
    }


    static class Model1 implements Contract.IModel {
        public boolean isSuccess() {
            return new Random().nextInt(2) == 1;
        }
    }

    static class View1 implements Contract.IView {

    }

    static class Presenter1 implements Contract.IPresenter {

    }

    @Rune(id = "1000", childMatchedId = "2000", childMismatchId = "3000", childExceptionId = "4000", group = "1")
    static class Node1 extends BusinessNode<Model1, View1, Presenter1> {
        @Override
        public void init() {
            System.out.println("Node1 init()");
        }

        @Override
        public boolean isMatch() {
            return getModel().isSuccess();
        }

        @Override
        public void onMatch() {
            System.out.println("Node1 onMatch()");
        }

        @Override
        public void onMismatch() {
            System.out.println("Node1 onMismatch()");
        }

        @Override
        public void onException() {
            System.out.println("Node1 onException()");
        }
    }


    static class Model2 implements Contract.IModel {
        public boolean isSuccess() {
            return new Random().nextInt(2) == 1;
        }
    }

    static class View2 implements Contract.IView {

    }

    static class Presenter2 implements Contract.IPresenter {

    }

    @Rune(id = "2000")
    static class Node2 extends BusinessNode<Model2, View2, Presenter2> {
        @Override
        public void init() {
            System.out.println("Node2 init()");
        }

        @Override
        public boolean isMatch() {
            return getModel().isSuccess();
        }

        @Override
        public void onMatch() {
            System.out.println("Node2 onMatch()");
        }

        @Override
        public void onMismatch() {
            System.out.println("Node2 onMismatch()");
        }

        @Override
        public void onException() {
            System.out.println("Node2 onException()");
        }
    }

    static class Model3 implements Contract.IModel {
        public boolean isSuccess() {
            return new Random().nextInt(2) == 1;
        }
    }

    static class View3 implements Contract.IView {

    }

    static class Presenter3 implements Contract.IPresenter {

    }

    @Rune(id = "3000")
    static class Node3 extends BusinessNode<Model3, View3, Presenter3> {
        @Override
        public void init() {
            System.out.println("Node3 init()");
        }

        @Override
        public boolean isMatch() {
            return getModel().isSuccess();
        }

        @Override
        public void onMatch() {
            System.out.println("Node3 onMatch()");
        }

        @Override
        public void onMismatch() {
            System.out.println("Node3 onMismatch()");
        }

        @Override
        public void onException() {
            System.out.println("Node3 onException()");
        }
    }
}
