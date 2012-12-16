/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Usuario
 */
@Embeddable
public class TarjetaIngresaEstacionPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "nombre_estacion")
    private String nombreEstacion;
    @Basic(optional = false)
    @Column(name = "ubicacion_estacion")
    private String ubicacionEstacion;
    @Basic(optional = false)
    @Column(name = "pin_tarjeta")
    private String pinTarjeta;

    public TarjetaIngresaEstacionPK() {
    }

    public TarjetaIngresaEstacionPK(String nombreEstacion, String ubicacionEstacion, String pinTarjeta) {
        this.nombreEstacion = nombreEstacion;
        this.ubicacionEstacion = ubicacionEstacion;
        this.pinTarjeta = pinTarjeta;
    }

    public String getNombreEstacion() {
        return nombreEstacion;
    }

    public void setNombreEstacion(String nombreEstacion) {
        this.nombreEstacion = nombreEstacion;
    }

    public String getUbicacionEstacion() {
        return ubicacionEstacion;
    }

    public void setUbicacionEstacion(String ubicacionEstacion) {
        this.ubicacionEstacion = ubicacionEstacion;
    }

    public String getPinTarjeta() {
        return pinTarjeta;
    }

    public void setPinTarjeta(String pinTarjeta) {
        this.pinTarjeta = pinTarjeta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombreEstacion != null ? nombreEstacion.hashCode() : 0);
        hash += (ubicacionEstacion != null ? ubicacionEstacion.hashCode() : 0);
        hash += (pinTarjeta != null ? pinTarjeta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TarjetaIngresaEstacionPK)) {
            return false;
        }
        TarjetaIngresaEstacionPK other = (TarjetaIngresaEstacionPK) object;
        if ((this.nombreEstacion == null && other.nombreEstacion != null) || (this.nombreEstacion != null && !this.nombreEstacion.equals(other.nombreEstacion))) {
            return false;
        }
        if ((this.ubicacionEstacion == null && other.ubicacionEstacion != null) || (this.ubicacionEstacion != null && !this.ubicacionEstacion.equals(other.ubicacionEstacion))) {
            return false;
        }
        if ((this.pinTarjeta == null && other.pinTarjeta != null) || (this.pinTarjeta != null && !this.pinTarjeta.equals(other.pinTarjeta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Logica.TarjetaIngresaEstacionPK[ nombreEstacion=" + nombreEstacion + ", ubicacionEstacion=" + ubicacionEstacion + ", pinTarjeta=" + pinTarjeta + " ]";
    }
    
}
