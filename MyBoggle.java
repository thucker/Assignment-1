import java.io.*;
import java.util.*;
public class MyBoggle
{
	public static void main(String[] args) throws Exception
	{
	Scanner dict = new Scanner( new File("dictionary.txt"));
	SimpleDictionary simple = new SimpleDictionary();
	SimpleDictionary smee = new SimpleDictionary();
	Trie dic = new Trie();
	Trie solved = new Trie();
	String temp = "";
	boolean gameType = false;
	char [][] gameboard = new char[4][4];
		for(int i = 0; i<args.length; i++) 								//check comcountd line arguments for what parameters are to be used
		{
			if(args[i].equals("-d"))
			{
					if(args[i+1].equals("simple"))
					{
						gameType = false;
					}
					if(args[i+1].equals("dlb"))
					{
						gameType = true;
					}
			}
			if(args[i].equals("-b"))
			{
				gameboard = loadBoard(args[i+1]);
			}
			
		}
		printBoard(gameboard);		
		
		for(int i =0; dict.hasNextLine(); i++)
		{
				temp = dict.nextLine();
				putWord(gameType, temp, simple, dic);
		}
		BooleanHolder B =  new BooleanHolder(false);
		Collection<String> list;
													//Below dtermines whether to play the game using simple dictionary or dlb
		if(!gameType)
		{
			String current = simple.getWord();
			
			while(!(current.equals("zzz")))
			{
				check(current, gameboard, gameType, simple, dic, B);
				if(B.getVal())
				{
					smee.add(current);
				}	
			}	
		}
		else
		{
			list = dic.keys();
		
		int tempy = 0;

		for (String theWord : list) 
		{
			check(theWord, gameboard, gameType,simple, dic, B);
			if(B.getVal())
				solved.put(theWord);
			
			tempy++;
		}
		}
														//start of game
		Scanner scantron  = new Scanner(System.in);
		System.out.println("Welcome to boggle!");
		System.out.println("* is a wildcard character and can be used as any letter forming a valid word");
		System.out.println("Enter the words you find and when you are done type \"*\" and press enter");
		ArrayList<String> guess = new ArrayList<String>();
		String h = "";
			while(!h.equals("*"))
			{
				h = scantron.next();
				if(containsWord(gameType, h, simple, dic))
				{
				System.out.println("---correct word---");
				guess.add(h);
				}
				else
				{
				System.out.println("---incorrect word---");
				}
			}
		System.out.println("Here are the correct words you guessed");	
		int count = 0;	
		for(String theWord: guess)
		{
			System.out.println(theWord);
			++count;
		}
		System.out.println("\nYou guessed " + count + " words");
		int correctword = 0;
		ArrayList<String> correct = new ArrayList<String>();
		for(String theWord: guess)
		{
			if(containsWord(gameType, theWord, smee, solved))
			{
			correctword++;
			correct.add(theWord);
			}	
		}
		System.out.println("You guessed " + correctword + "correctly and here they are:");	
		for(String theWord: correct)	
		{	
			System.out.println(theWord);
		}
		
	}
	
					//searching algorithm
		public static void search(char[][] board, int r, int c, int l, String word, Stack stack, BooleanHolder B)
		{
		if(stack.magicString(board).equals(word))
		{
			
			B.setBoo(true);
			stack.pop();
			return;
		} else {
				++l;
		}
			char temp = word.charAt(l);
		
		
		if ( (r > 0) && !B.getVal() ) 
		{
			if(((temp == (board[r-1][c]) || '*' == (board[r-1][c]))) && (!(stack.contains(r-1, c))))
			{
				stack.push(r-1,c);
				search(board, r-1, c, l, word, stack, B);
			}
		}
			
		if ( (r > 0) &&  (c < board.length-1) && !B.getVal() )
		{
			if(((temp == (board[r-1][c+1]) || '*' == (board[r-1][c+1]))) && (!(stack.contains(r-1, c+1))))
			{
				stack.push(r-1,c+1);
				search(board, r-1, c+1,l, word, stack, B);
			}
		}
	
		if ((c < board.length-1) && !B.getVal() ) 
		{
			if(((temp == (board[r][c+1]) || '*' == (board[r][c+1]))) && (!(stack.contains(r, c+1))))
			{
				
				stack.push(r,c+1);
				search(board, r, c+1, l, word, stack, B);
			}
		}
	
		if ( (c < board.length-1) && (r < board.length-1) && (!(stack.contains(r+1, c+1))) && !B.getVal())
		{
			if((temp == (board[r+1][c+1])) || '*' == board[r+1][c+1])
			{
				stack.push(r+1,c+1);
				search(board, r+1, c+1, l, word, stack, B);
			}
		}
		
		if ((r < board.length-1) && !B.getVal() )
		{
			if((((temp == (board[r+1][c]) || '*' == (board[r+1][c]))) && (!(stack.contains(r+1, c)))))
			{
				stack.push(r+1,c);
				search(board, r+1, c, l, word, stack, B);
			}	
		}
		
		if ((r < board.length-1) && (c > 0) && !B.getVal() )
		{
			if((((temp == (board[r+1][c-1]) || '*' == (board[r+1][c-1]))) && (!(stack.contains(r+1, c-1)))))
			{
				stack.push(r+1,c-1);
				search(board, r+1, c-1, l, word, stack, B);
			}
		
		}
		
		if ( (c > 0) && !B.getVal() )
		{
			if((((temp == (board[r][c-1]) || '*' == (board[r][c-1]))) && (!(stack.contains(r, c-1)))))
			{
				stack.push(r,c-1);
				search(board, r, c-1, l, word, stack, B);
			}	
		}
		
		if ((r > 0) && (c > 0) && !B.getVal())
		{
			if((((temp == (board[r-1][c-1]) || '*' == (board[r-1][c-1]))) && (!(stack.contains(r-1, c-1)))))
			{
			stack.push(r-1,c-1);
			search(board, r-1, c-1, l, word, stack, B);
			}
		}
		
		stack.pop();	
	}
		private static boolean containsWord(boolean gameType, String word, SimpleDictionary simple, Trie dic)
	{
		if(!gameType)
		{
			return (simple.search(new StringBuilder(word)) == 2);
		}
		else
		{
			return (dic.contains(word));
		}
	}
	
