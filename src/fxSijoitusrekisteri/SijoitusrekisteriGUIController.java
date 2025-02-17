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

/**
 * @author valla
 * @version 2.2.2025
 *
 */
public class SijoitusrekisteriGUIController implements Initializable {
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
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("SijoitusrekisteriLisaasijoitusGUIView.fxml"));
            Pane root = ldr.load();
            
            Stage uusiIkkuna = new Stage();
            uusiIkkuna.setScene(new Scene(root));
            uusiIkkuna.setTitle("Lisää uusi sijoitus");
            uusiIkkuna.initModality(Modality.APPLICATION_MODAL); // Estää pääikkunan käytön
            uusiIkkuna.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    
    /**
     * Tietojen tallennus
     */
    private void tallenna() {
        Dialogs.showMessageDialog("Tallennetetaan! Mutta ei toimi vielä");
    }
    
}