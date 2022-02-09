import org.chocosolver.solver.Model;

public class choco {
    public static void main(String[] args) {
        Model model = new Model("A first model");
        System.out.println(model.getName());
    }
}
