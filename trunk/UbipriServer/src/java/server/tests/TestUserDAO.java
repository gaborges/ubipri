/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.tests;

import java.util.ArrayList;

import server.dao.UserDAO;
import server.model.Environment;

import server.model.User;

/**
 *
 * @author Guilherme
 */
public class TestUserDAO {

    private static UserDAO dao;

    public static void main(String[] args) {
        dao = new UserDAO();
        //testInsert(); //- OK
        //testUpdate(); //- OK
        //testGet(); //- OK
        //testGetAll(); //- OK
        //testGetListWithPagination(); // -- ok
        //testGetUserEnvironment(); //- OK
        //testGetUserEnvironmentDois(); //- OK
        //testGetUserAfterUpdateUserEnvironment(); //- OK
    }
    
    public static void testGetUserEnvironment(){
        User u =  dao.getUserEnvironment("borges", 1);
        System.out.println(u.getUserName()+","+u.getUsersEnvironment().getUserProfile().getName()+","+u.getCurrentEnvironment().getName());
    }
    
    public static void testGetUserEnvironmentDois(){
        User u =  dao.getUserEnvironment(1, 2);
        System.out.println(u.getUserName()+","+u.getUsersEnvironment().getUserProfile().getName()+","+u.getCurrentEnvironment().getName());
    }
    
    public static void testGetUserAfterUpdateUserEnvironment(){
        User u =  dao.getUserAfterUpdateUserEnvironment(1, 1);
        System.out.println(u.getUserName()+","+u.getUsersEnvironment().getUserProfile().getName()+","+u.getCurrentEnvironment().getName());
    }

    public static void testInsert() {
        // funciona
    }

    public static void testDelete() {
        // funciona
    }

    public static void testUpdate() {
        User u = new User();
        Environment e = new Environment();
        e.setId(1);
        u.setId(1);
        u.setFullName("Guilherme A. Borges");
        u.setPassword("12345");
        u.setUserName("guilherme");
        //u.setCurrentEnvironment(null);
        u.setCurrentEnvironment(e);
        dao.update(u);
        System.out.println("Atualizou");
    }

    public static void testGet() {
        User d = dao.get(1, false);
        System.out.println("ID: "+d.getId()+" Nome: "+d.getUserName()+" Nome completo: "+d.getFullName()+" Senha: "+d.getPassword()
                + " Ambiênte atual: " + ( (d.getCurrentEnvironment() == null) ? "Desconhecido" : "{id:"+d.getCurrentEnvironment().getId()+",name:"+d.getCurrentEnvironment().getName()+"}"));
    }

    public static void testGetAll() {
        ArrayList<User> list =  dao.getList();
        for(User d :list){
            System.out.println("ID: "+d.getId()+" Nome: "+d.getUserName()+" Nome completo: "+d.getFullName()+" Senha: "+d.getPassword()
                + " Ambiênte atual: "+( 
                    (d.getCurrentEnvironment() == null) ? "Desconhecido" : "{id:"+d.getCurrentEnvironment().getId()+",name:"+d.getCurrentEnvironment().getName()+"}"));       
        }
    }
  
    public static void testGetListWithPagination(){
        ArrayList<User> list =  dao.getList(1,3);
        for(User d :list){
            System.out.println("ID: "+d.getId()+" Nome: "+d.getUserName()+" Nome completo: "+d.getFullName()+" Senha: "+d.getPassword()
                + " Ambiênte atual: "+( 
                    (d.getCurrentEnvironment() == null) ? "Desconhecido" : "{id:"+d.getCurrentEnvironment().getId()+",name:"+d.getCurrentEnvironment().getName()+"}"));       
        }
    }

}
