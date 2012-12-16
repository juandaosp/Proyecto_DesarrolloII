package sarum;

import java.util.Hashtable;
import java.util.ArrayList;

/**
 * Controla los Nodos y Aristas
 * @author SISTEMAS
 */
public class Grafo {

    ArrayList<String> nombres;
    ArrayList<Arco> aristas;//Conocido como enlaces
    Hashtable<String, Nodo> nodos;
    ArrayList<String>coordenadas;
    double pesoTotal;

    public Grafo() {
        nombres = new ArrayList<String>();
        coordenadas= new ArrayList<String>();
        aristas = new ArrayList<Arco>();
        nodos = new Hashtable<String, Nodo>();
    }

    public void ingresarNodo(String nombre, String coo) {

        nombres.add(nombre);
        coordenadas.add(coo);
        nodos.put(nombre, new Nodo(nombre, coo));
        
    }

    /**Ingresa un enlace solo una vez, si encuentra uno repitido
     * Esta le niega el ingreso.
     **/
    public void ingresarEnlace(String nodoInicial, String nodoTerminal, float peso) {

        System.out.println("nodo inicial : "+nodoInicial+" "+"nodo Terminal "+nodoTerminal+"  peso es "+peso);
        Arco nuevo = new Arco(nodoInicial, nodoTerminal, peso);


        boolean existeArco = false;
        for (int i = 0; i < aristas.size(); i++) {
            if (aristas.get(i).toString().equals(nuevo.swapNodos())) {

                existeArco = true;
                break;
            }
        }

        if (!existeArco) {
            int i = buscarIndice(nuevo.getPeso());

            if (i == -1) {//Si "nuevo" es la arista mas grande
                aristas.add(nuevo);
            } else {
                aristas.add(i, nuevo);
            }

            pesoTotal += peso;

            System.out.println("ARISTA Inicio " + nodoInicial + " :: Final " + nodoTerminal + " :: peso " + peso + " GUARDADO");

            nodos.get(nodoInicial).addEnlace(nodoTerminal, peso);
            nodos.get(nodoTerminal).addEnlace(nodoInicial, peso);
        } else {
            System.out.println("ARISTA Inicio " + nodoInicial + " :: Final " + nodoTerminal + " :: peso " + peso + " REPETIDO, no se guardo!!");
        }
    }

    public boolean repiteArista() {
        for (int i = 0; i < aristas.size(); i++) {
        }
        return false;
    }

    /**Busca el indice que corresponde al peso actual
     * para ordenarlo de forma ascendente los pesos
     **/
    public int buscarIndice(float peso) {
        for (int i = 0; i < aristas.size(); i++) {
            if (peso < aristas.get(i).getPeso()) {
                return i;
            }
        }
        return -1;
    }

    public boolean existeNodo(String nodo) {
        return nombres.contains(nodo);
    }

    public ArrayList<String> getNombres() {
        return nombres;
    }

    public ArrayList<Arco> getAristas() {
        return aristas;
    }

    public Nodo getNodo(String llave) {
        return nodos.get(llave);
    }
    public ArrayList<String> getCoor() {

        return coordenadas;
    }
}
