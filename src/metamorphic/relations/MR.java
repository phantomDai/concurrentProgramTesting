package metamorphic.relations;

public class MR {
    private String MRID;
    private String MRname;

    public String getMRID() {
        return MRID;
    }

    public void setMRID(String MRID) {
        this.MRID = MRID;
    }

    public String getMRname() {
        return MRname;
    }

    public void setMRname(String MRname) {
        this.MRname = MRname;
    }

    public MR(String myId,String myName) {
        setMRID(myId);
        setMRname(myName);
    }

}
