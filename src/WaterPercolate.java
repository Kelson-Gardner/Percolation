/**
 * @author Kelson Gardner
 */
public class WaterPercolate {

    public int[][] grid;
    final int[] TOP = {0};
    final int[] BOTTOM = {1000};

    /**
     * Runs the percolate method 50 times to get an average of
     * how many blocks need to be open for a full
     * percolation to happen.
     * @param args
     */
    public static void main(String[] args) {
        WaterPercolate thing = new WaterPercolate();

        boolean percolated = false;
        int openBlocks = 0;
        int averageCount = 0;
        for(int i = 0; i < 50; i++) {
            thing.initializeArray();
            percolated = false;
            openBlocks = 0;
            thing.resetTOPandBOTTOM();
            while (!percolated) {
                thing.generateArray(1);
                openBlocks++;
                thing.percolate();
                if (i != 49 && thing.isPercolated()) {
                    averageCount += openBlocks;
                    percolated = true;
                }
                if (i == 49 && (openBlocks % 50 == 0 || thing.isPercolated())) {
                    thing.printGrid();
                    System.out.println(openBlocks + " open blocks");
                    if (thing.isPercolated()) {
                        float average = (float) averageCount / 50;
                        System.out.println("For 50 tests Average: " + average + " open blocks");
                        percolated = true;
                    }
                }

            }
        }

    }


    String BLUE = "\u001B[44m   \u001B[0m";
    String WHITE = "\u001B[47m   \u001B[0m";
    String BLACK = "\u001B[40m   \u001B[0m";
    String RED = "\u001B[41m   \u001B[0m";
    String[] sym = {BLACK, WHITE, BLUE};
    final int SIZE = 20;

    public void resetTOPandBOTTOM(){
        TOP[0] = 0;
        BOTTOM[0] = 1000;
    }

    /*
        Prints out the grid of blocks with black blocks being closed,
        blue blocks being full or water, and gray/white blocks being
        empty open blocks.
     */
    public void printGrid() {

        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if (grid[x][y] == -1) {
                    System.out.print(BLACK);
                } else if (x == 0 || find(grid[x][y]) >=0 && find(grid[x][y]) <= 20) {
                    System.out.print(BLUE);
                } else {
                    System.out.print(WHITE);
                }
            }
            System.out.println();
        }
    }

    /**
     * Checks to see if the water has percolated to the bottom or not.
     * @return whether the water has percolated to the bottom or not.
     */
    public boolean isPercolated(){
        return find(TOP[0]) == find(BOTTOM[0]);
}

    /**
     * Unionizes all of the open blocks that are alligned with
     * open blocks that have water in them.
     */
    public void percolate(){
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                    if (grid[i][j] != -1) {
                        if (i != 0 && grid[i - 1][j] != -1) {
                            union(grid[i - 1][j], grid[i][j]);
                        }
                        if (i != 19 && (grid[i + 1][j] != -1)) {
                            union(grid[i + 1][j], grid[i][j]);
                        }
                        if (j != 19 && grid[i][j + 1] != -1) {
                            union(grid[i][j + 1], grid[i][j]);
                        }
                        if (j != 0 && grid[i][j - 1] != -1) {
                            union(grid[i][j - 1], grid[i][j]);
                        }
                        if(i == 0){
                            union(grid[i][j], TOP[0]);
                        }
                        if(i == 19){
                            union(grid[i][j], BOTTOM[0]);
                        }
                    }
                }
            }
        }

    /**
     * Initializes an array of 'nodes' with all of the roots
     * pointing to -1, meaning they are all alone.
     */
    public void initializeArray(){
        grid = new int[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                grid[i][j] = -1;
            }
        }
    }

    /**
     * Opens blocks in the grid at random.
     * @param randomOpenBlocks the amount of blocks we want to be open in
     *                         the grid.
     */

    public void generateArray(int randomOpenBlocks){
        int count = 0;
        while(count < randomOpenBlocks){
            int random = (int) (Math.random() * 20);
            int random2 = (int) (Math.random() * 20);
            if(grid[random][random2] == -1) {
                grid[random][random2] = (random * 20) + random2 + 1;
                count++;
            }
        }
    }

    /**
     * Does a find on both of the node and connects the node with the bigger root
     * to the node with the smaller root.
     *
     * @param a an id of a node you want to union
     * @param b an id of a node you wnat to union
     */
    public void union(int a, int b){

        int aRoot = find(a);
        int bRoot = find(b);

        if(aRoot == bRoot){
            return;
        }
        if(bRoot == BOTTOM[0]){
            if(aRoot == TOP[0]){
            BOTTOM[0] = aRoot;
            return;}
            else
            {
                return;
            }
        }
        if(aRoot < bRoot){
            int x = (b - 1) /20;
            int y = (b - 1) %20;
            grid[x][y] = aRoot;
        }else{
            int x =(a - 1)/20;
            int y = (a - 1)%20;

            grid[x][y] = bRoot;

        }
    }


    /**
     * Finds what the nodes root is and directly connects it to it.
     *
     *
     * @param a node to find
     * @return
     */
    public int find(int a){

        int min = 999;

        while(true) {
            if(a == TOP[0]) {
                return TOP[0];
            }
            if(a == BOTTOM[0]){
                return BOTTOM[0];
            }

            int x = (a - 1) / 20;
            int y = (a - 1) % 20;
            if(grid[x][y] == min){
                return min;
            }
            if(grid[x][y] < min){
                min = grid[x][y];
                a = min;
            }
        }
    }

}