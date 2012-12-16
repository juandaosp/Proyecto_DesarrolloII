/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "tarjeta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tarjeta.findAll", query = "SELECT t FROM Tarjeta t"),
    @NamedQuery(name = "Tarjeta.findByPin", query = "SELECT t FROM Tarjeta t WHERE t.pin = :pin"),
    @NamedQuery(name = "Tarjeta.findByCosto", query = "SELECT t FROM Tarjeta t WHERE t.costo = :costo"),
    @NamedQuery(name = "Tarjeta.findByTipo", query = "SELECT t FROM Tarjeta t WHERE t.tipo = :tipo"),
    @NamedQuery(name = "Tarjeta.findByEstado", query = "SELECT t FROM Tarjeta t WHERE t.estado = :estado"),
    @NamedQuery(name = "Tarjeta.findByPasajes", query = "SELECT t FROM Tarjeta t WHERE t.pasajes = :pasajes"),
    @NamedQuery(name = "Tarjeta.findByFecha", query = "SELECT t FROM Tarjeta t WHERE t.fecha = :fecha")})
public class Tarjeta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "pin")
    private String pin;
    @Basic(optional = false)
    @Column(name = "costo")
    private int costo;
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @Column(name = "pasajes")
    private int pasajes;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "tarjeta")
    private Recarga recarga;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tarjeta")
    private List<TarjetaIngresaEstacion> tarjetaIngresaEstacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pinTarjeta")
    private List<Usuario> usuarioList;
    @JoinColumn(name = "nombre_estacion", referencedColumnName = "nombre_estacion")
    @ManyToOne(optional = false)
    private Estacion nombreEstacion;

    public Tarjeta() {
    }

    public Tarjeta(String pin) {
        this.pin = pin;
    }

    public Tarjeta(String pin, int costo, String tipo, String estado, int pasajes, Date fecha) {
        this.pin = pin;
        this.costo = costo;
        this.tipo = tipo;
        this.estado = estado;
        this.pasajes = pasajes;
        this.fecha = fecha;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getPasajes() {
        return pasajes;
    }

    public void setPasajes(int pasajes) {
        this.pasajes = pasajes;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Recarga getRecarga() {
        return recarga;
    }

    public void setRecarga(Recarga recarga) {
        this.recarga = recarga;
    }

    @XmlTransient
    public List<TarjetaIngresaEstacion> getTarjetaIngresaEstacionList() {
        return tarjetaIngresaEstacionList;
    }

    public void setTarjetaIngresaEstacionList(List<TarjetaIngresaEstacion> tarjetaIngresaEstacionList) {
        this.tarjetaIngresaEstacionList = tarjetaIngresaEstacionList;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public Estacion getNombreEstacion() {
        return nombreEstacion;
    }

    public void setNombreEstacion(Estacion nombreEstacion) {
        this.nombreEstacion = nombreEstacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pin != null ? pin.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tarjeta)) {
            return false;
        }
        Tarjeta other = (Tarjeta) object;
        if ((this.pin == null && other.pin != null) || (this.pin != null && !this.pin.equals(other.pin))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Logica.Tarjeta[ pin=" + pin + " ]";
    }
    
}
