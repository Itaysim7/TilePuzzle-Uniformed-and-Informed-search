import java.util.HashMap;

public class DFID implements SearchAlgorithm
{
	private  int countVertices=1;
	private vertex start;
	public DFID(vertex s)
	{
		start=s;
	}
	@Override	
	/**
   * The function will run dfid algorithm to find the goal node
   * @param start-the node start
   */
  public vertex doAlgo() 
  {
	  boolean find=false;
	  vertex result=null;
	  for(int i=1;i<10000&&!find;i++)
	  {
		  HashMap<String, vertex> verString= new HashMap<String, vertex>();
		  result=dfs(start,i,verString);
		  if(result!=null) 
			  find=true;
	  }
	  if(result!=null&&result.getCost()>=0)
		  return result;
	  return null;
  }
 
	public int getCountVertices() 
	{
		return countVertices;
	}
	
	/**
	 * The function will run dfs limited algorithm to find the goal node
	 * @param current- current node 
	 * @param depth- the level of the search
	 * @param h- hash map that save the open list 
	 */
	private  vertex dfs(vertex current,int depth,HashMap<String, vertex> h) 
	{
		if(current.b.isAns())
			return current;
		if(depth==0)
			return null;
		h.put(current.b.uniqeString(),current);
		boolean isCutOff=false;
		move lastStep=current.getLastStep();
		int row=current.b.getRow();int col=current.b.getCol();
		//for each legal operator of start
		//operator left
		if(lastStep!=move.Left&&col<current.b.mat[0].length-1&&current.b.mat[row][col+1].getColor()!=0) 
		{
			countVertices++;
			Board matLeft=current.b.createLeft();
			if(!h.containsKey(matLeft.uniqeString()))
			{
				vertex left=new vertex(matLeft,current.getCost()+current.b.mat[row][col+1].getColor(),move.Right,current.getPath()+""+matLeft.mat[row][col].getNum()+"L-");
				vertex result=dfs(left,depth-1,h);
				if(result==null)
					isCutOff=true;
				else
					if(result.getCost()>=0)
						return result;
			}
		}
		//operator up
		if(lastStep!=move.Up&&row<current.b.mat.length-1&&current.b.mat[row+1][col].getColor()!=0) 
		{
			countVertices++;
			Board matUp=current.b.createUp();
			if(!h.containsKey(matUp.uniqeString()))
			{
				vertex up=new vertex(matUp,current.getCost()+current.b.mat[row+1][col].getColor(),move.Down,current.getPath()+""+matUp.mat[row][col].getNum()+"U-");
				vertex result=dfs(up,depth-1,h);
				if(result==null)
					isCutOff=true;
				else
					if(result.getCost()>=0)
						return result;
			}
		}
		//operator right
		if(lastStep!=move.Right&&col>0&&current.b.mat[row][col-1].getColor()!=0) 
		{
			countVertices++;
			Board matRight=current.b.createRight();
			if(!h.containsKey(matRight.uniqeString()))
			{
				vertex right=new vertex(matRight,current.getCost()+current.b.mat[row][col-1].getColor(),move.Left,current.getPath()+""+matRight.mat[row][col].getNum()+"R-");
				vertex result=dfs(right,depth-1,h);
				if(result==null)
					isCutOff=true;
				else
					if(result.getCost()>=0)
						return result;
			}
		}
		//operator down
		if(lastStep!=move.Down&&row>0&&current.b.mat[row-1][col].getColor()!=0) 
		{
			countVertices++;
			Board matDown=current.b.createDown();
			if(!h.containsKey(matDown.uniqeString()))
			{
				vertex down=new vertex(matDown,current.getCost()+current.b.mat[row-1][col].getColor(),move.Up,current.getPath()+""+matDown.mat[row][col].getNum()+"D-");
				vertex result=dfs(down,depth-1,h);
				if(result==null)
					isCutOff=true;
				else
					if(result.getCost()>=0)
						return result;
			}
		}
		h.remove(current.b.uniqeString());
		if(isCutOff)
			return null;
		return new vertex(current.b,-1,current.getLastStep(),current.getPath());
	}

}
