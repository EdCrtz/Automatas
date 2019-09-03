package Java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Scanner {
   private int lineaNo;
   private int indice;
   private String[] tokens;
   private String token;
   private ArrayList<String> estatutos;
   final Token getToken() {
      if (indice == tokens.length)
         return new Token("EOF", -2, -1);
         token = tokens[indice];
         indice++;
         Token thisToken = new Token(token, -1, -1);
         if (Arrays.asList("do", "until", "class", "system.in.readln").contains(token)) {
            thisToken.setClasificacion(0);
            switch (token) {
               case "do":
                  thisToken.setTipo(Tokens.DO.ordinal());
                  break;
               case "class":
                  thisToken.setTipo(Tokens.CLASS.ordinal());
                  break;
               case "until":
                  thisToken.setTipo(Tokens.UNTIL.ordinal());
                  break;
               case "system.in.readln":
                  thisToken.setTipo(Tokens.SIR.ordinal());
                  break;
            }
            return thisToken;
         }
         if (Arrays.asList("boolean", "int", "float").contains(token)) {
            thisToken.setClasificacion(1);
            switch (token) {
               case "int":
                  thisToken.setTipo(Tokens.INT.ordinal());
                  break;
               case "boolan":
                  thisToken.setTipo(Tokens.BOOLEAN.ordinal());
                  break;
               default:
                  thisToken.setTipo(Tokens.FLOAT.ordinal());
            }
            return thisToken;
         }
         if (Arrays.asList("*","+","-","<","=").contains(token)) {
            thisToken.setClasificacion(2);
            switch (token) {
               case "*":
                  thisToken.setTipo(Tokens.ASTER.ordinal());
                  break;
               case "+":
                  thisToken.setTipo(Tokens.PLUS.ordinal());
                  break;
               case "-":
                  thisToken.setTipo(Tokens.MINUS.ordinal());
                  break;
               case "<":
                  thisToken.setTipo(Tokens.MIN.ordinal());
                  break;
               case "=":
                  thisToken.setTipo(Tokens.EQ.ordinal());
                  break;
            }
            return thisToken;
         }
         if (Arrays.asList("(",")","{","}",";").contains(token)) {
            thisToken.setClasificacion(3);
            switch (token) {
               case "(":
                  thisToken.setTipo(Tokens.LPAREN.ordinal());
                  break;
               case ")":
                  thisToken.setTipo(Tokens.RPARENT.ordinal());
                  break;
               case ";":
                  thisToken.setTipo(Tokens.SEMI.ordinal());
                  break;
               case "{":
                  thisToken.setTipo(Tokens.LBRACE.ordinal());
                  break;
               case "}":
                  thisToken.setTipo(Tokens.RBRACE.ordinal());
                  break;
            }
            return thisToken;
         }
         if (token.equals("true") || token.equals("false")) {
            thisToken.setClasificacion(4);
            if (token.equals("true"))
               thisToken.setTipo(Tokens.TRUE.ordinal());
            else
               thisToken.setTipo(Tokens.FALSE.ordinal());
            return thisToken;
         }
         if (validaIdentificador(token)) {
            thisToken.setClasificacion(5);
            thisToken.setTipo(Tokens.ID.ordinal());
            return thisToken;
         }
         if (validaInteger(token)) {
            thisToken.setClasificacion(6);
            thisToken.setTipo(Tokens.NINTE.ordinal());
            return thisToken;
         }
         if (validaFloat(token)) {
            thisToken.setClasificacion(7);
            thisToken.setTipo(Tokens.NFLOA.ordinal());
            return thisToken;
         }
         return thisToken;
   }
   public final void analizar () {
      Token token = null;
      while (true) {
         token = getToken();
         if (token.getTipo() == -2) {
            indice = 0;
            return;
         }
         estatutos.add(token.toString());
      }
   }

   private boolean validaIdentificador(String t){
      Pattern pat = Pattern.compile("^[a-zA-Z][a-zA-Z[0-9]]*+$");;
      Matcher mat = pat.matcher(token);
      return mat.find();
   }
   private boolean validaInteger(String t){
      Pattern pat = Pattern.compile("^[0-9]+$");;
      Matcher mat = pat.matcher(token);
      return mat.find();
   }
   private boolean validaFloat(String t){
      Pattern pat = Pattern.compile("^[+-][1-9]+.[1-9]++$");
      Matcher mat = pat.matcher(token);
      return mat.find();
   }
   public ArrayList<String> dameSalidas () {
      return estatutos;
   }
   public Scanner(String codigo) {
         estatutos = new ArrayList<String>();
         lineaNo = 0;
         indice = 0;
         token = "";
         tokens = codigo.split("\\s+");
         estatutos.add("Iniciando scanning");
   }
}
