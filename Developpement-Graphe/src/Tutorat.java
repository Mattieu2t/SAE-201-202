
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import Exeptions.AlreadyExistExeption;
import Exeptions.LineNotValidExeption;
import Exeptions.OutOfRangeException;
import fr.ulille.but.sae2_02.graphes.Arete;
import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;

public abstract class Tutorat{
    private static float pourcentNoteTutore = 100;
    private static float pourcentNoteTuteur = 100;
    private static float pourcentAjoutPriorite3emeAnnee = 10;
    private static float pourcentRetraitPrioriteRetard = 5;

    public static float getPourcentNoteTutore() {
        return pourcentNoteTutore/2;
    }
    // Quand on modifie la priorité de la note du tutore, la priorité de la note du tuteur est compenser pour atteindre 100%
    // Est compris entre 30 -> 70
    public static void setPourcentNoteTutore(int pourcentNoteTutore) throws OutOfRangeException{
        if(pourcentNoteTutore < 71 && pourcentNoteTutore > 29){
            Tutorat.pourcentNoteTutore = pourcentNoteTutore*2;
            Tutorat.pourcentNoteTuteur = (100 - pourcentNoteTutore) *2;
        }
        else{
            throw new OutOfRangeException();
        }
    }
    public static float getPourcentNoteTuteur() {
        return pourcentNoteTuteur/2;
    }
    // Quand on modifie la priorité de la note du tuteur, la priorité de la note du tutoré est compenser pour atteindre 100%
    // Est compris entre 30 -> 70
    public static void setPourcentNoteTuteur(int pourcentNoteTuteur) throws OutOfRangeException{
        if(pourcentNoteTuteur < 71 && pourcentNoteTuteur > 29){
            Tutorat.pourcentNoteTuteur = pourcentNoteTuteur*2;
            Tutorat.pourcentNoteTutore = (100 - pourcentNoteTuteur) *2;
        }
        else{
            throw new OutOfRangeException();
        }
    }
    public static float getPourcentAjoutPriorite3emeAnnee() {
        return pourcentAjoutPriorite3emeAnnee;
    }
    // Est compris entre 0 -> 20
    public static void setPourcentAjoutPriorite3emeAnnee(int pourcentAjoutPriorite3emeAnnee) throws OutOfRangeException{
        if(pourcentAjoutPriorite3emeAnnee < 21 && pourcentAjoutPriorite3emeAnnee > -1){
            Tutorat.pourcentAjoutPriorite3emeAnnee = pourcentAjoutPriorite3emeAnnee;
        }
        else{
            throw new OutOfRangeException();
        }
    }
    public static float getPourcentRetraitPrioriteAbscence() {
        return pourcentRetraitPrioriteRetard;
    }
    // Est compris entre 0 -> 10
    public static void setPourcentRetraitPrioriteAbscence(int pourcentRetraitPrioriteRetard) throws OutOfRangeException{
        if(pourcentRetraitPrioriteRetard < 11 && pourcentRetraitPrioriteRetard > -1){
            Tutorat.pourcentRetraitPrioriteRetard = pourcentRetraitPrioriteRetard;
        }
        else{
            throw new OutOfRangeException();
        }
    }

    /** Calcul le poids entre un tuteur et un tutore sur la matiére du tuteut.
     * Methodes : (noteTutore(50%Defaut) + 20 - noteTuteur(50%Defaut)) 
     * : si 3eme annee(-5%TotalDefaut) : si 2eme annee(+5%TotalDefaut) : (nbAbscenceTutore / 4) * (+5%TotalDefaut)
     * Calcul par les notes GrosseValeur->PetiteValeur == Moins important -> Plus important.
     * @param Student tuteur
     * @param Student tutore
     * @return int poids
     */
    public static int calculPoid(Tutor tuteur, Tutee tutore){
        int poid = 999;
        if(tutore.getBulletin().getNoteMatieres().get(tuteur.getMatiere()) != null){
            // Calcul par les notes 40->0 == Moins important -> Plus important
            poid = Math.round(tutore.getBulletin().getNoteMatieres().get(tuteur.getMatiere()) * pourcentNoteTutore / 100) + 20; // Ajout note tutore
            poid -= tuteur.getNote() * pourcentNoteTuteur / 100; // Ajout note tuteur
            // Priorite pour les 3emes année
            if(tuteur.getAnnee() == 3){
                poid -= poid * pourcentAjoutPriorite3emeAnnee / 100;
            }
            else{
                poid += poid * pourcentAjoutPriorite3emeAnnee / 100;
            }
            // Penalite 4 demi journée d'abscence
            if(tutore.getNbAbscence() > 3){
                poid += tutore.getNbAbscence()/4 * (poid * pourcentRetraitPrioriteRetard / 100);
            }
        }
        return poid;
    }

