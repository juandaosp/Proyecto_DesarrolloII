/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Logica.Empleado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Logica.Programacion;
import java.util.ArrayList;
import java.util.List;
import Logica.Estacion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) throws PreexistingEntityException, Exception {
        if (empleado.getProgramacionList() == null) {
            empleado.setProgramacionList(new ArrayList<Programacion>());
        }
        if (empleado.getEstacionList() == null) {
            empleado.setEstacionList(new ArrayList<Estacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Programacion> attachedProgramacionList = new ArrayList<Programacion>();
            for (Programacion programacionListProgramacionToAttach : empleado.getProgramacionList()) {
                programacionListProgramacionToAttach = em.getReference(programacionListProgramacionToAttach.getClass(), programacionListProgramacionToAttach.getProgramacionPK());
                attachedProgramacionList.add(programacionListProgramacionToAttach);
            }
            empleado.setProgramacionList(attachedProgramacionList);
            List<Estacion> attachedEstacionList = new ArrayList<Estacion>();
            for (Estacion estacionListEstacionToAttach : empleado.getEstacionList()) {
                estacionListEstacionToAttach = em.getReference(estacionListEstacionToAttach.getClass(), estacionListEstacionToAttach.getNombreEstacion());
                attachedEstacionList.add(estacionListEstacionToAttach);
            }
            empleado.setEstacionList(attachedEstacionList);
            em.persist(empleado);
            for (Programacion programacionListProgramacion : empleado.getProgramacionList()) {
                Empleado oldEmpleadoOfProgramacionListProgramacion = programacionListProgramacion.getEmpleado();
                programacionListProgramacion.setEmpleado(empleado);
                programacionListProgramacion = em.merge(programacionListProgramacion);
                if (oldEmpleadoOfProgramacionListProgramacion != null) {
                    oldEmpleadoOfProgramacionListProgramacion.getProgramacionList().remove(programacionListProgramacion);
                    oldEmpleadoOfProgramacionListProgramacion = em.merge(oldEmpleadoOfProgramacionListProgramacion);
                }
            }
            for (Estacion estacionListEstacion : empleado.getEstacionList()) {
                Empleado oldIdEmpleadoOfEstacionListEstacion = estacionListEstacion.getIdEmpleado();
                estacionListEstacion.setIdEmpleado(empleado);
                estacionListEstacion = em.merge(estacionListEstacion);
                if (oldIdEmpleadoOfEstacionListEstacion != null) {
                    oldIdEmpleadoOfEstacionListEstacion.getEstacionList().remove(estacionListEstacion);
                    oldIdEmpleadoOfEstacionListEstacion = em.merge(oldIdEmpleadoOfEstacionListEstacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpleado(empleado.getId()) != null) {
                throw new PreexistingEntityException("Empleado " + empleado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getId());
            List<Programacion> programacionListOld = persistentEmpleado.getProgramacionList();
            List<Programacion> programacionListNew = empleado.getProgramacionList();
            List<Estacion> estacionListOld = persistentEmpleado.getEstacionList();
            List<Estacion> estacionListNew = empleado.getEstacionList();
            List<String> illegalOrphanMessages = null;
            for (Programacion programacionListOldProgramacion : programacionListOld) {
                if (!programacionListNew.contains(programacionListOldProgramacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Programacion " + programacionListOldProgramacion + " since its empleado field is not nullable.");
                }
            }
            for (Estacion estacionListOldEstacion : estacionListOld) {
                if (!estacionListNew.contains(estacionListOldEstacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Estacion " + estacionListOldEstacion + " since its idEmpleado field is not nullable.");
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
            empleado.setProgramacionList(programacionListNew);
            List<Estacion> attachedEstacionListNew = new ArrayList<Estacion>();
            for (Estacion estacionListNewEstacionToAttach : estacionListNew) {
                estacionListNewEstacionToAttach = em.getReference(estacionListNewEstacionToAttach.getClass(), estacionListNewEstacionToAttach.getNombreEstacion());
                attachedEstacionListNew.add(estacionListNewEstacionToAttach);
            }
            estacionListNew = attachedEstacionListNew;
            empleado.setEstacionList(estacionListNew);
            empleado = em.merge(empleado);
            for (Programacion programacionListNewProgramacion : programacionListNew) {
                if (!programacionListOld.contains(programacionListNewProgramacion)) {
                    Empleado oldEmpleadoOfProgramacionListNewProgramacion = programacionListNewProgramacion.getEmpleado();
                    programacionListNewProgramacion.setEmpleado(empleado);
                    programacionListNewProgramacion = em.merge(programacionListNewProgramacion);
                    if (oldEmpleadoOfProgramacionListNewProgramacion != null && !oldEmpleadoOfProgramacionListNewProgramacion.equals(empleado)) {
                        oldEmpleadoOfProgramacionListNewProgramacion.getProgramacionList().remove(programacionListNewProgramacion);
                        oldEmpleadoOfProgramacionListNewProgramacion = em.merge(oldEmpleadoOfProgramacionListNewProgramacion);
                    }
                }
            }
            for (Estacion estacionListNewEstacion : estacionListNew) {
                if (!estacionListOld.contains(estacionListNewEstacion)) {
                    Empleado oldIdEmpleadoOfEstacionListNewEstacion = estacionListNewEstacion.getIdEmpleado();
                    estacionListNewEstacion.setIdEmpleado(empleado);
                    estacionListNewEstacion = em.merge(estacionListNewEstacion);
                    if (oldIdEmpleadoOfEstacionListNewEstacion != null && !oldIdEmpleadoOfEstacionListNewEstacion.equals(empleado)) {
                        oldIdEmpleadoOfEstacionListNewEstacion.getEstacionList().remove(estacionListNewEstacion);
                        oldIdEmpleadoOfEstacionListNewEstacion = em.merge(oldIdEmpleadoOfEstacionListNewEstacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = empleado.getId();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Programacion> programacionListOrphanCheck = empleado.getProgramacionList();
            for (Programacion programacionListOrphanCheckProgramacion : programacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleado (" + empleado + ") cannot be destroyed since the Programacion " + programacionListOrphanCheckProgramacion + " in its programacionList field has a non-nullable empleado field.");
            }
            List<Estacion> estacionListOrphanCheck = empleado.getEstacionList();
            for (Estacion estacionListOrphanCheckEstacion : estacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleado (" + empleado + ") cannot be destroyed since the Estacion " + estacionListOrphanCheckEstacion + " in its estacionList field has a non-nullable idEmpleado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(empleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
