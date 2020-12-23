package tictactoe;

import java.util.Random;
import java.util.Scanner;

public class Main {
    static int xs = 0;
    static int os = 0;
    static int es = 0;

    public static void main(String[] args) {
        // write your code here
        menu();
    }

    // MENU METHOD
        // displays menu, takes input, calls right method according to input
    public static void menu() {
        Scanner scan = new Scanner(System.in);
        System.out.println("\nTic Tac Toe");
        System.out.println("Please select an option below.");
        System.out.println("\n1. Play CPU");
        System.out.println("2. Play a friend");
        int option = scan.nextInt();
        switch(option) {
            case 1:
                // cpu game
                runCPU();
                break;
            case 2:
                // friend game
                runFriend();
                break;
            default:
                // prompt for another input
                System.out.println("Please enter one of the options available.");
                menu();
                break;
        }
    }

    // USER INPUT METHODS
    // method gets user input and executes move
        // also updates grid and checks for win
    public static String promptInput(Scanner scan, String[][] grid, String l) {
        int[] coords = checkCoords(scan, grid, l);
        int x = coords[0];
        int y = coords[1];
        makeMove(grid, x, y, l);
        displayGrid(grid);
        String winner = getWinner(grid);
        System.out.println(winner);
        return winner;
    }
    // method gets user coords
    // requests user input until it is within bounds
    public static int[] checkCoords(Scanner scan, String[][] grid, String l) {
        System.out.println("\nEnter the coordinates for " + l + ":");
        String nums = scan.nextLine();
        int x, y;
        int[] coords = new int[2];
        if (nums.length() != 3) {
            System.out.println("Enter two numbers");
            return checkCoords(scan,grid, l);
        } else {
            String cx = Character.toString(nums.charAt(0));
            String cy = Character.toString(nums.charAt(2));
            x = Integer.parseInt(cx);
            y = Integer.parseInt(cy);
            if (x > 3 || x < 1 || y > 3 || y < 1) {
                System.out.println("Coordinates should be from 1 to 3!");
                return checkCoords(scan,grid, l);
            }
            coords[0] = x - 1;
            coords[1] = y - 1;
            if(!(grid[coords[0]][coords[1]].contains("_"))) {
                System.out.println("This cell is occupied! Choose another one!");
                return checkCoords(scan,grid, l);
            }
        }
        return coords;
    }

    //CPU METHODS
    // method gets cpu input and executes move
    // also updates grid and checks for win
    public static String promptCPUInput(String[][] grid, String l) {
        int[] coords = cpuCoords(grid);
        int x = coords[0];
        int y = coords[1];
        makeMove(grid, x, y, l);
        displayGrid(grid);
        String winner = getWinner(grid);
        System.out.println(winner);
        return winner;
    }
    // method gets cpu coords
        // requests random input until it is within bounds
    public static int[] cpuCoords(String[][] grid) {
        System.out.println("\nEnter the coordinates for O:");
        int x = randomCoord();
        int y = randomCoord();
        System.out.println(x + " " + y);
        int[] coords = new int[2];
        if (x > 3 || x < 1 || y > 3 || y < 1) {
            return cpuCoords(grid);
        }
        coords[0] = x - 1;
        coords[1] = y - 1;
        if (!(grid[coords[0]][coords[1]].contains("_"))) {
            return cpuCoords(grid);
        }
        return coords;
    }
    // method generates random coords
    public static int randomCoord() {
        int min = 1;
        int max = 4;
        Random r = new Random();
        return r.nextInt(max - min) + min;
    }

