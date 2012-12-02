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
import Logica.Tarjeta;
import Logica.Solicitud;
import Logica.Usuario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dash
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getSolicitudCollection() == null) {
            usuario.setSolicitudCollection(new ArrayList<Solicitud>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tarjeta pinTarjeta = usuario.getPinTarjeta();
            if (pinTarjeta != null) {
                pinTarjeta = em.getReference(pinTarjeta.getClass(), pinTarjeta.getPin());
                usuario.setPinTarjeta(pinTarjeta);
            }
            Collection<Solicitud> attachedSolicitudCollection = new ArrayList<Solicitud>();
            for (Solicitud solicitudCollectionSolicitudToAttach : usuario.getSolicitudCollection()) {
                solicitudCollectionSolicitudToAttach = em.getReference(solicitudCollectionSolicitudToAttach.getClass(), solicitudCollectionSolicitudToAttach.getTicket());
                attachedSolicitudCollection.add(solicitudCollectionSolicitudToAttach);
            }
            usuario.setSolicitudCollection(attachedSolicitudCollection);
            em.persist(usuario);
            if (pinTarjeta != null) {
                pinTarjeta.getUsuarioCollection().add(usuario);
                pinTarjeta = em.merge(pinTarjeta);
            }
            for (Solicitud solicitudCollectionSolicitud : usuario.getSolicitudCollection()) {
                Usuario oldIdUsuarioOfSolicitudCollectionSolicitud = solicitudCollectionSolicitud.getIdUsuario();
                solicitudCollectionSolicitud.setIdUsuario(usuario);
                solicitudCollectionSolicitud = em.merge(solicitudCollectionSolicitud);
                if (oldIdUsuarioOfSolicitudCollectionSolicitud != null) {
                    oldIdUsuarioOfSolicitudCollectionSolicitud.getSolicitudCollection().remove(solicitudCollectionSolicitud);
                    oldIdUsuarioOfSolicitudCollectionSolicitud = em.merge(oldIdUsuarioOfSolicitudCollectionSolicitud);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getIdUsuario()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdUsuario());
            Tarjeta pinTarjetaOld = persistentUsuario.getPinTarjeta();
            Tarjeta pinTarjetaNew = usuario.getPinTarjeta();
            Collection<Solicitud> solicitudCollectionOld = persistentUsuario.getSolicitudCollection();
            Collection<Solicitud> solicitudCollectionNew = usuario.getSolicitudCollection();
            List<String> illegalOrphanMessages = null;
            for (Solicitud solicitudCollectionOldSolicitud : solicitudCollectionOld) {
                if (!solicitudCollectionNew.contains(solicitudCollectionOldSolicitud)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Solicitud " + solicitudCollectionOldSolicitud + " since its idUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pinTarjetaNew != null) {
                pinTarjetaNew = em.getReference(pinTarjetaNew.getClass(), pinTarjetaNew.getPin());
                usuario.setPinTarjeta(pinTarjetaNew);
            }
            Collection<Solicitud> attachedSolicitudCollectionNew = new ArrayList<Solicitud>();
            for (Solicitud solicitudCollectionNewSolicitudToAttach : solicitudCollectionNew) {
                solicitudCollectionNewSolicitudToAttach = em.getReference(solicitudCollectionNewSolicitudToAttach.getClass(), solicitudCollectionNewSolicitudToAttach.getTicket());
                attachedSolicitudCollectionNew.add(solicitudCollectionNewSolicitudToAttach);
            }
            solicitudCollectionNew = attachedSolicitudCollectionNew;
            usuario.setSolicitudCollection(solicitudCollectionNew);
            usuario = em.merge(usuario);
            if (pinTarjetaOld != null && !pinTarjetaOld.equals(pinTarjetaNew)) {
                pinTarjetaOld.getUsuarioCollection().remove(usuario);
                pinTarjetaOld = em.merge(pinTarjetaOld);
            }
            if (pinTarjetaNew != null && !pinTarjetaNew.equals(pinTarjetaOld)) {
                pinTarjetaNew.getUsuarioCollection().add(usuario);
                pinTarjetaNew = em.merge(pinTarjetaNew);
            }
            for (Solicitud solicitudCollectionNewSolicitud : solicitudCollectionNew) {
                if (!solicitudCollectionOld.contains(solicitudCollectionNewSolicitud)) {
                    Usuario oldIdUsuarioOfSolicitudCollectionNewSolicitud = solicitudCollectionNewSolicitud.getIdUsuario();
                    solicitudCollectionNewSolicitud.setIdUsuario(usuario);
                    solicitudCollectionNewSolicitud = em.merge(solicitudCollectionNewSolicitud);
                    if (oldIdUsuarioOfSolicitudCollectionNewSolicitud != null && !oldIdUsuarioOfSolicitudCollectionNewSolicitud.equals(usuario)) {
                        oldIdUsuarioOfSolicitudCollectionNewSolicitud.getSolicitudCollection().remove(solicitudCollectionNewSolicitud);
                        oldIdUsuarioOfSolicitudCollectionNewSolicitud = em.merge(oldIdUsuarioOfSolicitudCollectionNewSolicitud);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getIdUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Solicitud> solicitudCollectionOrphanCheck = usuario.getSolicitudCollection();
            for (Solicitud solicitudCollectionOrphanCheckSolicitud : solicitudCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Solicitud " + solicitudCollectionOrphanCheckSolicitud + " in its solicitudCollection field has a non-nullable idUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Tarjeta pinTarjeta = usuario.getPinTarjeta();
            if (pinTarjeta != null) {
                pinTarjeta.getUsuarioCollection().remove(usuario);
                pinTarjeta = em.merge(pinTarjeta);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
