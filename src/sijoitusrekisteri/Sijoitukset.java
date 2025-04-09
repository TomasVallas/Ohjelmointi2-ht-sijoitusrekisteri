package sijoitusrekisteri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * @author valla
 * @version 16.3.2025
 *
 */
public class Sijoitukset implements Iterable<Sijoitus> {
    private static final int MAX_SIJOITUKSIA   = 10;
    private boolean muutettu = false;
    private int lkm = 0;
    private String tiedNimi = "sijoitusrekisteri\\sijoitukset";
    private Sijoitus alkiot[] = new Sijoitus[MAX_SIJOITUKSIA];


    /**
     * Oletusmuodostaja
     */
    public Sijoitukset() {
        // Attribuuttien oma alustus riittää
    }
    
    /**
     * Lisää uuden sijoituksen tietorakenteeseen.  Ottaa sijoituksen omistukseensa.
     * @param sijoitus lisätäävän sijoituksen viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Sijoitukset sijoitukset = new Sijoitukset();
     * Sijoitus Binance1 = new Sijoitus(), Binance2 = new Sijoitus();
     * sijoitukset.getLkm() === 0;
     * sijoitukset.lisaa(Binance1); sijoitukset.getLkm() === 1;
     * sijoitukset.lisaa(Binance2); sijoitukset.getLkm() === 2;
     * sijoitukset.lisaa(Binance1); sijoitukset.getLkm() === 3;
     * sijoitukset.anna(0) === Binance1;
     * sijoitukset.anna(1) === Binance2;
     * sijoitukset.anna(2) === Binance1;
     * sijoitukset.anna(1) == Binance1 === false;
     * sijoitukset.anna(1) == Binance2 === true;
     * sijoitukset.lisaa(Binance1); sijoitukset.getLkm() === 4;
     * sijoitukset.lisaa(Binance1); sijoitukset.getLkm() === 5;
     * sijoitukset.lisaa(Binance1); sijoitukset.getLkm() === 6;
     * sijoitukset.lisaa(Binance1); sijoitukset.getLkm() === 7;
     * sijoitukset.lisaa(Binance1); sijoitukset.getLkm() === 8;
     * sijoitukset.lisaa(Binance1); sijoitukset.getLkm() === 9;
     * sijoitukset.lisaa(Binance1); sijoitukset.getLkm() === 10;
     * </pre>
     */
    public void lisaa(Sijoitus sijoitus) throws SailoException {
        if (lkm >= alkiot.length) alkiot = Arrays.copyOf(alkiot, lkm+20);
        alkiot[lkm] = sijoitus;
        lkm++;
        muutettu = true;
    }
    
    /**
     * Palauttaa viitteen i:teen sijoitukseen.
     * @param i monennenko sijoituksen viite halutaan
     * @return viite sijoitukseen, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    public Sijoitus anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }

    /**
     * Lukee sijoituksien tiedostosta.
     * @param tiedosto tiedoston perusnimi
     * @throws SailoException jos lukeminen epäonnistuu
    * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * #import java.util.Iterator;
     * 
     *  Sijoitukset sijoitukset = new Sijoitukset();
     *  Sijoitus Binance1 = new Sijoitus(), Binance2 = new Sijoitus();
     *  Binance1.vastaaBinance();
     *  Binance2.vastaaBinance();
     *  String tiedNimi = "sijoitusrekisteri\\testitiedosto\\sijoituksetTest";
     *  File ftied = new File(tiedNimi+".dat");
     *  //ftied.delete();
     *  //sijoitukset.lueTiedostosta(ftied.getName()); #THROWS SailoException
     *  sijoitukset.lisaa(Binance1);
     *  sijoitukset.lisaa(Binance2);
     *  sijoitukset.tallenna();
     *  //sijoitukset = new Sijoitukset();            // Poistetaan vanhat luomalla uusi
     *  sijoitukset.lueTiedostosta(tiedNimi + ".dat");  // johon ladataan tiedot tiedostosta.
     *  Iterator<Sijoitus> i = sijoitukset.iterator();
     *  i.next() === Binance1;
     *  i.next() === Binance2;
     *  i.hasNext() === false;
     *  sijoitukset.lisaa(Binance2);
     *  sijoitukset.tallenna();
     *  ftied.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tiedosto) throws SailoException {
        try ( BufferedReader fi = new BufferedReader(new FileReader(tiedosto)) ) {
           String rivi = fi.readLine();
            if ( rivi == null ) throw new SailoException("Maksimikoko puuttuu");

            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Sijoitus sijoitus= new Sijoitus();
                sijoitus.parse(rivi);
                lisaa(sijoitus);
            }
            muutettu = false;
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + tiedosto + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }

    
    /**
     * Luetaan aikaisemmin annetun nimisestä tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonNimi());
    }



    /**
     * Tallentaa sijoitukset tiedostoon.  
     * Tiedoston muoto:
     * <pre>
     * Sijoitusrekisteri
     * 20
     * ; kommenttirivi
     * 2|Binance
     * 3|Nordnet
     * </pre>
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); // if .. System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); // if .. System.err.println("Ei voi nimetä");

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            fo.println(alkiot.length);
            for (Sijoitus sijoitus : this) {
                fo.println(sijoitus.toString());
            }
            //} catch ( IOException e ) { // ei heitä poikkeusta
            //  throw new SailoException("Tallettamisessa ongelmia: " + e.getMessage());
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;
    }


    /**
     * Palauttaa Kerhon koko nimen
     * @return Kerhon koko nimi merkkijononna
     */


