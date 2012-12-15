/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "recarga")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recarga.findAll", query = "SELECT r FROM Recarga r"),
    @NamedQuery(name = "Recarga.findByPinTarjeta", query = "SELECT r FROM Recarga r WHERE r.pinTarjeta = :pinTarjeta"),
    @NamedQuery(name = "Recarga.findByFechaRecarga", query = "SELECT r FROM Recarga r WHERE r.fechaRecarga = :fechaRecarga"),
    @NamedQuery(name = "Recarga.findByValorRecarga", query = "SELECT r FROM Recarga r WHERE r.valorRecarga = :valorRecarga")})
public class Recarga implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "pin_tarjeta")
    private String pinTarjeta;
    @Basic(optional = false)
    @Column(name = "fecha_recarga")
    @Temporal(TemporalType.DATE)
    private Date fechaRecarga;
    @Column(name = "valor_recarga")
    private Integer valorRecarga;
    @JoinColumn(name = "pin_tarjeta", referencedColumnName = "pin", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Tarjeta tarjeta;

    public Recarga() {
    }

    public Recarga(String pinTarjeta) {
        this.pinTarjeta = pinTarjeta;
    }

    public Recarga(String pinTarjeta, Date fechaRecarga) {
        this.pinTarjeta = pinTarjeta;
        this.fechaRecarga = fechaRecarga;
    }

    public String getPinTarjeta() {
        return pinTarjeta;
    }

    public void setPinTarjeta(String pinTarjeta) {
        this.pinTarjeta = pinTarjeta;
    }

    public Date getFechaRecarga() {
        return fechaRecarga;
    }

    public void setFechaRecarga(Date fechaRecarga) {
        this.fechaRecarga = fechaRecarga;
    }

    public Integer getValorRecarga() {
        return valorRecarga;
    }

    public void setValorRecarga(Integer valorRecarga) {
        this.valorRecarga = valorRecarga;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pinTarjeta != null ? pinTarjeta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recarga)) {
            return false;
        }
        Recarga other = (Recarga) object;
        if ((this.pinTarjeta == null && other.pinTarjeta != null) || (this.pinTarjeta != null && !this.pinTarjeta.equals(other.pinTarjeta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Logica.Recarga[ pinTarjeta=" + pinTarjeta + " ]";
    }
    
}
