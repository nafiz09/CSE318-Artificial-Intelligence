import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    public static int player = 0, computer = 1, heuristicType = 4,heuristicType1, heuristicType2,winner;
    public static int turn, binCount = 6, flag = 0, treeTurn, treeFlag = 0;
    public static int game = 1, simulation = 2;
    public static int maxDepth = 3;
    public static int numberOfGamesPerPair = 100;
    public static int[][] stat = new int[7][7];
    public static int[][] reverseStat = new int[7][7];
    public static State root = new State();
    public static State bestState;
    public static int stonesCapPlayer = 0, stonesCapCom = 0, addMovesPlayer = 0, addMovesCom = 0;
    public static ArrayList<State> gameState = new ArrayList<>();
    public static ArrayList<Integer> moves = new ArrayList<>();
    static Heuristic heuristic;


    public static void setHeuristicType(int type,int stonesCap, int addMoves) {
        if(type == 1)
            heuristic = new H1();
        else if(type == 2)
            heuristic = new H2();
        else if(type == 3)
            heuristic = new H3(stonesCap);
        else if(type == 4)
            heuristic = new H4(stonesCap, addMoves);
        else if(type == 5)
            heuristic = new H5();
        else if(type == 6)
            heuristic = new H6();
    }

    public static int minValue(State currState, int depth, int alpha, int beta) {

        if(currState.checkZero(currState.maxArr) || currState.checkZero(currState.minArr) || depth == maxDepth) {
            bestState = currState;
            return currState.move;

        }

        currState.value = Integer.MAX_VALUE;
        int i;
        State newState = null;

        for(i = 0; i < binCount; i++) {

            if(currState.minArr[i] == 0) continue;

            newState = AIvsPlayer.makeState(2, currState, i, treeTurn, depth);
            newState.move = i;

            if(treeFlag == 0) {

                treeTurn = (treeTurn + 1) % 2;
                newState.value = Math.min(newState.value, maxValue(newState, depth + 1, alpha, beta));
            }

            if(treeFlag == 1) {
                newState.value = Math.min(newState.value, minValue(newState, depth + 1, alpha, beta));
                treeFlag = 0;
            }

            if(newState.value <= alpha) {
                bestState = newState;
                return newState.value;
            }
            beta = Math.min(beta, newState.value);

        }
        bestState = newState;
        return newState.value;
    }


    public static int maxValue(State currState, int depth, int alpha, int beta) {

        if(currState.checkZero(currState.maxArr) || currState.checkZero(currState.minArr) || depth == maxDepth) {
            bestState = currState;
            return currState.value;

        }

        currState.value = Integer.MIN_VALUE;
        int i;
        State newState = null;
        for(i = 0; i < binCount; i++) {
            if(currState.maxArr[i] == 0) continue;

            newState = AIvsPlayer.makeState(2, currState, i, treeTurn, depth);
            newState.move = i;
            if(treeFlag == 0) {

                treeTurn = (treeTurn + 1) % 2;
                newState.value = Math.max(newState.value, minValue(newState, depth + 1, alpha, beta));

            }

            //computer or maximizing agent gets an extra turn
            if(treeFlag == 1) {

                newState.value = Math.max(newState.value, maxValue(newState, depth + 1, alpha, beta));
                treeFlag = 0;

            }

            if(newState.value >= beta) {
                //System.out.println("maxValue: 2nd return, depth: " + depth);
                bestState = newState;
                return newState.value;

            }
            alpha = Math.max(alpha, newState.value);

        }
        //System.out.println("maxValue: 3rd return, depth: " + depth);
        bestState = newState;
        return newState.value;
    }


    //takes the current state as a parameter and returns the best possible move
    public static void miniMax(State currState) {

        maxValue(currState, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        //System.out.println("Inside miniMax: " + bestState.move);
    }

    public static void main(String[] args) {
        System.out.println("Enter choice:\n" +
                "1. Player vs AI\n" +
                "2. AI vs AI\n" );

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        if(choice == 1) {
            System.out.println("First move:\n" +
                    "1. Player\n" +
                    "2. AI\n");
            int f = sc.nextInt();
            if(f == 1) {
                Board board = new Board();
                root = board.initializeBoardPlay(root, heuristicType, player);
                gameState.add(root);
                AIvsPlayer.gamePlay(root);
            }
            else
            {
                Board board = new Board();
                root = board.initializeBoardPlay(root, heuristicType, computer);
                gameState.add(root);
                AIvsPlayer.gamePlay(root);
            }

        }

        else {

            int i;

            //round decider
            for (i = 0; i < 5; i++) {

                if (i == 0) {
                    heuristicType1 = 2;
                    heuristicType2 = 1;
                    AIvsAI.match(2, 1);
                } else if (i == 1) {
                    heuristicType1 = 2;
                    heuristicType2 = 3;
                    AIvsAI.match(2, 3);
                } else if (i == 2) {
                    heuristicType1 = 2;
                    heuristicType2 = 4;
                    AIvsAI.match(2, 4);
                } else if (i == 3) {
                    heuristicType1 = 2;
                    heuristicType2 = 5;
                    AIvsAI.match(2, 5);
                    //System.out.println(heuristicType1 + "+" + heuristicType2);
                } else if (i == 4) {
                    heuristicType1 = 2;
                    heuristicType2 = 6;
                    AIvsAI.match(2,6);
                } /*else {
                    AIvsAI.match(i - 3, i - 2); //2 vs 3
                    heuristicType1 = 2;
                    heuristicType2 = 3;
                }*/

            }
            for (i = 0; i < 5; i++) {

                if (i == 0) {
                    heuristicType1 = 1;
                    heuristicType2 = 2;
                    AIvsAI.match(1, 2);
                } else if (i == 1) {
                    heuristicType1 = 1;
                    heuristicType2 = 3;
                    AIvsAI.match(1, 3);
                } else if (i == 2) {
                    heuristicType1 = 1;
                    heuristicType2 = 4;
                    AIvsAI.match(1, 4);
                } else if (i == 3) {
                    heuristicType1 = 1;
                    heuristicType2 = 5;
                    AIvsAI.match(1, 5);
                    //System.out.println(heuristicType1 + "+" + heuristicType2);
                } else if (i == 4) {
                    heuristicType1 = 1;
                    heuristicType2 = 6;
                    AIvsAI.match(1,6);
                } /*else {
                    AIvsAI.match(i - 3, i - 2); //2 vs 3
                    heuristicType1 = 2;
                    heuristicType2 = 3;
                }*/

            }

            for (i = 0; i < 5; i++) {

                if (i == 0) {
                    heuristicType1 = 3;
                    heuristicType2 = 1;
                    AIvsAI.match(3, 1);
                } else if (i == 1) {
                    heuristicType1 = 3;
                    heuristicType2 = 2;
                    AIvsAI.match(3, 2);
                } else if (i == 2) {
                    heuristicType1 = 3;
                    heuristicType2 = 4;
                    AIvsAI.match(3, 4);
                } else if (i == 3) {
                    heuristicType1 = 3;
                    heuristicType2 = 5;
                    AIvsAI.match(3, 5);
                    //System.out.println(heuristicType1 + "+" + heuristicType2);
                } else if (i == 4) {
                    heuristicType1 = 3;
                    heuristicType2 = 6;
                    AIvsAI.match(3,6);
                }
            }

            for (i = 0; i < 5; i++) {

                if (i == 0) {
                    heuristicType1 = 4;
                    heuristicType2 = 1;
                    AIvsAI.match(4, 1);
                } else if (i == 1) {
                    heuristicType1 = 4;
                    heuristicType2 = 2;
                    AIvsAI.match(4, 2);
                } else if (i == 2) {
                    heuristicType1 = 4;
                    heuristicType2 = 3;
                    AIvsAI.match(4, 3);
                } else if (i == 3) {
                    heuristicType1 = 4;
                    heuristicType2 = 5;
                    AIvsAI.match(4, 5);
                    //System.out.println(heuristicType1 + "+" + heuristicType2);
                } else if (i == 4) {
                    heuristicType1 = 4;
                    heuristicType2 = 6;
                    AIvsAI.match(4,6);
                }
            }

            for (i = 0; i < 5; i++) {

                if (i == 0) {
                    heuristicType1 = 5;
                    heuristicType2 = 1;
                    AIvsAI.match(5, 1);
                } else if (i == 1) {
                    heuristicType1 = 5;
                    heuristicType2 = 2;
                    AIvsAI.match(5, 2);
                } else if (i == 2) {
                    heuristicType1 = 5;
                    heuristicType2 = 3;
                    AIvsAI.match(5, 3);
                } else if (i == 3) {
                    heuristicType1 = 5;
                    heuristicType2 = 4;
                    AIvsAI.match(5, 4);
                    //System.out.println(heuristicType1 + "+" + heuristicType2);
                } else if (i == 4) {
                    heuristicType1 = 5;
                    heuristicType2 = 6;
                    AIvsAI.match(5,6);
                }
            }
            for (i = 0; i < 5; i++) {

                if (i == 0) {
                    heuristicType1 = 6;
                    heuristicType2 = 1;
                    AIvsAI.match(6, 1);
                } else if (i == 1) {
                    heuristicType1 = 6;
                    heuristicType2 = 2;
                    AIvsAI.match(6, 2);
                } else if (i == 2) {
                    heuristicType1 = 6;
                    heuristicType2 = 3;
                    AIvsAI.match(6, 3);
                } else if (i == 3) {
                    heuristicType1 = 6;
                    heuristicType2 = 4;
                    AIvsAI.match(6, 4);
                    //System.out.println(heuristicType1 + "+" + heuristicType2);
                } else if (i == 4) {
                    heuristicType1 = 6;
                    heuristicType2 = 5;
                    AIvsAI.match(6,5);
                }
            }


            int j;
            System.out.println("First move by player, won by player");
            for (i = 1; i < 7; i++) {

                for (j = 1; j < 7; j++)
                    System.out.print(stat[i][j] + " ");
                System.out.println();
            }

            System.out.println("Second move by player, won by player");
            for (i = 1; i < 7; i++) {

                for (j = 1; j < 7; j++)
                    System.out.print(reverseStat[j][i] + " ");
                System.out.println();
            }
        }

    }
}
