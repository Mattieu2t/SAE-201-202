import java.util.ArrayList;

import fr.ulille.but.sae2_02.graphes.Arete;
import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ControllerListeAffectations {

    @FXML
    private Button buttonAffiche;

    @FXML
    private Button buttonReglage;

    @FXML
    private CheckBox dev;

    @FXML
    private CheckBox graphe;

    @FXML
    private CheckBox ihm;

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
    private Label l6;

    @FXML
    private Label l7;

    @FXML
    private CheckBox math;

    @FXML
    private CheckBox reseau;

    @FXML
    private Button retourMenu;

    @FXML
    private CheckBox system;

    @FXML
    private TextArea tFNA;

    @FXML
    private TextArea tFNom;

    @FXML
    private TextArea tFNote;

    @FXML
    private CheckBox web;

    @FXML
    private Font x1;

    @FXML
    private Font x11;

    @FXML
    private Color x2;

    @FXML
    private Color x21;

    @FXML
    private Font x3;

    @FXML
    private Color x4;

    @FXML
    private ListView<Arete<Student>> listeVueAffectations;

    @FXML
    private Label labelProb;

    @FXML
    void afficherListe(ActionEvent event) {
        // CALCULS
        ArrayList<Tutor> tmpTuteurs = new ArrayList<>();
        ArrayList<Tutee> tmpTutores = new ArrayList<>();
        tmpTuteurs.addAll(Fenetres.tuteurs);
        tmpTutores.addAll(Fenetres.tutores);
        // Ajout des tuteurs des listes forcés
        for (Arete<Student> arete : Fenetres.coupleForce.aretes()) {
            tmpTuteurs.add(((Tutor) arete.getExtremite1()).copyInfo());
        }
        if (Fenetres.tuteurs.size() > Fenetres.tutores.size()) {
            Tutorat.combleExesTuteurs(tmpTutores, Fenetres.tuteurs.size());
        } else if (Fenetres.tuteurs.size() < Fenetres.tutores.size()) {
            Tutorat.combleExesTutores(tmpTuteurs, Fenetres.tutores.size());
        }

        Fenetres.graphe = Tutorat.creeGraphe(tmpTuteurs, tmpTutores, Fenetres.coupleBloque, Fenetres.coupleForce);
        Fenetres.calcul = new CalculAffectation<>(Fenetres.graphe, Tutorat.castToListeStudent(tmpTuteurs), Tutorat.castToListeStudent(tmpTutores));

        // AFFICHAGES
        // Affichages court
        for(Student s : tmpTuteurs){
            s.ecritureCourte = true;
        }
        for(Student s : tmpTutores){
            s.ecritureCourte = true;
        }
        ObservableList<Arete<Student>> ol = FXCollections.observableArrayList(Fenetres.calcul.getAffectation());
        // Suppression affectation bidon
        for(int i=0;i<ol.size();i++){
            if(((Tutor)ol.get(i).getExtremite1()).getMatiere().equals(SchoolSubject.NULL)){
                ol.remove(i);
            }
        }
        ol = filtrer(ol);
        listeVueAffectations.setItems(ol);
    }

    ObservableList<Arete<Student>> filtrer(ObservableList<Arete<Student>> liste){
        labelProb.setText("");
        CheckBox[] listCB = new CheckBox[]{math,dev,ihm,graphe,web,reseau,system};
        try{
            for(int i=0;i<liste.size();i++){
                Tutor tuteur = (Tutor)liste.get(i).getExtremite1();
                Tutee tutore = (Tutee)liste.get(i).getExtremite2();
                boolean exist = true;
                // Filtre Prenom
                if(exist && tFNom.getText().length() > 0 && !tuteur.getPrenom().equals(tFNom.getText()) && !tutore.getPrenom().equals(tFNom.getText())){
                    liste.remove(i);
                    exist = false;
                    i--;
                }
                // Filtre Note
                if(exist && tFNote.getText().length() > 0 && tuteur.getNote() != Float.parseFloat(tFNote.getText()) && 
                                            tutore.getBulletin().noteMatieres.get(tuteur.getMatiere()) != Float.parseFloat(tFNote.getText())){
                    liste.remove(i);
                    exist = false;
                    i--;
                }
                // Filtre Nombre Abscence
                if(exist && tFNA.getText().length() > 0 && tutore.getNbAbscence() != Float.parseFloat(tFNA.getText())){
                    liste.remove(i);
                    exist = false;
                    i--;
                }
                // Filtre Matiere
                for(int x=0;x<SchoolSubject.values().length-1;x++){
                    if(exist && !listCB[x].isSelected() && SchoolSubject.values()[x].equals(tuteur.getMatiere())){
                        liste.remove(i);
                        exist = false;
                        i--;
                        x = SchoolSubject.values().length;
                    }
                }
            }
            return liste;
        }catch(Exception e){
            labelProb.setTextFill(Color.RED);
            labelProb.setText("Filtres Invalide !");
        }

        return liste;
    }

    @FXML
    void backToMenu(ActionEvent event) {
        Fenetres.actualStage.setTitle("Menu");
        Fenetres.actualStage.setScene(Fenetres.sceneM);
    }

    @FXML
    void settings(ActionEvent event) {
        Fenetres.actualStage.setTitle("Reglages");
        Fenetres.actualStage.setScene(Fenetres.sceneR);
    }

    @FXML
    void selectAffectation(MouseEvent event){
        if(listeVueAffectations.getSelectionModel().getSelectedItem() != null){
            Arete<Student> select = listeVueAffectations.getSelectionModel().getSelectedItem();
            // Tuteur
            Tutor tuteur = (Tutor)(select.getExtremite1());

            l1.setText(tuteur.getPrenom());
            l2.setText(""+tuteur.getAnnee());
            l3.setText(""+tuteur.getNote());
            l4.setText(""+tuteur.getMatiere());

            // Tutore
            Tutee tutore = (Tutee)(select.getExtremite2());
            l5.setText(tutore.getPrenom());
            l6.setText(""+tutore.getBulletin().noteMatieres.get(tuteur.getMatiere()));
            l7.setText(""+tutore.getNbAbscence());
        }
    }
}
