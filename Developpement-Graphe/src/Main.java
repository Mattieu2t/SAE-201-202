import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Exeptions.LineNotValidExeption;
import Exeptions.OutOfRangeException;
import fr.ulille.but.sae2_02.graphes.Arete;
import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;

// PONDERATION DU CODE
public abstract class Main{
    static ArrayList<Tutor> tuteurs = new ArrayList<>();
    static ArrayList<Tutee> tutores = new ArrayList<>();
    static GrapheNonOrienteValue<Student> graphe;
    static CalculAffectation<Student> calcul;
    static GrapheNonOrienteValue<Student> coupleBloque = new GrapheNonOrienteValue<>();
    static GrapheNonOrienteValue<Student> coupleForce = new GrapheNonOrienteValue<>();
    static Scanner sc;

    /***************************************************************************/
    /*************************** OUTILS ****************************************/
    /***************************************************************************/

    static int demandeInt(String message) {
        sc = new Scanner(System.in);
        int choix = 0;
        try {
            System.out.println(message);
            choix = sc.nextInt();
        } catch (Exception e) {
            clearScreen();

            System.out.println("Entrée non valide !");
            choix = demandeInt(message);
        }
        clearScreen();

        return choix;
    }

    static String demandeString(String message) {
        sc = new Scanner(System.in);
        String choix = "";
        try {
            System.out.println(message);
            choix = sc.nextLine();
        } catch (Exception e) {
            clearScreen();

            System.out.println("Entrée non valide !");
            demandeInt(message);
        }
        clearScreen();
        return choix;
    }

    static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /***************************************************************************/
    /************************ DECOMPOSITION MAIN *******************************/
    /***************************************************************************/

    @SuppressWarnings("unchecked")
    static void menu() {
        clearScreen();
        // Chargement sauvegarde
        String chemin = System.getProperty("user.dir") + File.separator + "Developpement-Graphe" + File.separator
                + "res";
        if (new File(chemin + File.separator + "saveTutor.json").exists()) {
            Object[] tmp = Tutorat.deserialise(chemin);
            tuteurs = (ArrayList<Tutor>) tmp[0];
            tutores = (ArrayList<Tutee>) tmp[1];
            coupleBloque = (GrapheNonOrienteValue<Student>) tmp[2];
            coupleForce = (GrapheNonOrienteValue<Student>) tmp[3];
            System.out.println("Chargement sauvegarde");
        }

        boolean run = true;
        while (run) {
            int choix = demandeInt("MENU\n1 - Liste des affectations\n2 - Gestion fichier\n3 - Quitter");
            switch (choix) {
                case 1:
                    listeAffectations();
                    break;
                case 2:
                    gestionFichier();    
                    break;
                case 3:
                    System.out.println("Sortie du logiciel");
                    demandeSauvegarde();
                    run = false;
                    break;
                default:
                    System.out.println("Entrée non valide !");     
                    break;
            }
        }
    }

    static void demandeSauvegarde() {
        clearScreen();

        boolean run = true;
        while (run) {
            String choix = demandeString("SAUVEGARDER ETUDIANTS ? (oui/non) (O/N) :");
            if (choix.toUpperCase().equals("O") || choix.toUpperCase().equals("OUI")) {
                // Sauvegarde
                String chemin = System.getProperty("user.dir") + File.separator + "Developpement-Graphe"
                        + File.separator + "res";
                Tutorat.serialise(chemin, tuteurs, tutores, coupleBloque, coupleForce);
                run = false;
                sc.close();
            } else if (choix.toUpperCase().equals("N") || choix.toUpperCase().equals("NON")) {
                run = false;
                sc.close();
            } else {
                System.out.println("Entrée non valide !");
            }
        }
    }

