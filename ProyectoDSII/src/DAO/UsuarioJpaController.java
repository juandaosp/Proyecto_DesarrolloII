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
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
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
        if (usuario.getSolicitudList() == null) {
            usuario.setSolicitudList(new ArrayList<Solicitud>());
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
            List<Solicitud> attachedSolicitudList = new ArrayList<Solicitud>();
            for (Solicitud solicitudListSolicitudToAttach : usuario.getSolicitudList()) {
                solicitudListSolicitudToAttach = em.getReference(solicitudListSolicitudToAttach.getClass(), solicitudListSolicitudToAttach.getTicket());
                attachedSolicitudList.add(solicitudListSolicitudToAttach);
            }
            usuario.setSolicitudList(attachedSolicitudList);
            em.persist(usuario);
            if (pinTarjeta != null) {
                pinTarjeta.getUsuarioList().add(usuario);
                pinTarjeta = em.merge(pinTarjeta);
            }
            for (Solicitud solicitudListSolicitud : usuario.getSolicitudList()) {
                Usuario oldIdUsuarioOfSolicitudListSolicitud = solicitudListSolicitud.getIdUsuario();
                solicitudListSolicitud.setIdUsuario(usuario);
                solicitudListSolicitud = em.merge(solicitudListSolicitud);
                if (oldIdUsuarioOfSolicitudListSolicitud != null) {
                    oldIdUsuarioOfSolicitudListSolicitud.getSolicitudList().remove(solicitudListSolicitud);
                    oldIdUsuarioOfSolicitudListSolicitud = em.merge(oldIdUsuarioOfSolicitudListSolicitud);
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
            List<Solicitud> solicitudListOld = persistentUsuario.getSolicitudList();
            List<Solicitud> solicitudListNew = usuario.getSolicitudList();
            List<String> illegalOrphanMessages = null;
            for (Solicitud solicitudListOldSolicitud : solicitudListOld) {
                if (!solicitudListNew.contains(solicitudListOldSolicitud)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Solicitud " + solicitudListOldSolicitud + " since its idUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pinTarjetaNew != null) {
                pinTarjetaNew = em.getReference(pinTarjetaNew.getClass(), pinTarjetaNew.getPin());
                usuario.setPinTarjeta(pinTarjetaNew);
            }
            List<Solicitud> attachedSolicitudListNew = new ArrayList<Solicitud>();
            for (Solicitud solicitudListNewSolicitudToAttach : solicitudListNew) {
                solicitudListNewSolicitudToAttach = em.getReference(solicitudListNewSolicitudToAttach.getClass(), solicitudListNewSolicitudToAttach.getTicket());
                attachedSolicitudListNew.add(solicitudListNewSolicitudToAttach);
            }
            solicitudListNew = attachedSolicitudListNew;
            usuario.setSolicitudList(solicitudListNew);
            usuario = em.merge(usuario);
            if (pinTarjetaOld != null && !pinTarjetaOld.equals(pinTarjetaNew)) {
                pinTarjetaOld.getUsuarioList().remove(usuario);
                pinTarjetaOld = em.merge(pinTarjetaOld);
            }
            if (pinTarjetaNew != null && !pinTarjetaNew.equals(pinTarjetaOld)) {
                pinTarjetaNew.getUsuarioList().add(usuario);
                pinTarjetaNew = em.merge(pinTarjetaNew);
            }
            for (Solicitud solicitudListNewSolicitud : solicitudListNew) {
                if (!solicitudListOld.contains(solicitudListNewSolicitud)) {
                    Usuario oldIdUsuarioOfSolicitudListNewSolicitud = solicitudListNewSolicitud.getIdUsuario();
                    solicitudListNewSolicitud.setIdUsuario(usuario);
                    solicitudListNewSolicitud = em.merge(solicitudListNewSolicitud);
                    if (oldIdUsuarioOfSolicitudListNewSolicitud != null && !oldIdUsuarioOfSolicitudListNewSolicitud.equals(usuario)) {
                        oldIdUsuarioOfSolicitudListNewSolicitud.getSolicitudList().remove(solicitudListNewSolicitud);
                        oldIdUsuarioOfSolicitudListNewSolicitud = em.merge(oldIdUsuarioOfSolicitudListNewSolicitud);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getIdUsuario();
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

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<Solicitud> solicitudListOrphanCheck = usuario.getSolicitudList();
            for (Solicitud solicitudListOrphanCheckSolicitud : solicitudListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Solicitud " + solicitudListOrphanCheckSolicitud + " in its solicitudList field has a non-nullable idUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Tarjeta pinTarjeta = usuario.getPinTarjeta();
            if (pinTarjeta != null) {
                pinTarjeta.getUsuarioList().remove(usuario);
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

    public Usuario findUsuario(String id) {
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
