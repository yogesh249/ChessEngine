import java.util.ArrayList;
import java.util.List;

class Rook extends Piece {
	public Rook(boolean c) {
		super(c);
		setValue(5);
	}

	@Override
	public String toString() {
		return "R";
	}

	@Override
	public List<Move> getLegalMoves(Point from, ChessBoard b) {
		List<Move> v = new ArrayList<Move>();
		int dx, dy;

		int distance;
		boolean blocked;

		if (getColor() == b.getTurn()) {
			/* Check the horizontals */
			for (dx = -1; dx <= 1; dx += 2) {
				distance = 1;
				blocked = false;
				while (!blocked) {
					char fl = from.getFile().charAt(0);
					String file = String.valueOf(Character.valueOf((char) (fl + dx * distance)));
					int rank = from.getRank();
					Point tempPoint = new Point(file, rank);

					if (!tempPoint.onBoard())
						blocked = true;
					else if (!(b.isOccupied(tempPoint))) {
//						Move move = new Move(new Point(from),new Point(tempPoint));
						Move move = new Move(from, tempPoint);
						ChessBoard b2 = b.applyMove(move);
						if (b2.isPositionLegal()) {
							v.add(move);
						}
					} else if (b.getPiece(tempPoint).getColor() != getColor()) {
						Move move = new Move(from, tempPoint);
						ChessBoard b2 = b.applyMove(move);
						if (b2.isPositionLegal()) {
							v.add(move);
						}
						blocked = true;
					} else
						blocked = true;
					distance++;
				}
			}
			/* Check the verticals */
			for (dy = -1; dy <= 1; dy += 2) {
				distance = 1;
				blocked = false;
				while (!blocked) {
					String file = from.getFile();
					int rank = from.getRank() + dy * distance;
					Point tempPoint = new Point(file, rank);

					if (!tempPoint.onBoard())
						blocked = true;
					else if (!(b.isOccupied(tempPoint))) {
//						Move move = new Move(new Point(from),new Point(tempPoint));
						Move move = new Move(from, tempPoint);
						ChessBoard b2 = b.applyMove(move);
						if (b2.isPositionLegal()) {
							v.add(move);
						}
					} else if (b.getPiece(tempPoint).getColor() != getColor()) {
//						Move move = new Move(new Point(from),new Point(tempPoint));
						Move move = new Move(from, tempPoint);
						ChessBoard b2 = b.applyMove(move);
						if (b2.isPositionLegal()) {
							v.add(move);
						}
						blocked = true;
					} else
						blocked = true;
					distance++;
				}
			}
		}
		return v;
	}

	@Override
	public List<Move> getPossibleMoves(Point from, ChessBoard b) {
		List<Move> v = new ArrayList<Move>();
		int dx, dy;
		int distance;
		boolean blocked;

		if (getColor() == b.getTurn()) {
			/* Check the horizontals */
			for (dx = -1; dx <= 1; dx += 2) {
				distance = 1;
				blocked = false;
				while (!blocked) {

					char fl = from.getFile().charAt(0);
					String file = String.valueOf(Character.valueOf((char) (fl + dx * distance)));
					int rank = from.getRank();
					Point tempPoint = new Point(file, rank);

					if (!tempPoint.onBoard())
						blocked = true;
					else if (!(b.isOccupied(tempPoint))) {
						Move move = new Move(from, tempPoint);
						v.add(move);
					} else if (b.getPiece(tempPoint).getColor() != getColor()) {
						Move move = new Move(from, tempPoint);
						v.add(move);
						blocked = true;
					} else
						blocked = true;
					distance++;
				}
			}
			/* Check the verticals */
			for (dy = -1; dy <= 1; dy += 2) {
				distance = 1;
				blocked = false;
				while (!blocked) {
					String file = from.getFile();
					int rank = from.getRank() + dy * distance;
					Point tempPoint = new Point(file, rank);

					if (!tempPoint.onBoard())
						blocked = true;
					else if (!(b.isOccupied(tempPoint))) {
						Move move = new Move(from, tempPoint);
						v.add(move);
					} else if (b.getPiece(tempPoint).getColor() != getColor()) {
						Move move = new Move(from, tempPoint);
						v.add(move);
						blocked = true;
					} else
						blocked = true;
					distance++;
				}
			}
		}
		return v;
	}
}
