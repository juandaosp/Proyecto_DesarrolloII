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
public class ProgramacionPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_empleado")
    private String idEmpleado;
    @Basic(optional = false)
    @Column(name = "nombre_ruta")
    private String nombreRuta;
    @Basic(optional = false)
    @Column(name = "matricula_bus")
    private String matriculaBus;

    public ProgramacionPK() {
    }

    public ProgramacionPK(String idEmpleado, String nombreRuta, String matriculaBus) {
        this.idEmpleado = idEmpleado;
        this.nombreRuta = nombreRuta;
        this.matriculaBus = matriculaBus;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public String getMatriculaBus() {
        return matriculaBus;
    }

    public void setMatriculaBus(String matriculaBus) {
        this.matriculaBus = matriculaBus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpleado != null ? idEmpleado.hashCode() : 0);
        hash += (nombreRuta != null ? nombreRuta.hashCode() : 0);
        hash += (matriculaBus != null ? matriculaBus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProgramacionPK)) {
            return false;
        }
        ProgramacionPK other = (ProgramacionPK) object;
        if ((this.idEmpleado == null && other.idEmpleado != null) || (this.idEmpleado != null && !this.idEmpleado.equals(other.idEmpleado))) {
            return false;
        }
        if ((this.nombreRuta == null && other.nombreRuta != null) || (this.nombreRuta != null && !this.nombreRuta.equals(other.nombreRuta))) {
            return false;
        }
        if ((this.matriculaBus == null && other.matriculaBus != null) || (this.matriculaBus != null && !this.matriculaBus.equals(other.matriculaBus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Logica.ProgramacionPK[ idEmpleado=" + idEmpleado + ", nombreRuta=" + nombreRuta + ", matriculaBus=" + matriculaBus + " ]";
    }
    
}
