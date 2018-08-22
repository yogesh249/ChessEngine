
import java.util.Collections;
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
//    public static String position = "2K5/1r6/2k5/8/8/8/8/8 b";
//    public static String position = "2k5/1R6/2K5/8/8/8/8/8 w";
//    public static String position="rr4k1/RR6/7K/8/8/8/8/8 w";
//	  This is mate in 4, have solved it successfully in 5 minutes 20 seconds with depth 6
	public static String position = "8/8/8/8/8/6P1/6k1/4KR1R w";
	// This is mate in 2,
	// With depth set to 2, it took 5 minutes to solve this puzzle correctly.
//	public static String position="2bqkbn1/2pppp2/np2N3/r3P1p1/p2N2B1/5Q2/PPPPKPP1/RNB2r2 w";
	public static void main(String args[]) {
		System.out.println(new Date());
		// cb is made an array, just because we need to modify it inside a lambda expression.
		// And this is one of the ways, we can do it.
		final ChessBoard[] cb = {new ChessBoard(position)};
		System.out.println("After making chess board");
		Move bestMove = null;
		// depth is made an array, just because we need to modify it inside a lambda expression.
		// And this is one of the ways, we can do it.
		int[] depth= {6};
		Double moveno=1.0;
		int counter = 1;
		while (!cb[0].isGameOver()) {
			Map<Move, Integer> scoreMap = Collections.synchronizedMap(new HashMap<>());
			int score = 0;
			if (cb[0].getTurn() == Piece.WHITE) {
				score = -1000;
			} else {
				score = 1000;
			}

			List<Move> moves = cb[0].getAllLegalMoves();
			Consumer<Move> c = m -> {
				int sc = 0;
				ChessBoard cb2 = cb[0].applyMove(m);
				// Initial depth we pass as zero to minimax function.
				sc = cb2.minimax(depth[0]);
				scoreMap.put(m, sc);
				
			};
			moves.parallelStream().forEach(c);
			for(Move move: moves)
			{
				int moveScore = scoreMap.get(move);				
				if (Math.abs(moveScore) >= Math.abs(score)) {
					score = moveScore;
					bestMove = move;
				}
				
			}
			Piece pc = cb[0].getPiece(bestMove.from);
			Point to = bestMove.to;
			System.out.println(bestMove + " :" + scoreMap.get(bestMove));
			//##################################################################################################################
			// This logic between hashes is just for printing the moves properly....
			if (cb[0].getTurn() == Piece.WHITE) {

				System.out.print(moveno.intValue() + ". " + pc + "" + bestMove.from + to);// +":"+ score);
			} else {
				if (moveno == 1.0) {
					if (position.endsWith("b")) {
						System.out.print(moveno.intValue() + ". ...");
					} else {
						System.out.print("\t");
					}
				} else {
					System.out.print("\t");
				}
				System.out.print(pc + "" + bestMove.from + to + "\n");// +":"+ score);
			}
			if (position.endsWith("w")) {
				if (counter % 2 == 0) {
					moveno = moveno + 1;
				}
			} else {
				if (counter % 2 == 1) {
					moveno = moveno + 1;
				}
			}
			counter++;
			//##################################################################################################################
			cb[0] = cb[0].applyMove(bestMove);
			depth[0]--;
			bestMove = null;
		}
		System.out.println();
		System.out.println(new Date());
	}
}
