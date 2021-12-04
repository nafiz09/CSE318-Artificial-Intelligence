import java.util.Scanner;

public class Solver {
    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();


        Search_Node node = new Search_Node(n);
        System.out.println("Initial State");
        node.printMatrix();
        Search_Node goal = new Search_Node(n);
        System.out.println("Goal State");
        goal.printMatrix();

        //Thread.sleep(1500);

        Astar astarMan = new Astar(node, goal,1);
        Astar astarHam = new Astar(node, goal,2);
        Astar astarLin = new Astar(node, goal,3);

        if(node.solvability()) {
            astarHam.findPath();
            astarMan.findPath();
            astarLin.findPath();
        }
        else
            System.out.println("Not solvable");
    }
}
