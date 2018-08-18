
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

class Knight extends Piece {

    public Knight(boolean c) {
        super(c);
        setValue(3);
    }

    @Override
    public String toString() {
        return "N";
    }

    @Override
    public List<Move> getLegalMoves(Point from, ChessBoard b) {
//		Point from = b.findPiece(this);
        List<Move> v = new ArrayList<Move>();
        int dx, dy;
        Point tempPoint = new Point();

        if (getColor() == b.getTurn()) {
            int i;
            for (i = 1; i <= 8; i++) {
                switch (i) {
                    case 1:
                        dx = -1;
                        dy = -2;
                        break;
                    case 2:
                        dx = -2;
                        dy = -1;
                        break;
                    case 3:
                        dx = -2;
                        dy = 1;
                        break;
                    case 4:
                        dx = -1;
                        dy = 2;
                        break;
                    case 5:
                        dx = 1;
                        dy = 2;
                        break;
                    case 6:
                        dx = 2;
                        dy = 1;
                        break;
                    case 7:
                        dx = 2;
                        dy = -1;
                        break;
                    case 8:
                        dx = 1;
                        dy = -2;
                        break;
                    default:
                        dx = 0;
                        dy = 0;
                }

                char fl = from.getFile().charAt(0);
                String file = String.valueOf(Character.valueOf((char) (fl + dx)));
                int rank = from.getRank() + dy;
                tempPoint = new Point(file, rank);
                if (!tempPoint.onBoard()); else if (!(b.isOccupied(tempPoint))
                        || (b.getPiece(tempPoint).getColor() != getColor())) {
                    Move move = new Move(new Point(from), new Point(tempPoint));
                    ChessBoard b2 = b.applyMove(move);
                    if (b2.isPositionLegal()) {
                        v.add(move);
                    }
                }

            }
        }
        return v;
    }

    public void drawPiece(int x, int y, Graphics g) {
        int i;
        int[] X = {11, 25, 24, 23, 22, 20, 17, 14, 12, 12, 10, 11, 8, 6, 5, 7, 9,
            12, 14, 15, 16, 15, 14, 13, 11};
        int[] Y = {-5, -5, -12, -16, -19, -21, -23, -23, -25, -23, -24, -22, -17,
            -15, -14, -13, -12, -15, -17, -16, -16, -13, -11, -8, -5};
        for (i = 0; i < X.length; i++) {
            X[i] += x;
            Y[i] += y;
        }
        /*Polygon eye = new Polygon();
        eye.addPoint(x+10,y-17);
        eye.addPoint(x+13,y-18);
        eye.addPoint(x+14,y-22);
        eye.addPoint(x+11,y-20);
        eye.addPoint(x+10,y-17);
        g.drawPolygon(eye); */
        g.fillPolygon(X, Y, X.length);


    }

    @Override
    public List<Move> getPossibleMoves(Point from, ChessBoard b) {
        List<Move> v = new ArrayList<Move>();
        int dx, dy;
        Point tempPoint = new Point();

        if (getColor() == b.getTurn()) {
            int i;
            for (i = 1; i <= 8; i++) {
                switch (i) {
                    case 1:
                        dx = -1;
                        dy = -2;
                        break;
                    case 2:
                        dx = -2;
                        dy = -1;
                        break;
                    case 3:
                        dx = -2;
                        dy = 1;
                        break;
                    case 4:
                        dx = -1;
                        dy = 2;
                        break;
                    case 5:
                        dx = 1;
                        dy = 2;
                        break;
                    case 6:
                        dx = 2;
                        dy = 1;
                        break;
                    case 7:
                        dx = 2;
                        dy = -1;
                        break;
                    case 8:
                        dx = 1;
                        dy = -2;
                        break;
                    default:
                        dx = 0;
                        dy = 0;
                }

                char fl = from.getFile().charAt(0);
                String file = String.valueOf(Character.valueOf((char) (fl + dx)));
                int rank = from.getRank() + dy;
                tempPoint = new Point(file, rank);
                if (!tempPoint.onBoard())
                {
                	// do nothing
                }
                else if (!(b.isOccupied(tempPoint))
                        || (b.getPiece(tempPoint).getColor() != getColor())) {
                    Move move = new Move(from, tempPoint);
                    v.add(move);
                }

            }
        }
        return v;
    }
}
