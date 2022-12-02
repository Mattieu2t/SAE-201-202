import java.io.File;
import java.util.ArrayList;

import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
 
public class Fenetres extends Application {
    // Attribut Fenetres
    static Stage actualStage;
    static Scene sceneM;
    static Scene sceneGF;
    static Scene sceneR;
    static Scene sceneLE;
    static Scene sceneLA;

    // Attributs Tutorats
    static ArrayList<Tutor> tuteurs = new ArrayList<>();
    static ArrayList<Tutee> tutores = new ArrayList<>();
    static GrapheNonOrienteValue<Student> graphe;
    static CalculAffectation<Student> calcul;
    static GrapheNonOrienteValue<Student> coupleBloque = new GrapheNonOrienteValue<>();
    static GrapheNonOrienteValue<Student> coupleForce = new GrapheNonOrienteValue<>();

    public void start(Stage stage) throws Exception {
        actualStage = stage;
        Parent rootM = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        sceneM = new Scene(rootM);
        Parent rootGD = FXMLLoader.load(getClass().getResource("GestionFichiers.fxml"));
        sceneGF = new Scene(rootGD);
        Parent rootR = FXMLLoader.load(getClass().getResource("Reglages.fxml"));
        sceneR = new Scene(rootR);
        Parent rootLE = FXMLLoader.load(getClass().getResource("ListeEtudiants.fxml"));
        sceneLE = new Scene(rootLE);
        Parent rootLA = FXMLLoader.load(getClass().getResource("ListeAffectations.fxml"));
        sceneLA = new Scene(rootLA);
        
        actualStage.getIcons().setAll(new Image(getClass().getResource("appIcon_16.png").toExternalForm()), 
        new Image(getClass().getResource("appIcon_24.png").toExternalForm()), 
        new Image(getClass().getResource("appIcon_32.png").toExternalForm()), 
        new Image(getClass().getResource("appIcon_48.png").toExternalForm()), 
        new Image(getClass().getResource("appIcon_64.png").toExternalForm()), 
        new Image(getClass().getResource("appIcon_128.png").toExternalForm()), 
        new Image(getClass().getResource("appIcon_256.png").toExternalForm()));
        actualStage.setOnCloseRequest(event -> event.consume());
        actualStage.setResizable(false);
        actualStage.setTitle("FXML Welcome");
        actualStage.setScene(sceneM);
        actualStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}