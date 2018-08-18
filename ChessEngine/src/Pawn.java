
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

class Pawn extends Piece {

    public Pawn(boolean c) {
        super(c);
        setValue(1);
    }

    @Override
    public String toString() {
        return "P";
    }

    @Override
    public List<Move> getLegalMoves(Point from, ChessBoard b) {
//		Point from = b.findPiece(this);
        List<Move> v = new ArrayList<Move>();
        int dx = 0, dy;


        if (getColor() == b.getTurn()) {
            /* Find the direction for the pawn */
            if (b.getTurn() == WHITE) {
                dy = 1;
            } else {
                dy = -1;
            }

            /* Check for straight ahead move */
            char fl = from.getFile().charAt(0);
            String file = String.valueOf(Character.valueOf((char) (fl + dx)));
            int rank = from.getRank() + dy;
            Point tempPoint = new Point();

            if (!(b.isOccupied(tempPoint))) {
                /* forward promotion */
                if (tempPoint.getRank() == 1 || tempPoint.getRank() == 8) {
                    Move move = new Move(from, tempPoint);
                    ChessBoard b2 = b.applyMove(move);
                    if (b2.isPositionLegal()) {
                        v.add(move);
                    }

                } else {
                    Move move = new Move(from, tempPoint);
                    ChessBoard b2 = b.applyMove(move);
                    if (b2.isPositionLegal()) {
                        v.add(move);
                    }
                }


                /* Also check for double move */

                rank = from.getRank() + 2 * dy;
                file = from.getFile();
                tempPoint = new Point(file, rank);
                if (tempPoint.onBoard() && !(b.isOccupied(tempPoint))
                        && (from.getRank() == 2 || from.getRank() == 7)) {
                    Move move = new Move(from, tempPoint);
                    ChessBoard b2 = b.applyMove(move);
                    if (b2.isPositionLegal()) {
                        v.add(move);
                    }

                }
            }

            /* Check for attack */
            for (dx = -1; dx <= 1; dx += 2) {
                char f2 = from.getFile().charAt(0);
                file = String.valueOf(Character.valueOf((char) (f2 + dx)));
                rank = from.getRank() + dy;
                tempPoint = new Point(file, rank);

                if (tempPoint.onBoard() && b.isOccupied(tempPoint)
                        && b.getPiece(tempPoint).getColor() != getColor()) {
                    if (tempPoint.getRank() > 0 && tempPoint.getRank() < 7) {
                        Move move = new Move(from, tempPoint, true);
                        ChessBoard b2 = b.applyMove(move);
                        if (b2.isPositionLegal()) {
                            v.add(move);
                        }

                    } /* capture promotion */ else {
                        Move move = new Move(from, tempPoint);
                        ChessBoard b2 = b.applyMove(move);
                        if (b2.isPositionLegal()) {
                            v.add(move);
                        }

                    }
                }

//				/* Check for en passant */
//				else if (getColor() == BLACK && tempPoint.rank == 2)
//				{
//					Point capPoint = new Point(3,tempPoint.file);
//					Move lastMove = b.getLastMove();
//					if (tempPoint.onBoard() && b.isOccupied(capPoint) 
//						 && b.getPiece(capPoint).getColor() == WHITE
//						 && b.getPiece(capPoint).getValue() == 1
//						 && lastMove.equals(new Move(new Point(1,tempPoint.file),
//													 capPoint)))
//						v.add(new Move(from,tempPoint, capPoint));
//				}
//				else if (getColor() == WHITE && b.allMoves.size() > 0 && tempPoint.rank == 5)
//				{
//					Point capPoint = new Point(4,tempPoint.file);
//					Move lastMove = b.getLastMove();
//					if (tempPoint.onBoard() && b.isOccupied(capPoint) 
//						 && b.getPiece(capPoint).getColor() == BLACK
//						 && b.getPiece(capPoint).getValue() == 1
//						 && lastMove.equals(new Move(new Point(6,tempPoint.file),
//													 capPoint)))
//						v.add(new Move(from,tempPoint, capPoint));
//				}


            }
        }
        return v;
    }

