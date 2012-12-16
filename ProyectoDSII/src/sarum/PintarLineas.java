package sarum;

import java.util.ArrayList;

public class PintarLineas extends Thread {

    /**Controla el lienzo de dibujo**/
    Lienzo graficar;
    /**Es el grafo de ingreso**/
    Grafo original;
    /**El es arbol recubridor minimal**/
    Grafo arbol;
    /**Es un arco temporal**/
    Arco pro;
    /**Es la lista de arcos, de forma temporal**/
    ArrayList<Arco> L;

    /**
     * Resuelve para encontrar el arbol recubridor minimal
     **/
    @Override
    public void run() {
        try {
            while (L.size() != 0) {
                pro = L.get(0);

                if (HayCiclo(arbol, pro, arbol.getNodo(pro.getTerminal()), pro.getTerminal()) == false) {
                    PintarLineas.sleep(20);
                    arbol.ingresarEnlace(pro.getInicial(), pro.getTerminal(), pro.getPeso());
                    graficar.arbol = arbol;
                    graficar.repaint();
                }
                L.remove(pro);
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            System.out.println("fracaso el hilo");
        }
        System.out.println("termino el hilo exitosamente");
    }

    /**El constructor recibe el objeto del lienzo**/
    PintarLineas(Lienzo g) {
        graficar = g;
    }

    /**
     * Inicializa el proceso para encontrar el arbol recubridor minimal,
     * dado el Grafo de entrada
     **/
    public void aplicar(Grafo grafo) {
        arbol = new Grafo();
        ArrayList<String> nodos = grafo.getNombres();
          ArrayList<String> nod = grafo.getCoor();
        for (int j = 0; j < nodos.size()-1; j++) {
            System.out.println("lo que obtiene de la lista : "+nodos.get(j));
            arbol.ingresarNodo(  nodos.get(j)
                   // .substring(0,(nodos.get(j).indexOf(',')))
                    ,nod.get(j)
                    );
            
        }

        L = (ArrayList<Arco>) grafo.getAristas().clone();

        pro = L.get(0);
        arbol.ingresarEnlace(pro.getInicial(), pro.getTerminal(), pro.getPeso());
        graficar.arbol = arbol;
        graficar.repaint();
        L.remove(pro);
        //this.start();

    }

    /**
     * Este metodo recursivo devuelve "true" si el arco analizado
     * no genera ciclos, de lo contrario devuelve "false"
     **/
    public boolean HayCiclo(Grafo g, Arco aVerificar, Nodo terminal, String N) {
        ArrayList<Enlace> aux = terminal.getEnlaces();

        if (aux.size() == 0) {
            return false;
        }

        if (terminal.isExisteEnlace(aVerificar.getInicial()) != -1) {
            return true;
        }

        for (int i = 0; i < aux.size(); i++) {
            Enlace nodo = aux.get(i);

            if (nodo.getDestino().equals(N) == false) {
                if (HayCiclo(g, aVerificar, g.getNodo(nodo.getDestino()), terminal.getNombre())) {
                    return true;
                }
            }
        }
        return false;
    }
}
