import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;import javax.xml.stream.events.EndElement;

import java.io.*;

class TCPSerwer
{
    public static int port = 12347;
    public static BufferedReader con_br = new BufferedReader(new InputStreamReader(System.in));

    @SuppressWarnings("deprecation")
	public static void main(String[] args)throws IOException
    {
    	BattleBoard battleBoardServer = new BattleBoard();
		//BattleBoard battleBoardClient = new BattleBoard();
        ServerSocket ssock = new ServerSocket(port);
        Scanner in = new Scanner(System.in);
        System.out.println("<Serwer>: Oczekiwanie na po��czenie.");
        Socket csock = ssock.accept();
        System.out.println("<Serwer>: Nawi�zano po��czenie.");

        BufferedReader csock_br = new BufferedReader(new InputStreamReader(csock.getInputStream()));
        PrintWriter csock_pw = new PrintWriter(csock.getOutputStream(), false);

        //Thread chat_server_writer = new ChatWriter("chat_server_writer", csock_pw, con_br);
        //chat_server_writer.start();
        //csock_pw.println("Napisz: \"END\" by zako�czy� po��czenie.");
        //csock_pw.flush();
        //csock_pw.println("Czy chcesz rozpocz�� rozgrywk�?");
        //csock_pw.flush();
        String s;
        boolean correctDirection,correctRow=false,correctColumn=false,correctField=false;
        int clientHitResponse;
        while(true)
        {
        	//if(s.contains("tak")){
        		//csock_pw.println("tak");
        		//csock_pw.flush();
        		//((ChatWriter) chat_server_writer).setSuspended(true);
        		while(battleBoardServer.shipCounter<6)//dodawn1atkow dla serwera
	        	   {
        			battleBoardServer.drawOwnBoard();
     			   System.out.println("Podaj kierunek(N=0,S=1,W=2,E=3)");
	        		   
     			   int a = in.nextInt();
	        		 
	        		  
	        		   if(a==0 || a == 1 || a==2 || a ==3)
	        		   	{
	        			   	correctDirection = true;
	        		   	}
	        		   else
	        		   	{
	        			   correctDirection = false;
	        			   System.out.println("�le wybrany kierunek");
		        		   while(!correctDirection)
		        		   {
		        			   System.out.println("Podaj kierunek(N=0,S=1,W=2,E=3)");
			        		   a = in.nextInt();
			        		   if(a==0 || a == 1 || a==2 || a ==3)
			        		   	{
			        			   	correctDirection = true;
			        		   	}
		        		   }
	        		   	}  
	        		   System.out.println("Podaj pocz�tkowy rz�d");
	        		   int b = in.nextInt();
	        		   
	        		   System.out.println("Podaj pocz�tkow� kolumn�");
	        		   int c = in.nextInt();
	        		   battleBoardServer.addShip(a,battleBoardServer.shipCounter, b, c);
	        		   battleBoardServer.shipCounter++;
	        	   }
        		while(battleBoardServer.play)//ta petla sie wykonuje dopoki trwa gra
        		{
        			correctRow=false;
        			correctColumn=false;
        			correctField=false;
        			int a=0,b=0,c=0,d=0;//,ready=0,ready2=0;
        			battleBoardServer.drawBattleBoards();//rysujemy OBIE plansze
        			System.out.println("Twoja tura. Podaj wsp�rz�dne.");
        			while(!correctField)//sprawdzenie czy wybrane pole nie bylo juz wczesniej ostrzelane
        			{
        				while(!correctRow)//sprawdzenie poprawnosci wpisanego rzedu
	        			{
	        				System.out.println("Podaj rz�d");
	            			
	            				if(in.hasNextInt())
	            				{
	            					a = in.nextInt();
	            				}
	            				else
	            				{
	            					System.out.println("Poda�e� niew�a�ciw� liczb�, spr�buj ponownie");
		            				in.next();
		            				continue;
	            				}
	            				if(a<0 || a>9)
	                			{
	                				System.out.println("Poda�e� niew�a�ciw� liczb�.Spr�buj ponownie");
	                				
	                			}
	            				else
	            				{
	            					correctRow = true;
	            				}	
	        			}
	        			while(!correctColumn)//sprawdzenie poprawnosci kolumny
	        			{
	        				System.out.println("Podaj kolumn�");
	        				if(in.hasNextInt())
            				{
            					b = in.nextInt();
            				}
            				else
            				{
            					System.out.println("Poda�e� niew�a�ciw� liczb�, spr�buj ponownie");
	            				in.next();
	            				continue;
            				}
            				if(b<0 || b>9)
                			{
                				System.out.println("Poda�e� niew�a�ciw� liczb�.Spr�buj ponownie");
                				
                			}
            				else
            				{
            					correctColumn = true;
            				}
	            			
	        			}
	        			if(battleBoardServer.enemyBoard[a][b]=='*' || battleBoardServer.enemyBoard[a][b]=='X')
	        			{
	        				System.out.println("To pole by�o ju� ostrzelane, wybierz inne.");
	        				correctColumn = false;
	        				correctRow = false;
	        			}
	        			else
	        			{
	        				correctField = true;
	        			}
        			}//koniec sprawdzania poprawnosci podanego pola UWAGA OD NASTEPNEJ LINIJKI MOZE NIE DZIALAC
        			csock_pw.println(Integer.toString(a));//wysylanie wspolrzednych uderzenia do klienta
        			csock_pw.flush();
        			csock_pw.println(Integer.toString(b));
        			csock_pw.flush();
        			//DataOutputStream.
        			//Oczekiwanie na odpowiedz klienta, czy uderzenie trafilo
        			while(!csock_br.ready())
        			{
        				try {
							Thread.sleep(10);
        				} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        			}
        			clientHitResponse = Integer.parseInt(csock_br.readLine());
        			battleBoardServer.strikeEnemyBoard(a, b, clientHitResponse);//akutalizacja lokalnej planszy klienta
        			//START TURY KLIENTA
        			//OCZEKIWANIE NA JEGO STRZAL
        		
        			while(!csock_br.ready())
        			{
        				try {
							Thread.sleep(3000);
							System.out.println("Oczekiwanie na ruch przeciwnika");
        				} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        			}
        			c=Integer.parseInt(csock_br.readLine());//zczytanie rzedu
        			while(!csock_br.ready())
        			{
        				try {
							Thread.sleep(10);
        				} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        			}
        			d=Integer.parseInt(csock_br.readLine());//zczytanie kolumny
        			
     
        			battleBoardServer.getHit(c, d);//aktualizacja lokalnej planszy serwera
        			csock_pw.println(Integer.toString(battleBoardServer.response));//wyslanie odpowiedzi o trafieniu do klienta
        			
        		}
        		battleBoardServer.ready=true;
        		//((ChatWriter) chat_server_writer).setSuspended(false);
        	//}
            //System.out.println("\rclient: " + s);
            //System.out.print("> ");
            //if(battleBoardServer.ready==true)  battleBoardServer.drawBattleBoards();
        }
       // System.out.println("\rserver: Client has disconnected");
       // csock.close();
       // ssock.close();
    }
}