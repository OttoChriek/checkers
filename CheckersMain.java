import java.util.Scanner;

/**
 * Created by User on 10/23/2016.
 */
public class CheckersMain {
    //****************GAME VARS****************
    private static final int BOARDSIZE = 8;
    //****************OTHER SHIZNIT************
    private static final String CHARLIST = "ABCDEFGH";
    private static final String setPlainText = "\033[0;0m";
    private static final String setBoldText = "\033[0;1m";
    //*****************************************
    private static Piece[][] board = new Piece[BOARDSIZE][BOARDSIZE];
    private static char turn = '0';

    public static void main(String[] args) {
        setBoard();
        printBoard();

        while(game())
        {
            System.out.println(turn + " to move");
            while(!move());
            printBoard();
            if(turn == '0') {
                turn = '@';
            } else if(turn == '@'){
                turn = '0';
            }
        }

        System.out.println("\t!Congratulations winner!");
    }

    public static void setBoard() {
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

    public static void printBoard() {

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
                    if(board[j][i].isKing()){
                        System.out.print(setPlainText + board[j][i].getToken()  + setBoldText);
                    } else {
                        System.out.print(board[j][i].getToken());
                    }
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

    public static boolean move() {
        int X, Y, newX, newY;
        //*************PLAYER INPUT***************
        Scanner in = new Scanner(System.in);
        System.out.print("Which piece do you want to move: ");
        String pieceCoords = in.nextLine();
        System.out.print("Where do you want to move it: ");
        String newCoords = in.nextLine();
        pieceCoords.replaceAll("\\s+", "");
        newCoords.replaceAll("\\s+", ""); // (.replaceAll("\\s+","");) REMOVES WHITESPACE
        X = CHARLIST.indexOf(pieceCoords.toUpperCase().charAt(0));
        newX = CHARLIST.indexOf(newCoords.toUpperCase().charAt(0));
        try {
            Y = Integer.parseInt(pieceCoords.substring(1, 2)) - 1; //TODO add conditional for invalid numerical input
            newY = Integer.parseInt(newCoords.substring(1, 2)) - 1;
        } catch(NumberFormatException e) {
            System.out.println("\t!Please format your input as a letter followed by a number!");
            return false;
        }


        //**************MOVEMENT RULES*************
        if (board[Y][X] != null) { //checks the for the presence of a piece at selected coordinates
            if (newX > 7 || newX < 0 || newY > 7 || newY < 0) { //checks the coordinates are in bounds
                System.out.println("\t!Please enter valid input!");
                return false;
            } else {
                if(board[Y][X].getToken() == turn) { //checks the moved piece is on the correct team
                    if (board[Y][X].isKing()) { //king piece
                        if (Math.abs(X - newX) == 1 && Math.abs(Y - newY) == 1) { //normal move
                            movePiece(X, Y, newX, newY);
                            return true;
                        } else if (Math.abs(X - newX) == 2 && Math.abs(Y - newY) == 2 && board[Y][X].getToken() != board[(Y + newY) / 2][(X + newX) / 2].getToken()) { //jumping a piece
                            jumpPiece(X, Y, newX, newY);
                            return true;
                        } else {
                            System.out.println("\t!Please enter valid input!");
                            return false;
                        }
                    } else { //not a king piece
                        if (Math.abs(Y - newY) == 1 && X - newX == board[Y][X].getDirection()) { //normal move
                            movePiece(X, Y, newX, newY);
                            return true;
                        } else if (Math.abs(Y - newY) == 2 && X - newX == 2 * board[Y][X].getDirection() && board[Y][X].getToken() != board[(Y + newY) / 2][(X + newX) / 2].getToken()) { //jumping a piece
                            jumpPiece(X, Y, newX, newY);
                            return true;
                        } else {
                            System.out.println("\t!Please enter valid input!");
                            return false;
                        }

                    }
                } else {
                    System.out.println("\t!It's not your turn!");
                    return false;
                }
            }
        } else {
            System.out.println("\t!There's no piece there!");
            return false;
        }

    }

    public static void movePiece(int X, int Y, int newX, int newY) {
        board[newY][newX] = board[Y][X];
        board[Y][X] = null;
        kingMe(newX, newY);
    }

    public static void jumpPiece(int X, int Y, int newX, int newY) {
        board[newY][newX] = board[Y][X];
        board[Y][X] = null;
        board[(Y + newY) / 2][(X + newX) / 2] = null;
        kingMe(newX, newY);
    }

    public static void kingMe(int X, int Y){
        if(board[Y][X].getToken() == '0'){
            if(X == 0){
                board[Y][X].setKing(true);
            }
        } else if(board[Y][X].getToken() == '@') {
            if(Y == 7){
                board[Y][X].setKing(true);
            }
        }
    }

    public static boolean game(){
        int count0 = 0;
        int countAt = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j] != null) {
                    if (board[i][j].getToken() == '0') {
                        count0++;
                    } else if (board[i][j].getToken() == '@') {
                        countAt++;
                    }
                }
            }
        }

        if(count0 * countAt == 0){
            return false;
        } else {
            return true;
        }
    }
}
