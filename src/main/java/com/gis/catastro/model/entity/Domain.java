package com.gis.catastro.model.entity;

import com.gis.catastro.model.entity.Base.BaseConfigurationEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "catt_dominios")
public class Domain extends BaseConfigurationEntity {
    @NotNull(message = "El tipo de dominio no debe ser nulo")
    @ApiModelProperty(notes = "Contiene una descripcion del tipo de dominio")
    @Column(name = "dom_tipo_dominio", length = 10)
    private String typeDomain;

    @NotNull(message = "El codigo de dominio no debe ser nulo")
    @ApiModelProperty(notes = "Contiene el codigo del dominio")
    @Column(name = "dom_codigo_dominio", length = 100, unique = true)
    private String codeDomain;

    @NotNull(message = "El nombre de dominio no debe ser nulo")
    @ApiModelProperty(notes = "Contiene el nombre del dominio")
    @Column(name = "dom_nombre_dominio", length = 100)
    private String nameDomain;

    @NotNull(message = "La descripción del dominio no puede ser nulo")
    @ApiModelProperty(notes = "Contiene una descripcion del dominio")
    @Column(name = "dom_descripcion", length = 300)
    private String descriptionDomain;

    @OneToMany(mappedBy = "domain")
    private List<DomainValue> domainValueList;
}
