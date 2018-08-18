
import java.util.List;

public abstract class State {

    public abstract List<State> getChildren();

    public abstract Boolean getWinner();

    public abstract boolean isGameOver();
    private boolean turn;

    public boolean getTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }
    // Override this method based on the your needs.
    // The extending class should give its functionality, who is winning...

    public int minimax(int depth) {

        if (!getWinner().equals("")) {
            if (getWinner().equals(Boolean.TRUE)) {
                return 1000;
            }
            if (getWinner().equals(Boolean.FALSE)) {
                return -1000;
            }
            // THe below commented code will never be executed
            // because if getWinner() is null, it would have thrown exception at line no. 25.
//            if (getWinner() == null) {
//                return 0;
//            }
        }
        List<State> children = getChildren();
        if (getTurn()) {
            int maxScore = -100;
            for (State child : children) {
                int childScore = child.minimax(depth + 1);
                if (childScore > maxScore) {
                    maxScore = childScore;
                }
            }
            return maxScore;
        } else {
            int minScore = 100;
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
