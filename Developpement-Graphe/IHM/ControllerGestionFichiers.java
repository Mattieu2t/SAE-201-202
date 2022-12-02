import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Exeptions.LineNotValidExeption;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class ControllerGestionFichiers implements Initializable{
    List<String> listFile;

    @FXML
    private Label labelExport;

    @FXML
    private Button buttonChargeCSV;

    @FXML
    private Button buttonTelechargeCSV;

    @FXML
    private Label labelImport;

    @FXML
    private Button retourMenu;

    @FXML
    void backToMenu(ActionEvent event) {
        Fenetres.actualStage.setTitle("Menu");
        Fenetres.actualStage.setScene(Fenetres.sceneM);
    }

    @FXML
    void exporte(ActionEvent event) {
        DirectoryChooser dc = new DirectoryChooser();
        File f = dc.showDialog(null);
        
        if(f != null){
            try {
                Tutorat.exporteAffectations(f.getAbsolutePath(), Fenetres.calcul, Fenetres.coupleBloque, Fenetres.coupleForce);
                labelExport.setTextFill(Color.GREEN);
                labelExport.setText("Dossier : "+f.getName());
            } catch (NullPointerException e) {
                labelExport.setTextFill(Color.RED);
                labelExport.setText(e.getMessage());
            } catch (Exception e) {
                labelExport.setTextFill(Color.RED);
                labelExport.setText("Erreur lors de l'exportation !");
            }
        }
        else{
            labelExport.setTextFill(Color.BLACK);
            labelExport.setText("En attente...");
        }
    }

    @FXML
    void importe(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new ExtensionFilter("Csv Files", listFile));
        File f = fc.showOpenDialog(null);
        
        if(f != null){
            try {
                Tutorat.importeEtudiants(f.getAbsolutePath(), Fenetres.tuteurs, Fenetres.tutores);
                labelImport.setTextFill(Color.GREEN);
                labelImport.setText("Fichier : "+f.getName());
            } catch (LineNotValidExeption e) {
                labelImport.setTextFill(Color.RED);
                labelImport.setText(e.getMessage());
            } catch (IOException e) {
                labelImport.setTextFill(Color.RED);
                labelImport.setText("Erreur lors de l'importation !");
            }
        }
        else{
            labelImport.setTextFill(Color.BLACK);
            labelImport.setText("En attente...");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listFile = new ArrayList<>();
        listFile.add("*.csv");
    }
}
