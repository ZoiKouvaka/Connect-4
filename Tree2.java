//Vasiliki Katogianni AM 4696 Zoi Kouvaka AM 4706 Ioannis Tsironis AM 4908 
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
public class Tree2//klasi pou afora to dentro 
{
	private Grid root; //riza to adeio gridd
	private int n=0; //arithmos stilon
	private int m=0;//arithmos grammon
	private int k=0;//arithmos sinexomenon X/O
	private int depth=0; //vathos tou dentrou
	private boolean minormax=false; //whose turn is to play, false min,true max
	private List<List<Grid>> rowsList=new ArrayList<List<Grid>>();//periexei ola ta grid poy tha ftiaxtoun

	public Tree2(Grid root,int m,int n,int k,boolean minormax)//in constructor minormax is who start the game
	{
		this.root=root;
		this.n=n;
		this.m=m;
		this.k=k;
		this.minormax=minormax;
		generateTree(); //dimiourgeitai to dentro
	}

	public void generateTree()//ftiaxnei to dentro
	{
		Grid currentNode=root;//arxika o komvos pou koitame einai i riza
		List<Grid> rowOfChildren=new ArrayList<Grid>();//looking a row of a min or a max.Contains a row of children
		List<Grid> rowOfNextGeneration=new ArrayList<Grid>();//the next row children
		rowOfChildren.add(root);//vazoume arxika stin lista tin riza
		boolean iamroot=true;//an einai riza
		int counter=0;
		while(true)
		{
			minormax=!minormax;//seira tou max
			for(Grid child:rowOfChildren)//koitame ta paidia tou protou epipedou tou dentrou
			{
				currentNode=child;//thetoume to current iso me to paidi
				boolean topRow=false;//an ftasame stin pio pano seira
				if(currentNode.getWinning())//if someone winning the node will have no children
				{
					continue;
				}
				for(int i=0;i<n;i++)//it travels through columns
				{
					counter=0;
					topRow=false;
					while(currentNode.getMaze()[counter][i].isChecked()) //an ftasoume stin pano seira
					{
						counter++;
						if (counter==m)
						{
							
							topRow=true;
							break; //stamata
						}
					}
					if(topRow) //alios proxora
					{
						continue;
					}
					if(minormax)//if min plays
					{
						Grid copyOfCurrent=currentNode.copy();//ftiaxnoume ena antigrafo tou grid pou koitame
						copyOfCurrent.getMaze()[counter][i].checkWithO();//tsekarei to keli me O sto antigrafo grid
						copyOfCurrent.checkIfWin(copyOfCurrent.getCell(counter,i),!minormax);//tou dinei axia kai vlepei an nika
						copyOfCurrent.setParent(currentNode);//thetoume to curent iso me ton gonea tou 
						copyOfCurrent.setMinOrMax(minormax);
						currentNode.getChildren().add(copyOfCurrent); //vazei stin lista me ta paidia to antigrafo grid
						rowOfNextGeneration.add(copyOfCurrent);//stin epomeni genia prosthetoume to antigrafo grid
					}
					else //if max plays
					{
						Grid copyOfCurrent=currentNode.copy();//ftiaxnoume ena antigrafo tou grid pou koitame
						copyOfCurrent.getMaze()[counter][i].checkWithX();//tsekarei to keli me X sto antigrafo grid
						copyOfCurrent.checkIfWin(copyOfCurrent.getCell(counter,i),!minormax);//tou dinei axia kai vlepei an nika
						copyOfCurrent.setParent(currentNode);//thetoume to curent iso me ton gonea tou 
						copyOfCurrent.setMinOrMax(minormax);
						currentNode.getChildren().add(copyOfCurrent);//vazei stin lista me ta paidia to antigrafo grid
						rowOfNextGeneration.add(copyOfCurrent);//stin epomeni genia prosthetoume to antigrafo grid

					}
				}
			}
			rowOfChildren.clear();//adeiazoume tin lista me ta paidia
			for(Grid element:rowOfNextGeneration)//gia kathe paidi tis listas me tin epomeni genia paidion
			{
				rowOfChildren.add(element);//to prosthetoume stin lista me ta paidia
			}
			rowOfNextGeneration.clear();//adeiazoume tin lista
			if (rowOfChildren.isEmpty())//an den exei tipota stamato
			{
				break;
			}
			else
			{
				List<Grid> temp=new ArrayList<Grid>(); //ftiaxno mia prosorini lista
				for(Grid elemen:rowOfChildren)//gia kathe paidi
				{
					temp.add(elemen);//to vazo stin prosorini lista
				}
				rowsList.add(temp);//vazoume ta paidia me tin axia tous se mia lista
			}
		}
	}

