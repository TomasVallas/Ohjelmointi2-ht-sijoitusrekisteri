package sijoitusrekisteri;
import java.io.*;
import java.util.Random;

/**
 * @author valla
 * @version 16.3.2025
 *
 */
public class Sijoitus {
    private int        tunnusNro;
    private String     sijoituksennimi           = "";
    private String     palvelunnimi           = "";
    
    private static int seuraavaNro    = 1;
    

    /**
     * @return sijoituksen nimi
     * @example
     * <pre name="test">
     *   Sijoitus Bitcoin = new Sijoitus();
     *   Bitcoin.vastaaBitcoin();
     *   Bitcoin.getsijoitusNimi() =R= "Bitcoin .*";
     * </pre>
     */
    public String getsijoitusNimi() {
        return sijoituksennimi;
    }
    
    
    /**
     * @return sijoituksen nimi
     * @example
     * <pre name="test">
     *   Sijoitus Binance = new Sijoitus();
     *   Binance.vastaaBitcoin();
     *   Binance.getpalveluNimi() =R= "Binance .*";
     * </pre>
     */
    public String getpalveluNimi() {
        return palvelunnimi;
    }
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot sijoitukselle.
     */
    public void vastaaBitcoin() {
        Random rand = new Random();
        int rand_int1 = rand.nextInt(9999);
        
        sijoituksennimi = "Bitcoin" + rand_int1;
        palvelunnimi = "Binance";
    }
        
    /**
     * Tulostetaan sijoiyuksen tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro, 3) + "  " + sijoituksennimi + "  " + palvelunnimi);
    }    
        
    /**
     * Tulostetaan sijoituksen tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }    
    
    /**
     * Antaa jäsenelle seuraavan rekisterinumeron.
     * @return jäsenen uusi tunnusNro
     * @example
     * <pre name="test">
     *   Jasen Bitcoin1 = new Sijoitus();
     *   Bitcoin1.getTunnusNro() === 0;
     *   Bitcoin1.rekisteroi();
     *   Jasen Bitcoin2 = new Sijoitus();
     *   Bitcoin2.rekisteroi();
     *   int n1 = Bitcoin1.getTunnusNro();
     *   int n2 = Bitcoin2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    /**
     * Palauttaa sijoitusten tunnusnumeron.
     * @return sijoituksen tunnusnumero
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    /**
     * Testiohjelma sijoituksille.
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Sijoitus Bitcoin = new Sijoitus(), Bitcoin2 = new Sijoitus();
        Bitcoin.rekisteroi();
        Bitcoin2.rekisteroi();
        Bitcoin.tulosta(System.out);
        Bitcoin.vastaaBitcoin();
        Bitcoin.tulosta(System.out);

        Bitcoin2.vastaaBitcoin();
        Bitcoin2.tulosta(System.out);

        Bitcoin2.vastaaBitcoin();
        Bitcoin2.tulosta(System.out);
    }

}
        
        
        
        
        
        
        
        