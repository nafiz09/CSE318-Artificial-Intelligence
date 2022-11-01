public class H1 implements Heuristic {
    @Override
    public int getValue(State state) {
        return (state.minScore - state.maxScore);
    }
}
