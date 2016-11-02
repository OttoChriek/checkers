/**
 * Created by User on 10/23/2016.
 */
public class Piece {

    private char token;
    private boolean alive;
    private boolean king;
    private int direction;

    public Piece(char a, int b){
        token = a;
        alive = true;
        king = false;
        direction = b;
    }

    public char getToken() {
        return token;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isKing() {
        return king;
    }

    public void setKing(boolean king) {
        this.king = king;
    }

    public int getDirection() {
        return direction;
    }
}
