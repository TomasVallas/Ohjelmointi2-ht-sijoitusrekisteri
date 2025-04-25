package sijoitusrekisteri;
import java.util.List;
import java.util.Collection;

/**
 * Sijoitusrekisteri-luokka, joka huolehtii sijoituksusta.  Pääosin kaikki metodit
 * ovat vain "välittäjämetodeja" sijoituksiin.
 *
 * @author vallas
 * @version 25.4.2025
 *
 */
public class Sijoitusrekisteri {
    
    private Sijoitukset sijoitukset = new Sijoitukset();
    private Tapahtumat tapahtumat = new Tapahtumat(); 

    

    /**
     * Palauttaa sijoitusrekisterin sijoitusten määrän
     * @return sijoitukset määrä
     */
    public int getSijoituksia() {
        return sijoitukset.getLkm();
    }

    
    /**
     * Palauttaa sijoitusrekisterin tapahtumien määrän
     * @return tapahtumien määrä
     */
    public int getTapahtumia() {
        return tapahtumat.getLkm();
    }



    /**
     * Poistaa sijoituksista ja tapahtumista ne joilla on nro. Kesken.
     * @param nro viitenumero, jonka mukaan poistetaan
     * @return montako sijoitusta poistettiin
     */
    public int poista(@SuppressWarnings("unused") int nro) {
        return 0;
    }


    /**
     * Lisää sijoitusrekisteriin uuden sijoitusen
     * @param sijoitus lisättävä sijoitus
     * @throws SailoException jos lisäystä ei voida tehdä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Sijoitusrekisteri sijoitusrekisteri = new Sijoitusrekisteri();
     * Sijoitus Binance1 = new Sijoitus(), Binance2 = new Sijoitus();
     * sijoitusrekisteri.lisaa(Binance1);
     * sijoitusrekisteri.lisaa(Binance2); 
     * sijoitusrekisteri.lisaa(Binance1); 
     * Collection<Sijoitus> loytyneet = sijoitusrekisteri.etsi("",-1); 
     * Iterator<Sijoitus> it = loytyneet.iterator();
     * it.next() === Binance1;
     * it.next() === Binance2;
     * it.next() === Binance1;
     * </pre>
     */
    public void lisaa(Sijoitus sijoitus) throws SailoException {
        sijoitukset.lisaa(sijoitus);
    }
    
    
    /**
     * Palauttaa i:n sijoituksen
     * @param i monesko sijoius palautetaan
     * @return viite i:teen sijoitukseen
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Sijoitus annaSijoitukset(int i) throws IndexOutOfBoundsException {
        return sijoitukset.anna(i);
    }
    
    /**
     * @param sijoitus sijoitus jonka nimeä muutetaan
     * @param uusiSijoitusNimi nimi joka muokatessa annetaan sijoitukselle
     */
    public void muutaSijoitusNimi(Sijoitus sijoitus, String uusiSijoitusNimi) {
        sijoitukset.muutaNimi(sijoitus, uusiSijoitusNimi);
    }

    
    
    /**
     * Listään uusi tapahtuma sijoitusrekisteriin
     * @param tap lisättävä tapahtuma 
     * @throws SailoException jos tulee ongelmia
     */
    public void lisaa(Tapahtuma tap) throws SailoException {
        tapahtumat.lisaa(tap);
    }
    
    /**
     * @param tapahtuma tapahtuma jonka nimeä muutetaan
     * @param uusiTapahtumaNimi nimi joka muokatessa annetaan tapahtumalle
     */
    public void muutaTapahtumaNimi(Tapahtuma tapahtuma, String uusiTapahtumaNimi) {
        tapahtumat.muutatapNimi(tapahtuma, uusiTapahtumaNimi);
    }
    
    
    /**
     * Poistaa jäsenistöstä ja harrasteista jäsenen tiedot 
     * @param sijoitus jäsen jokapoistetaan
     * @return montako jäsentä poistettiin
    */
   public int poista(Sijoitus sijoitus) {
       if ( sijoitus == null ) return 0;
       int ret = sijoitukset.poista(sijoitus.getTunnusNro()); 
       tapahtumat.poistaSijoituksenTapahtumat(sijoitus.getTunnusNro()); 
       return ret; 
   }

