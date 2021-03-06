/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.EmpleadoJpaController;
import Logica.Empleado;
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
public class ControladorEmpleados 
{
    private FabricaObjetos mi_fabrica;
    EntityManager manager;
    EmpleadoJpaController DaoEmpleado;
      
    
    public ControladorEmpleados() {
    
       mi_fabrica = new FabricaObjetos();   
       manager= mi_fabrica.crear().createEntityManager();
       
       DaoEmpleado = new EmpleadoJpaController(mi_fabrica.getFactory());
       
    }
    
    public void consultarEmpleado(String idEmpleado){
    
        Iterator i;
        //sirve para ejecutar consultas
        i = manager.createQuery("SELECT e FROM Empleado e WHERE (e.id = '"+idEmpleado+"')").getResultList().iterator();
//        System.out.print("id |\t Estado  |\t Nombre |\t Login\n");
        while(i.hasNext())
        {
            Empleado empleado = (Empleado) i.next();
            System.out.print(empleado.getNombre());
        }
    }    
    
    public void insertarEmpleado(String id,String nombre,String login,String contrasena,String eps,String salario,String licencia,char genero,String estado_civil,String fecha,String cargo,String estado)
    {
        Date fecha_con_formato=this.getDate(fecha);        
        Empleado empleado = new Empleado(id, nombre, login, contrasena, eps, salario, licencia, genero, estado_civil,fecha_con_formato, cargo, estado);
        
        try 
        {
            DaoEmpleado.create(empleado);
        } 
        catch (Exception e) 
        {
        
        }
         
    }
    
    public void modificarEmpleado(String idEmpleado)
    {
        Empleado empleado_encontrado;
        empleado_encontrado=DaoEmpleado.findEmpleado(idEmpleado); 
        
            String nombre="Dash";
            String login="10002";
            String contrasena="1212";
            String eps="12122";
            String salario="1212";
            String licencia="1212";
            char genero='1';
            String estado_civil="12121";
            String fecha="10-08-1925";
            String cargo="12121";
            String estado="12121";
            
            empleado_encontrado.setNombre(nombre);
            empleado_encontrado.setLogin(login);
            empleado_encontrado.setContrasena(contrasena);
            empleado_encontrado.setEps(eps);
            empleado_encontrado.setSalario(salario);
            empleado_encontrado.setLicencia(licencia);
            empleado_encontrado.setGenero(genero);
            empleado_encontrado.setEstadoCivil(estado_civil);
            empleado_encontrado.setFechaNac(this.getDate(fecha));
            empleado_encontrado.setCargo(cargo);
            empleado_encontrado.setEstado(estado);
        
            System.out.print(estado);
        
        
        try 
        {
           
            DaoEmpleado.edit(empleado_encontrado);        
          
        } 
        catch(Exception e)
        {
            e.printStackTrace();
        }
        try 
        {
//            DaoEmpleado.edit(empleado_encontrado);
        } 
        catch (Exception e) {
        }
        
    }
    
    public void eliminarEmpleado(String idEmpleado)
    {
        Empleado empleado_encontrado;
        empleado_encontrado=DaoEmpleado.findEmpleado(idEmpleado);
        try 
        {
             DaoEmpleado.destroy(empleado_encontrado.getId());
             
        } catch (Exception e) 
        {
            e.printStackTrace();
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
    
    public static void main(String[] args)
    {
        ControladorEmpleados controladorEmpleados = new ControladorEmpleados();
        controladorEmpleados.consultarEmpleado("001");
    }

   
    
}
