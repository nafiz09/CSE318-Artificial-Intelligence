public class LinearConflict implements Heuristic{
    @Override
    public int getValue(Search_Node a, Search_Node b) {

        return linearConflict(a, b);

    }
    public int linearConflict(Search_Node a, Search_Node b){
        int totalcost=0;

        for(int i=0;i<a.k ;i++){
            for(int j=0;j<a.k;j++){
                if(a.matrix[i][j]==0) continue;
                int goalrow = (a.matrix[i][j]-1)/a.k;
                int goalcol = (a.matrix[i][j]-1)%a.k;
                totalcost += (Math.abs(goalrow-i )+Math.abs(goalcol-j));

                if(goalrow!=i) continue;
                int temprow;
                for (int k=j+1 ; k<a.k;k++){
                    if(a.matrix[i][k]==0) continue;
                    temprow = (a.matrix[i][k] - 1)/a.k;
                    if(temprow==i && a.matrix[i][j]>a.matrix[i][k]) totalcost+=2;
                }
            }
        }
        return totalcost;
    }
}
