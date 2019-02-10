//Surabhi S Nath
//2016271

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Reader
{
  static BufferedReader reader;
  static StringTokenizer tokenizer;

  static void init(InputStream input) 
  {
      reader = new BufferedReader(
                   new InputStreamReader(input) );
      tokenizer = new StringTokenizer("");
  }

  static String next() throws IOException 
  {
      while ( ! tokenizer.hasMoreTokens() ) 
      {
          tokenizer = new StringTokenizer(
                 reader.readLine() );
      }
      return tokenizer.nextToken();
  }

  static int nextInt() throws IOException 
  {
      return Integer.parseInt( next() );
  }
	
  static double nextDouble() throws IOException 
  {
      return Double.parseDouble( next() );
  }

}

public class bellman 
{

	public int findmax(String st)
	{
		int max = (int)st.charAt(0)-48;

		for(int i = 1; i < st.length() - 1; i++)
		{
			if(st.charAt(i-1) == ':' || st.charAt(i+1) == ':')
			{
				if((int)st.charAt(i)-48>max)
					max = (int)st.charAt(i)-48;
			}
		}
		
		return max;
	}
	
	public void printdistveci(int[][] dv, int ind)
	{
		for(int i = 0; i<dv.length; i++)
		{
			if(dv[ind][i] == Integer.MAX_VALUE)
				System.out.print("Infinity ");
			else
				System.out.print(dv[ind][i]+" ");
		}
		
		System.out.println();
	}
	
	
	public static void main(String[] args) throws IOException
	{
		Reader.init(System.in);
		bellman obj = new bellman();
		
		String line = Reader.next();
		String[] info = line.split(";");
		int max = obj.findmax(line);
		int[][] grid = new int[max][max];		
		int[][] distancevectors = new int[max][max];
		int[][] copy = new int[max][max];
		int[][] fwtable = new int[max][max];
		
		for(int i = 0; i<max; i++)
		{
			for(int j=0; j<max; j++)
			{
				if(i==j)
					distancevectors[i][j] = 0;
				else
					distancevectors[i][j] = Integer.MAX_VALUE;
			}
		}
		
		for(int i = 0; i < info.length; i++)
		{
			String two[] = info[i].split(",");
			int weight = Integer.parseInt(two[1]);
			String srcdest[] = two[0].split(":");
			int src = Integer.parseInt(srcdest[0]);
			int dest = Integer.parseInt(srcdest[1]);
			grid[src-1][dest-1] = weight;
			grid[dest-1][src-1] = weight;
			distancevectors[src-1][dest-1] = weight;
			distancevectors[dest-1][src-1] = weight;
			fwtable[src-1][dest-1] = dest-1+1;
			fwtable[dest-1][src-1] = src-1+1;
		}
		
		
//		for(int i = 0; i<max; i++)
//		{
//			for(int j = 0; j<max; j++)
//			{
//				System.out.print(fwtable[i][j]+" ");
//			}
//			
//			System.out.println();
//		}

		int change = 1;
		
		while(change == 1)
		{	
			for(int c = 0; c<max; c++)
			{
				for(int d = 0; d<max; d++)
				{
					copy[c][d] = distancevectors[c][d];					
				}
			}

			
			//----------------------------
			for(int i = 0; i<max; i++)
			{
				System.out.println("Node "+(i+1));
				obj.printdistveci(distancevectors, i);
				
				for(int j = 0; j<max; j++)
				{
					if(i==j)
						continue;
					
					int min = Integer.MAX_VALUE;
					
					int save = j;
					for(int k = 0; k<max; k++)
					{
//						if(k==j)
//							continue;
						
						if(grid[i][k]!=0)
						{
							if(distancevectors[k][j] != Integer.MAX_VALUE)
							{
								if(min>grid[i][k] + distancevectors[k][j])
									save = k+1;									
								min = Math.min(min, grid[i][k] + distancevectors[k][j]);
							}
						}
					}
					
					if(min != Integer.MAX_VALUE)
					{
						distancevectors[i][j] = min;
						fwtable[i][j] = save;
					}
					
					obj.printdistveci(distancevectors, i);
				}
			}
			//-----------------------------
			
//			System.out.println("COPY ie ORIGINAL");
//			for(int u = 0; u < max; u++)
//			{
//				for(int v = 0; v<max; v++)
//				{
//					System.out.print(copy[u][v]+" ");
//				}
//				System.out.println();
//			}
//			
//			System.out.println("DV ie NEW");
//			for(int u = 0; u < max; u++)
//			{
//				for(int v = 0; v<max; v++)
//				{
//					System.out.print(distancevectors[u][v]+" ");
//				}
//				System.out.println();
//			}
			
			change = 0;
			boolean flag = false;
			for(int a = 0; a<max; a++)
			{
				for(int b = 0; b<max; b++)
				{
					if(copy[a][b]!=distancevectors[a][b])
					{
						change = 1;
						flag = true;
						break;
					}	
				}
				
				if(flag == true)
					break;
			}
			
			if(change == 0)
			{
				System.out.println("Converged");
				System.out.println("Forwarding table: ");
				for(int y = 0; y<max; y++)
				{
					for(int z = 0; z<max; z++)
					{
						System.out.print(fwtable[y][z]+" ");
					}
					
					System.out.println();
				}
			}
			
			System.out.println("Any weight change to be made? Y/N/Stop");
			String ans = Reader.next();
			
			if(ans.equals("Stop"))
				break;
			
			if(ans.equals("Y"))
			{
				change = 1;
				System.out.print("Src node: ");
				int s = Reader.nextInt();
				System.out.print("Dest node: ");
				int d = Reader.nextInt();
				System.out.print("New weight: ");
				int w = Reader.nextInt();
				grid[s-1][d-1] = w;
				grid[d-1][s-1] = w;
			}
		}
	}

}
