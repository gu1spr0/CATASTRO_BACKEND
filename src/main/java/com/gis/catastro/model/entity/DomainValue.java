package com.gis.catastro.model.entity;

import com.gis.catastro.model.entity.Base.BaseConfigurationEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "catt_dominios_valores")
public class DomainValue extends BaseConfigurationEntity {
    @NotNull(message = "El codigo del valor no debe ser nulo")
    @ApiModelProperty(notes = "Contiene el codigo del valor de dominio")
    @Column(name = "dva_codigo_valor", length = 100, unique = true)
    private String codeValue;

    @NotNull(message = "La descripcion del titulo no debe ser nulo")
    @ApiModelProperty(notes = "Contiene una descripcion del título")
    @Column(name = "dva_titulo_descripcion", length = 300)
    private String titleDescription;

    @ApiModelProperty(notes = "Contiene una descripcion literal del valor de dominio")
    @Column(name = "dva_valor_caracter", length = 300)
    private String charValue;

    @ApiModelProperty(notes = "Contiene el valor numerico de los valores de dominio")
    @Column(name = "dva_valor_numerico")
    private Long numericValue;

    @ApiModelProperty(notes = "Contiene una descripcion literal extra del valor de dominio")
    @Column(name = "dva_valor_caracter_extra", length = 300)
    private String charValueExtra;

    @ApiModelProperty(notes = "Contiene el valor numerico extra de los valores de dominio")
    @Column(name = "dva_valor_numerico_extra")
    private Long numericValueExtra;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "dva_domcodigo")
    private Domain domain;
}
