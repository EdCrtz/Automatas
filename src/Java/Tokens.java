package Java;
public enum Tokens {
    CLASS ("class"),
    INT ("int"),
    FLOAT ("float"),
    BOOLEAN ("boolean"),
    DO ("do"),
    UNTIL ("until"),
    SIR ("system.in.readln"),
    LBRACE ("{"),
    RBRACE ("}"),
    LPAREN ("("),
    RPARENT (")"),
    PLUS ("+"),
    MINUS ("-"),
    ASTER ("*"),
    MIN ("<"),
    EQ ("="),
    ID ("Identificador"),
    NINTE ("Numero entero"),
    NFLOA ("Numero float"),
    SEMI (";"),
    FALSE ("flase"),
    TRUE ("true");
    private String cad;
    public String getCad(){
        return cad;
    }
    Tokens(String cad) {
        this.cad=cad;
    }
}