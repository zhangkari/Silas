package org.karic.silas.core;

import java.util.ArrayList;
import java.util.List;

public final class Context {
    private static final Context _instance = new Context();

    @SuppressWarnings("rawtypes")
    private List<ContextNode> mNodes;

    private Context() {
        if (_instance != null) {
            throw new RuntimeException("Do not instantiate me by Reflect !");
        }

        mNodes = new ArrayList<>(32);
    }

    public static Context getDefault() {
        return _instance;
    }


    public Context register(Node<? extends Contract.IModel, ? extends Contract.IView, ? extends Contract.IPresenter> node) {
        mNodes.add(new ContextNode<>(node));
        return this;
    }

    @SuppressWarnings("rawtypes")
    public void start(String id) {
        ContextNode node = findNodeById(id);
        if (node != null) {
            start(node);
        }
    }

    public void start() {
        if (mNodes.size() > 0) {
            start(mNodes.get(0));
        }
    }

    @SuppressWarnings("rawtypes")
    private void start(ContextNode node) {
        try {
            node.init();
            if (node.isMatch()) {
                node.onMatch();
                start(node.getConfig().nextMatchId);
            } else {
                node.onMismatch();
                start(node.getConfig().nextMismatchId);
            }
        } catch (Exception e) {
            node.onException();
            start(node.getConfig().nextExceptionId);
        }
    }

    @SuppressWarnings("rawtypes")
    private ContextNode findNodeById(String id) {
        if (id == null) {
            return null;
        }
        for (ContextNode node : mNodes) {
            if (id.equals(node.getConfig().id)) {
                return node;
            }
        }
        return null;
    }
}
