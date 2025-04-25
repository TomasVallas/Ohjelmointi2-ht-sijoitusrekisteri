package sijoitusrekisteri;


/**
 * Rajapinta tietueelle johon voidaan taulukon avulla rakentaa 
 * "attribuutit".
 * @author vallas
 * @version 25.4.2025
 * @example
 */
public interface Tietue {

    
    /**
     * @return tietueen kenttien lukumäärä
     * @example
     * <pre name="test">
     *   #import sijoitusrekisteri.Tapahtuma;
     *   Tapahtuma tap = new Tapahtuma();
     *   tap.getKenttia() === 12;
     * </pre>
     */
    public abstract int getKenttia();


    /**
     * @return ensimmäinen käyttäjän syötettävän kentän indeksi
     * @example
     * <pre name="test">
     *   Tapahtuma tap = new Tapahtuma();
     *   tap.ekaKentta() === 2;
     * </pre>
     */
    public abstract int ekaKentta();


    /**
     * @param k minkä kentän kysymys halutaan
     * @return valitun kentän kysymysteksti
     * @example
     * <pre name="test">
     *   Tapahtuma tap = new Tapahtuma();
     *   tap.getKysymys(2) === "Nimi";
     * </pre>
     */
    public abstract String getKysymys(int k);


    /**
     * @param k Minkä kentän sisältö halutaan
     * @return valitun kentän sisältö
     * @example
     * <pre name="test">
     *   Tapahtuma tap = new Tapahtuma();
     *   tap.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   tap.anna(0) === "2";   
     *   tap.anna(1) === "10";   
     *   tap.anna(2) === "Kalastus";   
     *   tap.anna(3) === "1949";   
     *   tap.anna(4) === "22";   
     * </pre>
     */
    public abstract String anna(int k);

    
    /**
     * Asetetaan valitun kentän sisältö.  Mikäli asettaminen onnistuu,
     * palautetaan null, muutoin virheteksti.
     * @param k minkä kentän sisältö asetetaan
     * @param s asetettava sisältö merkkijonona
     * @return null jos ok, muuten virheteksti
     * @example
     * <pre name="test">
     *   Tapahtuma tap = new Tapahtuma();
     *   tap.aseta(3,"kissa") === null;
     *   tap.aseta(3,"1940")  === null;
     *   tap.aseta(4,"kissa") === null;
     *   tap.aseta(4,"20")    === null;
     * </pre>
     */
    public abstract String aseta(int k, String s);


    /**
     * Tehdään identtinen klooni tietueesta
     * @return kloonattu tietue
     * @throws CloneNotSupportedException jos kloonausta ei tueta
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Tapahtuma tap = new Tapahtuma();
     *   tap.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   Object kopio = tap.clone();
     *   kopio.toString() === tap.toString();
     *   tap.parse("   1   |  11  |   Uinti  | 1949 | 22 t ");
     *   kopio.toString().equals(tap.toString()) === false;
     *   kopio instanceof Tapahtuma === true;
     * </pre>
     */
    public abstract Tietue clone() throws CloneNotSupportedException;


    /**
     * Palauttaa tietueen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return tietue tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Tapahtuma tapahtuma = new Tapahtuma();
     *   tapahtuma.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   tapahtuma.toString()    =R= "2\\|10\\|Kalastus\\|1949\\|22.*";
     * </pre>
     */
    @Override
    public abstract String toString();

}
