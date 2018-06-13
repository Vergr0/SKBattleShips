import java.net.*;
import java.util.Scanner;
import java.io.*;

class TCPClient
{
    public static int port = 12347;
    public static BufferedReader con_br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args)throws IOException
    {
    	//BattleBoard battleBoardServer = new BattleBoard();
 	    BattleBoard battleBoardClient = new BattleBoard();
 	    Scanner in = new Scanner(System.in);
        System.out.print("Podaj adres serwera: ");
        String address = con_br.readLine();
        Socket sock = new Socket(address, port);
        BufferedReader sock_br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        PrintWriter sock_pw = new PrintWriter(sock.getOutputStream(), false);
        System.out.println("Nawi¹zano po³¹czenie.");

        Thread chat_client_writer = new ChatWriter("chat_client_writer", sock_pw, con_br);
        chat_client_writer.start();
        boolean correctDirection,correctRow=false,correctColumn=false,correctField=false;
        int serverHitResponse;
        String s;
        while((s = sock_br.readLine()) != null)
        {
        	if(s.contains("tak")){
        		battleBoardClient.initBattleBoard();
        		((ChatWriter) chat_client_writer).setSuspended(true);
        		while(battleBoardClient.shipCounter<6)//dodawn1atkow dla klienta
	        	   {
        			   battleBoardClient.drawOwnBoard();
        			   System.out.println("Podaj kierunek(N=0,S=1,W=2,E=3)");
	        		   
        			   int a = in.nextInt();
	        		 
	        		  
	        		   if(a==0 || a == 1 || a==2 || a ==3)
	        		   	{
	        			   	correctDirection = true;
	        		   	}
	        		   else
	        		   	{
	        			   correctDirection = false;
	        			   System.out.println("le wybrany kierunek");
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
	        		   System.out.println("Podaj pocz¹tkowy rz¹d");
	        		   int b = in.nextInt();
	        		   
	        		   System.out.println("Podaj pocz¹tkow¹ kolumnê");
	        		   int c = in.nextInt();
	        		   battleBoardClient.addShip(a,battleBoardClient.shipCounter, b, c);
	        		   battleBoardClient.shipCounter++;
	        	   }//koniec dodawania statkow
        		while(battleBoardClient.play)//ta petla sie wykonuje dopoki trwa gra UWAGA TO MOZE NA RAZIE NIE DZIALAC
        		{
        			int a=0,b=0,c=0,d=0,ready=-1,ready2=0;
        			//serwer zaczyna wiec najpierw czekamy na jego strzal
        		
        			while(sock_br.ready())
        			{
        				continue;
        			}
        			c = sock_br.read();//czytamy rzad
        			while(sock_br.ready())
        			{
        				continue;
        			}
        			d = sock_br.read();//czytamy kolumne
        			battleBoardClient.getHit(c, d);//aktualizujemy nasza plansze 
        			sock_pw.print(battleBoardClient.response);//wysylamy informacje czy trafil, 1 jesli tak, 0 jesli nie
        			sock_pw.flush();
        			battleBoardClient.drawBattleBoards();//rysujemy OBIE plansze
        			System.out.println("Twoja tura. Podaj wspó³rzêdne.");//ZACZYNA SIE TURA KLIENTA
        			while(!correctField)//sprawdzenie czy wybrane pole nie bylo juz wczesniej ostrzelane
        			{
        				while(!correctRow)//sprawdzenie poprawnosci wpisanego rzedu
	        			{
	        				System.out.println("Podaj rz¹d");
	            			
	            				if(in.hasNextInt())
	            				{
	            					a = in.nextInt();
	            				}
	            				else
	            				{
	            					System.out.println("Poda³eœ niew³aœciw¹ liczbê, spróbuj ponownie");
		            				in.next();
		            				continue;
	            				}
	            				if(a<0 || a>9)
	                			{
	                				System.out.println("Poda³eœ niew³aœciw¹ liczbê.Spróbuj ponownie");
	                				
	                			}
	            				else
	            				{
	            					correctRow = true;
	            				}	
	        			}
	        			while(!correctColumn)//sprawdzenie poprawnosci kolumny
	        			{
	        				System.out.println("Podaj kolumnê");
	        				if(in.hasNextInt())
            				{
            					b = in.nextInt();
            				}
            				else
            				{
            					System.out.println("Poda³eœ niew³aœciw¹ liczbê, spróbuj ponownie");
	            				in.next();
	            				continue;
            				}
            				if(b<0 || b>9)
                			{
                				System.out.println("Poda³eœ niew³aœciw¹ liczbê.Spróbuj ponownie");
                				
                			}
            				else
            				{
            					correctRow = true;
            				}
	            			
	        			}
	        			if(battleBoardClient.enemyBoard[a][b]=='*' || battleBoardClient.enemyBoard[a][b]=='X')
	        			{
	        				System.out.println("To pole by³o ju¿ ostrzelane, wybierz inne.");
	        			}
	        			else
	        			{
	        				correctField = true;
	        			}
        			}//koniec sprawdzania poprawnosci podanego pola
        			
        			sock_pw.print(a);//wysylamy serwerowi rzad
        			sock_pw.flush();
        			sock_pw.print(b);//kolumne
        			sock_pw.flush();
        			while(sock_br.ready())
        			{
        				continue;
        			}
        			serverHitResponse = sock_br.read();//czekamy na odpowiedz czy bylo trafienie
        			battleBoardClient.strikeEnemyBoard(a, b, serverHitResponse); // aktualizujemy lokalna plansze serwera
        			//i od nowa lecimy
        		
        		}
        		
        		battleBoardClient.shipCounter = 0;
        		battleBoardClient.ready=true;
        		((ChatWriter) chat_client_writer).setSuspended(false);
        	}
            System.out.println("\rserver: " + s);
            if(battleBoardClient.ready==true)  battleBoardClient.drawBattleBoards();
        }
        sock.close();
    }
}