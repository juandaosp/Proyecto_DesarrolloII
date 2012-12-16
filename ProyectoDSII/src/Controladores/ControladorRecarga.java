
package Controladores;

import javax.swing.*;
import DAO.RecargaJpaController;
import Logica.Recarga;
import javax.persistence.EntityManager;
import Controladores.FabricaObjetos;
import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;


public class ControladorRecarga {
    
    String pin;
    String fecha;
    int valorRecarga;
    
    private FabricaObjetos mi_fabrica;    
     RecargaJpaController DaoRecarga;
     EntityManager manager;
     

   
    public ControladorRecarga()
    {
       mi_fabrica = new FabricaObjetos();   
       manager= mi_fabrica.crear().createEntityManager();

       //se requiere ingresar por parametro una fabrica de objetos para manipular las entidades (crear, modificar..en fin)
       DaoRecarga = new RecargaJpaController(mi_fabrica.getFactory());
    }
    
    public void consultarRecarga(String matricula)
    {
         Iterator i;
            //sirve para ejecutar consultas
        
            i = manager.createQuery("SELECT b FROM Bus b WHERE (b.matricula ='"+matricula+"')").getResultList().iterator();
            System.out.print("id |\t Nombre  |\t serial |\t proveedor\n");
            while(i.hasNext())
            {
                Recarga myRecarga = (Recarga) i.next();
                System.out.println("-----------------------------------------------------------------------------------");
                System.out.println(myRecarga.getPinTarjeta()+"\t"+myRecarga.getFechaRecarga()+"\t"+myRecarga.getValorRecarga()+"\t");
            }
         
    }
    
    public void agregarRecarga(String pinTarjeta, Date fechaRecarga, Integer valorRecarga) throws PreexistingEntityException, Exception
    {
    Recarga myRecarga = new Recarga();
    myRecarga.setPinTarjeta(pinTarjeta);
    myRecarga.setFechaRecarga(fechaRecarga);
    myRecarga.setValorRecarga(valorRecarga);
  
    
    DaoRecarga.create(myRecarga);
    }
    
    
    public void eliminarRecarga(String pinTarjeta) throws IllegalOrphanException, NonexistentEntityException
    {
        DaoRecarga.destroy(pinTarjeta);
    }
    
    
    public static void main (String atgs[]) throws PreexistingEntityException, Exception
    {
    ControladorBus con = new ControladorBus();
    ///.agregarBus("ryfvgujh456789", "sadad", "sadasd", "fdfd", "fgdfg", "saddfgdf", "dsfsdfsd", "sfsdfsd");
    ///con.consultarBus("123");
    ///con.editarBus("abc");
    ///con.eliminarBus("abc");
    }
    
    
}
