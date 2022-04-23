public class Board {
    public static State initializeBoardPlay(State root,int type,int turn){
        Game.turn = turn;
        int i;
        for(i = 0; i < 6; i++) {

            root.maxArr[i] = 4;
            root.minArr[i] = 4;
        }
        Heuristic heuristic = null;
            if(type == 1)
                heuristic = new H1();
            else if(type == 2)
                heuristic = new H2();
            else if(type == 3)
                heuristic = new H3(0);
            else if(type == 4)
                heuristic = new H4(0, 0);
            else if(type == 5)
                heuristic = new H5();
            else if(type == 6)
                heuristic = new H6();
        root.value = heuristic.getValue(root);
        root.printState();
        return root;
    }

    public static State initializeBoardSim(State root){
        int i;
        for(i = 0; i < 6; i++) {

            root.maxArr[i] = 4;
            root.minArr[i] = 4;
        }
        root.value = 0;
        return root;
    }
}
