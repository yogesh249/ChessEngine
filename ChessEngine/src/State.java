
import java.util.List;

public abstract class State {
	
	/**
	 * This method needs to be overriden to get the list of possible moves of the given position.
	 * @return
	 */
    public abstract List<State> getChildren();
    /**
     * Override this method to define if there is a winner in the given position.
     * @return
     */
    public abstract Boolean getWinner();

    /**
	 * @return true, if there is a winner in this position
	 * else return false;
	 */
	public boolean isGameOver() {
		return (getWinner()!=null);
	}
    private boolean turn;

    public boolean getTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }
    /**
     * This method is used for alpha beta pruning
     * @param depth
     * @param alpha
     * @param beta
     * @return
     */
    public int minimaxWithAlphaBeta(int depth, int alpha, int beta)
    {
    	Boolean winner = getWinner();
		if (winner != null) {
			if (winner.equals(Piece.WHITE)) {
				return Integer.MAX_VALUE;
			}
			if (winner.equals(Piece.BLACK)) {
				return -Integer.MAX_VALUE;
			}
		}
		else
		{
			// Give a terminating condition to recursion... if the tree is long enough like chess.
			
		}
		List<State> children = getChildren();
		if (children==null || children.isEmpty()) {

			// No legal move move left....
			// It should be draw. hence score  is zero.
			return 0;
		}
		
		if(getTurn())
		{
			int bestVal = -Integer.MAX_VALUE;
			for(State child: children)
			{
				int childScore = child.minimaxWithAlphaBeta(depth - 1, alpha, beta);
				bestVal = Math.max(bestVal, childScore);
				alpha = Math.max(alpha, bestVal);
				if (beta <= alpha) {
					break;
				}
			}
			return bestVal;
		}
		else
		{
			int bestVal = Integer.MAX_VALUE;
			for(State child: children)
			{
				int childScore = child.minimaxWithAlphaBeta(depth - 1, alpha, beta);
				bestVal = Math.min(bestVal,  childScore);
				 beta = Math.min( beta, bestVal);
				 if( beta <= alpha )
				 {
		                break;
				 }
			}
			return bestVal;
		}		
    }
    
    // Override this method based on the your needs.
    // The extending class should give its functionality, who is winning...

    public int minimax(int depth) {

        if (!getWinner().equals("")) {
            if (getWinner().equals(Boolean.TRUE)) {
                return Integer.MAX_VALUE;            
            }
            if (getWinner().equals(Boolean.FALSE)) {
            	// Deliberately used -Integer.MAX_VALUE and not Integer.MIN_VALUE
                return -Integer.MAX_VALUE;
            }
        }
        List<State> children = getChildren();
        if (getTurn()) {
            int maxScore = -Integer.MAX_VALUE;
            for (State child : children) {
                int childScore = child.minimax(depth - 1);
                if (childScore > maxScore) {
                    maxScore = childScore;
                }
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (State child : children) {
                int childScore = child.minimax(depth - 1);
                if (childScore < minScore) {
                    minScore = childScore;
                }
            }
            return minScore;
        }

    }
}
