
package sarum;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class HallarPuntosMasCercano {

 File objeto2;
 BufferedReader entrada2;
 LineNumberReader entradaLength2;
 StringTokenizer tokens;
 String temporal;
 int contador;
 String separador;
 String punto;
int x=0;
int y=0;
int comaPrimera=0;
int comaSegunda=0;
int parentesisAbre=0;
int ParentesisCierra=0;
int ParentesisCierra2=0;
int escojer=0;
int x1=0, x2=0;
int y1=0, y2=0;
Point origen;
Point destino;
/////////////
int InicioOrigen=0;
int FinDestino;

OrigenDestino OD= new OrigenDestino();

public void setArchivo(File archi) throws FileNotFoundException{

objeto2 = archi;
entrada2 = new BufferedReader(new FileReader(objeto2));
entradaLength2 = new LineNumberReader(new FileReader(objeto2));
}
    public void capturarPuntosArchivos(){
        try {
            
           /* if (objeto2.exists()) {
            } else {
            }*/
            
        if(escojer == 0){
            JOptionPane.showMessageDialog(null, "Escoje entrada por ARchivo");
            while ( (temporal = entrada2.readLine() ) != null){

                    separador = temporal;

                    tokens = new StringTokenizer(separador);

                    punto = (tokens.nextToken(""));

                    System.out.println("Los puntos origen : " +punto);

                    parentesisAbre=punto.indexOf("-(");
                    comaPrimera=punto.indexOf(",");
                     //System.out.println("coma : "+comaPrimera+ " ParentesisAbre : " +parentesisAbre);

                     comaSegunda=punto.lastIndexOf(",");
                    ParentesisCierra=punto.indexOf(')');
                    ParentesisCierra2=punto.lastIndexOf(')');
                    //System.out.println("comaSegunda : "+comaSegunda+ " ParentesisFinal : " +ParentesisCierra);  
                   origen= new Point(Integer.parseInt(punto.substring(1,comaPrimera)) , Integer.parseInt(punto.substring(comaPrimera+1, ParentesisCierra)));
                  InicioOrigen=  HallarPuntoMasCercano(origen);

                  System.out.println("ahora destino");

                   destino=new Point(Integer.parseInt(punto.substring(parentesisAbre+2 ,comaSegunda)) , Integer.parseInt(punto.substring(comaSegunda+1, ParentesisCierra2)));
                   FinDestino= HallarPuntoMasCercano(destino);

                   // System.out.println("El origen es : " +origen+" El destino es : "+destino);


            }
            entrada2.close();
        }else{
        JOptionPane.showMessageDialog(null, "escoje el tipo por Mouse");
            origen= new Point(x1,y1);

            InicioOrigen=  HallarPuntoMasCercano(origen);

            destino= new Point (x2, y2);
            
            FinDestino= HallarPuntoMasCercano(destino);
        }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
  /* OD.setOrigen(InicioOrigen);
            OD.setDestino(FinDestino);*/
     }

    

/*trae la informacion de que tipo de opcion de escoje para cargar el nodo mas cercano*/

    public void iniciarPuntos(String matriz[][], double escojerForma, double escojerForma2, double escojerForma3, double escojerForma4){
        try {

            //origen= new Point();
            escojer = (int) escojerForma;

            x1 =(int) escojerForma;
            x2 = (int)escojerForma3;
            y1 = (int)escojerForma2;
            y2 = (int)escojerForma4;

            MatP = matriz;

            //dimensionMatrizParadas();
            //llenarMatrizDeParadas();
            capturarPuntosArchivos();
            
            
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);

     }
    }

    String MatP[][];
    double distancia=0;
    double disTempo=1000000;
    int comita=0;
    String estacion="";
    int posicionI=0;

/*busca la hipotenusa entre dos puntos para saber cual es el mas cercano*/
public int HallarPuntoMasCercano(Point orig){

 for(int i = 0; i < MatP.length-1; i++){

        comita= MatP[i][3].indexOf(",");

        distancia = Math.sqrt( Math.pow((orig.x-(Integer.parseInt((MatP[i][3].substring(0,comita)))) ),2 )
               +
               Math.pow((orig.y-(Integer.parseInt(MatP[i][3].substring(comita+1,MatP[i][3].length() )) )), 2 )
               );

        if(distancia<disTempo){

            disTempo=distancia;
            estacion= MatP[i][2];
            posicionI=i;
        }
  }
 
System.out.println("distancia : "+disTempo+" la estacion mas cercana es : "+estacion);

 disTempo=1000;
 /*comita=0;
 estacion="";
 distancia=0;*/
 return posicionI;
     }

}
