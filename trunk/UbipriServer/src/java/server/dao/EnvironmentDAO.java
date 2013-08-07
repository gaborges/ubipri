/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import server.model.Environment;
import server.model.EnvironmentType;
import server.model.LocalizationType;
import server.model.LogEvent;
import server.util.AccessBD;
import server.util.Config;
import server.util.SingleConnection;

/**
 *
 * @author Estudo
 */
public class EnvironmentDAO {

    private AccessBD db = null;
    private PreparedStatement pstmt;
    private ResultSet rs;

    /**
     *
     * @param @return contrutor conecta-se por padrão com as configurações
     * dentro da classe server.util.Config;
     */
    public EnvironmentDAO() {
        db = SingleConnection.getAccessDB();
    }

    /**
     *
     * @param @return contrutor recebe por parâmetro um acesso BD;
     */
    public EnvironmentDAO(AccessBD db) {
        this.db = db;
    }

    /**
     *  retorna a conexão que está sendo usada pela classe
     * 
     * @param @return 
     */
    public Connection getConnection() {
        return db.getConnection();
    }

    /**
     * insere um device no banco de dados.
     *
     * @param
     * @return
     */
    public void insert(Environment o) {
        String sql =
                " INSERT INTO environment (env_name,env_latitude,env_longitude,parent_environment_id,"
                    + "localization_type_id,environment_type_id) VALUES (?,?,?,?,?,?,?) ;";
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, o.getName());
            pstmt.setDouble(2, o.getLatitude());
            pstmt.setDouble(3, o.getLongitude());
            pstmt.setInt(4, o.getParentEnvironment().getId());
            pstmt.setInt(5, o.getEnvironmentType().getId());
            pstmt.setInt(6, o.getLocalizationType().getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        //this.db.disconnect();
    }

    /**
     * Remove um registro no banco de dados através do id passado pelo objeto.
     * Ao menos o objeto deve conter o ID do device a ser removido.
     *
     * @param
     * @return void
     *
     */
    public void delete(Environment o) {
        String sql =
                " DELETE FROM environment WHERE env_id = ? ; ";
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, o.getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        //this.db.disconnect();
    }

    /**
     * Atualiza um registro no banco de dados, somente serão atualizados os
     * dados da classe, não atualizando os demais objetos que são agregados ou
     * que compõem ele.
     *
     * @param um device
     * @return void
     */
    public void update(Environment o) {
        String sql =
                " UPDATE environment SET env_name = ?, env_latitude = ?, enc_longitude = ?, "
                + " parent_environment_id = ?, localization_type_id = ?, environment_type_id = ? " +
                " WHERE env_id = ? ;";
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, o.getName());
            pstmt.setDouble(2, o.getLatitude());
            pstmt.setDouble(3, o.getLongitude());
            pstmt.setInt(4, o.getParentEnvironment().getId());
            pstmt.setInt(5, o.getLocalizationType().getId());
            pstmt.setInt(6, o.getEnvironmentType().getId());
            pstmt.setInt(7, o.getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        //this.db.disconnect();
    }

    /**
     * passa-se por parâmetro dois argumentos: <ul> <li> 1) o id do device que será
     * retornado;</li> <li>2) se true retorna além dos dados do device suas comunicações
     * e funcionalidades (Não implementado ainda) </li>
     * </ul>
     * @param id a ser retornado, argumento se vão ser necessários todos os
     * dados relacionados com a classe
     * @return retorna um device
     */
    public Environment get(int id, boolean allData) {
        Environment environment = null;
        String sql = "";
        
        // se encontrar -1 quer dizer que chegou ao nodo raiz  (recursivamente), retornando nulo
        if(id == -1) return null;
        
        if (allData) {
            // não implementado ainda
            sql =
                    " SELECT env_name,env_latitude,env_longitude,parent_environment_id,localization_type_id," +
                        " loctyp_name, loctyp_precision, loctyp_metric, environment_type_id, envtyp_name " +
                    " FROM environment, localization_type, environment_type " +
                    " WHERE environment_type_id = envtyp_id AND localization_type_id = loctyp_id AND env_id = ? ;";
        } else {

            sql =
                    " SELECT env_name,env_latitude,env_longitude,parent_environment_id,localization_type_id," +
                        " loctyp_name, loctyp_precision, loctyp_metric, environment_type_id, envtyp_name " +
                    " FROM environment, localization_type,environment_type " +
                    " WHERE environment_type_id = envtyp_id AND localization_type_id = loctyp_id AND env_id = ? ;";
        }
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                environment = new Environment();
                environment.setId(id); // Seta o ID
                environment.setName(rs.getString("env_name"));
                environment.setLatitude(rs.getDouble("env_latitude"));
                environment.setLongitude(rs.getDouble("env_longitude"));
                environment.setLocalizationType(
                        new LocalizationType(rs.getInt("localization_type_id"), rs.getString("loctyp_name"),
                            rs.getDouble("loctyp_precision"),rs.getString("loctyp_metric")));
                environment.setEnvironmentType(
                        new EnvironmentType(rs.getInt("environment_type_id"), rs.getString("envtyp_name")));
                environment.setParentEnvironment(get(rs.getInt("parent_environment_id"), false));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        //this.db.disconnect();
        return environment;
    }
    
