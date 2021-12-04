import java.util.LinkedList;
import java.util.PriorityQueue;

public class Astar extends Movement {
    private Search_Node parent;
    private PriorityQueue<Search_Node> open;
    private LinkedList<Search_Node> closed;
    private int expandedStates = 0;
    private Heuristic heuristic;
    private String modeWords;

    public Astar(Search_Node input, Search_Node goal, int mode) {
        parent = input;
        this.goal = goal;
        open = new PriorityQueue<Search_Node>();
        closed = new LinkedList<Search_Node>();
        defineMode(mode);
    }


    private void defineMode(int mode) {
        switch (mode) {
            case (1):
                heuristic = new Manhattan();
                modeWords = "Manhattan";
                break;
            case (2):
                heuristic = new Hamming();
                modeWords = "Hamming";
                break;
            case (3):
                heuristic = new LinearConflict();
                modeWords = "Linear Conflict";
        }
    }

    @Override
    public void findPath() {
        boolean solutionFound = false;

        parent.setCost(0);
        parent.setDepth(0);

        open.clear();
        closed.clear();

        goal.setParent(null);

        this.addToOpen(parent);

        while (open.size() != 0) {

            Search_Node current = getFirstInOpen();

            if (current.equals(goal)) {
                int expanded = closed.size();
                int explored = expanded + open.size();
                System.out.println("\n");
                System.out.println("<><><><><><><><><><><><><><><><>");
                System.out.println(modeWords);
                System.out.println("<><><><><><><><><><><><><><><><>");
                //System.out.println("\n");
                this.printSuccess(current, explored, expanded);
                System.out.println("Cost : " +current.getDepth());
                solutionFound = true;
                break;
            }
            //expandedStates++;

            this.removeFromOpen(current);
            this.addToClosed(current);
            //System.out.println("closedlist1:");
            //showClosed();
            //System.out.println("test");
            //this.printGrid(current);
            //System.out.println("test1");

            this.findChildren(current);
        }

        if (!solutionFound) {
            System.out.println("No GridStates left to explore");

        }
    }

    @Override
    public void movement(int zeroIndexX,int zeroIndexY, int direction, Search_Node current) {
        if (rulesForMovement(zeroIndexX,zeroIndexY, direction, current.k)) {
            Search_Node child = createChild(zeroIndexX,zeroIndexY, direction, current);
            //System.out.println("closed list after child creation:");
            //showClosed();
            child.setCost(current.getCost() + 1);
            child.setParent(current);
            child.setHeuristicCost(heuristic.getValue(child, goal));

            if (!inOpenList(child) && !inClosedList(child)) {
                this.addToOpen(child);
            } else {
                Search_Node checker = inList(child, open);
                if (checker != null) {
                    if (child.getCost() < checker.getCost()) {
                        removeFromOpen(checker);
                        addToOpen(child);
                    } else {
                        checker = inList(child, closed);
                        if (checker != null) {
                            if (child.getCost() < checker.getCost()) {
                                removeFromClosed(checker);
                                addToClosed(child);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Search_Node getFirstInOpen() {
        return open.peek();
    }

    public void addToOpen(Search_Node state) {
        open.add(state);
    }

    public boolean inOpenList(Search_Node state) {
        return open.contains(state);
    }

    public void removeFromOpen(Search_Node state) {
        open.remove(state);
    }

    public void addToClosed(Search_Node state) {
        closed.add(state);
    }

    public boolean inClosedList(Search_Node state) {
        return closed.contains(state);
    }
    void showClosed() {
        for (Search_Node node : closed) {
            node.printMatrix();
        }
    }

    public void removeFromClosed(Search_Node state) {
        closed.remove(state);
    }

    @Override
    public void printType() {
        System.out.println("A* Search - " + modeWords);
    }
}

