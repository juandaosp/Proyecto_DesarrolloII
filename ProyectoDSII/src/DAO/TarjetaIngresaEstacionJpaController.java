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
import Logica.Tarjeta;
import Logica.Estacion;
import Logica.TarjetaIngresaEstacion;
import Logica.TarjetaIngresaEstacionPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class TarjetaIngresaEstacionJpaController implements Serializable {

    public TarjetaIngresaEstacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TarjetaIngresaEstacion tarjetaIngresaEstacion) throws PreexistingEntityException, Exception {
        if (tarjetaIngresaEstacion.getTarjetaIngresaEstacionPK() == null) {
            tarjetaIngresaEstacion.setTarjetaIngresaEstacionPK(new TarjetaIngresaEstacionPK());
        }
        tarjetaIngresaEstacion.getTarjetaIngresaEstacionPK().setPinTarjeta(tarjetaIngresaEstacion.getTarjeta().getPin());
        tarjetaIngresaEstacion.getTarjetaIngresaEstacionPK().setUbicacionEstacion(tarjetaIngresaEstacion.getEstacion1().getUbicacion());
        tarjetaIngresaEstacion.getTarjetaIngresaEstacionPK().setNombreEstacion(tarjetaIngresaEstacion.getEstacion().getNombreEstacion());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tarjeta tarjeta = tarjetaIngresaEstacion.getTarjeta();
            if (tarjeta != null) {
                tarjeta = em.getReference(tarjeta.getClass(), tarjeta.getPin());
                tarjetaIngresaEstacion.setTarjeta(tarjeta);
            }
            Estacion estacion = tarjetaIngresaEstacion.getEstacion();
            if (estacion != null) {
                estacion = em.getReference(estacion.getClass(), estacion.getNombreEstacion());
                tarjetaIngresaEstacion.setEstacion(estacion);
            }
            Estacion estacion1 = tarjetaIngresaEstacion.getEstacion1();
            if (estacion1 != null) {
                estacion1 = em.getReference(estacion1.getClass(), estacion1.getNombreEstacion());
                tarjetaIngresaEstacion.setEstacion1(estacion1);
            }
            em.persist(tarjetaIngresaEstacion);
            if (tarjeta != null) {
                tarjeta.getTarjetaIngresaEstacionList().add(tarjetaIngresaEstacion);
                tarjeta = em.merge(tarjeta);
            }
            if (estacion != null) {
                estacion.getTarjetaIngresaEstacionList().add(tarjetaIngresaEstacion);
                estacion = em.merge(estacion);
            }
            if (estacion1 != null) {
                estacion1.getTarjetaIngresaEstacionList().add(tarjetaIngresaEstacion);
                estacion1 = em.merge(estacion1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTarjetaIngresaEstacion(tarjetaIngresaEstacion.getTarjetaIngresaEstacionPK()) != null) {
                throw new PreexistingEntityException("TarjetaIngresaEstacion " + tarjetaIngresaEstacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TarjetaIngresaEstacion tarjetaIngresaEstacion) throws NonexistentEntityException, Exception {
        tarjetaIngresaEstacion.getTarjetaIngresaEstacionPK().setPinTarjeta(tarjetaIngresaEstacion.getTarjeta().getPin());
        tarjetaIngresaEstacion.getTarjetaIngresaEstacionPK().setUbicacionEstacion(tarjetaIngresaEstacion.getEstacion1().getUbicacion());
        tarjetaIngresaEstacion.getTarjetaIngresaEstacionPK().setNombreEstacion(tarjetaIngresaEstacion.getEstacion().getNombreEstacion());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TarjetaIngresaEstacion persistentTarjetaIngresaEstacion = em.find(TarjetaIngresaEstacion.class, tarjetaIngresaEstacion.getTarjetaIngresaEstacionPK());
            Tarjeta tarjetaOld = persistentTarjetaIngresaEstacion.getTarjeta();
            Tarjeta tarjetaNew = tarjetaIngresaEstacion.getTarjeta();
            Estacion estacionOld = persistentTarjetaIngresaEstacion.getEstacion();
            Estacion estacionNew = tarjetaIngresaEstacion.getEstacion();
            Estacion estacion1Old = persistentTarjetaIngresaEstacion.getEstacion1();
            Estacion estacion1New = tarjetaIngresaEstacion.getEstacion1();
            if (tarjetaNew != null) {
                tarjetaNew = em.getReference(tarjetaNew.getClass(), tarjetaNew.getPin());
                tarjetaIngresaEstacion.setTarjeta(tarjetaNew);
            }
            if (estacionNew != null) {
                estacionNew = em.getReference(estacionNew.getClass(), estacionNew.getNombreEstacion());
                tarjetaIngresaEstacion.setEstacion(estacionNew);
            }
            if (estacion1New != null) {
                estacion1New = em.getReference(estacion1New.getClass(), estacion1New.getNombreEstacion());
                tarjetaIngresaEstacion.setEstacion1(estacion1New);
            }
            tarjetaIngresaEstacion = em.merge(tarjetaIngresaEstacion);
            if (tarjetaOld != null && !tarjetaOld.equals(tarjetaNew)) {
                tarjetaOld.getTarjetaIngresaEstacionList().remove(tarjetaIngresaEstacion);
                tarjetaOld = em.merge(tarjetaOld);
            }
            if (tarjetaNew != null && !tarjetaNew.equals(tarjetaOld)) {
                tarjetaNew.getTarjetaIngresaEstacionList().add(tarjetaIngresaEstacion);
                tarjetaNew = em.merge(tarjetaNew);
            }
            if (estacionOld != null && !estacionOld.equals(estacionNew)) {
                estacionOld.getTarjetaIngresaEstacionList().remove(tarjetaIngresaEstacion);
                estacionOld = em.merge(estacionOld);
            }
            if (estacionNew != null && !estacionNew.equals(estacionOld)) {
                estacionNew.getTarjetaIngresaEstacionList().add(tarjetaIngresaEstacion);
                estacionNew = em.merge(estacionNew);
            }
            if (estacion1Old != null && !estacion1Old.equals(estacion1New)) {
                estacion1Old.getTarjetaIngresaEstacionList().remove(tarjetaIngresaEstacion);
                estacion1Old = em.merge(estacion1Old);
            }
            if (estacion1New != null && !estacion1New.equals(estacion1Old)) {
                estacion1New.getTarjetaIngresaEstacionList().add(tarjetaIngresaEstacion);
                estacion1New = em.merge(estacion1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                TarjetaIngresaEstacionPK id = tarjetaIngresaEstacion.getTarjetaIngresaEstacionPK();
                if (findTarjetaIngresaEstacion(id) == null) {
                    throw new NonexistentEntityException("The tarjetaIngresaEstacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TarjetaIngresaEstacionPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TarjetaIngresaEstacion tarjetaIngresaEstacion;
            try {
                tarjetaIngresaEstacion = em.getReference(TarjetaIngresaEstacion.class, id);
                tarjetaIngresaEstacion.getTarjetaIngresaEstacionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tarjetaIngresaEstacion with id " + id + " no longer exists.", enfe);
            }
            Tarjeta tarjeta = tarjetaIngresaEstacion.getTarjeta();
            if (tarjeta != null) {
                tarjeta.getTarjetaIngresaEstacionList().remove(tarjetaIngresaEstacion);
                tarjeta = em.merge(tarjeta);
            }
            Estacion estacion = tarjetaIngresaEstacion.getEstacion();
            if (estacion != null) {
                estacion.getTarjetaIngresaEstacionList().remove(tarjetaIngresaEstacion);
                estacion = em.merge(estacion);
            }
            Estacion estacion1 = tarjetaIngresaEstacion.getEstacion1();
            if (estacion1 != null) {
                estacion1.getTarjetaIngresaEstacionList().remove(tarjetaIngresaEstacion);
                estacion1 = em.merge(estacion1);
            }
            em.remove(tarjetaIngresaEstacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TarjetaIngresaEstacion> findTarjetaIngresaEstacionEntities() {
        return findTarjetaIngresaEstacionEntities(true, -1, -1);
    }

    public List<TarjetaIngresaEstacion> findTarjetaIngresaEstacionEntities(int maxResults, int firstResult) {
        return findTarjetaIngresaEstacionEntities(false, maxResults, firstResult);
    }

    private List<TarjetaIngresaEstacion> findTarjetaIngresaEstacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TarjetaIngresaEstacion.class));
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

    public TarjetaIngresaEstacion findTarjetaIngresaEstacion(TarjetaIngresaEstacionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TarjetaIngresaEstacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTarjetaIngresaEstacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TarjetaIngresaEstacion> rt = cq.from(TarjetaIngresaEstacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
