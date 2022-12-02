public enum SchoolSubject {
    MATHS("MATHS"),DEVELOPPEMENT("DEV"),IHM("IHM"),
    GRAPHES("GRAPHE"),DEVWEB("WEB"),RESEAU("RESEAU"),SYSTEME("SYSTEME"),NULL("null");
    private String name;

    private SchoolSubject(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
