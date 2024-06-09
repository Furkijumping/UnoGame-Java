/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import game.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static client.Client.sInput;
import game.Card;
import game.Game;
import java.awt.Color;

/**
 *
 * @author USER
 */
class Listen extends Thread {

    @Override
    public void run() {
        //soket bağlı olduğu sürece dön
        while (Client.socket.isConnected()) {
            try {
                Message received = (Message) (sInput.readObject());
                switch (received.type) {
                    case Name:
                        break;

                    case Turn:
                        Game.ThisGame.turn = (int) received.content;
                        break;
                    case RivalConnected:
                        String name = received.content.toString();
                        Game.ThisGame.txt_rivalName.setText(name);
                        break;
                    case Disconnect:
                        break;
                    case Card:
                        String selected = (String) received.content;

                        int startIndex = selected.lastIndexOf('[') + 1; // "[" karakterinin son indeksini bulun ve bir sonraki indeksten başlayın
                        int endIndex = selected.indexOf(']'); // "]" karakterinin ilk indeksini bulun

                        String colorString = selected.substring(startIndex, endIndex); // Renk bileşenini elde edin
                        int number = Integer.parseInt(selected.substring(endIndex + 1)); // Sayı bileşenini elde edin

                        Color color;
                        switch (colorString) {
                            case "r=255,g=0,b=0":
                                color = Color.red;
                                break;
                            case "r=0,g=255,b=0":
                                color = Color.green;
                                break;
                            case "r=0,g=0,b=255":
                                color = Color.blue;
                                break;
                            default:
                                color = Color.yellow;
                                break;
                        }

                        Game.ThisGame.middleCard = new Card(color, number);
                        Game.ThisGame.setMiddle();
                        break;
                    case Selected:
                        break;
                    case Bitis:
                        break;

                }

            } catch (IOException ex) {

                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                //Client.Stop();
                break;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                //Client.Stop();
                break;
            }
        }

    }
}

public class Client {

    //her clientın bir soketi olmalı
    public static Socket socket;

    //verileri almak için gerekli nesne
    public static ObjectInputStream sInput;
    //verileri göndermek için gerekli nesne
    public static ObjectOutputStream sOutput;
    //serverı dinleme thredi 
    public static Listen listenMe;

    public static void Start(String ip, int port) {
        try {
            // Client Soket nesnesi
            Client.socket = new Socket(ip, port);
            Client.Display("Servera baglandi");
            // input stream
            Client.sInput = new ObjectInputStream(Client.socket.getInputStream());
            // output stream
            Client.sOutput = new ObjectOutputStream(Client.socket.getOutputStream());
            Client.listenMe = new Listen();
            Client.listenMe.start();

            //ilk mesaj olarak isim gönderiyorum
            Message msg = new Message(Message.Message_Type.Name);
            msg.content = Game.ThisGame.txt_name.getText();
            Client.Send(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //client durdurma fonksiyonu
    public static void Stop() {
        try {
            if (Client.socket != null) {
                Client.listenMe.stop();
                Client.socket.close();
                Client.sOutput.flush();
                Client.sOutput.close();

                Client.sInput.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void Display(String msg) {

        System.out.println(msg);

    }

    //mesaj gönderme fonksiyonu
    public static void Send(Message msg) {
        try {
            Client.sOutput.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
