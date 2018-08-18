
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
		final ChessBoard[] cb = {new ChessBoard(position)};
		System.out.println("After making chess board");
		final Move[] bestMove = new Move[1];
		int[] sc = {0};

		while (!cb[0].isGameOver()) {
			int[] score = {0};
			if (cb[0].getTurn() == Piece.WHITE) {
				score[0] = -1000;
			} else {
				score[0] = 1000;
			}

			List<Move> moves = cb[0].getAllLegalMoves();
//            System.out.println(moves);
			// Iterate over all the legal moves...
			moves.parallelStream().forEach(m-> {

				// Evaluate only if there are more than one possible moves
				if (moves.size() > 1) {
					ChessBoard cb2 = cb[0].applyMove(m);
					sc[0] = 0;
//					 Initial depth we pass as zero to minimax function.
					sc[0] = cb2.minimax(0);
//                    System.out.println(":" + sc);
					System.out.print(".");;
				}
				if (cb[0].getTurn() == Piece.WHITE) {
					if (sc[0] == 1000) {
						// white is winning in this move...
						score[0] = sc[0];
						bestMove[0] = m;
						return;
					}
					if (sc[0] >= score[0]) {
						score[0] = sc[0];
						bestMove[0] = m;
					}
				} else {
					if (sc[0] <= score[0]) {
						score[0] = sc[0];
						bestMove[0] = m;
					}
					if (sc[0] == -1000) {
						// Black is winning here...
						return;
					}
				}
			});
//			System.out.println(bestMove[0]);
			Piece pc = cb[0].getPiece(bestMove[0].from);
			Point to = bestMove[0].to;
			System.out.println(pc + "" +  to);
			cb[0] = cb[0].applyMove(bestMove[0]);
			bestMove[0] = null;
		}
		System.out.println(new Date());
	}
}
