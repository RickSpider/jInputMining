/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doxa.jinputmining.controller;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author BlackSpider
 */
public class dbUtil{
  
  private static final String PERSISTENCE_UNIT_NAME = "dbOld";
  private static EntityManagerFactory factory;

  public static EntityManagerFactory getEntityManagerFactory() {
    if (factory == null) {
      factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }
    return factory;
  }

  public static void shutdown() {
    if (factory != null) {
      factory.close();
    }
  }
  
}
