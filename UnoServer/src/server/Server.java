package server;

import game.Message;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

class ServerThread extends Thread {

    public void run() {
        while (!Server.serverSocket.isClosed()) {
            try {
                Server.Display("Client bekleniyor.");
                Socket clientSocket = Server.serverSocket.accept();
                Server.Display("Client geldi.");
                SClient nclient = new SClient(clientSocket, Server.IdClient);
                Server.IdClient++;
                Server.Clients.add(nclient);
                nclient.listenThread.start();
            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

public class Server {

    public static ServerSocket serverSocket;
    public static int IdClient = 0;
    public static int port = 0;
    public static ServerThread runThread;
    public static ArrayList<SClient> Clients = new ArrayList<>();
    public static Semaphore pairTwo = new Semaphore(1, true);

    public static void Start(int openport) {
        try {
            Server.port = openport;
            Server.serverSocket = new ServerSocket(Server.port);

            Server.runThread = new ServerThread();
            Server.runThread.start();

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Display(String msg) {

        System.out.println(msg);

    }

    public static void Send(SClient cl, Message msg) {

        try {
            cl.sOutput.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
