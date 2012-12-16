/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.TarjetaIngresaEstacionJpaController;
import DAO.TarjetaJpaController;
import Logica.Tarjeta;
import java.util.Date;
import java.util.Iterator;
import javax.persistence.EntityManager;

/**
 *
 * @author Dash
 */
public class ControladorTarjeta 
{
    private FabricaObjetos mi_fabrica;
    EntityManager manager;
    TarjetaJpaController DaoTarjeta;

    public ControladorTarjeta() 
    {
        mi_fabrica = new FabricaObjetos();   
        manager= mi_fabrica.crear().createEntityManager();
        
        DaoTarjeta=new TarjetaJpaController(mi_fabrica.getFactory());
    
    }
    
    public void consultarTarjeta(String pin)
    {
        Iterator i;
        //sirve para ejecutar consultas
        i = manager.createQuery("SELECT t FROM Tarjeta t WHERE (t.pin = '"+pin+"')").getResultList().iterator();
        while(i.hasNext())
        {
            Tarjeta tarjeta=(Tarjeta) i.next();
            System.out.print(tarjeta.getPin());
            
        }
    }
    
    public void insertarTarjeta(int costo,String estado,Date fecha,int pin,String tipo)
    {
     
        Tarjeta tarjeta = new Tarjeta(tipo, costo, tipo, estado, pin, fecha);        
        
        try 
        {
            DaoTarjeta.create(tarjeta);
        } 
        catch (Exception e)
        {
        
        }
    }
    
    public void eliminarTarjeta(String pin)
    {
        Tarjeta tarjeta = (Tarjeta)DaoTarjeta.findTarjeta(pin);
        
        try 
        {
            DaoTarjeta.destroy(tarjeta.getPin());
        } 
        catch (Exception e) 
        {
        
        }       
    }
    
    public void modificarTarjeta(String pin,int costo,String estado,Date fecha,String tipo)
    {
        Tarjeta tarjeta= DaoTarjeta.findTarjeta(pin);
        
        tarjeta.setEstado(estado);
        tarjeta.setCosto(costo);
        tarjeta.setFecha(fecha);
        tarjeta.setTipo(tipo); 
    
        try {
            DaoTarjeta.edit(tarjeta);
        } catch (Exception e) {
        }
    }
    
}
