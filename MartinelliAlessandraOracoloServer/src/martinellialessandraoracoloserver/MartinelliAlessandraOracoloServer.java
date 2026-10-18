/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package martinellialessandraoracoloserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Alessandra Martinell
 */
public class MartinelliAlessandraOracoloServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //inizializzazione variabili
        ServerSocket sSocket;
        Socket client;
        final int porta = 12345;
       
        try {
            // Il Server risponde sulla porta 12345
            sSocket = new ServerSocket(porta);
            // Ciclo in attesa delle connesisoni
            System.out.println("In attesa di connessioni...");
            while (true) {
                client = sSocket.accept();
                System.out.println("Connessione stabilita con " + client.getInetAddress().toString());
                Thread oracolo = new Thread(new ClientOracolo(client));
                oracolo.start();
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }
    
}
