package sijoitusrekisteri;

/**
 * @author valla
 * @version 16.3.2025
 *
 */
public class Sijoitusrekisteri {
    
    private final Sijoitukset sijoitukset = new Sijoitukset();


    /**
     * Palautaa kerhon sijoitusmäärän
     * @return sijoitusmäärä
     */
    public int getSijoituksia() {
        return sijoitukset.getLkm();
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
     * Palauttaa i:n sijoitusen
     * @param i monesko sijoitus palautetaan
     * @return viite i:teen sijoituseen
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Sijoitus annaSijoitus(int i) throws IndexOutOfBoundsException {
        return sijoitukset.anna(i);
    }


    /**
     * Lukee sijoitusrekisterin tiedot tiedostosta
     * @param nimi jota käyteään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        sijoitukset.lueTiedostosta(nimi);
    }


    /**
     * Tallettaa sijoitusrekisterin tiedot tiedostoon
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void talleta() throws SailoException {
        sijoitukset.talleta();
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

            System.out.println("============= sijoitusrekisterin testi =================");

            for (int i = 0; i < sijoitusrekisteri.getSijoituksia(); i++) {
                Sijoitus sijoitus = sijoitusrekisteri.annaSijoitus(i);
                System.out.println("Sijoitus paikassa: " + i);
                sijoitus.tulosta(System.out);
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
