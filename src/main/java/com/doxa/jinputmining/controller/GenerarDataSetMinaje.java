/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doxa.jinputmining.controller;

import com.doxa.jinputmining.model.mFamilia;
import com.doxa.jinputmining.model.mProducto;
import com.doxa.jinputmining.model.mVentaDetalle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author BlackSpider
 */
public class GenerarDataSetMinaje {

    private String fechaInicio;
    private String fechaFin;
    private String momento;
    private String ruta;
    private int fam;
    private boolean onlyFam;

    public GenerarDataSetMinaje(String fechaInicio, String fechaFin, String momento, String ruta, int fam ,boolean onlyFam) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.momento = momento;
        this.ruta = ruta;
        this.fam = fam;
        this.onlyFam = onlyFam;
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

    public String getMomento() {
        return momento;
    }

    public void setMomento(String momento) {
        this.momento = momento;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public void generarDataSet() {
        //PRE PANDEMIA
        /*String fechaInicio = "2019-02-01"; // la fecha debe escribirse en el formato yyyy-MM-dd
        String fechaFin = "2020-04-01"; // la fecha debe escribirse en el formato yyyy-MM-dd
        String momento = "PrePandemia";*/

        //POST PANDEMIA
        // String fechaInicio = "2020-04-01"; // la fecha debe escribirse en el formato yyyy-MM-dd
        // String fechaFin = "2021-05-24"; // la fecha debe escribirse en el formato yyyy-MM-dd
        // String momento = "Pandemia";
        EntityManager entityManagerTesis = DataSetUti.getEntityManagerFactory().createEntityManager();

        long[] ids = {fam};

        // TRUE: si quieres hacer familias vs productos
        // False: si quieres hacer familias vs famlias ojo debe de ser 2 como minimo
        // long idfamilia = 33;
        // mFamilia famSelected= null;
        List<mFamilia> lFamilias = new ArrayList<mFamilia>();

        if (ids.length > 0) {

            for (int i = 0; i < ids.length; i++) {

                lFamilias.add(entityManagerTesis.find(mFamilia.class, ids[i]));
                if (!entityManagerTesis.getTransaction().isActive()) {
                    entityManagerTesis.getTransaction().begin();
                }
            }

        }

        //entityManagerTesis.getTransaction().commit();
        /*String sqlVentas = "select ventaid, productoid, cantidad, preciomv \n"
                + "from ventasdetalles\n"
                + "order by ventaid asc;";*/
        String sqlVentas = "select vd.ventaid, vd.productoid, vd.cantidad, vd.preciomv \n"
                + "from ventasdetalles vd\n"
                + "LEFT JOIN ventas v ON v.ventaid = vd.ventaid\n"
                + "WHERE v.vfecha BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "';";

        List<mVentaDetalle> lmcats = entityManagerTesis.createNativeQuery(sqlVentas, mVentaDetalle.class).getResultList();

        //entityManagerTesis.getTransaction().begin();
        long ventaid = lmcats.get(0).getVentaid();
        String strVentas = "";
        StringBuffer sbContent = new StringBuffer();

        for (mVentaDetalle mvd : lmcats) {

            if (mvd.getVentaid() != ventaid) {

                strVentas += "\n";

                if (onlyFam) {

                    if (strVentas.length() > 0 && ids.length >= 1) {

                        boolean contieneFamilia = true;

                        for (int i = 0; i < ids.length; i++) {

                            if (!strVentas.contains("-" + ids[i])) {

                                contieneFamilia = false;
                                break;
                            }

                        }

                        if (contieneFamilia) {
                            sbContent.append(strVentas);
                        }

                    } else {

                        sbContent.append(strVentas);

                    }

                }else{
                
                    sbContent.append(strVentas);
                    
                }

                
                ventaid = mvd.getVentaid();
                strVentas = "";

            }

            mProducto producto = entityManagerTesis.find(mProducto.class, mvd.getProductoid());
//            entityManagerTesis.getTransaction().begin();

            //System.out.println(Descripcion);
            if (lFamilias.size() > 0) {

                boolean ban = false;
                boolean ban2 = false;
                boolean ban3 = false;

                boolean remplazoPorFamilia = false;

                for (mFamilia famSelected : lFamilias) {

                    String[] familia = famSelected.getFamilia().split("\\|");

                    if (producto.getDescripcionext().contains(familia[0])) {
                        ban = true;
                    }

                    if (producto.getDescripcionext().contains(familia[1])) {
                        ban2 = true;
                    }

                    if (producto.getDescripcionext().contains(familia[2])) {
                        ban3 = true;
                    }

                    if (ban == true && ban2 == true && ban3 == true) {

                        // System.out.println("Producto id: "+obj.getProductoid()+" es de familia "+ x.getFamilia());
                        // para que no se repita cuando muchas veces sale la misma familia
                        remplazoPorFamilia = true;

                        if (strVentas.contains(famSelected.getFamiliaid() + "") == false) {

                            strVentas += "-" + famSelected.getFamiliaid() + " ";

                            // System.out.println(strVentas);
                            break;

                        }

                    } else {

                        strVentas += mvd.getProductoid() + " ";
                    }

                }

                if (!remplazoPorFamilia) {
                    strVentas += mvd.getProductoid() + " ";
                }

            } else {

                strVentas += mvd.getProductoid() + " ";

            }

        }

        ruta += "\\DataSetMineria" + momento + new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss").format(new Date()) + ".txt";

        //content.append("hola wei");
        FileOutput fo = new FileOutput(ruta, sbContent.toString());
        fo.generarArchivo();

        entityManagerTesis.getTransaction().commit();
        //entityManagerTesis.close();
        //DataSetUti.shutdown();
        

    }

}
