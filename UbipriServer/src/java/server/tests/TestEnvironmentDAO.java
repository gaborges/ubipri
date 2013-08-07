/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.tests;

import java.util.ArrayList;
import server.dao.EnvironmentDAO;

import server.dao.UserDAO;
import server.model.Environment;

import server.model.User;
import server.modules.privacy.PrivacyControlUbiquitous;

/**
 *
 * @author Guilherme
 */
public class TestEnvironmentDAO {

    private static EnvironmentDAO dao;

    public static void main(String[] args) {
        dao = new EnvironmentDAO();
        //testInsert(); //- OK
        //testUpdate(); //- OK
        //testGet(); //- OK
        //testGetAll(); //- OK
        //testGetListWithPagination(); // -- ok
        //testJsonMap(); - OK
    }
    
    public static void testJsonMap(){
        PrivacyControlUbiquitous p = new PrivacyControlUbiquitous();
        String res = p.getJsonEnvironments("borges", "12345","1111111111", -0);
        System.out.println("RESP: "+res);
    }

    public static void testInsert() {
        // funciona
    }

    public static void testDelete() {
        // funciona
    }

    public static void testUpdate() { // não precisa testar
        User u = new User();
        Environment e = new Environment();
        e.setId(1);
        u.setId(1);
        u.setFullName("Guilherme A. Borges");
        u.setPassword("12345");
        u.setUserName("guilherme");
        //u.setCurrentEnvironment(null);
        u.setCurrentEnvironment(e);
        //dao.update(u);
        System.out.println("Atualizou");
    }

    public static void testGet() {
        Environment d = dao.get(2, false);
        System.out.println("ID: "+d.getId()+" Nome: "+d.getName()+" Latitude: "+d.getLatitude()+" Longitude: "+d.getLongitude()
                + " Tipo de localização: {id:"+d.getLocalizationType().getId() +",nome:"+d.getLocalizationType().getName()
                + ",precision:"+d.getLocalizationType().getPrecision()+",metric:"+d.getLocalizationType().getMetric()+"}"
                + " Pai: " + ( (d.getParentEnvironment()== null) ? "Não possui" : "{id:"+d.getParentEnvironment().getId()+",name:"+d.getParentEnvironment().getName()+"}"));
    }

    public static void testGetAll() {
        ArrayList<Environment> list =  dao.getList();
        for(Environment d :list){
            System.out.println("ID: "+d.getId()+" Nome: "+d.getName()+" Latitude: "+d.getLatitude()+" Longitude: "+d.getLongitude()
                + " Tipo de localização: {id:"+d.getLocalizationType().getId() +",nome:"+d.getLocalizationType().getName()
                + ",precision:"+d.getLocalizationType().getPrecision()+",metric:"+d.getLocalizationType().getMetric()+"}"
                + " Pai: " + ( (d.getParentEnvironment()== null) ? "Não possui" : "{id:"+d.getParentEnvironment().getId()+",name:"+d.getParentEnvironment().getName()+"}"));
        }
    }
  
    public static void testGetListWithPagination(){
        ArrayList<Environment> list =  dao.getList(1,3);
         for(Environment d :list){
            System.out.println("ID: "+d.getId()+" Nome: "+d.getName()+" Latitude: "+d.getLatitude()+" Longitude: "+d.getLongitude()
                + " Tipo de localização: {id:"+d.getLocalizationType().getId() +",nome:"+d.getLocalizationType().getName()
                + ",precision:"+d.getLocalizationType().getPrecision()+",metric:"+d.getLocalizationType().getMetric()+"}"
                + " Pai: " + ( (d.getParentEnvironment()== null) ? "Não possui" : "{id:"+d.getParentEnvironment().getId()+",name:"+d.getParentEnvironment().getName()+"}"));
        }
    }

}