    /** Rajoute des possibilités d'ajout d'étudiant pour les tuteurs de 3eme annee
     * 
     * @param graphe
     * @param tuteurs
     * @param nombreTutores
     */
    static void combleExesTutores(ArrayList<Tutor> tuteurs, int sizeTutores){
        int firstSizeTuteurs = tuteurs.size();
        int i=0;
        while(tuteurs.size() != sizeTutores){
            // On double les tuteurs de 3èeme annee
            if(i < firstSizeTuteurs){
                if(tuteurs.get(i).getAnnee() == 3){
                    tuteurs.add(tuteurs.get(i).copyInfo());
                }
            }
            // On comble avec des Tuteurs bidon
            else{
                tuteurs.add(new Tutor());
            }
            i++;
        }
    }

    /** Rajoute des étudiants bidon selon le besoin
     * 
     * @param graphe
     * @param tuteurs
     * @param nombreTutores
     */
    static void combleExesTuteurs(ArrayList<Tutee> tutores, int sizeTuteurs){
        int firstSizeTutores = tutores.size();
        for(int i=0;i<(sizeTuteurs-firstSizeTutores);i++){
            // On comble avec des Tuteurs bidon
            tutores.add(new Tutee());
        }
    }

    /** Cree un graphe à partir de deux listes tuteur, tutore et d'un graphe avec les couples qui n'existe pas
     * 
     * @param ArrayList<Student> tuteurs
     * @param ArrayList<Student> tutores
     * @return GrapheNonOrienteValue<Student> graphe
     */
    public static GrapheNonOrienteValue<Student> creeGraphe(ArrayList<Tutor> tuteurs, ArrayList<Tutee> tutores, GrapheNonOrienteValue<Student> coupleBloque, GrapheNonOrienteValue<Student> coupleForce){
        GrapheNonOrienteValue<Student> graphe = new GrapheNonOrienteValue<>();

        // Ajout sommets
        for(Student s:tutores){
            graphe.ajouterSommet(s);
        }
        for(Student s:tuteurs){
            graphe.ajouterSommet(s);
        }

        // Ajout aretes
        for(Tutor tuteur:tuteurs){
            for(Tutee tutore:tutores){
                // Si le couple est forcé
                if(coupleForce.contientArete(tuteur, tutore)){
                    graphe.ajouterArete(tuteur, tutore, -999);
                }
                // Si le couple est souhaité
                else if(!coupleBloque.contientArete(tuteur, tutore) && tutore.getBulletin().noteMatieres.containsKey(tuteur.getMatiere())){
                    graphe.ajouterArete(tuteur, tutore, calculPoid(tuteur, tutore));
                }else{
                    graphe.ajouterArete(tuteur, tutore, 999);
                }
            }
        }
        return graphe;
    }

    public static GrapheNonOrienteValue<Student> removeArete(GrapheNonOrienteValue<Student> graphe, Student extremite1, Student extremite2) {
        GrapheNonOrienteValue<Student> grapheFin = new GrapheNonOrienteValue<>();
        for(Arete<Student> arete : graphe.aretes()){
            grapheFin.ajouterSommet(arete.getExtremite1());
            grapheFin.ajouterSommet(arete.getExtremite2());
            if(arete.getExtremite1().getID() != extremite1.getID() || arete.getExtremite2().getID() != extremite2.getID()){
                grapheFin.ajouterArete(arete.getExtremite1(), arete.getExtremite2(), calculPoid((Tutor) arete.getExtremite1(), (Tutee) arete.getExtremite2()));
            }
        }
        return grapheFin;
    }

