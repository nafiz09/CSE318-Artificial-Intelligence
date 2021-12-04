public class Manhattan implements Heuristic {
    @Override
    public int getValue(Search_Node child, Search_Node goal) {
        int value = getManhattanHeuristicValue(child.matrix, goal.matrix);
        return value;
    }

    public int getManhattanHeuristicValue(int[][] child, int[][] goal) {
        int sum = 0;
        for (int i = 0; i < child.length; i++) {
            for (int j = 0; j < child[0].length; j++) {
                // Get the tile
                int current = child[i][j];

                // for each tile in the goal state
                for (int k = 0; k < goal.length; k++) {
                    for (int n = 0; n < goal[0].length; n++) {

                        if (goal[k][n] == current && current != 0) {
                            sum = sum + (Math.abs(i - k) + Math.abs(j - n));
                        }
                    }
                }
            }
        }
        return sum;
    }

}

