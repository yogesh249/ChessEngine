
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class MainApp {

	String fen;

	public MainApp(String fen) {
		this.fen = fen;
	}
	// This is mate in 3 in 35 sec
//    public static String position = "2k5/1R6/2K5/8/8/8/8/8 w";

//	  This is mate in 4, have solved it successfully in 10 minutes with depth 6
	public static String position = "8/8/8/8/8/6P1/6k1/4KR1R w";
	// This is mate in 2,
	// With depth set to 2, it took 5 minutes to solve this puzzle correctly.
//	public static String position="2bqkbn1/2pppp2/np2N3/r3P1p1/p2N2B1/5Q2/PPPPKPP1/RNB2r2 w";
	public static void main(String args[]) {
//		String position = "5rk1/6pp/4p3/pP1qPn2/P1pPN2b/7P/2Q2BP1/3R2K1 b";
		System.out.println(new Date());
		final ChessBoard[] cb = {new ChessBoard(position)};
		System.out.println("After making chess board");
		final Move[] bestMove = new Move[1];
		int[] depth= {0};
		while (!cb[0].isGameOver()) {
			Map<Move, Integer> scoreMap = new HashMap<>();
			int[] score = {0};
			if (cb[0].getTurn() == Piece.WHITE) {
				score[0] = -1000;
			} else {
				score[0] = 1000;
			}

			List<Move> moves = cb[0].getAllLegalMoves();
			Consumer<Move> c = m -> {
				int sc=0;
				// Evaluate only if there are more than one possible moves
				if (moves.size() >= 1) {
					ChessBoard cb2 = cb[0].applyMove(m);
					// Initial depth we pass as zero to minimax function.
					sc = cb2.minimax(depth[0]);
					scoreMap.put(m, sc);
					System.out.print(".");
				}
				
			};
			moves.parallelStream().forEach(c);
			System.out.println(scoreMap);
			System.out.println("*******************************************************************");
			for(Move move: moves)
			{
				int moveScore = scoreMap.get(move);
				if (cb[0].getTurn() == Piece.WHITE) {
					if (moveScore == 1000) {
						// white is winning in this move...
						score[0] = moveScore;
						bestMove[0] = move;
						break;
					}
					if (moveScore >= score[0]) {
						score[0] = moveScore;
						bestMove[0] = move;
					}
				} else {
					if (moveScore == -1000) {
						// Black is winning here...
						score[0] = moveScore;
						bestMove[0] = move;
						break;
					}
					if (moveScore <= score[0]) {
						score[0] = moveScore;
						bestMove[0] = move;
					}
				}
				
			}
			Piece pc = cb[0].getPiece(bestMove[0].from);
			Point to = bestMove[0].to;
			System.out.println(pc + "" + bestMove[0].from +  to +":"+ score[0]);
			cb[0] = cb[0].applyMove(bestMove[0]);
			depth[0]++;
			bestMove[0] = null;
		}
		System.out.println(new Date());
	}
}