    /**
     * Palauttaa kerhon sijoitusten lukumäärän
     * @return sijoitusten lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    



//    /**
//     * Asettaa tiedoston perusnimen ilan tarkenninta
//     * @param nimi tallennustiedoston perusnimi
//     */
//    public void setTiedostonNimi(String nimi) {
//        tiedNimi = nimi;
//    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return tiedNimi + ".dat";
    }


    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedNimi + ".bak";
    }
    
    /**
     * Luokka sijoitusten iteroimiseksi.
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Sijoitukset sijoitukset = new Sijoitukset();
     * Sijoitus Binance1 = new Sijoitus(), Binance2 = new Sijoitus();
     * Binance1.rekisteroi(); Binance2.rekisteroi();
     *
     * sijoitukset.lisaa(Binance1); 
     * sijoitukset.lisaa(Binance2); 
     * sijoitukset.lisaa(Binance1); 
     * 
     * StringBuffer ids = new StringBuffer(30);
     * for (Sijoitus sijoitus:sijoitukset)   // Kokeillaan for-silmukan toimintaa
     *   ids.append(" "+sijoitus.getTunnusNro());           
     * 
     * String tulos = " " + Binance1.getTunnusNro() + " " + Binance2.getTunnusNro() + " " + Binance1.getTunnusNro();
     * 
     * ids.toString() === tulos; 
     * 
     * ids = new StringBuffer(30);
     * for (Iterator<Sijoitus>  i=sijoitukset.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
     *   Sijoitus sijoitus = i.next();
     *   ids.append(" "+sijoitus.getTunnusNro());           
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<Sijoitus>  i=sijoitukset.iterator();
     * i.next() == Binance1  === true;
     * i.next() == Binance2  === true;
     * i.next() == Binance1  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     *  
     * </pre>
     */
    public class SijoituksetIterator implements Iterator<Sijoitus> {
        private int kohdalla = 0;


        /**
         * Onko olemassa vielä seuraavaa sijoitusta
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä sijoituksia
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Annetaan seuraava sijoitus
         * @return seuraava sijoitus
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Sijoitus next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
            return anna(kohdalla++);
        }


        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Ei poisteta");
        }
    }


    /**
     * Palautetaan iteraattori sijoituksiin.
     * @return sijoitus iteraattori
     */
    @Override
    public Iterator<Sijoitus> iterator() {
        return new SijoituksetIterator();
    }


    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien sijoitusten viitteet 
     * @param hakuehto hakuehto 
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä jäsenistä 
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     *   Sijoitukset  sijoitukset = new Sijoitukset (); 
     *   Sijoitus sijoitus1 = new Sijoitus(); sijoitus1.parse("1|Binance"); 
     *   Sijoitus sijoitus2 = new Sijoitus(); sijoitus2.parse("2|Nordea"); 
     *   Sijoitus sijoitus3 = new Sijoitus(); sijoitus3.parse("3|Nordnet"); 
     *   Sijoitus sijoitus4 = new Sijoitus(); sijoitus4.parse("4|Binance"); 
     *   Sijoitus sijoitus5 = new Sijoitus(); sijoitus5.parse("5|Binance"); 
     *   sijoitukset.lisaa(sijoitus1); sijoitukset.lisaa(sijoitus2); sijoitukset.lisaa(sijoitus3); sijoitukset.lisaa(sijoitus4); sijoitukset.lisaa(sijoitus5);
     *   // TODO: toistaiseksi palauttaa kaikki sijoitukset 
     * </pre> 
     */ 
    @SuppressWarnings("unused")
    public Collection<Sijoitus> etsi(String hakuehto, int k) { 
        Collection<Sijoitus> loytyneet = new ArrayList<Sijoitus>(); 
        for (Sijoitus sijoitus : this) { 
            loytyneet.add(sijoitus);  
        } 
        return loytyneet; 
    }



    /**
     * Testiohjelma sijoituksille
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Sijoitukset sijoitukset = new Sijoitukset();

        Sijoitus Binance = new Sijoitus(), Binance2 = new Sijoitus();
        Binance.rekisteroi();
        Binance.vastaaBinance();
        Binance.rekisteroi();
        Binance2.vastaaBinance();

        try {
            sijoitukset.lisaa(Binance);
            sijoitukset.lisaa(Binance2);

            System.out.println("============= Sijoitukset testi =================");

            for (int i = 0; i < sijoitukset.getLkm(); i++) {
                Sijoitus sijoitus = sijoitukset.anna(i);
                System.out.println("Sijoitus nro: " + i);
                sijoitus.tulosta(System.out);
            }

        } catch ( SailoException ex ) {
            System.out.println(ex.getMessage());
        }
    }

}

