package user_agent;

import model.ConcreteModel;
import model.Model;
import view.TextView;
import view.View;

public class Main {
    public static void main(String[] args) {
        Model model = ConcreteModel.getModel();
        View view = new TextView();
        view.render();
    }
}
