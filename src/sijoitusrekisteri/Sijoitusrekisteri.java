package sijoitusrekisteri;
import java.util.List;

/**
 * @author valla
 * @version 16.3.2025
 *
 */
public class Sijoitusrekisteri {
    
    private final Sijoitukset sijoitukset = new Sijoitukset();
    private final Tapahtumat tapahtumat = new Tapahtumat(); 

    

    
    /**
     * Palautaa sijoitusrekisterin sijoitusmäärän
     * @return sijoitusmäärä
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
     * Binance1.rekisteroi(); Binance2.rekisteroi();
     * sijoitusrekisteri.getSijoituksia() === 0;
     * sijoitusrekisteri.lisaa(Binance1); sijoitusrekisteri.getSijoituksia() === 1;
     * sijoitusrekisteri.lisaa(Binance2); sijoitusrekisteri.getSijoituksia() === 2;
     * sijoitusrekisteri.lisaa(Binance1); sijoitusrekisteri.getSijoituksia() === 3;
     * sijoitusrekisteri.getSijoituksia() === 3;
     * sijoitusrekisteri.annaSijoitus(0) === Binance1;
     * sijoitusrekisteri.annaSijoitus(1) === Binance2;
     * sijoitusrekisteri.annaSijoitus(2) === Binance1;
     * sijoitusrekisteri.annaSijoitus(3) === Binance1; #THROWS IndexOutOfBoundsException 
     * sijoitusrekisteri.lisaa(Binance1); sijoitusrekisteri.getSijoituksia() === 4;
     * sijoitusrekisteri.lisaa(Binance1); sijoitusrekisteri.getSijoituksia() === 5;
     * sijoitusrekisteri.lisaa(Binance1); sijoitusrekisteri.getSijoituksia() === 6;
     * sijoitusrekisteri.lisaa(Binance1); sijoitusrekisteri.getSijoituksia() === 7;
     * sijoitusrekisteri.lisaa(Binance1); sijoitusrekisteri.getSijoituksia() === 8;
     * sijoitusrekisteri.lisaa(Binance1); sijoitusrekisteri.getSijoituksia() === 9;
     * sijoitusrekisteri.lisaa(Binance1); sijoitusrekisteri.getSijoituksia() === 10;
     * sijoitusrekisteri.lisaa(Binance1);            #THROWS SailoException
     * </pre>
     */
    public void lisaa(Sijoitus sijoitus) throws SailoException {
        sijoitukset.lisaa(sijoitus);
    }
    
    /**
     * Listään uusi tapahtuma sijoitusrekisteriin
     * @param tap lisättävä tapahtuma 
     */
    public void lisaa(Tapahtuma tap) {
        tapahtumat.lisaa(tap);
    }



    /**
     * Palauttaa i:n sijoitusen
     * @param i monesko sijoitus palautetaan
     * @return viite i:teen sijoituseen
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Sijoitus annaSijoitus(int i) throws IndexOutOfBoundsException {
        return sijoitukset.anna(i);
    }
    
    /**
     * Haetaan kaikki sijoitus tapahtumat
     * @param sijoitus sijoitus jolle tapahtumia haetaan
     * @return tietorakenne jossa viiteet löydetteyihin  tapahtumiin
     * @example
     * <pre name="test">
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
     * @param nimi jota käyteään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        sijoitukset.lueTiedostosta(nimi);
        tapahtumat.lueTiedostosta(nimi);
    }


    /**
     * Tallettaa sijoitusrekisterin tiedot tiedostoon
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void talleta() throws SailoException {
        sijoitukset.talleta();
        tapahtumat.talleta();
        // TODO: yritä tallettaa toinen vaikka toinen epäonnistuisi
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
                Sijoitus sijoitus = sijoitusrekisteri.annaSijoitus(i);
                System.out.println("Sijoitus paikassa: " + i);
                sijoitus.tulosta(System.out);
                List<Tapahtuma> loytyneet = sijoitusrekisteri.annaTapahtumat(sijoitus);
                for (Tapahtuma tapahtuma : loytyneet)
                    tapahtuma.tulosta(System.out);
            }



        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
