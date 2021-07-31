/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doxa.jinputmining.controller;


import com.doxa.jinputmining.model.mFamilia;
import com.doxa.jinputmining.model.mProducto;
import com.doxa.jinputmining.model.mTopRotacion;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author BlackSpider
 */
public class GenerarRotacion {
    
    private int familiaInicio;
    private int familiaFin;
    
    private String fechaInicio;
    private String fechaFin;
    
    private String ruta;

    public GenerarRotacion(int familiaInicio, int familiaFin, String fechaInicio, String fechaFin, String ruta) {
        this.familiaInicio = familiaInicio;
        this.familiaFin = familiaFin;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.ruta = ruta;
    }

    public int getFamiliaInicio() {
        return familiaInicio;
    }

    public void setFamiliaInicio(int familiaInicio) {
        this.familiaInicio = familiaInicio;
    }

    public int getFamiliaFin() {
        return familiaFin;
    }

    public void setFamiliaFin(int familiaFin) {
        this.familiaFin = familiaFin;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void generarRI () {

        EntityManager entityManagerTesis = DataSetUti.getEntityManagerFactory().createEntityManager();

        /*int familiaInicio = 20;
        int familiaFin = 40;

        String fechaInicio = "2020-04-01"; // la fecha debe escribirse en el formato yyyy-MM-dd
        String fechaFin = "2021-05-24"; // la fecha debe escribirse en el formato yyyy-MM-dd*/

        List<mProducto> lProductos = entityManagerTesis.createNativeQuery(
                "Select "
                        + "productoid, familiaid, productoid, descripcionext, vprecio, stock, costo "
                        + "from Productos;", mProducto.class).getResultList();
       
        List<mTopRotacion> lTopRotacion = new ArrayList<mTopRotacion>();

        for (long i = familiaInicio; i <= familiaFin; i++) {

            mFamilia famSelected = entityManagerTesis.find(mFamilia.class, i);

            String[] familia = famSelected.getFamilia().split("\\|");

            mTopRotacion tr = new mTopRotacion(famSelected.getFamilia());
            tr.setIdItem(i);

            for (mProducto x : lProductos) {

                boolean ban = false;
                boolean ban2 = false;
                boolean ban3 = false;

                if (familia != null) {
                    if (x.getDescripcionext().contains(familia[0])) {
                        ban = true;
                    }

                    if (x.getDescripcionext().contains(familia[1])) {
                        ban2 = true;
                    }

                    if (x.getDescripcionext().contains(familia[2])) {
                        ban3 = true;
                    }
                }

                if (ban == true && ban2 == true && ban3 == true) {

                   // tr.setStockTotal(tr.getStockTotal() + x.getStock());
                    tr.getProductos().add(x);

                }

            }

            lTopRotacion.add(tr);

        }

        for (mTopRotacion x : lTopRotacion) {

            Double totalesVentas = 0.0;
            Double totalesCompras = 0.0;
            Double totalesStock = 0.0;

            for (mProducto x2 : x.getProductos()) {
                
                String detalle = "IdProducto: "+x2.getProductoid();

                /*   String sql = "select sum(cantidad) from ventasdetalles\n"
                        + "where productoid = "+x2.getProductoid()+" \n"
                        + "group by productoid;";*/
                String sql = "SELECT sum(vd.cantidad)\n"
                        + "FROM ventasdetalles vd\n"
                        + "LEFT JOIN ventas v ON v.ventaid = vd.ventaid\n"
                        + "WHERE vd.productoid = " + x2.getProductoid() + " AND v.vfecha BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "';";

                // System.out.println(sql);
                try {

                    BigDecimal ventas = (BigDecimal) entityManagerTesis.createNativeQuery(sql).getSingleResult();
                    
                      if(ventas == null)
                        ventas = new BigDecimal(0);
                      
                    detalle += " Venta: "+ventas.toString();

                    totalesVentas += ventas.longValue();

                } catch (javax.persistence.NoResultException ex) {

                    if (!entityManagerTesis.getTransaction().isActive()) {
                        entityManagerTesis.getTransaction().begin();
                    }

                    entityManagerTesis.getTransaction().commit();

                }

                String sqlCompras = "SELECT sum(cd.cantidad)\n"
                        + "FROM comprasdetalles cd\n"
                        + "LEFT JOIN compras c ON c.compraid = cd.compraid\n"
                        + "WHERE cd.productoid = " + x2.getProductoid() + " AND c.inifecha BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "';";

                try {

                    BigDecimal compras = (BigDecimal) entityManagerTesis.createNativeQuery(sqlCompras).getSingleResult();
                    
                    if(compras == null)
                        compras = new BigDecimal(0);
                    
                    detalle += " Compras: "+compras.toString();

                    totalesCompras += compras.longValue();

                } catch (javax.persistence.NoResultException ex) {

                    if (!entityManagerTesis.getTransaction().isActive()) {
                        entityManagerTesis.getTransaction().begin();
                    }

                    entityManagerTesis.getTransaction().commit();

                }

                String sqlSotckH = "SELECT stock::integer \n"
                        + "FROM productoshistoricos\n"
                        + "WHERE productoid = " + x2.getProductoid() + " AND fecha = '" + fechaInicio + "';";
                
                 try {

                    Integer stock = (Integer) entityManagerTesis.createNativeQuery(sqlSotckH).getSingleResult();
                    
                    
                    
                    if(stock == null || stock < 0)
                        stock = 0;

                    detalle += " StockInicial: "+stock.toString();

                    totalesStock += stock.longValue();

                } catch (javax.persistence.NoResultException ex) {

                    if (!entityManagerTesis.getTransaction().isActive()) {
                        entityManagerTesis.getTransaction().begin();
                    }

                    entityManagerTesis.getTransaction().commit();

                }
                 
               //  System.out.println(detalle);

            }

            x.setVentas(totalesVentas);
            x.setStockTotal(totalesStock);
            x.setTotalCompras(totalesCompras);

            Double ir = x.getVentas() / totalesStock+(totalesCompras/2);

            if (ir.isNaN()) {

                ir = 0.0;

            }

            x.setIndiceRotacion(ir);
            // System.out.println(x.getItem() +" Stock: "+ x.getStockTotal() + " Ventas: " + x.getVentas() + " IR: " + ir);

        }

        Collections.sort(lTopRotacion);

        for (mTopRotacion x : lTopRotacion) {

            //System.out.println("ID: " + x.getIdItem() + "\tCostoMin: " + x.getCostoMin() + "\tCostoMax: " + x.getCostoMax() + "\tCostoPromedio: " + new DecimalFormat("#.##").format(x.getCostoPromedio()) + "\tStock: " + x.getStockTotal() +  "\tCompras: " + x.getTotalCompras()+"\tVentas: " + x.getVentas() + "\tIR: " + new DecimalFormat("#.##").format(x.getIndiceRotacion()) + "\tFmailia: " + x.getItem());
            
            System.out.println("ID: " + x.getIdItem() + "\tIR: " + new DecimalFormat("#.##").format(x.getIndiceRotacion()) + "\tFmailia: " + x.getItem());

        }
        
        StringBuffer sbContent = new StringBuffer();
        
        sbContent.append("Rango: "+fechaInicio + " - "+ fechaFin + "\n");
        
        for (mTopRotacion tr : lTopRotacion){
        
            sbContent.append(tr.toStringAlt());
            
        }
         
         
        ruta += "\\IR" + new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss").format(new Date())+".txt";

        //content.append("hola wei");
        FileOutput fo = new FileOutput(ruta, sbContent.toString());
        fo.generarArchivo();

        DataSetUti.shutdown();
        // entityManagerTesis.close();

    }

    
}
