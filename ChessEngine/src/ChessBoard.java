import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
			Boolean playerHavingTheMove = getTurn();
			return isInCheck(playerHavingTheMove);
		}
		return false;
	}

	private int getMaterialCount(Boolean color) {
		ChessBoard cb = new ChessBoard(this);
		cb.setTurn(color);
		List<Point> pieces = cb.getPieces();
		int totalValue = 0;
		for (Point p : pieces) {
			Piece piece = getPiece(p);
			if (!(piece instanceof King)) {
				totalValue = totalValue + piece.getValue();
			}
		}
		return totalValue;
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
		
		Piece pc = this.getPiece(m.from);
		b2.boardMap.put(m.from, null);
		b2.boardMap.put(m.to, pc);
		b2.setTurn(!getTurn());
		b2.getMoves().add(m);
		return b2;
	}

	/*
	 * This function will return true, 
	 * if its WHITE's turn and blacks king is not in check or
	 * if its BLACK's turn and white's king is not in check.
	 * 
	 * else it will return false (This will be the case, if any player can capture opponent's king
	 */
	public boolean isPositionLegal() {
		// Find the king of the player who doesn't have the turn....
		boolean opponent = !getTurn();
		if (isInCheck(opponent)) {
			// its my turn and opponent is in check.
			// This can't be a legal position.
			return false;
		}
		// Its my turn and my opponent is NOT in check.
		// Hence the position is legal.
		return true;
	}

	// This function uses the current board position and the player who is to be checked to be in check is passed as an argument.
	public boolean isInCheck(boolean whosInCheck) {
		ChessBoard b = new ChessBoard(this);
		b.setTurn(!whosInCheck);
		List<Move> possibleMoves = b.getAllPossibleMoves();
		Point myKingPosition = findPiece(new King(whosInCheck));
		for (Move move : possibleMoves) {
			if (move.to.equals(myKingPosition)) {
				// My king can be captured.
				return true;
			}
		}
		return false;
	}

	public List<Move> getAllPossibleMoves() {
		// Because we are using mutliple threads to write into arraylist, we must use synchronized list.
		List<Move> allLegalMoves = Collections.synchronizedList(new ArrayList<Move>());
		List<Point> points = getPieces();
		points.parallelStream().forEach(p->{
			Piece pc = getPiece(p);
			List<Move> possibleMoves = pc.getPossibleMoves(p, this);
			allLegalMoves.addAll(possibleMoves);
		});
		return allLegalMoves;
	}

	public List<Move> getAllLegalMoves() {
		// Because we are using mutliple threads to write into arraylist, we must use synchronized list.
		List<Move> allLegalMoves = Collections.synchronizedList(new ArrayList<Move>());
		List<Point> points = getPieces();
		
		points.parallelStream().forEach(point-> {
			Piece pc = getPiece(point);
			List<Move> legalMoves = pc.getLegalMoves(point, this);
			allLegalMoves.addAll(legalMoves);
		});
		return allLegalMoves;
	}

	@Override
	public List<State> getChildren() {
		List<Move> legalMoves = getAllLegalMoves();
		List<State> children = new ArrayList<State>();
		for (Move m : legalMoves) {
			ChessBoard b = this.applyMove(m);
			children.add(b);
		}
		return children;
	}
	/**
	 * return true, if my king has been checkmated
	 * else return false;
	 */
	@Override
	public Boolean getWinner() {
		if(isMySelfCheckmated())
		{
			// My king is checkmated, Opponent wins...
			return !getTurn();
		}
		return null;
	}
	/**
	 * @return true, if there is a winner in this position
	 * else return false;
	 */
	@Override
	public boolean isGameOver() {
		return (getWinner()!=null);
	}

	// Override this method based on the your needs.
	// The extending class should give its functionality, who is winning...
	@Override
	public int minimax(int depth) {
		Boolean winner = getWinner();
		if (winner != null) {
			if (winner.equals(Piece.WHITE)) {
				return 1000 + depth;
			}
			if (winner.equals(Piece.BLACK)) {
				return -1000 - depth;
			}
		} else {
			if (depth == 0) {
				int whiteMaterialCount = getMaterialCount(Piece.WHITE);
				int blackMaterialCount = getMaterialCount(Piece.BLACK);
				return whiteMaterialCount - blackMaterialCount;
			}
		}
		List<State> children = getChildren();
		if (children==null || children.isEmpty()) {

			// No legal move move left....
			// It should be stalemate
			return 0;
		}
		Map<State, Integer> scoreMap = Collections.synchronizedMap(new HashMap<>());
		children.parallelStream().forEach(child-> {
			int childScore = child.minimax(depth - 1);
			scoreMap.put(child, childScore);
		});
		if (getTurn() == Piece.WHITE) {
			int maxScore = -1000;
			maxScore=Math.max(maxScore, Collections.max(scoreMap.values()));
			return maxScore;
		} else {
			int minScore = 1000;
			minScore = Math.min(minScore, Collections.min(scoreMap.values()));
			return minScore;
		}
	}

}
