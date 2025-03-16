package sijoitusrekisteri;

/**
 * @author valla
 * @version 16.3.2025
 *
 */
public class Sijoitukset {
    private static final int MAX_SIJOITUKSIA   = 100;
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
     * Jasenet sijoitukset = new Sijoitus();
     * Sijoitus Bitcoin1 = new Sijoitus(), Bitcoin2 = new Sijoitus();
     * Sijoitukset.getLkm() === 0;
     * Sijoitukset.lisaa(Bitcoin1); jasenet.getLkm() === 1;
     * Sijoitukset.lisaa(Bitcoin2); jasenet.getLkm() === 2;
     * Sijoitukset.lisaa(Bitcoin1); jasenet.getLkm() === 3;
     * Sijoitukset.anna(0) === Bitcoin1;
     * Sijoitukset.anna(1) === Bitcoin2;
     * Sijoitukset.anna(2) === Bitcoin1;
     * Sijoitukset.anna(1) == Bitcoin1 === false;
     * Sijoitukset.anna(1) == Bitcoin2 === true;
     * Sijoitukset.anna(3) === Bitcoin1; #THROWS IndexOutOfBoundsException 
     * Sijoitukset.lisaa(Bitcoin1); sijoitukset.getLkm() === 4;
     * Sijoitukset.lisaa(Bitcoin1); sijoitukset.getLkm() === 5;
     * Sijoitukset.lisaa(Bitcoin1);  #THROWS SailoException
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

        Sijoitus Bitcoin = new Sijoitus(), Bitcoin2 = new Sijoitus();
        Bitcoin.rekisteroi();
        Bitcoin.vastaaBitcoin();
        Bitcoin2.rekisteroi();
        Bitcoin2.vastaaBitcoin();

        try {
            sijoitukset.lisaa(Bitcoin);
            sijoitukset.lisaa(Bitcoin2);

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

