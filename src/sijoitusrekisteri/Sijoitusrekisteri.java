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
     * Jasen Bitcoin1 = new Jasen(), Bitcoin2 = new Jasen();
     * Bitcoin1.rekisteroi(); Bitcoin2.rekisteroi();
     * sijoitusrekisteri.getJasenia() === 0;
     * sijoitusrekisteri.lisaa(Bitcoin1); sijoitusrekisteri.getJasenia() === 1;
     * sijoitusrekisteri.lisaa(Bitcoin2); sijoitusrekisteri.getJasenia() === 2;
     * sijoitusrekisteri.lisaa(Bitcoin1); sijoitusrekisteri.getJasenia() === 3;
     * sijoitusrekisteri.getJasenia() === 3;
     * sijoitusrekisteri.annaJasen(0) === Bitcoin1;
     * sijoitusrekisteri.annaJasen(1) === Bitcoin2;
     * sijoitusrekisteri.annaJasen(2) === Bitcoin1;
     * sijoitusrekisteri.annaJasen(3) === Bitcoin1; #THROWS IndexOutOfBoundsException 
     * sijoitusrekisteri.lisaa(Bitcoin1); sijoitusrekisteri.getJasenia() === 4;
     * sijoitusrekisteri.lisaa(Bitcoin1); sijoitusrekisteri.getJasenia() === 5;
     * sijoitusrekisteri.lisaa(Bitcoin1);            #THROWS SailoException
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

            Sijoitus Bitcoin1 = new Sijoitus(), Bitcoin2 = new Sijoitus();
            Bitcoin1.rekisteroi();
            Bitcoin1.vastaaBitcoin();
            Bitcoin2.rekisteroi();
            Bitcoin2.vastaaBitcoin();

            sijoitusrekisteri.lisaa(Bitcoin1);
            sijoitusrekisteri.lisaa(Bitcoin2);

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
