/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "programacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Programacion.findAll", query = "SELECT p FROM Programacion p"),
    @NamedQuery(name = "Programacion.findByFecha", query = "SELECT p FROM Programacion p WHERE p.fecha = :fecha"),
    @NamedQuery(name = "Programacion.findByDescripcion", query = "SELECT p FROM Programacion p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Programacion.findByIdEmpleado", query = "SELECT p FROM Programacion p WHERE p.programacionPK.idEmpleado = :idEmpleado"),
    @NamedQuery(name = "Programacion.findByNombreRuta", query = "SELECT p FROM Programacion p WHERE p.programacionPK.nombreRuta = :nombreRuta"),
    @NamedQuery(name = "Programacion.findByMatriculaBus", query = "SELECT p FROM Programacion p WHERE p.programacionPK.matriculaBus = :matriculaBus")})
public class Programacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProgramacionPK programacionPK;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "nombre_ruta", referencedColumnName = "nombre_ruta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Ruta ruta;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Empleado empleado;
    @JoinColumn(name = "matricula_bus", referencedColumnName = "matricula", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Bus bus;

    public Programacion() {
    }

    public Programacion(ProgramacionPK programacionPK) {
        this.programacionPK = programacionPK;
    }

    public Programacion(ProgramacionPK programacionPK, Date fecha, String descripcion) {
        this.programacionPK = programacionPK;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    public Programacion(String idEmpleado, String nombreRuta, String matriculaBus) {
        this.programacionPK = new ProgramacionPK(idEmpleado, nombreRuta, matriculaBus);
    }

    public ProgramacionPK getProgramacionPK() {
        return programacionPK;
    }

    public void setProgramacionPK(ProgramacionPK programacionPK) {
        this.programacionPK = programacionPK;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (programacionPK != null ? programacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Programacion)) {
            return false;
        }
        Programacion other = (Programacion) object;
        if ((this.programacionPK == null && other.programacionPK != null) || (this.programacionPK != null && !this.programacionPK.equals(other.programacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Logica.Programacion[ programacionPK=" + programacionPK + " ]";
    }
    
}
