import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import za.ac.wits.snake.DevelopmentAgent;

public class MyAgent extends DevelopmentAgent {

    public static void main(String args[]) {
        MyAgent agent = new MyAgent();
        MyAgent.start(agent, args);
    }

    
    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String initString = br.readLine();
            String[] temp = initString.split(" ");
            int nSnakes = Integer.parseInt(temp[0]);
            
            // Board Size
            int[][] board;
            int height = Integer.parseInt(temp[1]), width = Integer.parseInt(temp[2]);
            
            // Declare and Initialize snakes
            Snake[] snakes = new Snake[4];
            for(int i = 0; i < 4; i++){
                snakes[i] = new Snake();
            }
            
            // Run till Game is Over
            while (true) {
                String line = br.readLine();
                if (line.contains("Game Over")) {
                    break;
                }
                // Refresh Board Each Tick
                board = new int[height][width];
                
                String apple1 = line; // power apple
                String apple2 = br.readLine(); // normal apple
                int[] apple1coords= {Integer.parseInt(apple1.split(" ")[0]), Integer.parseInt(apple1.split(" ")[1])};
                int[] apple2coords= {Integer.parseInt(apple2.split(" ")[0]), Integer.parseInt(apple2.split(" ")[1])};
                
                int mySnakeNum = Integer.parseInt(br.readLine());
                
                // Read, Update Snakes and Update Board Info
                for (int i = 0; i < nSnakes; i++) {
                    String snakeLine = br.readLine();
                    board = snakes[i].update(snakeLine, board); // Record Snake Info and Update Board
                    if(i != mySnakeNum){ // mark each snake head's neighbours unwalkable to avoid head collisions
                        board = snakes[i].markHeadSurr(board);
                    }
                }

                // Calculate Best Move
                PathFind findPath = new PathFind(apple1coords, apple2coords, snakes, mySnakeNum, board);
                int move = findPath.calculateMove();
                System.out.println(move);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}