/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doxa.jinputmining.controller;

import com.doxa.jinputmining.model.mFamilia;
import com.doxa.jinputmining.model.mProducto;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author BlackSpider
 */
public class GenerarInsert  {
    
    private String ruta;
    private String sql;
    private String tabla;

    public GenerarInsert(String ruta, String sql) {
        this.ruta = ruta;
        this.sql = sql;
        
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }
   
    public void generarInsertFamilia(){
    
        StringBuffer sbContent = new StringBuffer();

        EntityManager entityManager = dbUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        
        List<mFamilia> lmcats = entityManager.createNativeQuery(sql, mFamilia.class).getResultList();

        if (lmcats != null) {

            for (mFamilia mcat : lmcats) {

                /*entityManagerTesis.getTransaction().begin();

                System.out.println(mcat.toString());
                entityManagerTesis.persist(mcat);

                entityManagerTesis.getTransaction().commit();*/
                String s = "Insert into familias (familiaid, familia) values ( " + mcat.getFamiliaid() + ", '" + mcat.getFamilia() + "' );\n";
                sbContent.append(s);

            }

        }

        entityManager.getTransaction().commit();
        
        ruta += "\\InsertFamilia"+ new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss").format(new Date()) + ".txt";

        //content.append("hola wei");
        FileOutput fo = new FileOutput(ruta, sbContent.toString());
        fo.generarArchivo();
        
        
    }
    
     public void generarInsertProducto(){
    
        StringBuffer sbContent = new StringBuffer();

        EntityManager entityManager = dbUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        
        List<mFamilia> lmcats = entityManager.createNativeQuery(sql, mFamilia.class).getResultList();

       List<mProducto> articulos = entityManager.createNativeQuery(sql, mProducto.class).getResultList();

        if (articulos != null) {

            for (mProducto art : articulos) {

                /* System.out.println(art.toString());
                entityManagerTesis.getTransaction().begin();

               // entityManagerTesis.merge(art);
                entityManagerTesis.persist(art);

                entityManagerTesis.getTransaction().commit();*/
                String descripcionext = art.getDescripcionext().replaceAll("'", "''");

                String s = "Insert into productos (productoid, familiaid, producto, descripcionext, vprecio, stock, costo) values ( "
                        + art.getProductoid() + ", " + art.getFamiliaid() + ", '" + art.getProducto() + "', '" + descripcionext + "', " + art.getVprecio() + ", " + art.getStock() +", "+art.getCosto() +" );\n";
                sbContent.append(s);

            }
        }

        entityManager.getTransaction().commit();
        
        ruta += "\\InsertProducto"+ new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss").format(new Date()) + ".txt";

        //content.append("hola wei");
        FileOutput fo = new FileOutput(ruta, sbContent.toString());
        fo.generarArchivo();
        
        
    }
    
}
