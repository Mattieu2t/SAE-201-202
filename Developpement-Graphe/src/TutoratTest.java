/*import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Exeptions.AlreadyExistExeption;
import Exeptions.OutOfRangeException;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;

public class TutoratTest {
    Tutor george = new Tutor("George", 15, 2, SchoolSubject.DEVELOPPEMENT);
    Tutor pauline = new Tutor("Pauline", 11, 3, SchoolSubject.SYSTEME);

    Tutee clement = new Tutee("Clement", 3, new float[]{-2,8,-2,-2,-2,-2,12});
    Tutee michele = new Tutee("Michele", 12, new float[]{-2,0,5,-2,-2,8,-2});
    Tutee aurore = new Tutee("Aurore", 6, new float[]{4,-2,-6,-2,-2,-2,15});
    Tutee max = new Tutee("Max", 0, new float[]{-2,-2,-2,8,-2,-2,-2});

    ArrayList<Tutor> tuteurs = new ArrayList<>();
    ArrayList<Tutee> tutores = new ArrayList<>();

    @BeforeEach
    void initialisation(){
        Student.resetNumberStudent();
        tuteurs.add(george);
        tuteurs.add(pauline);
        
        tutores.add(clement);
        tutores.add(michele);
        tutores.add(aurore);
        tutores.add(max);

        try {
            Tutorat.setPourcentNoteTuteur(50);
            Tutorat.setPourcentAjoutPriorite3emeAnnee(5);
            Tutorat.setPourcentRetraitPrioriteAbscence(5);
        } catch (OutOfRangeException e) {
        }
    }

    @Test
    void attributsTest(){
        assertEquals(Tutorat.getPourcentNoteTuteur(), 50);
        assertEquals(Tutorat.getPourcentNoteTutore(), 50);
        assertEquals(Tutorat.getPourcentAjoutPriorite3emeAnnee(), 5);
        assertEquals(Tutorat.getPourcentRetraitPrioriteAbscence(), 5);

        try {
            Tutorat.setPourcentNoteTuteur(30);
            assertEquals(Tutorat.getPourcentNoteTuteur(), 30);
            assertEquals(Tutorat.getPourcentNoteTutore(), 70);
            Tutorat.setPourcentNoteTutore(40);
            assertEquals(Tutorat.getPourcentNoteTuteur(), 60);
            assertEquals(Tutorat.getPourcentNoteTutore(), 40);
            Tutorat.setPourcentAjoutPriorite3emeAnnee(10);
            assertEquals(Tutorat.getPourcentAjoutPriorite3emeAnnee(), 10);
            Tutorat.setPourcentRetraitPrioriteAbscence(3);
            assertEquals(Tutorat.getPourcentRetraitPrioriteAbscence(), 3);
            // Test Erreur volontaire
            Tutorat.setPourcentNoteTuteur(25);
            
        } catch (OutOfRangeException e) {
            System.out.println(e.getMessage());
        }
        assertFalse(Tutorat.getPourcentNoteTuteur() == 25);
    }

    @Test
    void calculPoidtest(){
        assertEquals(13, Tutorat.calculPoid(george, clement));
        assertEquals(19, Tutorat.calculPoid(pauline, clement));
        assertEquals(23, Tutorat.calculPoid(pauline, aurore));
        assertEquals(999, Tutorat.calculPoid(pauline, max));
    }

    @Test
    void nbAffectationPossibleTest(){
        int res = Tutorat.nbAffectationPossible(tuteurs);
        assertEquals(3, res);
    }

    @Test
    void creeGrapheTest(){
        tuteurs.add(new Tutor());
        tuteurs.add(new Tutor());
        GrapheNonOrienteValue<Student> graphe = Tutorat.creeGraphe(tuteurs, tutores, new GrapheNonOrienteValue<>(), new GrapheNonOrienteValue<>());
        assertEquals("Arete([George], [Clement])",
                      graphe.aretes().get(0).toString());
        assertTrue(graphe.contientArete(george, michele));
        assertTrue(graphe.contientArete(pauline, aurore));
    }

    @Test
    void ajouterAreteTest(){
        GrapheNonOrienteValue<Student> coupleBloqueTest = new GrapheNonOrienteValue<>();
        try{
            Tutorat.ajouterArete(coupleBloqueTest, george, michele);
            Tutorat.ajouterArete(coupleBloqueTest, pauline, max);
        }
        catch(AlreadyExistExeption aee){
            System.out.println(aee.getMessage());
        }

        assertTrue(coupleBloqueTest.contientArete(george, michele));
        assertTrue(coupleBloqueTest.contientArete(pauline, max));
        assertFalse(coupleBloqueTest.contientArete(pauline, clement));
    }

    @Test
    void removeAreteTest(){
        GrapheNonOrienteValue<Student> grapheTest = new GrapheNonOrienteValue<>();
        grapheTest.ajouterSommet(pauline);
        grapheTest.ajouterSommet(aurore);
        grapheTest.ajouterSommet(max);
        grapheTest.ajouterArete(pauline, aurore, 0);
        grapheTest.ajouterArete(pauline, max, 0);
        
        grapheTest = Tutorat.removeArete(grapheTest, pauline, aurore);
        assertFalse(grapheTest.contientArete(pauline, aurore));
        assertTrue(grapheTest.contientArete(pauline, max));
    }

    @Test
    void combleExesTest(){
        Tutorat.combleExesTutores(tuteurs, tutores.size());
        assertEquals(tutores.size(), tuteurs.size());
        tuteurs.add(new Tutor());
        tuteurs.add(new Tutor());
        Tutorat.combleExesTuteurs(tutores, tuteurs.size());
        assertEquals(tuteurs.size(), tutores.size());
    }

    @Test
    void takeArrayListTest(){
        GrapheNonOrienteValue<Student> grapheTest = Tutorat.creeGraphe(tuteurs, tutores, new GrapheNonOrienteValue<>(), new GrapheNonOrienteValue<>());
        ArrayList<Tutor> tmpTutors;
        ArrayList<Tutee> tmpTutees;
        tmpTutors = Tutorat.takeArrayListTutor(grapheTest);
        tmpTutees = Tutorat.takeArrayListTutee(grapheTest);

        assertEquals(tuteurs.size(), tmpTutors.size());
        assertEquals(tutores.size(), tmpTutees.size());
    }

    @Test
    void importeEtudiantstest(){
        // Les objets du CSV sont les mêmes que pour les attributs.
        ArrayList<Tutor> importTuteurs = new ArrayList<>();
        ArrayList<Tutee> importTutores = new ArrayList<>();
        String path = System.getProperty("user.dir")+File.separator+"bin"+File.separator+"res"+File.separator+"listeEtudiantTest.csv";
        Tutorat.importeEtudiants(path, importTuteurs, importTutores);

        assertEquals(george.toString(), importTuteurs.get(0).toString());
        assertEquals(pauline.toString(), importTuteurs.get(1).toString());
        assertEquals(clement.toString(), importTutores.get(0).toString());
        assertEquals(max.toString(), importTutores.get(importTutores.size()-1).toString());
    }
    
    @Test
    @SuppressWarnings("unchecked")
    void serialisationTest(){
        File path = new File(System.getProperty("user.dir")+File.separator+"bin"+File.separator+"res");
        File file1 = new File(path+File.separator+"saveTutor.json");
        File file2 = new File(path+File.separator+"saveTutee.json");
        File file3 = new File(path+File.separator+"saveGrapheForceTutor.json");
        File file4 = new File(path+File.separator+"saveGrapheForceTutee.json");
        File file5 = new File(path+File.separator+"saveGrapheForbiddenTutor.json");
        File file6 = new File(path+File.separator+"saveGrapheForbiddenTutee.json");
        if(file1.exists()){
            file1.delete();
            file2.delete();
            file3.delete();
            file4.delete();
            file5.delete();
            file6.delete();
        }
        GrapheNonOrienteValue<Student> coupleBloqueTest = new GrapheNonOrienteValue<>();
        try {
            Tutorat.ajouterArete(coupleBloqueTest, pauline, aurore);
        } catch (AlreadyExistExeption e) {
            e.printStackTrace();
        }
        GrapheNonOrienteValue<Student> coupleForceTest = new GrapheNonOrienteValue<>();

        Tutorat.serialise(path.getPath(), tuteurs, tutores, coupleBloqueTest, coupleForceTest);
        assertTrue(path.exists());


        Object[] tmp = Tutorat.deserialise(path.getPath());
        tuteurs = (ArrayList<Tutor>)tmp[0];
        tutores = (ArrayList<Tutee>)tmp[1];
        coupleBloqueTest = (GrapheNonOrienteValue<Student>)tmp[2];
        coupleForceTest = (GrapheNonOrienteValue<Student>)tmp[3];

        assertEquals(2, tuteurs.size());
        assertEquals(4, tutores.size());
        assertTrue(Tutorat.contientAreteByID(coupleBloqueTest, pauline, aurore));
        assertTrue(coupleForceTest.aretes().size() == 0);
    }

    @Test
    void sortTest(){
        // Sort Students
        ArrayList<Student> listStudent = Tutorat.castToListeStudent(tutores);
        Tutorat.sortNomDesc(listStudent);
        assertEquals("Michele", listStudent.get(0).getPrenom());
        Tutorat.sortNomAsc(listStudent);
        assertEquals("Aurore", listStudent.get(0).getPrenom());

        Tutorat.sortIDAsc(listStudent);
        assertEquals("Clement", listStudent.get(0).getPrenom());
        Tutorat.sortIDDesc(listStudent);
        assertEquals("Max", listStudent.get(0).getPrenom());

        // Sort Tuteurs
        Tutorat.sortNoteDesc(tuteurs);
        assertEquals("George", tuteurs.get(0).getPrenom());
        Tutorat.sortNoteAsc(tuteurs);
        assertEquals("Pauline", tuteurs.get(0).getPrenom());

        Tutorat.sortYearDesc(tuteurs);
        assertEquals("Pauline", tuteurs.get(0).getPrenom());
        Tutorat.sortYearAsc(tuteurs);
        assertEquals("George", tuteurs.get(0).getPrenom());

        // Sort Tutores
        Tutorat.sortNbAbscenceDesc(tutores);
        assertEquals("Michele", tutores.get(0).getPrenom());
        Tutorat.sortNbAbscenceAsc(tutores);
        assertEquals("Max", tutores.get(0).getPrenom());
    }

    @Test
    void filterTest(){
        // Filter Students
        ArrayList<Student> listStudent = Tutorat.castToListeStudent(tutores);
        ArrayList<Student> listTrierStudent = new ArrayList<>();
        listTrierStudent = Tutorat.filterNom(listStudent, "Aurore");
        assertEquals("Aurore", listTrierStudent.get(0).getPrenom());
        listTrierStudent = Tutorat.filterID(listStudent, 3);
        assertEquals("Michele", listTrierStudent.get(0).getPrenom());

        // Filter Tuteurs
        ArrayList<Tutor> listTrierTutor = new ArrayList<>();
        listTrierTutor = Tutorat.filterNote(tuteurs, 15);
        assertEquals("George", listTrierTutor.get(0).getPrenom());
        listTrierTutor = Tutorat.filterYear(tuteurs, 3);
        assertEquals("Pauline", listTrierTutor.get(0).getPrenom());

        // Filter Tutores
        ArrayList<Tutee> listTrierTutee = new ArrayList<>();
        listTrierTutee = Tutorat.filterNbAbscence(tutores, 3);
        assertEquals("Clement", listTrierTutee.get(0).getPrenom());
    }
}*/