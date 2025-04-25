package sijoitusrekisteri;
import java.io.*;
import java.util.Random;
import fi.jyu.mit.ohj2.Mjonot;



/**
 * Tapahtuma joka osaa mm. itse huolehtia tunnus_nro:staan.
 * 
 * @author valla
 * @version 25.4.2025
 *
 */
public class Tapahtuma implements Cloneable, Tietue {
    // Nykyiset kentät säilyvät
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
     * @return harrastukse kenttien lukumäärä
     */

    @Override
    public int getKenttia() {
        return 12; // Kaikki kentät mukaan lukien tunnusNro ja sijoitusNro
    }
    /**
     * @return ensimmäinen käyttäjän syötettävän kentän indeksi
     */

    @Override
    public int ekaKentta() {
        return 2; // Ensimmäinen käyttäjän syötettävä kenttä (tapahtumannimi)
    }
    /**
     * @param k minkä kentän kysymys halutaan
     * @return valitun kentän kysymysteksti
     */

    @Override
    public String getKysymys(int k) {
        switch (k) {
            case 0: return "Tunnusnro";
            case 1: return "Sijoitusnro";
            case 2: return "Nimi";
            case 3: return "Ostopäivä (pvk)";
            case 4: return "Ostohinta (€)";
            case 5: return "Ostomäärä";
            case 6: return "Kurssi (€)";
            case 7: return "Myyntipäivä (pvk)";
            case 8: return "Myyntikurssi (€)";
            case 9: return "Myyntimäärä";
            case 10: return "Tuotto (€)";
            case 11: return "Jäljellä";
            default: return "???"; 
        }
    }

    /**
     * @param k Minkä kentän sisältö halutaan
     * @return valitun kentän sisältö
    */
    @Override
    public String anna(int k) {
        switch (k) {
            case 0: return "" + tunnusNro;
            case 1: return "" + sijoitusNro;
            case 2: return tapahtumannimi;
            case 3: return "" + ostopv;
            case 4: return "" + ostohinta;
            case 5: return "" + ostomaara;
            case 6: return "" + kurssi;
            case 7: return "" + myyntipv;
            case 8: return "" + myyntikurssi;
            case 9: return "" + myyntimaara;
            case 10: return "" + tuotto;
            case 11: return "" + jaljella;
            default: return "???";
        }
    }
    
    /**
     * Palauttaa ostopäivän muotoiltuna merkkijonona xx.xx.xxxx
     * @return muotoiltu päivämäärä tai "-", jos päivää ei ole asetettu
     */
    public String getOstopvStr() {
        if (ostopv <= 0) return "-";
        String date = String.format("%08d", ostopv);
        return date.substring(0, 2) + "." + date.substring(2, 4) + "." + date.substring(4);
    }
    
    /**
     * Palauttaa myyntipäivän muotoiltuna merkkijonona xx.xx.xxxx
     * @return muotoiltu päivämäärä tai "-", jos päivää ei ole asetettu
     */
    public String getMyyntipvStr() {
        if (myyntipv <= 0) return "-";
        String date = String.format("%08d", myyntipv);
        return date.substring(0, 2) + "." + date.substring(2, 4) + "." + date.substring(4);
    }
    
    
    /**
     * Asetetaan valitun kentän sisältö.  Mikäli asettaminen onnistuu,
     * palautetaan null, muutoin virheteksti.
     * @param k minkä kentän sisältö asetetaan
     * @param s asetettava sisältö merkkijonona
     * @return null jos ok, muuten virheteksti
     */
    @Override
    public String aseta(int k, String s) {
        String st = s.trim();
        StringBuffer sb = new StringBuffer(st);
        switch (k) {
            case 0: setTunnusNro(Mjonot.erota(sb, '$', getTunnusNro())); return null;
            case 1: sijoitusNro = Mjonot.erota(sb, '$', sijoitusNro); return null;
            case 2: tapahtumannimi = st; return null;
            case 3: 
                if (!st.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) { // Tarkista muoto xx.xx.xxxx
                    return "Ostopäivän tulee olla muotoa xx.xx.xxxx";
                }
                st = st.replace(".", ""); // Poista pisteet
                if (st.length() != 8) return "Ostopäivässä tulee olla tasan 8 numeroa";
                sb = new StringBuffer(st);
                ostopv = Mjonot.erota(sb, '$', ostopv);
                return null;
            case 4: ostohinta = Mjonot.erota(sb, '$', ostohinta); return null;
            case 5: ostomaara = Mjonot.erota(sb, '$', ostomaara); return null;
            case 6: kurssi = Mjonot.erota(sb, '$', kurssi); return null;
            case 7:
                if (st.isEmpty() || st.equals("-")) { // Salli tyhjä tai "-"
                    myyntipv = 0;
                    return null;
                }
                if (!st.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) { // Tarkista muoto
                    return "Myyntipäivän tulee olla muotoa xx.xx.xxxx";
                }
                st = st.replace(".", "");
                if (st.length() != 8) return "Myyntipäivässä tulee olla tasan 8 numeroa";
                sb = new StringBuffer(st);
                myyntipv = Mjonot.erota(sb, '$', myyntipv);
                return null;
            case 8: myyntikurssi = Mjonot.erota(sb, '$', myyntikurssi); return null;
            case 9: myyntimaara = Mjonot.erota(sb, '$', myyntimaara); return null;
            case 10: tuotto = Mjonot.erota(sb, '$', tuotto); return null;
            case 11: jaljella = Mjonot.erota(sb, '$', jaljella); return null;
            default: return "Väärä kentän indeksi";
        }
    }

    @Override
    public Tapahtuma clone() throws CloneNotSupportedException {
        return (Tapahtuma)super.clone();
    }
    
    // Muut metodit säilyvät ennallaan
    
    /**
     * Alustetaan tapahtuma.  Toistaiseksi ei tarvitse tehdä mitään
     */
    public Tapahtuma() {
        // Vielä ei tarvita mitään
    }
    
    /**
     * @return ostopv
     */
    public int getOstopv() { 
        return ostopv; 
        }
    
    /**
     * @return ostohinta
     */
    public int getOstohinta() { 
        return ostohinta; 
        }
    
    /**
     * @return ostomaara
     */
    public int getOstomaara() { 
        return ostomaara; 
        }
    
    /**
     * @return kurssi
     */
    public int getKurssi() { 
        return kurssi; 
        }
    
    /**
     * @return myyntipv
     */
    public int getMyyntipv() { 
        return myyntipv; 
        }
    
    /**
     * @return myyntikurssi
     */
    public int getMyyntikurssi() { 
        return myyntikurssi; 
        }
    
    /**
     * @return myyntimaara
     */
    public int getMyyntimaara() { 
        return myyntimaara; 
        }
    
    /**
     * @return tuotto
     */
    public int getTuotto() { 
        return tuotto; 
        }
    
    /**
     * @return jaljella
     */
    public int getJaljella() { 
        return jaljella; 
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
     * @param nro sijoituksen numero
     * @param uusiTapahtumaNimi nimi joksi sijoituksen nimi muutetaan
     */
    public void asetatapNimi(int nro, String uusiTapahtumaNimi) {
        sijoitusNro = nro;
        tapahtumannimi = uusiTapahtumaNimi;
    }
    
    
    
    /**
     * @param uusiTapahtumaNimi nimi joksi sijoituksen nimi muutetaan
     */
    public void muutatapNimi( String uusiTapahtumaNimi) {
        tapahtumannimi = uusiTapahtumaNimi;
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
    
    
    
    

