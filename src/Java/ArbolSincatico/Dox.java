package Java.ArbolSincatico;

public class Dox extends Statx{
    private Expx s1;
    private Statx s2;

    public Dox( Statx st2, Expx st1) {
        s1 = st1;
        s2 = st2;
    }
    public String toString(){
        return "Dox: "+s2+", "+s1;
    }
}