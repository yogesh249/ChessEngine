
public class Point {

    @Override
    public String toString() {
        return file + rank;
    }
    private String file = "";
    private int rank = 0;

    @Override
    public int hashCode() {
        return (file + rank).hashCode();
    }

    public String getFile() {
        return file;
    }

    public int getRank() {
        return rank;
    }

    public Point(String file, int rank) {
        this.file = file;
        this.rank = rank;
    }

    public Point() {
        // TODO Auto-generated constructor stub
    }

    public Point(Point from) {
        this.file = from.file;
        this.rank = from.rank;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Point)) {
            return false;
        }
        Point other = (Point) obj;
        if (file == null) {
            if (other.file != null) {
                return false;
            }
        } else if (!file.equals(other.file)) {
            return false;
        }
        if (rank != other.rank) {
            return false;
        }
        return true;
    }

    public boolean onBoard() {
        if (file.compareTo("a") >= 0 && file.compareTo("h") <= 0) {
            if (rank >= 1 && rank <= 8) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
