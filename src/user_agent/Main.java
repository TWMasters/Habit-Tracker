package user_agent;

import controller.ConcreteController;
import controller.Controller;
import model.ConcreteModel;
import model.Model;
import view.TextView;
import view.View;

public class Main {
    public static void main(String[] args) {
        //  Compose Application
        Model model = ConcreteModel.getModel();
        Controller controller = new ConcreteController(model);
        View view = new TextView(controller);
        model.addView(view);
        // Run
        view.render();
    }
}
