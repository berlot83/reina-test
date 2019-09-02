package snya.reina.config;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@SuppressWarnings("serial")
@Entity
@Table(name="log_revinfo", catalog="Sistemassnya")
@RevisionEntity(RevisionContextoListener.class)
public class Revision extends DefaultRevisionEntity {

    @Column(name="Fecha")
    private Date fecha;    
    @Column(name="Usuario")
    private String usuario;
    @Column(name="IdSistema")
    private Integer sistema;
    @Column(name="IdSector")
    private Integer sector;
    @Column(name="IdRol")
    private Integer rol;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getSistema() {
        return sistema;
    }

    public void setSistema(Integer sistema) {
        this.sistema = sistema;
    }

    public Integer getSector() {
        return sector;
    }

    public void setSector(Integer sector) {
        this.sector = sector;
    }

    public Integer getRol() {
        return rol;
    }

    public void setRol(Integer rol) {
        this.rol = rol;
    }
}