    static void gestionFichier() {
        clearScreen();

        boolean run = true;
        while (run) {
            int choix = demandeInt(
                    "GESTIONFICHIER\n1 - Menu\n2 - Charger une liste étudiants\n3 - Télécharger la liste des affectations");
            String chemin = "";
            switch (choix) {
                case 1:
                    run = false;
                    break;
                case 2:
                    System.out.println("Exemple : ~\\D-G2\\Developpement-Graphe\\res\\listeEtudiantTest.csv");
                    chemin = demandeString("Coller le chemin absolu du fichier :");
                    try {
                        Tutorat.importeEtudiants(chemin, tuteurs, tutores);
                    } catch (LineNotValidExeption e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    if (calcul != null) {
                        System.out.println("Exemple : ~\\Bureau");
                        chemin = demandeString("Coller le chemin absolu du dossier :");
                        try {
                            Tutorat.exporteAffectations(chemin, calcul, coupleBloque, coupleForce);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("/!\\ Calcul des affectations non effectuer /!\\");
                    }
                    break;            
                default:
                System.out.println("Entrée non valide !");
                    break;
            }
        }
    }

    static void listeAffectations() {
        clearScreen();

        boolean run = true;
        while (run) {
            int choix = demandeInt(
                    "LISTEAFFECTATIONS\n1 - Menu\n2 - Afficher liste étudiants\n3 - Modifier règlages calcul\n4 - Calculer les affectations\n5 - Afficher la liste des affectations\n6 - Bloquer/Debloquer une affectation\n7 - Forcer/Deforcer une affectation");
            switch (choix) {
                case 1:
                    run = false;
                    break;
                case 2:
                    System.out.println("++++TUTEURS++++");
                    if (tuteurs.size() == 0) {
                        System.out.println("VIDE");
                    }
                    for (Tutor tutor : tuteurs) {
                        System.out.println(tutor);
                    }
                    System.out.println("++++TUTORES++++");
                    if (tutores.size() == 0) {
                        System.out.println("VIDE");
                    }
                    for (Tutee tutee : tutores) {
                        System.out.println(tutee);
                    }
                    break;
                case 3:
                    reglagesCalcul();
                    break;
                case 4:
                    ArrayList<Tutor> tmpTuteurs = new ArrayList<>();
                    ArrayList<Tutee> tmpTutores = new ArrayList<>();
                    tmpTuteurs.addAll(tuteurs);
                    tmpTutores.addAll(tutores);
                    // Ajout des tuteurs des listes forcés
                    for (Arete<Student> arete : coupleForce.aretes()) {
                        tmpTuteurs.add(((Tutor) arete.getExtremite1()).copyInfo());
                    }
                    if (tuteurs.size() > tutores.size()) {
                        Tutorat.combleExesTuteurs(tmpTutores, tuteurs.size());
                    } else if (tuteurs.size() < tutores.size()) {
                        Tutorat.combleExesTutores(tmpTuteurs, tutores.size());
                    }

                    graphe = Tutorat.creeGraphe(tmpTuteurs, tmpTutores, coupleBloque, coupleForce);
                    calcul = new CalculAffectation<>(graphe, Tutorat.castToListeStudent(tmpTuteurs),
                            Tutorat.castToListeStudent(tmpTutores));
                    System.out.println("Calcul effectuer");
                    break;
                case 5:
                    if (calcul != null) {
                        System.out.println("++++AFFECTATIONS++++");
                        if (calcul.getAffectation().size() == 0) {
                            System.out.println("VIDE");
                        }
                        for (Arete<Student> arete : calcul.getAffectation()) {
                            // Tout les valides
                            // J'enleve les couples bloquer, les couples bidons (null) et les couples de
                            // mauvaise matière
                            if (((Tutor) arete.getExtremite1()).getMatiere() != SchoolSubject.NULL &&
                                    (coupleForce.contientArete(arete.getExtremite1(), arete.getExtremite2()) ||
                                            ((Tutee) arete.getExtremite2()).getBulletin().getNoteMatieres()
                                                    .containsKey(((Tutor) arete.getExtremite1()).getMatiere()))) {
                                if (!coupleBloque.contientArete(arete.getExtremite1(), arete.getExtremite2())) {
                                    System.out.println(arete);
                                }
                            }
                        }
                    } else {
                        System.out.println("/!\\ Calcul des affectations non effectuer /!\\");
                    }
                    break;
                case 6:   
                    choixAffectation(coupleBloque);
                    break;
                case 7:   
                choixAffectation(coupleForce);
                    break;                 
                default:
                    System.out.println("Entrée non valide");
                    break;
            }
        }
    }

    static void choixAffectation(GrapheNonOrienteValue<Student> grapheChoix) {
        clearScreen();

        Tutor selectTutor = new Tutor();
        Tutee selectTutee = new Tutee();
        boolean run = true;
        while (run) {
            String message = "CHOIX AFFECTATION\n";
            message += "Tuteur selectionné = " + selectTutor.getPrenom() + "\n";
            message += "Tutore selectionné = " + selectTutee.getPrenom() + "\n";
            message += "1 - Retour\n2 - Afficher la liste des tuteurs\n    et choisir le tuteur\n3 - Afficher la liste des tutores\n    et choisir le tutore\n";
            message += "4 - Valider";
            int choix = demandeInt(message);
            switch (choix) {
                case 1:
                    run = false;
                    break;
                case 2:
                    message = "++++TUTEURS++++\n";
                    if (tuteurs.size() == 0) {
                        message += "VIDE\n";
                    }
                    for (int i = 0; i < tuteurs.size(); i++) {
                        message += "" + i + tuteurs.get(i) + "\n";
                    }
                    message += "Numéro tuteur : ";
                    choix = demandeInt(message);
                    if (choix < 0 || choix > tuteurs.size() - 1) {
                        System.out.println("Entrée non valide !");
                    } else {
                        selectTutor = tuteurs.get(choix);
                    }
                    break;
                case 3:
                    message = "++++TUTORES++++\n";
                    if (tutores.size() == 0) {
                        message += "VIDE\n";
                    }
                    for (int i = 0; i < tutores.size(); i++) {
                        message += "" + i + tutores.get(i) + "\n";
                    }
                    message += "Numéro tutore : ";
                    choix = demandeInt(message);
                    if (choix < 0 || choix > tutores.size() - 1) {
                        System.out.println("Entrée non valide !");
                    } else {
                        selectTutee = tutores.get(choix);
                    }
                break;
                case 4:
                    if (selectTutee.getID() == -1 || selectTutor.getID() == -1) {
                        System.out.println("L'affectation n'est pas valide !");
                    } else {
                        if (coupleBloque.contientArete(selectTutor, selectTutee)) {
                            coupleBloque = Tutorat.removeArete(coupleBloque, selectTutor, selectTutee);
                        }
                        if (coupleForce.contientArete(selectTutor, selectTutee)) {
                            coupleForce = Tutorat.removeArete(coupleForce, selectTutor, selectTutee);
                        }
                        if (!grapheChoix.contientSommet(selectTutor)) {
                            grapheChoix.ajouterSommet(selectTutor);
                        }
                        if (!grapheChoix.contientSommet(selectTutee)) {
                            grapheChoix.ajouterSommet(selectTutee);
                        }
                        if (grapheChoix.contientArete(selectTutor, selectTutee)) {
                            grapheChoix = Tutorat.removeArete(grapheChoix, selectTutor, selectTutee);
                        }
                        grapheChoix.ajouterArete((Student) selectTutor, (Student) selectTutee, 0);
                        run = false;
                    }
                    break;
                default:
                System.out.println("Entrée non valide !");
                    break;
            }
        }
    }

    static void reglagesCalcul(){
        clearScreen();
        
        boolean run = true;
        while(run){
            String message = "REGLAGESAFFECTATIONS\n1 - Retour\n";
            message += "2 - Note Tuteur = "+Tutorat.getPourcentNoteTuteur()+"%\n";
            message += "3 - Note Tutore = "+Tutorat.getPourcentNoteTutore()+"%\n";
            message += "4 - Ajout Priorité Tuteur de 3eme annee = +"+Tutorat.getPourcentAjoutPriorite3emeAnnee()+"%\n";
            message += "5 - Retrait Priorité Abscence Tutore (par 4 abscences) = -"+Tutorat.getPourcentRetraitPrioriteAbscence()+"%\n";
            message += "6 - Retour des réglages par Defaut";
            int choix = demandeInt(message);
            int choixPourcentage;
            switch (choix) {
                case 1:
                    run = false;
                    break;
                case 2:
                    choixPourcentage = demandeInt("Note tuteur (30%->70%) :");
                    try {
                        Tutorat.setPourcentNoteTuteur(choixPourcentage);
                    } catch (OutOfRangeException e) {
                        System.out.println("/!\\ Choix hors champ 30%->70% /!\\");
                    }
                    break;
                case 3:
                    choixPourcentage = demandeInt("Note tutore (30%->70%) :");
                    try {
                        Tutorat.setPourcentNoteTutore(choixPourcentage);
                    } catch (OutOfRangeException e) {
                    System.out.println("/!\\ Choix hors champ 30%->70% /!\\");
                    }
                    break;
                case 4:
                    choixPourcentage = demandeInt("Ajout Priorité Tuteur de 3eme annee (0%->20%) :");
                    try {
                        Tutorat.setPourcentAjoutPriorite3emeAnnee(choixPourcentage);
                    } catch (OutOfRangeException e) {
                        System.out.println("/!\\ Choix hors champ 0%->20% /!\\");
                    }
                    break;
                case 5:
                    choixPourcentage = demandeInt("Retrait Priorité Abscence Tutore (0%->20%) :");
                    try {
                        Tutorat.setPourcentRetraitPrioriteAbscence(choixPourcentage);
                    } catch (OutOfRangeException e) {
                        System.out.println("/!\\ Choix hors champ 0%->10% /!\\");
                    }
                    break;
                case 6:
                    try {
                        Tutorat.setPourcentNoteTuteur(50);
                        Tutorat.setPourcentNoteTutore(50);
                        Tutorat.setPourcentAjoutPriorite3emeAnnee(5);
                        Tutorat.setPourcentRetraitPrioriteAbscence(5);
                    } catch (OutOfRangeException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Reglage par defaut");
                    break;        
                
                    default:
                        System.out.println("Entrée non valide");
                        break;
            }

        }
    }

    /********************************MAIN**************************************/
    public static void main(String[] args){
        clearScreen();
        boolean run = true;
        while(run){
            int choix = demandeInt("CHOIX :\n1 - Logiciel (terminal)\n2 - Scénario autonome 1min 36sec (Terminal)");
            switch(choix){
                case 1:
                    menu();
                    run = false;
                    break;
                case 2:
                    Scenario.lancement();
                    run = false;
                    break;
                default:
                    System.out.println("Entrée non valide !");
            }
        }
    }
}
