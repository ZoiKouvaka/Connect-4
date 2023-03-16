//Vasiliki Katogianni AM 4696 Zoi Kouvaka AM 4706 Ioannis Tsironis AM 4908 
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
public class Grid//klasi pou afora ton pinaka
{
	private Cell[][] maze;//pedio gia na pairnoume ena cell tou grid
	private int n=0; //arithmos stilon
	private int m=0;//arithmos grammon
	private int value=0;
	private int numofchildren;
	private int k=0;//arithmos sinexomenon X/O
	private boolean minormax=false;//false min,true max
	private int minmax=-1;//if  max wins i axia einai 1000, if min wins i axia einai -1000 , if no one wins i axia einai -1
	private List<Grid> children = new ArrayList<Grid>(); //lista me ta paidia tou keliou
	private Grid parent=null;//goneas tou keliou
    private boolean winning=false; //an nikise kaneis
	public Grid(int m,int n,int k)//constructor
	{
		this.m=m;
		this.n=n;
		this.k=k;
		this.maze=this.generateMaze(m,n);//dimiourgoume ton pinaka
        this.buildNeighbors();//dimiourgoume tous geitones
	}

	public Grid copy()//ftiaxnoume ena antigrafo tou pinaka
	{
		Grid copy=new Grid(m,n,k);
    	for (int i=0;i<m;i++)
        {
            for(int j=0;j<n;j++)
            {
                if(this.getMaze()[i][j].isX())
                {
                    copy.getMaze()[i][j].checkWithX();
                }
                else if(this.getMaze()[i][j].isO())
                {
                    copy.getMaze()[i][j].checkWithO();
                }
                else
                {
                    ; 
                }
            }
    	}
        return copy;
    }

	public Cell[][] getMaze()//accessor method gia na paroume to pedio maze
	{
		return maze;
	}

	public void setMaze(Cell[][] maze)//mutator methodos pou allazoume ena pinaka
	{
		this.maze=maze;
	}

	public Cell[][] generateMaze(int M,int N) {//dimiourgoume ton pinaka
        Cell[][] maze = new Cell[M][N];
        for(int i=0; i < N; i++) {
            for(int j=0; j < N; j++) {
                maze[i][j] = new Cell(i,j);
            }
        }
        return maze;
    }
    public List<Cell> findNeighbors(Cell cell) {//vriskei tous geitones enos keliou sosta
        List<Cell> neighbors = new ArrayList<>();
        int[] position = cell.getPosition();
            if((position[0]-1) >= 0) {
                neighbors.add(maze[position[0]-1][position[1]]);
            }
            if ((position[0]+1) < m) {
                neighbors.add(maze[position[0]+1][position[1]]);
            }
            if ((position[1]-1) >= 0 && (position[0]-1) >= 0) {
                neighbors.add(maze[position[0]-1][position[1]-1]);
            }
            if((position[1]+1) < n) {
                neighbors.add(maze[position[0]][position[1]+1]);
            }
            if((position[1]-1) >= 0) {
                neighbors.add(maze[position[0]][position[1]-1]);
            }
            if((position[0]+1) < m && (position[1] +1) < n) {
                neighbors.add(maze[position[0]+1][position[1]+1]);
            }
            if((position[0]+1) < m && (position[1]-1) >= 0) {
                neighbors.add(maze[position[0]+1][position[1]-1]);
            }
            if((position[0]-1) >= 0 && (position[1]+1) < n) {
                neighbors.add(maze[position[0]-1][position[1]+1]);
            }
        return neighbors;
    }

