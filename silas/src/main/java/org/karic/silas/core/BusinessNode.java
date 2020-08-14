package org.karic.silas.core;

public abstract class BusinessNode<Model extends Contract.IModel, View extends Contract.IView, Presenter extends Contract.IPresenter> implements Node<Model, View, Presenter> {

    private Model model;
    private View view;
    private Presenter presenter;

    @Override
    public void registerView(View view) {
        this.view = view;
    }

    @Override
    public void registerPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void registerModel(Model model) {
        this.model = model;
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    public Model getModel() {
        return model;
    }
}
