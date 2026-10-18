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
            //inizializzazione variabili
            int porta = 12345;
            String host = "localhost";
            Socket socket = new Socket(host, porta);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner tastiera = new Scanner(System.in);
            //lettura primo messaggio
            String rispostaServer = in.readLine();
            System.out.println("Oracolo: " + rispostaServer);
            //ciclo per continuare la chat con l'oracolo
            while (true) {
                //input dati
                System.out.print("Inserisci comando: ");
                String comando = tastiera.nextLine();
                // Invia il messaggio al server
                out.println(comando);
                // Lettura della risposta del server
                rispostaServer = in.readLine();
                System.out.println("Oracolo: " + rispostaServer);
            }

        } catch (IOException e) {
            System.err.println("Impossibile connettersi al server: " + e.getMessage());
        }

    }
    
}
