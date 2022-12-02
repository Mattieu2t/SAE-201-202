import java.util.Comparator;

public class Tutor extends Student{
    private SchoolSubject matiere;
    private int annee;
    private float note;

    public Tutor(String prenom, float note, int annee, SchoolSubject matiere){
        super(prenom);
        this.matiere = matiere;
        this.annee = annee;
        this.note = note;
    }
    public Tutor(){
        super();
        this.matiere = SchoolSubject.NULL;
        this.annee = -1;
        this.note = -1;
    }

    public SchoolSubject getMatiere(){
        return matiere;
    }
    public int getAnnee() {
        return annee;
    }
    public float getNote() {
        return note;
    }

    public String toString() {
        if(ecritureCourte){
            return super.toString();
        }
        else{
            return super.toString()+", Annee:"+getAnnee()+", Matiere"+getMatiere()+", Note:"+note;
        }
    }

    public Tutor copyInfo(){
        Tutor t = new Tutor(this.getPrenom(), this.getNote(), this.getAnnee(), this.getMatiere());
        t.setID(this.getID());
        return t;
    }

    static class SortByNote implements Comparator<Tutor> {
        public int compare(Tutor a, Tutor b) {
            return ((Float)a.getNote()).compareTo(b.getNote());
        }
    }

    static class SortByYear implements Comparator<Tutor> {
        public int compare(Tutor a, Tutor b) {
            return ((Integer)a.getAnnee()).compareTo(b.getAnnee());
        }
    }
}
