import Exeptions.OutOfRangeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ControllerReglages {

    @FXML
    private Button boutonValide;

    @FXML
    private TextField l1;

    @FXML
    private TextField l2;

    @FXML
    private TextField l3;

    @FXML
    private TextField l4;

    @FXML
    private Button retourLA;

    @FXML
    private Slider slider1;

    @FXML
    private Slider slider2;

    @FXML
    private Slider slider3;

    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    void backToListeAffectations(ActionEvent event) {
        slider1.setValue(Tutorat.getPourcentNoteTutore());
        slider2.setValue(Tutorat.getPourcentAjoutPriorite3emeAnnee());
        slider3.setValue(Tutorat.getPourcentRetraitPrioriteAbscence());
        changeText();
        Fenetres.actualStage.setTitle("Liste Affectations");
        Fenetres.actualStage.setScene(Fenetres.sceneLA);
    }

    @FXML
    void update(MouseEvent event) {
        changeText();
    }

    void changeText(){
        l1.setText(""+(int)slider1.getValue());
        l2.setText(""+(int)(100-slider1.getValue()));
        l3.setText(""+(int)slider2.getValue());
        l4.setText(""+(int)slider3.getValue());
    }

    @FXML
    void validSettings(ActionEvent event) {
        try {
            Tutorat.setPourcentNoteTuteur((int)(100-slider1.getValue()));
            Tutorat.setPourcentNoteTutore((int)(slider1.getValue()));
            Tutorat.setPourcentAjoutPriorite3emeAnnee((int)(slider2.getValue()));
            Tutorat.setPourcentRetraitPrioriteAbscence((int)(slider3.getValue()));
        } catch (OutOfRangeException e) {
        }
        Fenetres.actualStage.setTitle("Liste Affectations");
        Fenetres.actualStage.setScene(Fenetres.sceneLA);
    }

}
