package org.karic.silas.core;

class Handler<Model extends Contract.IModel, View extends Contract.IView, Presenter extends Contract.IPresenter> implements Node<Model, View, Presenter> {
    private Node<Model, View, Presenter> node;

    public Handler(Node<Model, View, Presenter> node) {
        this.node = node;
    }

    @Override
    public void init() {
        node.init();
    }

    @Override
    public boolean isMatch() {
        return node.isMatch();
    }

    @Override
    public void onMatch() {
        node.onMatch();
    }

    @Override
    public void onMismatch() {
        node.onMismatch();
    }

    @Override
    public void onException() {
        node.onException();
    }

    @Override
    public void registerView(View view) {
        node.registerView(view);
    }

    @Override
    public void registerPresenter(Presenter presenter) {
        node.registerPresenter(presenter);
    }

    @Override
    public void registerModel(Model model) {
        node.registerModel(model);
    }

    @Override
    public View getView() {
        return node.getView();
    }

    @Override
    public Presenter getPresenter() {
        return node.getPresenter();
    }

    @Override
    public Model getModel() {
        return node.getModel();
    }
}