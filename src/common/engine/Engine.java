package common.engine;

public class Engine {
    public static enum ACTIONS {
        ROCK ("ROCK"),
        PAPER ("PAPER"),
        SCISSORS ("SCISSORS"),
        DEFAULT ("DEFAULT");

        public final String name;

        ACTIONS(String name) {this.name = name;}
    }

    public static String computeWinner(String player1, String player2, ACTIONS action1, ACTIONS action2){

        if(action1 == ACTIONS.ROCK && action2 == ACTIONS.PAPER) {return player2;}

        if(action1 == ACTIONS.ROCK && action2 == ACTIONS.SCISSORS) {return player1;}

        if(action1 == ACTIONS.PAPER && action2 == ACTIONS.ROCK) {return player1;}

        if(action1 == ACTIONS.PAPER && action2 == ACTIONS.SCISSORS) {return player2;}

        if(action1 == ACTIONS.SCISSORS && action2 == ACTIONS.ROCK) { return player2;}

        if(action1 == ACTIONS.SCISSORS && action2 == ACTIONS.PAPER) {return player1;}

        return "draw";
    }

    public static ACTIONS computeWinner(ACTIONS action1, ACTIONS action2){

        if(action1 == ACTIONS.ROCK && action2 == ACTIONS.PAPER) {return action2;}

        if(action1 == ACTIONS.ROCK && action2 == ACTIONS.SCISSORS) {return action1;}

        if(action1 == ACTIONS.PAPER && action2 == ACTIONS.ROCK) {return action1;}

        if(action1 == ACTIONS.PAPER && action2 == ACTIONS.SCISSORS) {return action2;}

        if(action1 == ACTIONS.SCISSORS && action2 == ACTIONS.ROCK) { return action2;}

        if(action1 == ACTIONS.SCISSORS && action2 == ACTIONS.PAPER) {return action1;}

        return action2; //in case of draw
    }

    public static void main(String[] args){
        String player1 = "Tommy";
        String player2 = "Jordan";

        ACTIONS[] player1Actions = {ACTIONS.ROCK, ACTIONS.PAPER, ACTIONS.SCISSORS};
        ACTIONS[] player2Actions = {ACTIONS.ROCK, ACTIONS.PAPER, ACTIONS.SCISSORS};

        for (ACTIONS player1Action : player1Actions) {
            for (ACTIONS player2Action : player2Actions) {
                System.out.println(
                        player1 + " plays " + player1Action.name +
                                ", " + player2 + " plays " + player2Action.name +
                                ": " + computeWinner(player1, player2, player1Action, player2Action)
                );
            }
        }
    }
}
