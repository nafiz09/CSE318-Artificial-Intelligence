import java.util.Collection;

public abstract class Movement {
    public Search_Node goal;

    public abstract void findPath();

    public abstract void movement(int zeroIndexX,int zeroIndexY, int direction, Search_Node current);

    public void findChildren(Search_Node current) {
        int zeroIndexX = current.ZeroIndexX();
        //System.out.println("zeroIndexX: " + zeroIndexX);
        int zeroIndexY = current.ZeroIndexY();
        //System.out.println("zeroIndexY: " + zeroIndexY);
        // 1 = right, 2 = left, 3 = up, 4 = down
        this.movement(zeroIndexX, zeroIndexY,1, current);
        this.movement(zeroIndexX, zeroIndexY,2, current);
        this.movement(zeroIndexX, zeroIndexY,3, current);
        this.movement(zeroIndexX, zeroIndexY,4, current);
    }

    public abstract Search_Node getFirstInOpen();

    public abstract void addToOpen(Search_Node state);

    public abstract boolean inOpenList(Search_Node state);

    public abstract void removeFromOpen(Search_Node state);

    public abstract void addToClosed(Search_Node state);

    public abstract boolean inClosedList(Search_Node state);

    public abstract void removeFromClosed(Search_Node state);

    public boolean rulesForMovement(int zeroIndexX,int zeroIndexY, int direction,int k) {
        boolean withinPossibility = false;
        if (zeroIndexY == k-1 && direction == 1) {
            withinPossibility = false;
        } else if (zeroIndexY == 0 && direction == 2) {
            withinPossibility = false;
        } else if (zeroIndexX == 0 && direction == 3) {
            withinPossibility = false;
        }
        else if (zeroIndexX == k-1 && direction == 4) {
            withinPossibility = false;
        }
        else {
            withinPossibility = true;
        }
        return withinPossibility;
    }


    public Search_Node createChild(int zeroIndexX,int zeroIndexY,
                               int direction, Search_Node parent) {

        int[][] childElements = new int[parent.k][parent.k];
        for(int i=0;i<parent.k;i++)
        {
            for(int j=0;j<parent.k;j++)
            {
                childElements[i][j]  = parent.matrix[i][j];
            }
        }
        Search_Node child = new Search_Node(childElements);
//        System.out.println("new child:");
//        child.printMatrix();
        if(direction == 1) {
            int temp = child.matrix[zeroIndexX][zeroIndexY+1];
            child.matrix[zeroIndexX][zeroIndexY+1] = child.matrix[zeroIndexX][zeroIndexY];
            child.matrix[zeroIndexX][zeroIndexY] = temp;
        }

        if(direction == 2) {
            int temp = child.matrix[zeroIndexX][zeroIndexY-1];
            child.matrix[zeroIndexX][zeroIndexY-1] = child.matrix[zeroIndexX][zeroIndexY];
            child.matrix[zeroIndexX][zeroIndexY] = temp;
        }

        if(direction == 4) {
            int temp = child.matrix[zeroIndexX+1][zeroIndexY];
            child.matrix[zeroIndexX+1][zeroIndexY] = child.matrix[zeroIndexX][zeroIndexY];
            child.matrix[zeroIndexX][zeroIndexY] = temp;
        }

        if(direction == 3) {
            int temp = child.matrix[zeroIndexX-1][zeroIndexY];
            child.matrix[zeroIndexX-1][zeroIndexY] = child.matrix[zeroIndexX][zeroIndexY];
            child.matrix[zeroIndexX][zeroIndexY] = temp;
        }
        child.parent = parent;
        child.depth = parent.depth + 1;
        //System.out.println("child:");
        //child.printMatrix();
        return child;

    }

    public Search_Node inList(Search_Node state, Collection<Search_Node> list) {
        for (Search_Node checker : list) {
            if (state.equals(checker)) {
                return checker;
            }
        }

        return null;
    }


    public void printGrid(Search_Node gridInput) {
        gridInput.printMatrix();
    }

    public void printSuccess(Search_Node current, int explored, int expanded) {
        System.out.println(">>>>>>>------------<<<<<<<");
        System.out.println("Found goal state!");
        //this.printGrid(current);

        System.out.println(">>>>>>>------------<<<<<<<");
        System.out.println("Steps : ");
        int steps = printPaths(current, current.depth,current.depth);
        //System.out.println("Parent to Goal:");
        //if(current.getParent() != null){
        //    this.printGrid(new Search_Node(current.getParent().getElements()));
       // }
        System.out.println("Explored: " + explored);
        System.out.println("Expanded: " + expanded);
    }

    public static int printPaths(Search_Node node, int cost,int steps)
    {
        int st = steps;
        if(node == null)
            return 0;
        st--;
        cost = 1 + printPaths(node.parent, cost,st);
        System.out.println("***************************");
        System.out.println("Step " + ++st);
        node.printMatrix();
        System.out.println("***************************");
        return cost;
    }

    public abstract void printType();

}

