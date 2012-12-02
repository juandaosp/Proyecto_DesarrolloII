
/*ESTE ALGORITMO UTILIZA DOS ESTRUCTURAS DE DATOS , PILA -> LA CUAL SE USA PARA SACAR TODAS LA RUTAS POSIBLES
 Y HASTABLE PARA ORDENAR ESAS RUTAS DE LA MEJOR A LA PEOR ENCONTRADA*/

package algoritmos;
import java.util.*;
public class BusquedaIngenua {
//matris de abyacencia 
  private int[][] matrix;
//vector se usa para sacar el camino 
  public static  Vector nodos;
//vector que contiene todas las rutas alladas mas su peso
  public static Vector rutasIngenuas; 
  // contien cada estacion por la que se puede pasar para llegar al destino final
  private String ingenuo;
 // se utiliza para ordenar la salida de rutas 
  private Hashtable<Integer,String> ordenar;
  
  // el constructor captura la matris de abyacencia el origen i y destino
  public BusquedaIngenua(int[][] matrix,int origen,int destino ) {
        nodos = new Vector(matrix.length, 1);
        for(int i =0; i<=matrix.length; i++){
               nodos.add(i);
        }
        //inicializo la matris, el vector que contenera las rutas ya ordenadas y la tabla hash
        this.matrix=matrix;
        rutasIngenuas = new Vector(nodos.size(), 1);
        ordenar = new Hashtable<Integer, String>();
        
        encontrarRutaMinimaFuerzaBruta(origen, destino);
    }
  
    // encuentra la ruta mínima entre dos nodos del matrix
    private void encontrarRutaMinimaFuerzaBruta(int inicio, int fin) {
     // cola para almacenar cada ruta que está siendo evaluada
        Stack<Integer> resultado = new Stack<Integer>();
        resultado.push(inicio);
        recorrerRutas(inicio, fin, resultado);
       
        //System.out.println("arreglo asignado ="+matrix[1][2]);
    }
 
    // recorre recursivamente las rutas entre un nodo inicial y un nodo final
    // almacenando en una cola cada nodo visitado

    private void recorrerRutas(int nodoI, int nodoF, Stack<Integer> resultado) {
        // si el nodo inicial es igual al final se muestra y evalúa la ruta en revisión
        ingenuo="";
        int peso=0;
        //Object peso;
    
        if(nodoI==nodoF) {
            
            for(int x: resultado){ 
              // almacena cada posicion en un estring separado por ,  
             ingenuo +=nodos.elementAt(x)+ ", ";
            }
            peso =evaluar(resultado);
            //le agrea el peso en la ultimaposicion del laruta
            ingenuo +=peso;
            // guarda en la tabla hash la ruta con el peso teniedo su peso como llave para asi despues ser ordenada la salida
            ordenar.put(peso,ingenuo);
            return;
            
        }
       
        //else System.out.println("no se encontro ruta");
        // Si el nodoInicial no es igual al final se crea una lista con todos los nodos
        // adyacentes al nodo inicial que no estén en la ruta en evaluación
        List<Integer> lista = new Vector<Integer>();
        for(int i=0; i<matrix.length;i++) {
            if(matrix[nodoI][i]!=0 && !resultado.contains(i)){
                lista.add(i);
            }
        }
        // se recorren todas las rutas formadas con los nodos adyacentes al inicial
        for(int nodo: lista) {
            resultado.push(nodo);
            recorrerRutas(nodo, nodoF, resultado);
            resultado.pop();
            
        
        }
        
            
    }
 
    // evaluar la longitud de una ruta
    public int evaluar(Stack<Integer> resultado) {
        int  resp = 0;
        int[]   r = new int[resultado.size()];
        int     i = 0;
        for(int x: resultado){
            r[i]=x;
            i++;            
        }
        for(i=0; i<r.length-1; i++){            
            resp+=matrix[r[i]][r[i+1]];}
        return resp;
    }
 
    
    public void salidaOrdenada(){
    
        String ruta="";
          for(int i=0; i<=nodos.size(); i++){
              
          // trae la ruta segun su llave  
          ruta = ordenar.get(i);
          //evalua si la salida segun su llave no sea null
            if ( ruta != null) {
                //adiciona ka ruta al vector de salida para despues con este sacar los buces que debe abordar
                 rutasIngenuas.add(ruta) ;
                  //System.out.println("salida ruta "+ruta);
            }
          }
             
    }
    
    
  }
    
 

 
    
  
