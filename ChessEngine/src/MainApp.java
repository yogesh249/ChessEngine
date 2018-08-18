
import java.util.Date;
import java.util.List;

public class MainApp {

	String fen;

	public MainApp(String fen) {
		this.fen = fen;
	}
	// This is mate in 3
//    public static String position = "2k5/1R6/2K5/8/8/8/8/8 w";

//	  This is mate in 4, have solved it successfully.
//	public static String position = "8/8/8/8/8/6P1/6k1/4KR1R w";
	// This is mate in 2,
	// With depth set to 2, it took 5 minutes to solve this puzzle correctly.
	public static String position="2bqkbn1/2pppp2/np2N3/r3P1p1/p2N2B1/5Q2/PPPPKPP1/RNB2r2 w";
	public static void main(String args[]) {
//		String position = "5rk1/6pp/4p3/pP1qPn2/P1pPN2b/7P/2Q2BP1/3R2K1 b";
		System.out.println(new Date());
		ChessBoard cb = new ChessBoard(position);
		System.out.println("After making chess board");
		Move bestMove = null;
		int sc = 0;

		while (!cb.isGameOver()) {
			int score = 0;
			if (cb.getTurn() == Piece.WHITE) {
				score = -1000;
			} else {
				score = 1000;
			}

			List<Move> moves = cb.getAllLegalMoves();
//            System.out.println(moves);
			// Iterate over all the legal moves...
			for (Move m : moves) {

				// Evaluate only if there are more than one possible moves
				if (moves.size() > 1) {
					ChessBoard cb2 = new ChessBoard(cb);
//                    System.out.print("Evaluating move " + m);
					cb2 = cb2.applyMove(m);
					sc = 0;
//					 Initial depth we pass as zero to minimax function.
					sc = cb2.minimax(0);
//                    System.out.println(":" + sc);
					System.out.print(".");;
				}
				if (cb.getTurn() == Piece.WHITE) {
					if (sc == 1000) {
						// white is winning in this move...
						score = sc;
						bestMove = m;
						break;
					}
					if (sc >= score) {
						score = sc;
						bestMove = m;
					}
				} else {
					if (sc <= score) {
						score = sc;
						bestMove = m;
					}
					if (sc == -1000) {
						// Black is winning here...
						break;
					}
				}
			}
			System.out.println(bestMove);
			cb = cb.applyMove(bestMove);
			bestMove = null;
		}
		System.out.println(new Date());
	}
}
