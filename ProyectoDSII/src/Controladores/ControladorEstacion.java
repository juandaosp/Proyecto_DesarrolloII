/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import javax.swing.*;
import DAO.EstacionJpaController;
import DAO.EmpleadoJpaController;
import Logica.Estacion;
import Logica.Empleado;
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
import sun.org.mozilla.javascript.internal.ast.CatchClause;

public class ControladorEstacion {
    
    String nombreEstacion;
    String ubicacion;
    String estado;
    String idEmpleado;
    
    private FabricaObjetos mi_fabrica;    
     EstacionJpaController DaoEstacion;
     EmpleadoJpaController DaoEmpleado;
     EntityManager manager;
    
     public ControladorEstacion()
    {
       mi_fabrica = new FabricaObjetos();   
       manager= mi_fabrica.crear().createEntityManager();

       //se requiere ingresar por parametro una fabrica de objetos para manipular las entidades (crear, modificar..en fin)
       DaoEmpleado = new EmpleadoJpaController(mi_fabrica.getFactory());
       DaoEstacion = new EstacionJpaController(mi_fabrica.getFactory());
       
    }
     
     public void consultarEstacion(String nombre)
    {
         Iterator i;
            //sirve para ejecutar consultas
        
            i = manager.createQuery("SELECT e FROM Estacion e WHERE (e.nombreEstacion ='"+nombre+"')").getResultList().iterator();
            System.out.print("id |\t Nombre  |\t serial |\t proveedor\n");
            while(i.hasNext())
            {
                Estacion myEstacion = (Estacion) i.next();
                System.out.println("-----------------------------------------------------------------------------------");
                System.out.println(myEstacion.getNombreEstacion()+"\t"+myEstacion.getUbicacion()+"\t"+myEstacion.getEstado()+"\t"+myEstacion.getIdEmpleado().getId()+"\t");
            }
         
    }
    
    public void agregarEstacion(String nombreEstacion, String ubicacion, String estado, String idEmpleado) throws PreexistingEntityException, Exception
    {
    Empleado emp = new Empleado();
    emp = DaoEmpleado.findEmpleado(idEmpleado);
    Estacion myEstacion = new Estacion();
    myEstacion.setNombreEstacion(nombreEstacion);
    myEstacion.setUbicacion(ubicacion);
    myEstacion.setEstado(estado);
    myEstacion.setIdEmpleado(emp);
    
    DaoEstacion.create(myEstacion);
    }
    
    public void editarEstacion(String nombre)
    {
        Estacion estacionEncontrada = DaoEstacion.findEstacion(nombre);
        Empleado e = new Empleado();
        e.setId("123");
        estacionEncontrada.setUbicacion("call algo");
        estacionEncontrada.setEstado("Activo");
        estacionEncontrada.setIdEmpleado(e);
        
        
        try 
        {   
            DaoEstacion.edit(estacionEncontrada);
            
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
    
    public void eliminarEstacion(String nombre) throws IllegalOrphanException, NonexistentEntityException
    {
        DaoEstacion.destroy(nombre);
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
    
    
    public static void main (String atgs[]) throws PreexistingEntityException, Exception
    {
    ControladorEstacion con = new ControladorEstacion();
    
    ///con.agregarEstacion("hola", "adasytughij", "saadafdghjkl", "123");
    ///con.consultarEstacion("saads");
    ///con.editarEstacion("hola");
    con.eliminarEstacion("hola");
    
    }
}
