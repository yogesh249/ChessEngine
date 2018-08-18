import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

class King extends Piece
{
	public King(boolean c)
	{	
		super(c);
		setValue(1000);
	}
    @Override
	public List<Move> getLegalMoves(Point from, ChessBoard b)
	{
		List<Move> v = new ArrayList<Move>();
		int dx,dy;
		
		/* Check for castling */
		if (b.canCastle(b.getTurn(),'Q'))
			v.add(new Move(from,new Point("c", from.getRank())));
		
		if (b.canCastle(b.getTurn(),'K'))
			v.add(new Move(from, new Point("g", from.getRank())));
		
		/* Check normal moves */
		if (getColor() == b.getTurn())
			for (dx=-1;dx<=1;dx++)
				for (dy=-1;dy<=1;dy++)
				{
					
					int rank = from.getRank() + dy;
					char fl = from.getFile().charAt(0);
					String file = String.valueOf(Character.valueOf((char) (fl+dx)));
                                        Point tempPoint = new Point(file, rank);
					if (tempPoint.onBoard())
					{
						if(b.isOccupied(tempPoint))
						{
							if(b.getPiece(tempPoint).getColor() != getColor())
							{
								// At tempPoint, an opponent piece is there
								// So its a capture.
								Move move = new Move(from,tempPoint);
								ChessBoard b2 = b.applyMove(move);
								if(b2.isPositionLegal())
								{
									v.add(move);
								}
							}
							else
							{
								// Cannot capture my own piece.
								
							}
						}
						else
						{
							Move move = new Move(from,tempPoint);
							ChessBoard b2 = b.applyMove(move);
							if(b2.isPositionLegal())
							{
								v.add(move);
							}
						}
					}
				}
		return v;
	}
	
	
	public void drawPiece(int x,int y, Graphics g)
	{	
		int []X = {10,20,22,22,24,25,25,24,22,20,18,17,15,
					13,12,10,8,6,5,5,6,8,8,10};
		int []Y = {5,5,6,10,13,15,17,18,20,20,18,22,23,
				    22,18,20,20,18,17,15,13,10,6,5};
		int i;
		for (i=0;i<X.length;i++)
		{
			X[i] += x;
			Y[i] = y-Y[i];
		}
									   
		g.fillPolygon(X,Y,X.length);
		g.drawLine(x+13,y-25,x+17,y-25);
		g.drawLine(x+15,y-27,x+15,y-23);
	}


	@Override
	public String toString() {
		return "K";
	}
	@Override
	public List<Move> getPossibleMoves(Point from, ChessBoard b) {
		List<Move> v = new ArrayList<Move>();
		int dx,dy;
		
		
		/* Check for castling */
		if (b.canCastle(b.getTurn(),'Q'))
			v.add(new Move(from,new Point("c", from.getRank())));
		
		if (b.canCastle(b.getTurn(),'K'))
			v.add(new Move(from, new Point("g", from.getRank())));
		
		/* Check normal moves */
		if (getColor() == b.getTurn())
			for (dx=-1;dx<=1;dx++)
				for (dy=-1;dy<=1;dy++)
				{
					
					int rank = from.getRank() + dy;
					char fl = from.getFile().charAt(0);
					String file = String.valueOf(Character.valueOf((char) (fl+dx)));
                                        Point tempPoint = new Point(file, rank);
					if (tempPoint.onBoard())
					{
						if(b.isOccupied(tempPoint))
						{
							if(b.getPiece(tempPoint).getColor() != getColor())
							{
								// At tempPoint, an opponent piece is there
								// So its a capture.
								Move move = new Move(from, tempPoint);
								v.add(move);
							}
							else
							{
								// Cannot capture my own piece.
								
							}
						}
						else
						{
							Move move = new Move(from, tempPoint);
							v.add(move);
						}
					}
				}
		return v;
	}
}
