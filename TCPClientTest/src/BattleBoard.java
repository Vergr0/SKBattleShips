
public class BattleBoard
{
	boolean ready=false,play = true;
	int response;
	int count = 0,enemyCount = 15;
	char [][] battleBoard = new char[10][10];
	char [][] enemyBoard = new char[10][10];
	String message;
	public int shipCounter = 1;
	void initBattleBoard()
	{
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				battleBoard[i][j] = 48;//wypelnianie mapy wod¹
				enemyBoard[i][j]= '?';//inicjalizacja mapy przeciwnika
			}
		}
	}
	void addShip(int direction,int length, int initRow, int initColumn)	
	{
		
			if(direction == 0)//do gory
			{
				int q=0;
				for(int i=0;i<length;i++)
				{
					
					try
					{
						if(battleBoard[initRow-i][initColumn]!='1')
							{
								battleBoard[initRow-i][initColumn] = '1';//wypelnianie pol statkiem
								q++;
								count++;
							}	
						else
							{
								for(int z=0;z<q;z++)
								{
									battleBoard[initRow-z][initColumn]=48;
									count--;
								}	
								shipCounter--;
								System.out.println("Nieprawid³owe ustawienie statku, spróbuj ponownie");
								i=6;
							}
						
					}catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("Nieprawid³owe ustawienie statku, spróbuj ponownie");
						shipCounter--;
						count--;
						for(int j = 0;j<q;j++)
						{
							if(initRow -j>=0 && initRow-j<=9 && initColumn >=0 && initColumn <=9)
								battleBoard[initRow-j][initColumn] = 48;
								count--;
						}
						i = 6;
					}
				}
				
			}
			else if(direction == 1)//do dolu
			{
				int q=0;
				for(int i=0;i<length;i++)
				{
					try
					{
						if(battleBoard[initRow+i][initColumn]!='1')
							{
								battleBoard[initRow+i][initColumn] = '1';
								q++;
								count++;
							}
						else
							{
								for(int z=0;z<q;z++)
									{
										battleBoard[initRow+z][initColumn]=48;
										count--;
									}
								shipCounter--;
								System.out.println("Nieprawid³owe ustawienie statku, spróbuj ponownie");
								i=6;
							}
					}catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("Nieprawid³owe ustawienie statku, spróbuj ponownie");
						shipCounter--;
						count--;
						for(int j = 0;j<q;j++)
						{
							if(initRow+j>= 0 && initRow+j<=9 && initColumn >=0 && initColumn <=9)
								battleBoard[initRow+j][initColumn] = 48;
								count--;
						}	
						i = 6;
					}
				}
				
			}
			else if(direction == 2)//w lewo
			{
				int q=0;
				for(int i=0;i<length;i++)
				{
					try
					{
						if(battleBoard[initRow][initColumn-i]!='1')
							{
								battleBoard[initRow][initColumn-i] = '1';
								q++;
								count++;
							}
							else 
							{
								for(int z=0;z<q;z++)
								{
									battleBoard[initRow][initColumn-z]=48;
									count--;
								}
								shipCounter--;
								System.out.println("Nieprawid³owe ustawienie statku, spróbuj ponownie");
								i=6;
							}
					}catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("Nieprawid³owe ustawienie statku, spróbuj ponownie");
						shipCounter--;
						
						for(int j = 0;j<q;j++)
						{
							if(initColumn-j>=0 && initColumn-j<=9 && initRow>=0 && initRow<=9)
								battleBoard[initRow][initColumn-j] = 48;
								count--;
						}
						i = 6;
					}
				}
			}	
			else if(direction == 3)//w prawo
			{
				int q=0;
				for(int i=0;i<length;i++)
				{
					try
					{
						if(battleBoard[initRow][initColumn+i]!='1')
							{
								battleBoard[initRow][initColumn+i] = '1';
								q++;
								count++;
							}
							else
							{
								for(int z=0;z<q;z++)
									{
										battleBoard[initRow][initColumn+z]=48;
										count--;
									}
								shipCounter--;
								System.out.println("Nieprawid³owe ustawienie statku, spróbuj ponownie");
								i=6;
							}
					}catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("Nieprawid³owe ustawienie statku, spróbuj ponownie");
						shipCounter--;
						
						for(int j = 0;j<q;j++)
						{
							if(initColumn+j<=9 && initColumn+j>=0 && initRow>=0 && initRow<=9)
							battleBoard[initRow][initColumn+j] = 48;
							count--;
						}
						i = 6;
					}
				}

			}	
	}
	void strikeEnemyBoard(int row, int column,int hit)
	{
		
		if(row<0 || row>9 || column<0 || column>9)
		{
			System.out.println();
		}
			
		if(hit == 1)
			{
				enemyBoard[row][column] = '*'; 
				System.out.println("Trafiony");
				enemyCount--;
				if(enemyCount==0)
				{
					System.out.println("Wygra³eœ, zatopi³eœ wszystkie statki przeciwnika!");
					play = false;
				}
			}
			else
				System.out.println("Pud³o");
				enemyBoard[row][column]='X';
		
		
	}
	void getHit(int row,int column)
	{
		if(battleBoard[row][column]=='1')
		{
			battleBoard[row][column] = '*';
			System.out.println("Twój statek zosta³ trafiony");
			count--;
			response = 1;
		}
		else
		{
			battleBoard[row][column] = 'X';
			response = 0;
		}
		if(count==0)
		{
			System.out.println("Przegra³eœ. Wszystkie twoje statki zosta³y zatopione.");
			play = false;
		}
	}
	void drawBattleBoards()
	{
		//rysowanie wlasnej siatki
		System.out.println(" ");
		System.out.print(" ");
		for(int j =0;j<10;j++)
		{
			System.out.print("|");
			System.out.print(j);
			
		}
		System.out.print("|");
		System.out.println("");
		
		for(int i=0;i<10;i++)
		{
			System.out.print(i);
			System.out.print("|");
			for(int j=0;j<10;j++)
			{
				System.out.print(battleBoard[i][j]);
				System.out.print("|");
			}
			System.out.println("");
		}
		
		
		//rysowanie siatki przeciwnika
		System.out.println(" ");
		System.out.print(" ");
		for(int j =0;j<10;j++)
		{
			System.out.print("|");
			System.out.print(j);
			
		}
		System.out.print("|");
		System.out.println("");
		
		for(int i=0;i<10;i++)
		{
			System.out.print(i);
			System.out.print("|");
			for(int j=0;j<10;j++)
			{
				System.out.print(enemyBoard[i][j]);
				System.out.print("|");
			}
			System.out.println("");
		}
	}
	void drawOwnBoard()
	{
		System.out.println(" ");
		System.out.print(" ");
		for(int j =0;j<10;j++)
		{
			System.out.print("|");
			System.out.print(j);
			
		}
		System.out.print("|");
		System.out.println("");
		
		for(int i=0;i<10;i++)
		{
			System.out.print(i);
			System.out.print("|");
			for(int j=0;j<10;j++)
			{
				System.out.print(battleBoard[i][j]);
				System.out.print("|");
			}
			System.out.println("");
		}
	}
	
	char[][] getBattleBoard()
	{
		if(count == 0)
			message = "Koniec gry. Czy chcesz rozpocz¹æ now¹ rozgrywkê?";
		return battleBoard;
	}
	public BattleBoard()
	{
		initBattleBoard();
	}
}
