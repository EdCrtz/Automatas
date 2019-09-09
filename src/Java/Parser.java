package Java;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public final class Parser {
   private int tkn;
   private ArrayList<String> estatutos;
   private Scanner scan;

   private void advance() {
      tkn = scan.getToken().getTipo();
   }

   private void eat(int t) {
      if(tkn == -2) // Si el token es Fin del archivo
      {
         estatutos.add("Token Esperado " + Tokens.values()[t].getCad()+ " Token Recibido <EOF>" );
         JOptionPane.showMessageDialog((Component)null, "Error..... Se esperaba " + Tokens.values()[t].getCad()+ " no <EOF>");
         estatutos.add("Error..... Se esperaba " + Tokens.values()[t].name() + " no <EOF>$");
         return;
      }
      estatutos.add("Token Esperado " + Tokens.values()[t].getCad() + " Token Recibido " + Tokens.values()[t].getCad());
      if (tkn == t) {
         advance();
      } else {
         JOptionPane.showMessageDialog((Component)null, "Error..... Se esperaba " + Tokens.values()[t].getCad() + " no " + Tokens.values()[tkn].getCad());
         estatutos.add("Error..... Se esperaba " + Tokens.values()[t].getCad() + " no " + Tokens.values()[tkn].getCad() + "$");
      }
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
      } else {
         estatutos.add("Error");
      }

   }

   private void statement() {
      if (tkn == Tokens.LBRACE.ordinal()) {
         eat(Tokens.LBRACE.ordinal());
         while(tkn == Tokens.DO.ordinal() || tkn == Tokens.SIR.ordinal() || tkn == Tokens.ID.ordinal()) {
            statement();
         }
         eat(Tokens.RBRACE.ordinal());
      } else if (tkn == Tokens.DO.ordinal()) {
         eat(Tokens.DO.ordinal());
         statement();
         eat(Tokens.UNTIL.ordinal());
         eat(Tokens.LPAREN.ordinal());
         expresion();
         eat(Tokens.RPARENT.ordinal());
      } else if (tkn == Tokens.SIR.ordinal()) {
         eat(Tokens.SIR.ordinal());
         eat(Tokens.LPAREN.ordinal());
         expresion();
         eat(Tokens.RPARENT.ordinal());
         eat(Tokens.SEMI.ordinal());
      } else if (tkn == Tokens.ID.ordinal()) {
         eat(Tokens.ID.ordinal());
         eat(Tokens.EQ.ordinal());
         expresion();
         eat(Tokens.SEMI.ordinal());
      }

   }

   public final void expresion() {
      if (tkn == Tokens.ID.ordinal()) {
         eat(Tokens.ID.ordinal());
         if (tkn == Tokens.MIN.ordinal()) {
            eat(Tokens.MIN.ordinal());
            eat(Tokens.ID.ordinal());
         } else if (tkn == Tokens.PLUS.ordinal()) {
            eat(Tokens.PLUS.ordinal());
            eat(Tokens.ID.ordinal());
         } else if (tkn == Tokens.MINUS.ordinal()) {
            eat(Tokens.MINUS.ordinal());
            eat(Tokens.ID.ordinal());
         } else if (tkn == Tokens.ASTER.ordinal()) {
            eat(Tokens.ASTER.ordinal());
            eat(Tokens.ID.ordinal());
         }
      } else if (tkn == Tokens.FALSE.ordinal()) {
         eat(Tokens.FALSE.ordinal());
      } else if (tkn == Tokens.TRUE.ordinal()) {
         eat(Tokens.TRUE.ordinal());
      } else if (tkn == Tokens.NFLOA.ordinal()) {
         eat(Tokens.NFLOA.ordinal());
      } else if (tkn == Tokens.NINTE.ordinal()) {
         eat(Tokens.NINTE.ordinal());
      } else {
         estatutos.add("Error... falta una expresion$");
      }

   }
   public final ArrayList<String> dameSalida() {
      return estatutos;
   }

   private final void varDeclaration() {
      if (tkn == Tokens.BOOLEAN.ordinal()) {
         eat(Tokens.BOOLEAN.ordinal());
         eat(Tokens.ID.ordinal());
         eat(Tokens.SEMI.ordinal());
         varDeclaration();
      } else if (tkn == Tokens.INT.ordinal()) {
         eat(Tokens.INT.ordinal());
         eat(Tokens.ID.ordinal());
         eat(Tokens.SEMI.ordinal());
         varDeclaration();
      } else if (tkn == Tokens.FLOAT.ordinal()) {
         eat(Tokens.FLOAT.ordinal());
         eat(Tokens.ID.ordinal());
         eat(Tokens.SEMI.ordinal());
         varDeclaration();
      }

   }

   public Parser(Scanner scan) {
      this.scan = scan;
      estatutos = new ArrayList<String>();
      tkn = scan.getToken().getTipo();
      estatutos.add("Iniciando el parsing");
   }
}
