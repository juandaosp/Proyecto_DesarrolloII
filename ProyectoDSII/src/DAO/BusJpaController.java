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
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
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
        if (bus.getProgramacionList() == null) {
            bus.setProgramacionList(new ArrayList<Programacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Programacion> attachedProgramacionList = new ArrayList<Programacion>();
            for (Programacion programacionListProgramacionToAttach : bus.getProgramacionList()) {
                programacionListProgramacionToAttach = em.getReference(programacionListProgramacionToAttach.getClass(), programacionListProgramacionToAttach.getProgramacionPK());
                attachedProgramacionList.add(programacionListProgramacionToAttach);
            }
            bus.setProgramacionList(attachedProgramacionList);
            em.persist(bus);
            for (Programacion programacionListProgramacion : bus.getProgramacionList()) {
                Bus oldBusOfProgramacionListProgramacion = programacionListProgramacion.getBus();
                programacionListProgramacion.setBus(bus);
                programacionListProgramacion = em.merge(programacionListProgramacion);
                if (oldBusOfProgramacionListProgramacion != null) {
                    oldBusOfProgramacionListProgramacion.getProgramacionList().remove(programacionListProgramacion);
                    oldBusOfProgramacionListProgramacion = em.merge(oldBusOfProgramacionListProgramacion);
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
            List<Programacion> programacionListOld = persistentBus.getProgramacionList();
            List<Programacion> programacionListNew = bus.getProgramacionList();
            List<String> illegalOrphanMessages = null;
            for (Programacion programacionListOldProgramacion : programacionListOld) {
                if (!programacionListNew.contains(programacionListOldProgramacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Programacion " + programacionListOldProgramacion + " since its bus field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Programacion> attachedProgramacionListNew = new ArrayList<Programacion>();
            for (Programacion programacionListNewProgramacionToAttach : programacionListNew) {
                programacionListNewProgramacionToAttach = em.getReference(programacionListNewProgramacionToAttach.getClass(), programacionListNewProgramacionToAttach.getProgramacionPK());
                attachedProgramacionListNew.add(programacionListNewProgramacionToAttach);
            }
            programacionListNew = attachedProgramacionListNew;
            bus.setProgramacionList(programacionListNew);
            bus = em.merge(bus);
            for (Programacion programacionListNewProgramacion : programacionListNew) {
                if (!programacionListOld.contains(programacionListNewProgramacion)) {
                    Bus oldBusOfProgramacionListNewProgramacion = programacionListNewProgramacion.getBus();
                    programacionListNewProgramacion.setBus(bus);
                    programacionListNewProgramacion = em.merge(programacionListNewProgramacion);
                    if (oldBusOfProgramacionListNewProgramacion != null && !oldBusOfProgramacionListNewProgramacion.equals(bus)) {
                        oldBusOfProgramacionListNewProgramacion.getProgramacionList().remove(programacionListNewProgramacion);
                        oldBusOfProgramacionListNewProgramacion = em.merge(oldBusOfProgramacionListNewProgramacion);
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
            List<Programacion> programacionListOrphanCheck = bus.getProgramacionList();
            for (Programacion programacionListOrphanCheckProgramacion : programacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Bus (" + bus + ") cannot be destroyed since the Programacion " + programacionListOrphanCheckProgramacion + " in its programacionList field has a non-nullable bus field.");
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
