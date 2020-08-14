package org.karic.silas.core;

import org.fish.silas.Rune;

class ContextNode<Model extends Contract.IModel, View extends Contract.IView, Presenter extends Contract.IPresenter> implements Node<Model, View, Presenter> {
    private Config config;
    private Handler<Model, View, Presenter> handler;

    public ContextNode(Node<Model, View, Presenter> node) {
        Rune rune = checkNodeRune(node);
        config = new Config(rune);
        handler = new Handler<>(node);
    }

    @SuppressWarnings("rawtypes")
    private static Rune checkNodeRune(Node node) {
        Rune rune = node.getClass().getAnnotation(Rune.class);
        if (rune == null) {
            throw new RuntimeException("Node must be annotated with @Rune !");
        }
        if (rune.id().length() < 1) {
            throw new RuntimeException("Node must specified id !");
        }
        return rune;
    }

    @Override
    public void init() {
        handler.init();
    }

    @Override
    public boolean isMatch() {
        return handler.isMatch();
    }

    @Override
    public void onMatch() {
        handler.onMatch();
    }

    @Override
    public void onMismatch() {
        handler.onMismatch();
    }

    @Override
    public void onException() {
        handler.onException();
    }

    @Override
    public void registerView(View view) {
        handler.registerView(view);
    }

    @Override
    public void registerPresenter(Presenter presenter) {
        handler.registerPresenter(presenter);
    }

    @Override
    public void registerModel(Model model) {
        handler.registerModel(model);
    }

    @Override
    public View getView() {
        return handler.getView();
    }

    @Override
    public Presenter getPresenter() {
        return handler.getPresenter();
    }

    @Override
    public Model getModel() {
        return handler.getModel();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object object) {
        if (!(object instanceof ContextNode)) {
            return false;
        }
        ContextNode<Model, View, Presenter> node = (ContextNode<Model, View, Presenter>) object;
        return config.equals(node.config);
    }

    public Config getConfig() {
        return config;
    }

    public Handler<Model, View, Presenter> getHandler() {
        return handler;
    }
}
