
import javax.swing.JOptionPane

class Parser(private var scan:Scanner){
    private var tkn: Int =0
    private var estatutos = ArrayList<String>()
    init {
        tkn = scan.getToken().tipo
        estatutos.add("Iniciando el parsing")
    }
    fun advance(){tkn = scan.getToken().tipo }
    fun eat(t:Int){
        if(tkn == -2)
            return
        estatutos.add("Token Esperado " + Tokens.values()[t].name + " Token Recibido " +  Tokens.values()[tkn].name)
         if(tkn == t) {
             advance();
             return
         }
        JOptionPane.showMessageDialog(null,"Error..... Se esperaba " + Tokens.values()[t].name+ " no " + Tokens.values()[tkn].name);
            estatutos.add("Error..... Se esperaba " + Tokens.values()[t].name+ " no " + Tokens.values()[tkn].name+"$");
    }
    fun program(){
        when(tkn){
            Tokens.CLASS.ordinal->{
                eat(Tokens.CLASS.ordinal)
                eat(Tokens.ID.ordinal)
                varDeclaration()
                eat(Tokens.LBRACE.ordinal)
                while(tkn == Tokens.DO.ordinal|| tkn == Tokens.SIR.ordinal ||tkn == Tokens.ID.ordinal ||tkn == Tokens.LBRACE.ordinal)
                    statement()
                eat(Tokens.RBRACE.ordinal)
            }
            else-> estatutos.add("Error")
        }
    }
    fun statement(){
        when(tkn) {
            Tokens.LBRACE.ordinal->{
                eat(Tokens.LBRACE.ordinal)
                while(tkn == Tokens.DO.ordinal|| tkn == Tokens.SIR.ordinal ||tkn == Tokens.ID.ordinal)
                    statement()
                eat(Tokens.RBRACE.ordinal)
            }
            Tokens.DO.ordinal-> {
                eat(Tokens.DO.ordinal)
                statement()
                eat(Tokens.UNTIL.ordinal)
                eat(Tokens.LPAREN.ordinal)
                expresion()
                eat(Tokens.RPARENT.ordinal)
            }
            Tokens.SIR.ordinal->{
                eat(Tokens.SIR.ordinal)
                eat(Tokens.LPAREN.ordinal)
                expresion()
                eat(Tokens.RPARENT.ordinal)
                eat(Tokens.SEMI.ordinal)
            }
            Tokens.ID.ordinal->{
                eat(Tokens.ID.ordinal)
                eat(Tokens.EQ.ordinal)
                expresion()
                eat(Tokens.SEMI.ordinal)
            }
        }
    }
    fun expresion(){
        when(tkn){
            Tokens.ID.ordinal->{
                eat(Tokens.ID.ordinal)
                when(tkn){
                    Tokens.MIN.ordinal->{
                        eat(Tokens.MIN.ordinal)
                        eat(Tokens.ID.ordinal)
                    }
                    Tokens.PLUS.ordinal->{
                        eat(Tokens.PLUS.ordinal)
                        eat(Tokens.ID.ordinal)
                    }
                    Tokens.MINUS.ordinal->{
                        eat(Tokens.MINUS.ordinal)
                        eat(Tokens.ID.ordinal)
                    }
                    Tokens.ASTER.ordinal->{
                        eat(Tokens.ASTER.ordinal)
                        eat(Tokens.ID.ordinal)
                    }
                }
            }
            Tokens.FALSE.ordinal-> eat(Tokens.FALSE.ordinal)
            Tokens.TRUE.ordinal-> eat(Tokens.TRUE.ordinal)
            Tokens.NFLOA.ordinal-> eat(Tokens.NFLOA.ordinal)
            Tokens.NINTE.ordinal-> eat(Tokens.NINTE.ordinal)
            else -> estatutos.add("Error... falta una expresion$")
        }
    }
    fun dameSalida() = estatutos
    fun varDeclaration(){
        when(tkn){
            Tokens.BOOLEAN.ordinal->{
                eat(Tokens.BOOLEAN.ordinal)
                eat(Tokens.ID.ordinal)
                eat(Tokens.SEMI.ordinal)
                varDeclaration()
            }
            Tokens.INT.ordinal->{
                eat(Tokens.INT.ordinal)
                eat(Tokens.ID.ordinal)
                eat(Tokens.SEMI.ordinal)
                varDeclaration()
            }
            Tokens.FLOAT.ordinal->{
                eat(Tokens.FLOAT.ordinal)
                eat(Tokens.ID.ordinal)
                eat(Tokens.SEMI.ordinal)
                varDeclaration()
            }
        }

    }


}