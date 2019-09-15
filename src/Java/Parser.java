package Java;

import Java.ArbolSincatico.*;
import java.util.ArrayList;
import java.util.Hashtable;

public final class Parser {
   private int tkn;
   private Token thisToken;
   private ArrayList<String> estatutos;
   public ArrayList<Declarax> declaraciones = new ArrayList<Declarax>();
   public ArrayList<Statx> statements = new ArrayList<Statx>();
   Hashtable<String,String> tabladesimolos=new Hashtable<String,String>();
   private Scanner scan;
   Programax p ;
   private void advance() {
      thisToken = scan.getToken();
      tkn = thisToken.getTipo();
   }

   private void eat(int t) {
      if(tkn == -2) // Si el token es Fin del archivo
      {
         estatutos.add("Token Esperado " + Tokens.values()[t].getCad()+ " Token Recibido <EOF>" );
         estatutos.add("Error..... Se esperaba " + Tokens.values()[t].getCad() + " no <EOF>$");
         return;
      }
      estatutos.add("Token Esperado " + Tokens.values()[t].getCad() + " Token Recibido " + Tokens.values()[t].getCad());
      if (tkn == t)
         advance();
      else
         estatutos.add("Error..... Se esperaba " + Tokens.values()[t].getCad() + " no " + Tokens.values()[tkn].getCad() + "$");
   }
   public final void program() {
      if (tkn == Tokens.CLASS.ordinal()) {
         eat(Tokens.CLASS.ordinal());
         eat(Tokens.ID.ordinal());
         varDeclaration();
         eat(Tokens.LBRACE.ordinal());
         while(tkn == Tokens.DO.ordinal() || tkn == Tokens.SIR.ordinal() || tkn == Tokens.ID.ordinal() || tkn == Tokens.LBRACE.ordinal()) {
            statement();
         }
         eat(Tokens.RBRACE.ordinal());
         p = new Programax(declaraciones,statements);
      } else {
         estatutos.add("Error$");
      }

   }

   private Statx statement() {
      Expx ex;
      if (tkn == Tokens.LBRACE.ordinal()) {
         eat(Tokens.LBRACE.ordinal());
         while(tkn == Tokens.DO.ordinal() || tkn == Tokens.SIR.ordinal() || tkn == Tokens.ID.ordinal()) {
            statement();
         }
         eat(Tokens.RBRACE.ordinal());
         return  null;
      } else if (tkn == Tokens.DO.ordinal()) {
         Statx s;
         Dox dox;
         eat(Tokens.DO.ordinal());
         s = statement();
         eat(Tokens.UNTIL.ordinal());
         eat(Tokens.LPAREN.ordinal());
         ex = expresion();
         eat(Tokens.RPARENT.ordinal());
         dox = new Dox(s,ex);
         statements.add(dox);
         return dox;
      } else if (tkn == Tokens.SIR.ordinal()) {
         Readx read;
         eat(Tokens.SIR.ordinal());
         eat(Tokens.LPAREN.ordinal());
         ex =expresion();
         eat(Tokens.RPARENT.ordinal());
         eat(Tokens.SEMI.ordinal());
         read = new Readx(ex);
         statements.add(read);
         return read;
      } else if (tkn == Tokens.ID.ordinal()) {
         Asignax as;
         Idx idx = new Idx(thisToken.getValor(),new Typex(tabladesimolos.get(thisToken.getValor())));
         eat(Tokens.ID.ordinal());
         eat(Tokens.EQ.ordinal());
         ex = expresion();
         eat(Tokens.SEMI.ordinal());
         as = new Asignax(idx,ex);
         statements.add(as);
         return as;
      }
      return null;
   }
   public final Expx expresion() {
      String tipo;
      Idx idx1;
      Idx idx2;
      if (tkn == Tokens.ID.ordinal()) {
         tipo = tabladesimolos.get(thisToken.getValor());
         idx1 = new Idx(thisToken.getValor(),new Typex(tipo));
         eat(Tokens.ID.ordinal());
         if (tkn == Tokens.MIN.ordinal()) {
            eat(Tokens.MIN.ordinal());
            tipo = tabladesimolos.get(thisToken.getValor());
            idx2 = new Idx(thisToken.getValor(),new Typex(tipo));
            eat(Tokens.ID.ordinal());
            return new Comparax(idx1,idx2);
         } else if (tkn == Tokens.PLUS.ordinal()) {
            eat(Tokens.PLUS.ordinal());
            tipo = tabladesimolos.get(thisToken.getValor());
            idx2 = new Idx(thisToken.getValor(),new Typex(tipo));
            eat(Tokens.ID.ordinal());
            return new Sumax(idx1,idx2);
         } else if (tkn == Tokens.MINUS.ordinal()) {
            eat(Tokens.MINUS.ordinal());
            tipo = tabladesimolos.get(thisToken.getValor());
            idx2 = new Idx(thisToken.getValor(),new Typex(tipo));
            eat(Tokens.ID.ordinal());
            return new Multiplicax(idx1,idx2);
         } else if (tkn == Tokens.ASTER.ordinal()) {
            eat(Tokens.ASTER.ordinal());
            tipo = tabladesimolos.get(thisToken.getValor());
            idx2 = new Idx(thisToken.getValor(),new Typex(tipo));
            eat(Tokens.ID.ordinal());
            return new Restax(idx1,idx2);
         }
      } else if (tkn == Tokens.FALSE.ordinal()) {
         eat(Tokens.FALSE.ordinal());
         return new Idx("",new Typex("Boolean"));
      } else if (tkn == Tokens.TRUE.ordinal()) {
         eat(Tokens.TRUE.ordinal());
         return new Idx("",new Typex("Boolean"));
      } else if (tkn == Tokens.NFLOA.ordinal()) {
         eat(Tokens.NFLOA.ordinal());
         return new Idx("",new Typex("Float"));
      } else if (tkn == Tokens.NINTE.ordinal()) {
         eat(Tokens.NINTE.ordinal());
         return new Idx("",new Typex("Integer"));
      } else {
         estatutos.add("Error... falta una expresion$");
      }
      return  null;
   }
   public final ArrayList<String> dameSalida() {
      return estatutos;
   }

   private final void varDeclaration() {
      if (tkn == Tokens.BOOLEAN.ordinal()) {
         eat(Tokens.BOOLEAN.ordinal());
         String var =thisToken.getValor();
         eat(Tokens.ID.ordinal());
         eat(Tokens.SEMI.ordinal());
         tabladesimolos.put(var, "Boolean");
         declaraciones.add(new Declarax(var,new Typex("Boolean")));
         varDeclaration();
      } else if (tkn == Tokens.INT.ordinal()) {
         eat(Tokens.INT.ordinal());
         String var =thisToken.getValor();
         eat(Tokens.ID.ordinal());
         eat(Tokens.SEMI.ordinal());
         tabladesimolos.put(var, "Int");
         declaraciones.add(new Declarax(var,new Typex("Int")));
         varDeclaration();
      } else if (tkn == Tokens.FLOAT.ordinal()) {
         eat(Tokens.FLOAT.ordinal());
         String var =thisToken.getValor();
         eat(Tokens.ID.ordinal());
         eat(Tokens.SEMI.ordinal());
         tabladesimolos.put(var, "Float");
         declaraciones.add(new Declarax(var,new Typex("Float")));
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
