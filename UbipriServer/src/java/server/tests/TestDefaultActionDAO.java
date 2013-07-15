/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.tests;

import java.util.ArrayList;
import server.dao.ActionDAO;
import server.model.Action;

/**
 *
 * @author Guilherme
 */
public class TestDefaultActionDAO {

    private static ActionDAO dao;

    public static void main(String[] args) {
        dao = new ActionDAO();
        //testInsert(); //- OK
        //testUpdate(); //- OK
        //testGet(); //- OK
        //testGetAll(); //- OK
        //testGetListWithPagination(); // -- ok
    }

    public static void testInsert() {
        // funciona
    }

    public static void testDelete() {
        // funciona
    }

    public static void testUpdate() { // não precisa testar
        // funciona
    }

    public static void testGet() {
        //DefaultAction d = dao.get(2,1,1);
        Action d = dao.get(1);
        System.out.println("Functionality {id: "+d.getFunctionality().getId()+",name: "+d.getFunctionality().getName() +"\n"
                + " Environment Type {id:"+d.getAccessLevel().getId()+",name:"+d.getAccessLevel().getImpactFactor()+"}\n"
                + " LocatizationType {id: "+d.getEnvironment().getLocalizationType().getId() +",name:"+d.getEnvironment().getLocalizationType().getName()
                + ",precision:"+d.getEnvironment().getLocalizationType().getPrecision()+",metric:"+d.getEnvironment().getLocalizationType().getMetric()+"}"
                + " Environment {id:"+d.getEnvironment().getId()+",name:"+d.getEnvironment().getName()+",latitude:"+d.getEnvironment().getLatitude()+",longitude:"+d.getEnvironment().getLongitude()+"}\n"
                + " Father of Environment: " + ( (d.getEnvironment().getParentEnvironment()== null) ? "Não possui\n" : "{id:"+d.getEnvironment().getParentEnvironment().getId()+",name:"+d.getEnvironment().getParentEnvironment().getName()+"}\n")
                + " Default Action {action:"+d.getAction()+",startDate:"+d.getStartDate()+",endDate:"+d.getEndDate()+",startInterval:"+d.getStartDailyInterval()+",endInterval:"+d.getEndDailyInterval()+"}");
    }

    public static void testGetAll() {
        ArrayList<Action> list =  dao.getList();
        for(Action d :list){
            System.out.println("Functionality {id: "+d.getFunctionality().getId()+",name: "+d.getFunctionality().getName() +"\n"
                + " Environment Type {id:"+d.getAccessLevel().getId()+",name:"+d.getAccessLevel().getImpactFactor()+"}\n"
                + " LocatizationType {id: "+d.getEnvironment().getLocalizationType().getId() +",name:"+d.getEnvironment().getLocalizationType().getName()
                + ",precision:"+d.getEnvironment().getLocalizationType().getPrecision()+",metric:"+d.getEnvironment().getLocalizationType().getMetric()+"}"
                + " Environment {id:"+d.getEnvironment().getId()+",name:"+d.getEnvironment().getName()+",latitude:"+d.getEnvironment().getLatitude()+",longitude:"+d.getEnvironment().getLongitude()+"}\n"
                + " Father of Environment: " + ( (d.getEnvironment().getParentEnvironment()== null) ? "Não possui\n" : "{id:"+d.getEnvironment().getParentEnvironment().getId()+",name:"+d.getEnvironment().getParentEnvironment().getName()+"}\n")
                + " Default Action {action:"+d.getAction()+",startDate:"+d.getStartDate()+",endDate:"+d.getEndDate()+",startInterval:"+d.getStartDailyInterval()+",endInterval:"+d.getEndDailyInterval()+"}");
            System.out.println("**********************");
        }
    }
  
    public static void testGetListWithPagination(){
        ArrayList<Action> list =  dao.getList(4,3);
         for(Action d :list){
            System.out.println("Functionality {id: "+d.getFunctionality().getId()+",name: "+d.getFunctionality().getName() +"\n"
                + " Environment Type {id:"+d.getAccessLevel().getId()+",name:"+d.getAccessLevel().getImpactFactor()+ ",impact factor:"+"}\n"
                + " LocatizationType {id: "+d.getEnvironment().getLocalizationType().getId() +",name:"+d.getEnvironment().getLocalizationType().getName()
                + ",precision:"+d.getEnvironment().getLocalizationType().getPrecision()+",metric:"+d.getEnvironment().getLocalizationType().getMetric()+"}"
                + " Environment {id:"+d.getEnvironment().getId()+",name:"+d.getEnvironment().getName()+",latitude:"+d.getEnvironment().getLatitude()+",longitude:"+d.getEnvironment().getLongitude()+"}\n"
                + " Father of Environment: " + ( (d.getEnvironment().getParentEnvironment()== null) ? "Não possui\n" : "{id:"+d.getEnvironment().getParentEnvironment().getId()+",name:"+d.getEnvironment().getParentEnvironment().getName()+"}\n")
                + " Default Action {action:"+d.getAction()+",startDate:"+d.getStartDate()+",endDate:"+d.getEndDate()+",startInterval:"+d.getStartDailyInterval()+",endInterval:"+d.getEndDailyInterval()+"}");
            System.out.println("**********************");
        }
    }

}
