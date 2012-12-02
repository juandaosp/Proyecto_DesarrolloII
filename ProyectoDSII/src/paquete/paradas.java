package paquete;
import java.io.File;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Estudiante
 */
public class paradas {
    int tipo;
    int key;
    String nombre;
    int x;
    int y; 
    
    
    public void paradas (int tamano){
        
    }
    
    public paradas(int tamano, File entradad){
        
    }

    public void setTipo(int tipo){
        this.tipo = tipo;
    }

    public void setKey(int key){
        this.key = key;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getTipo(){
        return tipo;
    }
    public int getKey(){
        return key;
    }
    public String getNombre(){
        return nombre;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    
}
