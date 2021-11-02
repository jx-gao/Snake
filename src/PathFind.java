import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class PathFind {
    int mySnakeNum;
    int[] apple1coords, apple2coords;
    int[][] board;
    Snake[] snakes;

    public PathFind(int[] apple1coords, int[] apple2coords, Snake[] snakes, int mySnakeNum, int[][] board){
        this.apple1coords = apple1coords;
        this.apple2coords = apple2coords;
        this.snakes = snakes;
        this.mySnakeNum = mySnakeNum;
        this.board = board;
    }

    public int calculateMove(){
        Snake mySnake = snakes[mySnakeNum];
        int move;

        if(isPowerAppleOnBoard()){ // go for power apple if it is on the board
            move = aStar(mySnake, apple1coords);
        }else{
            move = aStar(mySnake, apple2coords);
        }

        if(move == -1){ // follow tail if there's no path to apple's
            board[mySnake.getTailPos()[1]][mySnake.getTailPos()[0]] = 0; // mark tail as walkable on grid
            move = aStar(mySnake, mySnake.getTailPos());
        }

        if(move == -1){ // try survive if there's no path to tail or apple
            
        }
        return move;
    }

    /**
     * A-star algorithm to find best path to apple
     * @return best move, if there is no possible path the method return -1
     */
    private int aStar(Snake snake, int[] goal){
        // get coords of start and goal
        int startX = snake.getHeadPos()[0], startY = snake.getHeadPos()[1];
        int goalX = goal[0], goalY = goal[1];

        Node headNode = new Node(startX, startY);
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(headNode);

        while(!pq.isEmpty()){

            Node currN = pq.poll(); // get node with lowest f cost
            int x = currN.x, y = currN.y;

            if(isClosed(x, y)){ // if node has been explored go to start of loop
                continue;
            }
            setClosed(x, y);

            if(goalFound(x, y, goalX, goalY)){
                while(currN.parentNode.x != startX || currN.parentNode.y != startY){ // backtrack
                    currN = currN.parentNode;
                    // board[currN.y][currN.x] = 3;
                }
                return getMove(currN.x, currN.y);
            }

            // ↑
            if(isTraversable(x, y - 1)){
                Node newN = new Node(x, y - 1, currN);
                newN.calculateFcost(currN.g, goalX, goalY);
                pq.add(newN);
            }
            // ↓
            if(isTraversable(x, y + 1)){
                Node newN = new Node(x, y + 1, currN);
                newN.calculateFcost(currN.g, goalX, goalY);
                pq.add(newN);
            }
            // →
            if(isTraversable(x + 1, y)){
                Node newN = new Node(x + 1, y, currN);
                newN.calculateFcost(currN.g, goalX, goalY);
                pq.add(newN);
            }
            // ←
            if(isTraversable(x - 1, y)){
                Node newN = new Node(x - 1, y, currN);
                newN.calculateFcost(currN.g, goalX, goalY);
                pq.add(newN);
            }
        }
        return -1; // return -1 if no path is possible
    }

    // ==== helper methods ====

    private int getMove(int x, int y){
        int headX = snakes[mySnakeNum].getHeadPos()[0], headY = snakes[mySnakeNum].getHeadPos()[1];
        if(headX < x && headY == y){
            return directions.get("right");
        }else if(headX > x  && headY == y){
            return directions.get("left");
        }else if(headY < y  && headX == x){
            return directions.get("down");
        }else{
            return directions.get("up");
        }
    }

    private boolean goalFound(int x, int y, int goalX, int goalY){
        return x == goalX && y == goalY;
    }

    private boolean isTraversable(int x, int y){
        if(!(x >= 0 && x < board.length)){
            return false;
        }
        if(!(y >= 0 && y < board[0].length)){
            return false;
        }
        if(board[y][x] == 1){
            return false;
        }
        return true;
    }

    private boolean isClosed(int x, int y){
        return board[y][x] == 2;
    }

    private void setClosed(int x, int y){
        board[y][x] = 2;
    }

    private  Map<String, Integer> directions = new HashMap<String, Integer>() {{
        put("up", 0);
        put("down", 1);
        put("left", 2);
        put("right", 3);
        put("relative left", 4);
        put("straight", 5);
        put("relative right", 6);
        put("unknown", -1);
    }};

    private boolean isPowerAppleOnBoard(){
        return apple1coords[0] != -1 || apple1coords[1] != -1;
    }
}