    /** Renvoie la capacité maximal de tutores povant avoir un tuteur
     * 
     * @param tuteurs
     * @return nbAffectationPossible
     */
    public static int nbAffectationPossible(ArrayList<Tutor> tuteurs){
        int nbAffectationPossible = 0;
        for(Tutor tuteur : tuteurs){
            if(tuteur.getAnnee() == 2){
                nbAffectationPossible++;
            }
            else{
                nbAffectationPossible += 2;
            }
        }
        return nbAffectationPossible;
    }

    public static void ajouterArete(GrapheNonOrienteValue<Student> graphe, Tutor tuteur, Tutee tutore) throws AlreadyExistExeption{
        if(!graphe.contientSommet(tuteur)){
            graphe.ajouterSommet((Student)tuteur);
        }
        if(!graphe.contientSommet(tutore)){
            graphe.ajouterSommet((Student)tutore);
        }
        if(graphe.contientArete(tuteur, tutore)){
            throw new AlreadyExistExeption("Ce bloquage existe déjà !");
        }
        else{
            graphe.ajouterArete(tuteur, tutore, 100);
        }
    }

    /** Upcast une liste de Tutor ou de Tutee en liste de Student 
     * 
     * @param ArrayList<? extends Student> liste
     * @return ArrayList<Student> tmp
     */
    public static ArrayList<Student> castToListeStudent(ArrayList<? extends Student> liste){
        ArrayList<Student> tmp = new ArrayList<>();
        for(int i=0;i<liste.size();i++){
            tmp.add((Student) liste.get(i));
        }
        return tmp;
    }

    public static ArrayList<Tutor> castToListeTutor(ArrayList<? super Student> liste){
        ArrayList<Tutor> tmp = new ArrayList<>();
        for(int i=0;i<liste.size();i++){
            tmp.add((Tutor) liste.get(i));
        }
        return tmp;
    }

    public static ArrayList<Tutee> castToListeTutee(ArrayList<? super Student> liste){
        ArrayList<Tutee> tmp = new ArrayList<>();
        for(int i=0;i<liste.size();i++){
            tmp.add((Tutee) liste.get(i));
        }
        return tmp;
    }

    public static ArrayList<Tutor> takeArrayListTutor(GrapheNonOrienteValue<Student> graphe){
        ArrayList<Tutor> tuteurs = new ArrayList<>();
        ArrayList<Integer> IDs = new ArrayList<>();
        for(int i=0;i<graphe.aretes().size();i++){
            Tutor extremite1 = (Tutor)graphe.aretes().get(i).getExtremite1();
            if(!IDs.contains(extremite1.getID())){
                tuteurs.add(extremite1);
                IDs.add(extremite1.getID());
            }
        }
        return tuteurs;
    }

    public static ArrayList<Tutee> takeArrayListTutee(GrapheNonOrienteValue<Student> graphe){
        ArrayList<Tutee> tutores = new ArrayList<>();
        ArrayList<Integer> IDs = new ArrayList<>();
        for(int i=0;i<graphe.aretes().size();i++){
            Tutee extremite2 = (Tutee)graphe.aretes().get(i).getExtremite2();
            if(!IDs.contains(extremite2.getID())){
                tutores.add(extremite2);
                IDs.add(extremite2.getID());
            }
        }
        return tutores;
    }

    /** private : récupére dans une arrayList les notes d'un étudiant
     * 
     * @param infoStudent
     * @return ArrayList<Float>
     */
    private static float[] recupNotes(String[] infoStudent){
        // One neprend pas les 3 première valeur : Prenom;Annee;NbAbscence
        float[] listeNote = new float[infoStudent.length-3];
        for(int i=0;i<infoStudent.length-3;i++){
            listeNote[i] = Float.parseFloat(infoStudent[i+3]);
        }
        return listeNote;
    }

