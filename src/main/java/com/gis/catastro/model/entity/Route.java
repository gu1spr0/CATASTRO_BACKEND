package com.gis.catastro.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "catt_rutas")
public class Route implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Identificador generado por la base de datos")
    @Column(name = "id")
    private Long id;

    @NotNull(message = "La ruta no puede ser nulo")
    @ApiModelProperty(notes = "Contiene la ruta habilitada por el administrador")
    @Column(name = "rut_ruta")
    private String route;

    @NotNull(message = "La descripción de la ruta no puede ser nulo")
    @ApiModelProperty(notes = "Contiene la descripción breve de la ruta")
    @Column(name = "rut_descripcion")
    private String description;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Resource> resourceList;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ResourcesGroup> resourcesGroupList;
}
