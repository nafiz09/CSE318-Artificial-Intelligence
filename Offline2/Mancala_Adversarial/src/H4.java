public class H4 implements Heuristic {
    int addmoves;
    int CapturedStone;
    int w1 = 1;
    int w2 = 1;
    int w3 = 1;
    int w4 = 1;
    public H4(int addmoves,int CapturedStone){
        this.addmoves = addmoves;
        this.CapturedStone = CapturedStone;
    }
    @Override
    public int getValue(State state) {
        int scoredif = state.minScore - state.maxScore;
        int blockcount = state.countBlocks(state.minArr) - state.countBlocks(state.maxArr);
        return (w1 * scoredif + w2 * blockcount + w3 * addmoves + w4 * CapturedStone);
    }
}
