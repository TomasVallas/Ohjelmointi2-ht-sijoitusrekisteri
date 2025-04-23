package fxSijoitusrekisteri;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sijoitusrekisteri.Sijoitus;


/**
 * @author valla
 * @version 21.4.2025
 *<BorderPane xmlns="http://javafx.com/javafx/23.0.1" xmlns:fx="http://javafx.com/fxml/1" fx:controller="fxSijoitusrekisteri.SijoitusrekisteriLisaasijoitusGUIController">
 */
public class SijoitusrekisteriLisaasijoitusGUIController implements ModalControllerInterface<Sijoitus>,Initializable  {
    
    @FXML private TextField editNimi; 
    @FXML private Label labelVirhe;

    
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        //
    }
    
    @FXML private void handleOK() {
        ModalController.closeStage(labelVirhe);
    }

    
    @FXML private void handleCancel() {
        ModalController.closeStage(labelVirhe);
    }

// ========================================================    
    private Sijoitus sijoitusKohdalla;
//    private TextField edits[];

    /**
     * Tyhjentään tekstikentät 
     * @param edits tauluko jossa tyhjennettäviä tektsikenttiä
     */
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit : edits)
            edit.setText("");
    }



    /**
     * Tekee tarvittavat muut alustukset.
     */
//    protected void alusta() {
//        edits = new TextField[]{editNimi};
//    }
    
    
    @Override
    public void setDefault(Sijoitus oletus) {
        sijoitusKohdalla = oletus;
        if (sijoitusKohdalla != null)
            editNimi.setText(sijoitusKohdalla.getsijoitusNimi());
    }

    
    @Override
    public Sijoitus getResult() {
        return sijoitusKohdalla;
    }
    
    
    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        editNimi.requestFocus();
    }
    
    
    /**
     * Näytetään sijoituksen tiedot TextField komponentteihin
     * @param sijoitus näytettävä sijoitus
     * @param fields taulukko jossa tekstikenttiä
     */
//    public static void naytaSijoitus(TextField[] fields, Sijoitus sijoitus) {
//        if (sijoitus == null) return;
//        fields[0].setText(sijoitus.getsijoitusNimi());
//    }
    
    
    /**
     * Luodaan sijoituksen kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * TODO: korjattava toimimaan
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä dataan näytetään oletuksena
     * @return null jos painetaan Cancel, muuten täytetty tietue
     */
    public static Sijoitus kysySijoitus(Stage modalityStage, Sijoitus oletus) {
        return ModalController.<Sijoitus, SijoitusrekisteriLisaasijoitusGUIController>showModal(
                SijoitusrekisteriLisaasijoitusGUIController.class.getResource("SijoitusrekisteriLisaaPalveluGUIView.fxml"),
                    "Sijoitusrekisteri",
                    modalityStage, oletus, null 
                );
    }

}
