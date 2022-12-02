public abstract class Scenario {
    // Total 1min 36sec
    static void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void sleep(int sec){
        try {
            Thread.sleep(sec*1000);
        } catch (InterruptedException e) {
        }
    }

    public static void lancement(){
        clearScreen();
        System.out.println("MENU\n1 - Liste des affectations\n2 - Gestion fichier\n3 - Quitter");
        sleep(2);
        System.out.println(2);
        sleep(3);

        clearScreen();
        System.out.println("GESTIONFICHIER\n1 - Menu\n2 - Charger une liste étudiants\n3 - Télécharger la liste des affectations");
        sleep(3);
        System.out.println(2);
        sleep(2);
        clearScreen();
        System.out.println("Coller le chemin absolu du fichier :");
        sleep(2);
        System.out.println(".../LoginProf/Bureau/MaListeEtudiants.csv");
        sleep(3);

        clearScreen();
        System.out.println("Importation\nGESTIONFICHIER\n1 - Menu\n2 - Charger une liste étudiants\n3 - Télécharger la liste des affectations");
        sleep(2);
        System.out.println(1);
        sleep(2);

        clearScreen();
        System.out.println("MENU\n1 - Liste des affectations\n2 - Gestion fichier\n3 - Quitter");
        sleep(2);
        System.out.println(1);
        sleep(3);

        clearScreen();
        System.out.println("LISTEAFFECTATIONS\n1 - Menu\n2 - Afficher liste étudiants\n3 - Modifier règlages calcul\n4 - Calculer les affectations\n5 - Afficher la liste des affectations\n6 - Bloquer/Debloquer une affectation\n7 - Forcer/Deforcer une affectation");
        sleep(3);
        System.out.println(2);
        sleep(3);

        clearScreen();
        System.out.println("++++TUTEURS++++\n[George]\n[Pauline]\n++++TUTORES++++\n[Clement]\n[Michele]\n[Aurore]\n[Max]");
        System.out.println("LISTEAFFECTATIONS\n1 - Menu\n2 - Afficher liste étudiants\n3 - Modifier règlages calcul\n4 - Calculer les affectations\n5 - Afficher la liste des affectations\n6 - Bloquer/Debloquer une affectation\n7 - Forcer/Deforcer une affectation");
        sleep(4);
        System.out.println(3);
        sleep(2);

        clearScreen();
        String message = "REGLAGESAFFECTATIONS\n1 - Retour\n";
            message += "2 - Note Tuteur = "+Tutorat.getPourcentNoteTuteur()+"%\n";
            message += "3 - Note Tutore = "+Tutorat.getPourcentNoteTutore()+"%\n";
            message += "4 - Ajout Priorité Tuteur de 3eme annee = +"+Tutorat.getPourcentAjoutPriorite3emeAnnee()+"%\n";
            message += "5 - Retrait Priorité Abscence Tutore (par 4 abscences) = -"+Tutorat.getPourcentRetraitPrioriteAbscence()+"%\n";
            message += "6 - Retour des réglages par Defaut";
        System.out.println(message);
        sleep(4);
        System.out.println(2);
        sleep(3);

        clearScreen();
        System.out.println("Note tuteur (30%->70%) :");
        sleep(2);
        System.out.println(40);
        sleep(2);

        clearScreen();
        System.out.println(message);
        sleep(2);
        System.out.println(1);
        sleep(2);

        clearScreen();
        System.out.println("LISTEAFFECTATIONS\n1 - Menu\n2 - Afficher liste étudiants\n3 - Modifier règlages calcul\n4 - Calculer les affectations\n5 - Afficher la liste des affectations\n6 - Bloquer/Debloquer une affectation\n7 - Forcer/Deforcer une affectation");
        sleep(2);
        System.out.println(4);
        sleep(3);

        clearScreen();
        System.out.println("Calcul effectuer");
        System.out.println("LISTEAFFECTATIONS\n1 - Menu\n2 - Afficher liste étudiants\n3 - Modifier règlages calcul\n4 - Calculer les affectations\n5 - Afficher la liste des affectations\n6 - Bloquer/Debloquer une affectation\n7 - Forcer/Deforcer une affectation");
        sleep(2);
        System.out.println(5);
        sleep(2);

        clearScreen();
        System.out.println("++++AFFECTATIONS++++\nArete([Pauline], [Clement])\nArete([George], [Michele])\nArete([Pauline], [Aurore])");
        System.out.println("LISTEAFFECTATIONS\n1 - Menu\n2 - Afficher liste étudiants\n3 - Modifier règlages calcul\n4 - Calculer les affectations\n5 - Afficher la liste des affectations\n6 - Bloquer/Debloquer une affectation\n7 - Forcer/Deforcer une affectation");
        sleep(3);
        System.out.println(1);
        sleep(3);

        clearScreen();
        System.out.println("MENU\n1 - Liste des affectations\n2 - Gestion fichier\n3 - Quitter");
        sleep(2);
        System.out.println(2);
        sleep(2);

        clearScreen();
        System.out.println("GESTIONFICHIER\n1 - Menu\n2 - Charger une liste étudiants\n3 - Télécharger la liste des affectations");
        sleep(2);
        System.out.println(3);
        sleep(2);
        clearScreen();
        System.out.println("Coller le chemin absolu du dossier :");
        sleep(2);
        System.out.println(".../LoginProf/Bureau");
        sleep(3);

        clearScreen();
        System.out.println("Ecriture reussie");
        System.out.println("GESTIONFICHIER\n1 - Menu\n2 - Charger une liste étudiants\n3 - Télécharger la liste des affectations");
        sleep(2);
        System.out.println(1);
        sleep(2);

        clearScreen();
        System.out.println("MENU\n1 - Liste des affectations\n2 - Gestion fichier\n3 - Quitter");
        sleep(2);
        System.out.println(3);
        sleep(2);

        clearScreen();
        System.out.println("SAUVEGARDER ETUDIANTS ? (oui/non) (O/N) :");
        sleep(3);
        System.out.println("o");
        sleep(2);
        
        clearScreen();
        System.out.println("FIN DU SCENARIO : MERCI");
        sleep(3);
    }   
}
