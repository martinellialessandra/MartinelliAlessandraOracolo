/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package martinellialessandraoracoloclient;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Alessandra Martinell
 */
public class MartinelliAlessandraOracoloClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            //Configurazione connessione
            int porta = 12345; //E' la porta in cui il server è in connesso
            String host = "localhost"; //"localhost" significa che il server e il client condividono lo stesso computer
            Socket socket = new Socket(host, porta); //E' il comando che effettua la connessione di rete
            //Preparazione dei canali di comunicazione
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //Legge i messaggi che arrivano dal server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true); //Spedisce i comandi al server
            Scanner tastiera = new Scanner(System.in); //Legge ciò che l'utente scrive sulla tastiera
            //lettura primo messaggio
            //Quando ci si connette, il client aspetta che il server invii il suo messaggio di "benvenuto" e che lo stampi sullo schermo 
            String rispostaServer = in.readLine();
            System.out.println("Oracolo: " + rispostaServer);
            //ciclo per continuare la chat con l'oracolo
            while (true) {
                //input dati
                System.out.print("Inserisci comando: "); //Chiede all'utente di inserire un comando 
                String comando = tastiera.nextLine(); //Prende il testo digitato dall'utente (es. testo della domanda DOMANDA_come stai?)
                // Invia il messaggio al server attraverso la socket
                out.println(comando);
                // Lettura della risposta.
                rispostaServer = in.readLine(); //Legge la risposta data dal server
                System.out.println("Oracolo: " + rispostaServer);//Stampa la risposta dell'Oracolo sullo schermo
            }
            //Gestione errori
        } catch (IOException e) {
            System.err.println("Impossibile connettersi al server: " + e.getMessage()); //Se succedesse qualcosa, invece di bloccarsi manda un messaggio di errore
        }

    }
    
}
