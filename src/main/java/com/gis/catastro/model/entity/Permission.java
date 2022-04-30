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
@Table(name = "catt_permisos")
public class Permission extends BaseConfigurationEntity {
    @NotNull(message = "El permiso no puede ser nulo")
    @ApiModelProperty(notes = "Contiene el permiso habilitado para el rol")
    @Column(name = "per_titulo")
    private String title;

    @NotNull(message = "El código del permiso no puede ser nulo")
    @ApiModelProperty(notes = "Contiene el código del permiso")
    @Column(name = "per_codigo_permiso")
    private String permissionCode;

    @NotNull(message = "La prioridad no debe ser nula")
    @ApiModelProperty(notes = "Contiene la prioridad del permiso")
    @Column(name = "per_orden_despliegue")
    private int deploymentOrder;

    @ManyToMany(mappedBy = "permissions")
    private List<Role> roles;

    @ManyToOne
    @JoinColumn(name = "per_gpecodigo")
    private PermissionsGroup permissionsGroup;
}
