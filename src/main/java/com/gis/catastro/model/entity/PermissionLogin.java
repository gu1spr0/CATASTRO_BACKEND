package com.gis.catastro.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "catv_grupos_permisos")
public class PermissionLogin implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "gpe_titulo")
    private String permissionGroupTitle;

    @Column(name = "gpe_codigo_grupo_permiso")
    private String permissionGroupCode;

    @Column(name = "gpe_orden_despliegue")
    private int permissionGroupDeploymentOrder;

    @Column(name = "id_permiso")
    private long permissionId;

    @Column(name = "per_titulo")
    private String permissionTitle;

    @Column(name = "per_codigo_permiso")
    private String permissionCode;

    @Column(name = "per_orden_despliegue")
    private int permissionDeploymentOrder;
}
