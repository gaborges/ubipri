/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.util;

/**
 *
 * @author Estudo
 */
public class SingleConnection {
    
    private static AccessBD accessDB = null;
    
    private SingleConnection(){
        
    }

    public static AccessBD getAccessDB() {
        if(accessDB == null){
            accessDB = new AccessBD(
                        Config.dbServer, // IP do Servidor
                        Config.dbName, // Nome do Banco de dados
                        Config.dbUser, // Usuário
                        Config.dbPassword); // Senha
            accessDB.connect();
        }
        return accessDB;
    }
    
    public static boolean closeAccessDB(){
        if(accessDB != null){
            accessDB.connect();
            accessDB = null;
        }
        return true;
    }
}
