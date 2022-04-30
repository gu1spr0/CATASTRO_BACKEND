package com.gis.catastro.model.entity;

import com.gis.catastro.model.entity.Base.BaseConfigurationEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "catt_recursos")
public class Resource extends BaseConfigurationEntity {
    @NotNull(message = "El recurso no puede ser nulo")
    @ApiModelProperty(notes = "Contiene el recursos habilitado para el sistema adminitrador")
    @Column(name = "rec_titulo")
    private String title;

    @NotNull(message = "La descripción del recurso no puede ser nulo")
    @ApiModelProperty(notes = "Contiene la descripción breve del recurso")
    @Column(name = "rec_descripcion")
    private String description;

    @NotNull(message = "La prioridad no debe ser nula")
    @ApiModelProperty(notes = "Contiene la prioridad del recurso")
    @Column(name = "rec_orden_despliegue")
    private int deploymentOrder;

    @ManyToMany(mappedBy = "resources")
    private List<Role> roles;

    @ManyToOne
    @JoinColumn(name = "rec_grecodigo")
    private ResourcesGroup resourcesGroup;

    @ManyToOne
    @JoinColumn(name = "rec_rutcodigo")
    private Route route;
}
