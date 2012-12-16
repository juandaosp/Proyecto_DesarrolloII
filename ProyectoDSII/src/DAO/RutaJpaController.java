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
import java.util.List;
import Logica.Programacion;
import Logica.Ruta;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
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
        if (ruta.getEstacionList() == null) {
            ruta.setEstacionList(new ArrayList<Estacion>());
        }
        if (ruta.getProgramacionList() == null) {
            ruta.setProgramacionList(new ArrayList<Programacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Estacion> attachedEstacionList = new ArrayList<Estacion>();
            for (Estacion estacionListEstacionToAttach : ruta.getEstacionList()) {
                estacionListEstacionToAttach = em.getReference(estacionListEstacionToAttach.getClass(), estacionListEstacionToAttach.getNombreEstacion());
                attachedEstacionList.add(estacionListEstacionToAttach);
            }
            ruta.setEstacionList(attachedEstacionList);
            List<Programacion> attachedProgramacionList = new ArrayList<Programacion>();
            for (Programacion programacionListProgramacionToAttach : ruta.getProgramacionList()) {
                programacionListProgramacionToAttach = em.getReference(programacionListProgramacionToAttach.getClass(), programacionListProgramacionToAttach.getProgramacionPK());
                attachedProgramacionList.add(programacionListProgramacionToAttach);
            }
            ruta.setProgramacionList(attachedProgramacionList);
            em.persist(ruta);
            for (Estacion estacionListEstacion : ruta.getEstacionList()) {
                estacionListEstacion.getRutaList().add(ruta);
                estacionListEstacion = em.merge(estacionListEstacion);
            }
            for (Programacion programacionListProgramacion : ruta.getProgramacionList()) {
                Ruta oldRutaOfProgramacionListProgramacion = programacionListProgramacion.getRuta();
                programacionListProgramacion.setRuta(ruta);
                programacionListProgramacion = em.merge(programacionListProgramacion);
                if (oldRutaOfProgramacionListProgramacion != null) {
                    oldRutaOfProgramacionListProgramacion.getProgramacionList().remove(programacionListProgramacion);
                    oldRutaOfProgramacionListProgramacion = em.merge(oldRutaOfProgramacionListProgramacion);
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
            List<Estacion> estacionListOld = persistentRuta.getEstacionList();
            List<Estacion> estacionListNew = ruta.getEstacionList();
            List<Programacion> programacionListOld = persistentRuta.getProgramacionList();
            List<Programacion> programacionListNew = ruta.getProgramacionList();
            List<String> illegalOrphanMessages = null;
            for (Programacion programacionListOldProgramacion : programacionListOld) {
                if (!programacionListNew.contains(programacionListOldProgramacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Programacion " + programacionListOldProgramacion + " since its ruta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Estacion> attachedEstacionListNew = new ArrayList<Estacion>();
            for (Estacion estacionListNewEstacionToAttach : estacionListNew) {
                estacionListNewEstacionToAttach = em.getReference(estacionListNewEstacionToAttach.getClass(), estacionListNewEstacionToAttach.getNombreEstacion());
                attachedEstacionListNew.add(estacionListNewEstacionToAttach);
            }
            estacionListNew = attachedEstacionListNew;
            ruta.setEstacionList(estacionListNew);
            List<Programacion> attachedProgramacionListNew = new ArrayList<Programacion>();
            for (Programacion programacionListNewProgramacionToAttach : programacionListNew) {
                programacionListNewProgramacionToAttach = em.getReference(programacionListNewProgramacionToAttach.getClass(), programacionListNewProgramacionToAttach.getProgramacionPK());
                attachedProgramacionListNew.add(programacionListNewProgramacionToAttach);
            }
            programacionListNew = attachedProgramacionListNew;
            ruta.setProgramacionList(programacionListNew);
            ruta = em.merge(ruta);
            for (Estacion estacionListOldEstacion : estacionListOld) {
                if (!estacionListNew.contains(estacionListOldEstacion)) {
                    estacionListOldEstacion.getRutaList().remove(ruta);
                    estacionListOldEstacion = em.merge(estacionListOldEstacion);
                }
            }
            for (Estacion estacionListNewEstacion : estacionListNew) {
                if (!estacionListOld.contains(estacionListNewEstacion)) {
                    estacionListNewEstacion.getRutaList().add(ruta);
                    estacionListNewEstacion = em.merge(estacionListNewEstacion);
                }
            }
            for (Programacion programacionListNewProgramacion : programacionListNew) {
                if (!programacionListOld.contains(programacionListNewProgramacion)) {
                    Ruta oldRutaOfProgramacionListNewProgramacion = programacionListNewProgramacion.getRuta();
                    programacionListNewProgramacion.setRuta(ruta);
                    programacionListNewProgramacion = em.merge(programacionListNewProgramacion);
                    if (oldRutaOfProgramacionListNewProgramacion != null && !oldRutaOfProgramacionListNewProgramacion.equals(ruta)) {
                        oldRutaOfProgramacionListNewProgramacion.getProgramacionList().remove(programacionListNewProgramacion);
                        oldRutaOfProgramacionListNewProgramacion = em.merge(oldRutaOfProgramacionListNewProgramacion);
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
            List<Programacion> programacionListOrphanCheck = ruta.getProgramacionList();
            for (Programacion programacionListOrphanCheckProgramacion : programacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ruta (" + ruta + ") cannot be destroyed since the Programacion " + programacionListOrphanCheckProgramacion + " in its programacionList field has a non-nullable ruta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Estacion> estacionList = ruta.getEstacionList();
            for (Estacion estacionListEstacion : estacionList) {
                estacionListEstacion.getRutaList().remove(ruta);
                estacionListEstacion = em.merge(estacionListEstacion);
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
