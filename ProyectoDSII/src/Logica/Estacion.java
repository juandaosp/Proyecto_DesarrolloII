/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dash
 */
@Entity
@Table(name = "estacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estacion.findAll", query = "SELECT e FROM Estacion e"),
    @NamedQuery(name = "Estacion.findByNombreEstacion", query = "SELECT e FROM Estacion e WHERE e.nombreEstacion = :nombreEstacion"),
    @NamedQuery(name = "Estacion.findByUbicacion", query = "SELECT e FROM Estacion e WHERE e.ubicacion = :ubicacion"),
    @NamedQuery(name = "Estacion.findByEstado", query = "SELECT e FROM Estacion e WHERE e.estado = :estado")})
public class Estacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nombre_estacion")
    private String nombreEstacion;
    @Column(name = "ubicacion")
    private String ubicacion;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @JoinTable(name = "ruta_estacion", joinColumns = {
        @JoinColumn(name = "nombre_estacion", referencedColumnName = "nombre_estacion")}, inverseJoinColumns = {
        @JoinColumn(name = "nombre_ruta", referencedColumnName = "nombre_ruta")})
    @ManyToMany
    private Collection<Ruta> rutaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estacion")
    private Collection<TarjetaIngresaEstacion> tarjetaIngresaEstacionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estacion1")
    private Collection<TarjetaIngresaEstacion> tarjetaIngresaEstacionCollection1;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empleado idEmpleado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nombreEstacion")
    private Collection<Tarjeta> tarjetaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nombreEstacion")
    private Collection<Solicitud> solicitudCollection;

    public Estacion() {
    }

    public Estacion(String nombreEstacion) {
        this.nombreEstacion = nombreEstacion;
    }

    public Estacion(String nombreEstacion, String estado) {
        this.nombreEstacion = nombreEstacion;
        this.estado = estado;
    }

    public String getNombreEstacion() {
        return nombreEstacion;
    }

    public void setNombreEstacion(String nombreEstacion) {
        this.nombreEstacion = nombreEstacion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Collection<Ruta> getRutaCollection() {
        return rutaCollection;
    }

    public void setRutaCollection(Collection<Ruta> rutaCollection) {
        this.rutaCollection = rutaCollection;
    }

    @XmlTransient
    public Collection<TarjetaIngresaEstacion> getTarjetaIngresaEstacionCollection() {
        return tarjetaIngresaEstacionCollection;
    }

    public void setTarjetaIngresaEstacionCollection(Collection<TarjetaIngresaEstacion> tarjetaIngresaEstacionCollection) {
        this.tarjetaIngresaEstacionCollection = tarjetaIngresaEstacionCollection;
    }

    @XmlTransient
    public Collection<TarjetaIngresaEstacion> getTarjetaIngresaEstacionCollection1() {
        return tarjetaIngresaEstacionCollection1;
    }

    public void setTarjetaIngresaEstacionCollection1(Collection<TarjetaIngresaEstacion> tarjetaIngresaEstacionCollection1) {
        this.tarjetaIngresaEstacionCollection1 = tarjetaIngresaEstacionCollection1;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    @XmlTransient
    public Collection<Tarjeta> getTarjetaCollection() {
        return tarjetaCollection;
    }

    public void setTarjetaCollection(Collection<Tarjeta> tarjetaCollection) {
        this.tarjetaCollection = tarjetaCollection;
    }

    @XmlTransient
    public Collection<Solicitud> getSolicitudCollection() {
        return solicitudCollection;
    }

    public void setSolicitudCollection(Collection<Solicitud> solicitudCollection) {
        this.solicitudCollection = solicitudCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombreEstacion != null ? nombreEstacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estacion)) {
            return false;
        }
        Estacion other = (Estacion) object;
        if ((this.nombreEstacion == null && other.nombreEstacion != null) || (this.nombreEstacion != null && !this.nombreEstacion.equals(other.nombreEstacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Logica.Estacion[ nombreEstacion=" + nombreEstacion + " ]";
    }
    
}
