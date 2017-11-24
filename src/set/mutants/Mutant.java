package set.mutants;

public class Mutant {
    private String ID ;
    private String CLASSNAME ;

    public String getID() { return this.ID; }

    public void setID(String ID) { this.ID = ID; }

    public String getCLASSNAME() {
        return this.CLASSNAME;
    }

    public void setCLASSNAME(String CLASSNAME) {
        this.CLASSNAME = CLASSNAME;
    }

    public Mutant(String id,String cn) {
        setID(id);
        setCLASSNAME(cn);
    }

}
