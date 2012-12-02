/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paquete;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.lang.Integer;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import algoritmos.BusquedaIngenua;
import algoritmos.Dinamico;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;


//import ArchivoEntrada.
public class Main {
    
    int NoParadas=0;
    String textoAImprimir="";
    private String sAux;
    private String[][] matrizEstaciones;
    private int con;
    private StringTokenizer tokens;
    private String tipo_paradas;
    private String indice;
    private String nombre;
    private String coord;
    static File objeto;
    private int origen;
    private int destino;
    private   String RutaDinamico;
    static Vector <String[]> RutaNombresIng = new Vector <String[]>();
    static Vector <String[]> RutaIngString= new Vector <String[]>();
   //public static String rutaArchivo = "Ejemplo1Entrada1ParadasYRutas.txt";
   public static String rutaArchivo;
     //static String rutaArchivo = "/media/HANDER FDO2/VariosAlgorimtosVoraces-jhon/src/ArchivosEntrad/Ejemplo1Entrada1ParadasYRutas.txt";
    static Vector <String[]> RutasIng = new Vector<String[]>();
    private BufferedReader entrada3;
    private LineNumberReader entradaLength3;
    private String sAux3;
    
    
    
   
 public void obtenerRuta (String rutaArchivo){
 objeto = new File (rutaArchivo);
 this.rutaArchivo=rutaArchivo;
 }
 
 public void obtenePuntos(int origen, int destino){
 
     this.origen=origen;
     this.destino=destino;
 }
//public static void main(String[] args) throws FileNotFoundException, IOException
//{ 
//     
//    Main m = new Main();
//   
//    //rutaArchivo = "Ejemplo1Entrada1ParadasYRutas.txt";
//    
//    
//    
//    
//    
//    
//    
//    
//    
//   // System.err.println("archivo"+objeto);
//    m.iniciar();
//
//
//}

