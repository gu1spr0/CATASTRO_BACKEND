package com.gis.catastro.model.entity;

import com.gis.catastro.model.entity.Base.BaseConfigurationEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "catt_roles")
public class Role extends BaseConfigurationEntity {
    @Size(min = 5, message = "El nombre del rol debe tener por lo menos 5 caracteres")
    @NotNull(message = "El nombre del rol no debe ser nulo")
    @ApiModelProperty(notes = "Contiene el nombre del rol")
    @Column(name = "rol_rol")
    private String role;

    @NotNull(message = "La descripcion no debe ser nula")
    @ApiModelProperty(notes = "Contiene la descripción del rol")
    @Column(name = "rol_descripcion")
    private String description;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="catt_roles_recursos",
            joinColumns = @JoinColumn(name="ror_rolcodigo", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ror_reccodigo", referencedColumnName = "id"))
    private List<Resource> resources;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="catt_roles_permisos",
            joinColumns = @JoinColumn(name="rop_rolcodigo", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "rop_percodigo", referencedColumnName = "id"))
    private List<Permission> permissions;
}
