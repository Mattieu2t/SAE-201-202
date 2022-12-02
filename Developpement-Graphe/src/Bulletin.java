import java.util.HashMap;
import java.util.Scanner;

public class Bulletin {
    public HashMap<SchoolSubject, Float> noteMatieres;
    final static int MINNOTE = -1;
    final static int MAXNOTE = 20;

    public Bulletin(float[] listeNote){
        this.noteMatieres = new HashMap<>();
        for(int i=0;i<listeNote.length;i++){
            if(listeNote[i] >= 0){
                this.noteMatieres.put(SchoolSubject.values()[i], listeNote[i]);
            }
        }
    }

    public Bulletin() {
        this.noteMatieres = new HashMap<>();
    }

    public HashMap<SchoolSubject, Float> getNoteMatieres(){
        return noteMatieres;
    }

    // demande de la note a entrer et a quel emplacement l'ajouter
    // (donc la matière a laquelle la note sera mise)
    // avec sécurisation de la note entre -1 pour une note non existante et 20
    void askAddGrade() {
        System.out.println(
                "Veuillez saisir les notes les unes apres les autres,\n si les notes contiennent des virgules,merci d'utiliser des points");
        System.out.println("Entrez -1 si il n'y a pas encore de note");
        for (int i = 0; i < SchoolSubject.values().length; i++) {
            System.out.println("Note en " + SchoolSubject.valueOf("" + i).toString() + " : ");
            float value = 0;
            String input;
            boolean inputIsValid = true;
            do {
                Scanner scanFloat = new Scanner(System.in);
                input = "" + scanFloat.next();
                scanFloat.close();
                try {
                    value = Float.parseFloat(input);
                    
                } catch (NullPointerException npe) {
                    System.out.println("La note est nulle");
                    inputIsValid = false;
                } catch (NumberFormatException nfe) {
                    System.out.println("pas un float");
                    inputIsValid = false;
                }
            } while (value < MINNOTE && value > MAXNOTE && !inputIsValid);
            addNote(SchoolSubject.values()[i], value);
        }
    }

    // METTRE UNE ERROR SI CA EXISTE DEJA
    private void addNote(SchoolSubject mat, Float note){
        noteMatieres.put(mat, note);
    }

    /* Met tout les matières à -2
    void allDisable(){
        for(int i=0;i<bulletin.size();i++){
            bulletin.add()
        }
    }*/
}