    public static void verifLine(String[] infoStudent, int i) throws LineNotValidExeption{
        String message = "Erreur lors de l'importation ligne "+i;
        
        if(infoStudent.length > 3 && infoStudent.length < 11){
            if(infoStudent[0].length() == 0){
                throw new LineNotValidExeption(message+" : prénom colone 1 Invalides !");
            }
            else{
                int annee = -1;
                annee = Integer.parseInt(infoStudent[1]);
                // Tutore
                if(annee == 1){
                    if(Integer.parseInt(infoStudent[2]) > -1){
                        boolean erreur = false;
                        int col = 1;
                        for(int x=3;x<10;x++){
                            try{
                                if((Integer.parseInt(infoStudent[x]) < 0 || Integer.parseInt(infoStudent[x]) > 20) && Integer.parseInt(infoStudent[x]) != -2){
                                    erreur = true;
                                    col += x;
                                }
                            }catch(Exception e){
                                col += x;
                                throw new LineNotValidExeption(message+" : note colone "+col+" Invalide !");
                            }
                        }
                        if(erreur){
                            throw new LineNotValidExeption(message+" : note colone "+col+" Invalide !");
                        }
                    }
                    else{
                        message += " : nombre abscence colone 3 Invalide !";
                        throw new LineNotValidExeption(message);
                    }
                }
                // Tuteur
                else if(annee == 2 || annee == 3){
                    try{
                        if(Integer.parseInt(infoStudent[2]) > -1 && Integer.parseInt(infoStudent[2]) < 21){
                            try{
                            if(SchoolSubject.valueOf(infoStudent[3]) == null){
                                throw new LineNotValidExeption(message+" : matiére colone 4 Invalide !");
                            }
                            }catch(Exception e){
                                throw new LineNotValidExeption(message+" : matiére colone 4 Invalide !");
                            }
                        }
                    }catch(Exception e){
                        throw new LineNotValidExeption(message+" : note colone 3 Invalide !");
                    }
                }
            }
        }
        else{
            throw new LineNotValidExeption(message+" : annee colones Invalides !");
        }
    }

    /** */

    /** Recupere les données d'un fichier csv d'étudiants donner en parametre dans les liste tuteurs et tutores
     * Construction:
     * Tuteur : Prenom; annee; note; matiere;
     * Tutore : Prenom; annee; nbAbscence; noteMATHS; noteDEV; noteIHM; noteGRAPHE; noteWEB; noteRESEAU; noteSYSTEME; Si -1 = pas souhaiter
     * 
     * @param String chemin
     * @param ArrayList<Student> tuteurs
     * @param ArrayList<Student> tutores
     * @throws IOException
     * @throws LineNotValidExeption
     */
    public static void importeEtudiants(String chemin, ArrayList<Tutor> tuteurs, ArrayList<Tutee> tutores) throws IOException, LineNotValidExeption{
        // Ouverture puis lecture du fichier CSV
        BufferedReader br = new BufferedReader(new FileReader(chemin));
        int i = 1;
        String line = br.readLine();
        if(line == null){
            br.close();
            throw new IOException("CSV Vide !");
        }
        String[] infoStudent = line.split(";");
        verifLine(infoStudent, i);
        tuteurs.clear();
        tutores.clear();
        while(line != null) {
            // repartition etudiants selon tuteur/tutore A MODIF ARRAYLIST<ShoolSubject>
            if(Integer.parseInt(infoStudent[1])>1){
                tuteurs.add(new Tutor(infoStudent[0], Float.parseFloat(infoStudent[2]), Integer.parseInt(infoStudent[1]),SchoolSubject.valueOf(infoStudent[3].toUpperCase())));
            }
            else{
                tutores.add(new Tutee(infoStudent[0], Integer.parseInt(infoStudent[2]), recupNotes(infoStudent)));
            }
            // Ligne suivante
            i++;
            line = br.readLine();
            if(line != null)infoStudent = line.split(";");
            verifLine(infoStudent, i);
        }
        br.close();
    }