   /**
    * Poistaa annetun tapahtuman rekisteristä
    * @param tapahtuma poistettava tapahtuma
    */
   public void poistaTapahtuma(Tapahtuma tapahtuma) { 
       if (tapahtuma == null) return;
       tapahtumat.poista(tapahtuma.getTunnusNro()); 
   }



    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien sijoitusten viitteet 
     * @param hakuehto hakuehto  
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä sijoituksista 
     * @throws SailoException Jos jotakin menee väärin
     */ 
    public Collection<Sijoitus> etsi(String hakuehto, int k) throws SailoException { 
        return sijoitukset.etsi(hakuehto, k); 
    } 

    
    /**
     * Haetaan kaikki sijoitus tapahtumat
     * @param sijoitus sijoitus jolle tapahtumia haetaan
     * @return tietorakenne jossa viiteet löydetteyihin  tapahtumiin
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.util.*;
     * 
     *  Sijoitusrekisteri sijoitusrekisteri = new Sijoitusrekisteri();
     *  Sijoitus Binance1 = new Sijoitus(), Binance2 = new Sijoitus(), Binance3 = new Sijoitus();
     *  Binance1.rekisteroi(); Binance2.rekisteroi(); Binance3.rekisteroi();
     *  int id1 = Binance1.getTunnusNro();
     *  int id2 = Binance2.getTunnusNro();
     *  Tapahtuma Bitcoin11 = new Tapahtuma(id1); sijoitusrekisteri.lisaa(Bitcoin11);
     *  Tapahtuma Bitcoin12 = new Tapahtuma(id1); sijoitusrekisteri.lisaa(Bitcoin12);
     *  Tapahtuma Bitcoin21 = new Tapahtuma(id2); sijoitusrekisteri.lisaa(Bitcoin21);
     *  Tapahtuma Bitcoin22 = new Tapahtuma(id2); sijoitusrekisteri.lisaa(Bitcoin22);
     *  Tapahtuma Bitcoin23 = new Tapahtuma(id2); sijoitusrekisteri.lisaa(Bitcoin23);
     *  
     *  List<Tapahtuma> loytyneet;
     *  loytyneet = sijoitusrekisteri.annaTapahtumat(Binance3);
     *  loytyneet.size() === 0; 
     *  loytyneet = sijoitusrekisteri.annaTapahtumat(Binance1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == Bitcoin11 === true;
     *  loytyneet.get(1) == Bitcoin12 === true;
     *  loytyneet = sijoitusrekisteri.annaTapahtumat(Binance2);
     *  loytyneet.size() === 3; 
     *  loytyneet.get(0) == Bitcoin21 === true;
     * </pre> 
     */
    public List<Tapahtuma> annaTapahtumat(Sijoitus sijoitus) {
        return tapahtumat.annaTapahtumat(sijoitus.getTunnusNro());
    }




    /**
     * Lukee sijoitusrekisterin tiedot tiedostosta
     * @throws SailoException jos lukeminen epäonnistuu
     * 
     */
    public void lueTiedostosta() throws SailoException {
        sijoitukset.lueTiedostosta();
        tapahtumat.lueTiedostosta();
    }


    /**
     * Tallenttaa kerhon tiedot tiedostoon.  
     * Vaikka jäsenten tallettamien epäonistuisi, niin yritetään silti tallettaa
     * harrastuksia ennen poikkeuksen heittämistä.
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            sijoitukset.tallenna();
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }

        try {
            tapahtumat.tallenna();
        } catch ( SailoException ex ) {
            virhe += ex.getMessage();
        }
        if ( !"".equals(virhe) ) throw new SailoException(virhe);
    }
    

    
    /**
     * Korvaa tapahtuman tai lisää uuden jos ei ole
     * @param tapahtuma lisättävä/korvattava tapahtuma
     * @throws SailoException jos tulee ongelmia
     */
    public void korvaaTaiLisaa(Tapahtuma tapahtuma) throws SailoException {
        tapahtumat.poista(tapahtuma.getTunnusNro()); // Poistaa vanhan tunnusnumerolla
        tapahtumat.lisaa(tapahtuma); // Lisää päivitetyn tapahtuman
    }


    /**
     * Testiohjelma sijoitusrekisteristä
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Sijoitusrekisteri sijoitusrekisteri = new Sijoitusrekisteri();

        try {
            // sijoitusrekisteri.lueTiedostosta("kelmit");

            Sijoitus Binance1 = new Sijoitus(), Binance2 = new Sijoitus();
            Binance1.rekisteroi();
            Binance1.vastaaBinance();
            Binance2.rekisteroi();
            Binance2.vastaaBinance();

            sijoitusrekisteri.lisaa(Binance1);
            sijoitusrekisteri.lisaa(Binance2);
            int id1 = Binance1.getTunnusNro();
            int id2 = Binance2.getTunnusNro();
            Tapahtuma Binance11 = new Tapahtuma(id1); Binance11.vastaaBitcoin(id1); sijoitusrekisteri.lisaa(Binance11);
            Tapahtuma Binance12 = new Tapahtuma(id1); Binance12.vastaaBitcoin(id1); sijoitusrekisteri.lisaa(Binance12);
            Tapahtuma Binance21 = new Tapahtuma(id2); Binance21.vastaaBitcoin(id2); sijoitusrekisteri.lisaa(Binance21);
            Tapahtuma Binance22 = new Tapahtuma(id2); Binance22.vastaaBitcoin(id2); sijoitusrekisteri.lisaa(Binance22);
            Tapahtuma Binance23 = new Tapahtuma(id2); Binance23.vastaaBitcoin(id2); sijoitusrekisteri.lisaa(Binance23);


            System.out.println("============= sijoitusrekisterin testi =================");

            for (int i = 0; i < sijoitusrekisteri.getSijoituksia(); i++) {
                Sijoitus sijoitus = sijoitusrekisteri.annaSijoitukset(i);

                System.out.println("Sijoitus paikassa: " + i);
                sijoitus.tulosta(System.out);
                List<Tapahtuma> loytyneet = sijoitusrekisteri.annaTapahtumat(sijoitus);
                for (Tapahtuma tapahtuma : loytyneet)
                    tapahtuma.tulosta(System.out);
                i++;
            }



        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
