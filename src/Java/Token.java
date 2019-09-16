package Java;

public final class Token {
   private final String[] clas;
   private String valor;
   private Tokens tipo;
   private int clasificacion;
   public String toString() {
      return this.clasificacion != -1 ? "Token encontrado...." + this.clas[this.clasificacion] + ' ' + this.valor: "Token error... " + this.valor + " no puede ser acepatado$";
   }

   public final String getValor() {
      return this.valor;
   }

   public final void setValor(String var1) {
      this.valor = var1;
   }

   final Tokens getTipo() {
      return this.tipo;
   }

   final void setTipo(Tokens var1) {
      this.tipo = var1;
   }

   public final int getClasificacion() {
      return this.clasificacion;
   }

   final void setClasificacion(int var1) {
      this.clasificacion = var1;
   }

   Token(String valor, Tokens tipo, int clasificacion) {
      this.valor = valor;
      this.tipo = tipo;
      this.clasificacion = clasificacion;
      this.clas = new String[]{"palabra reservada", "tipo de dato", "operador", "delimitador", "booleano", "identificador", "integer", "float"};
   }
}