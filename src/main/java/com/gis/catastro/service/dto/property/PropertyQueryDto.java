package com.gis.catastro.service.dto.property;

import com.vividsolutions.jts.geom.Geometry;
import lombok.Data;

@Data
public class PropertyQueryDto {
    private Geometry geometry;
    private String referencia;
    private Double nGrilla;
    private String geocodigo;
    private Double shapeLeng;
    private Double shapeArea;
}
