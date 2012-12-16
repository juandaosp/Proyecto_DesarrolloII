/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "bus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bus.findAll", query = "SELECT b FROM Bus b"),
    @NamedQuery(name = "Bus.findByMatricula", query = "SELECT b FROM Bus b WHERE b.matricula = :matricula"),
    @NamedQuery(name = "Bus.findByEstado", query = "SELECT b FROM Bus b WHERE b.estado = :estado"),
    @NamedQuery(name = "Bus.findByAno", query = "SELECT b FROM Bus b WHERE b.ano = :ano"),
    @NamedQuery(name = "Bus.findByFabricante", query = "SELECT b FROM Bus b WHERE b.fabricante = :fabricante"),
    @NamedQuery(name = "Bus.findByCapacidad", query = "SELECT b FROM Bus b WHERE b.capacidad = :capacidad"),
    @NamedQuery(name = "Bus.findByCilindrinaje", query = "SELECT b FROM Bus b WHERE b.cilindrinaje = :cilindrinaje"),
    @NamedQuery(name = "Bus.findByChasis", query = "SELECT b FROM Bus b WHERE b.chasis = :chasis"),
    @NamedQuery(name = "Bus.findByTipoBus", query = "SELECT b FROM Bus b WHERE b.tipoBus = :tipoBus")})
public class Bus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "matricula")
    private String matricula;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @Column(name = "ano")
    private String ano;
    @Basic(optional = false)
    @Column(name = "fabricante")
    private String fabricante;
    @Basic(optional = false)
    @Column(name = "capacidad")
    private String capacidad;
    @Basic(optional = false)
    @Column(name = "cilindrinaje")
    private String cilindrinaje;
    @Basic(optional = false)
    @Column(name = "chasis")
    private String chasis;
    @Basic(optional = false)
    @Column(name = "tipo_bus")
    private String tipoBus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bus")
    private List<Programacion> programacionList;

    public Bus() {
    }

    public Bus(String matricula) {
        this.matricula = matricula;
    }

    public Bus(String matricula, String estado, String ano, String fabricante, String capacidad, String cilindrinaje, String chasis, String tipoBus) {
        this.matricula = matricula;
        this.estado = estado;
        this.ano = ano;
        this.fabricante = fabricante;
        this.capacidad = capacidad;
        this.cilindrinaje = cilindrinaje;
        this.chasis = chasis;
        this.tipoBus = tipoBus;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }

    public String getCilindrinaje() {
        return cilindrinaje;
    }

    public void setCilindrinaje(String cilindrinaje) {
        this.cilindrinaje = cilindrinaje;
    }

    public String getChasis() {
        return chasis;
    }

    public void setChasis(String chasis) {
        this.chasis = chasis;
    }

    public String getTipoBus() {
        return tipoBus;
    }

    public void setTipoBus(String tipoBus) {
        this.tipoBus = tipoBus;
    }

    @XmlTransient
    public List<Programacion> getProgramacionList() {
        return programacionList;
    }

    public void setProgramacionList(List<Programacion> programacionList) {
        this.programacionList = programacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (matricula != null ? matricula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bus)) {
            return false;
        }
        Bus other = (Bus) object;
        if ((this.matricula == null && other.matricula != null) || (this.matricula != null && !this.matricula.equals(other.matricula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Logica.Bus[ matricula=" + matricula + " ]";
    }
    
}
