import java.util.Scanner;

/**
 * Created by User on 10/23/2016.
 */
public class CheckersMain {
    //****************GAME VARS****************
    private static final int BOARDSIZE = 8;
    //****************OTHER SHIZNIT************
    private static final String CHARLIST = "ABCDEFGH";
    //*****************************************
    private static Piece[][] BOARD = new Piece[BOARDSIZE][BOARDSIZE];

    public static void main(String[] args) {
        Piece[][] gameBoard = BOARD;
        char turn = '0';
        setBoard(gameBoard);
        printBoard(gameBoard);
        //while(game(gameBoard)){
        System.out.println(turn + "to move");
        while(!move(gameBoard));
        // }
        printBoard(gameBoard);
    }

    public static void movePiece(Piece[][] board, int X, int Y, int newX, int newY) {
        board[newY][newX] = board[Y][X];
        board[Y][X] = null;
    }

    public static boolean move(Piece[][] board){
        //*************PLAYER INPUT***************
        Scanner in = new Scanner(System.in);
        System.out.print("Which piece do you want to move: ");
        String pieceCoords = in.nextLine();
        System.out.print("Where do you want to move it: ");
        String newCoords = in.nextLine();
        pieceCoords.replaceAll("\\s+", "");
        newCoords.replaceAll("\\s+", ""); // (.replaceAll("\\s+","");) REMOVES WHITESPACE
        int X = CHARLIST.indexOf(pieceCoords.toUpperCase().charAt(0));
        int Y = Integer.parseInt(pieceCoords.substring(1, 2)) - 1;
        int newX = CHARLIST.indexOf(newCoords.toUpperCase().charAt(0));
        int newY = Integer.parseInt(newCoords.substring(1, 2)) - 1;


        //**************MOVEMENT RULES*************
        if (newX > 7 || newX < 0 || newY > 7 || newY < 0) {
            System.err.println("Please enter valid input");
            return false;
        } else {
            if (board[Y][X].isKing()) {   //TODO add Jumped piece deletion
                if ((Math.abs(X - newX) == 1 && Math.abs(Y - newY) == 1) || (Math.abs(X - newX) == 2 && Math.abs(Y - newY) == 2 && board[Y][X].getToken() != board[(Y + newY) / 2][(X + newX) / 2].getToken())) { //if(only moving 1 square or jumping a piece)
                    movePiece(board, X, Y, newX, newY);
                    return true;
                } else {
                    System.err.println("Please enter valid input");
                    return false;
                }
            } else {
                if ((Math.abs(Y - newY) == 1 && X - newX == board[Y][X].getDirection()) || (Math.abs(Y - newY) == 2 && X - newX == 2 * board[Y][X].getDirection() && board[Y][X].getToken() != board[(Y + newY) / 2][(X + newX) / 2].getToken())) {
                    movePiece(board, X, Y, newX, newY);
                    return true;
                } else {
                    System.err.println("Please enter valid input");
                    return false;
                }

            }
        }

    }

    public static void setBoard(Piece[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (j < 2) {
                    board[i][j] = new RedPiece();
                } else if (j > 5) {
                    board[i][j] = new BlackPiece();
                }
            }
        }
    }

    public static void printBoard(Piece[][] board) {

        //**********HEADER*********
        System.out.println("-------------------------");
        System.out.print("|\\/\\|");
        for (int i = 0; i < board.length; i++) {
            System.out.print(i + 1);
            if (i + 1 != board.length)
                System.out.print(" ");
        }
        System.out.println("|/\\/|");
        //*************************

        for (int i = 0; i < board.length; i++) {
            System.out.print("| " + CHARLIST.charAt(i) + " ");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print("|");
                if (board[j][i] == null) {
                    System.out.print("-");
                } else {
                    System.out.print(board[j][i].getToken()); //TODO add king tokens
                }
            }
            System.out.println("| " + CHARLIST.charAt(i) + " |");
        }

        //************FOOTER*************
        System.out.print("|\\/\\|");
        for (int i = 0; i < board.length; i++) {
            System.out.print(i + 1);
            if (i + 1 != board.length)
                System.out.print(" ");
        }
        System.out.println("|/\\/|");
        System.out.println("-------------------------");
        //*******************************
    }

    public static boolean game(Piece[][] board){
        return true;  //TODO add functionality
    }
}
