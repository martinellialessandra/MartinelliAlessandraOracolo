/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package martinellialessandraoracoloserver;

import java.util.*;

/**
 *
 * @author Alessandra Martinell
 */
public class Oracolo {
    private HashMap<String, ArrayList<String>> risposte;
    private String personalitaAttuale;
    /**
     * Metodo costruttore
     * @param personalita personalita dell'oracolo
     */
    public Oracolo(String personalita) {
        inizializzaPersonalita();
        if (!setPersonalitaAttuale(personalita)) {
        this.personalitaAttuale = "SIBILLA"; // Default di sicurezza
    }
    }
   
    /**
     * Metodo per inizializzare la mappa con le risposte in base alla personalita
     */
    private void inizializzaPersonalita(){
        risposte = new HashMap<>();

        ArrayList<String> risposteCappello = new ArrayList<>();
        risposteCappello.add("Vedo in te un coraggio da leone. La tua prossima sfida scolastica richiederà il sangue freddo di un vero Grifondoro.");
        risposteCappello.add("La tua mente brilla di ingegno. I tuoi sforzi saranno ricompensati, la saggezza dei Corvonero guida i tuoi passi.");
        risposteCappello.add("Il tuo futuro risplende di lealtà e duro lavoro. Una meritata ricompensa attende la tua anima da Tassorosso.");
        risposteCappello.add("Vedo ambizione e astuzia nei tuoi occhi. Nel tuo futuro c'è un grande potere ... o forse solo un sacco di compiti. Usa l'ingegno Serpeverde.");
        risposte.put("CAPPELLO", risposteCappello);

        ArrayList<String> risposteSibilla = new ArrayList<>();
        risposteSibilla.add("La tua mente è limpida come l'acqua, ma il tuo futuro è nebbioso. Un Platano Picchiatore intralcerà il tuo cammino: impara a schivare i colpi.");
        risposteSibilla.add("Le stelle dicono che la tua prossima interrogazione sarà un successo, a patto che tu non riponga troppa fiducia in un Giratempo.");
        risposteSibilla.add("Un grande mistero si cela nel tuo futuro. Ricorda: non sono le nostre capacità che dimostrano chi siamo davvero, ma le nostre scelte.");
        risposteSibilla.add("Vedo...sì, vedo un Gramo nel fondo della tua tazza! Ah no, aspetta...è solo una macchia di cioccolata della tu Mielandia. Ma tieni comunque gli occhi aperti.");
        risposte.put("SIBILLA", risposteSibilla);

        ArrayList<String> rispostePiton = new ArrayList<>();
        rispostePiton.add("Il Gramo ha posato il suo sguardo su di te. Nel tuo futuro prossimo vedo una sfortuna nera...e un calzino spaiato.");
        rispostePiton.add("Le foglie di tè non mentono: una nube oscura si addensa sulla tua testa. Sei per caso inseguito da un Dissennatore o hai solo dimenticato di studiare?");
        rispostePiton.add("La tua linea della vita si incrocia con il cammino di un Molliccio. La tua paura più grande prenderà forma quando meno te lo aspetti.");
        rispostePiton.add("Il futuro non vi riserverà la gloria che scioccamente vi ostinate a sognare, ma soltanto il rovinoso compimento della vostra stessa, crassa ignoranza.");
        risposte.put("PITON", rispostePiton);
    }

    /**
     * Metodo get
     * @return personalita attuale dell'oracolo
     */
    public String getPersonalitaAttuale() {
        return personalitaAttuale;
    }

   
    /**
     * Metodo set
     * @param personalita nuova personalita
     * @return true se la personalita e' stata cambiata, false altrimenti
     */
    public boolean setPersonalitaAttuale(String personalita) {
        if(risposte.containsKey(personalita)) {
            personalitaAttuale = personalita;
            return true;
        }
        return false;
       
    }
   
   
    /**
     * Metodo per rispondere alle domande
     * @return risposta alla domanda
     */
    public String rispondi(){
        Random rnd = new Random();
        ArrayList<String> listaRisposte = risposte.get(personalitaAttuale);
        return listaRisposte.get(rnd.nextInt(listaRisposte.size()));
    }
}
