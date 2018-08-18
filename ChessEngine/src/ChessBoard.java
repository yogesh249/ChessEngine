import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChessBoard extends State {

	private List<Move> moves = new ArrayList<Move>();

	public List<Move> getMoves() {
		return moves;
	}

	public static boolean whiteCanCastleQueenSide = false;
	public static boolean blackCanCastleQueenSide = false;
	public static boolean whiteCanCastleKingSide = false;
	public static boolean blackCanCastleKingSide = false;
	public Map<Point, Piece> boardMap = new LinkedHashMap<Point, Piece>();

	public boolean isMySelfCheckmated() {
		List<Move> legalMoves = getAllLegalMoves();
		if (legalMoves == null || legalMoves.isEmpty()) {
			if (isInCheck(getTurn())) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public int getWhiteMaterialCount() {
		ChessBoard cb = new ChessBoard(this);
		if (getTurn() == Piece.BLACK) {
			if (cb.isMySelfCheckmated()) {
				return 1000;
			}
			cb.setTurn(Piece.WHITE);
		}
		List<Point> whitePieces = cb.getPieces();
		int totalValue = 0;
		for (Point p : whitePieces) {
			Piece whitePiece = getPiece(p);
			if (!(whitePiece instanceof King)) {
				totalValue = totalValue + whitePiece.getValue();
			}
		}
		return totalValue;
	}

	public int getBlackMaterialCount() {
		ChessBoard cb = new ChessBoard(this);
		if (getTurn() == Piece.WHITE) {
			if (cb.isMySelfCheckmated()) {
				return -1000;
			}
			cb.setTurn(Piece.BLACK);
		}
		List<Point> blackPieces = cb.getPieces();
		int totalValue = 0;
		for (Point p : blackPieces) {
			Piece blackPiece = getPiece(p);
			if (!(blackPiece instanceof King)) {
				totalValue = totalValue + blackPiece.getValue();
			}
		}
		return totalValue;
	}

	public int score() {
		// if the player having the turn has no legal moves
		// and king is in check... Then isMySelfCheckmated is true.
		if (isMySelfCheckmated()) {
			return -1000;
		} else {
			// if there is a mate in 1.
			List<Move> legalMoves = getAllLegalMoves();
			// For each of the legal moves, search for a mate in 1.
			for (Move m : legalMoves) {
				ChessBoard c = new ChessBoard(this);
				ChessBoard newBoard = c.applyMove(m);
				if (newBoard.isMySelfCheckmated()) {
					return 1000;
				}
			}
			return 0;
		}
	}

	public boolean isOccupied(Point tempPoint) {
		if (boardMap.get(tempPoint) == null) {
			return false;
		} else {
			return true;
		}
	}

	public ChessBoard(String fen) {
		String[] parts = fen.split(" ");
		setTurn("w".equalsIgnoreCase(parts[1]) ? Piece.WHITE : Piece.BLACK);

		if (parts.length >= 3) {
			String castling = parts[2];
			if (castling.contains("K")) {
				whiteCanCastleKingSide = true;
			}
			if (castling.contains("k")) {
				blackCanCastleKingSide = true;
			}
			if (castling.contains("Q")) {
				whiteCanCastleQueenSide = true;
			}
			if (castling.contains("q")) {
				blackCanCastleQueenSide = true;
			}
		} else {
			// default value is false;
		}

		for (int x = 0; x < fen.length(); x++) {
			if (Character.isDigit(fen.charAt(x))) {
				int digitValue = Integer.valueOf(String.valueOf(fen.charAt(x)));
				String expandedValue = "";
				for (int p = 1; p <= digitValue; p++) {
					expandedValue = expandedValue + "1";
				}
				fen = fen.replace(String.valueOf(fen.charAt(x)), expandedValue);
			}
		}
		System.out.println(fen);
		String[] ranks = fen.split("/");
		for (int r = 1; r <= 8; r++) {
			String rank = ranks[r - 1];
			for (int k = 0; k < rank.length(); k++) {
				char file = (char) (97 + k);
				String fileName = Character.valueOf(file).toString();
				int rankName = (9 - r);
				String value = Character.valueOf(rank.charAt(k)).toString();
				Piece pc = PieceFactory.getPiece(value);
				Point pt = new Point(fileName, rankName);
				if (value.equals("1")) {
					boardMap.put(pt, null);
				} else {
					boardMap.put(pt, pc);
				}
			}
		}

	}

	public ChessBoard(ChessBoard chessBoard) {
		this.boardMap = new LinkedHashMap<Point, Piece>(chessBoard.boardMap);
		setTurn(chessBoard.getTurn());
		this.moves = new ArrayList<Move>(chessBoard.getMoves());
	}

	public Piece getPiece(Point tempPoint) {
		return boardMap.get(tempPoint);
	}

	public Point findPiece(Piece pc) {
		for (Map.Entry<Point, Piece> entry : boardMap.entrySet()) {
			if (entry.getValue() != null) {
				if (entry.getValue().getColor() == pc.getColor()) {
					if (entry.getValue().getClass().equals(pc.getClass())) {
						return entry.getKey();
					}
				}
			}
		}
		return null;
	}

	private List<Point> getPieces() {
		List<Point> piecePoints = new ArrayList<Point>();
		for (Map.Entry<Point, Piece> entry : boardMap.entrySet()) {
			if (entry.getValue() != null) {
				if (entry.getValue().getColor() == getTurn()) {
					if (entry.getKey().onBoard()) {
						piecePoints.add(entry.getKey());
					}
				}
			}
		}
		return piecePoints;

	}

	public boolean canCastle(boolean turn2, char c) {
		if (turn2) {
			if (c == 'Q') {
				if (getPiece(new Point("b", 1)) == null && getPiece(new Point("c", 1)) == null
						&& getPiece(new Point("d", 1)) == null) {
					return whiteCanCastleQueenSide;
				} else {
					return false;
				}
			} else {
				if (getPiece(new Point("f", 1)) == null && getPiece(new Point("g", 1)) == null) {
					return whiteCanCastleKingSide;
				} else {
					return false;
				}

			}
		} else {
			if (c == 'Q') {
				if (getPiece(new Point("b", 8)) == null && getPiece(new Point("c", 8)) == null
						&& getPiece(new Point("d", 8)) == null) {
					return blackCanCastleQueenSide;
				} else {
					return false;
				}
			} else {
				if (getPiece(new Point("f", 8)) == null && getPiece(new Point("g", 8)) == null) {
					return blackCanCastleKingSide;
				} else {
					return false;
				}

			}
		}
	}

	public ChessBoard applyMove(Move m) {
		if (m.from == null)
			throw new IllegalArgumentException("Source square is empty in Move " + m);
		ChessBoard b2 = new ChessBoard(this);
		b2.boardMap.put(m.from, null);
		b2.boardMap.put(m.to, this.getPiece(m.from));
		b2.setTurn(!getTurn());
		b2.getMoves().add(m);
		return b2;
	}

	/*
	 * This function will return true, if its WHITE's turn and blacks king is not in
	 * check or if its BLACK's turn and white's king is not in check.
	 * 
	 * else it will return false
	 */
	public boolean isPositionLegal() {
		// Find the king of the player who doesn't have the turn....

		// if black has the turn and
		if (getTurn() == Piece.BLACK) {
			if (isInCheck(Piece.WHITE)) {
				// white's king is in check, then position is illegal.
				return false;
			}
		} else {
			// If white has the turn, then
			if (isInCheck(Piece.BLACK)) {
				// if black's king is in check, the position is illegal.
				return false;
			}
		}
		return true;
	}

	// This function uses the current board position and the player to move
	// is specified by the argument passed.
	public boolean isInCheck(boolean turn) {
		ChessBoard b = new ChessBoard(this);
		b.setTurn(!turn);
		List<Move> possibleMoves = b.getAllPossibleMoves();
		Point myKingPosition = findPiece(new King(turn));
		for (Move move : possibleMoves) {
			if (move.to.equals(myKingPosition)) {
				// My king can be captured.
				return true;
			}
		}
		return false;
	}

	public List<Move> getAllPossibleMoves() {
		List<Move> allLegalMoves = new ArrayList<Move>();
		List<Point> points = getPieces();
		for (Point p : points) {
			Piece pc = getPiece(p);
			List<Move> possibleMoves = pc.getPossibleMoves(p, this);
			allLegalMoves.addAll(possibleMoves);
		}
		return allLegalMoves;
	}

	public List<Move> getAllLegalMoves() {
		List<Move> allLegalMoves = new ArrayList<Move>();
		List<Point> points = getPieces();
		for (Point point : points) {
			Piece pc = getPiece(point);
			List<Move> legalMoves = pc.getLegalMoves(point, this);
//                        System.out.println("Legal Moves for " + pc + ":"+ point+ " = " + legalMoves);
			allLegalMoves.addAll(legalMoves);
		}
		return allLegalMoves;
	}

	@Override
	public List<State> getChildren() {
		List<Move> legalMoves = getAllLegalMoves();
		List<State> children = new ArrayList<State>();
		for (Move m : legalMoves) {
			ChessBoard b = new ChessBoard(this).applyMove(m);
			children.add(b);
		}
		return children;
	}

	@Override
	public Boolean getWinner() {
		if (getTurn()) {
			// White to move
			if (isMySelfCheckmated()) {
				// White is checkmated
				// Black wins
				return Piece.BLACK;
			}
		} else {
			// Black to move
			if (isMySelfCheckmated()) {
				// Black is checkmated
				// White wins
				return Piece.WHITE;
			}
		}
		return null;
	}

	@Override
	public boolean isGameOver() {

		if (isMySelfCheckmated()) {
			return true;
		}

//		ChessBoard b = new ChessBoard(this);
//		b.setTurn(!getTurn());
//		if(b.isMySelfCheckmated())
//		{
//			// If opponent checkmated
//			return true;
//		}
		return false;
	}

	// Override this method based on the your needs.
	// The extending class should give its functionality, who is winning...
	@Override
	public int minimax(int depth) {

		if (getWinner() != null) {
			if (getWinner().equals(Piece.WHITE)) {
				return 1000 - depth;
			}
			if (getWinner().equals(Piece.BLACK)) {
				return -1000 + depth;
			}
		} else {
			if (depth == 4) {
				int whiteMaterialCount = getWhiteMaterialCount();
				int blackMaterialCount = getBlackMaterialCount();
//				System.out.println("Returning maxScore : "+(whiteMaterialCount - blackMaterialCount));
				return whiteMaterialCount - blackMaterialCount;
			}
		}
		List<State> children = getChildren();
		if (children.isEmpty()) {
			
			// No legal move move left....
			// It might be stalemate
			return 0;
		}
		if (getTurn() == Piece.WHITE) {
			int maxScore = -1000;
			for (State child : children) {
				int childScore = child.minimax(depth + 1);
				if (childScore > maxScore) {
					maxScore = childScore;
				}
			}

			return maxScore;
		} else {
			int minScore = 1000;
			for (State child : children) {
				int childScore = child.minimax(depth + 1);
				if (childScore < minScore) {
					minScore = childScore;
				}
			}
			return minScore;
		}
	}

}
