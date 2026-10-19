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
        ServerSocket sSocket; //Trasforma il programma in un server di rete che fa in grado di ascoltare la porta specificata.
        Socket client; //Contiene la connessione del client non appena questo si collega.
        final int porta = 12345; //Indica il numero di porta in cui il server si è connesso. Deve essere identica a quella del client.
       
        try {
            // Il Server risponde sulla porta 12345
            sSocket = new ServerSocket(porta);
            // Ciclo in attesa delle connesisoni
            System.out.println("In attesa di connessioni...");
            while (true) {
                client = sSocket.accept(); //Aspetta che il client si connetta. Quando si connette, si sblocca e restituisce una Socket. 
                System.out.println("Connessione stabilita con " + client.getInetAddress().toString()); //Stampa l'IP dell'utente
                Thread oracolo = new Thread(new ClientOracolo(client)); // Passa il socket del client alla classe gestore
                // Avvia la conversazione con il client su una corsia parallela (Thread separato) (35)
                oracolo.start();
            //Importante perchè così il server affida il client al server e intanto lui può ritornare al while per iniziare la comunicazione con un altro client
            }
            //Se la porta dov'esse essere occupato o altro, il "catch" cattura l'errore ed evita che il programma si blocchi
        } catch (IOException e) {
            System.err.println(e);
        }

    }
    
}
