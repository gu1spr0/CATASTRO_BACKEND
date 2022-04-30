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
@Table(name = "catt_usuarios")
public class User extends BaseConfigurationEntity {
    @NotNull(message = "El identificador del usuario no debe ser nula")
    @ApiModelProperty(notes = "Contiene el identificador del usuario a nivel del LDAP del Banco")
    @Size(min = 4, message = "El identificador del usuario debe tener por lo menos 4 caracteres")
    @Column(name = "usu_usuario", length = 50)
    private String username;

    @NotNull(message = "La contraseña no debe ser nula")
    @ApiModelProperty(notes = "Contiene la contraseña del usuario")
    @Column(name = "usu_password", length = 100)
    private String password;

    @NotNull(message = "El nombre del usuario no debe ser nulo")
    @ApiModelProperty(notes = "Contiene el nombre del usuario")
    @Column(name = "usu_nombre", length = 100)
    private String name;

    @NotNull(message = "El apellido del usuario no debe ser nulo")
    @ApiModelProperty(notes = "Contiene el apellido del usuario")
    @Column(name = "usu_apellido", length = 100)
    private String lastName;

    @NotNull(message = "El correo del usuario no debe ser nulo")
    @ApiModelProperty(notes = "Contiene el correo del usuario")
    @Column(name = "usu_correo", length = 100)
    private String email;

    @NotNull(message = "El teléfono del usuario no debe ser nulo")
    @ApiModelProperty(notes = "Contiene el teléfono del usuario")
    @Column(name = "usu_telefono", length = 10)
    private String telephone;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="catt_usuarios_roles",
            joinColumns = @JoinColumn(name="uro_usucodigo", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "uro_rolcodigo", referencedColumnName = "id"))
    private List<Role> roles;
}
