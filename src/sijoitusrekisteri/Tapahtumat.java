package sijoitusrekisteri;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


/**
 * @author valla
 * @version 17.3.2025
 *
 */
public class Tapahtumat implements Iterable<Tapahtuma> {

    private boolean muutettu = false;
    private String tiedostonPerusNimi = "sijoitusrekisteri\\tapahtumat";


    /** Taulukko harrastuksista */
    private final Collection<Tapahtuma> alkiot = new ArrayList<Tapahtuma>();


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
        muutettu = true;
    }
    
    /**
     * @param tapahtuma tapahtuma, jonka nimeä muutetaan
     * @param uusiTapahtumaNimi pellon uusi nimi
     */
    public void muutatapNimi(Tapahtuma tapahtuma, String uusiTapahtumaNimi) {
        int nro = tapahtuma.getTunnusNro();
        for (Tapahtuma tap : alkiot) {
            if (tap.getTunnusNro() == nro) {
                tap.muutatapNimi(uusiTapahtumaNimi);
                muutettu = true;
                return;                
            }
        }
        lisaa(tapahtuma);
    }
    
    
    /**
     * Poistaa kaikki tietyn tietyn sijoituksen tapahtumat
     * @param tunnusNro viite siihen, mihin liittyvät tietueet poistetaan
     * @return montako poistettiin 
    */
   public int poistaSijoituksenTapahtumat(int tunnusNro) {
       int n = 0;
       for (Iterator<Tapahtuma> it = alkiot.iterator(); it.hasNext();) {
           Tapahtuma tap = it.next();
           if ( tap.getSijoitusNro() == tunnusNro ) {
               it.remove();
               n++;
           }
       }
       if (n > 0) muutettu = true;
       return n;
   }


 
    /**
     * Lukee tapahtumat tiedostosta.  
     * @param tied tiedoston nimen alkuosa
     * @throws SailoException jos lukeminen epäonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  Tapahtumat tapahtumaat = new Tapahtumat();
     *  Tapahtuma bitcoin21 = new Tapahtuma(); bitcoin21.vastaaBitcoin(2);
     *  Tapahtuma bitcoin11 = new Tapahtuma(); bitcoin11.vastaaBitcoin(1);
     *  Tapahtuma bitcoin22 = new Tapahtuma(); bitcoin22.vastaaBitcoin(2); 
     *  Tapahtuma bitcoin12 = new Tapahtuma(); bitcoin12.vastaaBitcoin(1); 
     *  Tapahtuma bitcoin23 = new Tapahtuma(); bitcoin23.vastaaBitcoin(2); 
     *  String tiedNimi = "sijoitusrekisteri\\testitiedosto\\tapahtumatTest";
     *  File ftied = new File(tiedNimi+".dat");
     *  //ftied.delete();
     *  //tapahtumaat.lueTiedostosta(ftied.getName()); #THROWS SailoException
     *  tapahtumaat.lisaa(bitcoin21);
     *  tapahtumaat.lisaa(bitcoin11);
     *  tapahtumaat.lisaa(bitcoin22);
     *  tapahtumaat.lisaa(bitcoin12);
     *  tapahtumaat.lisaa(bitcoin23);
     *  tapahtumaat.tallenna();
     *  //tapahtumaat = new Tapahtumat();
     *  tapahtumaat.lueTiedostosta(tiedNimi + ".dat");
     *  Iterator<Tapahtuma> i = tapahtumaat.iterator();
     *  i.next().toString() === bitcoin21.toString();
     *  i.next().toString() === bitcoin11.toString();
     *  i.next().toString() === bitcoin22.toString();
     *  i.next().toString() === bitcoin12.toString();
     *  i.next().toString() === bitcoin23.toString();
     *  i.hasNext() === false;
     *  tapahtumaat.lisaa(bitcoin23);
     *  tapahtumaat.tallenna();
     *  ftied.delete() === true;
     * </pre>
     */

    public void lueTiedostosta(String tied) throws SailoException {
        try ( BufferedReader fi = new BufferedReader(new FileReader(tied)) ) {

            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Tapahtuma tap = new Tapahtuma();
                tap.parse(rivi); // voisi olla virhekäsittely
                lisaa(tap);
            }
            muutettu = false;

        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + tied + " ei aukea");
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
     * Tallentaa tapahtuman tiedostoon.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); //  if ... System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); //  if ... System.err.println("Ei voi nimetä");

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Tapahtuma tap : this) {
                fo.println(tap.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;
    }


    /**
     * Palauttaa Sijoitusrekisterin tapahtumien lukumäärän
     * @return tapahtumien lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }
    
//    /**
//     * Asettaa tiedoston perusnimen ilan tarkenninta
//     * @param tied tallennustiedoston perusnimi
//     */
//    public void setTiedostonPerusNimi(String tied) {
//        tiedostonPerusNimi = tied;
//    }
    

    
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return tiedostonPerusNimi + ".dat";
    }

    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
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
