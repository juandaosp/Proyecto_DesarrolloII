package sarum;
import paquete.Voraz;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.StringTokenizer;
import java.util.Vector;
import paquete.Voraz;

public class Funcional {

int adyacen[][];
StringTokenizer tokens2;
int fila=0;
int columna=0;
String direccion;
File objeto;

public Funcional(){

//System.out.print("constructor Funcional"+" : "+adyacen.length);
}
public void setObjeto(File O){
    objeto = O;
}
int numerN=0;
public void numeroLineaApartirLeer(int cantidadN){
  numerN = cantidadN;
  //System.out.print("numerocantidad"+numerN);
}
/*
public static void main(String[] args) throws IOException {

	Funcional m = new Funcional();
	m.inicializarMatrizAdyacensia();
        m.iniciarTodo();
    }
 */
public void inicializarMatrizAdyacensia(){

	 for(int h=0; h<adyacen.length; h++){
	      for(int k=0;k<adyacen.length; k++){
	    	  adyacen[h][k]=0;
	         // System.out.print(adyacen[h][k]);
	      }
	     // System.out.print("\n");
	    }
}

Vector<String> buses = new Vector();

public void iniciarTodo() throws FileNotFoundException, IOException{
    adyacen = new int[numerN][numerN];
inicializarMatrizAdyacensia();

// System.out.print("NUMERO PARA LEER ARCHIVO "+numerN);

int NoParadas=-1;

//Variables para lectura de archivos
 
 BufferedReader entrada;
 LineNumberReader entradaLength;
 String sAux="";

int con=0;
 //Variables String Para los campos de la matriz de paradas
 String NombreRutaBus="";
 String sentido="";
 String frecuencia="";
 String tiempo = "";

//
   StringTokenizer tokens;
       
      
        String simbolos="";
            if (objeto.exists()){ } else{}
 entrada = new BufferedReader(new FileReader(objeto));
   entradaLength = new  LineNumberReader(new FileReader(objeto));

int i=0;
    while ((sAux = entrada.readLine())!=null){

         if(sAux.equals("")){}
            
    else{
        if(con>numerN+1){

        simbolos=sAux;
    tokens=new StringTokenizer(simbolos);
    NombreRutaBus=(tokens.nextToken("#"));
     buses.add(NombreRutaBus);
    frecuencia=(tokens.nextToken("#"));
    tiempo = (tokens.nextToken("#"));
    llenarAdyacensia(tiempo);
                    //i++;
        }
        con++;
       }
    }
    Voraz.setVectorBusesRutas(buses);
}
int peso=0;
int punto1=0;
int punto2=0;
int coma1=0;
int coma2=0;
;
String listacaminos="", vacio="";
int w=0;
String mvec[];
String temporarParadas[];

public void llenarAdyacensia(String listaCaminos){

listacaminos=listaCaminos;



tokens2=new StringTokenizer(listacaminos, "-");
temporarParadas = new String[tokens2.countTokens()];


 while(tokens2.hasMoreElements()){
	String punto=(tokens2.nextToken());
        temporarParadas[w]=punto;
   w++;
}
w=0;
//imprimirVec(temporarParadas);
mvec=temporarParadas;
Voraz.setVectorParadas(mvec);
matriz();
}
Voraz Di = new Voraz();

public void matriz(){

for(int i =0; i<temporarParadas.length-1; i++){
    coma1=mvec[i].indexOf(',');
    coma2=mvec[i+1].indexOf(',');
   //System.out.println("coma "+coma2);
    adyacen[
            Integer.parseInt(
            mvec[i].substring(coma1+1,mvec[i].length()-1)
            )-1
            ]

            [
            Integer.parseInt(
            mvec[i+1].substring(coma2+1,mvec[i+1].length()-1 )
            )-1
            ]
            =Integer.parseInt(
            mvec[i+1].substring(1,coma2)
            )
            ;
    
}
imprimirMatriz(adyacen);
//return adyacen;
   
}

public int[][] retornatMatrizAdyacencia( ){

return adyacen;
}
public void imprimirVec(String vec[]){

    for(int h=0; h<vec.length; h++){
    //System.out.print(vec[h]);
    }
 //System.out.print("el que sigue : \n");
}

public void imprimirMatriz(int mat[][]){

    for(int h=0; h<mat.length; h++){
      for(int k=0;k<mat.length; k++){
          
          System.out.print(mat[h][k]);

      }
      System.out.print("\n");
    }

    }
public void imprimirBuses(Vector<String> bus){

    for(String busesin : bus){
        
        System.out.println("los buses : "+busesin);
      
}

    }
}