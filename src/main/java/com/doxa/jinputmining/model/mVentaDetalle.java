/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doxa.jinputmining.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author BlackSpider
 */
@Entity
@Table (name = "VentasDetalles")
public class mVentaDetalle implements Serializable{
    
    @Id
    private long ventaid;
    
    @Id
    private long productoid;
    
    private int cantidad;
    
    private double preciomv;

    public long getVentaid() {
        return ventaid;
    }

    public void setVentaid(long ventaid) {
        this.ventaid = ventaid;
    }

    public long getProductoid() {
        return productoid;
    }

    public void setProductoid(long productoid) {
        this.productoid = productoid;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPreciomv() {
        return preciomv;
    }

    public void setPreciomv(double preciomv) {
        this.preciomv = preciomv;
    }

    @Override
    public String toString() {
        return "mVentaDetalle{" + "ventaid=" + ventaid + ", productoid=" + productoid + ", cantidad=" + cantidad + ", preciomv=" + preciomv + '}';
    }

   
    
}
