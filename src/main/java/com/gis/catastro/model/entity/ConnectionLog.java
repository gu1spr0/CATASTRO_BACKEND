package com.gis.catastro.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "catt_log_conexiones")
public class ConnectionLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Identificador generado por la base de datos")
    @Column(name = "id")
    private Long id;

    @NotNull(message = "El usuario debe ser nulo")
    @ApiModelProperty(notes = "Contiene el usuario que se conecta a la base")
    @Column(name = "lco_usuario")
    private String username;

    @PastOrPresent(message = "La fecha de login debe ser actual")
    @NotNull(message = "La fecha de login no debe ser nula")
    @Column(name = "lco_fecha_login")
    @ApiModelProperty(notes = "Fecha de login al sistema")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginDate;

    @Column(name = "lco_fecha_logout")
    @ApiModelProperty(notes = "Fecha de logout del sistema")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logoutDate;
}
