public class PositionOnBoard
{
	private int row;
	private int col;
	
	public PositionOnBoard(int row, int col)
	{
		this.row = row;
		this.col = col;
	}
	
	public PositionOnBoard(PositionOnBoard other)
	{
		this.row = other.row;
		this.col = other.col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	
	@Override
	public boolean equals(Object obj) 
	{
		if (obj instanceof PositionOnBoard) 
		{
			PositionOnBoard other = (PositionOnBoard) obj;
			return this.row == other.row && this.col == other.col;
		}
		return super.equals(obj);
	}
	
	

	public String toString()
	{
		return "(" + row + ", " + col + ")";
	}

}
