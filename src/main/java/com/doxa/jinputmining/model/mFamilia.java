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
@Table(name = "familias")
public class mFamilia implements Serializable{

    @Id
    private long familiaid;

    private String familia;

    public String getInsert() {

        return "insert into familias (familiaid, familia) values (" + this.familiaid + ", '" + this.familia + "');\n";

    }

    public long getFamiliaid() {
        return familiaid;
    }

    public void setFamiliaid(long familiaid) {
        this.familiaid = familiaid;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    @Override
    public String toString() {
        return "mFamilia{" + "familiaid=" + familiaid + ", familia=" + familia + '}';
    }

}