    public void buildNeighbors() //vriskei tous geitones 
    {
    	List<Cell> temp = new ArrayList<>();
    	for (int i =0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                temp=findNeighbors(maze[i][j]);
                for(Cell neighbor:temp)
                {
                	maze[i][j].getNumKeyNeighborsMap().put(maze[i][j].kindOfNeighbor(neighbor),neighbor);
                	maze[i][j].getCellKeyNeighborsMap().put(neighbor,maze[i][j].kindOfNeighbor(neighbor));
                }
            }
        }
    }
    public Cell getCell(int x, int y) {//epistrefei ena keli
        return maze[x][y];
    }

    public String toString() { //tiponei ton pinaka me tis theseis tou kathe keliou
        String stringMaze = "";
        for(int i=m-1; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                stringMaze += maze[i][j].toString()+" ";
            }
            stringMaze += "\n";
        }
        return stringMaze;
    }
		public String gridstring() {//tiponei ton pinaka me tis theseis kathe keliou
        String stringMaze = "";
        for(int i=m-1; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                stringMaze += maze[i][j].toString1()+" ";
            }
            stringMaze += "\n";
        }
        return stringMaze;
    }	
	public String graphicToString(Cell cell,boolean Max) { //tiponei ton pinaka me X/O antistoixa analoga me to keli pou tou dinoume
        String stringMaze = "";
        for(int i=0; i < m; i++) {
            for (int j = 0; j < n; j++) {
				if(cell==null){
					stringMaze +=  maze[i][j].toString()+" ";
				}else{
					if(cell!=maze[i][j]){
						stringMaze +=  maze[i][j].toString()+" ";
					}else{
						if(Max==true){
							stringMaze +=  maze[i][j].toString()+"O";
						}else{
							stringMaze +=  maze[i][j].toString()+"X";	
						}
					}
				}	
            }
            stringMaze += "\n";
        }
        return stringMaze;
    }

    public List<Grid> getChildren()//accessor method gia na paroume to pedio children
    {
    	return children;
    }

    public void checkIfWin(Cell playedCell,boolean isX)//if max plays then X (true), if min plays then Y(false),vazei axia sto grid analoga me to poios kai an nika
    {
    	int N=k;
    	List<Cell> sameNeighborList= new ArrayList<Cell>();
    	for(Cell neighbor:playedCell.getCellKeyNeighborsMap().keySet())
    	{
            if (isX && neighbor.isX())
    		{
    			sameNeighborList.add(neighbor);//prosthetoume stin lista ton geitona
    		}
    		else if(!isX && neighbor.isO())//an to keli kai o geitonas tou exoun O
    		{
    			sameNeighborList.add(neighbor);//prosthetoume stin lista ton geitona
    		}
    		else
    		{
    			continue;//alios pame ston epomeno geitona
    		}
    	}

    	for(Cell neighbor:sameNeighborList)// gia kathe geitona pou vrisketai stin lista
    	{
    		if (neighbor.isChecked())//an den exei X/O o geitonas
    		{
    			if(isX && neighbor.isO() || !isX && neighbor.isX())//an to keli exei X kai o geiotnas O opos kai to antistrofo
    			{
    				continue;//pame ston epomeno geitona
    			}
    		}
    		else
    		{
    			continue;//pame ston epomeno geitona
    		}
    		int countToN=0;//metritis gia na metra ta sinexomena kelia pou exoun X/O
    		int neighborCode=playedCell.getCode(neighbor);//to kleidi tou geitona
    		Cell currentCell=playedCell;//poio keli koitame kathe fora
            while (currentCell.getNeighborFromCode(9-neighborCode)!=null)//we get the symmetrical neighbor of what the same we have (9's complement)
            {
                if(currentCell.getNeighborFromCode(9-neighborCode).isChecked() && currentCell.getNeighborFromCode(9-neighborCode).whatIs()==isX)
                {
                    currentCell=currentCell.getNeighborFromCode(9-neighborCode);
                }
                else
                {
                    break;
                }
            }
    		while((currentCell.getNeighborFromCode(neighborCode)!=null) && (countToN<(N-1)))
    		{
                if(currentCell.getNeighborFromCode(neighborCode).whatIs()==isX)
    			{
                    countToN++;
                    currentCell=currentCell.getNumKeyNeighborsMap().get(neighborCode);// go to neighbor to check same-kind neighbors

                }
                else
                {
                    break;
                }
            }
    		if (countToN==N-1)//we found N same cells so we have a winner
    		{
                if(currentCell.isX())//periptosi pou nika o X
    			{
    				winning=true;
                    minmax= 1000;
                    return ;     			}
    			else if (currentCell.isO())//periptosi pou nika o X
    			{
    				winning=true;
                    minmax= -1000;
                    return ;
    			}
    			else
    			{
                    minmax= -1;
    			}
    		}
    	}
    	minmax= -1;//den nika kaneis
        return ;
    }
    public int getMinmax()//accessor method gia na paroume to pedio minmax
    {
    	return minmax;
    }

    public boolean equals(Grid other)//vriskei an dio grids einai idia
    {
    	for(int i=0;i<n;i++)
    	{
    		for(int j=0;j<m;j++)
    		{
    			if(maze[i][j].isChecked() && other.getMaze()[i][j].isChecked())//are both checked
    			{
    				if (maze[i][j].whatIs()!=other.getMaze()[i][j].whatIs()) //but not containing the same things
    				{
    					return false;
    				}
    			}
    			else if(!(maze[i][j].isChecked() || other.getMaze()[i][j].isChecked()))//both empty
    			{
    				continue;
    			}
    			else //one checked one empty
    			{
    				return false;
    			}
    		}
    	}
    	return true; //same content
    }

    public void setParent(Grid grid)//thetoume ton gonea enos grid
    {
    	parent=grid;
    }

    public Grid getParent()//accessor method gia na paroume to pedio parent
    {
    	return parent;
    }

    public void setMinmax(int n)//thetoume tin axia issi me kapoia alli pou dosame
    {
        minmax=n;
    }
	public boolean getMinOrMax()
    {
        return minormax;
    }
    public boolean getWinning()//accessor method gia na paroume to pedio winning
    {
        return winning;
    }
	public void setMinOrMax(boolean set)
    {
        minormax=set;
    }
    public static void main(String[] args) {//kanoume mia main na doume an douleuei sosta
        Grid grid =new Grid (2,2,2);
        Cell tester=grid.getCell(1,0);
        System.out.println(tester.cordinates());
        Cell tester2=grid.getCell(0,0);
        tester.checkWithX();
        tester2.checkWithO();
        System.out.println(grid);
        grid.checkIfWin(tester,false);

    }
}