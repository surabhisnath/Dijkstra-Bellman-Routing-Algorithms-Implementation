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

  /** call this method to initialize reader for InputStream */
  static void init(InputStream input) 
  {
      reader = new BufferedReader(
                   new InputStreamReader(input) );
      tokenizer = new StringTokenizer("");
  }

  /** get next word */
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


public class dijkstra 
{
	String[][] forwtable;
	int[][] parentarr;
	
	int minDistance(int dist[], Boolean sptSet[], int V) 
    {
        int min = Integer.MAX_VALUE, min_index=-1; 
  
        for (int v = 0; v < V; v++) 
            if (sptSet[v] == false && dist[v] <= min) 
            { 
                min = dist[v]; 
                min_index = v; 
            } 
  
        return min_index; 
    } 
	
	void findpath(int graph[][], int src, int V) 
    { 
        int dist[] = new int[V];
        Boolean sptSet[] = new Boolean[V]; 

        for (int i = 0; i < V; i++) 
        { 
            dist[i] = Integer.MAX_VALUE; 
            sptSet[i] = false; 
        } 
  
        int[] parent = new int[V];
        
        dist[src] = 0;
        parent[src] = -1;
        
        printdist(dist);

        for (int count = 0; count < V-1; count++) 
        { 
        	int u = minDistance(dist, sptSet, V); 
            sptSet[u] = true;
            for (int v = 0; v < V; v++) 
            {
                if (!sptSet[v] && graph[u][v]!=0 && dist[u] != Integer.MAX_VALUE && dist[u]+graph[u][v] < dist[v]) 
                {
                	parent[v] = u;
                	dist[v] = dist[u] + graph[u][v]; 
                }
                
                if(count == V-2 && v == V-1)
                	printdist_fwdtble(dist,src);
                else
                	printdist(dist);
            }	
        }
        
        for(int l = 0; l<V; l++)
        	parentarr[src][l] = parent[l];
    }
	
	public void printdist(int[] dist)
	{
		for(int i = 0; i<dist.length; i++)
		{
			if(dist[i] == Integer.MAX_VALUE)
			{
				System.out.print("Infinity ");
			}
				
			else
				System.out.print(dist[i] + " ");
		}
		
		System.out.println();
	}
	
	public void printdist_fwdtble(int[] dist, int ind)
	{
		
		
		for(int i = 0; i<dist.length; i++)
		{
			
			if(dist[i] == Integer.MAX_VALUE)
			{
				forwtable[ind][i] = "Infinity";
				 
				System.out.print("Infinity ");
			}
				
			else
			{
				forwtable[ind][i] = dist[i]+"";
				
				System.out.print(dist[i] + " ");
			}	
		}
		
		System.out.println();
	}
	
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

	public static void main(String[] args) throws IOException
	{
		Reader.init(System.in);
		dijkstra obj = new dijkstra();
		String line = Reader.next();
		String[] info = line.split(";");
		int max = obj.findmax(line);
		int[][] grid = new int[max][max];
		obj.forwtable = new String[max][max];
		obj.parentarr = new int[max][max];
		
		for(int i = 0; i < info.length; i++)
		{
			String two[] = info[i].split(",");
			int weight = Integer.parseInt(two[1]);
			String srcdest[] = two[0].split(":");
			int src = Integer.parseInt(srcdest[0]);
			int dest = Integer.parseInt(srcdest[1]);
			grid[src-1][dest-1] = weight;
			grid[dest-1][src-1] = weight;
		}		
		
//		for(int i = 0; i<max; i++)
//		{
//			for(int j = 0; j<max; j++)
//			{
//				System.out.print(grid[i][j]+" ");
//			}
//			
//			System.out.println();
//		}
		
		for(int i = 1; i<=max; i++)
		{
			System.out.println("Node "+i);
			obj.findpath(grid,i-1,max);
			System.out.println();
		}
		
		//SHORTEST DISTANCES
		System.out.println("Shortest distances: ");
		for(int a = 0; a<max; a++)
		{
			for(int b = 0; b<max; b++)
			{
				System.out.print(obj.forwtable[a][b]+" ");
			}
			
			System.out.println();
		}
		
		System.out.println();
		
		//FORWARDING TABLE
		System.out.println("Forwarding Table: ");
		for(int a = 0; a<max; a++)
		{
			for(int b = 0; b<max; b++)
			{
				if(obj.parentarr[b][a]!=-1)
					System.out.print((obj.parentarr[b][a]+1)+" ");
				else
					System.out.print(obj.parentarr[b][a]+" ");
			}
		
			System.out.println();
		}
	}
}