    public void drawPiece(int x, int y, Graphics g) {
        int[] X = {5, 25, 23, 21, 18, 20, 20, 18, 22, 20, 17,
            13, 10, 8, 12, 10, 10, 12, 9, 7, 5};
        int[] Y = {5, 5, 7, 8, 9, 11, 14, 16, 16, 17, 18,
            18, 17, 16, 16, 14, 11, 9, 8, 7, 5};
        int i;
        for (i = 0; i < X.length; i++) {
            X[i] += x;
            Y[i] = y - Y[i];
        }

        g.fillPolygon(X, Y, X.length);
        g.fillOval(x + 12, y - 23, 6, 6);

    }

    @Override
    public List<Move> getPossibleMoves(Point from, ChessBoard b) {
        List<Move> v = new ArrayList<Move>();
        int dx = 0, dy;

        if (getColor() == b.getTurn()) {
            /* Find the direction for the pawn */
            if (b.getTurn() == WHITE) {
                dy = 1;
            } else {
                dy = -1;
            }

            /* Check for straight ahead move */
            char fl = from.getFile().charAt(0);
            String file = String.valueOf(Character.valueOf((char) (fl + dx)));
            int rank = from.getRank() + dy;
            Point tempPoint = new Point(file, rank);
            if (!(b.isOccupied(tempPoint))) {
                /* forward promotion */
                if (tempPoint.getRank() == 1 || tempPoint.getRank() == 8) {
                    Move move = new Move(from, tempPoint);
                    ChessBoard b2 = b.applyMove(move);
                    if (b2.isPositionLegal()) {
                        v.add(move);
                    }

                } else {
                    Move move = new Move(from, tempPoint);
                    ChessBoard b2 = b.applyMove(move);
                    if (b2.isPositionLegal()) {
                        v.add(move);
                    }
                }


                /* Also check for double move */
                rank = from.getRank() + 2 * dy;
                file = from.getFile();
                tempPoint = new Point(file, rank);
                if (tempPoint.onBoard() && !(b.isOccupied(tempPoint))
                        && (from.getRank() == 2 || from.getRank() == 7)) {
                    Move move = new Move(new Point(from), new Point(tempPoint));
                    ChessBoard b2 = b.applyMove(move);
                    if (b2.isPositionLegal()) {
                        v.add(move);
                    }

                }
            }

            /* Check for attack */
            for (dx = -1; dx <= 1; dx += 2) {
                char f2 = from.getFile().charAt(0);
                file = String.valueOf(Character.valueOf((char) (f2 + dx)));
                rank = from.getRank() + dy;
                tempPoint = new Point(file, rank);
                if (tempPoint.onBoard() && b.isOccupied(tempPoint)
                        && b.getPiece(tempPoint).getColor() != getColor()) {
                    if (tempPoint.getRank() > 0 && tempPoint.getRank() < 7) {
                        Move move = new Move(new Point(from), new Point(tempPoint));
                        v.add(move);
                    } /* capture promotion */ else {
                        Move move = new Move(new Point(from), new Point(tempPoint));
                        v.add(move);
                    }
                }

//				/* Check for en passant */
//				else if (getColor() == BLACK && tempPoint.rank == 2)
//				{
//					Point capPoint = new Point(3,tempPoint.file);
//					Move lastMove = b.getLastMove();
//					if (tempPoint.onBoard() && b.isOccupied(capPoint) 
//						 && b.getPiece(capPoint).getColor() == WHITE
//						 && b.getPiece(capPoint).getValue() == 1
//						 && lastMove.equals(new Move(new Point(1,tempPoint.file),
//													 capPoint)))
//						v.add(new Move(from,tempPoint, capPoint));
//				}
//				else if (getColor() == WHITE && b.allMoves.size() > 0 && tempPoint.rank == 5)
//				{
//					Point capPoint = new Point(4,tempPoint.file);
//					Move lastMove = b.getLastMove();
//					if (tempPoint.onBoard() && b.isOccupied(capPoint) 
//						 && b.getPiece(capPoint).getColor() == BLACK
//						 && b.getPiece(capPoint).getValue() == 1
//						 && lastMove.equals(new Move(new Point(6,tempPoint.file),
//													 capPoint)))
//						v.add(new Move(from,tempPoint, capPoint));
//				}


            }
        }
        return v;
    }
}