package fxSijoitusrekisteri;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import fi.jyu.mit.fxgui.*;

/**
 * @author valla
 * @version 2.2.2025
 *<BorderPane xmlns="http://javafx.com/javafx/23.0.1" xmlns:fx="http://javafx.com/fxml/1" fx:controller="fxSijoitusrekisteri.SijoitusrekisteriLisaasijoitusGUIController">
 */
public class SijoitusrekisteriLisaasijoitusGUIController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        //
    }
    
    @FXML private void handlePeruuta(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    
    /**
     * Käsitellään uuden jäsenen lisääminen
     */
    @FXML private void handleUusiSijoitus() {
        Dialogs.showMessageDialog("Lisäys ei toimi");
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