import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

class Queen extends Piece
{
	public Queen(boolean c)
	{	
		super(c);
		setValue(9);
	}
    @Override
	public String toString()
	{
		return "Q";
	}	
    @Override
	public List<Move> getLegalMoves(Point from, ChessBoard b)
	{
//		Point from = b.findPiece(this);
		List<Move> v = new ArrayList<Move>();
		int dx,dy;
		
		int distance;
		boolean blocked;
		
		if (getColor() == b.getTurn())
			for (dx=-1;dx<=1;dx++)
				for (dy=-1;dy<=1;dy++)
				{
					distance = 1;
					blocked = false;
					while (!blocked)
					{
						char fl = from.getFile().charAt(0);
                                                
						String file = String.valueOf(Character.valueOf((char) (fl+dx*distance)));
						int rank = from.getRank() + dy*distance;
                                                Point tempPoint=new Point(file, rank);
				
						if (!tempPoint.onBoard())
							blocked = true;
						else if (!(b.isOccupied(tempPoint)))
						{
//							Move move = new Move(new Point(from),new Point(tempPoint));
                                                        Move move = new Move(from ,tempPoint);
							ChessBoard b2 = b.applyMove(move);
							if(b2.isPositionLegal())
							{
//								System.out.println("Adding " + move + " to legal Moves");
                                                                v.add(move);
							}				
							
						}
						else if (b.getPiece(tempPoint).getColor() != getColor()) 
						{
//							Move move = new Move(new Point(from),new Point(tempPoint));
                                                    Move move = new Move(from ,tempPoint);
							ChessBoard b2 = b.applyMove(move);
							if(b2.isPositionLegal())
							{
//                                                            System.out.println("Adding " + move + " to legal Moves");	
                                                            v.add(move);
							}				
							blocked = true;
						}
						else
							blocked = true;
						distance++;
					}
				}
		return v;
	}
	
	
	public void drawPiece(int x,int y, Graphics g)
	{	
		
		int []X = {10,20,20,28,19,20,15,10,11,2,10,10};
		int []Y = {5,5,10,18,14,25,16,25,14,18,10,5};
		int i;
		for (i=0;i<X.length;i++)
		{
			X[i] += x;
			Y[i] = y-Y[i];
		}
									   
		g.fillPolygon(X,Y,X.length);
	}
	@Override
	public List<Move> getPossibleMoves(Point from, ChessBoard b) {
		List<Move> v = new ArrayList<Move>();
		int dx,dy;
		
		int distance;
		boolean blocked;
		
		if (getColor() == b.getTurn())
			for (dx=-1;dx<=1;dx++)
				for (dy=-1;dy<=1;dy++)
				{
					distance = 1;
					blocked = false;
					while (!blocked)
					{
						char fl = from.getFile().charAt(0);
						String file = String.valueOf(Character.valueOf((char) (fl+dx*distance)));
						int rank = from.getRank() + dy*distance;
                                                Point tempPoint=new Point(file, rank);
						if (!tempPoint.onBoard())
							blocked = true;
						else if (!(b.isOccupied(tempPoint)))
						{
//							Move move = new Move(new Point(from),new Point(tempPoint));
                                                        Move move = new Move(from ,tempPoint);
							v.add(move);
							
						}
						else if (b.getPiece(tempPoint).getColor() != getColor()) 
						{
//							Move move = new Move(new Point(from),new Point(tempPoint));
                                                    Move move = new Move(from ,tempPoint);
							v.add(move);
							blocked = true;
						}
						else
							blocked = true;
						distance++;
					}
				}
		return v;
	}
}
