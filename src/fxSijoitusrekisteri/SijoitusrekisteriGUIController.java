package fxSijoitusrekisteri;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
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
    
    private String sijoitusrekisterinnimi = "Sijoitusrekisteri";
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        //
    }
    

    
    /**
     * Ei toimi
     */
    @FXML private void handleEitoimiviela() {
        Dialogs.showMessageDialog("Ei toimi vielä");
    }
    
    @FXML private void handlePoista() {
        boolean vastaus = Dialogs.showQuestionDialog("Poisto?",
                "Poista sijoitus: SIJOITUKSEN NIMI", "Kyllä", "Ei");
        if ( vastaus ); //poistaJasen(...
    }
    
    @FXML private void handleMuokkaa() {
        Dialogs.showMessageDialog("Muokkaus ei toimi vielä");
    }

    /**
     * Käsitellään uuden jäsenen lisääminen
     */
    @FXML private void handleUusiSijoitus() {
        uusiSijoitus();
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

    

    
    private Sijoitusrekisteri sijoitusrekisteri;
    private Sijoitus sijoitusKohdalla;
    private TextArea areaSijoitus = new TextArea();
    
    /**
     * Suorittaa tarvittavat alkuvalmistelut. Korvaa aiemman GridPanen
     * yhdellä suurella tekstialueella, johon sijoitusten tiedot voidaan tulostaa.
     * Alustetaan myös sijoituslistan kuuntelija.
     */
    protected void alusta() {
//        panelSijoitus.setContent(areaSijoitus);
//        areaSijoitus.setFont(new Font("Courier New", 12));
//        panelSijoitus.setFitToHeight(true);
        
        chooserSijoitukset.clear();
        chooserSijoitukset.addSelectionListener(e -> naytaSijoitus());
    }


    
    private void naytaVirhe(String virhe) {
        if (virhe == null || virhe.isEmpty()) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
    
    private void setTitle(String title) {
        ModalController.getStage(hakuehto).setTitle(title);
    }
    
    /**
     * Lataa sijoitusrekisterin tiedot annetusta tiedostosta.
     * @param nimi tiedoston nimi
     */
    protected void lueTiedosto(String nimi) {
        sijoitusrekisterinnimi = nimi;
        setTitle("Sijoitusrekisteri - " + sijoitusrekisterinnimi);
        String virhe = "Ei osata lukea vielä";  // TODO: toteuta tiedoston luku
        // if (virhe != null)            
            Dialogs.showMessageDialog(virhe);
    }
    
//    /**
//     * Kysyy käyttäjältä tiedoston nimen ja lataa sen.
//     * @return true, jos lataus onnistui, false muuten
//     */
//    public boolean avaa() {
//        String uusinimi = SijoitusrekisterinNimiController.kysyNimi(null, sijoitusrekisterinnimi);
//        if (uusinimi == null) return false;
//        lueTiedosto(uusinimi);
//        return true;
//    }
    
    /**
     * Tallentaa tiedot.
     */
    private void tallenna() {
        Dialogs.showMessageDialog("Tallennetaan! Mutta ei toimi vielä");
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

        areaSijoitus.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaSijoitus)) {
            sijoitusKohdalla.tulosta(os);
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
            Sijoitus sijoitus = sijoitusrekisteri.annaSijoitus(i);
            if (sijoitus.getTunnusNro() == snro) index = i;
            chooserSijoitukset.add(sijoitus.getsijoitusNimi(), sijoitus);
        }
        chooserSijoitukset.setSelectedIndex(index);
    }
    
    /**
     * Luo uuden sijoituksen ja lisää sen rekisteriin.
     */
    protected void uusiSijoitus() {
        Sijoitus uusi = new Sijoitus();
        uusi.rekisteroi();
        uusi.vastaaBinance();
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
     * @param sijoitus tulostettava sijoitus
     */
    public void tulosta(PrintStream os, final Sijoitus sijoitus) {
        os.println("----------------------------------------------");
        sijoitus.tulosta(os);
        os.println("----------------------------------------------");
    }
    
    /**
     * Tulostaa listassa olevat sijoitukset tekstialueeseen.
     * @param text alue, johon sijoitukset tulostetaan
     */
    public void tulostaValitut(TextArea text) {
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
            os.println("Tulostetaan kaikki sijoitukset");
            for (int i = 0; i < sijoitusrekisteri.getSijoituksia(); i++) {
                Sijoitus sijoitus = sijoitusrekisteri.annaSijoitus(i);
                tulosta(os, sijoitus);
                os.println("\n\n");
            }
        }
    }
}