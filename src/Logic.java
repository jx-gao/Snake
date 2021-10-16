import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.text.AbstractDocument.LeafElement;

public class Logic {

    static int mySnakeNum;
    static int[] apple1coords, apple2coords;
    static Snake[] snakes;

    public static int calculateMove(int[] apple1coords, int[] apple2coords, Snake[] snakes, int mySnakeNum){
        Logic.apple1coords = apple1coords;
        Logic.apple2coords = apple2coords;
        Logic.snakes = snakes;
        Logic.mySnakeNum = mySnakeNum;
        return simpleMove();
    }
    
    private static Map<String, Integer> directions = new HashMap<String, Integer>() {{
        put("up", 0);
        put("down", 1);
        put("left", 2);
        put("right", 3);
        put("relative left", 4);
        put("straight", 5);
        put("relative right", 6);
        put("unknown", -1);
    }};

    private static int simpleMove(){
        int currDirection = snakes[mySnakeNum].calcCurrDirection();
        int[] head = snakes[mySnakeNum].getHeadPos();
        int move;

        if(head[0] < apple2coords[0] && head[1] < apple2coords[1]){ // apple right & below
            move = directions.get("right");
        }else if(head[0] < apple2coords[0] && head[1] > apple2coords[1]){ // apple right & above
            move = directions.get("right");
        }else if(head[0] > apple2coords[0] && head[1] < apple2coords[1]){ // apple left & below
            move = directions.get("left");
        }else if(head[0] > apple2coords[0] && head[1] > apple2coords[1]){ // apple left & above
            move = directions.get("left");
        }else if(head[0] == apple2coords[0] && head[1] < apple2coords[1]){ // apple below
            move = directions.get("down");
        }else if(head[0] == apple2coords[0] && head[1] > apple2coords[1]){ // apple above
            move = directions.get("up");
        }else if(head[0] < apple2coords[0] && head[1] == apple2coords[1]){ // apple right
            move = directions.get("right");
        }else{ // apple left
            move = directions.get("left");
        }

        if(move == 0 && currDirection == 1){
            move = directions.get("right");
        }else if(move == 1 && currDirection == 0){
            move = directions.get("left");
        }else if(move == 2 && currDirection == 3){
            move = directions.get("up");
        }else if(move == 3 && currDirection == 2){
            move = directions.get("down");
        }

        return move;
    }
}
