
public class Move {
	Point from;
	Point to;
	boolean capture=false;
	public Move(Point source, Point destination)
	{
		from = source;
		to = destination;
		capture=false;
	}
	public Move(Point source, Point destination, boolean capture)
	{
		from = source;
		to = destination;
		this.capture=capture;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Move))
			return false;
		Move other = (Move) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Move [from=" + from + ", to=" + to + "]";
	}
	
}
