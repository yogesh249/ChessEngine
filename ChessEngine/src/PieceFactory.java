
public class PieceFactory {
	public static Piece getPiece(String piece)
	{
		Piece pc = null;
		if(piece.equalsIgnoreCase("P"))
		{
			pc = new Pawn(Character.isUpperCase(piece.charAt(0)));
		}
		else if("R".equalsIgnoreCase(piece))
		{
			pc = new Rook(Character.isUpperCase(piece.charAt(0)));
		}
		else if("N".equalsIgnoreCase(piece))
		{
			pc = new Knight(Character.isUpperCase(piece.charAt(0)));
		}
		else if("B".equalsIgnoreCase(piece))
		{
			pc = new Bishop(Character.isUpperCase(piece.charAt(0)));
		}
		else if("Q".equalsIgnoreCase(piece))
		{
			pc = new Queen(Character.isUpperCase(piece.charAt(0)));
		}
		else if("K".equalsIgnoreCase(piece))
		{
			pc = new King(Character.isUpperCase(piece.charAt(0)));
		}
		return pc;
	}
}	
