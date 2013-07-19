/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.util;

/**
 *
 * @author Estudo
 */
public class Config {
    public static String dbName     = "localizaPos4";
    public static String dbServer   = "127.0.0.1";
    public static String dbUser     = "postgres";
    public static String dbPassword = "postgres";
    public static boolean debugSQL  = false;
    public static boolean debugLocalCommunication  = false;
    public static boolean debugClassesDAO  = false;
    public static boolean debugInteracao  = false;
    public static boolean debugResourceService  = true;
    public static boolean debugPrivacyModule  = true;
}
