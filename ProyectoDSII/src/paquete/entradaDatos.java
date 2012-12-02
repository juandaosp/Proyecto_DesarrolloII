package paquete;
import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
/*Funcion encarda de recibir el archivo .txt y apartir de este realizar la estructura de datos,
 *Utiliza las clases paradas y rutas, en las cuales se guarda toda la informacion
 */
public class entradaDatos {
    static String rutaArchivoEntrada;
    static paradas[] parada;
    static rutas[] ruta;
    static int cantidadParadas;
    static int cantidadRutas;
    static int [][] matrizAyacenciaTotal;
    static int contador=0;
    static boolean estado = true;
    //static creandoArbol arb,arb1;
    static String subRta="";

    public entradaDatos()throws Exception{
        rutaArchivoEntrada = Main.rutaArchivo ;
       //"src/Entradas/Ejemplo1Entrada1ParadasYRutas.txt"
       File objeto = new File(rutaArchivoEntrada);
       if (objeto.exists())
           System.out.println(objeto.getAbsolutePath());
       else
           System.out.println("No existe");


       BufferedReader entrada = new BufferedReader(new FileReader(objeto));
       BufferedReader entradaAux = new BufferedReader(new FileReader(objeto));
       LineNumberReader entradaLength = new  LineNumberReader(new FileReader(objeto));

       String sAux = null;
       String nombreArreglo = null;


       // se saca la cantidad de rutas y la cantidad de paradas del archivo .txt
       if ((nombreArreglo = entrada.readLine()).equals("paradas")&& (sAux = entradaLength.readLine())!= null){
           while (!(sAux = entrada.readLine()).equals("rutas")&& (sAux = entradaLength.readLine())!= null)
               cantidadParadas++;
           System.out.println(cantidadParadas);
        }
       else{
           nombreArreglo = "rutas";
           while (!(sAux = entrada.readLine()).equals("paradas")&& (sAux = entradaLength.readLine())!= null)
               cantidadRutas++;
           System.out.println(cantidadRutas);
       }

       if (nombreArreglo.equals("paradas")){
            while ((sAux = entrada.readLine())!= null && (sAux = entradaLength.readLine())!= null)
                cantidadRutas++;
       }
       else{
            while ((sAux = entrada.readLine())!= null && (sAux = entradaLength.readLine())!= null)
                cantidadParadas++;
       }

       //parada = new paradas(cantidadParadas,objeto);
       //arreglo que contiene todas las paradas del archivo ingresado
       parada = new paradas[cantidadParadas];
       
       //arreglo que contiene todas las rutas del archivo ingresado
       ruta = new rutas[cantidadRutas];

       boolean estadoP=true,estadoR=true;
       String estado;
       estado = entradaAux.readLine();
       int contadorEstado = 0;

       while(estadoP||estadoR){
           if(estado.equals("paradas")){
               for (int j = 0; j < cantidadParadas; j++){
//                   System.out.println(entradaAux.readLine());
                   parada[j] = new paradas(cantidadParadas,objeto);
                   ingresoDatosParadas(entradaAux.readLine(),j);
                   imprimirParada(parada[j],j);
                   if (j==cantidadParadas-1){
                       estadoP=false;
                   }
               }
           }

           if(estado.equals("rutas")){
               for (int j = 0; j < cantidadRutas; j++){
//                   System.out.println(entradaAux.readLine());
                   ruta[j] = new rutas();
                   ingresoDatosRutas(entradaAux.readLine(),j);
                   imprimirRutas(ruta[j],j);
                   if (j==cantidadRutas-1){
                       estadoR=false;
                   }
               }
           }
           contadorEstado++;
           if(contadorEstado<2)
               estado = entradaAux.readLine();
       }

//        imprimirArreglo(ruta[7].estacionesParada);
//        imprimirMatriz(ruta[7].matrizAyacencia);
                
       matrizAyacenciaTotal = new int[ruta[0].matrizAyacencia.length][ruta[0].matrizAyacencia[0].length];
       for(int i = 0; i < ruta.length; i++){
           sumarMatriz(ruta[i].matrizAyacencia);
       }


 //      for(int i=0 ; i< ruta[6].estacionesParada.length; i++)
 //          System.out.println(ruta[6].estacionesParada[i]);
   //    imprimirMatriz(ruta[6].matrizAyacencia);

     //  imprimirMatriz(matrizAyacenciaTotal);
     //  armandoArbol(1,5);
       //imprimirMatriz(matrizAyacenciaTotal);
    }

// Metodo encargado de ingresar los datos al objeto paradas
     public static void ingresoDatosParadas(String descomponer, int key){
         StringTokenizer tokens=new StringTokenizer(descomponer, "#");
         int nDatos=tokens.countTokens();
         for (int i = 0; i < nDatos; i++){
             switch(i){
                 case 0:
                     parada[key].setTipo(Integer.parseInt(tokens.nextToken()));
                     break;
                 case 1:
                     parada[key].setKey(Integer.parseInt(tokens.nextToken()));
                     break;
                 case 2:
                     parada[key].setNombre(tokens.nextToken());
                     break;
                 case 3:
                     StringTokenizer tokensXY=new StringTokenizer(tokens.nextToken(), ",");
                     parada[key].setX(Integer.parseInt(tokensXY.nextToken()));
                     parada[key].setY(Integer.parseInt(tokensXY.nextToken()));
                     break;
                 default:
                    System.out.println("Error ingresoDatosParadas");
             }             
         }
     }

// Metodo encargado de ingresar los datos al objeto rutas
     public static void ingresoDatosRutas(String descomponer, int key){
         StringTokenizer tokens=new StringTokenizer(descomponer, "#");
         int nDatos=tokens.countTokens();
         for (int i = 0; i < nDatos; i++){
             switch(i){
                 case 0:
                     ruta[key].setNombre(tokens.nextToken());
                     break;
                 case 1:
                     ruta[key].setFrecuencia(Integer.parseInt(tokens.nextToken()));
                     break;
                 case 2:
                     descomponerRutas(tokens.nextToken(),key);
                     crearMatrizAyacencia(key);
                     break;
                 default:
                    System.out.println("Error ingresoDatosRutas");
             }
         }
     }

