import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ControllerListeEtudiants implements Initializable{
    private String prenom;
    private int annee;
    private int nbAbscence;
    private float[] notes = new float[7]; 

    @FXML
    private Button boutonAfficheListe;

    @FXML
    private Button boutonAjouter;

    @FXML
    private CheckBox dev;

    @FXML
    private CheckBox dev1;

    @FXML
    private CheckBox graphe;

    @FXML
    private CheckBox graphe1;

    @FXML
    private CheckBox ihm;

    @FXML
    private CheckBox ihm1;

    @FXML
    private Label l1;

    @FXML
    private Label l2;

    @FXML
    private Label l3;

    @FXML
    private Label l4;

    @FXML
    private Label l5;

    @FXML
    private Text l41;

    @FXML
    private Label labelProb;

    @FXML
    private ListView<Student> listeVueEtudiants;

    @FXML
    private CheckBox math;

    @FXML
    private CheckBox math1;

    @FXML
    private RadioButton one;

    @FXML
    private CheckBox reseau;

    @FXML
    private CheckBox reseau1;

    @FXML
    private Button retourMenu;

    @FXML
    private CheckBox system;

    @FXML
    private CheckBox system1;

    @FXML
    private TextField tfNombreAbscence;

    @FXML
    private TextField tfPrenom;

    @FXML
    private TextField n1;
    @FXML
    private TextField n2;
    @FXML
    private TextField n3;
    @FXML
    private TextField n4;
    @FXML
    private TextField n5;
    @FXML
    private TextField n6;
    @FXML
    private TextField n7;

    @FXML
    private RadioButton three;

    @FXML
    private ComboBox<String> tri;

    @FXML
    private RadioButton two;

    @FXML
    private CheckBox web;

    @FXML
    private CheckBox web1;

    @FXML
    private Font x1;

    @FXML
    private Font x12;

    @FXML
    private Color x2;

    @FXML
    private Color x22;

    @FXML
    private Font x3;

    @FXML
    private Color x4;

    @FXML
    private CheckBox cbD;

    @FXML
    void add(ActionEvent event) {
        labelProb.setTextFill(Color.RED);
        labelProb.setText("");

        // Matières
        CheckBox[] listCB = new CheckBox[]{math,dev,ihm,graphe,web,reseau,system};
        TextField[] listTF = new TextField[]{n1,n2,n3,n4,n5,n6,n7};
        try {
        for(int i=0;i<7;i++){
            if(listCB[i].isSelected()){
                    if(Integer.parseInt(listTF[i].getText()) > -1 && Integer.parseInt(listTF[i].getText()) < 21){
                        notes[i] = Float.parseFloat(listTF[i].getText());
                    }
                    else{
                        throw new NullPointerException();
                    }
            }
            else{
                notes[i] = (float)-2;
            }
        }
        } catch (Exception e) {
            labelProb.setText("Matières et/ou notes Invalide !");
        }

        try{
            // Nombre abscence
            if(Integer.parseInt(tfNombreAbscence.getText()) > -1){
                nbAbscence = Integer.parseInt(tfNombreAbscence.getText());
            }else{
                throw new Exception();
            }
        }catch(Exception e){
            labelProb.setText("Nombre d'abscence invalide !");
        }

        // Année
        if(one.isSelected()){
            annee = 1;
        }else if(two.isSelected()){
            annee = 2;
        }else if(three.isSelected()){
            annee = 3;
        }else{
            labelProb.setText("Aucune année !");
        }

        // Prenom
        if(tfPrenom.getText().length() < 2){
            labelProb.setText("Prenom trop court !");
        }else{
            prenom = tfPrenom.getText();
        }

        if(labelProb.getText().length() == 0){
            ajouterEtudiant();
        }
    }

    void ajouterEtudiant(){
        if(annee == 1){
            Fenetres.tutores.add(new Tutee(prenom, nbAbscence, notes));
            labelProb.setTextFill(Color.GREEN);
            labelProb.setText("Tutore ajouté !");
        }
        else{
            int valid = 0;
            int index = 0;
            for(int i=0;i<notes.length;i++){
                if(notes[i] != -2){
                    index = i;
                    valid++;
                }
            }
            if(valid == 1){
                Fenetres.tuteurs.add(new Tutor(prenom, notes[index], annee, SchoolSubject.values()[index]));
                labelProb.setTextFill(Color.GREEN);
                labelProb.setText("Tuteur ajouté !");
            }
            else{
                labelProb.setText("La matière pour le tuteur :\n-n'est pas seul !\n- n'est pas selectionné !");
            }
        }
    }

    @FXML
    void afficherListe(ActionEvent event) {
        ArrayList<Student> listeEtudiants = new ArrayList<>();
        listeEtudiants = trier(listeEtudiants);
        for(Tutor tuteur : Fenetres.tuteurs){
            tuteur.ecritureCourte = false;
            listeEtudiants.add((Student)tuteur);
        }
        for(Tutee tutore : Fenetres.tutores){
            tutore.ecritureCourte = false;
            listeEtudiants.add((Student)tutore);
        }
        listeEtudiants = trier(listeEtudiants);
        ObservableList<Student> ol = FXCollections.observableArrayList(listeEtudiants);
        listeVueEtudiants.setItems(ol);
    }

    @FXML
    void backToMenu(ActionEvent event) {
        Fenetres.actualStage.setTitle("Menu");
        Fenetres.actualStage.setScene(Fenetres.sceneM);
    }

    @FXML
    void selectAnnee1(ActionEvent event) {

    }
    @FXML
    void selectAnnee2(ActionEvent event) {

    }
    @FXML
    void selectAnnee3(ActionEvent event) {

    }

    @FXML
    void selectMatiere(ActionEvent event) {

    }

    @FXML
    void selectStudent(MouseEvent event) {
        if(listeVueEtudiants.getSelectionModel().getSelectedItem() != null){
            Student select = listeVueEtudiants.getSelectionModel().getSelectedItem();
            l2.setText(select.getPrenom());
            if(select.getClass().getSimpleName().equals("Tutor")){
                Tutor selectT = (Tutor)select;
                l1.setText("Tuteur");
                l3.setText(""+selectT.getAnnee());
                l41.setText("Note :");
                l4.setText(""+selectT.getNote());
                l5.setText(""+selectT.getMatiere());
            }
            else{
                Tutee selectT = (Tutee)select;
                l1.setText("Tutore");
                l3.setText("1");
                l41.setText("Nombre d'abscence :");
                l4.setText(""+selectT.getNbAbscence());
                String matieres = "";
                for(Map.Entry<SchoolSubject, Float> entry : selectT.getBulletin().noteMatieres.entrySet()){
                    matieres += entry.getKey()+" : "+entry.getValue()+"\n";
                }
                l5.setText(matieres);
            }
        }
    }

    ArrayList<Student> trier(ArrayList<Student> liste) {
        if(!cbD.isSelected()){
            switch(tri.getSelectionModel().getSelectedIndex()){
                case 0: // Prenom
                    liste = Tutorat.sortNomAsc(liste);
                    break;

                case 1: // Note Tuteur
                    Fenetres.tuteurs = Tutorat.sortNoteAsc(Fenetres.tuteurs);
                    break;

                case 2: // Annee
                    Fenetres.tuteurs = Tutorat.sortYearAsc(Fenetres.tuteurs);
                    break;

                case 3: // Nombre abscence
                    Fenetres.tutores = Tutorat.sortNbAbscenceAsc(Fenetres.tutores);
                    break;

                case 4: // Identifiant
                    liste = Tutorat.sortIDAsc(liste);
                    break;
            }
        }
        else{
            switch(tri.getSelectionModel().getSelectedIndex()){
                case 0: // Prenom
                    liste = Tutorat.sortNomDesc(liste);
                    break;

                case 1: // Note Tuteur
                    Fenetres.tuteurs = Tutorat.sortNoteDesc(Fenetres.tuteurs);
                    break;

                case 2: // Annee
                    Fenetres.tuteurs = Tutorat.sortYearDesc(Fenetres.tuteurs);
                    break;

                case 3: // Nombre abscence
                    Fenetres.tutores = Tutorat.sortNbAbscenceDesc(Fenetres.tutores);
                    break;

                case 4: // Identifiant
                    liste = Tutorat.sortIDDesc(liste);
                    break;
            }
        }
        return liste;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        tri.getItems().addAll("Nom","Note Tuteur","Année Tuteur","Nombre d'abscence", "Identifiant");
    }
}
