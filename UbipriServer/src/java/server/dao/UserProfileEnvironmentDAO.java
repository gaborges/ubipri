/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import server.model.UserProfileEnvironment;
import server.util.AccessBD;
import server.util.Config;

/**
 *
 * @author Guazzelli
 */
public class UserProfileEnvironmentDAO {
    
    private AccessBD db = null;
    private PreparedStatement pstmt;
    private ResultSet rs;

    /**
     *
     * @param @return contrutor conecta-se por padrão com as configurações
     * dentro da classe server.util.Config;
     */
    public UserProfileEnvironmentDAO() {
        db = new AccessBD(
                Config.dbServer, // IP do Servidor
                Config.dbName, // Nome do Banco de dados
                Config.dbUser, // Usuário
                Config.dbPassword); // Senha
    }

    /**
     *
     * @param @return contrutor recebe por parâmetro um acesso BD;
     */
    public UserProfileEnvironmentDAO(AccessBD db) {
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
    public void insert(UserProfileEnvironment o) {
        String sql =
                " INSERT INTO user_profile_environment (useproenv_name) VALUES (?); ";
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, o.getName());
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
    public void delete(UserProfileEnvironment o) {
        String sql =
                " DELETE FROM user_profile_environment WHERE useproenv_id = ? ; ";
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
    public void update(UserProfileEnvironment o) {
        String sql =
                " UPDATE user_profile_environment SET useproenv_name = ? WHERE useproenv_id = ? ;";
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, o.getName());
            pstmt.setInt(2, o.getId());
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
    public UserProfileEnvironment get(int id, boolean allData) {
        UserProfileEnvironment temp = new UserProfileEnvironment();
        String sql = "";
        
        // se encontrar -1 quer dizer que chegou ao nodo raiz  (recursivamente), retornando nulo
        if(id == -1) return null;
        
        if (allData) {
            // não implementado ainda
            sql =
                    " SELECT useproenv_name " +
                    " FROM user_profile_environment " +
                    " WHERE useproenv_id = ? ;";
        } else {

            sql =
                    " SELECT useproenv_name " +
                    " FROM user_profile_environment " +
                    " WHERE useproenv_id = ? ;";
        }
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            
            if (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                temp = new UserProfileEnvironment(id, rs.getString("useproenv_name"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        //this.db.disconnect();
        return temp;
    }

    /**
     * retorna todos os registros cadastrados no banco de dados referentes ao
     * objeto alvo
     *
     * @param
     * @return
     */
    public ArrayList<UserProfileEnvironment> getList() {
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
    public ArrayList<UserProfileEnvironment> getList(int begin, int limit) {
        return getList(begin, limit, false);
    }

    /**
     * Retorna todos os registros que começam pelo índice Begin até
     * uma quatidade Limit, método serve de suporte aos outros 2 lists
     * 
     * @param @return 
     */
    private ArrayList<UserProfileEnvironment> getList(int begin, int limit, boolean all) {
        ArrayList<UserProfileEnvironment> list = null;
        String sql = "";

        //this.db.connect();
        try {
            if (all || (begin == -1) || (limit == -1)) {
                sql = "SELECT useproenv_id, useproenv_name " +
                        " FROM user_profile_environment "
                        + " ORDER BY useproenv_id;";
                pstmt = getConnection().prepareStatement(sql);
            } else {
                sql = " SELECT useproenv_id, useproenv_name " +
                        " FROM user_profile_environment ORDER BY useproenv_id " +
                        " LIMIT ? OFFSET ? "
                        + " ORDER BY useproenv_id;";
                pstmt = getConnection().prepareStatement(sql);
                pstmt.setInt(1, limit);
                pstmt.setInt(2, (begin-1));
            }

            rs = pstmt.executeQuery();
            list = new ArrayList<UserProfileEnvironment>();
            while (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                list.add(new UserProfileEnvironment(rs.getInt("useproenv_id"), rs.getString("useproenv_name")));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        //this.db.disconnect();
        return list;
    }
}
