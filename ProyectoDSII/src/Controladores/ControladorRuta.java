/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.RutaJpaController;
import Logica.Ruta;
import java.util.Date;
import java.util.Iterator;
import javax.persistence.EntityManager;

/**
 *
 * @author Dash
 */
public class ControladorRuta 
{
    private FabricaObjetos mi_fabrica;
    EntityManager manager;
    RutaJpaController DaoRuta;
    
    public ControladorRuta() 
    {
        mi_fabrica = new FabricaObjetos();   
        manager= mi_fabrica.crear().createEntityManager();
        DaoRuta = new RutaJpaController(mi_fabrica.getFactory());
        
    }
    
    
     public void consultarRuta(String nombre){
    
        Iterator i;
        //sirve para ejecutar consultas
        i = manager.createQuery("SELECT r FROM Ruta r WHERE (r.nombreRuta= '"+nombre+"')").getResultList().iterator();
//        System.out.print("id |\t Estado  |\t Nombre |\t Login\n");
        while(i.hasNext())
        {
            Ruta ruta = (Ruta) i.next();
            System.out.print(ruta.getNombreRuta());
        }
    }
     
     public void insertarRuta(String nombre,String descripcion,String estado)
    {
               
        Ruta ruta= new Ruta(nombre, descripcion, estado);
        
        try 
        {
            DaoRuta.create(ruta);
        } 
        catch (Exception e) 
        {
        
        }
         
    }
     
    public void modificarRuta(String nombre)
    {
        Ruta ruta_encontrada;
        ruta_encontrada = DaoRuta.findRuta(nombre);
        
        ruta_encontrada.setNombreRuta(nombre);
        ruta_encontrada.setEstado("");
        ruta_encontrada.setDescripcion("");
        
        try 
        {
            DaoRuta.edit(ruta_encontrada);
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    
    }
    
    public void eliminarRuta(String nombre){
        Ruta ruta_encontrada;
        ruta_encontrada = DaoRuta.findRuta(nombre);
        
        try 
        {
            DaoRuta.destroy(nombre);
        } 
        catch (Exception e) {
        }
        
    }
    
   
}
