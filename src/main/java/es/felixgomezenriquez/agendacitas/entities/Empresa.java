/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.felixgomezenriquez.agendacitas.entities;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author usuario
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "EMPRESA")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Empresa.findAll", query = "SELECT e FROM Empresa e"),
    @javax.persistence.NamedQuery(name = "Empresa.findById", query = "SELECT e FROM Empresa e WHERE e.id = :id"),
    @javax.persistence.NamedQuery(name = "Empresa.findByCodigo", query = "SELECT e FROM Empresa e WHERE e.codigo = :codigo"),
    @javax.persistence.NamedQuery(name = "Empresa.findByNombre", query = "SELECT e FROM Empresa e WHERE e.nombre = :nombre")})
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "ID")
    private Integer id;
    @javax.persistence.Column(name = "CODIGO")
    private String codigo;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "NOMBRE")
    private String nombre;
    @javax.persistence.OneToMany(mappedBy = "empresa")
    private Collection<Reunion> reunionCollection;

    public Empresa() {
    }

    public Empresa(Integer id) {
        this.id = id;
    }

    public Empresa(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Collection<Reunion> getReunionCollection() {
        return reunionCollection;
    }

    public void setReunionCollection(Collection<Reunion> reunionCollection) {
        this.reunionCollection = reunionCollection;
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
        if (!(object instanceof Empresa)) {
            return false;
        }
        Empresa other = (Empresa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.felixgomezenriquez.agendacitas.entities.Empresa[ id=" + id + " ]";
    }
    
}
