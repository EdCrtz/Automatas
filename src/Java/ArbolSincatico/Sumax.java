package Java.ArbolSincatico;

public class Sumax extends Expx{
    private Idx s1;
    private Idx s2;

    public Sumax(Idx st1, Idx st2){
        s1 = st1;
        s2 = st2;
    }
    public String toString(){
        return "Sumax: "+s1+", "+s2;
    }
}