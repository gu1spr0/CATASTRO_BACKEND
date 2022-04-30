package com.gis.catastro.util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

public class ReportUtil {
    HashMap<String, List> mapDatosReporte;
    HashMap<String, Object> mapParametros;
    private JasperReport reporte;
    private static JasperPrint reportFilled;
    public ReportUtil(){
        this.mapDatosReporte = new HashMap<>();
        this.mapParametros = new HashMap<>();
    }
    public JasperPrint GenerarReporte(List<HashMap<String, Object>> data, HashMap<String, Object> param, String pReport, String pPathReport){
        JRBeanCollectionDataSource vJRBeanCollectionDataSource = new JRBeanCollectionDataSource(data);
        try{
            /*File file = ResourceUtils.getFile("classpath:solicitud_apertura.jrxml");
            reporte = (JasperReport) JRLoader.loadObject(file);*/
            //File file = ResourceUtils.getFile("classpath:solicitud_apertura.jrxml");
//            File file = ResourceUtils.getFile("classpath:"+pReport+".jrxml");
            File file = ResourceUtils.getFile(pPathReport+pReport+".jrxml");
            reporte = JasperCompileManager.compileReport(file.getAbsolutePath());
            reportFilled = JasperFillManager.fillReport(reporte, param, vJRBeanCollectionDataSource);
            return reportFilled;
        } catch (JRException e){
            return null;
        } catch (FileNotFoundException e){
            return null;
        }
    }
}
