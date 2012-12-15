/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Logica.Recarga;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Logica.Tarjeta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class RecargaJpaController implements Serializable {

    public RecargaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Recarga recarga) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Tarjeta tarjetaOrphanCheck = recarga.getTarjeta();
        if (tarjetaOrphanCheck != null) {
            Recarga oldRecargaOfTarjeta = tarjetaOrphanCheck.getRecarga();
            if (oldRecargaOfTarjeta != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Tarjeta " + tarjetaOrphanCheck + " already has an item of type Recarga whose tarjeta column cannot be null. Please make another selection for the tarjeta field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tarjeta tarjeta = recarga.getTarjeta();
            if (tarjeta != null) {
                tarjeta = em.getReference(tarjeta.getClass(), tarjeta.getPin());
                recarga.setTarjeta(tarjeta);
            }
            em.persist(recarga);
            if (tarjeta != null) {
                tarjeta.setRecarga(recarga);
                tarjeta = em.merge(tarjeta);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRecarga(recarga.getPinTarjeta()) != null) {
                throw new PreexistingEntityException("Recarga " + recarga + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Recarga recarga) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Recarga persistentRecarga = em.find(Recarga.class, recarga.getPinTarjeta());
            Tarjeta tarjetaOld = persistentRecarga.getTarjeta();
            Tarjeta tarjetaNew = recarga.getTarjeta();
            List<String> illegalOrphanMessages = null;
            if (tarjetaNew != null && !tarjetaNew.equals(tarjetaOld)) {
                Recarga oldRecargaOfTarjeta = tarjetaNew.getRecarga();
                if (oldRecargaOfTarjeta != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Tarjeta " + tarjetaNew + " already has an item of type Recarga whose tarjeta column cannot be null. Please make another selection for the tarjeta field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tarjetaNew != null) {
                tarjetaNew = em.getReference(tarjetaNew.getClass(), tarjetaNew.getPin());
                recarga.setTarjeta(tarjetaNew);
            }
            recarga = em.merge(recarga);
            if (tarjetaOld != null && !tarjetaOld.equals(tarjetaNew)) {
                tarjetaOld.setRecarga(null);
                tarjetaOld = em.merge(tarjetaOld);
            }
            if (tarjetaNew != null && !tarjetaNew.equals(tarjetaOld)) {
                tarjetaNew.setRecarga(recarga);
                tarjetaNew = em.merge(tarjetaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = recarga.getPinTarjeta();
                if (findRecarga(id) == null) {
                    throw new NonexistentEntityException("The recarga with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Recarga recarga;
            try {
                recarga = em.getReference(Recarga.class, id);
                recarga.getPinTarjeta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The recarga with id " + id + " no longer exists.", enfe);
            }
            Tarjeta tarjeta = recarga.getTarjeta();
            if (tarjeta != null) {
                tarjeta.setRecarga(null);
                tarjeta = em.merge(tarjeta);
            }
            em.remove(recarga);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Recarga> findRecargaEntities() {
        return findRecargaEntities(true, -1, -1);
    }

    public List<Recarga> findRecargaEntities(int maxResults, int firstResult) {
        return findRecargaEntities(false, maxResults, firstResult);
    }

    private List<Recarga> findRecargaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Recarga.class));
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

    public Recarga findRecarga(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Recarga.class, id);
        } finally {
            em.close();
        }
    }

    public int getRecargaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Recarga> rt = cq.from(Recarga.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