    /** Créé un fichier Json avec à partir de 2 Arraylistes et d'un graphe
     * 
     * @param chemin
     * @param tuteurs
     * @param tutores
     * @param coupleBloque
     */
    public static void serialise(String chemin, ArrayList<Tutor> tuteurs, ArrayList<Tutee> tutores, 
                                 GrapheNonOrienteValue<Student> coupleBloque, GrapheNonOrienteValue<Student> coupleForce){
        // Creation ou remplacement puis ecriture du fichier Json
        try{
            FileWriter f1 = new FileWriter(chemin+File.separator+"saveTutor.json");
            f1.flush();
            FileWriter f2 = new FileWriter(chemin+File.separator+"saveTutee.json");
            f2.flush();
            FileWriter f3 = new FileWriter(chemin+File.separator+"saveGrapheForbiddenTutor.json");
            f3.flush();
            FileWriter f4 = new FileWriter(chemin+File.separator+"saveGrapheForceTutor.json");
            f4.flush();
            FileWriter f5 = new FileWriter(chemin+File.separator+"saveGrapheForbiddenTutee.json");
            f5.flush();
            FileWriter f6 = new FileWriter(chemin+File.separator+"saveGrapheForceTutee.json");
            f6.flush();
            BufferedWriter bwTutors = new BufferedWriter(f1);
            BufferedWriter bwTutees = new BufferedWriter(f2);
            BufferedWriter bwGrapheForbiddenTutor = new BufferedWriter(f3);
            BufferedWriter bwGrapheForceTutor = new BufferedWriter(f4);
            BufferedWriter bwGrapheForbiddenTutee = new BufferedWriter(f5);
            BufferedWriter bwGrapheForceTutee = new BufferedWriter(f6);
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            bwTutors.write(gson.toJson(tuteurs));
            bwTutees.write(gson.toJson(tutores));
            bwGrapheForbiddenTutor.write(gson.toJson(takeArrayListTutor(coupleBloque)));
            bwGrapheForceTutor.write(gson.toJson(takeArrayListTutor(coupleForce)));
            bwGrapheForbiddenTutee.write(gson.toJson(takeArrayListTutee(coupleBloque)));
            bwGrapheForceTutee.write(gson.toJson(takeArrayListTutee(coupleForce)));

            bwTutors.close();
            bwTutees.close();
            bwGrapheForbiddenTutor.close();
            bwGrapheForceTutor.close();
            bwGrapheForbiddenTutee.close();
            bwGrapheForceTutee.close();
			
		} catch(FileNotFoundException e) {
			System.out.println("File not found: "+e.getMessage());
			
		} catch(IOException e) {
			System.out.println("Reading error: " + e.getMessage());
			e.printStackTrace();
		}
    }

    /** Désérialise depuis un Json dans 2 Arraylistes et d'un graphe
     * @param <E>
     * 
     * @param chemin
     * @param tuteurs
     * @param tutores
     * @param coupleBloque
     */
    public static Object[] deserialise(String chemin){
        // Ouverture puis lecture du fichier Json
        Object[] tmp = new Object[4];
        try{
            BufferedReader brTutor = new BufferedReader(new FileReader(chemin+File.separator+"saveTutor.json"));
            BufferedReader brTutee = new BufferedReader(new FileReader(chemin+File.separator+"saveTutee.json"));
            BufferedReader brGrapheForbiddenTutor = new BufferedReader(new FileReader(chemin+File.separator+"saveGrapheForbiddenTutor.json"));
            BufferedReader brGrapheForceTutor = new BufferedReader(new FileReader(chemin+File.separator+"saveGrapheForceTutor.json"));
            BufferedReader brGrapheForbiddenTutee = new BufferedReader(new FileReader(chemin+File.separator+"saveGrapheForbiddenTutee.json"));
            BufferedReader brGrapheForceTutee = new BufferedReader(new FileReader(chemin+File.separator+"saveGrapheForceTutee.json"));
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            Type tutorTypeList = new TypeToken<ArrayList<Tutor>>(){}.getType();
            Type tuteeTypeList = new TypeToken<ArrayList<Tutee>>(){}.getType();

            // Liste Tuteurs
            tmp[0] = gson.fromJson(brTutor, tutorTypeList);
            // Liste Tutores
            tmp[1] = gson.fromJson(brTutee, tuteeTypeList);
            // Graphe coupleBloquer
            ArrayList<Tutor> listTutorForbid = gson.fromJson(brGrapheForbiddenTutor, tutorTypeList);
            ArrayList<Tutee> listTuteeForbid = gson.fromJson(brGrapheForbiddenTutee, tuteeTypeList);
            tmp[2] = creeGraphe(listTutorForbid, listTuteeForbid, new GrapheNonOrienteValue<>(), new GrapheNonOrienteValue<>());
            // Graphe coupleForcer
            ArrayList<Tutor> listTutorForced = gson.fromJson(brGrapheForceTutor, tutorTypeList);
            ArrayList<Tutee> listTuteeForced = gson.fromJson(brGrapheForceTutee, tuteeTypeList);
            tmp[3] = creeGraphe(listTutorForced, listTuteeForced, new GrapheNonOrienteValue<>(), new GrapheNonOrienteValue<>());
		
		} catch(FileNotFoundException e) {
			System.out.println("File not found : "); e.printStackTrace();
		}catch(Exception e){
            System.out.println("Probleme lors du chargement de la sauvegarde :"+e);
        }
        return tmp;
    }

