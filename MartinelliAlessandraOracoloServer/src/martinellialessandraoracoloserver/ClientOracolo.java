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
//Multithread e inizializzazione
public class ClientOracolo implements Runnable { //Posso lavorare su un thread separato, così che il server lavora + client insieme, senza bloccarsi
    private Socket socket; //E' la porta di comunicazione aperta con lo specifico client connesso
    private Oracolo oracolo; //Ogni utente connesso ha la propria istanza dell'Oracolo, che parte di default con la personalità di "SIBILLA".


    public ClientOracolo(Socket socket) {
        this.socket = socket;
        oracolo = new Oracolo("SIBILLA"); //Personalità di default
    }
    //Ciclo di vita della connessione
    @Override
    public void run() {
        try (
                //Legge i comandi inviati dal client via rete                                           //Spedisce le risposte del Server al client ("true" invio immediato) 
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            //MESSAGGIO DI BENVENUTO
            //Appena il client si connette, il server invia un essaggio d'inizio dicendo la personalità di default (persona attiva attualemente)
            out.println("Ti sei collegato. Questa è la persona che incontrerai " + oracolo.getPersonalitaAttuale());

            String messaggioClient;
            // Legge i messaggi finché la connessione rimane aperta. Ogni volta che il client invia una riga di testo: (38)
            while ((messaggioClient = in.readLine()) != null) {
                // Elabora il messaggio con il protocollo e invia la risposta
                String risposta = gestisciProtocollo(messaggioClient); //<-Passa il messaggio al metodo
                out.println(risposta); //Invia la risposta attraverso la socket
            }
            //DISCONNESSIONE
            //Se il client si scollegasse, si attiva il "IOException" che stampa "Sei disconnesso" e invoca "ChiudiSocket"
        } catch (IOException e) {
            System.out.println("Sei disconnesso.");
        }
        chiudiSocket();
    }

    /**
     * 
     *
     * @param messaggio messaggio del client
     * @return risposta
     */
    //Metodo per vedere che protocollo e' stato inserito
    public String gestisciProtocollo(String messaggio) {
        //Se il messaggio è vuoto, risponde con un messaggio di errore
        if (messaggio == null || messaggio.isEmpty()) {
            return "Nessun comando inserito.";
        }

        //divisione del messaggio in messaggio e parametro
        String[] messaggioDiviso = messaggio.split("_", 2); //Divide la stringa in due parti usando '_' come separatore (64)
        //Es. "PERSONA_PITON" diventa "PERSONA", "PITON" 
        String comando = messaggioDiviso[0];
       
        if(messaggioDiviso.length < 2 && !comando.equalsIgnoreCase("HELP")){
            return "Inserisci '_' dopo il comando prima dei parametri.";
        }
        //Se il comando non è HELP e non contiene il separatore _, avvisa l'utente del formato errato. (67, 65)
        
        //controllo dei comandi
        //HELP
        //Restituisce la lista di tutti i comandi possibili
        if (comando.equalsIgnoreCase("HELP")) {
            return "I comandi che si possono trovare sono: PERSONA_[nome personalita'] cambia la personalita' dell'oracolo, DOMANDA_[testo] fa una domanda all'oracolo, HELP mostra i comandi";
        //PERSONA
        //Imposta la nuova persoanlità
        //Se il nome non fosse valido (es. non è PITON, SIBILLA, CAPPELLO) manda un messaggio di errore
        } else if (comando.equalsIgnoreCase("PERSONA")) {
            if (!oracolo.setPersonalitaAttuale(messaggioDiviso[1])) {
                return "Inserisci una personalita' tra: SIBILLA, PITON o CAPPELLO";
            } else {
                return "Personalita' cambiata";
            }
        //DOMANDA
        //Controlla che la domanda non sia scritta solo da spazi vuoti
        //Se la domanda è valida, invoca "oracolo.rispondi()" che restituisce la profezia del personaggio corrente
        } else if (comando.equalsIgnoreCase("DOMANDA")) {
            if (messaggioDiviso[1].isBlank()) {
                return "L'Oracolo richiede un quesito! Formula la tua domanda";
            }
            return oracolo.rispondi();
        } else {
            return "Inserisci un comando corretto"; //altrimenti manda un messaggio di errore
        }
    }

    /**
     * Metodo per chiudere la socket
     */
    //Garantisce che la Socket sia liberata correttamente senza sprechi di memoria sul Server
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
