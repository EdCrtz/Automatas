package Java;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public final class Parser {
   private int tkn;
   private ArrayList<String> estatutos;
   private Scanner scan;

   private void advance() {
      this.tkn = this.scan.getToken().getTipo();
   }

   private void eat(int t) {
      if (this.tkn != -2) {
         this.estatutos.add("Token Esperado " + Tokens.values()[t].name() + " Token Recibido " + Tokens.values()[this.tkn].name());
         if (this.tkn == t) {
            this.advance();
         } else {
            JOptionPane.showMessageDialog((Component)null, "Error..... Se esperaba " + Tokens.values()[t].name() + " no " + Tokens.values()[this.tkn].name());
            this.estatutos.add("Error..... Se esperaba " + Tokens.values()[t].name() + " no " + Tokens.values()[this.tkn].name() + "$");
         }
      }
   }
   public final void program() {
      int var1 = this.tkn;
      if (var1 == Tokens.CLASS.ordinal()) {
         this.eat(Tokens.CLASS.ordinal());
         this.eat(Tokens.ID.ordinal());
         this.varDeclaration();
         this.eat(Tokens.LBRACE.ordinal());

         while(this.tkn == Tokens.DO.ordinal() || this.tkn == Tokens.SIR.ordinal() || this.tkn == Tokens.ID.ordinal() || this.tkn == Tokens.LBRACE.ordinal()) {
            this.statement();
         }

         this.eat(Tokens.RBRACE.ordinal());
      } else {
         this.estatutos.add("Error");
      }

   }

   private void statement() {
      int var1 = this.tkn;
      if (var1 == Tokens.LBRACE.ordinal()) {
         this.eat(Tokens.LBRACE.ordinal());

         while(this.tkn == Tokens.DO.ordinal() || this.tkn == Tokens.SIR.ordinal() || this.tkn == Tokens.ID.ordinal()) {
            this.statement();
         }

         this.eat(Tokens.RBRACE.ordinal());
      } else if (var1 == Tokens.DO.ordinal()) {
         this.eat(Tokens.DO.ordinal());
         this.statement();
         this.eat(Tokens.UNTIL.ordinal());
         this.eat(Tokens.LPAREN.ordinal());
         this.expresion();
         this.eat(Tokens.RPARENT.ordinal());
      } else if (var1 == Tokens.SIR.ordinal()) {
         this.eat(Tokens.SIR.ordinal());
         this.eat(Tokens.LPAREN.ordinal());
         this.expresion();
         this.eat(Tokens.RPARENT.ordinal());
         this.eat(Tokens.SEMI.ordinal());
      } else if (var1 == Tokens.ID.ordinal()) {
         this.eat(Tokens.ID.ordinal());
         this.eat(Tokens.EQ.ordinal());
         this.expresion();
         this.eat(Tokens.SEMI.ordinal());
      }

   }

   public final void expresion() {
      int var1 = this.tkn;
      if (var1 == Tokens.ID.ordinal()) {
         this.eat(Tokens.ID.ordinal());
         int var2 = this.tkn;
         if (var2 == Tokens.MIN.ordinal()) {
            this.eat(Tokens.MIN.ordinal());
            this.eat(Tokens.ID.ordinal());
         } else if (var2 == Tokens.PLUS.ordinal()) {
            this.eat(Tokens.PLUS.ordinal());
            this.eat(Tokens.ID.ordinal());
         } else if (var2 == Tokens.MINUS.ordinal()) {
            this.eat(Tokens.MINUS.ordinal());
            this.eat(Tokens.ID.ordinal());
         } else if (var2 == Tokens.ASTER.ordinal()) {
            this.eat(Tokens.ASTER.ordinal());
            this.eat(Tokens.ID.ordinal());
         }
      } else if (var1 == Tokens.FALSE.ordinal()) {
         this.eat(Tokens.FALSE.ordinal());
      } else if (var1 == Tokens.TRUE.ordinal()) {
         this.eat(Tokens.TRUE.ordinal());
      } else if (var1 == Tokens.NFLOA.ordinal()) {
         this.eat(Tokens.NFLOA.ordinal());
      } else if (var1 == Tokens.NINTE.ordinal()) {
         this.eat(Tokens.NINTE.ordinal());
      } else {
         this.estatutos.add("Error... falta una expresion$");
      }

   }
   public final ArrayList<String> dameSalida() {
      return this.estatutos;
   }

   private final void varDeclaration() {
      int var1 = this.tkn;
      if (var1 == Tokens.BOOLEAN.ordinal()) {
         this.eat(Tokens.BOOLEAN.ordinal());
         this.eat(Tokens.ID.ordinal());
         this.eat(Tokens.SEMI.ordinal());
         this.varDeclaration();
      } else if (var1 == Tokens.INT.ordinal()) {
         this.eat(Tokens.INT.ordinal());
         this.eat(Tokens.ID.ordinal());
         this.eat(Tokens.SEMI.ordinal());
         this.varDeclaration();
      } else if (var1 == Tokens.FLOAT.ordinal()) {
         this.eat(Tokens.FLOAT.ordinal());
         this.eat(Tokens.ID.ordinal());
         this.eat(Tokens.SEMI.ordinal());
         this.varDeclaration();
      }

   }

   public Parser(Scanner scan) {
      this.scan = scan;
      this.estatutos = new ArrayList<String>();
      this.tkn = this.scan.getToken().getTipo();
      this.estatutos.add("Iniciando el parsing");
   }
}