    /** Verifie le nombre de fichier AffectationsEtudiants(?).csv
     * 
     * @param chemin
     * @return int nbFile
     */
    static int nbExportAlreadyExist(String chemin){
        int nbFile = 0;
        File folder = new File(chemin);
        if(folder.isDirectory()){

            String supplement = "";
            File file = new File(chemin+File.separator+"listeAffectation.csv");

            int cherche = 0;
            while(cherche < folder.listFiles().length){
                if(file.exists()){
                    nbFile++;
                    supplement = "("+nbFile+")";
                }
                file = new File(chemin+File.separator+"affectationsEtudiants"+supplement+".csv");
                cherche++;
            }
        }
        return nbFile;
    }

    /** Créé un fichier CSV avec la liste des affectations
     * Si le fichier existe deja, ajoute nom(1)
     * @param chemin
     * @param affectations
     * @throws IOException
     */
    static void exporteAffectations(String chemin, CalculAffectation<Student> affectations, GrapheNonOrienteValue<Student> coupleBloquer, GrapheNonOrienteValue<Student> coupleForce) throws IOException, NullPointerException{
        if(affectations != null){
            String supplement = ""; // En cas de fichier dèja existant
            int nombreFichier = nbExportAlreadyExist(chemin);
            if(nombreFichier > 0){
                supplement = "("+nombreFichier+")";
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(chemin+File.separator+"listeAffectation"+supplement+".csv"));
            //Nom Colonne
            bw.write("Tuteur;Tutore;Matière;");
            bw.newLine();
            for(int i=0;i<affectations.getAffectation().size();i++) {
                SchoolSubject matiere = ((Tutor)affectations.getAffectation().get(i).getExtremite1()).getMatiere();
                Tutor tuteur = (Tutor)affectations.getAffectation().get(i).getExtremite1();
                Tutee tutore = (Tutee)affectations.getAffectation().get(i).getExtremite2();
                // Si le couple n'est pas BIDON (null) ET que (Il possède la même matière OU il est forcé)
                if(matiere != SchoolSubject.NULL && 
                    (contientAreteByID(coupleForce, tuteur, tutore) ||
                    tutore.getBulletin().noteMatieres.containsKey(matiere))){
                    // Si le couple n'est pas bloquer
                    if(!contientAreteByID(coupleBloquer, tuteur, tutore)){
                        bw.write(tuteur.getPrenom()+";");
                        bw.write(tutore.getPrenom()+";");
                        bw.write(matiere.name()+";");
                        bw.newLine();
                    }
                }
            }
            System.out.println("Ecriture reussie");
            bw.close();
        }
        else{
            throw new NullPointerException("Aucune liste calculé !");
        }
    }

    public static boolean contientAreteByID(GrapheNonOrienteValue<Student> graphe, Student s1, Student s2){
        boolean rep = false;
        int i=0;
        Student extremite1, extremite2;
        while(!rep && i<graphe.aretes().size()){
            extremite1 = graphe.aretes().get(i).getExtremite1();
            extremite2 = graphe.aretes().get(i).getExtremite2();
            if(extremite1.getID() == s1.getID() && extremite2.getID() == s2.getID()){
                rep = true;
            }
            i++;
        }
        return rep;
    }

    /**
     * 
     * @param liste
     */
    static ArrayList<Student> sortNomAsc(ArrayList<Student> liste){
        Collections.sort(liste, new Student.SortByName());
        return liste;
    }

