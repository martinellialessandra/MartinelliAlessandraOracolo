/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package martinellialessandraoracoloserver;

import java.io.*;
import java.net.Socket;

/**
 *
 * @author Alessandra Martinell
 */
public class ClientOracolo implements Runnable {
    private Socket socket;
    private Oracolo oracolo;

    public ClientOracolo(Socket socket) {
        this.socket = socket;
        oracolo = new Oracolo("SIBILLA");
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("Ti sei collegato. Questa è la persona che incontrerai " + oracolo.getPersonalitaAttuale());

            String messaggioClient;
            // Legge i messaggi finché la connessione rimane aperta
            while ((messaggioClient = in.readLine()) != null) {
                // Elabora il messaggio con il protocollo e invia la risposta
                String risposta = gestisciProtocollo(messaggioClient);
                out.println(risposta);
            }
        } catch (IOException e) {
            System.out.println("Sei disconnesso.");
        }
        chiudiSocket();
    }

    /**
     * Metodo per vedere che protocollo e' stato inserito
     *
     * @param messaggio messaggio del client
     * @return risposta
     */
    public String gestisciProtocollo(String messaggio) {
        //controllo per vedere se il messaggio e' vuoto
        if (messaggio == null || messaggio.isEmpty()) {
            return "Nessun comando inserito.";
        }

        //divisione del messaggio in messaggio e parametro
        String[] messaggioDiviso = messaggio.split("_", 2);
        String comando = messaggioDiviso[0];
       
        if(messaggioDiviso.length < 2 && !comando.equalsIgnoreCase("HELP")){
            return "Inserisci '_' dopo il comando prima dei parametri.";
        }
        //controllo protocollo
        if (comando.equalsIgnoreCase("HELP")) {
            return "I comandi che si possono trovare sono: PERSONA_[nome personalita'] cambia la personalita' dell'oracolo, DOMANDA_[testo] fa una domanda all'oracolo, HELP mostra i comandi";
        } else if (comando.equalsIgnoreCase("PERSONA")) {
            if (!oracolo.setPersonalitaAttuale(messaggioDiviso[1])) {
                return "Inserisci una personalita' tra: SIBILLA, PITON o CAPPELLO";
            } else {
                return "Personalita' cambiata";
            }
        } else if (comando.equalsIgnoreCase("DOMANDA")) {
            if (messaggioDiviso[1].isBlank()) {
                return "L'Oracolo richiede un quesito! Formula la tua domanda";
            }
            return oracolo.rispondi();
        } else {
            return "Inserisci un comando corretto";
        }
    }

    /**
     * Metodo per chiudere la socket
     */
    public void chiudiSocket() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Errore durante la chiusura del socket: " + e.getMessage());
        }
    }
}
