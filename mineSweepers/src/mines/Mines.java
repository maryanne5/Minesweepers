package mines;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Mines {
	private int height,width;
	private Square [][] board;
	private boolean showAll;
	
	public Mines(int height, int width, int numMines) {
		this.height=height;
		this.width=width;
		showAll = false;
		board = new Square[height][width];
		for(int i=0; i<height; i++)
			for(int j=0;j<width; j++)
				board[i][j] = new Square(i,j);
		Random rand=new Random();
    	for(int k=0;k< numMines;k++) {
    		int i=rand.nextInt(height);
    		int j=rand.nextInt(width);
    		if(!(addMine(i, j)))
    			k--;
    	}
	}
	
	private class Square{
		
		private int i, j;
		private boolean flag, mine, open;
		
		public Square(int i, int j) {
			this.i = i;
			this.j = j;
			flag=false;
			mine=false;
			open=false;
		}

		public Set<Square> getNeighbors(){
			Set<Square> s = new HashSet<>();
			if(i-1 >= 0) {
				s.add(board[i-1][j]);
				if(j-1 >= 0) 
					s.add(board[i-1][j-1]);
				if(j+1 < width) 
					s.add(board[i-1][j+1]);
			}
			
			if(i+1 < height) {
				s.add(board[i+1][j]);
				if(j-1 >= 0) 
					s.add(board[i+1][j-1]);
				if(j+1 < width) 
					s.add(board[i+1][j+1]);
			}
			
			if(j-1 >= 0)
				s.add(board[i][j-1]);
			if(j+1 < width)
				s.add(board[i][j+1]);
			
			return s;
		}
		
		public int minesNum() {
			Set<Square> s = getNeighbors();
			int count=0;
			for(Square sqr : s) {
				if(sqr.mine==true)
					count++;
			}
			return count;
		}
		
	}
	public Boolean getFlag(int i,int j) {
		return board[i][j].flag;
	}

	public String get(int i, int j) {
		int tmp;
		Square s = board[i][j];
		if(s.open == false && showAll == false) {
			if(s.flag == true )
				return "F";
			else return ".";
		}
		else {
			if(s.mine == true)
				return "X";
			else {
				tmp = s.minesNum();
				if(tmp==0)
					return " ";
				else return Integer.toString(tmp);
			}
		} 
	}
	
	public boolean addMine(int i, int j) {
		if(board[i][j].mine==false) {
			board[i][j].mine=true;
			return true;
		}
		return false;
	}
	
	public boolean open(int i, int j) {
		boolean tmp = false;
		Set<Square> s ;
		if(board[i][j].mine==true) return false;
		if(board[i][j].open==false) board[i][j].open=true;
		else return true;
		s = board[i][j].getNeighbors();
		for(Square sqr : s) tmp = tmp || sqr.mine;
		if(tmp==false)
			for(Square sqr : s) open(sqr.i,sqr.j);
		return true;
	}
	
	public void toggleFlag(int x, int y) {
		if(board[x][y].flag==false)
			board[x][y].flag=true;
		else board[x][y].flag=false;
	}
	
	public boolean isDone() { 
		for(int i=0;i<height;i++)
			for(int j=0;j<width;j++) {
				if(board[i][j].open==false && board[i][j].mine==false)
					return false;
			}
		return true;
				
	}
	
	
	public void setShowAll(boolean showAll) {
		this.showAll=showAll;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		for(int i=0; i<height; i++) {
			for(int j=0;j<width; j++) 
				s.append(get(i,j));
			s.append("\n");
		}
		return s.toString();
			}
		
	
	}
 