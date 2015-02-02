import java.util.*;
public class Trie
{
private class Node
{
	private char ch;
	private Node D;
	private Node R;
	private Node(char c, Node first, Node second)
	{
		ch = c;
		D = first;
		R = second;
	}
	private Node(char c)
	{
		ch = c;
		Node D= null;
		Node R = null;
	}
	private Node()
	{
	this('#', null, null);
	}		
}
		private Node root;
		private int n;
		public Trie()
		{
		root = null;
		n= 0;
		}	
	public void put(String key) 
	{
        root = put(root, key, 0);
    }

    private Node put(Node x, String key, int d) 
	{
        if (x == null && d < key.length()) 
			{
			x = new Node(key.charAt(d));
			}
			
			
		if (d == key.length())
		{
			
				n++;
			x = new Node('$');	
			
			return x;
		}
		if(x.ch != key.charAt(d))
		{
			x.R = put(x.R, key, d);
		}
		else
		{
        char c = key.charAt(d);
        x.D = put(x.D, key, d+1);
		}
        return x;
	}	
	
		
		
		public boolean contains(String key)
		{
			return get(key) == '$';
		}
		
		public char get(String key) 
		{
        Node x = get(root, key, 0);
			if (x == null) 
				return '#';
        return x.ch;
		}
	
		private Node get(Node x, String key, int d) 
		{
        if (x == null) 
			return null;
        if (d == key.length() && x.D.ch == '$') 
			return x;
        char c = key.charAt(d);
		while(x.ch != c)
		{
		x= x.R;
		
		if(x.ch == '$' || x == null)
			return new Node('!');
		}
			
		
        return get(x.D, key, d+1);
		}
		
		public Collection<String> keys() {
        return keysHelper("");
    }

		public Collection<String> keysHelper(String prefix) 
		{
        HashSet<String> listy = new HashSet<String>();
        Node x = get(root, prefix, 0);
        collect(x, new StringBuilder(prefix), listy);
        return listy;
		}
		
		private void collect(Node x, StringBuilder prefix, HashSet<String> listy) {
			if (x == null) 
				return;
			if (x.ch == '$') 
				{
				listy.add(prefix.toString());
				}
					prefix.append(x.ch);
					if(x.D != null)
						collect(x.D, prefix, listy);
					prefix.deleteCharAt(prefix.length() - 1);
					
					if(x.R != null)
						collect(x.R, prefix, listy);
		}	
}	
