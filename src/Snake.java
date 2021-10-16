import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Snake {

    public boolean alive;
    public int length, kills;
    public ArrayList<String> snakeCoords;
    public int currDirection;
    public String snakeLine; //use this to debug

    public Snake(){
        this.alive = true;
        this.length = 0;
        this.kills = 0;
        this.snakeCoords = new ArrayList<String>();
    }
    
    public void update(String snakeLine){
        this.snakeLine = snakeLine;
        // clear previous information
        int headIndex = 3;
        snakeCoords.clear();
        this.alive = true;

        // update with latest information
        String[] snakeInfo = snakeLine.split(" ");
        if(snakeInfo[0].equals("dead")){
            this.alive = false;
            return;
        }
        if(snakeInfo[0].equals("invisible")){
            headIndex = 4;
        }
        this.length = Integer.parseInt(snakeInfo[1]);
        this.kills = Integer.parseInt(snakeInfo[2]);
        
        // add all snakes coords to arraylist
        for(int i = headIndex; i < snakeInfo.length; i++){
            snakeCoords.add(snakeInfo[i]);
        }
    }
    
    private Map<String, Integer> directions = new HashMap<String, Integer>() {{
        put("up", 0);
        put("down", 1);
        put("left", 2);
        put("right", 3);
        put("unknown", -1);
    }};

    public int calcCurrDirection(){
        // when enemy snake is invis
        if(snakeCoords.size() == 1){
            return directions.get("unknown");
        }

        // get x, y coords of head
        String[] sHead = snakeCoords.get(0).split(",");
        int[] head = {Integer.parseInt(sHead[0]), Integer.parseInt(sHead[1])};

        // get x, y coords of first bend
        String[] sBend = snakeCoords.get(1).split(",");
        int[] bend = {Integer.parseInt(sBend[0]), Integer.parseInt(sBend[1])};

        if(head[0] == bend[0] && head[1] < bend[1]){
            return directions.get("up");
        }else if(head[0] == bend[0] && head[1] > bend[1]){
            return directions.get("down");
        }else if(head[1] == bend[1] && head[0] < bend[0]){
            return directions.get("left");
        }else{
            return directions.get("right");
        }
    }

    public int[] getHeadPos(){
        String[] sHead = snakeCoords.get(0).split(",");
        int[] head = {Integer.parseInt(sHead[0]), Integer.parseInt(sHead[1])};
        return head;
    }
}
