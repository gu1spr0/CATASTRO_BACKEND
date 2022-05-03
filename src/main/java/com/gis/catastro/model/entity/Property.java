package com.gis.catastro.model.entity;

import com.vividsolutions.jts.geom.Geometry;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "`v_Predios`")
public class Property implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Identificador generado por la base de datos")
    @Column(name = "`GEOCODIGO`")
    private Long id;

    @NotNull(message = "La geometria no puede ser nula")
    @ApiModelProperty(notes = "Contiene la geometria del predio")
    @Column(name = "geom", columnDefinition = "geography")
    private Geometry geometry;

    @NotNull(message = "El Id objeto no puede ser nulo")
    @ApiModelProperty(notes = "Contiene el id del objeto Predio")
    @Column(name = "objectid")
    private Long objectId;

    @NotNull(message = "El Join count no puedo ser nulo")
    @ApiModelProperty(notes = "Contiene el Join count del predio")
    @Column(name = "join_count")
    private Long joinCount;

    @NotNull(message = "El codigo fid no puedo ser nulo")
    @ApiModelProperty(notes = "Contiene el codigo fid del predio")
    @Column(name = "target_fid")
    private Long target_fid;

    @NotNull(message = "El campo referencia no puedo ser nulo")
    @ApiModelProperty(notes = "Contiene el campo referencia del predio")
    @Column(name = "referencia", length = 22)
    private String referencia;

    @NotNull(message = "El campo estGabine no puedo ser nulo")
    @ApiModelProperty(notes = "Contiene el valor estGabine del predio")
    @Column(name = "est_gabine")
    private Long estGabine;

    @NotNull(message = "El campot referenc1 no puedo ser nulo")
    @ApiModelProperty(notes = "Contiene el campo referenc1 del predio")
    @Column(name = "referenc_1", length = 22)
    private String referenc1;

    @NotNull(message = "El valor grilla no puedo ser nulo")
    @ApiModelProperty(notes = "Contiene el valor grilla del predio")
    @Column(name = "n_grilla", precision = 6)
    private Double nGrilla;

    @NotNull(message = "El contador no puedo ser nulo")
    @ApiModelProperty(notes = "Contiene el contador del predio")
    @Column(name = "contador", length = 3)
    private String contador;

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
