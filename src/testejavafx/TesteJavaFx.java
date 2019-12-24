/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testejavafx;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author f5078775
 */
public class TesteJavaFx extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/br/intranet/cenopservicoscwb/views/TelaPrincipal.fxml"));
        AnchorPane root1 = fxmlLoader.load();
     
    Stage stage = new Stage();
    //stage.setTitle(Util.tituloSistema + " Pesquisar motorista");
    stage.setScene(new Scene(root1));
    //stage.initModality(Modality.WINDOW_MODAL);
    //stage.initOwner(((Node) event.getSource()).getScene().getWindow());
    stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
