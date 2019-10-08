package Java.ArbolSincatico;

public class Constx extends Expx {
    private String s1;
    private Typex tp ;
    public Constx(String st1,Typex tp) {
        s1 = st1;
        this.tp = tp ;
    }
    public String toString(){
        return "Constx: "+s1+", "+tp;
    }
    public String getIdx() {
        return s1;
    }
    public String getType(){
        return tp.getTypex();
    }
}
