/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Logica.Ruta;
import Logica.Empleado;
import Logica.Bus;
import Logica.Programacion;
import Logica.ProgramacionPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class ProgramacionJpaController implements Serializable {

    public ProgramacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Programacion programacion) throws PreexistingEntityException, Exception {
        if (programacion.getProgramacionPK() == null) {
            programacion.setProgramacionPK(new ProgramacionPK());
        }
        programacion.getProgramacionPK().setMatriculaBus(programacion.getBus().getMatricula());
        programacion.getProgramacionPK().setNombreRuta(programacion.getRuta().getNombreRuta());
        programacion.getProgramacionPK().setIdEmpleado(programacion.getEmpleado().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ruta ruta = programacion.getRuta();
            if (ruta != null) {
                ruta = em.getReference(ruta.getClass(), ruta.getNombreRuta());
                programacion.setRuta(ruta);
            }
            Empleado empleado = programacion.getEmpleado();
            if (empleado != null) {
                empleado = em.getReference(empleado.getClass(), empleado.getId());
                programacion.setEmpleado(empleado);
            }
            Bus bus = programacion.getBus();
            if (bus != null) {
                bus = em.getReference(bus.getClass(), bus.getMatricula());
                programacion.setBus(bus);
            }
            em.persist(programacion);
            if (ruta != null) {
                ruta.getProgramacionList().add(programacion);
                ruta = em.merge(ruta);
            }
            if (empleado != null) {
                empleado.getProgramacionList().add(programacion);
                empleado = em.merge(empleado);
            }
            if (bus != null) {
                bus.getProgramacionList().add(programacion);
                bus = em.merge(bus);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProgramacion(programacion.getProgramacionPK()) != null) {
                throw new PreexistingEntityException("Programacion " + programacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Programacion programacion) throws NonexistentEntityException, Exception {
        programacion.getProgramacionPK().setMatriculaBus(programacion.getBus().getMatricula());
        programacion.getProgramacionPK().setNombreRuta(programacion.getRuta().getNombreRuta());
        programacion.getProgramacionPK().setIdEmpleado(programacion.getEmpleado().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Programacion persistentProgramacion = em.find(Programacion.class, programacion.getProgramacionPK());
            Ruta rutaOld = persistentProgramacion.getRuta();
            Ruta rutaNew = programacion.getRuta();
            Empleado empleadoOld = persistentProgramacion.getEmpleado();
            Empleado empleadoNew = programacion.getEmpleado();
            Bus busOld = persistentProgramacion.getBus();
            Bus busNew = programacion.getBus();
            if (rutaNew != null) {
                rutaNew = em.getReference(rutaNew.getClass(), rutaNew.getNombreRuta());
                programacion.setRuta(rutaNew);
            }
            if (empleadoNew != null) {
                empleadoNew = em.getReference(empleadoNew.getClass(), empleadoNew.getId());
                programacion.setEmpleado(empleadoNew);
            }
            if (busNew != null) {
                busNew = em.getReference(busNew.getClass(), busNew.getMatricula());
                programacion.setBus(busNew);
            }
            programacion = em.merge(programacion);
            if (rutaOld != null && !rutaOld.equals(rutaNew)) {
                rutaOld.getProgramacionList().remove(programacion);
                rutaOld = em.merge(rutaOld);
            }
            if (rutaNew != null && !rutaNew.equals(rutaOld)) {
                rutaNew.getProgramacionList().add(programacion);
                rutaNew = em.merge(rutaNew);
            }
            if (empleadoOld != null && !empleadoOld.equals(empleadoNew)) {
                empleadoOld.getProgramacionList().remove(programacion);
                empleadoOld = em.merge(empleadoOld);
            }
            if (empleadoNew != null && !empleadoNew.equals(empleadoOld)) {
                empleadoNew.getProgramacionList().add(programacion);
                empleadoNew = em.merge(empleadoNew);
            }
            if (busOld != null && !busOld.equals(busNew)) {
                busOld.getProgramacionList().remove(programacion);
                busOld = em.merge(busOld);
            }
            if (busNew != null && !busNew.equals(busOld)) {
                busNew.getProgramacionList().add(programacion);
                busNew = em.merge(busNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ProgramacionPK id = programacion.getProgramacionPK();
                if (findProgramacion(id) == null) {
                    throw new NonexistentEntityException("The programacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ProgramacionPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Programacion programacion;
            try {
                programacion = em.getReference(Programacion.class, id);
                programacion.getProgramacionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The programacion with id " + id + " no longer exists.", enfe);
            }
            Ruta ruta = programacion.getRuta();
            if (ruta != null) {
                ruta.getProgramacionList().remove(programacion);
                ruta = em.merge(ruta);
            }
            Empleado empleado = programacion.getEmpleado();
            if (empleado != null) {
                empleado.getProgramacionList().remove(programacion);
                empleado = em.merge(empleado);
            }
            Bus bus = programacion.getBus();
            if (bus != null) {
                bus.getProgramacionList().remove(programacion);
                bus = em.merge(bus);
            }
            em.remove(programacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Programacion> findProgramacionEntities() {
        return findProgramacionEntities(true, -1, -1);
    }

    public List<Programacion> findProgramacionEntities(int maxResults, int firstResult) {
        return findProgramacionEntities(false, maxResults, firstResult);
    }

    private List<Programacion> findProgramacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Programacion.class));
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

    public Programacion findProgramacion(ProgramacionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Programacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getProgramacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Programacion> rt = cq.from(Programacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
