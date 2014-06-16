/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.tests;

import server.modules.communication.Communication;

/**
 *
 * @author Estudo
 */
public class TestCommunication {
    public static void main(String[] args) {
        Communication comm = new Communication();
       // comm.onChangeCurrentUserLocalization(1, "guilherme","12345","1234554321");
        
        String res = comm.onChangeCurrentUserLocalizationWithResponse(1, "borges","12345","1234554321",false);
        System.out.println("RES 2: "+res);
        
        //res = comm.onChangeCurrentUserLocalizationWithResponse(2, "guilherme","12345","1234554321");
        System.out.println("RES 2: "+res);
    }
}
