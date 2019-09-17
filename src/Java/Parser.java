package Java;

import Java.ArbolSincatico.*;

import java.util.ArrayList;
import java.util.Hashtable;

public final class Parser {
    private Tokens tkn;
    private Token thisToken;
    private ArrayList<String> estatutos;
    public ArrayList<Declarax> declaraciones = new ArrayList<Declarax>();
    public ArrayList<Statx> statements = new ArrayList<Statx>();
    Hashtable<String, String> tabladesimolos = new Hashtable<String, String>();
    private Scanner scan;
    Programax p;
    private void advance() {
        thisToken = scan.getToken();
        tkn = thisToken.getTipo();
    }

    private void eat(Tokens t) {
        if (thisToken.getClasificacion() == -2) // Si el token es Fin del archivo
        {
            estatutos.add("Token Esperado " + t.getCad() + " Token Recibido <EOF>");
            estatutos.add("Error..... Se esperaba " + t.getCad() + " no <EOF>$");
            return;
        }
        estatutos.add("Token Esperado " + t.getCad() + " Token Recibido " + tkn.getCad()+" en la linea "+thisToken.getLinea());
        if (tkn == t)
            advance();
        else
            estatutos.add("Error..... Se esperaba " + t.getCad() + " no " + tkn.getCad() +" en la linea "+thisToken.getLinea()+ "$");
    }

    public final void program() {
        if (tkn == Tokens.CLASS) {
            eat(Tokens.CLASS);
            eat(Tokens.ID);
            varDeclaration();
            eat(Tokens.LBRACE);
            while (tkn == Tokens.DO || tkn == Tokens.SIR || tkn == Tokens.ID || tkn == Tokens.LBRACE) {
                statement();
            }
            eat(Tokens.RBRACE);
            p = new Programax(declaraciones, statements);
        } else {
            estatutos.add("Error$");
        }

    }

    private Statx statement() {
        Expx ex;
        if (tkn == Tokens.LBRACE) {
            eat(Tokens.LBRACE);
            while (tkn == Tokens.DO || tkn == Tokens.SIR || tkn == Tokens.ID) {
                statement();
            }
            eat(Tokens.RBRACE);
            return null;
        } else if (tkn == Tokens.DO) {
            Statx s ;
            Dox dox;
            eat(Tokens.DO);
            do {
                s = statement();
            }while (tkn == Tokens.DO || tkn == Tokens.SIR || tkn == Tokens.ID || tkn == Tokens.LBRACE);
            eat(Tokens.UNTIL);
            eat(Tokens.LPAREN);
            ex = expresion();
            eat(Tokens.RPARENT);
            dox = new Dox(s, ex);
            statements.add(dox);
            return dox;
        } else if (tkn == Tokens.SIR) {
            Readx read;
            eat(Tokens.SIR);
            eat(Tokens.LPAREN);
            ex = expresion();
            eat(Tokens.RPARENT);
            eat(Tokens.SEMI);
            read = new Readx(ex);
            statements.add(read);
           return read;
        } else if (tkn == Tokens.ID) {
            Asignax as;
            Idx idx = new Idx(thisToken.getValor(), new Typex(tabladesimolos.get(thisToken.getValor())));
            eat(Tokens.ID);
            eat(Tokens.EQ);
            ex = expresion();
            eat(Tokens.SEMI);
            as = new Asignax(idx, ex);
            statements.add(as);
            return as;
        }
        return null;
    }

    public final Expx expresion() {
        String tipo;
        Idx idx1;
        Idx idx2;
        if (tkn == Tokens.ID) {
            tipo = tabladesimolos.get(thisToken.getValor());
            idx1 = new Idx(thisToken.getValor(), new Typex(tipo));
            eat(Tokens.ID);
            if (tkn == Tokens.MIN) {
                eat(Tokens.MIN);
                tipo = tabladesimolos.get(thisToken.getValor());
                idx2 = new Idx(thisToken.getValor(), new Typex(tipo));
                eat(Tokens.ID);
                return new Comparax(idx1, idx2);
            } else if (tkn == Tokens.PLUS) {
                eat(Tokens.PLUS);
                tipo = tabladesimolos.get(thisToken.getValor());
                idx2 = new Idx(thisToken.getValor(), new Typex(tipo));
                eat(Tokens.ID);
                return new Sumax(idx1, idx2);
            } else if (tkn == Tokens.MINUS) {
                eat(Tokens.MINUS);
                tipo = tabladesimolos.get(thisToken.getValor());
                idx2 = new Idx(thisToken.getValor(), new Typex(tipo));
                eat(Tokens.ID);
                return new Multiplicax(idx1, idx2);
            } else if (tkn == Tokens.ASTER) {
                eat(Tokens.ASTER);
                tipo = tabladesimolos.get(thisToken.getValor());
                idx2 = new Idx(thisToken.getValor(), new Typex(tipo));
                eat(Tokens.ID);
                return new Restax(idx1, idx2);
            }
        } else if (tkn == Tokens.FALSE) {
            eat(Tokens.FALSE);
            return new Idx("", new Typex("Boolean"));
        } else if (tkn == Tokens.TRUE) {
            eat(Tokens.TRUE);
            return new Idx("", new Typex("Boolean"));
        } else if (tkn == Tokens.NFLOA) {
            eat(Tokens.NFLOA);
            return new Idx("", new Typex("Float"));
        } else if (tkn == Tokens.NINTE) {
            eat(Tokens.NINTE);
            return new Idx("", new Typex("Integer"));
        } else {
            estatutos.add("Error... falta una expresion en la linea "+thisToken.getLinea()+"$");
        }
        return null;
    }

    public final ArrayList<String> dameSalida() {
        return estatutos;
    }

    private final void varDeclaration() {
        if (tkn == Tokens.BOOLEAN) {
            eat(Tokens.BOOLEAN);
            String var = thisToken.getValor();
            eat(Tokens.ID);
            eat(Tokens.SEMI);
            tabladesimolos.put(var, "Boolean");
            declaraciones.add(new Declarax(var, new Typex("Boolean")));
            varDeclaration();
        } else if (tkn == Tokens.INT) {
            eat(Tokens.INT);
            String var = thisToken.getValor();
            eat(Tokens.ID);
            eat(Tokens.SEMI);
            tabladesimolos.put(var, "Int");
            declaraciones.add(new Declarax(var, new Typex("Int")));
            varDeclaration();
        } else if (tkn == Tokens.FLOAT) {
            eat(Tokens.FLOAT);
            String var = thisToken.getValor();
            eat(Tokens.ID);
            eat(Tokens.SEMI);
            tabladesimolos.put(var, "Float");
            declaraciones.add(new Declarax(var, new Typex("Float")));
            varDeclaration();
        }

    }

    public Parser(Scanner scan) {
        this.scan = scan;
        estatutos = new ArrayList<String>();
        thisToken = scan.getToken();
        tkn = thisToken.getTipo();
        estatutos.add("Iniciando el parsing");
    }
}
