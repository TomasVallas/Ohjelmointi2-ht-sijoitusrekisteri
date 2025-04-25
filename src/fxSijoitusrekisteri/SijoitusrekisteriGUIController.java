package fxSijoitusrekisteri;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import fi.jyu.mit.fxgui.StringGrid;
import fi.jyu.mit.fxgui.*;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import sijoitusrekisteri.Sijoitus;
import sijoitusrekisteri.Sijoitusrekisteri;
import sijoitusrekisteri.Tapahtuma;
import sijoitusrekisteri.SailoException;
import javafx.scene.input.KeyCode;
import javafx.scene.control.TextArea;



/**
 * Luokka sijoitusrekisterin käyttöliittymän tapahtumien hoitamiseksi.
 * 
 * @author valla
 * @version 25.4.2025
 *
 */
public class SijoitusrekisteriGUIController implements Initializable {
    
    @FXML private ListChooser<Sijoitus> chooserSijoitukset;
    @FXML private ListChooser<Tapahtuma> chooserTapahtumat;
    @FXML private StringGrid<Tapahtuma> tapahtumaGrid;
    @FXML private TextArea areaTapahtumat;
    @FXML private Label labelYhteensa;
    

    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();    
    }
  
    @FXML private void handleHakuehto() {
        if (sijoitusKohdalla != null)
            hae(sijoitusKohdalla.getTunnusNro());
    }
    
    @FXML
    private void handleLajitteleSijoitukset() {
        lajitteleJaPaivitaSijoitukset();
    }
       
    @FXML private void handleMuokkaa() {
        Dialogs.showMessageDialog("Muokkaus ei toimi vielä");
    }


      
    @FXML private void handleTallenna() {
        tallenna();
    }
    
    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    }
    
    @FXML private void handleApua() {
        avustus();
    }
    
    
    
    @FXML private void handleUusiSijoitus() {
        String uusiSijoitusNimi = Dialogs.showInputDialog("Anna palvelun nimi", "");
        if (uusiSijoitusNimi == null) return;
        uusiSijoitus(uusiSijoitusNimi);
    }
    
    @FXML private void handleMuokkaaSijoitus() {
        String uusiSijoitusNimi = Dialogs.showInputDialog("Muuta palvelun nimeä", 
            chooserSijoitukset.getSelectedObject().getsijoitusNimi());
        if (uusiSijoitusNimi == null) return;
        muokkaa(uusiSijoitusNimi);
    }
    
    @FXML private void handlePoistaSijoitus() {
        poistaSijoitus();
    }
    
    
    
    @FXML private void handleUusiTapahtuma() {
        uusiTapahtuma();
    }
    
    @FXML private void handleMuokkaaTapahtuma() {
        muokkaaTapahtuma();
    }
    
    @FXML private void handlePoistaTapahtuma() {
        poistaTapahtuma();
    }
    
    
    
