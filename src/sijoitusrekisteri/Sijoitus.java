package sijoitusrekisteri;
import java.io.*;
import java.util.Random;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * Sijoitusrekisterin sijoitus joka osaa mm. itse huolehtia tunnusNro:staan.
 * 
 * @author valla
 * @version 25.4.2025
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
     * @param uusiSijoitusNimi nimi joksi sijoituksen nimi muutetaan
     */
    public void muutaNimi(String uusiSijoitusNimi) {
        sijoituksennimi = uusiSijoitusNimi;
    }
    

 
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot sijoitukselle.
     */
    public void vastaaBinance() {
        Random rand = new Random();
        int rand_int1 = rand.nextInt(9999);
        
        sijoituksennimi = "Binance " + rand_int1;
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
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }
    

    /**
     * Palauttaa sijoituksen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return sijoitus tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Sijoitus sijoitus = new Sijoitus();
     *   sijoitus.parse("3|Binance1234");
     *   sijoitus.getTunnusNro() === 3;
     *   sijoitus.getsijoitusNimi() === "Binance1234";
     *   sijoitus.toString().startsWith("3|Binance1234") === true;
     * </pre>
     */
    @Override
    public String toString() {
        return "" + getTunnusNro() + "|" + sijoituksennimi;
    }


    /**
     * Selvitää sijoituksen tiedot | erotellusta merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta sijoituksen tiedot otetaan
     * 
     * @example
     * <pre name="test">
     *   Sijoitus sijoitus = new Sijoitus();
     *   sijoitus.parse("3|Binance1234");
     *   sijoitus.getTunnusNro() === 3;
     *   sijoitus.toString().startsWith("3|Binance1234") === true;
     *
     *   sijoitus.rekisteroi();
     *   int n = sijoitus.getTunnusNro();
     *   sijoitus.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
     *   sijoitus.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
     *   sijoitus.getTunnusNro() === n+20+1;
     *     
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        sijoituksennimi = Mjonot.erota(sb, '|', sijoituksennimi);
    }
    
    
    @Override
    public boolean equals(Object sijoitus) {
        if ( sijoitus == null ) return false;
        return this.toString().equals(sijoitus.toString());
    }


    @Override
    public int hashCode() {
        return tunnusNro;
    }
    
    @Override
    public Sijoitus clone() throws CloneNotSupportedException {
        Sijoitus uusi;
        uusi = (Sijoitus) super.clone();
        return uusi;
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