/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.tests;

import java.util.ArrayList;
import server.dao.DeviceDAO;
import server.model.Device;
import server.model.DeviceType;
import server.model.User;

/**
 *
 * @author Guilherme
 */
public class TestDeviceDAO {

    private static DeviceDAO dao;

    public static void main(String[] args) {
        dao = new DeviceDAO();
        //testInsert(); //- OK
        //testUpdate(); //- OK
        //testGet(); //- OK
        //testGetAll(); //- OK
        //testGetListWithPagination(); // -- ok
    }

    public static void testInsert() {
        Device d = new Device();
        d.setCode("6666teste6666");
        d.setName("Note rox");
        d.setUser(new User(1, "guilherme", "12345"));
        d.setDeviceType(new DeviceType(2, "notebook"));
        dao.insert(d);
        System.out.println("Inseriu");
    }

    public static void testDelete() {
    }

    public static void testUpdate() {
        Device d = new Device();
        d.setId(2);
        d.setCode("666teste666");
        d.setName("Note rox2");
        d.setUser(new User(1, "guilherme", "12345"));
        d.setDeviceType(new DeviceType(2, "notebook"));
        dao.update(d);
        System.out.println("Atualizou");
    }

    public static void testGet() {
        Device d = dao.get(2, false);
        System.out.println("Chegou aki 66");
        System.out.println("ID: "+d.getId()+" Nome: "+d.getName()+" Code: "+d.getCode()+" User: "+d.getUser().getUserName()
                + " DeviceType: "+ d.getDeviceType().getName() + " Num Communications: "
                +  (d.getDeviceCommunications() == null ? 0 : d.getDeviceCommunications().size()));
    }

    public static void testGetAll() {
        ArrayList<Device> list =  dao.getList();
        for(Device d :list) {
            System.out.println("ID: "+d.getId()+" Nome: "+d.getName()+" Code: "+d.getCode()+" User: "+d.getUser().getUserName()
                + " DeviceType: "+ d.getDeviceType().getName()+ " Num Communications: "
                +  d.getDeviceCommunications().size());
        }
    }
    
    public static void testGetListWithPagination(){
        ArrayList<Device> list =  dao.getList(1,3);
        for(Device d :list) {
            System.out.println("ID: "+d.getId()+" Nome: "+d.getName()+" Code: "+d.getCode()+" User: "+d.getUser().getUserName()
                + " DeviceType: "+ d.getDeviceType().getName());
        }
         
    }
}
