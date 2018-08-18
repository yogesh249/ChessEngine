
import java.util.List;

abstract public class Piece {

    public final static boolean WHITE = true;
    public final static boolean BLACK = false;
    private boolean color;
    private int value;

    public Piece(boolean c) {
        color = c;
        value = 0;
    }

    abstract public List<Move> getLegalMoves(Point from, ChessBoard b);
    /* getLegalMoves does not test for checks.  Therefore,
    the calling statement should filter the moves through
    an isCheck() function */

    abstract public List<Move> getPossibleMoves(Point from, ChessBoard b);

    /* draw a piece given the lower left corner of the square */
//	abstract public void drawPiece(int x, int y, Graphics g);
    public boolean getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    protected void setValue(int v) {
        value = v;
    }
}