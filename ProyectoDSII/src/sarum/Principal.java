package sarum;


import java.awt.Point;
import java.io.IOException;
import java.util.Vector;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Wilmar
 */
public class Principal
{
    public static void main(String[] args) throws IOException
    {
        
        SARUM prueba = new SARUM();
        Parada[] arregloP=prueba.capturarParadas("src/Entradas/Ejemplo1Entrada1ParadasYRutas.txt");
        //System.out.println(arregloP.length);
        Ruta[] rutas=prueba.capturarRutas("src/Entradas/Ejemplo1Entrada1ParadasYRutas 2.txt");
        //prueba.buscarPuntoCercano(15,14, arregloP);
//        for(int i =0; i<rutas.length; i++){
//            prueba.rutaDirecta(24, 31, rutas[i]);
//            JOptionPane.showMessageDialog(null, "Ruta actual desde for: "+rutas[i].getNombre());
//        }
        //prueba.itinerariosArchivo("src/Entradas/Ejemplo1Entrada1ParadasYRutas 2.txt","src/Entradas/Ejemplo1Entrada2OrigenesYDestinos.txt");
        prueba.itinerariosMouse(60, 70, -30, 40);
        prueba.imprimirEnArchivo();
        //buscarPuntoCercano.buscarPuntoCercano(51, 14, arregloP);
        //System.out.println("distance2D: "+buscarPuntoCercano.buscarIndiceParada(21, 70, arregloP));
//        prueba.buscarIndiceParada(21, 70, arregloP);
//        System.out.println(prueba.buscarPuntoCercano(5, 563, arregloP));

//        Parada parada = new Parada();
//        Parada parada2 = new Parada();
//        parada.setNombre("Chiminangos");
//        parada2.setNombre("Universidades");
//        SemiRecorrido semiRecorrido = new SemiRecorrido();
//        semiRecorrido.setParadaAscenso(1);
//        semiRecorrido.setParadaDescenso(1);
//        semiRecorrido.setNombreRuta("T31-NS");
//        Vector<SemiRecorrido> recorrido = new Vector<SemiRecorrido>();
//        recorrido.add(semiRecorrido);
//        Itinerario itinerario = new Itinerario();
//        itinerario.setRecorrido(recorrido);
//        itinerario.imprimirEnArchivo("src/Salida/buscarPuntoCercano.txt");

//        Point punto = new Point();
//        punto.setLocation(53, 46);
//        Point punto1 = new Point();
//        punto1.setLocation(51, 14);
//        double distancia = punto.distance(punto1);
//        System.out.println("distance: "+distancia);
//        double distancia2D = punto.distanceSq(punto);
//        System.out.println("Tamaño arreglo: "+arregloP.length);

        





    }
}
