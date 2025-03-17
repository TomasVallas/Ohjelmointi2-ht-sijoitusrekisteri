package sijoitusrekisteri;


/**
 * @author valla
 * @version 16.3.2025
 *
 */
public class Sijoitukset {
    private static final int MAX_SIJOITUKSIA   = 10;
    private int              lkm           = 0;
    private String           tiedostonNimi = "";
    private Sijoitus            alkiot[]      = new Sijoitus[MAX_SIJOITUKSIA];


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
     * sijoitukset.anna(3) === Binance1; #THROWS IndexOutOfBoundsException 
     * sijoitukset.lisaa(Binance1); sijoitukset.getLkm() === 4;
     * sijoitukset.lisaa(Binance1); sijoitukset.getLkm() === 5;
     * sijoitukset.lisaa(Binance1); sijoitukset.getLkm() === 6;
     * sijoitukset.lisaa(Binance1); sijoitukset.getLkm() === 7;
     * sijoitukset.lisaa(Binance1); sijoitukset.getLkm() === 8;
     * sijoitukset.lisaa(Binance1); sijoitukset.getLkm() === 9;
     * sijoitukset.lisaa(Binance1); sijoitukset.getLkm() === 10;
     * sijoitukset.lisaa(Binance1);  #THROWS SailoException
     * </pre>
     */
    public void lisaa(Sijoitus sijoitus) throws SailoException {
        if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
        alkiot[lkm] = sijoitus;
        lkm++;
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
     * Lukee sijoituksien tiedostosta.  Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/sijoitukset.dat";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }


    /**
     * Tallentaa sijoituksien tiedostoon.  Kesken.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }


    /**
     * Palauttaa kerhon sijoitusten lukumäärän
     * @return sijoitusten lukumäärä
     */
    public int getLkm() {
        return lkm;
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

            System.out.println("============= Jäsenet testi =================");

            for (int i = 0; i < sijoitukset.getLkm(); i++) {
                Sijoitus sijoitus = sijoitukset.anna(i);
                System.out.println("Sijoitus nro: " + i);
                sijoitus.tulosta(System.out);
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }

}