	private static void putWord(boolean gameType, String word, SimpleDictionary simple, Trie dic)
	{
		if(!gameType)
		{
			if(simple == null)
				simple = new SimpleDictionary();
			else	
				simple.add(word);
		}
		if(gameType)
		{
			if(dic == null)
				dic = new Trie();
			else
				dic.put(word);
		}
	}
		public static void check(String s, char[][] board, boolean gameType, SimpleDictionary simple, Trie dic, BooleanHolder B)
		{
		B.setBoo(false);
		for(int i = 0; i < board.length; i++)
		{
			for(int j =0; j<board.length; j++)
			{
				B.setBoo(false);
				if(s.charAt(0) ==(board[i][j]))
				{
					Stack stack = new Stack(i, j);
					search(board, i , j, board[i].length-1, s, stack , B);
					
					if((!(containsWord(gameType,s, simple, dic))  && B.getVal()))
					{
								putWord(gameType, s, simple, dic);
								break;
					}
				
				}
				
			}
		}
	
	}
	
	
	

	private static char[][] loadBoard( String infileName) throws Exception
    {
        Scanner scanmaster2000 = new Scanner( new File(infileName) );
        int rows=4;
        int cols = rows;  		// ASSUME A SQUARE GRID
        char[][] board = new char[rows][cols];
		String strang = scanmaster2000.nextLine();
		
		int h = 0;
        for(int r = 0; r < rows ; r++)
        	for(int c = 0; c < cols; c++)
				{
                 board[r][c] = strang.charAt(h); 
                 board[r][c] = strang.charAt(h); 
				h++;
				 }
        scanmaster2000.close();
        return board;
    }
	
	private static void printBoard(char[][] theBoard )
    {
        
        System.out.print( "\n   ");
        for(int c = 0; c < theBoard.length; c++)
        	System.out.print("- ");
       System.out.print( "\n");

        for(int r = 0; r < theBoard.length; r++)
        {	System.out.print("| ");
            for(int c = 0; c < theBoard[r].length; c++)
                 System.out.print( theBoard[r][c] + " ");
            System.out.println("|");
        }
        System.out.print( "   ");
        for(int c = 0; c < theBoard.length; c++)
        	System.out.print("- ");
       System.out.print( "\n");
    }
public static class LE
{
  private int row, column;
  private LE next;

  public LE()
  {
    this( 0, 0, null );
  }

  public LE(int r, int c)
  {
    this( r, c, null );
  }

  public LE(int r, int c, LE next)
  {
    setRow( r );
	setColumn( c );
    setNext( next );
  }

  public int getRow()
  {
    return row;
  }
  
  public int getColumn()
  {
	return column;
  }

  public LE getNext()
  {
    return next;
  }

  public void setRow(int row)
  {
     this.row = row;
  }
  
  public void setColumn(int column)
  {
	this.column = column;
  }
  
  public void setNext(LE next)
  {
    this.next = next;
  }
  
  public String getData()
	{
		return ("[" +row +","+ column + "]"); 
	}
} //EOF


public static class Stack
{
	private LE stack;

	
	public Stack( int r, int c)
	{
		stack = new LE( r, c );
	}
	public void push( int r, int c )
	{ 
		stack = new LE( r , c, stack );
	}

   
	public void pop()
	{
		stack = stack.getNext();
	}

 
	public String front()
	{
		return ("[" + stack.getRow() +","+ stack.getColumn() + "]"); 
	}
	
	public boolean contains(int r , int c)
	{
		return containsH(r,c,stack);
	}
	
	private boolean containsH(int r, int c, LE stack)
	{
		if(stack == null) 
		return false;	
				
		if((r == stack.getRow() && c == stack.getColumn()))
		return true;
		
		return containsH(r,c,stack.getNext());
		
		
	
	}
  
	public boolean empty()
	{
		if (stack == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
		public String magicString(char[][] board)
	{
		String s= "";
		s= toStringHelp(stack,s, board);
		return s;
	}
	
	private String toStringHelp(LE stack, String s, char[][]board)
	{
	
	if(stack!=null)
	{
	String cha;
	cha = toStringHelp(stack.getNext(), s,board)+ board[stack.getRow()][stack.getColumn()];
	s = cha + s;
	}
	return s;
	}
}
	public static class BHP
	{
		private boolean b;
	
		public BHP(boolean bo)
		{
		b = bo;
		}
	
		public boolean getB()
		{
		return b;
		}
	
		public void setB(boolean bo)
		{
		b = bo;	
		}
	}
	
	public static class BooleanHolder
	{
		private BHP b;
		
		public BooleanHolder(boolean bo)
		{
			b = new BHP(bo);
		}
		
		public boolean getVal()
		{
			return b.getB();
		}
		
		public void setBoo(boolean bo)
		{
			b.setB(bo);
		}

	}
}	

