package sarum;
/**
 *
 * @author SISTEMAS
 */
public class Arco {
    private String inicial;
    private String terminal;
    private float peso;
    
    public Arco(String start, String end, float weight){
        inicial = start;
        terminal = end;
        peso = weight;
    }

    public float getPeso(){
        return peso;
    }

    public String getInicial(){
        return inicial;
    }

    public String getTerminal(){
        return terminal;
    }

    public void setTerminal(String end){
        terminal = end;
    }

    @Override
    public String toString(){
        return "(" + inicial + ", " + terminal + ", " + peso + ")";
    }

    /**Utilizado para el ingreso de Enlaces, para evitar
     * ingresar dos veces el mismo arista, solo con los
     * terminales cambiados.
     **/
    public String swapNodos(){
        String i = inicial;
        String t = terminal;
        inicial = terminal;
        terminal = i;
        String retorno = this.toString();
        inicial = i;
        terminal = t;
        return retorno;
    }
}
