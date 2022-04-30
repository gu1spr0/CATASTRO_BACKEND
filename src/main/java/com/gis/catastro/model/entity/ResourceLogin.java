package com.gis.catastro.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "catv_grupos_recursos")
public class ResourceLogin implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "gre_titulo")
    private String resourceGroupTitle;

    @Column(name = "gre_icono")
    private String resourceGroupIcon;

    @Column(name = "gre_orden_despliegue")
    private int resourceGroupDeploymentOrder;

    @Column(name = "codigo_recurso")
    private Long resourceId;

    @Column(name = "rec_titulo")
    private String resourceTitle;

    @Column(name = "rec_orden_despliegue")
    private int resourceDeploymentOrder;

    @Column(name = "rut_ruta")
    private String route;
}
