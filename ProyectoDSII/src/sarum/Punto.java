package sarum;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;


public class Punto {

    /**Contador de nodos ingresados**/
    static int contNodos;
    /**Cuanta cuantos nodos son en total**/
    public static int NodosTotales;
    /**Para poder distribuir los nodos de forma equitativa**/
    static double grados;
  
    /**Coordenadas del punto**/
    double x, y;
    /**Nombre del nodo con estas coordenas**/
    String nombre;
    double normalSpace=50;
    String coo;
    int coma;
    
    public Punto(String nombre, String coordenada) {
        
        coo=coordenada;

        coma=coo.indexOf(',');

        x=Double.parseDouble( coo.substring(0,coma) ) * 9 + 800
                
                ;
        y=Double.parseDouble(coo.substring( coma+1, coo.length() ) ) * 9 + 800
     
                ;
        this.nombre = nombre;
    }

    public void cambiar(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void dibujar(Graphics2D g2d) {
        g2d.setColor(Color.BLUE);

        g2d.fill(new Ellipse2D.Double(x, y, 20, 20));
       // g2d.fill(new Ellipse2D.Double(250+x, y, 25, 25));

        g2d.setColor(Color.GREEN);

       g2d.drawString(nombre, (int)x+10, (int)y+16);
       // g2d.drawString(nombre, (int)x+10+250, (int)y+16);
    }
}
