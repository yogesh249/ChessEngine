
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

class Bishop extends Piece {

    public Bishop(boolean c) {
        super(c);
        setValue(3);
    }

    @Override
    public String toString() {
        return "B";
    }

    @Override
    public List<Move> getLegalMoves(Point from, ChessBoard b) {
//		Point from = b.findPiece(this);
        List<Move> v = new ArrayList<Move>();
        int dx, dy;

        int distance;
        boolean blocked;

        if (getColor() == b.getTurn()) {
            for (dx = -1; dx <= 1; dx += 2) {
                for (dy = -1; dy <= 1; dy += 2) {
                    Point tempPoint = new Point();
                    distance = 1;
                    blocked = false;
                    while (!blocked) {

                        //tempPoint.file = from.file + dx*distance;
                        char fl = from.getFile().charAt(0);
                        String file = String.valueOf(Character.valueOf((char) (fl + dx * distance)));
                        int rank = from.getRank() + dy * distance;
                        tempPoint = new Point(file, rank);

                        if (!tempPoint.onBoard()) {
                            blocked = true;
                        } else if (!(b.isOccupied(tempPoint))) {
                            Move move = new Move(new Point(from), new Point(tempPoint));
                            ChessBoard b2 = b.applyMove(move);
                            if (b2.isPositionLegal()) {
                                v.add(move);
                            }

                        } else if (b.getPiece(tempPoint).getColor() != getColor()) {
                            Move move = new Move(new Point(from), new Point(tempPoint));
                            ChessBoard b2 = b.applyMove(move);
                            if (b2.isPositionLegal()) {
                                v.add(move);
                            }
                            blocked = true;
                        } else {
                            blocked = true;
                        }
                        distance++;
                    }
                }
            }
        }
        return v;
    }

    public void drawPiece(int x, int y, Graphics g) {
        int[] X = {5, 13, 17, 25, 25, 17, 18, 20, 20, 19, 17, 18, 14, 13, 12, 11, 10, 10, 11, 12, 5, 5};
        int[] Y = {5, 7, 7, 5, 8, 8, 10, 15, 17, 20, 25, 16, 25, 23, 21, 18, 15, 12, 10, 8, 8, 5};
        int i;
        for (i = 0; i < X.length; i++) {
            X[i] += x;
            Y[i] = y - Y[i];
        }
        g.fillPolygon(X, Y, X.length);
    }

    @Override
    public List<Move> getPossibleMoves(Point from, ChessBoard b) {
        List<Move> v = new ArrayList<Move>();
        int dx, dy;

        int distance;
        boolean blocked;

        if (getColor() == b.getTurn()) {
            for (dx = -1; dx <= 1; dx += 2) {
                for (dy = -1; dy <= 1; dy += 2) {
                    Point tempPoint = new Point();
                    distance = 1;
                    blocked = false;
                    while (!blocked) {

                        //tempPoint.file = from.file + dx*distance;
                        char fl = from.getFile().charAt(0);
                        String file = String.valueOf(Character.valueOf((char) (fl + dx * distance)));
                        int rank = from.getRank() + dy * distance;
                        tempPoint = new Point(file, rank);
                        if (!tempPoint.onBoard()) {
                            blocked = true;
                        } else if (!(b.isOccupied(tempPoint))) {
                            Move move = new Move(from, tempPoint);
                            v.add(move);
                        } else if (b.getPiece(tempPoint).getColor() != getColor()) {
                            Move move = new Move(from, tempPoint);
                            v.add(move);
                            blocked = true;
                        } else {
                            blocked = true;
                        }
                        distance++;
                    }
                }
            }
        }
        return v;
    }
}