    @SuppressWarnings("static-access")
public void iniciar() throws FileNotFoundException, IOException{
    
    int matrizAdyacencia[][];
    String matrizParadas[][];
    
   matrizParadas = dimensionMatrizParadas();
   
    
   Funcional funcional = new Funcional();
   
   funcional.setObjeto(objeto);
    
   // System.out.println("linea Aparti de la cual leer : "+NoParadas);
        funcional.numeroLineaApartirLeer(NoParadas);
        funcional.iniciarTodo();
        
        matrizAdyacencia=funcional.retornatMatrizAdyacencia();
		
                
                Voraz D = new Voraz();
                int exit=1;
                
              for(int n=1
                      ; n<matrizAdyacencia.length; n++){
                for(int m=1; m<matrizAdyacencia.length; m++){
             System.out.print(" "+matrizAdyacencia[n][m]);                     
                }
              System.out.println("");
            }
              
              // while(exit ==1){
                
               //int origen=Integer.parseInt( JOptionPane.showInputDialog("Digite el origen") );
               
               //int destino = Integer.parseInt( JOptionPane.showInputDialog("Digite el destino") );
               
           // llama al algoritmo  de busqueda ingenua el cual saca todas las rutas des de la mejor hasta la peor
           BusquedaIngenua jj =new BusquedaIngenua(matrizAdyacencia,origen,destino);
           jj.salidaOrdenada();
         
                     
           
                        
               D.principal(matrizAdyacencia, origen, destino);
               int []Ruta = D.RutaOpt;
        try {
            
            dimensionMatrizParadas();
            llenarMatrizDeParadas();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
             
               Dinamico dinamico= new Dinamico();
               dinamico.iniciar(matrizAdyacencia,origen, destino, vec);
               
               
               String [] RutaNombres = new String [Ruta.length-1];
               String RutaOpt ="";
                String RutaOptIng ="";
               try {
                   entradaDatos entrada = new entradaDatos();
                   int n = jj.rutasIngenuas.size();
                  
                   for (int i=0; i<n;i++){                 
                   
                       StringTokenizer token = new StringTokenizer(jj.rutasIngenuas.elementAt(i).toString(),", ");
                      
                       int nDatos=token.countTokens();
                       String []arreglo = new  String [nDatos];
                       
                        for (int j = 0; j < nDatos; j++){
                            //int aux = Integer.parseInt(token.nextToken().toString());
                            arreglo[j]= token.nextToken().toString();
                            //JOptionPane.showMessageDialog(null, arreglo[j]);
                        }
                        RutasIng.add(arreglo);                  
                   }
                   n= RutasIng.size();
                   //JOptionPane.showMessageDialog(null, n);
                  for(int i = 0; i<n; i++){
                       String []aux = RutasIng.elementAt(i);
                       RutaIngString.add(aux);
                       //JOptionPane.showMessageDialog(null, aux.length+" Tamano aux  "+ i);
                       //JOptionPane.showMessageDialog(null, aux[i]);
                       String [] RutaNombresIngArreglo = new String[aux.length-2]; 
                       //RutaNombresIng = new String[jj.rutasIngenuas.size()][aux.length-1];
                       String tiempo="";
                      for(int j = 0; j<aux.length-2; j++){                
                          RutaNombresIngArreglo[j]=entrada.buscarNombreRuta(Integer.parseInt(aux[j]), Integer.parseInt(aux[j+1]));
                                            
                          RutaOptIng +=Integer.parseInt(aux[j])+"#"+RutaNombresIngArreglo[j]+"#"+Integer.parseInt(aux[j+1])+"\n";
                             tiempo=aux[j+2];                        
                                                  
                       }
                     
                  
                      JOptionPane.showMessageDialog(null,RutaOptIng,"salida ingenuo Tiempo:"+tiempo,JOptionPane.INFORMATION_MESSAGE);
                       RutaOptIng="";

                      
                      
                  }
               String[] RutaNombresDina = new String [dinamico.rutaDina.length] ;
                  
                   for(int i = 0; i<dinamico.rutaDina.length-1; i++){
                       RutaNombresDina[i]= entrada.buscarNombreRuta(Integer.parseInt(dinamico.rutaDina[i]), Integer.parseInt(dinamico.rutaDina[i+1]));
                       //JOptionPane.showMessageDialog(null,RutaNombres[i]);
                       RutaDinamico += Integer.parseInt(dinamico.rutaDina[i])+"#"+RutaNombresDina[i]+"#"+Integer.parseInt(dinamico.rutaDina[i+1])+"\n";
                    
                   }
                       
                   JOptionPane.showMessageDialog(null, RutaDinamico,"salida Dianmico tiempo:",JOptionPane.PLAIN_MESSAGE);
                   
                   
                   for(int i = 0; i<Ruta.length-1; i++){
                       RutaNombres[i]= entrada.buscarNombreRuta(Ruta[i], Ruta[i+1]);
                       //JOptionPane.showMessageDialog(null,RutaNombres[i]);
                       RutaOpt += Ruta[i]+"#"+RutaNombres[i]+"#"+Ruta[i+1]+"\n";
                      
                   }

               } catch (Exception ex) {
                   Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
               }

            // textoAImprimir+=RutaOptIng+RutaDinamico+RutaOpt;
               JOptionPane.showMessageDialog(null, RutaOpt,"salida voras tiempo:",JOptionPane.PLAIN_MESSAGE);


             
    }
    
 BufferedReader entrada;

 LineNumberReader entradaLength;
    
public String[][] dimensionMatrizParadas() throws FileNotFoundException, IOException{

    entrada = new BufferedReader(new FileReader(objeto));
   entradaLength = new  LineNumberReader(new FileReader(objeto));

     while (!(sAux = entrada.readLine()).equals("rutas")){
          if(sAux.equals(""));
    else{

         NoParadas++;
           //System.out.println(sAux);
        //cantidadSimbol(sAux,"paradas");
     }
     }
     matrizEstaciones = new String[NoParadas][4];
 //System.out.println("tamaño matriz : "+matrizEstaciones.length);
     entrada.close();
     //NoParadas=0;
     return matrizEstaciones;
    }
             
             public String[][] llenarMatrizDeParadas() throws Exception{
    String simbolos="";
            //if (objeto.exists()){ } else{}
 entrada = new BufferedReader(new FileReader(objeto));
   entradaLength = new  LineNumberReader(new FileReader(objeto));

int i=0;
    while (!(sAux = entrada.readLine()).equals("rutas")){
 if(sAux.equals(""));
    else{
         if(con>0){
        simbolos=sAux;
           //System.out.println(sAux);
        //cantidadSimbol(sAux,"paradas");
     tokens=new StringTokenizer(simbolos);
    tipo_paradas=(tokens.nextToken("#"));
    indice=(tokens.nextToken("#"));
    vec.add(indice);
    nombre=(tokens.nextToken("#"));
    coord = (tokens.nextToken("#\n"));

//System.out.print(matrizEstaciones.length);
                    matrizEstaciones[i][0]=tipo_paradas;
                    matrizEstaciones[i][1]=indice;
                    matrizEstaciones[i][2]=nombre;
                    matrizEstaciones[i][3]=coord;
                    i++;
        }
        con++;
    }
    }


  imprimirMatriz(matrizEstaciones);
    entrada.close();
    return matrizEstaciones;
  }
             
public void imprimirMatriz(String mat[][]){

    for(int h=0; h<mat.length; h++){

      for(int k=0;k<4; k++){
          
          //System.out.print(mat[h][k]);

      }
      //System.out.print("\n");
    }

    }


Vector vec = new Vector(1,1);



public String imprimirEnArchivo()
{
    try
        {
            FileWriter archivo = new FileWriter("src/Salida/Resultados.txt");
            BufferedWriter bufer = new BufferedWriter(archivo);
            PrintWriter salida = new PrintWriter(bufer);
            String titulo = "Solución Calculada con algoritmo voraz:\n\n";
            salida.println(titulo+=textoAImprimir);
            salida.close();
            JOptionPane.showMessageDialog(null, "Puede revisar los resultados en: src/Salida/Resultados.txt");
        } catch (IOException ex) {
        }
        return textoAImprimir;
}
}
