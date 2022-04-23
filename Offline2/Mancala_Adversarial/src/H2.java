public class H2 implements Heuristic {
    int w1 = 1;
    int w2 = 1;
    @Override
    public int getValue(State state) {
        int scoredif = state.minScore - state.maxScore;
        int blockcount = state.countBlocks(state.minArr) - state.countBlocks(state.maxArr);
        return (w1 * scoredif + w2 * blockcount);
    }
}
