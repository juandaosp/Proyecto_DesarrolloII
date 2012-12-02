/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Logica.Empleado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Logica.Programacion;
import java.util.ArrayList;
import java.util.Collection;
import Logica.Estacion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dash
 */
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) throws PreexistingEntityException, Exception {
        if (empleado.getProgramacionCollection() == null) {
            empleado.setProgramacionCollection(new ArrayList<Programacion>());
        }
        if (empleado.getEstacionCollection() == null) {
            empleado.setEstacionCollection(new ArrayList<Estacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Programacion> attachedProgramacionCollection = new ArrayList<Programacion>();
            for (Programacion programacionCollectionProgramacionToAttach : empleado.getProgramacionCollection()) {
                programacionCollectionProgramacionToAttach = em.getReference(programacionCollectionProgramacionToAttach.getClass(), programacionCollectionProgramacionToAttach.getProgramacionPK());
                attachedProgramacionCollection.add(programacionCollectionProgramacionToAttach);
            }
            empleado.setProgramacionCollection(attachedProgramacionCollection);
            Collection<Estacion> attachedEstacionCollection = new ArrayList<Estacion>();
            for (Estacion estacionCollectionEstacionToAttach : empleado.getEstacionCollection()) {
                estacionCollectionEstacionToAttach = em.getReference(estacionCollectionEstacionToAttach.getClass(), estacionCollectionEstacionToAttach.getNombreEstacion());
                attachedEstacionCollection.add(estacionCollectionEstacionToAttach);
            }
            empleado.setEstacionCollection(attachedEstacionCollection);
            em.persist(empleado);
            for (Programacion programacionCollectionProgramacion : empleado.getProgramacionCollection()) {
                Empleado oldEmpleadoOfProgramacionCollectionProgramacion = programacionCollectionProgramacion.getEmpleado();
                programacionCollectionProgramacion.setEmpleado(empleado);
                programacionCollectionProgramacion = em.merge(programacionCollectionProgramacion);
                if (oldEmpleadoOfProgramacionCollectionProgramacion != null) {
                    oldEmpleadoOfProgramacionCollectionProgramacion.getProgramacionCollection().remove(programacionCollectionProgramacion);
                    oldEmpleadoOfProgramacionCollectionProgramacion = em.merge(oldEmpleadoOfProgramacionCollectionProgramacion);
                }
            }
            for (Estacion estacionCollectionEstacion : empleado.getEstacionCollection()) {
                Empleado oldIdEmpleadoOfEstacionCollectionEstacion = estacionCollectionEstacion.getIdEmpleado();
                estacionCollectionEstacion.setIdEmpleado(empleado);
                estacionCollectionEstacion = em.merge(estacionCollectionEstacion);
                if (oldIdEmpleadoOfEstacionCollectionEstacion != null) {
                    oldIdEmpleadoOfEstacionCollectionEstacion.getEstacionCollection().remove(estacionCollectionEstacion);
                    oldIdEmpleadoOfEstacionCollectionEstacion = em.merge(oldIdEmpleadoOfEstacionCollectionEstacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpleado(empleado.getId()) != null) {
                throw new PreexistingEntityException("Empleado " + empleado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getId());
            Collection<Programacion> programacionCollectionOld = persistentEmpleado.getProgramacionCollection();
            Collection<Programacion> programacionCollectionNew = empleado.getProgramacionCollection();
            Collection<Estacion> estacionCollectionOld = persistentEmpleado.getEstacionCollection();
            Collection<Estacion> estacionCollectionNew = empleado.getEstacionCollection();
            List<String> illegalOrphanMessages = null;
            for (Programacion programacionCollectionOldProgramacion : programacionCollectionOld) {
                if (!programacionCollectionNew.contains(programacionCollectionOldProgramacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Programacion " + programacionCollectionOldProgramacion + " since its empleado field is not nullable.");
                }
            }
            for (Estacion estacionCollectionOldEstacion : estacionCollectionOld) {
                if (!estacionCollectionNew.contains(estacionCollectionOldEstacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Estacion " + estacionCollectionOldEstacion + " since its idEmpleado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Programacion> attachedProgramacionCollectionNew = new ArrayList<Programacion>();
            for (Programacion programacionCollectionNewProgramacionToAttach : programacionCollectionNew) {
                programacionCollectionNewProgramacionToAttach = em.getReference(programacionCollectionNewProgramacionToAttach.getClass(), programacionCollectionNewProgramacionToAttach.getProgramacionPK());
                attachedProgramacionCollectionNew.add(programacionCollectionNewProgramacionToAttach);
            }
            programacionCollectionNew = attachedProgramacionCollectionNew;
            empleado.setProgramacionCollection(programacionCollectionNew);
            Collection<Estacion> attachedEstacionCollectionNew = new ArrayList<Estacion>();
            for (Estacion estacionCollectionNewEstacionToAttach : estacionCollectionNew) {
                estacionCollectionNewEstacionToAttach = em.getReference(estacionCollectionNewEstacionToAttach.getClass(), estacionCollectionNewEstacionToAttach.getNombreEstacion());
                attachedEstacionCollectionNew.add(estacionCollectionNewEstacionToAttach);
            }
            estacionCollectionNew = attachedEstacionCollectionNew;
            empleado.setEstacionCollection(estacionCollectionNew);
            empleado = em.merge(empleado);
            for (Programacion programacionCollectionNewProgramacion : programacionCollectionNew) {
                if (!programacionCollectionOld.contains(programacionCollectionNewProgramacion)) {
                    Empleado oldEmpleadoOfProgramacionCollectionNewProgramacion = programacionCollectionNewProgramacion.getEmpleado();
                    programacionCollectionNewProgramacion.setEmpleado(empleado);
                    programacionCollectionNewProgramacion = em.merge(programacionCollectionNewProgramacion);
                    if (oldEmpleadoOfProgramacionCollectionNewProgramacion != null && !oldEmpleadoOfProgramacionCollectionNewProgramacion.equals(empleado)) {
                        oldEmpleadoOfProgramacionCollectionNewProgramacion.getProgramacionCollection().remove(programacionCollectionNewProgramacion);
                        oldEmpleadoOfProgramacionCollectionNewProgramacion = em.merge(oldEmpleadoOfProgramacionCollectionNewProgramacion);
                    }
                }
            }
            for (Estacion estacionCollectionNewEstacion : estacionCollectionNew) {
                if (!estacionCollectionOld.contains(estacionCollectionNewEstacion)) {
                    Empleado oldIdEmpleadoOfEstacionCollectionNewEstacion = estacionCollectionNewEstacion.getIdEmpleado();
                    estacionCollectionNewEstacion.setIdEmpleado(empleado);
                    estacionCollectionNewEstacion = em.merge(estacionCollectionNewEstacion);
                    if (oldIdEmpleadoOfEstacionCollectionNewEstacion != null && !oldIdEmpleadoOfEstacionCollectionNewEstacion.equals(empleado)) {
                        oldIdEmpleadoOfEstacionCollectionNewEstacion.getEstacionCollection().remove(estacionCollectionNewEstacion);
                        oldIdEmpleadoOfEstacionCollectionNewEstacion = em.merge(oldIdEmpleadoOfEstacionCollectionNewEstacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = empleado.getId();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Programacion> programacionCollectionOrphanCheck = empleado.getProgramacionCollection();
            for (Programacion programacionCollectionOrphanCheckProgramacion : programacionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleado (" + empleado + ") cannot be destroyed since the Programacion " + programacionCollectionOrphanCheckProgramacion + " in its programacionCollection field has a non-nullable empleado field.");
            }
            Collection<Estacion> estacionCollectionOrphanCheck = empleado.getEstacionCollection();
            for (Estacion estacionCollectionOrphanCheckEstacion : estacionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleado (" + empleado + ") cannot be destroyed since the Estacion " + estacionCollectionOrphanCheckEstacion + " in its estacionCollection field has a non-nullable idEmpleado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(empleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
