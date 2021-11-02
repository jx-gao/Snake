public class Node implements Comparable<Node> {
    public int x, y;
    public double f, g, h;
    public boolean opened;
    public Node parentNode;
    
    public Node(int x, int y, Node parentNode){
        this.x = x; // x coord
        this.y = y; // y coord
        this.parentNode = parentNode;
    }

    public Node(int x, int y){ // constructor for start node
        this.x = x; // x coord
        this.y = y; // y coord
        this.g = 0.0;
    }

    /**
     * Calculate f cost of the node
     * @param parent g cost
     * @param goalX x coord of goal node
     * @param goalY y coord of goal node
     */
    public void calculateFcost(double pg, int goalX, int goalY){
        g = pg + 1;
        h = manhattan(x, y, goalX, goalY);
        f = g + h;
    }

    /**
     * Manhattan heuristic to calculate h cost
     */
    public double manhattan(int x, int y, int goalX, int goalY){
        h = Math.abs((x - goalX)) + Math.abs((y - goalY));
        return h;
    }

    /**
     * Compare method used by Priority Queue to order nodes by least f cost
     */
    @Override
	public int compareTo(Node o) {
		if (this.f > o.f){
			return 1;
        }else if (this.f < o.f){
			return -1;
        }
		return 0;
	}
}
