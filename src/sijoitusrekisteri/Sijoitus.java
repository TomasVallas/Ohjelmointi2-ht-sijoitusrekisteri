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

    
    private static int seuraavaNro    = 1;
    

    /**
     * @return sijoituksen nimi
     * @example
     * <pre name="test">
     *   Sijoitus Binance = new Sijoitus();
     *   Binance.vastaaBinance();
     *   Binance.getsijoitusNimi() =R= "Binance .*";
     * </pre>
     */
    public String getsijoitusNimi() {
        return sijoituksennimi;
    }
    
 
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot sijoitukselle.
     */
    public void vastaaBinance() {
        Random rand = new Random();
        int rand_int1 = rand.nextInt(9999);
        
        sijoituksennimi = "Binance" + rand_int1;
    }
        
    /**
     * Tulostetaan sijoiyuksen tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro, 3) + "  " + sijoituksennimi);
    }    
        
    /**
     * Tulostetaan sijoituksen tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }    
    
    /**
     * Antaa sijoitukselle seuraavan rekisterinumeron.
     * @return jäsenen uusi tunnusNro
     * @example
     * <pre name="test">
     *   Sijoitus Binance1 = new Sijoitus();
     *   Binance1.getTunnusNro() === 0;
     *   Binance1.rekisteroi();
     *   Sijoitus Binance2 = new Sijoitus();
     *   Binance2.rekisteroi();
     *   int n1 = Binance1.getTunnusNro();
     *   int n2 = Binance2.getTunnusNro();
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
        Sijoitus Binance = new Sijoitus(), Binance2 = new Sijoitus();
        Binance.rekisteroi();
        Binance2.rekisteroi();
        Binance.tulosta(System.out);
        Binance.vastaaBinance();
        Binance.tulosta(System.out);

        Binance2.vastaaBinance();
        Binance2.tulosta(System.out);

        Binance2.vastaaBinance();
        Binance2.tulosta(System.out);
    }

}