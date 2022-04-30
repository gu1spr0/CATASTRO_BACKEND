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
@Table(name = "catt_grupos_permisos")
public class PermissionsGroup extends BaseConfigurationEntity {
    @NotNull(message = "El nombre del grupo no puede ser nulo")
    @ApiModelProperty(notes = "Contiene los permisos habilitado para el rol")
    @Column(name = "gpe_titulo")
    private String title;

    @NotNull(message = "El código del grupo de permisos no puede ser nulo")
    @ApiModelProperty(notes = "Contiene el código del grupo")
    @Column(name = "gpe_codigo_grupo_permiso")
    private String permissionGroupCode;

    @NotNull(message = "La prioridad no debe ser nula")
    @ApiModelProperty(notes = "Contiene la prioridad del grupo de permiso")
    @Column(name = "gpe_orden_despliegue")
    private int deploymentOrder;

    @OneToMany(mappedBy = "permissionsGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    private List<Permission> permissionList;
}
