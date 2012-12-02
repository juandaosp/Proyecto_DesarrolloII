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
 * @author Dash
 */
@Entity
@Table(name = "tarjeta_ingresa_estacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TarjetaIngresaEstacion.findAll", query = "SELECT t FROM TarjetaIngresaEstacion t"),
    @NamedQuery(name = "TarjetaIngresaEstacion.findByNombreEstacion", query = "SELECT t FROM TarjetaIngresaEstacion t WHERE t.tarjetaIngresaEstacionPK.nombreEstacion = :nombreEstacion"),
    @NamedQuery(name = "TarjetaIngresaEstacion.findByUbicacionEstacion", query = "SELECT t FROM TarjetaIngresaEstacion t WHERE t.tarjetaIngresaEstacionPK.ubicacionEstacion = :ubicacionEstacion"),
    @NamedQuery(name = "TarjetaIngresaEstacion.findByPinTarjeta", query = "SELECT t FROM TarjetaIngresaEstacion t WHERE t.tarjetaIngresaEstacionPK.pinTarjeta = :pinTarjeta"),
    @NamedQuery(name = "TarjetaIngresaEstacion.findByFecha", query = "SELECT t FROM TarjetaIngresaEstacion t WHERE t.fecha = :fecha")})
public class TarjetaIngresaEstacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TarjetaIngresaEstacionPK tarjetaIngresaEstacionPK;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "pin_tarjeta", referencedColumnName = "pin", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Tarjeta tarjeta;
    @JoinColumn(name = "nombre_estacion", referencedColumnName = "nombre_estacion", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Estacion estacion;
    @JoinColumn(name = "ubicacion_estacion", referencedColumnName = "ubicacion", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Estacion estacion1;

    public TarjetaIngresaEstacion() {
    }

    public TarjetaIngresaEstacion(TarjetaIngresaEstacionPK tarjetaIngresaEstacionPK) {
        this.tarjetaIngresaEstacionPK = tarjetaIngresaEstacionPK;
    }

    public TarjetaIngresaEstacion(TarjetaIngresaEstacionPK tarjetaIngresaEstacionPK, Date fecha) {
        this.tarjetaIngresaEstacionPK = tarjetaIngresaEstacionPK;
        this.fecha = fecha;
    }

    public TarjetaIngresaEstacion(String nombreEstacion, String ubicacionEstacion, int pinTarjeta) {
        this.tarjetaIngresaEstacionPK = new TarjetaIngresaEstacionPK(nombreEstacion, ubicacionEstacion, pinTarjeta);
    }

    public TarjetaIngresaEstacionPK getTarjetaIngresaEstacionPK() {
        return tarjetaIngresaEstacionPK;
    }

    public void setTarjetaIngresaEstacionPK(TarjetaIngresaEstacionPK tarjetaIngresaEstacionPK) {
        this.tarjetaIngresaEstacionPK = tarjetaIngresaEstacionPK;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public Estacion getEstacion() {
        return estacion;
    }

    public void setEstacion(Estacion estacion) {
        this.estacion = estacion;
    }

    public Estacion getEstacion1() {
        return estacion1;
    }

    public void setEstacion1(Estacion estacion1) {
        this.estacion1 = estacion1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tarjetaIngresaEstacionPK != null ? tarjetaIngresaEstacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TarjetaIngresaEstacion)) {
            return false;
        }
        TarjetaIngresaEstacion other = (TarjetaIngresaEstacion) object;
        if ((this.tarjetaIngresaEstacionPK == null && other.tarjetaIngresaEstacionPK != null) || (this.tarjetaIngresaEstacionPK != null && !this.tarjetaIngresaEstacionPK.equals(other.tarjetaIngresaEstacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Logica.TarjetaIngresaEstacion[ tarjetaIngresaEstacionPK=" + tarjetaIngresaEstacionPK + " ]";
    }
    
}
