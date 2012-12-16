package sarum;


import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Wilmar
 */
public class Ruta
{
    String nombre;
    int frecuaencia;
    int [][] paradaTiempo;

    public Ruta()
    {
    }
    
    public void setNombre(String nombre)
    {
        this.nombre=nombre;
    }
    public String getNombre()
    {
        return this.nombre;
    }
    
    public void setFrecuencia(int frecuencia)
    {
        this.frecuaencia= frecuencia;
    }
    public int getFrecuencia()
    {
        return this.frecuaencia;
    }
    
//    public void imprimirParadas()
//    {
//        for(int i=0; i<paradaTiempo.length; i++)
//            System.out.println("Parada: "+ paradaTiempo[i][0]+" Tiempo: "+paradaTiempo[i][1]);
//    }
//    public int obtenerTiempo(int indice)
//    {
//        int tiempo=0;
//        for(int i=0; i<paradaTiempo.length; i++)
//            if(indice==paradaTiempo[i][0])
//                tiempo=paradaTiempo[i][1];
//
//        return tiempo;
//    }
    public int obtenerPosicParada(int indice)
    {
        int posicion=0;
        for(int i=0; i<paradaTiempo.length; i++)
            if(indice==paradaTiempo[i][0])
                posicion=i;
        return posicion;
    }

    public boolean contieneParada(int indice)
    {
        boolean existe=false;
        for(int i=0; i<paradaTiempo.length; i++)
        {
             if(indice==paradaTiempo[i][0])
                 existe=true;
        }
        return existe;
    }
}
