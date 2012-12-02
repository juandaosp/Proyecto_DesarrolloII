package paquete;
import java.util.HashMap;
import java.util.Map;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Estudiante
 */
public class rutas {

    String nombre;
    int frecuencia;
    Map<String,String> estaciones;
    int [] estacionesParada;
    int [][] matrizAyacencia;
    boolean estado;

    public rutas(){
        estado =true;
        estaciones = new HashMap<String,String>();
        matrizAyacencia = new int[entradaDatos.cantidadParadas][entradaDatos.cantidadParadas];
        for(int i=0; i < matrizAyacencia.length; i++){
	  for (int k=0; k < matrizAyacencia[0].length; k++)
	      matrizAyacencia[i][k] = 0;
        }
    }

    public void setNombre(String nombre){
       this.nombre = nombre;
    }

    public void setEstado(boolean estado){
       this.estado = estado;
    }

    public void setFrecuencia(int frecuencia){
       this.frecuencia = frecuencia;
    }

    public String getNombre(){
        return nombre;
    }

    public int getFrecuencia(){
        return frecuencia;
    }

    public boolean getEstado(){
        return estado;
    }

}
