package com.gis.catastro.service.dto.upgrade;

import com.vividsolutions.jts.geom.Geometry;
import lombok.Data;

@Data
public class UpgradeQueryDto {
    private Long id;
    private Geometry geometry;
    private Long mejora;
    private String observacion;
    private String geocodigo;
    private Double shapeLeng;
    private Double shapeArea;
}
