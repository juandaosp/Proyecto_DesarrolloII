/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Logica.Empleado;
import Logica.Estacion;
import Logica.Ruta;
import java.util.ArrayList;
import java.util.Collection;
import Logica.TarjetaIngresaEstacion;
import Logica.Tarjeta;
import Logica.Solicitud;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dash
 */
public class EstacionJpaController implements Serializable {

    public EstacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estacion estacion) throws PreexistingEntityException, Exception {
        if (estacion.getRutaCollection() == null) {
            estacion.setRutaCollection(new ArrayList<Ruta>());
        }
        if (estacion.getTarjetaIngresaEstacionCollection() == null) {
            estacion.setTarjetaIngresaEstacionCollection(new ArrayList<TarjetaIngresaEstacion>());
        }
        if (estacion.getTarjetaIngresaEstacionCollection1() == null) {
            estacion.setTarjetaIngresaEstacionCollection1(new ArrayList<TarjetaIngresaEstacion>());
        }
        if (estacion.getTarjetaCollection() == null) {
            estacion.setTarjetaCollection(new ArrayList<Tarjeta>());
        }
        if (estacion.getSolicitudCollection() == null) {
            estacion.setSolicitudCollection(new ArrayList<Solicitud>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado idEmpleado = estacion.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado = em.getReference(idEmpleado.getClass(), idEmpleado.getId());
                estacion.setIdEmpleado(idEmpleado);
            }
            Collection<Ruta> attachedRutaCollection = new ArrayList<Ruta>();
            for (Ruta rutaCollectionRutaToAttach : estacion.getRutaCollection()) {
                rutaCollectionRutaToAttach = em.getReference(rutaCollectionRutaToAttach.getClass(), rutaCollectionRutaToAttach.getNombreRuta());
                attachedRutaCollection.add(rutaCollectionRutaToAttach);
            }
            estacion.setRutaCollection(attachedRutaCollection);
            Collection<TarjetaIngresaEstacion> attachedTarjetaIngresaEstacionCollection = new ArrayList<TarjetaIngresaEstacion>();
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionCollectionTarjetaIngresaEstacionToAttach : estacion.getTarjetaIngresaEstacionCollection()) {
                tarjetaIngresaEstacionCollectionTarjetaIngresaEstacionToAttach = em.getReference(tarjetaIngresaEstacionCollectionTarjetaIngresaEstacionToAttach.getClass(), tarjetaIngresaEstacionCollectionTarjetaIngresaEstacionToAttach.getTarjetaIngresaEstacionPK());
                attachedTarjetaIngresaEstacionCollection.add(tarjetaIngresaEstacionCollectionTarjetaIngresaEstacionToAttach);
            }
            estacion.setTarjetaIngresaEstacionCollection(attachedTarjetaIngresaEstacionCollection);
            Collection<TarjetaIngresaEstacion> attachedTarjetaIngresaEstacionCollection1 = new ArrayList<TarjetaIngresaEstacion>();
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionCollection1TarjetaIngresaEstacionToAttach : estacion.getTarjetaIngresaEstacionCollection1()) {
                tarjetaIngresaEstacionCollection1TarjetaIngresaEstacionToAttach = em.getReference(tarjetaIngresaEstacionCollection1TarjetaIngresaEstacionToAttach.getClass(), tarjetaIngresaEstacionCollection1TarjetaIngresaEstacionToAttach.getTarjetaIngresaEstacionPK());
                attachedTarjetaIngresaEstacionCollection1.add(tarjetaIngresaEstacionCollection1TarjetaIngresaEstacionToAttach);
            }
            estacion.setTarjetaIngresaEstacionCollection1(attachedTarjetaIngresaEstacionCollection1);
            Collection<Tarjeta> attachedTarjetaCollection = new ArrayList<Tarjeta>();
            for (Tarjeta tarjetaCollectionTarjetaToAttach : estacion.getTarjetaCollection()) {
                tarjetaCollectionTarjetaToAttach = em.getReference(tarjetaCollectionTarjetaToAttach.getClass(), tarjetaCollectionTarjetaToAttach.getPin());
                attachedTarjetaCollection.add(tarjetaCollectionTarjetaToAttach);
            }
            estacion.setTarjetaCollection(attachedTarjetaCollection);
            Collection<Solicitud> attachedSolicitudCollection = new ArrayList<Solicitud>();
            for (Solicitud solicitudCollectionSolicitudToAttach : estacion.getSolicitudCollection()) {
                solicitudCollectionSolicitudToAttach = em.getReference(solicitudCollectionSolicitudToAttach.getClass(), solicitudCollectionSolicitudToAttach.getTicket());
                attachedSolicitudCollection.add(solicitudCollectionSolicitudToAttach);
            }
            estacion.setSolicitudCollection(attachedSolicitudCollection);
            em.persist(estacion);
            if (idEmpleado != null) {
                idEmpleado.getEstacionCollection().add(estacion);
                idEmpleado = em.merge(idEmpleado);
            }
            for (Ruta rutaCollectionRuta : estacion.getRutaCollection()) {
                rutaCollectionRuta.getEstacionCollection().add(estacion);
                rutaCollectionRuta = em.merge(rutaCollectionRuta);
            }
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionCollectionTarjetaIngresaEstacion : estacion.getTarjetaIngresaEstacionCollection()) {
                Estacion oldEstacionOfTarjetaIngresaEstacionCollectionTarjetaIngresaEstacion = tarjetaIngresaEstacionCollectionTarjetaIngresaEstacion.getEstacion();
                tarjetaIngresaEstacionCollectionTarjetaIngresaEstacion.setEstacion(estacion);
                tarjetaIngresaEstacionCollectionTarjetaIngresaEstacion = em.merge(tarjetaIngresaEstacionCollectionTarjetaIngresaEstacion);
                if (oldEstacionOfTarjetaIngresaEstacionCollectionTarjetaIngresaEstacion != null) {
                    oldEstacionOfTarjetaIngresaEstacionCollectionTarjetaIngresaEstacion.getTarjetaIngresaEstacionCollection().remove(tarjetaIngresaEstacionCollectionTarjetaIngresaEstacion);
                    oldEstacionOfTarjetaIngresaEstacionCollectionTarjetaIngresaEstacion = em.merge(oldEstacionOfTarjetaIngresaEstacionCollectionTarjetaIngresaEstacion);
                }
            }
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionCollection1TarjetaIngresaEstacion : estacion.getTarjetaIngresaEstacionCollection1()) {
                Estacion oldEstacion1OfTarjetaIngresaEstacionCollection1TarjetaIngresaEstacion = tarjetaIngresaEstacionCollection1TarjetaIngresaEstacion.getEstacion1();
                tarjetaIngresaEstacionCollection1TarjetaIngresaEstacion.setEstacion1(estacion);
                tarjetaIngresaEstacionCollection1TarjetaIngresaEstacion = em.merge(tarjetaIngresaEstacionCollection1TarjetaIngresaEstacion);
                if (oldEstacion1OfTarjetaIngresaEstacionCollection1TarjetaIngresaEstacion != null) {
                    oldEstacion1OfTarjetaIngresaEstacionCollection1TarjetaIngresaEstacion.getTarjetaIngresaEstacionCollection1().remove(tarjetaIngresaEstacionCollection1TarjetaIngresaEstacion);
                    oldEstacion1OfTarjetaIngresaEstacionCollection1TarjetaIngresaEstacion = em.merge(oldEstacion1OfTarjetaIngresaEstacionCollection1TarjetaIngresaEstacion);
                }
            }
            for (Tarjeta tarjetaCollectionTarjeta : estacion.getTarjetaCollection()) {
                Estacion oldNombreEstacionOfTarjetaCollectionTarjeta = tarjetaCollectionTarjeta.getNombreEstacion();
                tarjetaCollectionTarjeta.setNombreEstacion(estacion);
                tarjetaCollectionTarjeta = em.merge(tarjetaCollectionTarjeta);
                if (oldNombreEstacionOfTarjetaCollectionTarjeta != null) {
                    oldNombreEstacionOfTarjetaCollectionTarjeta.getTarjetaCollection().remove(tarjetaCollectionTarjeta);
                    oldNombreEstacionOfTarjetaCollectionTarjeta = em.merge(oldNombreEstacionOfTarjetaCollectionTarjeta);
                }
            }
            for (Solicitud solicitudCollectionSolicitud : estacion.getSolicitudCollection()) {
                Estacion oldNombreEstacionOfSolicitudCollectionSolicitud = solicitudCollectionSolicitud.getNombreEstacion();
                solicitudCollectionSolicitud.setNombreEstacion(estacion);
                solicitudCollectionSolicitud = em.merge(solicitudCollectionSolicitud);
                if (oldNombreEstacionOfSolicitudCollectionSolicitud != null) {
                    oldNombreEstacionOfSolicitudCollectionSolicitud.getSolicitudCollection().remove(solicitudCollectionSolicitud);
                    oldNombreEstacionOfSolicitudCollectionSolicitud = em.merge(oldNombreEstacionOfSolicitudCollectionSolicitud);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstacion(estacion.getNombreEstacion()) != null) {
                throw new PreexistingEntityException("Estacion " + estacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estacion estacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estacion persistentEstacion = em.find(Estacion.class, estacion.getNombreEstacion());
            Empleado idEmpleadoOld = persistentEstacion.getIdEmpleado();
            Empleado idEmpleadoNew = estacion.getIdEmpleado();
            Collection<Ruta> rutaCollectionOld = persistentEstacion.getRutaCollection();
            Collection<Ruta> rutaCollectionNew = estacion.getRutaCollection();
            Collection<TarjetaIngresaEstacion> tarjetaIngresaEstacionCollectionOld = persistentEstacion.getTarjetaIngresaEstacionCollection();
            Collection<TarjetaIngresaEstacion> tarjetaIngresaEstacionCollectionNew = estacion.getTarjetaIngresaEstacionCollection();
            Collection<TarjetaIngresaEstacion> tarjetaIngresaEstacionCollection1Old = persistentEstacion.getTarjetaIngresaEstacionCollection1();
            Collection<TarjetaIngresaEstacion> tarjetaIngresaEstacionCollection1New = estacion.getTarjetaIngresaEstacionCollection1();
            Collection<Tarjeta> tarjetaCollectionOld = persistentEstacion.getTarjetaCollection();
            Collection<Tarjeta> tarjetaCollectionNew = estacion.getTarjetaCollection();
            Collection<Solicitud> solicitudCollectionOld = persistentEstacion.getSolicitudCollection();
            Collection<Solicitud> solicitudCollectionNew = estacion.getSolicitudCollection();
            List<String> illegalOrphanMessages = null;
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionCollectionOldTarjetaIngresaEstacion : tarjetaIngresaEstacionCollectionOld) {
                if (!tarjetaIngresaEstacionCollectionNew.contains(tarjetaIngresaEstacionCollectionOldTarjetaIngresaEstacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TarjetaIngresaEstacion " + tarjetaIngresaEstacionCollectionOldTarjetaIngresaEstacion + " since its estacion field is not nullable.");
                }
            }
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionCollection1OldTarjetaIngresaEstacion : tarjetaIngresaEstacionCollection1Old) {
                if (!tarjetaIngresaEstacionCollection1New.contains(tarjetaIngresaEstacionCollection1OldTarjetaIngresaEstacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TarjetaIngresaEstacion " + tarjetaIngresaEstacionCollection1OldTarjetaIngresaEstacion + " since its estacion1 field is not nullable.");
                }
            }
            for (Tarjeta tarjetaCollectionOldTarjeta : tarjetaCollectionOld) {
                if (!tarjetaCollectionNew.contains(tarjetaCollectionOldTarjeta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tarjeta " + tarjetaCollectionOldTarjeta + " since its nombreEstacion field is not nullable.");
                }
            }
            for (Solicitud solicitudCollectionOldSolicitud : solicitudCollectionOld) {
                if (!solicitudCollectionNew.contains(solicitudCollectionOldSolicitud)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Solicitud " + solicitudCollectionOldSolicitud + " since its nombreEstacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idEmpleadoNew != null) {
                idEmpleadoNew = em.getReference(idEmpleadoNew.getClass(), idEmpleadoNew.getId());
                estacion.setIdEmpleado(idEmpleadoNew);
            }
            Collection<Ruta> attachedRutaCollectionNew = new ArrayList<Ruta>();
            for (Ruta rutaCollectionNewRutaToAttach : rutaCollectionNew) {
                rutaCollectionNewRutaToAttach = em.getReference(rutaCollectionNewRutaToAttach.getClass(), rutaCollectionNewRutaToAttach.getNombreRuta());
                attachedRutaCollectionNew.add(rutaCollectionNewRutaToAttach);
            }
            rutaCollectionNew = attachedRutaCollectionNew;
            estacion.setRutaCollection(rutaCollectionNew);
            Collection<TarjetaIngresaEstacion> attachedTarjetaIngresaEstacionCollectionNew = new ArrayList<TarjetaIngresaEstacion>();
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacionToAttach : tarjetaIngresaEstacionCollectionNew) {
                tarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacionToAttach = em.getReference(tarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacionToAttach.getClass(), tarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacionToAttach.getTarjetaIngresaEstacionPK());
                attachedTarjetaIngresaEstacionCollectionNew.add(tarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacionToAttach);
            }
            tarjetaIngresaEstacionCollectionNew = attachedTarjetaIngresaEstacionCollectionNew;
            estacion.setTarjetaIngresaEstacionCollection(tarjetaIngresaEstacionCollectionNew);
            Collection<TarjetaIngresaEstacion> attachedTarjetaIngresaEstacionCollection1New = new ArrayList<TarjetaIngresaEstacion>();
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionCollection1NewTarjetaIngresaEstacionToAttach : tarjetaIngresaEstacionCollection1New) {
                tarjetaIngresaEstacionCollection1NewTarjetaIngresaEstacionToAttach = em.getReference(tarjetaIngresaEstacionCollection1NewTarjetaIngresaEstacionToAttach.getClass(), tarjetaIngresaEstacionCollection1NewTarjetaIngresaEstacionToAttach.getTarjetaIngresaEstacionPK());
                attachedTarjetaIngresaEstacionCollection1New.add(tarjetaIngresaEstacionCollection1NewTarjetaIngresaEstacionToAttach);
            }
            tarjetaIngresaEstacionCollection1New = attachedTarjetaIngresaEstacionCollection1New;
            estacion.setTarjetaIngresaEstacionCollection1(tarjetaIngresaEstacionCollection1New);
            Collection<Tarjeta> attachedTarjetaCollectionNew = new ArrayList<Tarjeta>();
            for (Tarjeta tarjetaCollectionNewTarjetaToAttach : tarjetaCollectionNew) {
                tarjetaCollectionNewTarjetaToAttach = em.getReference(tarjetaCollectionNewTarjetaToAttach.getClass(), tarjetaCollectionNewTarjetaToAttach.getPin());
                attachedTarjetaCollectionNew.add(tarjetaCollectionNewTarjetaToAttach);
            }
            tarjetaCollectionNew = attachedTarjetaCollectionNew;
            estacion.setTarjetaCollection(tarjetaCollectionNew);
            Collection<Solicitud> attachedSolicitudCollectionNew = new ArrayList<Solicitud>();
            for (Solicitud solicitudCollectionNewSolicitudToAttach : solicitudCollectionNew) {
                solicitudCollectionNewSolicitudToAttach = em.getReference(solicitudCollectionNewSolicitudToAttach.getClass(), solicitudCollectionNewSolicitudToAttach.getTicket());
                attachedSolicitudCollectionNew.add(solicitudCollectionNewSolicitudToAttach);
            }
            solicitudCollectionNew = attachedSolicitudCollectionNew;
            estacion.setSolicitudCollection(solicitudCollectionNew);
            estacion = em.merge(estacion);
            if (idEmpleadoOld != null && !idEmpleadoOld.equals(idEmpleadoNew)) {
                idEmpleadoOld.getEstacionCollection().remove(estacion);
                idEmpleadoOld = em.merge(idEmpleadoOld);
            }
            if (idEmpleadoNew != null && !idEmpleadoNew.equals(idEmpleadoOld)) {
                idEmpleadoNew.getEstacionCollection().add(estacion);
                idEmpleadoNew = em.merge(idEmpleadoNew);
            }
            for (Ruta rutaCollectionOldRuta : rutaCollectionOld) {
                if (!rutaCollectionNew.contains(rutaCollectionOldRuta)) {
                    rutaCollectionOldRuta.getEstacionCollection().remove(estacion);
                    rutaCollectionOldRuta = em.merge(rutaCollectionOldRuta);
                }
            }
            for (Ruta rutaCollectionNewRuta : rutaCollectionNew) {
                if (!rutaCollectionOld.contains(rutaCollectionNewRuta)) {
                    rutaCollectionNewRuta.getEstacionCollection().add(estacion);
                    rutaCollectionNewRuta = em.merge(rutaCollectionNewRuta);
                }
            }
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacion : tarjetaIngresaEstacionCollectionNew) {
                if (!tarjetaIngresaEstacionCollectionOld.contains(tarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacion)) {
                    Estacion oldEstacionOfTarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacion = tarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacion.getEstacion();
                    tarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacion.setEstacion(estacion);
                    tarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacion = em.merge(tarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacion);
                    if (oldEstacionOfTarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacion != null && !oldEstacionOfTarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacion.equals(estacion)) {
                        oldEstacionOfTarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacion.getTarjetaIngresaEstacionCollection().remove(tarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacion);
                        oldEstacionOfTarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacion = em.merge(oldEstacionOfTarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacion);
                    }
                }
            }
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionCollection1NewTarjetaIngresaEstacion : tarjetaIngresaEstacionCollection1New) {
                if (!tarjetaIngresaEstacionCollection1Old.contains(tarjetaIngresaEstacionCollection1NewTarjetaIngresaEstacion)) {
                    Estacion oldEstacion1OfTarjetaIngresaEstacionCollection1NewTarjetaIngresaEstacion = tarjetaIngresaEstacionCollection1NewTarjetaIngresaEstacion.getEstacion1();
                    tarjetaIngresaEstacionCollection1NewTarjetaIngresaEstacion.setEstacion1(estacion);
                    tarjetaIngresaEstacionCollection1NewTarjetaIngresaEstacion = em.merge(tarjetaIngresaEstacionCollection1NewTarjetaIngresaEstacion);
                    if (oldEstacion1OfTarjetaIngresaEstacionCollection1NewTarjetaIngresaEstacion != null && !oldEstacion1OfTarjetaIngresaEstacionCollection1NewTarjetaIngresaEstacion.equals(estacion)) {
                        oldEstacion1OfTarjetaIngresaEstacionCollection1NewTarjetaIngresaEstacion.getTarjetaIngresaEstacionCollection1().remove(tarjetaIngresaEstacionCollection1NewTarjetaIngresaEstacion);
                        oldEstacion1OfTarjetaIngresaEstacionCollection1NewTarjetaIngresaEstacion = em.merge(oldEstacion1OfTarjetaIngresaEstacionCollection1NewTarjetaIngresaEstacion);
                    }
                }
            }
            for (Tarjeta tarjetaCollectionNewTarjeta : tarjetaCollectionNew) {
                if (!tarjetaCollectionOld.contains(tarjetaCollectionNewTarjeta)) {
                    Estacion oldNombreEstacionOfTarjetaCollectionNewTarjeta = tarjetaCollectionNewTarjeta.getNombreEstacion();
                    tarjetaCollectionNewTarjeta.setNombreEstacion(estacion);
                    tarjetaCollectionNewTarjeta = em.merge(tarjetaCollectionNewTarjeta);
                    if (oldNombreEstacionOfTarjetaCollectionNewTarjeta != null && !oldNombreEstacionOfTarjetaCollectionNewTarjeta.equals(estacion)) {
                        oldNombreEstacionOfTarjetaCollectionNewTarjeta.getTarjetaCollection().remove(tarjetaCollectionNewTarjeta);
                        oldNombreEstacionOfTarjetaCollectionNewTarjeta = em.merge(oldNombreEstacionOfTarjetaCollectionNewTarjeta);
                    }
                }
            }
            for (Solicitud solicitudCollectionNewSolicitud : solicitudCollectionNew) {
                if (!solicitudCollectionOld.contains(solicitudCollectionNewSolicitud)) {
                    Estacion oldNombreEstacionOfSolicitudCollectionNewSolicitud = solicitudCollectionNewSolicitud.getNombreEstacion();
                    solicitudCollectionNewSolicitud.setNombreEstacion(estacion);
                    solicitudCollectionNewSolicitud = em.merge(solicitudCollectionNewSolicitud);
                    if (oldNombreEstacionOfSolicitudCollectionNewSolicitud != null && !oldNombreEstacionOfSolicitudCollectionNewSolicitud.equals(estacion)) {
                        oldNombreEstacionOfSolicitudCollectionNewSolicitud.getSolicitudCollection().remove(solicitudCollectionNewSolicitud);
                        oldNombreEstacionOfSolicitudCollectionNewSolicitud = em.merge(oldNombreEstacionOfSolicitudCollectionNewSolicitud);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = estacion.getNombreEstacion();
                if (findEstacion(id) == null) {
                    throw new NonexistentEntityException("The estacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estacion estacion;
            try {
                estacion = em.getReference(Estacion.class, id);
                estacion.getNombreEstacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TarjetaIngresaEstacion> tarjetaIngresaEstacionCollectionOrphanCheck = estacion.getTarjetaIngresaEstacionCollection();
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionCollectionOrphanCheckTarjetaIngresaEstacion : tarjetaIngresaEstacionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estacion (" + estacion + ") cannot be destroyed since the TarjetaIngresaEstacion " + tarjetaIngresaEstacionCollectionOrphanCheckTarjetaIngresaEstacion + " in its tarjetaIngresaEstacionCollection field has a non-nullable estacion field.");
            }
            Collection<TarjetaIngresaEstacion> tarjetaIngresaEstacionCollection1OrphanCheck = estacion.getTarjetaIngresaEstacionCollection1();
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionCollection1OrphanCheckTarjetaIngresaEstacion : tarjetaIngresaEstacionCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estacion (" + estacion + ") cannot be destroyed since the TarjetaIngresaEstacion " + tarjetaIngresaEstacionCollection1OrphanCheckTarjetaIngresaEstacion + " in its tarjetaIngresaEstacionCollection1 field has a non-nullable estacion1 field.");
            }
            Collection<Tarjeta> tarjetaCollectionOrphanCheck = estacion.getTarjetaCollection();
            for (Tarjeta tarjetaCollectionOrphanCheckTarjeta : tarjetaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estacion (" + estacion + ") cannot be destroyed since the Tarjeta " + tarjetaCollectionOrphanCheckTarjeta + " in its tarjetaCollection field has a non-nullable nombreEstacion field.");
            }
            Collection<Solicitud> solicitudCollectionOrphanCheck = estacion.getSolicitudCollection();
            for (Solicitud solicitudCollectionOrphanCheckSolicitud : solicitudCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estacion (" + estacion + ") cannot be destroyed since the Solicitud " + solicitudCollectionOrphanCheckSolicitud + " in its solicitudCollection field has a non-nullable nombreEstacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Empleado idEmpleado = estacion.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado.getEstacionCollection().remove(estacion);
                idEmpleado = em.merge(idEmpleado);
            }
            Collection<Ruta> rutaCollection = estacion.getRutaCollection();
            for (Ruta rutaCollectionRuta : rutaCollection) {
                rutaCollectionRuta.getEstacionCollection().remove(estacion);
                rutaCollectionRuta = em.merge(rutaCollectionRuta);
            }
            em.remove(estacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estacion> findEstacionEntities() {
        return findEstacionEntities(true, -1, -1);
    }

    public List<Estacion> findEstacionEntities(int maxResults, int firstResult) {
        return findEstacionEntities(false, maxResults, firstResult);
    }

    private List<Estacion> findEstacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estacion.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Estacion findEstacion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estacion> rt = cq.from(Estacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