     // se descompone todos los datos referentes a las paradas de cada ruta ingresandolos en map <estacion,tiempo>
     // y se llena un arreglo que contine todas las paradas de la ruta
     public static void descomponerRutas(String descomponer,int key){
         StringTokenizer tokens=new StringTokenizer(descomponer, "-");
         StringTokenizer tokensCoord;
         int nDatos=tokens.countTokens();
         ruta[key].estacionesParada = new int [nDatos];
         String coordenada;
         String tiempo;
         String estacion;
         if (nDatos>=cantidadParadas)
             JOptionPane.showMessageDialog(null, "Tamano mayor :"+ nDatos );

         for (int i = 0; i < nDatos; i++){
             coordenada = tokens.nextToken();
             coordenada = coordenada.substring(1, coordenada.length()-1);
             tokensCoord = new StringTokenizer(coordenada, ",");
             tiempo = tokensCoord.nextToken();
             estacion = tokensCoord.nextToken();
             ruta[key].estaciones.put(estacion, tiempo);
             ruta[key].estacionesParada[i]=Integer.parseInt(estacion);
         }

     }

     // se crea la  matriz de adyacencia por cada ruta del .txt
     public static void crearMatrizAyacencia(int key){
         for (int i = 0; i < ruta[key].estacionesParada.length-1; i++){
             ruta[key].matrizAyacencia[ruta[key].estacionesParada[i]-1][ruta[key].estacionesParada[i+1]-1] = 1;
         }
//         imprimirMatriz(ruta[key].matrizAyacencia);
     }

     //no se Utiliza en el algoritmo
     public static void imprimirParada(paradas p, int key){
         System.out.println("Tipo "+parada[key].getTipo());
         System.out.println("Key "+parada[key].getKey());
         System.out.println("Nombre "+parada[key].getNombre());
         System.out.println("X "+parada[key].getX());
         System.out.println("Y "+parada[key].getY());
     }

