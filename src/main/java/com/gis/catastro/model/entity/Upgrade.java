package com.gis.catastro.model.entity;

import com.vividsolutions.jts.geom.Geometry;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "`v_Mejoras`")
public class Upgrade implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Identificador generado por la base de datos")
    @Column(name = "id")
    private Long id;

    @NotNull(message = "La geometria no puede ser nula")
    @ApiModelProperty(notes = "Contiene la geometria del predio")
    @Column(name = "geom", columnDefinition = "geography")
    private Geometry geometry;

    @NotNull(message = "El Id objeto no puede ser nulo")
    @ApiModelProperty(notes = "Contiene el id del objeto mejora")
    @Column(name = "objectid")
    private Long objectId;

    @NotNull(message = "El campo referencia no puedo ser nulo")
    @ApiModelProperty(notes = "Contiene el campo referencia de la mejora")
    @Column(name = "referencia", length = 22)
    private String referencia;

    @NotNull(message = "El valor de mejora no puede ser nulo")
    @ApiModelProperty(notes = "Contiene el valor de mejora")
    @Column(name = "mejora")
    private Long mejora;

    @NotNull(message = "El campo observación ser nulo")
    @ApiModelProperty(notes = "Contiene el campo observación de la mejora")
    @Column(name = "observacio", length = 100)
    private String observacion;

    @NotNull(message = "El geocodigo no puedo ser nulo")
    @ApiModelProperty(notes = "Contiene el geocodigo identificativo del predio")
    @Column(name = "geocodigo", length = 22, columnDefinition = "text")
    private String geocodigo;

    @NotNull(message = "La longitud no puedo ser nulo")
    @ApiModelProperty(notes = "Contiene la longitud del predio")
    @Column(name = "shape_leng", precision = 9)
    private Double shapeLeng;

    @NotNull(message = "El area no puedo ser nulo")
    @ApiModelProperty(notes = "Contiene el area del predio")
    @Column(name = "shape_area", precision = 9)
    private Double shapeArea;
}
