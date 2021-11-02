import java.time.Duration;
import java.time.Instant;
import java.util.PriorityQueue;

public class Test {
    static String[] snakeCoords;
    static int[][] board = new int[50][50];
    static int[] apple2coords = {25, 49};

    public static void main(String[] args){
        update("42,22 18,22");
        Test.board = drawSnake(board);
        board[49][48] = 1;
        board[48][48] = 1;
        board[48][49] = 1;
        Instant before = Instant.now();
        System.out.println(aStar());
        Instant after = Instant.now();
        board[apple2coords[1]][apple2coords[0]] = 5;
        printBoard(board);
        System.out.println(Duration.between(before, after).toMillis());
    }
    
    private static void update(String snakeLine){
        snakeCoords = snakeLine.split(" ");
        
    }

    private static void printBoard(int[][] board){
        for(int i = 0; i < 50; i++){
            for(int j = 0; j < 50; j++){
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    private static int[][] drawSnake(int[][] board){
        // loop through each bend
        for(int i = 0; i < snakeCoords.length - 1; i++){
            board = drawLine(board, snakeCoords[i], snakeCoords[i + 1], 1);
        }
        return board;
    }
    
    private static int[][] drawLine(int[][] board, String s1, String s2, int num){
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

    private static int aStar(){
        int mySnakeX = 6, mySnakeY = 9;
        
        Node headNode = new Node(mySnakeX, mySnakeY);
        
        PriorityQueue<Node> pq = new PriorityQueue<>();

        pq.add(headNode);

        while(!pq.isEmpty()){
            // get node with lowest f value
            Node currN = pq.poll();
            int x = currN.x, y = currN.y;
            int goalX = apple2coords[0], goalY = apple2coords[1];

            if(isClosed(x, y)){ // if node has been explored go to start of loop
                continue;
            }
            setClosed(x, y);

            if(x == goalX && y == goalY){
                while(currN.parentNode.x != mySnakeX || currN.parentNode.y != mySnakeY){
                    currN = currN.parentNode;
                    board[currN.y][currN.x] = 3;
                }
                System.out.println(currN.x + " " + currN.y);
                return getMove(currN.x, currN.y);
            }

            // ↑
            if(isTraversable(x, y - 1)){
                Node newN = new Node(x, y - 1, currN);
                newN = calcHeuristics(currN, newN, x, y - 1);
                pq.add(newN);
            }
            // ↓
            if(isTraversable(x, y + 1)){
                Node newN = new Node(x, y + 1, currN);
                newN = calcHeuristics(currN, newN, x, y + 1);
                pq.add(newN);
            }
            // →
            if(isTraversable(x + 1, y)){
                Node newN = new Node(x + 1, y, currN);
                newN = calcHeuristics(currN, newN, x + 1, y);
                pq.add(newN);
            }
            // ←
            if(isTraversable(x - 1, y)){
                Node newN = new Node(x - 1, y, currN);
                newN = calcHeuristics(currN, newN, x - 1, y);
                pq.add(newN);
            }
        }
        return 6;
    }

    // ==== helper methods ====

    private static int getMove(int x, int y){
        int headX = 6, headY = 9;
        if(headX < x && headY == y){
            return 3;
        }else if(headX > x  && headY == y){
            return 2;
        }else if(headY < y  && headX == x){
            return 1;
        }else{
            return 0;
        }
    }

    private static boolean isTraversable(int x, int y){
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

    private static boolean isClosed(int x, int y){
        return board[y][x] == 2;
    }

    private static void setClosed(int x, int y){
        board[y][x] = 2;
    }

    private static Node calcHeuristics(Node cn, Node n, int x, int y){
        n.g = cn.g + 1;
        n.h = n.manhattan(x, y, apple2coords[0], apple2coords[1]);
        n.f = n.g + n.h;
        return n;
    }
}
