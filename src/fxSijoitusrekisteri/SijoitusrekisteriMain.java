package fxSijoitusrekisteri;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import sijoitusrekisteri.Sijoitusrekisteri;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author valla
 * @version 2.2.2025
 *
 */
public class SijoitusrekisteriMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("SijoitusrekisteriGUIView.fxml"));
            final Pane root = ldr.load();
            final SijoitusrekisteriGUIController sijoitusrekisteriCtrl = (SijoitusrekisteriGUIController) ldr.getController();
            
            
            final Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("sijoitusrekisteri.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("sijoitusrekisteri");
            primaryStage.show();
        
        
        
        
        
            primaryStage.setOnCloseRequest((event) -> {
                if ( !sijoitusrekisteriCtrl.voikoSulkea() ) event.consume();
            });
        
            Sijoitusrekisteri sijoitusrekisteri = new Sijoitusrekisteri();  
            sijoitusrekisteriCtrl.setSijoitusrekisteri(sijoitusrekisteri); 
            sijoitusrekisteriCtrl.lueTiedosto();
            
            primaryStage.show();
            } catch(Exception e) {
                e.printStackTrace();
            }
       }

    /**
     * @param args Ei kaytossa
     */
    public static void main(String[] args) {
        launch(args);
    }
}