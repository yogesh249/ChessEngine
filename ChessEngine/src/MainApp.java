
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
	// This is mate in 3 in 12 sec with depth 6 (with alpha beta pruning)
//    public static String position = "2k5/1R6/2K5/8/8/8/8/8 w";
//     Not able to solve this, with depth 12 (even with Alpha beta pruning)
//    public static String position="rr4k1/RR6/7K/8/8/8/8/8 w";
//	  This is mate in 4, have solved it successfully in 20 seconds with depth 6 using alpha beta pruning
	// This is currently not evaluating mate in 4 correctly.
	public static String position = "8/8/8/8/8/6P1/6k1/4KR1R w";
	// With depth set to 2, it took 25 seconds to solve this puzzle correctly (mate in 2) with alpha beta pruning
//	public static String position="2bqkbn1/2pppp2/np2N3/r3P1p1/p2N2B1/5Q2/PPPPKPP1/RNB2r2 w";
	public static void main(String args[]) {
		System.out.println(new Date());
		// cb is made an array, just because we need to modify it inside a lambda expression.
		// And this is one of the ways, we can do it.
		final ChessBoard[] cb = {new ChessBoard(position)};
		System.out.println("After making chess board");
		Move bestMove = null;
		int[] depth= {6};
		int[] alpha = { -Integer.MAX_VALUE };
		int[] beta = {Integer.MAX_VALUE };
		
		while (!cb[0].isGameOver()) {
			Map<Move, Integer> scoreMap = Collections.synchronizedMap(new HashMap<>());
			int score = 0;
			if (cb[0].getTurn() == Piece.WHITE) {
				score = -1000000;
			} else {
				score = 1000000;
			}

			List<Move> moves = cb[0].getAllLegalMoves();
			moves.parallelStream().forEach(m -> {
				int sc = 0;
				ChessBoard cb2 = cb[0].applyMove(m);
				sc = cb2.minimaxWithAlphaBeta(depth[0], alpha[0], beta[0]); 
				scoreMap.put(m, sc);
			});
			for(Move move: moves)
			{
				int moveScore = scoreMap.get(move);		
				if(cb[0].getTurn()==Piece.WHITE)
				{
					if(moveScore >= score)
					{
						score = moveScore;
						bestMove = move;
					}
				}
				else
				{
					if(moveScore<=score)
					{
						score = moveScore;
						bestMove = move;
					}
				}
			}
			cb[0] = cb[0].applyMove(bestMove);
			depth[0]--;
			bestMove = null;
		}
		printMoves(cb[0]);
		System.out.println();
		System.out.println(new Date());
	}
	
	public static void printMoves(ChessBoard cb)
	{
		Double moveno=1.0;
		int counter = 1;
		ChessBoard initialPosition = new ChessBoard(position);
		for(Move m: cb.getMoves())
		{
			Piece pc = initialPosition.getPiece(m.from);
			Point to = m.to;
			if (initialPosition.getTurn() == Piece.WHITE) {
				System.out.print(moveno.intValue() + ". " + pc + "" + m.from + to);// +":"+ score);
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
				System.out.print(pc + "" + m.from + to + "\n");// +":"+ score);
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
			initialPosition=initialPosition.applyMove(m);
			counter++;
		}
	}
}
