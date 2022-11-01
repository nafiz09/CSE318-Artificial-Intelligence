import java.util.Scanner;

public class AIvsPlayer {

    public static State makeState(int id, State currState, int num, int turn, int depth) {

        State newState = new State();
        newState.maxScore = currState.maxScore;
        newState.minScore = currState.minScore;
        newState.value = currState.value;
        newState.copy(currState.maxArr, "maxArr");
        newState.copy(currState.minArr, "minArr");
        int sum = 0;
        //For player's turn
        if((turn == Game.player && id == Game.game) || (Game.treeTurn == Game.player && id == Game.simulation)) {

            int count = newState.minArr[num];
            newState.minArr[num] = 0;
            int j = 1;
            while(sum != count) {

                //player's array
                while(sum < count && j + num < Game.binCount) {

                    newState.minArr[num + j] += 1;
                    sum++;

                    //if the last block ends up in player's array's empty bin
                    if(sum == count && newState.minArr[num + j] == 1 && newState.maxArr[num + j] != 0) {
                        newState.minScore += 1 + newState.maxArr[num + j];
                        Game.stonesCapPlayer += newState.maxArr[num + j];
                        newState.minArr[num + j] = 0;
                        newState.maxArr[num + j] = 0;

                    }

                    j++;

                }

                //player's store
                if(sum < count) {

                    newState.minScore++;
                    sum++;

                    //if the last block ends up in player's store
                    if(sum == count) {

                        if(turn == Game.player && id == Game.game) Game.flag = 1;
                        if(Game.treeTurn == Game.player && id == Game.simulation) {

                            Game.treeFlag = 1;
                            Game.addMovesPlayer += 1;

                        }
                    }

                }

                //computer's array
                int k = Game.binCount - 1;
                while(sum < count && k >= 0) {

                    newState.maxArr[k] += 1;
                    k--;
                    sum++;

                }
                num = 0;
                j = 0;

            }
        }

        //For computer's turn
        else if((turn == Game.computer && id == Game.game) || (Game.treeTurn == Game.computer && id == Game.simulation)) {

            int count = newState.maxArr[num];
            newState.maxArr[num] = 0;
            int j = 1;
            while(sum != count) {

                //computer's array
                while(sum < count && num - j >= 0) {

                    newState.maxArr[num - j] += 1;
                    sum++;

                    //if the last block ends up in computer's array's empty bin
                    if(sum == count && newState.maxArr[num - j] == 1 && newState.minArr[num - j] != 0) {

                        newState.maxScore += 1 + newState.minArr[num - j];
                        Game.stonesCapCom += newState.minArr[num - j];
                        newState.minArr[num - j] = 0;
                        newState.maxArr[num - j] = 0;

                    }
                    j++;

                }

                //computer's store
                if(sum < count) {

                    newState.maxScore++;
                    sum++;

                    //if the last block ends up in computer's store
                    if(sum == count) {

                        if(turn == Game.computer && id == Game.game) Game.flag = 1;
                        if(Game.treeTurn == Game.computer && id == Game.simulation) {

                            Game.treeFlag = 1;
                            Game.addMovesCom += 1;
                        }
                    }

                }

                //player's array
                int k = 0;
                while(sum < count && k < Game.binCount) {

                    newState.minArr[k] += 1;
                    k++;
                    sum++;

                }
                num = Game.binCount;
                j = 1;

            }
        }

        //System.out.println("Treeturn: " + treeTurn + ", depth: " + depth);
        if((Game.treeTurn == Game.player && id == 2 && depth == Game.maxDepth) || ((currState.checkZero(currState.maxArr) || currState.checkZero(currState.minArr)) && id == Game.simulation))  {
            {
                Game.setHeuristicType(Game.heuristicType, Game.stonesCapPlayer, Game.addMovesPlayer);
                newState.value = Game.heuristic.getValue(newState);
            }
            //  System.out.println("Heuristic value is: " + newState.value);
        }
        if((Game.treeTurn == Game.computer && id == 2 && depth == Game.maxDepth) || ((currState.checkZero(currState.maxArr) || currState.checkZero(currState.minArr)) && id == Game.simulation)) {
            {
                Game.setHeuristicType(Game.heuristicType, Game.stonesCapCom, Game.addMovesPlayer);
                newState.value = Game.heuristic.getValue(newState);
            }
            //System.out.println("Heuristic value is: " + newState.value);
        }
        //System.out.println("does it?");
        return newState;

    }

    public static void gamePlay(State currState) {

        while (!gameOver(currState)) {

            if(Game.turn == 0)
                System.out.println("Your turn");
            else if(Game.turn == 1)
                System.out.print("Computer's turn: ");

            int num = 0;
            //player's turn
            while(Game.turn == 0) {

                Scanner in = new Scanner(System.in);
                num = in.nextInt() - 1;

                //check if the move is valid
                if(num < 0 || num >= Game.binCount)
                    continue;
                else {

                    //if the move is given in a bin containing zero blocks
                    if (Game.turn == 0 && currState.minArr[num] == 0)
                        continue;
                    else if (Game.turn == 1 && currState.maxArr[num] == 0)
                        continue;
                }

                break;

            }

            //computer's turn
            if(Game.turn == 1) {

                //make a copy of the current State and use it
                State intelligentState = new State();
                intelligentState.maxScore = currState.maxScore;
                intelligentState.minScore = currState.minScore;
                intelligentState.value = currState.value;
                intelligentState.copy(currState.minArr, "minArr");
                intelligentState.copy(currState.maxArr, "maxArr");
                //Scanner in = new Scanner(System.in);
                //num = in.nextInt() - 1;
                Game.treeTurn = Game.computer;
                Game.miniMax(intelligentState);
                num = Game.bestState.move;
                System.out.println(num + 1);
            }


            Game.moves.add(num);
            State newState;
            newState = makeState(1, currState, num, Game.turn, 0);
            currState.bestChild = newState;
            newState.move = num;
            newState.printState();
            Game.gameState.add(newState);
            currState = newState;
            //System.out.println("value:" + newState.value);

            //System.out.println("Before Flag: " + flag);
            if(Game.flag == 0)
                Game.turn = (Game.turn + 1) % 2;
            Game.flag = 0;
            //System.out.println("After Flag: " + flag);
            //System.out.println("Turn: " + turn);
        }
    }

    public static boolean gameOver(State currState) {

        if (currState.checkZero(currState.maxArr) || currState.checkZero(currState.minArr)) {

            int count = currState.countBlocks(currState.maxArr);
            if (count != 0)
                currState.maxScore += count;

            count = currState.countBlocks(currState.minArr);
            if (count != 0)
                currState.minScore += count;

            System.out.println("Your final score: " + currState.minScore);
            System.out.println("Computer's final score: " + currState.maxScore);
            if (currState.maxScore < currState.minScore)
                System.out.println("Congratulations! You won");
            else if (currState.minScore < currState.maxScore)
                System.out.println("Sorry! You lost");
            else
                System.out.println("Game drawn");

            return true;

        }

        return false;
    }
}
