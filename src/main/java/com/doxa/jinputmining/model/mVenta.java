/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doxa.jinputmining.model;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author BlackSpider
 */
@Entity
@Table(name = "ventas")
public class mVenta implements Serializable{
    
    @Id
    private long ventaid;
     
     @Temporal(TemporalType.TIMESTAMP)
    private Date vfecha;
    
    private double vmonto;

    public long getVentaid() {
        return ventaid;
    }

    public void setVentaid(long ventaid) {
        this.ventaid = ventaid;
    }

    public Date getVfecha() {
        return vfecha;
    }

    public void setVfecha(Date vfecha) {
        this.vfecha = vfecha;
    }

    public double getVmonto() {
        return vmonto;
    }

    public void setVmonto(double vmonto) {
        this.vmonto = vmonto;
    }

    @Override
    public String toString() {
        return "mVenta{" + "ventaid=" + ventaid + ", vfecha=" + vfecha + ", vmonto=" + vmonto + '}';
    }

    
  
}
