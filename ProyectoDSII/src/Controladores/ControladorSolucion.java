/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.SolucionJpaController;
import Logica.Solucion;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import javax.persistence.EntityManager;

/**
 *
 * @author Dash
 */
public class ControladorSolucion 
{
    private FabricaObjetos mi_fabrica;
    EntityManager manager;
    SolucionJpaController DaoSolucion;
    
    public ControladorSolucion() 
    {
        mi_fabrica = new FabricaObjetos();   
        manager= mi_fabrica.crear().createEntityManager();
        //Daos
        DaoSolucion= new SolucionJpaController(mi_fabrica.getFactory());
    
    }
    
    public void consultarSolucion(String idSolicitud)
    {
        Iterator i;
        //sirve para ejecutar consultas
        i = manager.createQuery("SELECT s FROM Solucion s WHERE (s.tiquetSolicitud = '"+idSolicitud+"')").getResultList().iterator();
//        System.out.print("id |\t Estado  |\t Nombre |\t Login\n");
        while(i.hasNext())
        {
            Solucion solucion= (Solucion) i.next();
            System.out.print(solucion.getTiquetSolicitud());
            
        }
    }
    
    public void insertarSolucion(String ticket,String descripcion,String ejecutada,String fecha)
    {
        Solucion solucion = new Solucion();
        Date fecha_con_formato=this.getDate(fecha);
        
        solucion.setDescripcion(descripcion);
        solucion.setEjecutada(ejecutada);
        solucion.setTiquetSolicitud(ticket);
        solucion.setFecha(fecha_con_formato);
        
        try 
        {
            DaoSolucion.create(solucion);
        } 
        catch (Exception e) 
        {
        }
        
    }
    
    public void modificarSolucion(String ticket)
    {
        Solucion solucion = DaoSolucion.findSolucion(ticket);
        
        solucion.setDescripcion("");
        solucion.setEjecutada("");        
        solucion.setFecha(this.getDate("2000-08-04"));
        
        try 
        {
            DaoSolucion.edit(solucion);
        } 
        catch (Exception e) 
        {
        
        }
        
    }
    
    public void eliminarSolucion(String ticket)
    {
        
        Solucion solucion_encontrada = DaoSolucion.findSolucion(ticket);
        
        try 
        {
            DaoSolucion.destroy(ticket);
        } 
        catch (Exception e) 
        {
        
        }
        
        
    }
    
    
    
    public Date getDate(String date)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try 
        {

            return df.parse(date);

        } 
        catch (ParseException ex) 
        {

        }

        return null;

    }
    
    
}
