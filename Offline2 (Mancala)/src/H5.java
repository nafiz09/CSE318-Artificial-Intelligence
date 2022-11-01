public class H5 implements Heuristic{
    @Override
    public int getValue(State state) {
        return state.countBlocks(state.minArr);
    }
}
