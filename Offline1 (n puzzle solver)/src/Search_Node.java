import java.util.Arrays;
import java.util.Scanner;

public class Search_Node implements Comparable<Search_Node>{
    public int k;
    public int[][] matrix;
    public Search_Node parent = null;
    private int cost = 0;
    public int depth = 1;
    private int heuristicCost = 0;

    public Search_Node(int k) {
        this.k = k;
        this.matrix = new int[k][k];
        Scanner sc = new Scanner(System.in);
        for(int i = 0; i < k; i++) {
            for(int j = 0; j < k; j++) {
                String c = sc.nextLine();
                if(!c.equals("*")){
                    int num = Integer.parseInt(c);
                    this.matrix[i][j] = num;
                }
                else
                    this.matrix[i][j] = 0;

            }
        }
    }

    public Search_Node(int[][] mat)
    {
        this.k = mat.length;
        this.matrix = mat;
    }

    public int[][] getElements() {
        return matrix;
    }

    public void setElements(int k) {
        this.k = k;
        this.matrix = new int[k][k];
        Scanner sc = new Scanner(System.in);
        for(int i = 0; i < k; i++) {
            for(int j = 0; j < k; j++) {
                String c = sc.nextLine();
                if(!c.equals("*")){
                    int num = Integer.parseInt(c);
                    this.matrix[i][j] = num;
                }
                else
                    this.matrix[i][j] = 0;

            }
        }
    }

    public int setParent(Search_Node newParent) {
        parent = newParent;
        if(parent != null){
            depth = parent.getDepth() + 1;
        }
        return depth;
    }

    public Search_Node getParent() {
        return parent;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }

    public void setHeuristicCost(int heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    public int getHeuristicCost() {
        return heuristicCost;
    }

    @Override
    public boolean equals(Object obj) {
        Search_Node testGrid = (Search_Node) obj;
        int[][] test = testGrid.getElements();
        return arrayCompare(test);
    }


    public boolean arrayCompare(int[][] mat)
    {
        for(int i = 0; i < k; i++) {
            for(int j = 0; j < k; j++) {
                if(mat[i][j] != matrix[i][j])
                    return false;
            }
        }
        return true;
    }

    @Override
    public int compareTo(Search_Node oGrid) {

        int tCost = this.getHeuristicCost() + this.getDepth();
        int oCost = oGrid.getHeuristicCost() + oGrid.getDepth();

        return Integer.compare(tCost, oCost);
    }

    public void printMatrix() {
        System.out.println("-----------------");
        for(int i = 0; i < k; i++) {
            for(int j = 0; j < k; j++) {
                if(matrix[i][j] != 0) {
                    System.out.print(this.matrix[i][j] + " ");
                }
                else
                    System.out.print("* ");
            }
            System.out.println();
        }
        System.out.println("-----------------");
    }

    boolean solvability(){
        int inversions = 0;
        int blankRow = 0;
        if(k % 2 == 0){
            blankRow = ZeroIndexX();
        }

        int[] arr = new int[k*k];
        for(int i = 0; i < k; i++){
            for(int j = 0; j < k; j++){
                arr[i*k+j] = matrix[i][j];
            }
        }
        for(int i = 0; i < k*k; i++){
            for(int j = i+1; j < k*k; j++){
                if(arr[i] != 0 && arr[j] != 0 && arr[i] > arr[j]){
                    inversions++;
                }
            }
        }
        //System.out.println(inversions);
        if(k % 2 == 1 && inversions % 2 == 0){
            return true;
        }

        else if(k % 2 == 0 && inversions % 2 == 1 && blankRow % 2 == 0){
            return true;
        }

        else if(k % 2 == 0 && inversions % 2 == 0 && blankRow % 2 == 1){
            return true;
        }

        else
            return false;

    }

    int ZeroIndexX(){
        int z = 0;
        for(int i = 0; i < k; i++){
            for(int j = 0; j < k; j++){
                if(matrix[i][j] == 0){
                    z = i;
                }
            }
        }
        return z;
    }

    int ZeroIndexY(){
        int z = 0;
        for(int i = 0; i < k; i++){
            for(int j = 0; j < k; j++){
                if(matrix[i][j] == 0){
                    z = j;
                }
            }
        }
        return z;
    }



    @Override
    public String toString() {
        return "Search_Node{" +
                "k=" + k +
                ", matrix=" + Arrays.toString(matrix) +
                ", cost=" + cost +
                ", depth=" + depth +
                ", heuristicCost=" + heuristicCost +
                '}';
    }
}