	public void giveMinimaxValue(boolean minimax)
	{
		Grid curNode=rowsList.get(rowsList.size()-1).get(0);
		int count=1;
		List<Integer> minmaxList=new ArrayList<Integer>();
		Grid parent=null;
		while(curNode.getParent()!=root)
		{
			for(Grid child:rowsList.get(rowsList.size()-count))
			{
				if(parent==child.getParent())
				{
					continue;
				}
				for (Grid chil: child.getParent().getChildren())
				{
					minmaxList.add(chil.getMinmax());
				}
				if(child.getParent().getMinOrMax())
				{
					child.getParent().setMinmax(maximum(minmaxList));
					curNode=child.getParent();
					parent=child.getParent();
				}
				else
				{
					child.getParent().setMinmax(minimum(minmaxList));
					curNode=child.getParent();
					parent=child.getParent();
				}
				minmaxList.clear();
			}
			count++;
		}
		for (Grid chil: root.getChildren())
		{
			minmaxList.add(chil.getMinmax());
		}
		if(minimax)
		{
			root.setMinmax(maximum(minmaxList));
		}
		else
		{
			root.setMinmax(minimum(minmaxList));
		}
	}

	public int minimum(List<Integer> list)//vriskei tin mikroteri axia
	{
		int minimum = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            if (minimum > list.get(i))
                minimum = list.get(i);
        }
        return minimum;
	}

	public int maximum(List<Integer> list)//vriskei tin megaliteri axia
	{
		int maximum = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            if (maximum < list.get(i))
                maximum = list.get(i);
        }
        return maximum;
	}

	public void minmax(boolean mm)//algorithm minimax
	{
		Grid current=root;//arxika einai i riza o current
		giveMinimaxValue(mm);
		Scanner scanner=new Scanner(System.in);
		Grid copyCurrent=current.copy();//ftiaxnoume to antigrafo
		int whoWon=0;
		boolean validMove=false;//an mporei na paei se kapoia thesi
		boolean xWon=false;//an nikise o X
		while(current.getWinning()==false && !current.getChildren().isEmpty())//oso den iparxei nikitis/den einai adeia i lista
		{
			for(Grid child:current.getChildren())//gia kathe paidi
			{
				if (child.getMinmax()==current.getMinmax())
				{
					current=child;
				}
				if(current.getWinning()==true)
				{
					xWon=true;
				}
			}
			System.out.println("Computer played: \n"+current);
			while(validMove==false && xWon==false)//oso den nikise kaneis
			{
				System.out.println("Give the row of the element you want to play:(play a valid move)");
				Integer row=scanner.nextInt();
				System.out.println("Give the column of the element you want to play:(play a valid move)");
				Integer column=scanner.nextInt();
					copyCurrent=current.copy();
					copyCurrent.getCell(column,row).checkWithO();//tsekarei to keli pou dialexe o xristis
					for(Grid child:current.getChildren())
					{
						if(copyCurrent.equals(child))
						{
							validMove=true;
							current=child;
						}
					}
					System.out.println("You played:\n"+current);
					copyCurrent=current.copy();
			}
			validMove=false;
		}
		if (current.getWinning()==true)//an nikise kapoios
		{
			whoWon=current.getMinmax();
			if(whoWon==1000)//nika o max
			{
				System.out.print("X won!");
			}
			else//nika o min
			{
				System.out.print("O won!");
			}
		}
		else//isopalia
		{
			System.out.println("Draw!");
		}


	}
	public static void main(String[] args) {
		Grid grid=new Grid(3,3,3);
		System.out.print(grid.gridstring());
		Tree2 tree=new Tree2(grid,3,3,3,true);
		tree.minmax(false);
	}
}