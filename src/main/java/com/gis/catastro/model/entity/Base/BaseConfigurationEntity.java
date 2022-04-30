package com.gis.catastro.model.entity.Base;

import com.gis.catastro.util.Constants;
import com.gis.catastro.util.SecurityUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
public class BaseConfigurationEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Identificador generado por la base de datos")
    @Column(name = "id")
    private Long id;

    @PastOrPresent(message = "La fecha de alta del registro debe ser actual")
    @NotNull(message = "La fecha de alta del registro no debe ser nula")
    @Column(name = "fecha_alta")
    @ApiModelProperty(notes = "Fecha de alta del registro en base de datos")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @NotNull(message = "El usuario que dió de alta el registro no debe ser nula")
    @Column(name = "usuario_alta")
    @ApiModelProperty(notes = "Usuario que dió de alta el registro")
    private String createdBy;

    @Column(name = "fecha_baja", nullable = true)
    @ApiModelProperty(notes = "Fecha de baja del registro en base de datos")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;

    @Column(name = "usuario_baja", nullable = true)
    @ApiModelProperty(notes = "Usuario que dió de baja el registro")
    private String deletedBy;

    @ApiModelProperty(notes = "Fecha de última modificacón del registro")
    @Column(name = "fecha_modificacion", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Column(name = "usuario_modificacion", nullable = true)
    @ApiModelProperty(notes = "Usuario que modificó por última vez el registro")
    private String lastModifiedBy;

    @NotNull(message = "El estado no puede ser nulo")
    @ApiModelProperty(notes = "Contiene el estado del regristro")
    @Column(name = "estado")
    private String state;

    @PrePersist
    public void prePersist() {
        Date now = new Date();
        if (createdDate == null) {
            createdDate = now;
            createdBy = SecurityUtil.getUserOfAuthenticatedUser();
            state = Constants.STATE_ACTIVE;
        }
    }

    @PreUpdate
    public void preUpdate(){
        lastModifiedBy = SecurityUtil.getUserOfAuthenticatedUser();
        lastModifiedDate = new Date();
        if  (deletedDate != null){
            deletedBy = SecurityUtil.getUserOfAuthenticatedUser();
            state = Constants.STATE_DELETED;
        }
    }
}