    private Environment getExclusive(int id, boolean allData) {
        EnvironmentDAO dao = new EnvironmentDAO();
        return dao.get(id, allData);
    }

    /**
     * retorna todos os registros cadastrados no banco de dados referentes ao
     * objeto alvo
     *
     * @param
     * @return
     */
    public ArrayList<Environment> getList() {
        return getList(-1, -1, false);
    }

    /**
     * Retorna os registros cadastrados no banco de dados a partir de parâmetro de paginação.
     * 
     * @param passa-se o índice inicial e o número de elementos a serem
     * retornados
     * @return retorna todos os devices que começam pelo índice Begin até uma
     * quatidade Limit
     */
    public ArrayList<Environment> getList(int begin, int limit) {
        return getList(begin, limit, false);
    }

    /**
     * Retorna todos os devices que começam pelo índice Begin até
     * uma quatidade Limit, método serve de suporte aos outros 2 lists
     * 
     * @param @return 
     */
    private ArrayList<Environment> getList(int begin, int limit, boolean all) {
        ArrayList<Environment> list = null;
        Environment temp = null;
        String sql = "";

        //this.db.connect();
        try {
            if (all || (begin == -1) || (limit == -1)) {
                sql = " SELECT env_id, env_name,env_latitude,env_longitude,parent_environment_id,localization_type_id," +
                            " loctyp_name, loctyp_precision, loctyp_metric, environment_type_id, envtyp_name " +
                        " FROM environment, localization_type " +
                        " WHERE environment_type_id = envtyp_id AND localization_type_id = loctyp_id;";
                pstmt = getConnection().prepareStatement(sql);
            } else {
                sql = " SELECT env_id,env_name,env_latitude,env_longitude,parent_environment_id,localization_type_id," +
                            " loctyp_name, loctyp_precision, loctyp_metric, environment_type_id, envtyp_name " +
                        " FROM environment, localization_type " +
                        " WHERE environment_type_id = envtyp_id AND localization_type_id = loctyp_id LIMIT ? OFFSET ?";
                pstmt = getConnection().prepareStatement(sql);
                pstmt.setInt(1, limit);
                pstmt.setInt(2, (begin-1));
            }

            rs = pstmt.executeQuery();
            list = new ArrayList<Environment>();
            while (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                temp = new Environment();
                temp.setId(rs.getInt("env_id")); // Seta o ID
                temp.setName(rs.getString("env_name"));
                temp.setLatitude(rs.getDouble("env_latitude"));
                temp.setLongitude(rs.getDouble("env_longitude"));
                temp.setLocalizationType(
                        new LocalizationType(rs.getInt("localization_type_id"), rs.getString("loctyp_name"),
                            rs.getDouble("loctyp_precision"),rs.getString("loctyp_metric")));
                temp.setEnvironmentType(
                        new EnvironmentType(rs.getInt("environment_type_id"), rs.getString("envtyp_name")));
                temp.setParentEnvironment(getExclusive(rs.getInt("parent_environment_id"), false));

                list.add(temp);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        //this.db.disconnect();
        return list;
    }
    
    /**
     * insere um device no banco de dados.
     *
     * @param
     * @return
     */
    public void insertLog(LogEvent o) {
        String sql =
                " INSERT INTO log_event (log_time,user_id,environment_id,log_code,device_id,log_event) " +
                " VALUES (?,?,?,?,?,?); ";
        if(Config.debugSQL) System.out.println(" INSERT LOG: "+o.getTime().toString()+","+o.getUser().getId()+","
                +o.getEnvironment().getId()+","+o.getCurrentData()+","+ o.getDevice().getId()+","+o.getEvent());
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setTimestamp(1, new Timestamp(o.getTime().getTime()));
            pstmt.setInt(2, o.getUser().getId());
            pstmt.setInt(3, o.getEnvironment().getId());
            pstmt.setString(4, o.getCurrentData());
            pstmt.setInt(5, o.getDevice().getId());
            pstmt.setString(6, o.getEvent());
            pstmt.execute();
            pstmt.close();
            
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        //this.db.disconnect();
    }
}
