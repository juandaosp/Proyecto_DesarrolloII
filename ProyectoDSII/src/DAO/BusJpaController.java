/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Logica.Bus;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Logica.Programacion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dash
 */
public class BusJpaController implements Serializable {

    public BusJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bus bus) throws PreexistingEntityException, Exception {
        if (bus.getProgramacionCollection() == null) {
            bus.setProgramacionCollection(new ArrayList<Programacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Programacion> attachedProgramacionCollection = new ArrayList<Programacion>();
            for (Programacion programacionCollectionProgramacionToAttach : bus.getProgramacionCollection()) {
                programacionCollectionProgramacionToAttach = em.getReference(programacionCollectionProgramacionToAttach.getClass(), programacionCollectionProgramacionToAttach.getProgramacionPK());
                attachedProgramacionCollection.add(programacionCollectionProgramacionToAttach);
            }
            bus.setProgramacionCollection(attachedProgramacionCollection);
            em.persist(bus);
            for (Programacion programacionCollectionProgramacion : bus.getProgramacionCollection()) {
                Bus oldBusOfProgramacionCollectionProgramacion = programacionCollectionProgramacion.getBus();
                programacionCollectionProgramacion.setBus(bus);
                programacionCollectionProgramacion = em.merge(programacionCollectionProgramacion);
                if (oldBusOfProgramacionCollectionProgramacion != null) {
                    oldBusOfProgramacionCollectionProgramacion.getProgramacionCollection().remove(programacionCollectionProgramacion);
                    oldBusOfProgramacionCollectionProgramacion = em.merge(oldBusOfProgramacionCollectionProgramacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBus(bus.getMatricula()) != null) {
                throw new PreexistingEntityException("Bus " + bus + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bus bus) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bus persistentBus = em.find(Bus.class, bus.getMatricula());
            Collection<Programacion> programacionCollectionOld = persistentBus.getProgramacionCollection();
            Collection<Programacion> programacionCollectionNew = bus.getProgramacionCollection();
            List<String> illegalOrphanMessages = null;
            for (Programacion programacionCollectionOldProgramacion : programacionCollectionOld) {
                if (!programacionCollectionNew.contains(programacionCollectionOldProgramacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Programacion " + programacionCollectionOldProgramacion + " since its bus field is not nullable.");
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
            bus.setProgramacionCollection(programacionCollectionNew);
            bus = em.merge(bus);
            for (Programacion programacionCollectionNewProgramacion : programacionCollectionNew) {
                if (!programacionCollectionOld.contains(programacionCollectionNewProgramacion)) {
                    Bus oldBusOfProgramacionCollectionNewProgramacion = programacionCollectionNewProgramacion.getBus();
                    programacionCollectionNewProgramacion.setBus(bus);
                    programacionCollectionNewProgramacion = em.merge(programacionCollectionNewProgramacion);
                    if (oldBusOfProgramacionCollectionNewProgramacion != null && !oldBusOfProgramacionCollectionNewProgramacion.equals(bus)) {
                        oldBusOfProgramacionCollectionNewProgramacion.getProgramacionCollection().remove(programacionCollectionNewProgramacion);
                        oldBusOfProgramacionCollectionNewProgramacion = em.merge(oldBusOfProgramacionCollectionNewProgramacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = bus.getMatricula();
                if (findBus(id) == null) {
                    throw new NonexistentEntityException("The bus with id " + id + " no longer exists.");
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
            Bus bus;
            try {
                bus = em.getReference(Bus.class, id);
                bus.getMatricula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bus with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Programacion> programacionCollectionOrphanCheck = bus.getProgramacionCollection();
            for (Programacion programacionCollectionOrphanCheckProgramacion : programacionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Bus (" + bus + ") cannot be destroyed since the Programacion " + programacionCollectionOrphanCheckProgramacion + " in its programacionCollection field has a non-nullable bus field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(bus);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bus> findBusEntities() {
        return findBusEntities(true, -1, -1);
    }

    public List<Bus> findBusEntities(int maxResults, int firstResult) {
        return findBusEntities(false, maxResults, firstResult);
    }

    private List<Bus> findBusEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bus.class));
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

    public Bus findBus(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bus.class, id);
        } finally {
            em.close();
        }
    }

    public int getBusCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bus> rt = cq.from(Bus.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