     //no se Utiliza en el algoritmo
     public static void imprimirRutas(rutas r, int key){
         System.out.println("Nombre " + ruta[key].getNombre());
         System.out.println("Frecuencia " + ruta[key].getFrecuencia());
         System.out.println("cantidad de estaciones :   " +ruta[key].estaciones.size());
         Iterator it = ruta[key].estaciones.entrySet().iterator();
         while (it.hasNext()) {
             Map.Entry pairs = (Map.Entry)it.next();
             System.out.println(pairs.getKey() + " = " + pairs.getValue());
         }         
     }

     //no se Utiliza en el algoritmo
     public static void imprimirMatriz(int[][] matrixC){
      int f=1;
      for(int i=0; i < matrixC.length; i++){
	  int c = 1;
	  System.out.printf("\n");
	  for (int k=0; k < matrixC[0].length; k++){
	      System.out.printf("%d:%d = %d\t",f,c,matrixC[i][k]);
	      c++;
	   }
	   f++;
      }
      System.out.printf("\n");
    }

     
     public static void sumarMatriz(int[][] matrizAyacenciaT){
         for(int i=0; i < matrizAyacenciaT.length; i++){
             for (int k=0; k < matrizAyacenciaT[0].length; k++){
                 matrizAyacenciaTotal[i][k] += matrizAyacenciaT[i][k];
             }
         }        
     }

     //no se Utiliza en el algoritmo
     public static void imprimirArreglo(int[] matrixC){

      for(int i=0; i < matrixC.length; i++){
          System.out.printf("%d\t",matrixC[i]);
      }
      System.out.printf("\n");
    }

     
     public static int armandoArbol(int origen,int destino){
         if(origen==destino)
             return 0;
         int columna=0;
         if (origen>0)
            columna=origen-1;
         for(int i=origen-1; i < matrizAyacenciaTotal.length; i++){
             for (int k=0; k < matrizAyacenciaTotal[0].length; k++){
                 if(matrizAyacenciaTotal[i][k]>contador)
                     contador = matrizAyacenciaTotal[i][k];
             }
         }
         //System.out.println("Contador   "+contador);
         for(int j=0; j<contador; j++){
             for(int i=origen-1; i < destino-1; i++){
                 for (int k=0; k < matrizAyacenciaTotal[0].length; k++){

                     if (matrizAyacenciaTotal[i][k]>0)
                        subRta+=i+"-"+buscarNombreRuta(i,k)+"-"+k+" ";
                     if(k>i){
                         if (matrizAyacenciaTotal[i][k]>0){
                             matrizAyacenciaTotal[i][k]=matrizAyacenciaTotal[i][k]-1;
                             if (i==columna){
//                                 arb = new creandoArbol(" :", i+1, k+1, destino);
//                                 arb.start();
                                 //arb.guardarSubArbol(i+1, k+1, destino);
                                 //JOptionPane.showMessageDialog(null,"despues del hilo " + "fila "+i +": columna "+k);
                                 guardarSubArbol(i+1,k+1,destino);
                             }
                             if(estado){
                                 columna = k;
                                 estado = false;
                             }
                         }
                     }
                 }
             }
         }
         //System.out.println(arbol);
         return 0;
     }

     private static void guardarSubArbol(int origen, int destino, int key) {
        System.out.println(origen+"-"+buscarNombreRuta(origen,destino)+"-"+destino);
        armandoArbol(destino,key);
     }

     
     public static String buscarNombreRuta(int origen, int destino){
       
        for(int i = 0; i<ruta.length ; i++){
           for(int j=0; j<ruta[i].estacionesParada.length-1; j++){
                if (ruta[i].estacionesParada[j]==origen && ruta[i].estacionesParada[j+1]==destino){                    
                    //ruta[i].estacionesParada[j]=0;                    
                    //ruta[i].setEstado(false);
                    return ruta[i].nombre;
                }
           }
        }
         return "No hay RUTA";
    }



     public static void main(String[] args) throws Exception {
         entradaDatos entradad = new entradaDatos();

    }
}
