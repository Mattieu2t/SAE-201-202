import java.util.Comparator;

public class Tutee extends Student{
    private Bulletin bulletin;
    private int nbAbscence;

    public Tutee(String prenom, int nbAbscence, float[] listeNote){
        super(prenom);
        this.nbAbscence = nbAbscence;
        this.bulletin = new Bulletin(listeNote);
    }
    public Tutee(){
        super();
        this.nbAbscence = -1;
        this.bulletin = new Bulletin(new float[]{-1,-1,-1,-1,-1,-1,-1});
    }
    public int getNbAbscence(){
        return nbAbscence;
    }
    public Bulletin getBulletin(){
        return bulletin;
    }

    public String toString() {
        if(ecritureCourte){
            return super.toString();
        }
        else{
            return super.toString()+", nbAbscence:"+getNbAbscence()+", Nombre de matiere:"+bulletin.noteMatieres.size();
        }
    }

    static class SortByNbAbscence implements Comparator<Tutee> {
        @Override
        public int compare(Tutee a, Tutee b) {
            return ((Integer)a.getNbAbscence()).compareTo(b.getNbAbscence());
        }
    }
}
