/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.EmpleadoJpaController;
import DAO.EstacionJpaController;
import DAO.RutaJpaController;
import DAO.SolicitudJpaController;
import DAO.UsuarioJpaController;
import Logica.Estacion;
import Logica.Solicitud;
import Logica.Usuario;
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
public class ControladorSolicitud 
{
    private FabricaObjetos mi_fabrica;
    EntityManager manager;
    SolicitudJpaController DaoSolicitud;
    UsuarioJpaController DaoUsuario;
    RutaJpaController DaoRuta;
    EstacionJpaController DaoEstacion;

    public ControladorSolicitud() 
    {
        mi_fabrica = new FabricaObjetos();   
        manager= mi_fabrica.crear().createEntityManager();
        //Daos
        DaoSolicitud = new SolicitudJpaController(mi_fabrica.getFactory());
        DaoRuta = new RutaJpaController(mi_fabrica.getFactory());
        DaoUsuario= new UsuarioJpaController(mi_fabrica.getFactory());       
        DaoEstacion= new EstacionJpaController(mi_fabrica.getFactory()); 
    }
    
    
    public void consultarSolicitud(String ticket)
    {
        Iterator i;
        //sirve para ejecutar consultas
        i = manager.createQuery("SELECT s FROM Solicitud s WHERE (s.ticket = '"+ticket+"')").getResultList().iterator();
//        System.out.print("id |\t Estado  |\t Nombre |\t Login\n");
        while(i.hasNext())
        {
            Solicitud solicitud = (Solicitud) i.next();
            System.out.print(solicitud.getTicket());
        }
    }    
    
    
    
    public void insertarSolicitud(String ticket,String motivo,String descripcion,String hora ,String fecha ,String estado,String id_usuario ,String nombre_estacion)
    {
        Solicitud solicitud = new Solicitud();
        Usuario usuario = DaoUsuario.findUsuario(id_usuario);
        Estacion estacion = DaoEstacion.findEstacion(nombre_estacion);
        Date fecha_con_formato=this.getDate(fecha);   
        
        
        solicitud.setTicket(ticket);
        solicitud.setDescripcion(descripcion);
        solicitud.setMotivo(motivo);
        solicitud.setHora(hora);
        solicitud.setFecha(fecha_con_formato);
        solicitud.setEstado(estado);
        solicitud.setIdUsuario(usuario);
        solicitud.setNombreEstacion(estacion); 
        
        try 
        {
            DaoSolicitud.create(solicitud);
        } 
        
        catch (Exception e) 
        {
        
        }   
        
    }
    
    public void modificarSolicitud(String ticket)
    {
        Solicitud solicitud_encontrada = DaoSolicitud.findSolicitud(ticket); 
        Usuario usuario= DaoUsuario.findUsuario("001");
        Estacion estacion=DaoEstacion.findEstacion("Pampalinda") ;
        
        solicitud_encontrada.setDescripcion("");
        solicitud_encontrada.setMotivo("");
        solicitud_encontrada.setHora("");
        solicitud_encontrada.setFecha(this.getDate("1999-02-03"));
        solicitud_encontrada.setEstado("");
        solicitud_encontrada.setIdUsuario(usuario);
        solicitud_encontrada.setNombreEstacion(estacion);
        
        try 
        {
            DaoSolicitud.edit(solicitud_encontrada);
        } 
        catch (Exception e) {
        }
        
        
    }
    
    public void eliminarSolicitud(String ticket)
    {
        Solicitud solicitud_encontrada = DaoSolicitud.findSolicitud(ticket);
        
        try 
        {
            DaoSolicitud.destroy(ticket);
        } 
        catch (Exception e) {
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
