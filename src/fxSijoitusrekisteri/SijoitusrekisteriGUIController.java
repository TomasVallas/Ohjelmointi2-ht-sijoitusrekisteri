package fxSijoitusrekisteri;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.List;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Modality;
import fi.jyu.mit.fxgui.*;
import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import sijoitusrekisteri.Sijoitus;
import sijoitusrekisteri.Sijoitusrekisteri;
import sijoitusrekisteri.Tapahtuma;
import sijoitusrekisteri.SailoException;




/**
 * @author valla
 * @version 2.2.2025
 *
 */
public class SijoitusrekisteriGUIController implements Initializable {
    

    @FXML private TextField hakuehto;
    @FXML private ComboBoxChooser<String> cbKentat;
    @FXML private Label labelVirhe;
    @FXML private ScrollPane panelSijoitus;
    @FXML private ListChooser<Sijoitus> chooserSijoitukset;
    @FXML private ListChooser<Tapahtuma> chooserTapahtumat;
    @FXML private TextArea areaTapahtumat;
    @FXML private TextArea areaSijoitus;
    @FXML private TextField editNimi; 
    

    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();    
    }
    

    /**
     * Ei toimi
     */
    @FXML private void handleEitoimiviela() {
        Dialogs.showMessageDialog("Ei toimi vielä");
    }
    
    
    @FXML private void handleHakuehto() {
        if ( sijoitusKohdalla != null )
            hae(sijoitusKohdalla.getTunnusNro());
    }
    
    

    
    @FXML private void handlePoistaSijoitus() {
        poistaSijoitus();
    }
    

    
    @FXML private void handleMuokkaa() {
        Dialogs.showMessageDialog("Muokkaus ei toimi vielä");
    }

    /**
     * Käsitellään uuden sijoituksen lisääminen
     */
    @FXML private void handleUusiSijoitus() {
        String uusiSijoitusNimi = Dialogs.showInputDialog("Anna palvelun nimi", "");
        if (uusiSijoitusNimi == null) return;
        uusiSijoitus(uusiSijoitusNimi);
    }
    

    @FXML private void handleApua() {
        avustus();
    }
    
    
    /**
     * Käsitellään tallennuskäsky
     */
    @FXML private void handleTallenna() {
        tallenna();
    }
    
    
    /**
     * Käsitellään lopetuskäsky
     */
    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    }
    
    @FXML private void handleUusiTapahtuma() {
        String uusiTapahtumaNimi = Dialogs.showInputDialog("Anna tapahtuman nimi", "");
        if (uusiTapahtumaNimi == null) return;
        uusiTapahtuma(uusiTapahtumaNimi);
    }
    

    @FXML private void handleMuokkaaSijoitus() {
        String uusiSijoitusNimi = Dialogs.showInputDialog("Muuta palvelun nimeä", chooserSijoitukset.getSelectedObject().getsijoitusNimi());
        if (uusiSijoitusNimi == null) return;
        muokkaa(uusiSijoitusNimi);
    }
    
    @FXML private void handleMuokkaaTapahtuma() {
        String uusiTapahtumaNimi = Dialogs.showInputDialog("Muuta tapahtuman nimeä", chooserTapahtumat.getSelectedObject().getNimi());
        if (uusiTapahtumaNimi == null) return;
        muokkaatap(uusiTapahtumaNimi);
    }
    

    @FXML private void handlePoistaTapahtuma() {
        Dialogs.showMessageDialog("Ei osata vielä poistaa tapahtumaa");
    }


    


    private Sijoitusrekisteri sijoitusrekisteri;
    private Sijoitus sijoitusKohdalla;
    private Tapahtuma tapahtumaKohdalla;
    private TextField edits[]; 
    
    /**
     * Suorittaa tarvittavat alkuvalmistelut. Korvaa aiemman GridPanen
     * yhdellä suurella tekstialueella, johon sijoitusten tiedot voidaan tulostaa.
     * Alustetaan myös sijoituslistan kuuntelija.
     */
    protected void alusta() {
        chooserSijoitukset.clear();
        chooserSijoitukset.addSelectionListener(_ -> naytaSijoitus());
        chooserTapahtumat.clear();
        chooserTapahtumat.addSelectionListener(_ -> naytaTapahtuma());
        
        edits = new TextField[]{editNimi}; 
    }


    
