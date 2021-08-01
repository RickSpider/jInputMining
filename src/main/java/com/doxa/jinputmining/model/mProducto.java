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
@Table(name = "productos")
public class mProducto{
    
    @Id
    private long productoid;
    
    private String producto;
    private double vprecio;
    private long familiaid;
    private double stock;
    private String descripcionext;
    private double costo;

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

   
    public String getDescripcionext() {
        return descripcionext;
    }

    public void setDescripcionext(String descripcionext) {
        this.descripcionext = descripcionext;
    }

  
   // private long idarticulo;
    
    public double getStock(){
    
        return stock;
        
    }
    
    public void setStock(double stock){
    
        this.stock = stock;
        
    }

    public long getProductoid() {
        return productoid;
    }

    public void setProductoid(long productoid) {
        this.productoid = productoid;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public double getVprecio() {
        return vprecio;
    }

    public void setVprecio(double vprecio) {
        this.vprecio = vprecio;
    }

    public long getFamiliaid() {
        return familiaid;
    }

    public void setFamiliaid(long familiaid) {
        this.familiaid = familiaid;
    }

    @Override
    public String toString() {
        return "mProducto{" + "productoid=" + productoid + ", producto=" + producto + ", vprecio=" + vprecio + ", familiaid=" + familiaid + ", stock=" + stock + '}';
    }

   
   
}
