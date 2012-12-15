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
import java.util.List;
import Logica.TarjetaIngresaEstacion;
import Logica.Tarjeta;
import Logica.Solicitud;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
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
        if (estacion.getRutaList() == null) {
            estacion.setRutaList(new ArrayList<Ruta>());
        }
        if (estacion.getTarjetaIngresaEstacionList() == null) {
            estacion.setTarjetaIngresaEstacionList(new ArrayList<TarjetaIngresaEstacion>());
        }
        if (estacion.getTarjetaIngresaEstacionList1() == null) {
            estacion.setTarjetaIngresaEstacionList1(new ArrayList<TarjetaIngresaEstacion>());
        }
        if (estacion.getTarjetaList() == null) {
            estacion.setTarjetaList(new ArrayList<Tarjeta>());
        }
        if (estacion.getSolicitudList() == null) {
            estacion.setSolicitudList(new ArrayList<Solicitud>());
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
            List<Ruta> attachedRutaList = new ArrayList<Ruta>();
            for (Ruta rutaListRutaToAttach : estacion.getRutaList()) {
                rutaListRutaToAttach = em.getReference(rutaListRutaToAttach.getClass(), rutaListRutaToAttach.getNombreRuta());
                attachedRutaList.add(rutaListRutaToAttach);
            }
            estacion.setRutaList(attachedRutaList);
            List<TarjetaIngresaEstacion> attachedTarjetaIngresaEstacionList = new ArrayList<TarjetaIngresaEstacion>();
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionListTarjetaIngresaEstacionToAttach : estacion.getTarjetaIngresaEstacionList()) {
                tarjetaIngresaEstacionListTarjetaIngresaEstacionToAttach = em.getReference(tarjetaIngresaEstacionListTarjetaIngresaEstacionToAttach.getClass(), tarjetaIngresaEstacionListTarjetaIngresaEstacionToAttach.getTarjetaIngresaEstacionPK());
                attachedTarjetaIngresaEstacionList.add(tarjetaIngresaEstacionListTarjetaIngresaEstacionToAttach);
            }
            estacion.setTarjetaIngresaEstacionList(attachedTarjetaIngresaEstacionList);
            List<TarjetaIngresaEstacion> attachedTarjetaIngresaEstacionList1 = new ArrayList<TarjetaIngresaEstacion>();
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionList1TarjetaIngresaEstacionToAttach : estacion.getTarjetaIngresaEstacionList1()) {
                tarjetaIngresaEstacionList1TarjetaIngresaEstacionToAttach = em.getReference(tarjetaIngresaEstacionList1TarjetaIngresaEstacionToAttach.getClass(), tarjetaIngresaEstacionList1TarjetaIngresaEstacionToAttach.getTarjetaIngresaEstacionPK());
                attachedTarjetaIngresaEstacionList1.add(tarjetaIngresaEstacionList1TarjetaIngresaEstacionToAttach);
            }
            estacion.setTarjetaIngresaEstacionList1(attachedTarjetaIngresaEstacionList1);
            List<Tarjeta> attachedTarjetaList = new ArrayList<Tarjeta>();
            for (Tarjeta tarjetaListTarjetaToAttach : estacion.getTarjetaList()) {
                tarjetaListTarjetaToAttach = em.getReference(tarjetaListTarjetaToAttach.getClass(), tarjetaListTarjetaToAttach.getPin());
                attachedTarjetaList.add(tarjetaListTarjetaToAttach);
            }
            estacion.setTarjetaList(attachedTarjetaList);
            List<Solicitud> attachedSolicitudList = new ArrayList<Solicitud>();
            for (Solicitud solicitudListSolicitudToAttach : estacion.getSolicitudList()) {
                solicitudListSolicitudToAttach = em.getReference(solicitudListSolicitudToAttach.getClass(), solicitudListSolicitudToAttach.getTicket());
                attachedSolicitudList.add(solicitudListSolicitudToAttach);
            }
            estacion.setSolicitudList(attachedSolicitudList);
            em.persist(estacion);
            if (idEmpleado != null) {
                idEmpleado.getEstacionList().add(estacion);
                idEmpleado = em.merge(idEmpleado);
            }
            for (Ruta rutaListRuta : estacion.getRutaList()) {
                rutaListRuta.getEstacionList().add(estacion);
                rutaListRuta = em.merge(rutaListRuta);
            }
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionListTarjetaIngresaEstacion : estacion.getTarjetaIngresaEstacionList()) {
                Estacion oldEstacionOfTarjetaIngresaEstacionListTarjetaIngresaEstacion = tarjetaIngresaEstacionListTarjetaIngresaEstacion.getEstacion();
                tarjetaIngresaEstacionListTarjetaIngresaEstacion.setEstacion(estacion);
                tarjetaIngresaEstacionListTarjetaIngresaEstacion = em.merge(tarjetaIngresaEstacionListTarjetaIngresaEstacion);
                if (oldEstacionOfTarjetaIngresaEstacionListTarjetaIngresaEstacion != null) {
                    oldEstacionOfTarjetaIngresaEstacionListTarjetaIngresaEstacion.getTarjetaIngresaEstacionList().remove(tarjetaIngresaEstacionListTarjetaIngresaEstacion);
                    oldEstacionOfTarjetaIngresaEstacionListTarjetaIngresaEstacion = em.merge(oldEstacionOfTarjetaIngresaEstacionListTarjetaIngresaEstacion);
                }
            }
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionList1TarjetaIngresaEstacion : estacion.getTarjetaIngresaEstacionList1()) {
                Estacion oldEstacion1OfTarjetaIngresaEstacionList1TarjetaIngresaEstacion = tarjetaIngresaEstacionList1TarjetaIngresaEstacion.getEstacion1();
                tarjetaIngresaEstacionList1TarjetaIngresaEstacion.setEstacion1(estacion);
                tarjetaIngresaEstacionList1TarjetaIngresaEstacion = em.merge(tarjetaIngresaEstacionList1TarjetaIngresaEstacion);
                if (oldEstacion1OfTarjetaIngresaEstacionList1TarjetaIngresaEstacion != null) {
                    oldEstacion1OfTarjetaIngresaEstacionList1TarjetaIngresaEstacion.getTarjetaIngresaEstacionList1().remove(tarjetaIngresaEstacionList1TarjetaIngresaEstacion);
                    oldEstacion1OfTarjetaIngresaEstacionList1TarjetaIngresaEstacion = em.merge(oldEstacion1OfTarjetaIngresaEstacionList1TarjetaIngresaEstacion);
                }
            }
            for (Tarjeta tarjetaListTarjeta : estacion.getTarjetaList()) {
                Estacion oldNombreEstacionOfTarjetaListTarjeta = tarjetaListTarjeta.getNombreEstacion();
                tarjetaListTarjeta.setNombreEstacion(estacion);
                tarjetaListTarjeta = em.merge(tarjetaListTarjeta);
                if (oldNombreEstacionOfTarjetaListTarjeta != null) {
                    oldNombreEstacionOfTarjetaListTarjeta.getTarjetaList().remove(tarjetaListTarjeta);
                    oldNombreEstacionOfTarjetaListTarjeta = em.merge(oldNombreEstacionOfTarjetaListTarjeta);
                }
            }
            for (Solicitud solicitudListSolicitud : estacion.getSolicitudList()) {
                Estacion oldNombreEstacionOfSolicitudListSolicitud = solicitudListSolicitud.getNombreEstacion();
                solicitudListSolicitud.setNombreEstacion(estacion);
                solicitudListSolicitud = em.merge(solicitudListSolicitud);
                if (oldNombreEstacionOfSolicitudListSolicitud != null) {
                    oldNombreEstacionOfSolicitudListSolicitud.getSolicitudList().remove(solicitudListSolicitud);
                    oldNombreEstacionOfSolicitudListSolicitud = em.merge(oldNombreEstacionOfSolicitudListSolicitud);
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
            List<Ruta> rutaListOld = persistentEstacion.getRutaList();
            List<Ruta> rutaListNew = estacion.getRutaList();
            List<TarjetaIngresaEstacion> tarjetaIngresaEstacionListOld = persistentEstacion.getTarjetaIngresaEstacionList();
            List<TarjetaIngresaEstacion> tarjetaIngresaEstacionListNew = estacion.getTarjetaIngresaEstacionList();
            List<TarjetaIngresaEstacion> tarjetaIngresaEstacionList1Old = persistentEstacion.getTarjetaIngresaEstacionList1();
            List<TarjetaIngresaEstacion> tarjetaIngresaEstacionList1New = estacion.getTarjetaIngresaEstacionList1();
            List<Tarjeta> tarjetaListOld = persistentEstacion.getTarjetaList();
            List<Tarjeta> tarjetaListNew = estacion.getTarjetaList();
            List<Solicitud> solicitudListOld = persistentEstacion.getSolicitudList();
            List<Solicitud> solicitudListNew = estacion.getSolicitudList();
            List<String> illegalOrphanMessages = null;
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionListOldTarjetaIngresaEstacion : tarjetaIngresaEstacionListOld) {
                if (!tarjetaIngresaEstacionListNew.contains(tarjetaIngresaEstacionListOldTarjetaIngresaEstacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TarjetaIngresaEstacion " + tarjetaIngresaEstacionListOldTarjetaIngresaEstacion + " since its estacion field is not nullable.");
                }
            }
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionList1OldTarjetaIngresaEstacion : tarjetaIngresaEstacionList1Old) {
                if (!tarjetaIngresaEstacionList1New.contains(tarjetaIngresaEstacionList1OldTarjetaIngresaEstacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TarjetaIngresaEstacion " + tarjetaIngresaEstacionList1OldTarjetaIngresaEstacion + " since its estacion1 field is not nullable.");
                }
            }
            for (Tarjeta tarjetaListOldTarjeta : tarjetaListOld) {
                if (!tarjetaListNew.contains(tarjetaListOldTarjeta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tarjeta " + tarjetaListOldTarjeta + " since its nombreEstacion field is not nullable.");
                }
            }
            for (Solicitud solicitudListOldSolicitud : solicitudListOld) {
                if (!solicitudListNew.contains(solicitudListOldSolicitud)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Solicitud " + solicitudListOldSolicitud + " since its nombreEstacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idEmpleadoNew != null) {
                idEmpleadoNew = em.getReference(idEmpleadoNew.getClass(), idEmpleadoNew.getId());
                estacion.setIdEmpleado(idEmpleadoNew);
            }
            List<Ruta> attachedRutaListNew = new ArrayList<Ruta>();
            for (Ruta rutaListNewRutaToAttach : rutaListNew) {
                rutaListNewRutaToAttach = em.getReference(rutaListNewRutaToAttach.getClass(), rutaListNewRutaToAttach.getNombreRuta());
                attachedRutaListNew.add(rutaListNewRutaToAttach);
            }
            rutaListNew = attachedRutaListNew;
            estacion.setRutaList(rutaListNew);
            List<TarjetaIngresaEstacion> attachedTarjetaIngresaEstacionListNew = new ArrayList<TarjetaIngresaEstacion>();
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionListNewTarjetaIngresaEstacionToAttach : tarjetaIngresaEstacionListNew) {
                tarjetaIngresaEstacionListNewTarjetaIngresaEstacionToAttach = em.getReference(tarjetaIngresaEstacionListNewTarjetaIngresaEstacionToAttach.getClass(), tarjetaIngresaEstacionListNewTarjetaIngresaEstacionToAttach.getTarjetaIngresaEstacionPK());
                attachedTarjetaIngresaEstacionListNew.add(tarjetaIngresaEstacionListNewTarjetaIngresaEstacionToAttach);
            }
            tarjetaIngresaEstacionListNew = attachedTarjetaIngresaEstacionListNew;
            estacion.setTarjetaIngresaEstacionList(tarjetaIngresaEstacionListNew);
            List<TarjetaIngresaEstacion> attachedTarjetaIngresaEstacionList1New = new ArrayList<TarjetaIngresaEstacion>();
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionList1NewTarjetaIngresaEstacionToAttach : tarjetaIngresaEstacionList1New) {
                tarjetaIngresaEstacionList1NewTarjetaIngresaEstacionToAttach = em.getReference(tarjetaIngresaEstacionList1NewTarjetaIngresaEstacionToAttach.getClass(), tarjetaIngresaEstacionList1NewTarjetaIngresaEstacionToAttach.getTarjetaIngresaEstacionPK());
                attachedTarjetaIngresaEstacionList1New.add(tarjetaIngresaEstacionList1NewTarjetaIngresaEstacionToAttach);
            }
            tarjetaIngresaEstacionList1New = attachedTarjetaIngresaEstacionList1New;
            estacion.setTarjetaIngresaEstacionList1(tarjetaIngresaEstacionList1New);
            List<Tarjeta> attachedTarjetaListNew = new ArrayList<Tarjeta>();
            for (Tarjeta tarjetaListNewTarjetaToAttach : tarjetaListNew) {
                tarjetaListNewTarjetaToAttach = em.getReference(tarjetaListNewTarjetaToAttach.getClass(), tarjetaListNewTarjetaToAttach.getPin());
                attachedTarjetaListNew.add(tarjetaListNewTarjetaToAttach);
            }
            tarjetaListNew = attachedTarjetaListNew;
            estacion.setTarjetaList(tarjetaListNew);
            List<Solicitud> attachedSolicitudListNew = new ArrayList<Solicitud>();
            for (Solicitud solicitudListNewSolicitudToAttach : solicitudListNew) {
                solicitudListNewSolicitudToAttach = em.getReference(solicitudListNewSolicitudToAttach.getClass(), solicitudListNewSolicitudToAttach.getTicket());
                attachedSolicitudListNew.add(solicitudListNewSolicitudToAttach);
            }
            solicitudListNew = attachedSolicitudListNew;
            estacion.setSolicitudList(solicitudListNew);
            estacion = em.merge(estacion);
            if (idEmpleadoOld != null && !idEmpleadoOld.equals(idEmpleadoNew)) {
                idEmpleadoOld.getEstacionList().remove(estacion);
                idEmpleadoOld = em.merge(idEmpleadoOld);
            }
            if (idEmpleadoNew != null && !idEmpleadoNew.equals(idEmpleadoOld)) {
                idEmpleadoNew.getEstacionList().add(estacion);
                idEmpleadoNew = em.merge(idEmpleadoNew);
            }
            for (Ruta rutaListOldRuta : rutaListOld) {
                if (!rutaListNew.contains(rutaListOldRuta)) {
                    rutaListOldRuta.getEstacionList().remove(estacion);
                    rutaListOldRuta = em.merge(rutaListOldRuta);
                }
            }
            for (Ruta rutaListNewRuta : rutaListNew) {
                if (!rutaListOld.contains(rutaListNewRuta)) {
                    rutaListNewRuta.getEstacionList().add(estacion);
                    rutaListNewRuta = em.merge(rutaListNewRuta);
                }
            }
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionListNewTarjetaIngresaEstacion : tarjetaIngresaEstacionListNew) {
                if (!tarjetaIngresaEstacionListOld.contains(tarjetaIngresaEstacionListNewTarjetaIngresaEstacion)) {
                    Estacion oldEstacionOfTarjetaIngresaEstacionListNewTarjetaIngresaEstacion = tarjetaIngresaEstacionListNewTarjetaIngresaEstacion.getEstacion();
                    tarjetaIngresaEstacionListNewTarjetaIngresaEstacion.setEstacion(estacion);
                    tarjetaIngresaEstacionListNewTarjetaIngresaEstacion = em.merge(tarjetaIngresaEstacionListNewTarjetaIngresaEstacion);
                    if (oldEstacionOfTarjetaIngresaEstacionListNewTarjetaIngresaEstacion != null && !oldEstacionOfTarjetaIngresaEstacionListNewTarjetaIngresaEstacion.equals(estacion)) {
                        oldEstacionOfTarjetaIngresaEstacionListNewTarjetaIngresaEstacion.getTarjetaIngresaEstacionList().remove(tarjetaIngresaEstacionListNewTarjetaIngresaEstacion);
                        oldEstacionOfTarjetaIngresaEstacionListNewTarjetaIngresaEstacion = em.merge(oldEstacionOfTarjetaIngresaEstacionListNewTarjetaIngresaEstacion);
                    }
                }
            }
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionList1NewTarjetaIngresaEstacion : tarjetaIngresaEstacionList1New) {
                if (!tarjetaIngresaEstacionList1Old.contains(tarjetaIngresaEstacionList1NewTarjetaIngresaEstacion)) {
                    Estacion oldEstacion1OfTarjetaIngresaEstacionList1NewTarjetaIngresaEstacion = tarjetaIngresaEstacionList1NewTarjetaIngresaEstacion.getEstacion1();
                    tarjetaIngresaEstacionList1NewTarjetaIngresaEstacion.setEstacion1(estacion);
                    tarjetaIngresaEstacionList1NewTarjetaIngresaEstacion = em.merge(tarjetaIngresaEstacionList1NewTarjetaIngresaEstacion);
                    if (oldEstacion1OfTarjetaIngresaEstacionList1NewTarjetaIngresaEstacion != null && !oldEstacion1OfTarjetaIngresaEstacionList1NewTarjetaIngresaEstacion.equals(estacion)) {
                        oldEstacion1OfTarjetaIngresaEstacionList1NewTarjetaIngresaEstacion.getTarjetaIngresaEstacionList1().remove(tarjetaIngresaEstacionList1NewTarjetaIngresaEstacion);
                        oldEstacion1OfTarjetaIngresaEstacionList1NewTarjetaIngresaEstacion = em.merge(oldEstacion1OfTarjetaIngresaEstacionList1NewTarjetaIngresaEstacion);
                    }
                }
            }
            for (Tarjeta tarjetaListNewTarjeta : tarjetaListNew) {
                if (!tarjetaListOld.contains(tarjetaListNewTarjeta)) {
                    Estacion oldNombreEstacionOfTarjetaListNewTarjeta = tarjetaListNewTarjeta.getNombreEstacion();
                    tarjetaListNewTarjeta.setNombreEstacion(estacion);
                    tarjetaListNewTarjeta = em.merge(tarjetaListNewTarjeta);
                    if (oldNombreEstacionOfTarjetaListNewTarjeta != null && !oldNombreEstacionOfTarjetaListNewTarjeta.equals(estacion)) {
                        oldNombreEstacionOfTarjetaListNewTarjeta.getTarjetaList().remove(tarjetaListNewTarjeta);
                        oldNombreEstacionOfTarjetaListNewTarjeta = em.merge(oldNombreEstacionOfTarjetaListNewTarjeta);
                    }
                }
            }
            for (Solicitud solicitudListNewSolicitud : solicitudListNew) {
                if (!solicitudListOld.contains(solicitudListNewSolicitud)) {
                    Estacion oldNombreEstacionOfSolicitudListNewSolicitud = solicitudListNewSolicitud.getNombreEstacion();
                    solicitudListNewSolicitud.setNombreEstacion(estacion);
                    solicitudListNewSolicitud = em.merge(solicitudListNewSolicitud);
                    if (oldNombreEstacionOfSolicitudListNewSolicitud != null && !oldNombreEstacionOfSolicitudListNewSolicitud.equals(estacion)) {
                        oldNombreEstacionOfSolicitudListNewSolicitud.getSolicitudList().remove(solicitudListNewSolicitud);
                        oldNombreEstacionOfSolicitudListNewSolicitud = em.merge(oldNombreEstacionOfSolicitudListNewSolicitud);
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
            List<TarjetaIngresaEstacion> tarjetaIngresaEstacionListOrphanCheck = estacion.getTarjetaIngresaEstacionList();
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionListOrphanCheckTarjetaIngresaEstacion : tarjetaIngresaEstacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estacion (" + estacion + ") cannot be destroyed since the TarjetaIngresaEstacion " + tarjetaIngresaEstacionListOrphanCheckTarjetaIngresaEstacion + " in its tarjetaIngresaEstacionList field has a non-nullable estacion field.");
            }
            List<TarjetaIngresaEstacion> tarjetaIngresaEstacionList1OrphanCheck = estacion.getTarjetaIngresaEstacionList1();
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionList1OrphanCheckTarjetaIngresaEstacion : tarjetaIngresaEstacionList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estacion (" + estacion + ") cannot be destroyed since the TarjetaIngresaEstacion " + tarjetaIngresaEstacionList1OrphanCheckTarjetaIngresaEstacion + " in its tarjetaIngresaEstacionList1 field has a non-nullable estacion1 field.");
            }
            List<Tarjeta> tarjetaListOrphanCheck = estacion.getTarjetaList();
            for (Tarjeta tarjetaListOrphanCheckTarjeta : tarjetaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estacion (" + estacion + ") cannot be destroyed since the Tarjeta " + tarjetaListOrphanCheckTarjeta + " in its tarjetaList field has a non-nullable nombreEstacion field.");
            }
            List<Solicitud> solicitudListOrphanCheck = estacion.getSolicitudList();
            for (Solicitud solicitudListOrphanCheckSolicitud : solicitudListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estacion (" + estacion + ") cannot be destroyed since the Solicitud " + solicitudListOrphanCheckSolicitud + " in its solicitudList field has a non-nullable nombreEstacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Empleado idEmpleado = estacion.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado.getEstacionList().remove(estacion);
                idEmpleado = em.merge(idEmpleado);
            }
            List<Ruta> rutaList = estacion.getRutaList();
            for (Ruta rutaListRuta : rutaList) {
                rutaListRuta.getEstacionList().remove(estacion);
                rutaListRuta = em.merge(rutaListRuta);
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
