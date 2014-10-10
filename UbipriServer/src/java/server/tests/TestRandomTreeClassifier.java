/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.tests;

import server.model.EnvironmentType;
import server.model.LogEvent;
import server.model.UserProfileEnvironment;
import server.model.classify.Classifier;
import server.model.classify.ClassifierRandomTreeBorges;

/**
 *
 * @author Guilherme
 */
public class TestRandomTreeClassifier {
    
    /* 
    Valores possíveis:
        shift: LogEvent.DAY_SHIFT ou NIGHT_SHIFT
        workday: LogEvent.YES_WORKDAY ou NOT_WORKDAY
        weekday: LogEvent.DAY_OF_WEEK; ou LogEvent.DAY_OF_WEEKEND;
        profile (id;nome): 
            1;"Unknown"
            2;"Transient"
            3;"User"
            4;"Responsible"
            5;"Student"
            6;"Manager"
        EnvironmentType (id;nome):
            1;"Blocked"
            2;"Private"
            3;"Public"
    Saídas
        1 = "Blocked"
        2 = "Guest"
        3 = "Basic"
        4 = "Advanced"
        5 = "Administrative"
    
    */
    public static void main(String[] args) {
        //Todas as Possibilidades
        EnvironmentType et[] = {
                new EnvironmentType(1, "Blocked"),
                new EnvironmentType(2, "Private"),
                new EnvironmentType(3, "Public")};
        UserProfileEnvironment p[] = {  new UserProfileEnvironment(1, "Unknown"),
                                        new UserProfileEnvironment(2, "Transient"),
                                        new UserProfileEnvironment(3, "User"),
                                        new UserProfileEnvironment(4, "Responsible"),
                                        new UserProfileEnvironment(5, "Student"),
                                        new UserProfileEnvironment(6, "Manager")};
        char shift[] = {LogEvent.DAY_SHIFT,LogEvent.NIGHT_SHIFT};
        char workday[] = {LogEvent.YES_WORKDAY,LogEvent.NOT_WORKDAY};
        int week[] = {LogEvent.DAY_OF_WEEK,LogEvent.DAY_OF_WEEKEND};
        ClassifierRandomTreeBorges c = new ClassifierRandomTreeBorges();
        
        // All Blocked Environments
        //testaTipoAmbiente(p,et[0],shift,workday,week,c);
        
        // All Public Environments
        //testaTipoAmbiente(p,et[2],shift,workday,week,c);
        
        // All Private Environments
        //testaTipoAmbiente(p,et[1],shift,workday,week,c);
        
        // Unknown and private
        // testaTipoAmbienteProfile(p[0],et[1],shift,workday,week,c); // OK
        // Transient and private
        // testaTipoAmbienteProfile(p[1],et[1],shift,workday,week,c); // ok
        // testaTipoAmbienteProfileDetalha(p[1],et[1],shift,workday,week,c); // ok
        // User and private
        // testaTipoAmbienteProfile(p[2],et[1],shift,workday,week,c); // ok
        // testaTipoAmbienteProfileDetalha(p[2],et[1],shift,workday,week,c); // ok
        // Responsible and private
        // testaTipoAmbienteProfile(p[3],et[1],shift,workday,week,c); // NOp olhar - deu mais basid e menos advanced
        // testaTipoAmbienteProfileDetalha(p[3],et[1],shift,workday,week,c); 
        // Student and private
        // testaTipoAmbienteProfile(p[4],et[1],shift,workday,week,c); // ok
        // testaTipoAmbienteProfileDetalha(p[4],et[1],shift,workday,week,c); // OK
        // Manager and private
        // testaTipoAmbienteProfile(p[5],et[1],shift,workday,week,c); // - OK
//        for(char s : shift)
//            for(char work : workday)
//                for(int w : week)
//                    System.out.println(c.classify(p[0], et[1], s, work, w).getName());
        
        
        
        
    }
     
    private static void testaTipoAmbiente(UserProfileEnvironment[] p, EnvironmentType et, char[] shift, char[] workday, int[] week,Classifier c) {
        int i = 0;
        for(UserProfileEnvironment u : p){
            for(char s : shift)
                for(char work : workday)
                    for(int w : week){
                        i++;
                        System.out.print(i+" "+c.classify(u, et, s, work, w).getName()+ " ");
                    }
             
        }
    }
    
    private static void testaTipoAmbienteProfile(UserProfileEnvironment p, EnvironmentType et, char[] shift, char[] workday, int[] week,Classifier c) {
        int i = 0;
        for(char s : shift)
            for(char work : workday)
                for(int w : week){
                    i++;
                    System.out.print(i+" "+c.classify(p, et, s, work, w).getName()+ " ");
                }
             
    }
    
    private static void testaTipoAmbienteProfileDetalha(UserProfileEnvironment p, EnvironmentType et, char[] shift, char[] workday, int[] week,Classifier c) {
        int i = 0;
        for(int w : week)
            for(char s : shift)
                for(char work : workday){
                    i++;
                    System.out.print(i+" "+c.classify(p, et, s, work, w).getName()+ " ");
                    System.out.print(" "+p.getName()+ " ");
                    System.out.print(" "+et.getName()+ " ");
                    System.out.println(" "+w+ " "+s+" "+work);
                }
             
    }
    
}
