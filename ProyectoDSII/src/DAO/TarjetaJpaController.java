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
import java.util.List;
import Logica.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
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
        if (tarjeta.getTarjetaIngresaEstacionList() == null) {
            tarjeta.setTarjetaIngresaEstacionList(new ArrayList<TarjetaIngresaEstacion>());
        }
        if (tarjeta.getUsuarioList() == null) {
            tarjeta.setUsuarioList(new ArrayList<Usuario>());
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
            List<TarjetaIngresaEstacion> attachedTarjetaIngresaEstacionList = new ArrayList<TarjetaIngresaEstacion>();
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionListTarjetaIngresaEstacionToAttach : tarjeta.getTarjetaIngresaEstacionList()) {
                tarjetaIngresaEstacionListTarjetaIngresaEstacionToAttach = em.getReference(tarjetaIngresaEstacionListTarjetaIngresaEstacionToAttach.getClass(), tarjetaIngresaEstacionListTarjetaIngresaEstacionToAttach.getTarjetaIngresaEstacionPK());
                attachedTarjetaIngresaEstacionList.add(tarjetaIngresaEstacionListTarjetaIngresaEstacionToAttach);
            }
            tarjeta.setTarjetaIngresaEstacionList(attachedTarjetaIngresaEstacionList);
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : tarjeta.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getIdUsuario());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            tarjeta.setUsuarioList(attachedUsuarioList);
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
                nombreEstacion.getTarjetaList().add(tarjeta);
                nombreEstacion = em.merge(nombreEstacion);
            }
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionListTarjetaIngresaEstacion : tarjeta.getTarjetaIngresaEstacionList()) {
                Tarjeta oldTarjetaOfTarjetaIngresaEstacionListTarjetaIngresaEstacion = tarjetaIngresaEstacionListTarjetaIngresaEstacion.getTarjeta();
                tarjetaIngresaEstacionListTarjetaIngresaEstacion.setTarjeta(tarjeta);
                tarjetaIngresaEstacionListTarjetaIngresaEstacion = em.merge(tarjetaIngresaEstacionListTarjetaIngresaEstacion);
                if (oldTarjetaOfTarjetaIngresaEstacionListTarjetaIngresaEstacion != null) {
                    oldTarjetaOfTarjetaIngresaEstacionListTarjetaIngresaEstacion.getTarjetaIngresaEstacionList().remove(tarjetaIngresaEstacionListTarjetaIngresaEstacion);
                    oldTarjetaOfTarjetaIngresaEstacionListTarjetaIngresaEstacion = em.merge(oldTarjetaOfTarjetaIngresaEstacionListTarjetaIngresaEstacion);
                }
            }
            for (Usuario usuarioListUsuario : tarjeta.getUsuarioList()) {
                Tarjeta oldPinTarjetaOfUsuarioListUsuario = usuarioListUsuario.getPinTarjeta();
                usuarioListUsuario.setPinTarjeta(tarjeta);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldPinTarjetaOfUsuarioListUsuario != null) {
                    oldPinTarjetaOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldPinTarjetaOfUsuarioListUsuario = em.merge(oldPinTarjetaOfUsuarioListUsuario);
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
            List<TarjetaIngresaEstacion> tarjetaIngresaEstacionListOld = persistentTarjeta.getTarjetaIngresaEstacionList();
            List<TarjetaIngresaEstacion> tarjetaIngresaEstacionListNew = tarjeta.getTarjetaIngresaEstacionList();
            List<Usuario> usuarioListOld = persistentTarjeta.getUsuarioList();
            List<Usuario> usuarioListNew = tarjeta.getUsuarioList();
            List<String> illegalOrphanMessages = null;
            if (recargaOld != null && !recargaOld.equals(recargaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Recarga " + recargaOld + " since its tarjeta field is not nullable.");
            }
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionListOldTarjetaIngresaEstacion : tarjetaIngresaEstacionListOld) {
                if (!tarjetaIngresaEstacionListNew.contains(tarjetaIngresaEstacionListOldTarjetaIngresaEstacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TarjetaIngresaEstacion " + tarjetaIngresaEstacionListOldTarjetaIngresaEstacion + " since its tarjeta field is not nullable.");
                }
            }
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioListOldUsuario + " since its pinTarjeta field is not nullable.");
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
            List<TarjetaIngresaEstacion> attachedTarjetaIngresaEstacionListNew = new ArrayList<TarjetaIngresaEstacion>();
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionListNewTarjetaIngresaEstacionToAttach : tarjetaIngresaEstacionListNew) {
                tarjetaIngresaEstacionListNewTarjetaIngresaEstacionToAttach = em.getReference(tarjetaIngresaEstacionListNewTarjetaIngresaEstacionToAttach.getClass(), tarjetaIngresaEstacionListNewTarjetaIngresaEstacionToAttach.getTarjetaIngresaEstacionPK());
                attachedTarjetaIngresaEstacionListNew.add(tarjetaIngresaEstacionListNewTarjetaIngresaEstacionToAttach);
            }
            tarjetaIngresaEstacionListNew = attachedTarjetaIngresaEstacionListNew;
            tarjeta.setTarjetaIngresaEstacionList(tarjetaIngresaEstacionListNew);
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getIdUsuario());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            tarjeta.setUsuarioList(usuarioListNew);
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
                nombreEstacionOld.getTarjetaList().remove(tarjeta);
                nombreEstacionOld = em.merge(nombreEstacionOld);
            }
            if (nombreEstacionNew != null && !nombreEstacionNew.equals(nombreEstacionOld)) {
                nombreEstacionNew.getTarjetaList().add(tarjeta);
                nombreEstacionNew = em.merge(nombreEstacionNew);
            }
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionListNewTarjetaIngresaEstacion : tarjetaIngresaEstacionListNew) {
                if (!tarjetaIngresaEstacionListOld.contains(tarjetaIngresaEstacionListNewTarjetaIngresaEstacion)) {
                    Tarjeta oldTarjetaOfTarjetaIngresaEstacionListNewTarjetaIngresaEstacion = tarjetaIngresaEstacionListNewTarjetaIngresaEstacion.getTarjeta();
                    tarjetaIngresaEstacionListNewTarjetaIngresaEstacion.setTarjeta(tarjeta);
                    tarjetaIngresaEstacionListNewTarjetaIngresaEstacion = em.merge(tarjetaIngresaEstacionListNewTarjetaIngresaEstacion);
                    if (oldTarjetaOfTarjetaIngresaEstacionListNewTarjetaIngresaEstacion != null && !oldTarjetaOfTarjetaIngresaEstacionListNewTarjetaIngresaEstacion.equals(tarjeta)) {
                        oldTarjetaOfTarjetaIngresaEstacionListNewTarjetaIngresaEstacion.getTarjetaIngresaEstacionList().remove(tarjetaIngresaEstacionListNewTarjetaIngresaEstacion);
                        oldTarjetaOfTarjetaIngresaEstacionListNewTarjetaIngresaEstacion = em.merge(oldTarjetaOfTarjetaIngresaEstacionListNewTarjetaIngresaEstacion);
                    }
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Tarjeta oldPinTarjetaOfUsuarioListNewUsuario = usuarioListNewUsuario.getPinTarjeta();
                    usuarioListNewUsuario.setPinTarjeta(tarjeta);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldPinTarjetaOfUsuarioListNewUsuario != null && !oldPinTarjetaOfUsuarioListNewUsuario.equals(tarjeta)) {
                        oldPinTarjetaOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldPinTarjetaOfUsuarioListNewUsuario = em.merge(oldPinTarjetaOfUsuarioListNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = tarjeta.getPin();
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

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<TarjetaIngresaEstacion> tarjetaIngresaEstacionListOrphanCheck = tarjeta.getTarjetaIngresaEstacionList();
            for (TarjetaIngresaEstacion tarjetaIngresaEstacionListOrphanCheckTarjetaIngresaEstacion : tarjetaIngresaEstacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tarjeta (" + tarjeta + ") cannot be destroyed since the TarjetaIngresaEstacion " + tarjetaIngresaEstacionListOrphanCheckTarjetaIngresaEstacion + " in its tarjetaIngresaEstacionList field has a non-nullable tarjeta field.");
            }
            List<Usuario> usuarioListOrphanCheck = tarjeta.getUsuarioList();
            for (Usuario usuarioListOrphanCheckUsuario : usuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tarjeta (" + tarjeta + ") cannot be destroyed since the Usuario " + usuarioListOrphanCheckUsuario + " in its usuarioList field has a non-nullable pinTarjeta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Estacion nombreEstacion = tarjeta.getNombreEstacion();
            if (nombreEstacion != null) {
                nombreEstacion.getTarjetaList().remove(tarjeta);
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

    public Tarjeta findTarjeta(String id) {
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
