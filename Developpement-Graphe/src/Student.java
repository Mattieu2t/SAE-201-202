import java.util.Comparator;

public abstract class Student{
    private int ID;
    private static int numberStudent = 0;
    private boolean toAssign;
    private String prenom;
    boolean ecritureCourte = true;

    public Student(String prenom) {
        this.prenom = prenom;
        this.ID = numberStudent;
        Student.numberStudent++;
    }
    public Student() {
        this.prenom = "None";
        this.ID = -1; 
        this.toAssign = true;
    }

    public boolean getToAssign(){
        return toAssign;
    }
    public void setToAssign(boolean toAssign){
        this.toAssign = toAssign;
    }
    public int getID(){
        return ID;
    }
    public void setID(int x){
        this.ID = x;
    }
    public String getPrenom() {
        return prenom;
    }

    public String toString() {
        if(ecritureCourte){
            return "["+prenom+"]";
        }
        else{
            return "ID:"+ID+", Prenom:"+prenom;
        }
    }

    static protected void resetNumberStudent(){
        Student.numberStudent = 0;
    }

    static class SortByName implements Comparator<Student> {
        @Override
        public int compare(Student a, Student b) {
            return a.getPrenom().compareTo(b.getPrenom());
        }
    }
    static class SortByID implements Comparator<Student> {
        @Override
        public int compare(Student a, Student b) {
            return ((Integer)a.getID()).compareTo(b.getID());
        }
    }
}