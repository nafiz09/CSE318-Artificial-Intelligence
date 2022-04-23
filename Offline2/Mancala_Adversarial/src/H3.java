public class H3 implements Heuristic {
    int addmoves;
    int w1 = 1;
    int w2 = 1;
    int w3 = 1;
    public H3(int addmoves){
        this.addmoves = addmoves;
    }
    @Override
    public int getValue(State state) {
        int scoredif = state.minScore - state.maxScore;
        int blockcount = state.countBlocks(state.minArr) - state.countBlocks(state.maxArr);
        return (w1 * scoredif + w2 * blockcount + w3 * addmoves);
    }
}