// =========================================================
    private Sijoitusrekisteri sijoitusrekisteri;
    private Sijoitus sijoitusKohdalla;
    private static Tapahtuma aputapahtuma = new Tapahtuma(); 
    

    /**
     * Tekee tarvittavat muut alustukset
     */
    protected void alusta() {
        chooserSijoitukset.clear();
        chooserSijoitukset.addSelectionListener(e -> naytaSijoitus());
        
        chooserTapahtumat.clear();
        chooserTapahtumat.addSelectionListener(e -> naytaTapahtuma());
        
        // Initialize the event grid
        int eka = aputapahtuma.ekaKentta(); 
        int lkm = aputapahtuma.getKenttia(); 
        String[] headings = new String[lkm-eka]; 
        for (int i=0, k=eka; k<lkm; i++, k++) 
            headings[i] = aputapahtuma.getKysymys(k); 
        tapahtumaGrid.initTable(headings); 
        tapahtumaGrid.setEditable(false); 
        tapahtumaGrid.setPlaceholder(new Label("Ei vielä tapahtumia")); 
        
        tapahtumaGrid.setOnMouseClicked(e -> { 
            if (e.getClickCount() > 1) muokkaaTapahtuma(); 
        });
        tapahtumaGrid.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.F2) muokkaaTapahtuma();
        }); 
    }


    /**
     * Näyttää sijoituksen
     */
    protected void naytaSijoitus() {
        sijoitusKohdalla = chooserSijoitukset.getSelectedObject();
        if (sijoitusKohdalla == null) return;
        
        haeT(sijoitusKohdalla.getTunnusNro());
        naytaTapahtumat(sijoitusKohdalla);
    }
    
    
    /**
     * Näyttää Tapahtuman
     */
    protected void naytaTapahtuma() {
        Tapahtuma tap = chooserTapahtumat.getSelectedObject();
        if (tap == null) return;
        
        areaTapahtumat.clear();
        areaTapahtumat.appendText("Tapahtuman tiedot:\n");
        areaTapahtumat.appendText("Nimi: " + tap.getNimi() + "\n");
        areaTapahtumat.appendText("Ostopäivä: " + tap.getOstopv() + "\n");
        areaTapahtumat.appendText("Ostohinta: " + tap.getOstohinta() + "€\n");
        areaTapahtumat.appendText("Määrä: " + tap.getOstomaara() + "\n");
        areaTapahtumat.appendText("Kurssi: " + tap.getKurssi() + "€\n");
        if (tap.getMyyntipv() > 0) {
            areaTapahtumat.appendText("Myyntipäivä: " + tap.getMyyntipv() + "\n");
            areaTapahtumat.appendText("Myyntikurssi: " + tap.getMyyntikurssi() + "€\n");
            areaTapahtumat.appendText("Tuotto: " + tap.getTuotto() + "€\n");
        }
        areaTapahtumat.appendText("Jäljellä: " + tap.getJaljella() + "\n");
    }
    
    
    /**
     * Näyttää Tapahtumat
     */
    private void naytaTapahtumat(Sijoitus sijoitus) {
        tapahtumaGrid.clear();
        if (sijoitus == null) return;
        
        List<Tapahtuma> tapahtumat = sijoitusrekisteri.annaTapahtumat(sijoitus);
        if (tapahtumat.isEmpty()) return;
        
        labelYhteensa.setText("Yhteensä: " + tapahtumat.size() + " kpl");
        
        for (Tapahtuma tap : tapahtumat) {
            naytaTapahtumaGridissa(tap);
        }
    }
    

    /**
     * Näyttää Tapahtumat gridissä
     */
    private void naytaTapahtumaGridissa(Tapahtuma tap) {
        String[] rivi = {
            tap.getNimi(),
            String.valueOf(tap.getOstopv()),
            tap.getOstohinta() + "€",
            String.valueOf(tap.getOstomaara()),
            tap.getKurssi() + "€",
            tap.getMyyntipv() > 0 ? String.valueOf(tap.getMyyntipv()) : "-",
            tap.getMyyntipv() > 0 ? tap.getMyyntikurssi() + "€" : "-",
            tap.getMyyntipv() > 0 ? String.valueOf(tap.getMyyntimaara()) : "-",
            tap.getTuotto() + "€",
            String.valueOf(tap.getJaljella())
        };
        tapahtumaGrid.add(tap, rivi);
    }
    
    
    /**
     * Hakee sijoitusten tiedot listaan
     * @param snro sijoituksen numero, joka aktivoidaan haun jälkeen
     */
    protected void hae(int snro) {
        chooserSijoitukset.clear();
        
        int index = 0;
        for (int i = 0; i < sijoitusrekisteri.getSijoituksia(); i++) {
            Sijoitus sijoitus = sijoitusrekisteri.annaSijoitukset(i);
            if (sijoitus.getTunnusNro() == snro) index = i;
            chooserSijoitukset.add(sijoitus.getsijoitusNimi(), sijoitus);
        }
        chooserSijoitukset.setSelectedIndex(index);
    }
    

    /**
     * Hakee sijoitusten tiedot listaan
     * @param tnro tapahtuman numero, joka aktivoidaan haun jälkeen
     */
    protected void haeT(int tnro) {
        chooserTapahtumat.clear();
        if (sijoitusKohdalla == null) return;
        
        List<Tapahtuma> tapahtumat = sijoitusrekisteri.annaTapahtumat(sijoitusKohdalla);
        
        int index = 0;
        for (int i = 0; i < tapahtumat.size(); i++) {
            Tapahtuma tapahtuma = tapahtumat.get(i);
            if (tapahtuma.getTunnusNro() == tnro) index = i;
            chooserTapahtumat.add(tapahtuma.getNimi(), tapahtuma);
        }
        chooserTapahtumat.setSelectedIndex(index); 
    }

    
    /**
     * Luo uuden sijoituksen jota aletaan editoimaan 
     * @param nimi sijoituksen nimi
     */
    protected void uusiSijoitus(String nimi) {
        Sijoitus uusi = new Sijoitus();
        uusi.rekisteroi();
        uusi.muutaNimi(nimi);
        try {
            sijoitusrekisteri.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
        hae(uusi.getTunnusNro());
    }
    
    
    /**
     * Luo uuden tapahtuman jota aletaan editoimaan 
     */
    private void uusiTapahtuma() {
        if (sijoitusKohdalla == null) return;
        try {
            Tapahtuma uusi = new Tapahtuma(sijoitusKohdalla.getTunnusNro());
            uusi = SijoitusrekisteriLisaasijoitusGUIController.kysyTietue(null, uusi, 0);
            if (uusi == null) return;
            uusi.rekisteroi();
            sijoitusrekisteri.lisaa(uusi);
            haeT(sijoitusKohdalla.getTunnusNro());
            naytaTapahtumat(sijoitusKohdalla);
            tapahtumaGrid.selectRow(1000);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Lisääminen epäonnistui: " + e.getMessage());
        }
    }
    
    
    /**
     * Muokkaa valittua tapahtumaa.
     * Käyttäjä voi muokata tapahtuman tietoja, ja muutokset tallennetaan.
     * Jos muokkaus epäonnistuu (esim. kloonausvirhe tai tallennusvirhe),
     * käyttäjälle näytetään virheilmoitus.
     */
    private void muokkaaTapahtuma() {
        Tapahtuma tap = chooserTapahtumat.getSelectedObject(); // Käytä chooseria, jos gridin valinta ei toimi
        if (tap == null) {
            Dialogs.showMessageDialog("Valitse tapahtuma ensin!");
            return;
        }

        try {
            Tapahtuma kopio = tap.clone();
            kopio = SijoitusrekisteriLisaasijoitusGUIController.kysyTietue(null, kopio, 0);
            if (kopio == null) return;

            sijoitusrekisteri.korvaaTaiLisaa(kopio);
            haeT(sijoitusKohdalla.getTunnusNro());
            naytaTapahtumat(sijoitusKohdalla);

            // Etsi muokatun tapahtuman uusi indeksi ja aseta se valituksi
            List<Tapahtuma> tapahtumat = sijoitusrekisteri.annaTapahtumat(sijoitusKohdalla);
            int index = tapahtumat.indexOf(kopio);
            if (index >= 0) {
                chooserTapahtumat.setSelectedIndex(index);
            }
        } catch (CloneNotSupportedException e) {
            Dialogs.showMessageDialog("Virhe tapahtuman kopioinnissa: " + e.getMessage());
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Tallennusvirhe: " + e.getMessage());
        }
    }   

    
    /*
     * Poistetaan listalta valittu tapahtuma
     */
    private void poistaTapahtuma() {
        Tapahtuma tap = chooserTapahtumat.getSelectedObject(); // Käytä chooseria, jos gridin valinta ei toimi
        if (tap == null) {
            Dialogs.showMessageDialog("Valitse tapahtuma ensin!");
            return;
        }

        if (!Dialogs.showQuestionDialog("Poisto", "Poistetaanko tapahtuma " + tap.getNimi() + "?", "Kyllä", "Ei")) {
            return;
        }

        sijoitusrekisteri.poistaTapahtuma(tap);
        haeT(sijoitusKohdalla.getTunnusNro());
        naytaTapahtumat(sijoitusKohdalla);
    }

       
    /*
     * Muokataan valittua sijoitusta
     */
    private void muokkaa(String uusiNimi) {
        if (sijoitusKohdalla == null) return;
        sijoitusKohdalla.muutaNimi(uusiNimi);
        hae(sijoitusKohdalla.getTunnusNro());
    }
    
    
    /*
     * Poistetaan listalta valittu sijoitus
     */
    private void poistaSijoitus() {
        Sijoitus sijoitus = sijoitusKohdalla;
        if ( sijoitus == null ) return;
        if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko palvelu: " + sijoitus.getsijoitusNimi(), "Kyllä", "Ei") )
            return;
        sijoitusrekisteri.poista(sijoitus);
        int index = chooserSijoitukset.getSelectedIndex();
        hae(0);
        chooserSijoitukset.setSelectedIndex(index);
    }

    
    /**
     * Tietojen tallennus
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    private String tallenna() {
        try {
            sijoitusrekisteri.tallenna();
            return null;
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
            return ex.getMessage();
        }
    }
    
    
    /*
     * Lajittelee sijoitukset aakkosjärjestykseen napin painalluksella
     */
    private void lajitteleJaPaivitaSijoitukset() {
        List<Sijoitus> sijoitukset = new ArrayList<>();
        for (int i = 0; i < sijoitusrekisteri.getSijoituksia(); i++) {
            sijoitukset.add(sijoitusrekisteri.annaSijoitukset(i));
        }
        
        sijoitukset.sort(Comparator.comparing(Sijoitus::getsijoitusNimi, String.CASE_INSENSITIVE_ORDER));
        
        int valittuTunnus = (sijoitusKohdalla != null) ? sijoitusKohdalla.getTunnusNro() : -1;
        
        chooserSijoitukset.clear();
        sijoitukset.forEach(s -> chooserSijoitukset.add(s.getsijoitusNimi(), s));
        
        if (valittuTunnus != -1) {
            for (int i = 0; i < sijoitukset.size(); i++) {
                if (sijoitukset.get(i).getTunnusNro() == valittuTunnus) {
                    chooserSijoitukset.setSelectedIndex(i);
                    break;
                }
            }
        }
    }
    
    
    /**
     * Tarkistaa, onko tallennus tehty.
     * @return true, jos sovelluksen voi sulkea, false muuten
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
    
    
    /**
     * Lataa sijoitusrekisterin tiedot annetusta tiedostosta.
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    protected String lueTiedosto() {
        try {
            sijoitusrekisteri.lueTiedostosta();
            hae(0);
            haeT(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            haeT(0);
            String virhe = e.getMessage(); 
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
     }

    
    /**
     * Avaa ohjeet erillisessä selaimessa.
     */
    private void avustus() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/v/2025/kevat/ht/valltoha");
            desktop.browse(uri);
        } catch (URISyntaxException | IOException e) {
            return;
        }
    }

    
    /**
     * @param sijoitusrekisteri Sijoitusrekisteri jota käytetään tässä käyttöliittymässä
     */
    public void setSijoitusrekisteri(Sijoitusrekisteri sijoitusrekisteri) {
        this.sijoitusrekisteri = sijoitusrekisteri;
        naytaSijoitus();
    }
}