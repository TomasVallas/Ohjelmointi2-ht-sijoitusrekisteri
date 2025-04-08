package sijoitusrekisteri;
import java.io.*;
import java.util.Random;
import fi.jyu.mit.ohj2.Mjonot;



/**
 * @author valla
 * @version 17.3.2025
 *
 */
public class Tapahtuma {
    private int tunnusNro;
    private int sijoitusNro;
    private String tapahtumannimi;
    private int ostopv;
    private int ostohinta;
    private int ostomaara;
    private int kurssi;
    private int myyntipv;
    private int myyntikurssi;
    private int myyntimaara;
    private int tuotto;
    private int jaljella;

    private static int seuraavaNro = 1;
    
    /**
     * Alustetaan tapahtuma.  Toistaiseksi ei tarvitse tehdä mitään
     */
    public Tapahtuma() {
        // Vielä ei tarvita mitään
    }
    
    
    /**
     * Alustetaan tietyn sijoituksen tapahtuma.  
     * @param sijoitusNro sijoituksen viitenumero 
     */
    public Tapahtuma(int sijoitusNro) {
        this.sijoitusNro = sijoitusNro;
    }

    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot Tapahtumalle.
     * Luvut arvotaan, jotta kahdella tapahtumalla ei olisi
     * samoja tietoja.
     * @param nro viite sijoitukseen, jonka tapahtumasta on kyse
     */
    public void vastaaBitcoin(int nro) {
        Random rand = new Random();
        int rand_int1 = rand.nextInt(9999);
        int rand_int2 = rand.nextInt(9999);
        int rand_int3 = rand.nextInt(9999);
        int rand_int4 = rand.nextInt(9999);
        int rand_int5 = rand.nextInt(9999);
        int rand_int6 = rand.nextInt(9999);
        int rand_int7 = rand.nextInt(9999);
        int rand_int8 = rand.nextInt(9999);
        int rand_int9 = rand.nextInt(9999);
        
        sijoitusNro = nro;
        tapahtumannimi = "Bitcoin";
        ostopv =  rand_int1;
        ostohinta =  rand_int2;
        ostomaara =  rand_int3;
        kurssi =  rand_int4;
        myyntipv =  rand_int5;
        myyntikurssi =  rand_int6;
        myyntimaara =  rand_int7;
        tuotto =  rand_int8;
        jaljella =  rand_int9;
    }

    /**
     * Tulostetaan tapahtuman tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(tapahtumannimi + " " + ostopv + " " + ostohinta + " " + ostomaara + " " + kurssi + " " + myyntipv + " " + myyntikurssi + " " + myyntimaara + " " + tuotto + " " + jaljella);
    }

    /**
     * Tulostetaan sijoituksen tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }

    /**
     * Antaa tapahtumalle seuraavan rekisterinumeron.
     * @return tapahtuman uusi tunnus_nro
     * @example
     * <pre name="test">
     *   Tapahtuma Bitcoin1 = new Tapahtuma();
     *   Bitcoin1.getTunnusNro() === 0;
     *   Bitcoin1.rekisteroi();
     *   Tapahtuma Bitcoin2 = new Tapahtuma();
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
     * Palautetaan tapahtuman oma id
     * @return tapahtuman id
     */
    public int getTunnusNro() {
        return tunnusNro;
    }

    /**
     * Palautetaan mille jäsenelle harrastus kuuluu
     * @return sijoituksen id
     */
    public int getSijoitusNro() {
        return sijoitusNro;
    }
    
    /**
     * 
     * @return tapahtuman nimi
     */
    public String getNimi() {
        return tapahtumannimi;
    }
    
    /**
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if ( tunnusNro >= seuraavaNro ) seuraavaNro = tunnusNro + 1;
    }



    /**
     * Palauttaa tapahtuman tiedot merkkijonona, jonka voi tallentaa tiedostoon.
     * @return tapahtuma tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Tapahtuma tapahtuma = new Tapahtuma();
     *   tapahtuma.parse("2|10|Bitcoin|20200101|35000|2|40000|20210101|45000|1|10000|1");
     *   tapahtuma.getSijoitusNro() === 10;
     *   tapahtuma.toString() === "2|10|Bitcoin|20200101|35000|2|40000|20210101|45000|1|10000|1";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + getTunnusNro() + "|" + sijoitusNro + "|" + tapahtumannimi + "|" + ostopv + "|" + ostohinta + "|" + ostomaara + "|" + kurssi + "|" + myyntipv + "|" + myyntikurssi + "|" + myyntimaara + "|" + tuotto + "|" + jaljella;
    }


    /**
     * Selvitää tapahtuman tiedot |-erotellusta merkkijonosta.
     * Pitää huolen, että seuraavaNro on suurempi kuin tuleva tunnusnro.
     * @param rivi josta tapahtuman tiedot otetaan
     * @example
     * <pre name="test">
     *   Tapahtuma tapahtuma = new Tapahtuma();
     *   tapahtuma.parse("2|10|Bitcoin|20200101|35000|2|40000|20210101|45000|1|10000|1");
     *   tapahtuma.getSijoitusNro() === 10;
     *   tapahtuma.toString() === "2|10|Bitcoin|20200101|35000|2|40000|20210101|45000|1|10000|1";
     *   
     *   tapahtuma.rekisteroi();
     *   int n = tapahtuma.getTunnusNro();
     *   tapahtuma.parse((n+20) + "|10|Bitcoin|20200101|35000|2|40000|20210101|45000|1|10000|1");
     *   tapahtuma.rekisteroi();
     *   tapahtuma.getTunnusNro() === n+20+1;
     *   tapahtuma.toString() === "" + (n+20+1) + "|10|Bitcoin|20200101|35000|2|40000|20210101|45000|1|10000|1";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        sijoitusNro    = Mjonot.erota(sb, '|', sijoitusNro);
        tapahtumannimi = Mjonot.erota(sb, '|', tapahtumannimi);
        ostopv         = Mjonot.erota(sb, '|', ostopv);
        ostohinta      = Mjonot.erota(sb, '|', ostohinta);
        ostomaara      = Mjonot.erota(sb, '|', ostomaara);
        kurssi         = Mjonot.erota(sb, '|', kurssi);
        myyntipv       = Mjonot.erota(sb, '|', myyntipv);
        myyntikurssi   = Mjonot.erota(sb, '|', myyntikurssi);
        myyntimaara    = Mjonot.erota(sb, '|', myyntimaara);
        tuotto         = Mjonot.erota(sb, '|', tuotto);
        jaljella       = Mjonot.erota(sb, '|', jaljella);
    }


    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) return false;
        return this.toString().equals(obj.toString());
    }
    

    @Override
    public int hashCode() {
        return tunnusNro;
    }

    

    /**
     * Testiohjelma Tapahtumalle.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Tapahtuma tap = new Tapahtuma();
        tap.vastaaBitcoin(2);
        tap.tulosta(System.out);
    }

    
}    
    
    
    
    

