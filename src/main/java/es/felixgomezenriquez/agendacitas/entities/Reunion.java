/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.felixgomezenriquez.agendacitas.entities;

import es.felixgomezenriquez.agendacitas.entities.Empresa;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author RaymanKLK
 */
@Entity
@Table(name = "REUNION")
@NamedQueries({
    @NamedQuery(name = "Reunion.findAll", query = "SELECT r FROM Reunion r"),
    @NamedQuery(name = "Reunion.findById", query = "SELECT r FROM Reunion r WHERE r.id = :id"),
    @NamedQuery(name = "Reunion.findByNombreReunion", query = "SELECT r FROM Reunion r WHERE r.nombreReunion = :nombreReunion"),
    @NamedQuery(name = "Reunion.findByLugarReunion", query = "SELECT r FROM Reunion r WHERE r.lugarReunion = :lugarReunion"),
    @NamedQuery(name = "Reunion.findByFechaReunion", query = "SELECT r FROM Reunion r WHERE r.fechaReunion = :fechaReunion"),
    @NamedQuery(name = "Reunion.findByTemasATratar", query = "SELECT r FROM Reunion r WHERE r.temasATratar = :temasATratar"),
    @NamedQuery(name = "Reunion.findByOrganizador", query = "SELECT r FROM Reunion r WHERE r.organizador = :organizador"),
    @NamedQuery(name = "Reunion.findByFotoOrganizador", query = "SELECT r FROM Reunion r WHERE r.fotoOrganizador = :fotoOrganizador")})
public class Reunion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NOMBRE_REUNION")
    private String nombreReunion;
    @Basic(optional = false)
    @Column(name = "LUGAR_REUNION")
    private String lugarReunion;
    @Column(name = "FECHA_REUNION")
    @Temporal(TemporalType.DATE)
    private Date fechaReunion;
    @Basic(optional = false)
    @Column(name = "TEMAS_A_TRATAR")
    private String temasATratar;
    @Column(name = "ORGANIZADOR")
    private String organizador;
    @Column(name = "FOTO_ORGANIZADOR")
    private String fotoOrganizador;
    @JoinColumn(name = "EMPRESA", referencedColumnName = "ID")
    @ManyToOne
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

    public String getOrganizador() {
        return organizador;
    }

    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }

    public String getFotoOrganizador() {
        return fotoOrganizador;
    }

    public void setFotoOrganizador(String fotoOrganizador) {
        this.fotoOrganizador = fotoOrganizador;
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