//    private void naytaVirhe(String virhe) {
//        if (virhe == null || virhe.isEmpty()) {
//            labelVirhe.setText("");
//            labelVirhe.getStyleClass().removeAll("virhe");
//            return;
//        }
//        labelVirhe.setText(virhe);
//        labelVirhe.getStyleClass().add("virhe");
//    }
    

    
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
     * Tallentaa tiedot.
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

    
    /**
     * Tarkistaa, onko tallennus tehty.
     * @return true, jos sovelluksen voi sulkea, false muuten
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
    
    
    /**
     * Näyttää listasta valitun sijoituksen tiedot tekstialueella.
     */
    protected void naytaSijoitus() {
        sijoitusKohdalla = chooserSijoitukset.getSelectedObject();
        
        if (sijoitusKohdalla == null) return;
//        areaSijoitus.setText("");
       haeT(sijoitusKohdalla.getTunnusNro());
       
    }
    
    
    /**
     * Näytä listasta valitun tapahtuman tiedot
     */
    protected void naytaTapahtuma() {
        tapahtumaKohdalla = chooserTapahtumat.getSelectedObject();
        
        if (tapahtumaKohdalla == null) return;
        
        areaTapahtumat.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaTapahtumat)) {
            tulosta(os, tapahtumaKohdalla);  
        }
        
    }

        
    /**
     * Hakee sijoitusten tiedot ja päivittää listan.
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
     * Hakee sijoitukset tiedot listaan
     * @param tnro sijoituksen numero, joka aktivoidaan haun jälkeen
     */
    protected void haeT(int tnro) {
        chooserTapahtumat.clear();
        Sijoitus valittuSijoitus = chooserSijoitukset.getSelectedObject();
        
        if (valittuSijoitus == null) return;
        
        List<Tapahtuma> tapahtumat = sijoitusrekisteri.annaTapahtumat(valittuSijoitus);
        
        int index = 0;
        for (int i = 0; i < tapahtumat.size(); i++) {
            Tapahtuma tapahtuma = tapahtumat.get(i);
            if (tapahtuma.getTunnusNro() == tnro) index = i;
            chooserTapahtumat.add(tapahtuma.getNimi(), tapahtuma);
        }
        chooserTapahtumat.setSelectedIndex(index); 
    }

    
    /**
     * Luo uuden sijoituksen ja lisää sen rekisteriin.
     * @param SijoitusksenNimi uusi sijoitus
     */
    protected void uusiSijoitus(String SijoitusksenNimi) {
        Sijoitus uusi = new Sijoitus();
        uusi.rekisteroi();
        uusi.muutaNimi(SijoitusksenNimi);
        try {
            sijoitusrekisteri.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
        /*       try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("SijoitusrekisteriLisaasijoitusGUIView.fxml"));
            Pane root = ldr.load();
            
            Stage uusiIkkuna = new Stage();
            uusiIkkuna.setScene(new Scene(root));
            uusiIkkuna.setTitle("Lisää uusi sijoitus");
            uusiIkkuna.initModality(Modality.APPLICATION_MODAL); // Estää pääikkunan käytön
            uusiIkkuna.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        } */
        hae(uusi.getTunnusNro());
    }
    
    /** 
     * Tekee uuden tyhjän tapahtunman editointia varten 
     * @param tapahtumannimi uusi tapahtuma
     */ 
    public void uusiTapahtuma(String tapahtumannimi) { 
        if ( sijoitusKohdalla == null ) return;  
        Tapahtuma tap = new Tapahtuma();  
        tap.rekisteroi();  
        tap.asetatapNimi(sijoitusKohdalla.getTunnusNro(), tapahtumannimi);  
        try {
            sijoitusrekisteri.lisaa(tap);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia lisäämisessä! " + e.getMessage());
        }         
        haeT(sijoitusKohdalla.getTunnusNro());          
    } 
    
//    private void muokkaa(String uusiSijoitusNimi) {
//        if (sijoitusKohdalla == null) return;
//        try {
//            Sijoitus sijoitus;
//            sijoitus = sijoitusKohdalla.clone();
//            if (sijoitus == null) return;
//            sijoitusrekisteri.muutaSijoitusNimi(sijoitus, uusiSijoitusNimi);
//            hae(sijoitus.getTunnusNro());
//        } catch (CloneNotSupportedException e) {
//            //
//        }
//    }
    
    private void muokkaa(String uusiSijoitusNimi) {
        if (sijoitusKohdalla == null) return;
        sijoitusKohdalla.muutaNimi(uusiSijoitusNimi); // tai setNimi(...)
        hae(sijoitusKohdalla.getTunnusNro()); // päivitä näkymä
    }
    
    private void muokkaatap(String uusiTapahtumaNimi) {
        if (tapahtumaKohdalla == null) return;
        sijoitusrekisteri.muutaTapahtumaNimi(tapahtumaKohdalla, uusiTapahtumaNimi); // tai setNimi(...)
        haeT(tapahtumaKohdalla.getTunnusNro()); // päivitä näkymä
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
     * Asettaa käytettävän sijoitusrekisterin.
     * @param sijoitusrekisteri sijoitusrekisteri, jota käyttöliittymä käyttää
     */
    public void setSijoitusrekisteri(Sijoitusrekisteri sijoitusrekisteri) {
        this.sijoitusrekisteri = sijoitusrekisteri;
        naytaSijoitus();
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
     * Tulostaa sijoituksen tiedot tietovirtaan.
     * @param os tietovirta
     * @param tapahtuma tulostettava sijoitus
     */
    public void tulosta(PrintStream os, final Tapahtuma tapahtuma) {
        os.println("----------------------------------------------");
        tapahtuma.tulosta(os);
        os.println("----------------------------------------------");
    }
    
//    /**
//     * Tulostaa listassa olevat sijoitukset tekstialueeseen.
//     * @param text alue, johon sijoitukset tulostetaan
//     */
//    public void tulostaValitut(TextArea text) {
//        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
//            os.println("Tulostetaan kaikki sijoitukset");
//            for (int i = 0; i < sijoitusrekisteri.getSijoituksia(); i++) {
//                Sijoitus sijoitus = sijoitusrekisteri.annaSijoitus(i);
//                tulosta(os, sijoitus);
//                os.println("\n\n");
//            }
//        }
//    }
}