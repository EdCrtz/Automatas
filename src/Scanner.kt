import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.Arrays



class Scanner//CONSTRUCTOR
    (codigo:String) {
    //DECLARACIONES
    private var lineaNo:Int = 0
    private var indice:Int = 0
    private var tokens: Array<String> = codigo.split(("\\s+").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    private var token:String
    private val reservadas = arrayOf<String>("do", "until","class", "system.ln.readln")
    private val tipos = arrayOf("boolean", "int", "float")
    private val operadores = arrayOf<String>("*", "-", "+","<","=")
    private val delimitadores = arrayOf<String>("{", "}", "(",")",";")
    private var estatutos = ArrayList<String>()
    var tope:Int =0
    init{
        lineaNo = 0 //Aun sin implementar...
        indice= 0
        token = ""
        tope = tokens.size
        estatutos.add("Iniciando scanning")
    }
    //MÉTODO que retorna tokens válidos al parser
    fun getToken():Token{
        if(indice==tokens.size)
            return Token("EOF",-2,-1)
        token = tokens[indice]
        indice++;
        val thisToken: Token = Token(token,-1,-1)
        //VERIFICACIÓN LÉXICA
        if (reservadas.contains(token)){
            thisToken.clasificacion = 0
            when(token){
                "do"-> thisToken.tipo = Tokens.DO.ordinal
                "until"->thisToken.tipo = Tokens.UNTIL.ordinal
                "class"->thisToken.tipo = Tokens.CLASS.ordinal
                "system.ln.readln"->thisToken.tipo = Tokens.SLR.ordinal
            }
            return thisToken
        }
        if (tipos.contains(token)){
            thisToken.clasificacion = 1
            when(token){
                "int"-> thisToken.tipo = Tokens.INT.ordinal
                "boolean"->thisToken.tipo = Tokens.BOOLEAN.ordinal
                "float"->thisToken.tipo = Tokens.FLOAT.ordinal
            }
            return thisToken
        }
        if (operadores.contains(token)){
            thisToken.clasificacion = 2
            when(token){
                "<"-> thisToken.tipo = Tokens.MIN.ordinal
                "-"->thisToken.tipo = Tokens.MINUS.ordinal
                "+"->thisToken.tipo = Tokens.PLUS.ordinal
                "*"->thisToken.tipo = Tokens.ASTER.ordinal
                "="->thisToken.tipo = Tokens.EQ.ordinal
            }
            return thisToken
        }
        if (delimitadores.contains(token)){
            thisToken.clasificacion = 3
            when(token){
                "{"-> thisToken.tipo = Tokens.LBRACE.ordinal
                "}"->thisToken.tipo = Tokens.RBRACE.ordinal
                "("->thisToken.tipo = Tokens.LPAREN.ordinal
                ")"->thisToken.tipo = Tokens.RPARENT.ordinal
                ";"->thisToken.tipo = Tokens.SEMI.ordinal
            }
            return thisToken
        }
        if(token == "true"||token == "false"){
            thisToken.clasificacion = 4
            if(token == "true")
                thisToken.tipo = Tokens.TRUE.ordinal
            else
                thisToken.tipo = Tokens.FALSE.ordinal
            return thisToken
        }
        //Identificadores:
        if (validaIdentificador(token))
        {
            thisToken.clasificacion = 5
            thisToken.tipo = Tokens.ID.ordinal
            return thisToken
        }
        if(validaInteger(token)){
            thisToken.clasificacion = 6
            thisToken.tipo = Tokens.NINTE.ordinal
            return thisToken
        }
        if(validaFloat(token)){
            thisToken.clasificacion = 7
            thisToken.tipo = Tokens.NFLOA.ordinal
            return thisToken
        }
        return thisToken
    }
    fun analizar(){
        var token: Token
        while (true){
            token = getToken()
            if(token.tipo == -2)
                break;
            estatutos.add(token.toString())
        }
        indice = 0
    }
    fun validaIdentificador(t:String):Boolean {
        val pat: Pattern = Pattern.compile("^[a-zA-Z][a-zA-Z[0-9]]*+$")//Expresiones Regulares
        val mat = pat.matcher(token);
        return  mat.find()
    }
    fun validaInteger(t:String):Boolean{
        for (item in t)
            if(!item.isDigit())
                return false
        return true
    }
    fun validaFloat(t:String):Boolean{
        val pat: Pattern = Pattern.compile("^[+-][1-9]+.[1-9]++$")//Expresiones Regulares
        val mat = pat.matcher(token);
        return  mat.find()
    }
    fun dameSalidas():ArrayList<String> = estatutos
}