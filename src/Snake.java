import java.util.ArrayList;
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
    
    /**
    Update snake information and return updated board with snake drawn on it
    */
    public int[][] update(String snakeLine, int[][] board){

        this.snakeLine = snakeLine;

        // clear previous information
        int headIndex = 3;
        snakeCoords.clear();
        this.alive = true;

        // update with latest information
        String[] snakeInfo = snakeLine.split(" ");
        if(snakeInfo[0].equals("dead")){
            this.alive = false;
            return board;
        }
        if(snakeInfo[0].equals("invisible")){
            headIndex = 5;
        }
        this.length = Integer.parseInt(snakeInfo[1]);
        this.kills = Integer.parseInt(snakeInfo[2]);
        
        // add all snakes coords to arraylist
        for(int i = headIndex; i < snakeInfo.length; i++){
            snakeCoords.add(snakeInfo[i]);
        }

        // update board with snake
        board = drawSnake(board);
        return board;
    }
    
    private int[][] drawSnake(int[][] board){
        // loop through each bend
        for(int i = 0; i < snakeCoords.size() - 1; i++){
            board = drawLine(board, snakeCoords.get(i), snakeCoords.get(i + 1), 1);
        }
        return board;
    }
    
    private int[][] drawLine(int[][] board, String s1, String s2, int num){
        String[] p1 = s1.split(",");
        String[] p2 = s2.split(",");
        
        int x1 = Integer.parseInt(p1[1]);
        int x2 = Integer.parseInt(p2[1]);
        
        int y1 = Integer.parseInt(p1[0]);
        int y2 = Integer.parseInt(p2[0]);
        
        if(x1 > x2){ x2 = swap(x1, x1 = x2);}
        if(y1 > y2){ y2 = swap(y1, y1 = y2);}
        
        for(int row = x1; row <= x2; row++){
            board[row][y1] = num;
        }
        
        for(int col = y1; col <= y2; col++){
            board[x1][col] = num;
        }
        
        return board;
    }
    
    private static int swap(int a, int b) {  // usage: y = swap(x, x=y);
        return a;
    }

    public int[] getHeadPos(){
        String[] sHead = snakeCoords.get(0).split(",");
        int[] head = {Integer.parseInt(sHead[0]), Integer.parseInt(sHead[1])};
        return head;
    }

    public int[] getTailPos(){
        String[] sTail = snakeCoords.get(snakeCoords.size() - 1).split(",");
        int[] tail = {Integer.parseInt(sTail[0]), Integer.parseInt(sTail[1])};
        return tail;
    }    
}
