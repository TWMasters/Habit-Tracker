package controller;

import model.Model;

public class ConcreteController implements Controller{
    private Model model;

    public ConcreteController(Model model) {
        this.model = model;
    }

    @Override
    public void getData() {
        model.notifyViews();
    }
}
