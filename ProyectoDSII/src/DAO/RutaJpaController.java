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
import Logica.Estacion;
import java.util.ArrayList;
import java.util.Collection;
import Logica.Programacion;
import Logica.Ruta;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dash
 */
public class RutaJpaController implements Serializable {

    public RutaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ruta ruta) throws PreexistingEntityException, Exception {
        if (ruta.getEstacionCollection() == null) {
            ruta.setEstacionCollection(new ArrayList<Estacion>());
        }
        if (ruta.getProgramacionCollection() == null) {
            ruta.setProgramacionCollection(new ArrayList<Programacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Estacion> attachedEstacionCollection = new ArrayList<Estacion>();
            for (Estacion estacionCollectionEstacionToAttach : ruta.getEstacionCollection()) {
                estacionCollectionEstacionToAttach = em.getReference(estacionCollectionEstacionToAttach.getClass(), estacionCollectionEstacionToAttach.getNombreEstacion());
                attachedEstacionCollection.add(estacionCollectionEstacionToAttach);
            }
            ruta.setEstacionCollection(attachedEstacionCollection);
            Collection<Programacion> attachedProgramacionCollection = new ArrayList<Programacion>();
            for (Programacion programacionCollectionProgramacionToAttach : ruta.getProgramacionCollection()) {
                programacionCollectionProgramacionToAttach = em.getReference(programacionCollectionProgramacionToAttach.getClass(), programacionCollectionProgramacionToAttach.getProgramacionPK());
                attachedProgramacionCollection.add(programacionCollectionProgramacionToAttach);
            }
            ruta.setProgramacionCollection(attachedProgramacionCollection);
            em.persist(ruta);
            for (Estacion estacionCollectionEstacion : ruta.getEstacionCollection()) {
                estacionCollectionEstacion.getRutaCollection().add(ruta);
                estacionCollectionEstacion = em.merge(estacionCollectionEstacion);
            }
            for (Programacion programacionCollectionProgramacion : ruta.getProgramacionCollection()) {
                Ruta oldRutaOfProgramacionCollectionProgramacion = programacionCollectionProgramacion.getRuta();
                programacionCollectionProgramacion.setRuta(ruta);
                programacionCollectionProgramacion = em.merge(programacionCollectionProgramacion);
                if (oldRutaOfProgramacionCollectionProgramacion != null) {
                    oldRutaOfProgramacionCollectionProgramacion.getProgramacionCollection().remove(programacionCollectionProgramacion);
                    oldRutaOfProgramacionCollectionProgramacion = em.merge(oldRutaOfProgramacionCollectionProgramacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRuta(ruta.getNombreRuta()) != null) {
                throw new PreexistingEntityException("Ruta " + ruta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ruta ruta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ruta persistentRuta = em.find(Ruta.class, ruta.getNombreRuta());
            Collection<Estacion> estacionCollectionOld = persistentRuta.getEstacionCollection();
            Collection<Estacion> estacionCollectionNew = ruta.getEstacionCollection();
            Collection<Programacion> programacionCollectionOld = persistentRuta.getProgramacionCollection();
            Collection<Programacion> programacionCollectionNew = ruta.getProgramacionCollection();
            List<String> illegalOrphanMessages = null;
            for (Programacion programacionCollectionOldProgramacion : programacionCollectionOld) {
                if (!programacionCollectionNew.contains(programacionCollectionOldProgramacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Programacion " + programacionCollectionOldProgramacion + " since its ruta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Estacion> attachedEstacionCollectionNew = new ArrayList<Estacion>();
            for (Estacion estacionCollectionNewEstacionToAttach : estacionCollectionNew) {
                estacionCollectionNewEstacionToAttach = em.getReference(estacionCollectionNewEstacionToAttach.getClass(), estacionCollectionNewEstacionToAttach.getNombreEstacion());
                attachedEstacionCollectionNew.add(estacionCollectionNewEstacionToAttach);
            }
            estacionCollectionNew = attachedEstacionCollectionNew;
            ruta.setEstacionCollection(estacionCollectionNew);
            Collection<Programacion> attachedProgramacionCollectionNew = new ArrayList<Programacion>();
            for (Programacion programacionCollectionNewProgramacionToAttach : programacionCollectionNew) {
                programacionCollectionNewProgramacionToAttach = em.getReference(programacionCollectionNewProgramacionToAttach.getClass(), programacionCollectionNewProgramacionToAttach.getProgramacionPK());
                attachedProgramacionCollectionNew.add(programacionCollectionNewProgramacionToAttach);
            }
            programacionCollectionNew = attachedProgramacionCollectionNew;
            ruta.setProgramacionCollection(programacionCollectionNew);
            ruta = em.merge(ruta);
            for (Estacion estacionCollectionOldEstacion : estacionCollectionOld) {
                if (!estacionCollectionNew.contains(estacionCollectionOldEstacion)) {
                    estacionCollectionOldEstacion.getRutaCollection().remove(ruta);
                    estacionCollectionOldEstacion = em.merge(estacionCollectionOldEstacion);
                }
            }
            for (Estacion estacionCollectionNewEstacion : estacionCollectionNew) {
                if (!estacionCollectionOld.contains(estacionCollectionNewEstacion)) {
                    estacionCollectionNewEstacion.getRutaCollection().add(ruta);
                    estacionCollectionNewEstacion = em.merge(estacionCollectionNewEstacion);
                }
            }
            for (Programacion programacionCollectionNewProgramacion : programacionCollectionNew) {
                if (!programacionCollectionOld.contains(programacionCollectionNewProgramacion)) {
                    Ruta oldRutaOfProgramacionCollectionNewProgramacion = programacionCollectionNewProgramacion.getRuta();
                    programacionCollectionNewProgramacion.setRuta(ruta);
                    programacionCollectionNewProgramacion = em.merge(programacionCollectionNewProgramacion);
                    if (oldRutaOfProgramacionCollectionNewProgramacion != null && !oldRutaOfProgramacionCollectionNewProgramacion.equals(ruta)) {
                        oldRutaOfProgramacionCollectionNewProgramacion.getProgramacionCollection().remove(programacionCollectionNewProgramacion);
                        oldRutaOfProgramacionCollectionNewProgramacion = em.merge(oldRutaOfProgramacionCollectionNewProgramacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = ruta.getNombreRuta();
                if (findRuta(id) == null) {
                    throw new NonexistentEntityException("The ruta with id " + id + " no longer exists.");
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
            Ruta ruta;
            try {
                ruta = em.getReference(Ruta.class, id);
                ruta.getNombreRuta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ruta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Programacion> programacionCollectionOrphanCheck = ruta.getProgramacionCollection();
            for (Programacion programacionCollectionOrphanCheckProgramacion : programacionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ruta (" + ruta + ") cannot be destroyed since the Programacion " + programacionCollectionOrphanCheckProgramacion + " in its programacionCollection field has a non-nullable ruta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Estacion> estacionCollection = ruta.getEstacionCollection();
            for (Estacion estacionCollectionEstacion : estacionCollection) {
                estacionCollectionEstacion.getRutaCollection().remove(ruta);
                estacionCollectionEstacion = em.merge(estacionCollectionEstacion);
            }
            em.remove(ruta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ruta> findRutaEntities() {
        return findRutaEntities(true, -1, -1);
    }

    public List<Ruta> findRutaEntities(int maxResults, int firstResult) {
        return findRutaEntities(false, maxResults, firstResult);
    }

    private List<Ruta> findRutaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ruta.class));
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

    public Ruta findRuta(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ruta.class, id);
        } finally {
            em.close();
        }
    }

    public int getRutaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ruta> rt = cq.from(Ruta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
