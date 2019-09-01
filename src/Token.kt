class Token (var valor: String, var tipo: Int, var clasificacion:Int) {
    private val clas = arrayOf("palabra reservada", "tipo de dato", "operador","delimitador","booleano","identificador","integer","float");
    override fun toString(): String {
        return if (clasificacion!=-1) "Token encontrado...."+clas[clasificacion]+" $valor "+ Tokens.values()[tipo].name else "Token error... $valor"
    }
}