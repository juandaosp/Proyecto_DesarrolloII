/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.TarjetaIngresaEstacionJpaController;
import DAO.TarjetaJpaController;
import Logica.Tarjeta;
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
    
    public void insertarTarjeta(int costo,String estado,Date fecha){
     
        
    }    
    
}
