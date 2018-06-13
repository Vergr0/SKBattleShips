import java.io.*;

class ChatWriter extends Thread
{
	boolean threadSuspended;
    BufferedReader con_br;
    PrintWriter sock_pw;

    public ChatWriter(String name, PrintWriter sock_pw, BufferedReader con_br)
    {
        super(name);
        this.sock_pw = sock_pw;
        this.con_br = con_br;
    }

    public void run()
    {
        String s;
        try
        {
            while(true)
            {
            	if(!threadSuspended){
                System.out.print("> ");
                s = con_br.readLine();
                if(s != null)
                    {	
                		sock_pw.println(s);
                		sock_pw.flush();
                    }
                else
                    break;
            }
            }
        }
        catch(Exception e)
        {System.err.println("Chat(serwer): Wystapil blad: \n" + e);}
    }
    
    public void setSuspended(boolean suspended){
    	this.threadSuspended=suspended;
    }
}