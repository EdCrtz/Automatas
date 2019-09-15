package Java.ArbolSincatico;

public class Multiplicax extends Expx{
    private Idx s1;
    private Idx s2;

    public Multiplicax(Idx st1, Idx st2){
        s1 = st1;
        s2 = st2;
    }
    public String toString(){
        return "Multiplicax: "+s1+", "+s2;
    }
}