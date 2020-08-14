package org.karic.silas.core;

public interface Node<Model extends Contract.IModel, View extends Contract.IView, Presenter extends Contract.IPresenter> {
    void init();

    boolean isMatch();

    void onMatch();

    void onMismatch();

    void onException();

    void registerView(View view);

    void registerPresenter(Presenter presenter);

    void registerModel(Model model);

    View getView();

    Presenter getPresenter();

    Model getModel();
}