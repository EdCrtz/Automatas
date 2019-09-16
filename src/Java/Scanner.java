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
            return new Token("EOF",null, -2);
        token = tokens[indice];
        indice++;
        Token thisToken = new Token(token,null, -1);
        for (Tokens e : Tokens.values()) {
            if (token.equals(e.getCad())) {
                thisToken.setTipo(e);
                if (Arrays.asList("do", "until", "class", "system.in.readln").contains(token)) {
                    thisToken.setClasificacion(0);
                    return thisToken;
                }
                if (Arrays.asList("boolean", "int", "float").contains(token)) {
                    thisToken.setClasificacion(1);
                    return thisToken;
                }
                if (Arrays.asList("*", "+", "-", "<", "=").contains(token)) {
                    thisToken.setClasificacion(2);
                    return thisToken;
                }
                if (Arrays.asList("(", ")", "{", "}", ";").contains(token)) {
                    thisToken.setClasificacion(3);
                    return thisToken;
                }
                if (token.equals("true") || token.equals("false")) {
                    thisToken.setClasificacion(4);
                    return thisToken;
                }
            }
        }
        if (validaIdentificador(token)) {
            thisToken.setClasificacion(5);
            thisToken.setTipo(Tokens.ID);
            return thisToken;
        }
        if (validaInteger(token)) {
            thisToken.setClasificacion(6);
            thisToken.setTipo(Tokens.NINTE);
            return thisToken;
        }
        if (validaFloat(token)) {
            thisToken.setClasificacion(7);
            thisToken.setTipo(Tokens.NFLOA);
            return thisToken;
        }
        return thisToken;
    }

    public final void analizar() {
        Token token = null;
       while (true) {
            token = getToken();
            if (token.getClasificacion() == -2) {
                indice = 0;
                return;
            }
            estatutos.add(token.toString());
        }
    }

    private boolean validaIdentificador(String t) {
        Pattern pat = Pattern.compile("^[a-zA-Z][a-zA-Z[0-9]]*+$");
        Matcher mat = pat.matcher(t);
        return mat.find();
    }

    private boolean validaInteger(String t) {
        Pattern pat = Pattern.compile("^[0-9]+$");
        Matcher mat = pat.matcher(t);
        return mat.find();
    }

    private boolean validaFloat(String t) {
        Pattern pat = Pattern.compile("^[+-][1-9]+.[1-9]++$");
        Matcher mat = pat.matcher(t);
        return mat.find();
    }

    public ArrayList<String> dameSalidas() {
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
