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

            // declare and initialize snakes
            Snake[] snakes = new Snake[4];
            for(int i = 0; i < 4; i++){
                snakes[i] = new Snake();
            }

            // run logic till game is over
            while (true) {
                String line = br.readLine();
                if (line.contains("Game Over")) {
                    break;
                }
                
                String apple1 = line;
                String apple2 = br.readLine();
                //do stuff with apples
                int[] apple1coords= {Integer.parseInt(apple1.split(" ")[0]), Integer.parseInt(apple1.split(" ")[1])};
                int[] apple2coords= {Integer.parseInt(apple2.split(" ")[0]), Integer.parseInt(apple2.split(" ")[1])};
                
                int mySnakeNum = Integer.parseInt(br.readLine());
                
                // read and update snake info
                for (int i = 0; i < nSnakes; i++) {
                    String snakeLine = br.readLine();
                    snakes[i].update(snakeLine);
                }
                //finished reading, calculate move:
                int move = Logic.calculateMove(apple1coords, apple2coords, snakes, mySnakeNum);
                System.out.println("log" + move);
                System.out.println(move);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void checkSurrround(){

    }

}