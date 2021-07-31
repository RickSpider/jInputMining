/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doxa.jinputmining.model;

import java.util.ArrayList;

/**
 *
 * @author BlackSpider
 */
public class mTopRotacion implements Comparable<mTopRotacion> {

    private String Item;
    private long idItem;
    private double stockTotal = 0;
    private double totalCompras = 0;
    private double ventas = 0;
    private double indiceRotacion;
    private ArrayList<mProducto> lProductos;
    
     public ArrayList<mProducto> getlProductos() {
        return lProductos;
    }

    public void setlProductos(ArrayList<mProducto> lProductos) {
        this.lProductos = lProductos;
    }

    public double getCostoMin() {

        if (lProductos.size() > 0) {

            double costoMin = lProductos.get(0).getCosto();

            for (mProducto x : lProductos) {

                if (costoMin > x.getCosto()) {
                    costoMin = x.getCosto();
                }

            }

            return costoMin;

        }

        return 0.0;

    }

    public double getCostoMax() {

        if (lProductos.size() > 0) {

            double costoMax = lProductos.get(0).getCosto();

            for (mProducto x : lProductos) {

                if (costoMax < x.getCosto()) {
                    costoMax = x.getCosto();
                }

            }

            return costoMax;

        }

        return 0.0;

    }

    public double getCostoPromedio() {

        if (lProductos.size() > 0) {
            double suma = 0.0;

            for (mProducto x : lProductos) {

                suma += x.getCosto();

            }

            double promedio = suma / lProductos.size();

            return promedio;
        }
        return 0.0;

    }

    public long getIdItem() {
        return idItem;
    }

    public void setIdItem(long idItem) {
        this.idItem = idItem;
    }

    public double getIndiceRotacion() {
        return indiceRotacion;
    }

    public void setIndiceRotacion(double indiceRotacion) {
        this.indiceRotacion = indiceRotacion;
    }

    public ArrayList<mProducto> getProductos() {
        return lProductos;
    }

    public void setProductos(ArrayList<mProducto> lIdProductos) {
        this.lProductos = lIdProductos;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String Item) {
        this.Item = Item;
    }

    public double getStockTotal() {
        return stockTotal;
    }

    public void setStockTotal(double stockTotal) {
        this.stockTotal = stockTotal;
    }

    public double getVentas() {
        return ventas;
    }

    public void setVentas(double ventas) {
        this.ventas = ventas;
    }

    public mTopRotacion(String Item) {
        this.Item = Item;
        this.lProductos = new ArrayList<mProducto>();
    }

    public double getTotalCompras() {
        return totalCompras;
    }

    public void setTotalCompras(double totalCompras) {
        this.totalCompras = totalCompras;
    }

    @Override
    public int compareTo(mTopRotacion tr) {

        if (indiceRotacion > tr.getIndiceRotacion()) {

            return -1;

        }
        if (indiceRotacion < tr.getIndiceRotacion()) {

            return 1;

        }

        return 0;

    }

    
    public String toStringAlt() {
        return "Item=" + Item + ",\tidItem=" + idItem + ",\tstockTotal=" + stockTotal + ",\ttotalCompras=" + totalCompras + ",\tventas=" + ventas + ",\tindiceRotacion=" + indiceRotacion + "\n";
    }
    
    

}