    /**
     * 
     * @param liste
     */
    static ArrayList<Student> sortNomDesc(ArrayList<Student> liste){
        Collections.sort(liste, new Student.SortByName());
        Collections.reverse(liste);
        return liste;
    }

    /**
     * 
     * @param liste
     */
    static ArrayList<Tutor> sortNoteAsc(ArrayList<Tutor> liste){
        Collections.sort(liste, new Tutor.SortByNote());
        return liste;
    }

    /**
     * 
     * @param liste
     */
    static ArrayList<Tutor> sortNoteDesc(ArrayList<Tutor> liste){
        Collections.sort(liste, new Tutor.SortByNote());
        Collections.reverse(liste);
        return liste;
    }

    /**
     * 
     * @param liste
     */
    static ArrayList<Tutor> sortYearAsc(ArrayList<Tutor> liste){
        Collections.sort(liste, new Tutor.SortByYear());
        return liste;
    }

    /**
     * 
     * @param liste
     */
    static ArrayList<Tutor> sortYearDesc(ArrayList<Tutor> liste){
        Collections.sort(liste, new Tutor.SortByYear());
        Collections.reverse(liste);
        return liste;
    }

    /**
     * 
     * @param liste
     */
    static ArrayList<Tutee> sortNbAbscenceAsc(ArrayList<Tutee> liste){
        Collections.sort(liste, new Tutee.SortByNbAbscence());
        return liste;
    }

    /**
     * 
     * @param liste
     */
    static ArrayList<Tutee> sortNbAbscenceDesc(ArrayList<Tutee> liste){
        Collections.sort(liste, new Tutee.SortByNbAbscence());
        Collections.reverse(liste);
        return liste;
    }

    /**
     * 
     * @param liste
     */
    static ArrayList<Student> sortIDAsc(ArrayList<Student> liste){
        Collections.sort(liste, new Student.SortByID());
        return liste;
    }

    /**
     * 
     * @param liste
     */
    static ArrayList<Student> sortIDDesc(ArrayList<Student> liste){
        Collections.sort(liste, new Student.SortByID());
        Collections.reverse(liste);
        return liste;
    }

    /********************************** */
    /**
     * 
     * @param liste
     * @param nom
     * @return sortListe
     */
    static ArrayList<Student> filterNom(ArrayList<Student> liste, String nom){
        ArrayList<Student> tmp = new ArrayList<>();
        for(int i=0;i<liste.size();i++){
            if(liste.get(i).getPrenom().equals(nom)){
                tmp.add(liste.get(i));
            }
        }
        return tmp;
    }

    /**
     * 
     * @param liste
     * @param note
     * @return sortListe
     */
    static ArrayList<Tutor> filterNote(ArrayList<Tutor> liste, float note){
        ArrayList<Tutor> tmp = new ArrayList<>();
        for(int i=0;i<liste.size();i++){
            if(liste.get(i).getNote() == note){
                tmp.add(liste.get(i));
            }
        }
        return tmp;
    }

    /**
     * 
     * @param liste
     * @param year
     * @return sortListe
     */
    static ArrayList<Tutor> filterYear(ArrayList<Tutor> liste, int year){
        ArrayList<Tutor> tmp = new ArrayList<>();
        for(int i=0;i<liste.size();i++){
            if(liste.get(i).getAnnee() == year){
                tmp.add(liste.get(i));
            }
        }
        return tmp;
    }

    /**
     * 
     * @param liste
     * @param nbAbscence
     * @return sortListe
     */
    static ArrayList<Tutee> filterNbAbscence(ArrayList<Tutee> liste, int nbAbscence){
        ArrayList<Tutee> tmp = new ArrayList<>();
        for(int i=0;i<liste.size();i++){
            if(liste.get(i).getNbAbscence() == nbAbscence){
                tmp.add(liste.get(i));
            }
        }
        return tmp;
    }

    /**
     * 
     * @param liste
     * @param ID
     * @return sortListe
     */
    static ArrayList<Student> filterID(ArrayList<Student> liste, int ID){
        ArrayList<Student> tmp = new ArrayList<>();
        for(int i=0;i<liste.size();i++){
            if(liste.get(i).getID() == ID){
                tmp.add(liste.get(i));
            }
        }
        return tmp;
    }
}
