import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ControllerMenu {

    @FXML
    private Button bouttonGF;

    @FXML
    private Button bouttonLA;

    @FXML
    private Button bouttonLE;

    @FXML
    private Button bouttonQ;
    
    @FXML
    void acceGF(ActionEvent event) {
        Fenetres.actualStage.setTitle("Gestion des Fichiers");
        Fenetres.actualStage.setScene(Fenetres.sceneGF);
    }

    @FXML
    void acceLA(ActionEvent event) {
        Fenetres.actualStage.setTitle("Liste des Affectations");
        Fenetres.actualStage.setScene(Fenetres.sceneLA);
    }

    @FXML
    void acceLE(ActionEvent event) {
        Fenetres.actualStage.setTitle("Liste des Etudiants");
        Fenetres.actualStage.setScene(Fenetres.sceneLE);
    }

    @FXML
    void leave(ActionEvent event) {
        Fenetres.actualStage.close();
    }

}
