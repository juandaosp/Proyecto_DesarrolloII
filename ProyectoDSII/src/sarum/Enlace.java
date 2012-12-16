package sarum;

/**
 * Es controlado por cada instancia de Nodo
 * @author SISTEMAS
 */
public class Enlace {

    private String destino;
    private float peso;

    public Enlace(String dest, float weight){
        destino = dest;
        peso = weight;
    }

    public Enlace(String dest){
        this(dest,-1f);
    }

    public String getDestino(){
        return destino;
    }

    public float getPeso(){
        return peso;
    }
}
