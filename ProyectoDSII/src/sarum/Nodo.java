package sarum;



import java.util.ArrayList;

public class Nodo {
    private String coo;
    /**Nombre del nodo**/
    private String nombre;
    /**Guarda los enlaces de este nodo**/
    public ArrayList<Enlace> enlaces;
    /**cuenta los enlaces existentes**/
    int contEnlaces;
    /**El auxiliar para graficar**/
    Punto punto;

    /**Constructor**/
    public Nodo(String nuevoNodo, String coordenada){
        nombre = nuevoNodo;
        coo= coordenada;
        contEnlaces = -1;
        enlaces = new ArrayList<Enlace>();
        punto = new Punto(nuevoNodo, coo);
       // System.out.println("Llamado al construtor Nodo : "+nombre+" : "+coo);
    }

    /**Devuelve la cantidad de enlaces existentes**/
    public int getEnlacesExistentes(){
        return contEnlaces;
    }

    /**Devuelve el nombre del nodo**/
    public String getNombre(){
        return nombre;
    }

    /**aniada un nuevo enlace al presente nodo**/
    public void addEnlace(String nuevoEnlace, float peso){
        if (contEnlaces == -1){//Si este nodo no tiene enlaces
            enlaces.add(new Enlace(nuevoEnlace,peso));
            contEnlaces++;//Incrementa el contador
        } else {
            int existeEnlace = isExisteEnlace(nuevoEnlace);
            if (existeEnlace == -1) {//Significa que no se repite el enlace
                enlaces.add(new Enlace(nuevoEnlace,peso));
                contEnlaces++;
            }
        }
    }

    /**Revisa si el enlace de prueba existe**/
    public int isExisteEnlace(String nombrePrueba){
        for (int i = 0; i < enlaces.size(); i++) {
            Enlace temp = enlaces.get(i);
            if (temp.getDestino().equals(nombrePrueba))
                return i;
        }
        return -1;
    }

    /**Devuelve el peso del nodo enlazado dado la posicion
     * @param posicion Indica la posicion del vector "enlaces"
     * @return float peso del enlace
     **/
    public float getPesoEnlazado(int posicion){
        return enlaces.get(posicion).getPeso();
    }

    /**Devuelve el nombre del nodo, el cual esta enlazado con el actual
     * @param posicion Indica la posicion del vector "enlaces"
     **/
    public String getNodoEnlazado(int posicion){
        return enlaces.get(posicion).getDestino();
    }

    public ArrayList<Enlace> getEnlaces() {
        return enlaces;
    }
}
