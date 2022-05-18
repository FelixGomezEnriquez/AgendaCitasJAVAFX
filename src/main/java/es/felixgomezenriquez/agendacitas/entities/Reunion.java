/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.felixgomezenriquez.agendacitas.entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author usuario
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "REUNION")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Reunion.findAll", query = "SELECT r FROM Reunion r"),
    @javax.persistence.NamedQuery(name = "Reunion.findById", query = "SELECT r FROM Reunion r WHERE r.id = :id"),
    @javax.persistence.NamedQuery(name = "Reunion.findByNombreReunion", query = "SELECT r FROM Reunion r WHERE r.nombreReunion = :nombreReunion"),
    @javax.persistence.NamedQuery(name = "Reunion.findByLugarReunion", query = "SELECT r FROM Reunion r WHERE r.lugarReunion = :lugarReunion"),
    @javax.persistence.NamedQuery(name = "Reunion.findByFechaReunion", query = "SELECT r FROM Reunion r WHERE r.fechaReunion = :fechaReunion"),
    @javax.persistence.NamedQuery(name = "Reunion.findByTemasATratar", query = "SELECT r FROM Reunion r WHERE r.temasATratar = :temasATratar")})
public class Reunion implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "ID")
    private Integer id;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "NOMBRE_REUNION")
    private String nombreReunion;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "LUGAR_REUNION")
    private String lugarReunion;
    @javax.persistence.Column(name = "FECHA_REUNION")
    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaReunion;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "TEMAS_A_TRATAR")
    private String temasATratar;
    @javax.persistence.JoinColumn(name = "EMPRESA", referencedColumnName = "ID")
    @javax.persistence.ManyToOne
    private Empresa empresa;

    public Reunion() {
    }

    public Reunion(Integer id) {
        this.id = id;
    }

    public Reunion(Integer id, String nombreReunion, String lugarReunion, String temasATratar) {
        this.id = id;
        this.nombreReunion = nombreReunion;
        this.lugarReunion = lugarReunion;
        this.temasATratar = temasATratar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreReunion() {
        return nombreReunion;
    }

    public void setNombreReunion(String nombreReunion) {
        this.nombreReunion = nombreReunion;
    }

    public String getLugarReunion() {
        return lugarReunion;
    }

    public void setLugarReunion(String lugarReunion) {
        this.lugarReunion = lugarReunion;
    }

    public Date getFechaReunion() {
        return fechaReunion;
    }

    public void setFechaReunion(Date fechaReunion) {
        this.fechaReunion = fechaReunion;
    }

    public String getTemasATratar() {
        return temasATratar;
    }

    public void setTemasATratar(String temasATratar) {
        this.temasATratar = temasATratar;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reunion)) {
            return false;
        }
        Reunion other = (Reunion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.felixgomezenriquez.agendacitas.entities.Reunion[ id=" + id + " ]";
    }
    
}
