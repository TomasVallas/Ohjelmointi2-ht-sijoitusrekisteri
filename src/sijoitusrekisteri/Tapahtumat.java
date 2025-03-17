package sijoitusrekisteri;
import java.util.*;


/**
 * @author valla
 * @version 17.3.2025
 *
 */
public class Tapahtumat implements Iterable<Tapahtuma> {

    private String                      tiedostonNimi = "";

    /** Taulukko harrastuksista */
    private final Collection<Tapahtuma> alkiot        = new ArrayList<Tapahtuma>();


    /**
     * Harrastusten alustaminen
     */
    public Tapahtumat() {
        // toistaiseksi ei tarvitse tehdä mitään
    }

    /**
     * Lisää uuden tapahtuman tietorakenteeseen.  Ottaa tapahtuman omistukseensa.
     * @param tap lisättävä tapahtuma.  Huom tietorakenne muuttuu omistajaksi
     */
    public void lisaa(Tapahtuma tap) {
        alkiot.add(tap);
    }
 
    /**
     * Lukee sijoituksien tiedostosta.  
     * TODO Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + ".tap";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }

    /**
     * Tallentaa sijoituksien tiedostoon.  
     * TODO Kesken.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }

    /**
     * Palauttaa Sijoitusrekisterin tapahtumien lukumäärän
     * @return tapahtumien lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }

    /**
     * Iteraattori kaikkien harrastusten läpikäymiseen
     * @return harrastusiteraattori
     * 
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     *  Tapahtumat tapahtumaat = new Tapahtumat();
     *  Tapahtuma Bitcoin21 = new Tapahtuma(2); tapahtumaat.lisaa(Bitcoin21);
     *  Tapahtuma Bitcoin11 = new Tapahtuma(1); tapahtumaat.lisaa(Bitcoin11);
     *  Tapahtuma Bitcoin22 = new Tapahtuma(2); tapahtumaat.lisaa(Bitcoin22);
     *  Tapahtuma Bitcoin12 = new Tapahtuma(1); tapahtumaat.lisaa(Bitcoin12);
     *  Tapahtuma Bitcoin23 = new Tapahtuma(2); tapahtumaat.lisaa(Bitcoin23);
     * 
     *  Iterator<Tapahtuma> i2=tapahtumaat.iterator();
     *  i2.next() === Bitcoin21;
     *  i2.next() === Bitcoin11;
     *  i2.next() === Bitcoin22;
     *  i2.next() === Bitcoin12;
     *  i2.next() === Bitcoin23;
     *  i2.next() === Bitcoin12;  #THROWS NoSuchElementException  
     *  
     *  int n = 0;
     *  int jnrot[] = {2,1,2,1,2};
     *  
     *  for ( Tapahtuma tap:tapahtumaat ) { 
     *    tap.getSijoitusNro() === jnrot[n]; n++;  
     *  }
     *  
     *  n === 5;
     *  
     * </pre>
     */
    @Override
    public Iterator<Tapahtuma> iterator() {
        return alkiot.iterator();
    }

    /**
     * Haetaan kaikki sijoitus Tapahtumat
     * @param tunnusnro sijoituksen tunnusnumero jolle tapahtumia haetaan
     * @return tietorakenne jossa viiteet löydetteyihin tapahtumiin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Tapahtumat tapahtumaat = new Tapahtumat();
     *  Tapahtuma Bitcoin21 = new Tapahtuma(2); tapahtumaat.lisaa(Bitcoin21);
     *  Tapahtuma Bitcoin11 = new Tapahtuma(1); tapahtumaat.lisaa(Bitcoin11);
     *  Tapahtuma Bitcoin22 = new Tapahtuma(2); tapahtumaat.lisaa(Bitcoin22);
     *  Tapahtuma Bitcoin12 = new Tapahtuma(1); tapahtumaat.lisaa(Bitcoin12);
     *  Tapahtuma Bitcoin23 = new Tapahtuma(2); tapahtumaat.lisaa(Bitcoin23);
     *  Tapahtuma Bitcoin51 = new Tapahtuma(5); tapahtumaat.lisaa(Bitcoin51);
     *  
     *  List<Tapahtuma> loytyneet;
     *  loytyneet = tapahtumaat.annaTapahtumat(3);
     *  loytyneet.size() === 0; 
     *  loytyneet = tapahtumaat.annaTapahtumat(1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == Bitcoin11 === true;
     *  loytyneet.get(1) == Bitcoin12 === true;
     *  loytyneet = tapahtumaat.annaTapahtumat(5);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) == Bitcoin51 === true;
     * </pre> 
     */
    public List<Tapahtuma> annaTapahtumat(int tunnusnro) {
        List<Tapahtuma> loydetyt = new ArrayList<Tapahtuma>();
        for (Tapahtuma tap : alkiot)
            if (tap.getSijoitusNro() == tunnusnro) loydetyt.add(tap);
        return loydetyt;
    }
    
    /**
     * Testiohjelma tapahtumille
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Tapahtumat tapahtumaat = new Tapahtumat();
        Tapahtuma Bitcoin1 = new Tapahtuma();
        Bitcoin1.vastaaBitcoin(2);
        Tapahtuma Bitcoin2 = new Tapahtuma();
        Bitcoin2.vastaaBitcoin(1);
        Tapahtuma Bitcoin3 = new Tapahtuma();
        Bitcoin3.vastaaBitcoin(2);
        Tapahtuma Bitcoin4 = new Tapahtuma();
        Bitcoin4.vastaaBitcoin(2);

        tapahtumaat.lisaa(Bitcoin1);
        tapahtumaat.lisaa(Bitcoin2);
        tapahtumaat.lisaa(Bitcoin3);
        tapahtumaat.lisaa(Bitcoin2);
        tapahtumaat.lisaa(Bitcoin4);

        System.out.println("============= Tapahtumat testi =================");

        List<Tapahtuma> tapahtumat2 = tapahtumaat.annaTapahtumat(2);

        for (Tapahtuma tap : tapahtumat2) {
            System.out.print(tap.getSijoitusNro() + " ");
            tap.tulosta(System.out);
        }

    }
 
}