    // GAME METHODS
    // method to run game against CPU
    public static void runCPU() {
        String[][] grid = createEmptyGrid();
        String winner = getWinner(grid);
        Scanner scan = new Scanner(System.in);

        while (winner == " ") {
            // prompt for user input
            winner = promptInput(scan, grid, "X");
            if(winner == "X wins" || winner == "O wins" || winner == "Draw" || winner == "Impossible") {
                System.out.println(exitMessage());
                break;
            }
            // prompt cpu input
            winner = promptCPUInput(grid, "O");
            if(winner == "X wins" || winner == "O wins" || winner == "Draw" || winner == "Impossible") {
                System.out.println(exitMessage());
                break;
            }
        }
    }
    // method to run game against Friend
    public static void runFriend() {
        String[][] grid = createEmptyGrid();
        String winner = getWinner(grid);
        Scanner scan = new Scanner(System.in);

        while (winner == " ") {
            // prompt input for X
            winner = promptInput(scan, grid, "X");
            if (winner == "X wins" || winner == "O wins" || winner == "Draw" || winner == "Impossible") {
                break;
            }
            // prompt input for O
            winner = promptInput(scan, grid, "O");
            if (winner == "X wins" || winner == "O wins" || winner == "Draw" || winner == "Impossible") {
                break;
            }
        }
    }
    // method makes move on grid
    public static void makeMove(String[][] grid, int row, int col, String l) {
        grid[row][col] = l;
    }
    // method runs grid through various conditions to find a winner
    public static String getWinner(String[][] grid) {
        String result = " ";
        String x = "X";
        String o = "O";
        int xWin = 0;
        int oWin = 0;
        es = 0;
        //rows are checked
        for (int row = 0; row < grid.length; row++) {
            int xCount = 0;
            int oCount = 0;
            for(int col = 0; col < grid[row].length; col++) {
                    if (grid[row][col].contains(x)) {
                        xCount++;
                    } else if (grid[row][col].contains(o)) {
                        oCount++;
                    }else if (grid[row][col].contains("_")) {
                        es++;
                    }
            }
            if(xCount == 3) {
                result = "X wins";
                xWin++;
            }else if(oCount == 3) {
                result = "O wins";
                oWin++;
            }
        }
        //cols are checked
        for (int col = 0; col < grid[0].length; col++) {
            int xCount = 0;
            int oCount = 0;
            for(int row = 0; row < grid.length; row++) {
                if (grid[row][col].contains(x)) {
                    xCount++;
                } else if (grid[row][col].contains(o)) {
                    oCount++;
                } else if (grid[row][col].contains("_")) {
                    es++;
                }
            }
            if(xCount == 3) {
                result = "X wins";
                xWin++;
            }else if(oCount == 3) {
                result = "O wins";
                oWin++;
            }
        }
        //diags are checked
        String topL = grid[0][0],
                topR = grid[0][2],
                middle = grid[1][1],
                botL = grid[2][0],
                botR = grid[2][2];
        if(topL.contains(x) && middle.contains(x) && botR.contains(x)) {
            result = "X wins";
            xWin++;
        }else if(topL.contains(o) && middle.contains(o) && botR.contains(o)) {
            result = "O wins";
            oWin++;
        }
        if(topR.contains(x) && middle.contains(x) && botL.contains(x)) {
            result = "X wins";
            xWin++;
        }else if(topR.contains(o) && middle.contains(o) && botL.contains(o)) {
            result = "O wins";
            oWin++;
        }

        //draw, not finished, impossible
        if((xWin > 0 && oWin > 0) || (xs - os >= 2) || (os - xs) >= 2) {
            result = "Impossible";
            return result;
        } else if(xWin == 0 && oWin == 0 && es == 0) {
            result = "Draw";
            return result;
        }


        return result;
    }

    //GRID METHODS
    // method to display an empty grid
    public static String[][] createEmptyGrid() {
        String[][] grid = new String[3][3];
        char[] splitInput = new char[9];
        String empty = "_________";
        // empty grid
        organizeGrid(grid, splitInput, empty);
        displayGrid(grid);
        return grid;
    }
    // method sets up grid array with string input
    public static void organizeGrid(String[][] grid, char[] splitInput, String input) {
        for (int i = 0; i < input.length(); i++) {
            splitInput[i] = input.charAt(i);
        }
        int count= 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(count < 9) {
                    grid[i][j] = Character.toString(splitInput[count]);
                    count++;
                    if(grid[i][j].contains("X")) {
                        xs++;
                    }else if (grid[i][j].contains("O")) {
                        os++;
                    }
                }
            }
        }
    }
    // method displays grid as output
    public static void displayGrid(String[][] grid) {
        System.out.println("---------");
        for(int i = 0; i < grid.length; i++) {
            String line = "| ";
            for(int j = 0; j < grid[i].length; j++) {
                line += grid[i][j] + " ";
            }
            System.out.println(line + "|");
        }
        System.out.println("---------");
    }

    // exit message
    public static String exitMessage() {
        return "Thank you for playing my game!";
    }
}
