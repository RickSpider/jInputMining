/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doxa.jinputmining.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author BlackSpider
 */
public class FileOutput {
    
    private String ruta;
    private String data;

    public FileOutput(String ruta, String data) {
        this.ruta = ruta;
        this.data = data;
    }
    
    public void generarArchivo(){
    
         try {

            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    
    
}
