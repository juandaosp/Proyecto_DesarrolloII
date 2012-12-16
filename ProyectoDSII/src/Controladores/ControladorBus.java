
package Controladores;

import javax.swing.*;
import DAO.BusJpaController;
import Logica.Bus;
import javax.persistence.EntityManager;
import Controladores.FabricaObjetos;
import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import java.util.Iterator;


public class ControladorBus {
    
     String matricula;
     String estado;
     String ano;
     String fabricante;
     String capacidad;
     String cilindrinaje;
     String chasis;
     String tipoBus;
     
     private FabricaObjetos mi_fabrica;    
     BusJpaController DaoBus;
     EntityManager manager;
     

   
    public ControladorBus()
    {
       mi_fabrica = new FabricaObjetos();   
       manager= mi_fabrica.crear().createEntityManager();

       //se requiere ingresar por parametro una fabrica de objetos para manipular las entidades (crear, modificar..en fin)
       DaoBus = new BusJpaController(mi_fabrica.getFactory());
    }
    
    public void consultarBus(String matricula)
    {
         Iterator i;
            //sirve para ejecutar consultas
        
            i = manager.createQuery("SELECT b FROM Bus b WHERE (b.matricula ='"+matricula+"')").getResultList().iterator();
            System.out.print("id |\t Nombre  |\t serial |\t proveedor\n");
            while(i.hasNext())
            {
                Bus mybus = (Bus) i.next();
                System.out.println("-----------------------------------------------------------------------------------");
                System.out.println(mybus.getMatricula()+"\t"+mybus.getEstado()+"\t"+mybus.getAno()+"\t"+mybus.getFabricante()+"\t"+mybus.getCapacidad()+"\t"+mybus.getCilindrinaje()+"\t"+mybus.getChasis()+"\t"+mybus.getTipoBus()+"\t");
            }
         
    }
    
    public void agregarBus(String matricula, String estado, String ano, String fabricante, String capacidad, String cilindrinaje, String chasis, String tipoBus ) throws PreexistingEntityException, Exception
    {
        Bus mybus = new Bus();
        mybus.setMatricula(matricula);
        mybus.setEstado(estado);
        mybus.setAno(ano);
        mybus.setFabricante(fabricante);
        mybus.setCapacidad(capacidad);
        mybus.setCilindrinaje(cilindrinaje);
        mybus.setChasis(chasis);
        mybus.setTipoBus(tipoBus);

        DaoBus.create(mybus);
    }
    
    public void editarBus(String matricula,String estado,String ano,String fabricante,String capacidad,String cilindraje,String chasis,String tipo)
    {
        Bus busEncontrado = DaoBus.findBus(matricula);
        
        busEncontrado.setEstado(estado);
        busEncontrado.setAno(ano);
        busEncontrado.setFabricante(fabricante);
        busEncontrado.setCapacidad(capacidad);
        busEncontrado.setCilindrinaje(cilindraje);
        busEncontrado.setChasis(chasis);
        busEncontrado.setTipoBus(tipo);
        
        try 
        {   
            DaoBus.edit(busEncontrado);
            
        }
        catch (NonexistentEntityException ex) 
        {
            System.out.println(ex.getMessage());
        }
        catch (Exception ex) 
        {
            System.out.println(ex.getMessage());
        }
    }     
    
    public void eliminarBus(String matricula) throws IllegalOrphanException, NonexistentEntityException
    {
        DaoBus.destroy(matricula);
    }
    
    
    
}
