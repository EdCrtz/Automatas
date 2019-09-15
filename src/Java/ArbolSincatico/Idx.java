package Java.ArbolSincatico;

public class Idx extends Expx{
    private String s1;
    private Typex tp ;
    public Idx(String st1,Typex tp) {
        s1 = st1;
        this.tp = tp ;
    }
    public String toString(){
        return "IDx: "+s1+", "+tp;
    }
    public String getIdx() {
        return s1;
    }
    public String getType(){
        return tp.getTypex();
    }
}