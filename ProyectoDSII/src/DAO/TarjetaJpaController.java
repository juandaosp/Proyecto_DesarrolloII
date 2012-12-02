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
import Logica.Recarga;
import Logica.Estacion;
import Logica.Tarjeta;
import Logica.TarjetaIngresaEstacion;
import java.util.ArrayList;
import java.util.Collection;
import Logica.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dash
 */
public class TarjetaJpaController implements Serializable {

    public TarjetaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tarjeta tarjeta) throws PreexistingEntityException, Exception {
        if (tarjeta.getTarjetaIngresaEstacionCollection() == null) {
            tarjeta.setTarjetaIngresaEstacionCollection(new ArrayList<TarjetaIngresaEstacion>());
        }
        if (tarjeta.getUsuarioCollection() == null) {
            tarjeta.setUsuarioCollection(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Recarga recarga = tarjeta.getRecarga();
            if (recarga != null) {
                recarga = em.getReference(recarga.getClass(), recarga.getPinTarjeta());
                tarjeta.setRecarga(recarga);
            }
            Estacion nombreEstacion = tarjeta.getNombreEstacion();
            if (nombreEstacion != null) {
                nombreEstacion = em.getReference(nombreEstacion.getClass(), nombreEstacion.getNombreEstacion());
                tarjeta.setNombreEstacion(nombreEstacion);
            }
            Collection<TarjetaIngresaEstacion> attachedTarjetaIngresaEstacionCollection = new ArrayList<TarjetaIngresaEstacion>();
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionCollectionTarjetaIngresaEstacionToAttach : tarjeta.getTarjetaIngresaEstacionCollection()) {
                tarjetaIngresaEstacionCollectionTarjetaIngresaEstacionToAttach = em.getReference(tarjetaIngresaEstacionCollectionTarjetaIngresaEstacionToAttach.getClass(), tarjetaIngresaEstacionCollectionTarjetaIngresaEstacionToAttach.getTarjetaIngresaEstacionPK());
                attachedTarjetaIngresaEstacionCollection.add(tarjetaIngresaEstacionCollectionTarjetaIngresaEstacionToAttach);
            }
            tarjeta.setTarjetaIngresaEstacionCollection(attachedTarjetaIngresaEstacionCollection);
            Collection<Usuario> attachedUsuarioCollection = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionUsuarioToAttach : tarjeta.getUsuarioCollection()) {
                usuarioCollectionUsuarioToAttach = em.getReference(usuarioCollectionUsuarioToAttach.getClass(), usuarioCollectionUsuarioToAttach.getIdUsuario());
                attachedUsuarioCollection.add(usuarioCollectionUsuarioToAttach);
            }
            tarjeta.setUsuarioCollection(attachedUsuarioCollection);
            em.persist(tarjeta);
            if (recarga != null) {
                Tarjeta oldTarjetaOfRecarga = recarga.getTarjeta();
                if (oldTarjetaOfRecarga != null) {
                    oldTarjetaOfRecarga.setRecarga(null);
                    oldTarjetaOfRecarga = em.merge(oldTarjetaOfRecarga);
                }
                recarga.setTarjeta(tarjeta);
                recarga = em.merge(recarga);
            }
            if (nombreEstacion != null) {
                nombreEstacion.getTarjetaCollection().add(tarjeta);
                nombreEstacion = em.merge(nombreEstacion);
            }
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionCollectionTarjetaIngresaEstacion : tarjeta.getTarjetaIngresaEstacionCollection()) {
                Tarjeta oldTarjetaOfTarjetaIngresaEstacionCollectionTarjetaIngresaEstacion = tarjetaIngresaEstacionCollectionTarjetaIngresaEstacion.getTarjeta();
                tarjetaIngresaEstacionCollectionTarjetaIngresaEstacion.setTarjeta(tarjeta);
                tarjetaIngresaEstacionCollectionTarjetaIngresaEstacion = em.merge(tarjetaIngresaEstacionCollectionTarjetaIngresaEstacion);
                if (oldTarjetaOfTarjetaIngresaEstacionCollectionTarjetaIngresaEstacion != null) {
                    oldTarjetaOfTarjetaIngresaEstacionCollectionTarjetaIngresaEstacion.getTarjetaIngresaEstacionCollection().remove(tarjetaIngresaEstacionCollectionTarjetaIngresaEstacion);
                    oldTarjetaOfTarjetaIngresaEstacionCollectionTarjetaIngresaEstacion = em.merge(oldTarjetaOfTarjetaIngresaEstacionCollectionTarjetaIngresaEstacion);
                }
            }
            for (Usuario usuarioCollectionUsuario : tarjeta.getUsuarioCollection()) {
                Tarjeta oldPinTarjetaOfUsuarioCollectionUsuario = usuarioCollectionUsuario.getPinTarjeta();
                usuarioCollectionUsuario.setPinTarjeta(tarjeta);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
                if (oldPinTarjetaOfUsuarioCollectionUsuario != null) {
                    oldPinTarjetaOfUsuarioCollectionUsuario.getUsuarioCollection().remove(usuarioCollectionUsuario);
                    oldPinTarjetaOfUsuarioCollectionUsuario = em.merge(oldPinTarjetaOfUsuarioCollectionUsuario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTarjeta(tarjeta.getPin()) != null) {
                throw new PreexistingEntityException("Tarjeta " + tarjeta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tarjeta tarjeta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tarjeta persistentTarjeta = em.find(Tarjeta.class, tarjeta.getPin());
            Recarga recargaOld = persistentTarjeta.getRecarga();
            Recarga recargaNew = tarjeta.getRecarga();
            Estacion nombreEstacionOld = persistentTarjeta.getNombreEstacion();
            Estacion nombreEstacionNew = tarjeta.getNombreEstacion();
            Collection<TarjetaIngresaEstacion> tarjetaIngresaEstacionCollectionOld = persistentTarjeta.getTarjetaIngresaEstacionCollection();
            Collection<TarjetaIngresaEstacion> tarjetaIngresaEstacionCollectionNew = tarjeta.getTarjetaIngresaEstacionCollection();
            Collection<Usuario> usuarioCollectionOld = persistentTarjeta.getUsuarioCollection();
            Collection<Usuario> usuarioCollectionNew = tarjeta.getUsuarioCollection();
            List<String> illegalOrphanMessages = null;
            if (recargaOld != null && !recargaOld.equals(recargaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Recarga " + recargaOld + " since its tarjeta field is not nullable.");
            }
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionCollectionOldTarjetaIngresaEstacion : tarjetaIngresaEstacionCollectionOld) {
                if (!tarjetaIngresaEstacionCollectionNew.contains(tarjetaIngresaEstacionCollectionOldTarjetaIngresaEstacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TarjetaIngresaEstacion " + tarjetaIngresaEstacionCollectionOldTarjetaIngresaEstacion + " since its tarjeta field is not nullable.");
                }
            }
            for (Usuario usuarioCollectionOldUsuario : usuarioCollectionOld) {
                if (!usuarioCollectionNew.contains(usuarioCollectionOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioCollectionOldUsuario + " since its pinTarjeta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (recargaNew != null) {
                recargaNew = em.getReference(recargaNew.getClass(), recargaNew.getPinTarjeta());
                tarjeta.setRecarga(recargaNew);
            }
            if (nombreEstacionNew != null) {
                nombreEstacionNew = em.getReference(nombreEstacionNew.getClass(), nombreEstacionNew.getNombreEstacion());
                tarjeta.setNombreEstacion(nombreEstacionNew);
            }
            Collection<TarjetaIngresaEstacion> attachedTarjetaIngresaEstacionCollectionNew = new ArrayList<TarjetaIngresaEstacion>();
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacionToAttach : tarjetaIngresaEstacionCollectionNew) {
                tarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacionToAttach = em.getReference(tarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacionToAttach.getClass(), tarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacionToAttach.getTarjetaIngresaEstacionPK());
                attachedTarjetaIngresaEstacionCollectionNew.add(tarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacionToAttach);
            }
            tarjetaIngresaEstacionCollectionNew = attachedTarjetaIngresaEstacionCollectionNew;
            tarjeta.setTarjetaIngresaEstacionCollection(tarjetaIngresaEstacionCollectionNew);
            Collection<Usuario> attachedUsuarioCollectionNew = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionNewUsuarioToAttach : usuarioCollectionNew) {
                usuarioCollectionNewUsuarioToAttach = em.getReference(usuarioCollectionNewUsuarioToAttach.getClass(), usuarioCollectionNewUsuarioToAttach.getIdUsuario());
                attachedUsuarioCollectionNew.add(usuarioCollectionNewUsuarioToAttach);
            }
            usuarioCollectionNew = attachedUsuarioCollectionNew;
            tarjeta.setUsuarioCollection(usuarioCollectionNew);
            tarjeta = em.merge(tarjeta);
            if (recargaNew != null && !recargaNew.equals(recargaOld)) {
                Tarjeta oldTarjetaOfRecarga = recargaNew.getTarjeta();
                if (oldTarjetaOfRecarga != null) {
                    oldTarjetaOfRecarga.setRecarga(null);
                    oldTarjetaOfRecarga = em.merge(oldTarjetaOfRecarga);
                }
                recargaNew.setTarjeta(tarjeta);
                recargaNew = em.merge(recargaNew);
            }
            if (nombreEstacionOld != null && !nombreEstacionOld.equals(nombreEstacionNew)) {
                nombreEstacionOld.getTarjetaCollection().remove(tarjeta);
                nombreEstacionOld = em.merge(nombreEstacionOld);
            }
            if (nombreEstacionNew != null && !nombreEstacionNew.equals(nombreEstacionOld)) {
                nombreEstacionNew.getTarjetaCollection().add(tarjeta);
                nombreEstacionNew = em.merge(nombreEstacionNew);
            }
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacion : tarjetaIngresaEstacionCollectionNew) {
                if (!tarjetaIngresaEstacionCollectionOld.contains(tarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacion)) {
                    Tarjeta oldTarjetaOfTarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacion = tarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacion.getTarjeta();
                    tarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacion.setTarjeta(tarjeta);
                    tarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacion = em.merge(tarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacion);
                    if (oldTarjetaOfTarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacion != null && !oldTarjetaOfTarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacion.equals(tarjeta)) {
                        oldTarjetaOfTarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacion.getTarjetaIngresaEstacionCollection().remove(tarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacion);
                        oldTarjetaOfTarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacion = em.merge(oldTarjetaOfTarjetaIngresaEstacionCollectionNewTarjetaIngresaEstacion);
                    }
                }
            }
            for (Usuario usuarioCollectionNewUsuario : usuarioCollectionNew) {
                if (!usuarioCollectionOld.contains(usuarioCollectionNewUsuario)) {
                    Tarjeta oldPinTarjetaOfUsuarioCollectionNewUsuario = usuarioCollectionNewUsuario.getPinTarjeta();
                    usuarioCollectionNewUsuario.setPinTarjeta(tarjeta);
                    usuarioCollectionNewUsuario = em.merge(usuarioCollectionNewUsuario);
                    if (oldPinTarjetaOfUsuarioCollectionNewUsuario != null && !oldPinTarjetaOfUsuarioCollectionNewUsuario.equals(tarjeta)) {
                        oldPinTarjetaOfUsuarioCollectionNewUsuario.getUsuarioCollection().remove(usuarioCollectionNewUsuario);
                        oldPinTarjetaOfUsuarioCollectionNewUsuario = em.merge(oldPinTarjetaOfUsuarioCollectionNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tarjeta.getPin();
                if (findTarjeta(id) == null) {
                    throw new NonexistentEntityException("The tarjeta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tarjeta tarjeta;
            try {
                tarjeta = em.getReference(Tarjeta.class, id);
                tarjeta.getPin();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tarjeta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Recarga recargaOrphanCheck = tarjeta.getRecarga();
            if (recargaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tarjeta (" + tarjeta + ") cannot be destroyed since the Recarga " + recargaOrphanCheck + " in its recarga field has a non-nullable tarjeta field.");
            }
            Collection<TarjetaIngresaEstacion> tarjetaIngresaEstacionCollectionOrphanCheck = tarjeta.getTarjetaIngresaEstacionCollection();
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionCollectionOrphanCheckTarjetaIngresaEstacion : tarjetaIngresaEstacionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tarjeta (" + tarjeta + ") cannot be destroyed since the TarjetaIngresaEstacion " + tarjetaIngresaEstacionCollectionOrphanCheckTarjetaIngresaEstacion + " in its tarjetaIngresaEstacionCollection field has a non-nullable tarjeta field.");
            }
            Collection<Usuario> usuarioCollectionOrphanCheck = tarjeta.getUsuarioCollection();
            for (Usuario usuarioCollectionOrphanCheckUsuario : usuarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tarjeta (" + tarjeta + ") cannot be destroyed since the Usuario " + usuarioCollectionOrphanCheckUsuario + " in its usuarioCollection field has a non-nullable pinTarjeta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Estacion nombreEstacion = tarjeta.getNombreEstacion();
            if (nombreEstacion != null) {
                nombreEstacion.getTarjetaCollection().remove(tarjeta);
                nombreEstacion = em.merge(nombreEstacion);
            }
            em.remove(tarjeta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tarjeta> findTarjetaEntities() {
        return findTarjetaEntities(true, -1, -1);
    }

    public List<Tarjeta> findTarjetaEntities(int maxResults, int firstResult) {
        return findTarjetaEntities(false, maxResults, firstResult);
    }

    private List<Tarjeta> findTarjetaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tarjeta.class));
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

    public Tarjeta findTarjeta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tarjeta.class, id);
        } finally {
            em.close();
        }
    }

    public int getTarjetaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tarjeta> rt = cq.from(Tarjeta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
