public class Hamming implements Heuristic {

    @Override
    public int getValue(Search_Node a, Search_Node b) {
        int n = getHammingHeuristic(a.matrix, b.matrix);
        return n;
    }

    public int getHammingHeuristic(int[][] a, int[][] b) {
        int count = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                if (a[i][j] != b[i][j] && a[i][j] != 0) {
                    count++;
                }
            }
        }
        return count;
    }
}
