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
@Table(name = "catt_grupos_recursos")
public class ResourcesGroup extends BaseConfigurationEntity {
    @NotNull(message = "El nombre del grupo no puede ser nulo")
    @ApiModelProperty(notes = "Contiene el recursos habilitado para el sistema adminitrador")
    @Column(name = "gre_titulo")
    private String title;

    @NotNull(message = "La descripción del recurso no puede ser nulo")
    @ApiModelProperty(notes = "Contiene la descripción breve del grupo")
    @Column(name = "gre_descripcion")
    private String description;

    @NotNull(message = "El icono no debe ser nulo")
    @ApiModelProperty(notes = "Contiene  el icono del grupo de recursos")
    @Column(name = "gre_icono")
    private String icon;

    @NotNull(message = "La prioridad no debe ser nula")
    @ApiModelProperty(notes = "Contiene la prioridad del recurso")
    @Column(name = "gre_orden_despliegue")
    private int deploymentOrder;

    @OneToMany(mappedBy = "resourcesGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Resource> resourceList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gre_rutcodigo")
    private Route route;
}
