//Vasiliki Katogianni AM 4696 Zoi Kouvaka AM 4706 Ioannis Tsironis AM 4908 
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
public class Cell //klasi pou afora to keli 
{
	private int[] position={0,0};//pedio pou periexei tin thesi tou kathe keliou diladi to x kai y axona
	private boolean isChecked=false;//boolean metavliti gia to an ena keli exei X h O
	private boolean isX=false;//boolean metavliti gia to an ena keli exei X
	private boolean isO=false; //boolean metavliti gia to an ena keli exei O
	private HashMap<Integer,Cell> numKeyNeighbors=new HashMap<Integer,Cell>();//kathe geitonas exei ena int kleidi gia na kseroume ti eidous geitonas einai .Parartima sto telos
	private HashMap<Cell,Integer> cellKeyNeighbors=new HashMap<Cell,Integer>();  //lexiko me ton kathe geitona kai to kleidi tou
	private List<Cell> chain;
	public Cell(int x,int y) // constructor
	{
		position[0]=x;//dinei tin timi pou tou dothike os parametros stin orizontia thesi tou keliou 
		position[1]=y;//dinei tin timi pou tou dothike os parametros stin katakorifi thesi tou keliou 
	}
	public void checkWithX()//checkaroume ena keli me X
	{
		isX=true;
		isChecked=true;
	}

	public void checkWithO() //checkaroume ena keli me O
	{
		isO=true;
		isChecked=true;
	}
	public boolean isChecked()//checkaroume ena keli me X/O
	{
		return isChecked;
	}
	public boolean isO()//accessor method gia na paroume to pedio isO
	{
		return isO;
	}
	public boolean isX()//accessor method gia na paroume to pedio isX
	{
		return isX;
	}
	public boolean whatIs()//koitame an ena keli exei X /an exei O
	{
		if(this.isX)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public void addNeighbor(Integer code,Cell neighbor)//prothetoume ton geitona me to kleidi tou
	{
		numKeyNeighbors.put(code,neighbor);
		cellKeyNeighbors.put(neighbor,code);
	}

	public String toString()//tiponoume to X/O
	{
		if(isO)
		{
			return "O ";
		}
		else if(isX)
		{
			return "X ";
		}
		return "_ ";
	}
	public String toString1()//tiponoume tin thesi tou keliou
	{
		return "("+position[0]+","+position[1]+")";
	}
	public void changePosition(int x, int y) {//methodos pou allazoume tin x kai y thesi tou keliou me tis times pou tou dothikan os parametroi
        position[0] = x;
        position[1] = y;
    }

    public int[] getPosition()//accessor method gia na paroume to pedio position
    {
    	return position;
    }

	public int kindOfNeighbor(Cell neighbor)//koita ti eidous geitonas einai autos//code generator
	{
		if(neighbor.getPosition()[0]<position[0] && neighbor.getPosition()[1]>position[1])
		{
			return 1;
		}
		else if(neighbor.getPosition()[0]==position[0] && neighbor.getPosition()[1]>position[1])
		{
			return 2;
		}
		else if(neighbor.getPosition()[0]>position[0] && neighbor.getPosition()[1]>position[1])
		{
			return 3;
		}
		else if(neighbor.getPosition()[0]<position[0] && neighbor.getPosition()[1]==position[1])
		{
			return 4;
		}
		else if(neighbor.getPosition()[0]>position[0] && neighbor.getPosition()[1]==position[1])
		{
			return 5;
		}
		else if(neighbor.getPosition()[0]<position[0] && neighbor.getPosition()[1]<position[1])
		{
			return 6;
		}
		else if(neighbor.getPosition()[0]==position[0] && neighbor.getPosition()[1]<position[1])
		{
			return 7;
		}
		else if(neighbor.getPosition()[0]>position[0] && neighbor.getPosition()[1]<position[1])
		{
			return 8;
		}
		else
		{
			return -1; //sfalma
		}
	}

	public HashMap<Integer,Cell> getNumKeyNeighborsMap()//accessor method gia na paroume to pedio numKeyNeighbors
	{
		return numKeyNeighbors;
	}

	public HashMap<Cell,Integer> getCellKeyNeighborsMap()//accessor method gia na paroume to pedio cellKeyNeighbors
	{
		return cellKeyNeighbors;
	}

	public int getCode(Cell neighbor)//epistrefei to kleidi tou geitona pou theloume
	{
		if(cellKeyNeighbors.get(neighbor)!=null)
		{
			return cellKeyNeighbors.get(neighbor);
		}
		else
		{
			return -1;
		}
	}

	public Cell getNeighborFromCode(int code)//epistrefei ton geitona apo to kleidi pou tou dosame
	{
		if(numKeyNeighbors.get(code)!=null)
		{
			return numKeyNeighbors.get(code);
		}
		else
		{
			return null;
		}
	}

	public String cordinates()//tiponei tin thesi tou keliou
	{
		return "("+position[0]+","+position[1]+")";
	}

}

/*Pararthma

O kathe geitonas exei kledi analoga me to eidos tou gia na gnwrizoume pws tha eksetasoume ta diplana idia kelia
Sxhmatika (opou cur= current cell)

1   2   3
4  cur  5
6   7   8

*/